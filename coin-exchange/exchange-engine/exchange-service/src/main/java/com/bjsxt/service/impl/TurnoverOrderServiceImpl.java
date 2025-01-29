package com.bjsxt.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bjsxt.domain.TurnoverOrder;
import com.bjsxt.mapper.TurnoverOrderMapper;
import com.bjsxt.service.TurnoverOrderService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TurnoverOrderServiceImpl extends ServiceImpl<TurnoverOrderMapper, TurnoverOrder> implements TurnoverOrderService {

    /**
     * 查询分页对象
     *
     * @param page   分页数据
     * @param userId 用户的ID
     * @param symbol 交易对
     * @param type   交易类型
     * @return
     */
    @Override
    public Page<TurnoverOrder> findByPage(Page<TurnoverOrder> page, Long userId, String symbol, Integer type) {
//        return page(page,new LambdaQueryWrapper<TurnoverOrder>().eq(TurnoverOrder::getus));
        return null;
    }


    /**
     * 获取买入的订单的成功的记录
     *
     * @param orderId
     * @return
     */
    @Override
    public List<TurnoverOrder> getBuyTurnoverOrder(Long orderId, Long userId) {
        return list(new LambdaQueryWrapper<TurnoverOrder>().eq(TurnoverOrder::getOrderId, orderId)
                .eq(TurnoverOrder::getBuyUserId, userId)
        );
    }


    /**
     * 获取卖出订单的成交记录
     *
     * @param orderId
     * @return
     */
    @Override
    public List<TurnoverOrder> getSellTurnoverOrder(Long orderId,Long userId) {
        return list(new LambdaQueryWrapper<TurnoverOrder>().eq(TurnoverOrder::getOrderId, orderId)
                .eq(TurnoverOrder::getSellUserId, userId)
        );
    }
}
