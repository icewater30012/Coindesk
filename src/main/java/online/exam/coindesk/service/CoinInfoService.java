package online.exam.coindesk.service;

import online.exam.coindesk.dao.CoinInfoDao;
import online.exam.coindesk.entity.CoinInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CoinInfoService {

    @Autowired
    private CoinInfoDao coinInfoDao;

    public List<CoinInfo> findAllCoins(){
        return (List<CoinInfo>) coinInfoDao.findAll();
    }

    public Optional<CoinInfo> findCoin(final Integer i){
        return coinInfoDao.findById(i);
    }

    public void createCoin(CoinInfo coinInfo){
        coinInfoDao.save(coinInfo);
    }

    public void deleteCoin(CoinInfo coinInfo){
        coinInfoDao.delete(coinInfo);
    }

    public void deleteAllCoins(){
        coinInfoDao.deleteAll();
    }

    public long count(){
        return coinInfoDao.count();
    }
}
