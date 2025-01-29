package com.bjsxt.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bjsxt.domain.EntrustOrder;
import com.baomidou.mybatisplus.extension.service.IService;
import com.bjsxt.vo.TradeEntrustOrderVo;

public interface EntrustOrderService extends IService<EntrustOrder>{


    /**
     * 分页查询委托单
     * @param page
     *  分页参数
     * @param userId
     *      用户的id
     * @param symbol
     *      交易对
     * @param type
     *      交易类型
     * @return
     */
    Page<EntrustOrder> findByPage(Page<EntrustOrder> page, Long userId, String symbol, Integer type);


    /**
     * 获取用户的历史委托记录
     * @param page
     *      分页参数
     * @param symbol
     *          交易对
     * @param userId
     *          用户的Id
     * @return
     */
    Page<TradeEntrustOrderVo> getHistoryEntrustOrder(Page<EntrustOrder> page, String symbol, Long userId);
}
