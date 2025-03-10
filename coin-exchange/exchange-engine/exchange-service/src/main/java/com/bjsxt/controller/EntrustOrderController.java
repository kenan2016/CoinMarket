package com.bjsxt.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bjsxt.domain.EntrustOrder;
import com.bjsxt.model.R;
import com.bjsxt.service.EntrustOrderService;
import com.bjsxt.vo.TradeEntrustOrderVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("/entrustOrders")
@Api(tags = "委托记录")
public class EntrustOrderController {

    @Autowired
    private EntrustOrderService entrustOrderService ;


    @GetMapping
    @ApiOperation(value = "查询用户的委托记录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current" ,value = "当前页") ,
            @ApiImplicitParam(name = "size" ,value = "显示的条数") ,
            @ApiImplicitParam(name = "symbol" ,value = "交易对") ,
            @ApiImplicitParam(name = "symbol" ,value = "类型") ,
    })
    public R<Page<EntrustOrder>> findByPage(@ApiIgnore Page<EntrustOrder> page,String symbol ,Integer type){
        Long userId = Long.valueOf(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString()) ;
        Page<EntrustOrder> entrustOrderPage = entrustOrderService.findByPage(page,userId,symbol,type) ;
        return R.ok(entrustOrderPage) ;
    }


    @GetMapping("/history/{symbol}")
    @ApiOperation(value = "查询历史的委托单记录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current",value = "当前页") ,
            @ApiImplicitParam(name = "size",value = "条数") ,
    })
    public R<Page<TradeEntrustOrderVo>> historyEntrustOrder(@ApiIgnore Page<EntrustOrder> page , @PathVariable("symbol") String symbol){
        Long userId = Long.valueOf(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString()) ;
        Page<TradeEntrustOrderVo> pageData = entrustOrderService.getHistoryEntrustOrder(page,symbol,userId) ;
        return R.ok(pageData) ;
    }
}
