package online.exam.coindesk.dao;

import online.exam.coindesk.entity.CoinInfo;
import org.springframework.data.repository.CrudRepository;

public interface CoinInfoDao extends CrudRepository<CoinInfo, Integer> {
}
