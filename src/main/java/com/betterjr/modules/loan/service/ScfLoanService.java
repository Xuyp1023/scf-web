package com.betterjr.modules.loan.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.betterjr.common.service.BaseService;
import com.betterjr.common.utils.BTAssert;
import com.betterjr.common.utils.Collections3;
import com.betterjr.mapper.pagehelper.Page;
import com.betterjr.modules.account.service.CustAccountService;
import com.betterjr.modules.loan.dao.ScfLoanMapper;
import com.betterjr.modules.loan.entity.ScfLoan;

@Service
public class ScfLoanService extends BaseService<ScfLoanMapper, ScfLoan> {

    @Autowired
    private CustAccountService custAccountService;
    
    /**
     * 新增放款记录
     * @param anLoan
     * @return
     */
    public ScfLoan addLoan(ScfLoan anLoan) {
        BTAssert.notNull(anLoan, "anLoan不能为空");
        anLoan.init();
        this.insert(anLoan);
        return findLoanDetail(anLoan.getId());
    }
    
    /**
     * 修改放款记录
     * 
     * @param anLoan
     * @return
     */
    public ScfLoan saveModifyLoan(ScfLoan anLoan, Long anId) {
        BTAssert.notNull(anLoan, "anLoan不能为空");

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("factorNo", anLoan.getFactorNo());
        map.put("id", anId);
        if(Collections3.isEmpty(selectByClassProperty(ScfLoan.class, map))){
            throw new IllegalArgumentException("找不到原数据");
        }

        anLoan.initModify();
        anLoan.setId(anId);
        this.updateByPrimaryKeySelective(anLoan);
        return findLoanDetail(anId);
    }

    /**
     * 查询放款记录列表
     * 
     * @param anMap
     * @param anFlag
     * @param anPageNum
     * @param anPageSize
     * @return
     */
    public Page<ScfLoan> queryLoanList(Map<String, Object> anMap, int anFlag, int anPageNum, int anPageSize) {
        Page<ScfLoan> page = this.selectPropertyByPage(anMap, anPageNum, anPageSize, 1 == anFlag);
        for (ScfLoan loan : page) {
            loan.setCustName(custAccountService.queryCustName(loan.getCustNo()));
            loan.setFactorName(custAccountService.queryCustName(loan.getFactorNo()));
        }
        return page;
    }

    /**
     * 查询放款记录详情
     * 
     * @param anId
     * @return
     */
    public ScfLoan findLoanDetail(Long anId) {
        BTAssert.notNull(anId, "anId不能为空");
        
        ScfLoan loan = this.selectByPrimaryKey(anId);
        if(null == loan){
            return new ScfLoan();
        }
        
        loan.setCustName(custAccountService.queryCustName(loan.getCustNo()));
        loan.setFactorName(custAccountService.queryCustName(loan.getFactorNo()));
        return loan;
    }
    

}
