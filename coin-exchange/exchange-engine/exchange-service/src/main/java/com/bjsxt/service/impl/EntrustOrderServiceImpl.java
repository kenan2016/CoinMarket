package com.bjsxt.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bjsxt.domain.EntrustOrder;
import com.bjsxt.domain.TurnoverOrder;
import com.bjsxt.mapper.EntrustOrderMapper;
import com.bjsxt.service.EntrustOrderService;
import com.bjsxt.service.MarketService;
import com.bjsxt.service.TurnoverOrderService;
import com.bjsxt.vo.TradeEntrustOrderVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class EntrustOrderServiceImpl extends ServiceImpl<EntrustOrderMapper, EntrustOrder> implements EntrustOrderService{

    @Autowired
    private MarketService marketService ;

    @Autowired
    private TurnoverOrderService turnoverOrderService ;
    /**
     * 分页查询委托单
     *
     * @param page   分页参数
     * @param userId 用户的id
     * @param symbol 交易对
     * @param type   交易类型
     * @return
     */
    @Override
    public Page<EntrustOrder> findByPage(Page<EntrustOrder> page, Long userId, String symbol, Integer type) {
        return page(page,
                 new LambdaQueryWrapper<EntrustOrder>()
                            .eq(EntrustOrder::getUserId,userId)
                            .eq(!StringUtils.isEmpty(symbol) ,EntrustOrder::getSymbol ,symbol)
                            .eq(type!=null && type!=0,EntrustOrder::getType ,type)
                            .orderByDesc(EntrustOrder::getCreated)

                );
    }


    /**
     * 获取用户的历史委托记录
     *
     * @param page   分页参数
     * @param symbol 交易对
     * @param userId 用户的Id
     * @return
     */
    @Override
    public Page<TradeEntrustOrderVo> getHistoryEntrustOrder(Page<EntrustOrder> page, String symbol, Long userId) {
        // 该用户对该交易对的交易记录
        Page<EntrustOrder> entrustOrderPage = page(page, new LambdaQueryWrapper<EntrustOrder>()
                .eq(EntrustOrder::getUserId, userId)
                .eq(EntrustOrder::getSymbol, symbol)
        );
        Page<TradeEntrustOrderVo> tradeEntrustOrderVoPage = new Page<>(page.getCurrent(),page.getSize());
        List<EntrustOrder> entrustOrders = entrustOrderPage.getRecords();
        if(CollectionUtils.isEmpty(entrustOrders)){
            tradeEntrustOrderVoPage.setRecords(Collections.emptyList()) ;
        }else{
            List<TradeEntrustOrderVo> tradeEntrustOrderVos = entrustOrders2tradeEntrustOrderVos(entrustOrders) ;
            tradeEntrustOrderVoPage.setRecords(tradeEntrustOrderVos) ;
        }

        return tradeEntrustOrderVoPage;
    }

    /**
     * 将委托单装化为TradeEntrustOrderVo
     * @param entrustOrders
     *      委托单
     * @return TradeEntrustOrderVos
     */
    private List<TradeEntrustOrderVo> entrustOrders2tradeEntrustOrderVos(List<EntrustOrder> entrustOrders) {
        List<TradeEntrustOrderVo> tradeEntrustOrderVos = new ArrayList<>(entrustOrders.size());
        for (EntrustOrder entrustOrder : entrustOrders) {
            tradeEntrustOrderVos.add(entrustOrder2TradeEntrustOrderVo(entrustOrder)) ;
        }
        return tradeEntrustOrderVos ;
    }

    private TradeEntrustOrderVo entrustOrder2TradeEntrustOrderVo(EntrustOrder entrustOrder) {
        TradeEntrustOrderVo tradeEntrustOrderVo = new TradeEntrustOrderVo();
        tradeEntrustOrderVo.setOrderId(entrustOrder.getId());
        tradeEntrustOrderVo.setCreated(entrustOrder.getCreated());


        tradeEntrustOrderVo.setType(entrustOrder.getType().intValue()); //1-买入；2-卖出
        // 查询已经成交的额度
        BigDecimal dealAmount = BigDecimal.ZERO ;
        BigDecimal dealVolume = BigDecimal.ZERO ;
        if(tradeEntrustOrderVo.getType()==1){
            List<TurnoverOrder> buyTurnoverOrders = turnoverOrderService.getBuyTurnoverOrder(entrustOrder.getId(),entrustOrder.getUserId()) ;
//            buyTurnoverOrders.fo
        }
        if(tradeEntrustOrderVo.getType()==2){
            List<TurnoverOrder> sellTurnoverOrders = turnoverOrderService.getSellTurnoverOrder(entrustOrder.getId(),entrustOrder.getUserId()) ;
        }


        // 算买卖的额度
        tradeEntrustOrderVo.setDealAmount(dealAmount); // 已经成交的总量
//        tradeEntrustOrderVo.setDealAvgPrice(); // 成交的评价价格
//        tradeEntrustOrderVo.setDealVolume(); // 成功的总额度

        return null;
    }
}
