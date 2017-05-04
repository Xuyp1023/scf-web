package com.betterjr.modules.commission.dao;

import java.util.Map;

import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;

import com.betterjr.common.annotation.BetterjrMapper;
import com.betterjr.mapper.common.Mapper;
import com.betterjr.modules.commission.entity.CommissionDailyStatement;

@BetterjrMapper
public interface CommissionDailyStatementMapper extends Mapper<CommissionDailyStatement> {
    
    
    /***
     * 根据不同状态统计查询记录数
     * @param anParam
     * @return
     */    
    @Select("select t1.totalCount as allTotalCount,t1.totalBalance as allTotalBalance,t2.totalCount as successTotalCount,t2.totalBalance as successTotalBalance,t3.totalCount as failureTotalCount,t3.totalBalance as failureTotalBalance from "
            + "("
            + "(select count(ID) as totalCount,sum(F_TOTAL_BALANCE) as totalBalance from t_cps_daily_statement"
            + "where D_PAY_DATE>=#{startDate} and D_PAY_DATE<=#{endDate} and L_OWN_CUSTNO=#{ownCustNo} and C_BUSIN_STATUS in ('0','1','2')"
            + ") as t1,"
            + "("
            + "select count(ID) as totalCount,sum(F_TOTAL_BALANCE) as totalBalance from t_cps_daily_statement"
            + "where D_PAY_DATE>=#{startDate} and D_PAY_DATE<=#{endDate} and L_OWN_CUSTNO=#{ownCustNo} and C_BUSIN_STATUS in ('2')"
            + ") as t2,"
            + "("
            + "select count(ID) as totalCount,sum(F_TOTAL_BALANCE) as totalBalance from t_cps_daily_statement"
            + "where D_PAY_DATE>=#{startDate} and D_PAY_DATE<=#{endDate} and L_OWN_CUSTNO=#{ownCustNo} and C_BUSIN_STATUS in ('0','1')"
            + ") as t3"
            + ")")
    @ResultType(Map.class)
    public Map selectDailyStatementCount(Map<String,Object> anParam);
    
//    /***
//     * 统计查询总金额 
//     * @param anParam
//     * @return
//     */
//    @Select("select SUM(F_TOTAL_BALANCE) from t_cps_daily_statement where D_PAY_DATE>=#{startDate} and D_PAY_DATE<=#{endDate} and L_OWN_CUSTNO=#{ownCustNo} and C_BUSIN_STATUS in (#{businStatus});")
//    @ResultType(Long.class)
//    public Long selectDailyStatementTotalBalance(Map<String,Object> anParam);
    
}