package antifraud.service;

import antifraud.model.Result;
import antifraud.model.dto.AmountRequest;
import antifraud.model.dto.ResultResponse;
import antifraud.repository.StolenCardRepository;
import antifraud.repository.SuspiciousIDRepository;
import antifraud.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.commons.lang3.StringUtils;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class TransactionService {

    @Autowired
    StolenCardRepository stolenCardRepository;

    @Autowired
    SuspiciousIDRepository suspiciousIDRepository;

    @Autowired
    TransactionRepository transactionRepository;

    @Transactional
    public ResultResponse processAmount(AmountRequest amount) {
        transactionRepository.save(amount);
        Result result=null;
        List<String> wordsForInfo = new ArrayList<>();
        List<AmountRequest> list = getFromLastHour(amount);
        if(getNumberOfRegions(list, amount)>2) {
            result = Result.PROHIBITED;
            wordsForInfo.add("region-correlation");
        }
        if(getNumberOfIps(list, amount)>2) {
            result = Result.PROHIBITED;
            wordsForInfo.add("ip-correlation");
        }
        if(getNumberOfRegions(list, amount)==2) {
            result = Result.MANUAL_PROCESSING;
            wordsForInfo.add("region-correlation");
        }
        if(getNumberOfIps(list, amount)==2) {
            result = Result.MANUAL_PROCESSING;
            wordsForInfo.add("ip-correlation");
        }
        if(stolenCardRepository.existsByNumber(amount.getNumber())) {
            result = Result.PROHIBITED;
            wordsForInfo.add("card-number");
        }
        if(suspiciousIDRepository.existsByIp(amount.getIp())) {
            result = Result.PROHIBITED;
            wordsForInfo.add("ip");
        }
        Result result1 =getResultByAmount(amount);
        if(result1==Result.PROHIBITED) {
            wordsForInfo.add("amount");
            result = Result.PROHIBITED;
        }
        if (result1==Result.MANUAL_PROCESSING) {
            if(result != Result.PROHIBITED) {
                result = Result.MANUAL_PROCESSING;
                wordsForInfo.add("amount");
            }
        }
        if (result1==Result.ALLOWED) {
            if(result != Result.PROHIBITED && result != Result.MANUAL_PROCESSING) {
                result = Result.ALLOWED;
                wordsForInfo.add("none");
            }
        }

        String info = "none";
        wordsForInfo.sort(Comparator.naturalOrder());
        if(wordsForInfo.size() > 1) {
           info =  StringUtils.join(wordsForInfo, ", ");
        } else if (wordsForInfo.size()>0){
            info=wordsForInfo.get(0);
        }


        return new ResultResponse(result, info);
    }

    private Result getResultByAmount(AmountRequest amount) {
        if(amount.getAmount() <= 200) {
            return Result.ALLOWED;
        } else  if(amount.getAmount() <= 1500){
            return Result.MANUAL_PROCESSING;
        } else {
            return Result.PROHIBITED;
        }
    }

    private List<AmountRequest> getFromLastHour(AmountRequest amount) {
        return transactionRepository.getAllOneHourOld(amount.getNumber(), amount.getDate().minusHours(1), amount.getDate());
    }

    private long getNumberOfRegions(List<AmountRequest> list, AmountRequest amount) {
        return list.stream().map(a -> a.getRegion()).filter(a -> !a.equals(amount.getRegion())).distinct().count();
    }

    private long getNumberOfIps(List<AmountRequest> list, AmountRequest amount) {
        return list.stream().map(a -> a.getIp()).filter(a -> !a.equals(amount.getIp())).distinct().count();
    }

}
