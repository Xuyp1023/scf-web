package com.betterjr.modules.push.service;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.betterjr.common.service.BaseService;
import com.betterjr.modules.customer.data.CustRelationData;
import com.betterjr.modules.enquiry.entity.ScfEnquiry;
import com.betterjr.modules.enquiry.service.ScfEnquiryService;
import com.betterjr.modules.push.dao.ScfSupplierPushMapper;
import com.betterjr.modules.push.entity.ScfSupplierPush;
import com.betterjr.modules.push.entity.ScfSupplierPushDetail;

@Service
public class ScfSupplierPushService extends BaseService<ScfSupplierPushMapper, ScfSupplierPush> {

    private static final Logger logger = LoggerFactory.getLogger(ScfSupplierPushService.class);
    
    @Autowired
    private ScfSupplierPushDetailService supplierPushDetailService;
    @Autowired
    private ScfPushCheckService pushCheckService;
    @Autowired
    private ScfEnquiryService scfEnquiryService;
    
    public boolean pushSupplierInfo(Map<String, Object> anMap){
        boolean bool=false;
        try {
            Long coreCustNo=Long.parseLong(anMap.get("coreCustNo").toString());
            Long supplierCustNo=Long.parseLong(anMap.get("supplierNo").toString());
            ScfSupplierPushDetail supplierPushDetail=new ScfSupplierPushDetail();
            supplierPushDetail.initDefValue(anMap);
            String factors="";
            int i=0;
            // 添加发送记录
            for(CustRelationData custRelationData:pushCheckService.pushData(coreCustNo,supplierCustNo,supplierPushDetail)){
                if(i==0){
                    factors=custRelationData.getRelateCustno().toString();
                }else{
                    factors=","+custRelationData.getRelateCustno().toString();
                }
                supplierPushDetail.setAgencyNo(custRelationData.getRelateCustno().toString());
                if(supplierPushDetailService.addPushDetail(supplierPushDetail)){
                    ScfSupplierPush supplierPush=new ScfSupplierPush();
                    supplierPush.initDefValue(coreCustNo,supplierCustNo,supplierPushDetail.getId());
                    this.insert(supplierPush);
                    bool=true;
                }
                i++;
            }
            if(bool){
                // 推送成功后调询价接口
                ScfEnquiry scfEnquiry=new ScfEnquiry();
                scfEnquiry.setCustNo(coreCustNo);
                scfEnquiry.setFactors(factors);
                scfEnquiry.setOrders(supplierPushDetail.getOrderId().toString());
                scfEnquiry.setRequestType("2");
                scfEnquiry.setEnquiryMethod("1");
                scfEnquiryService.addEnquiry(scfEnquiry);
            }
        }
        catch (Exception e) {
            logger.error("异常："+e.getMessage());
            bool=false;
        }
        return bool;
    }
    
    /***
     * 修改放弃原因
     * @param anPushId 推送信息的编号
     * @param anRemarkId 放弃原因的列表 ID
     * @param anRemark   放弃
     * @return
     */
    public boolean saveSupplierInfo(Long anPushId,String anRemarkId,String anRemark){
        logger.info("入参："+anPushId+"，"+anRemarkId+"，"+anRemark);
        boolean bool=false;
        try {
            ScfSupplierPush supplierPush=this.selectByPrimaryKey(anPushId);
            supplierPush.setRemark(anRemark);
            supplierPush.setRemarkId(anRemarkId);
            return this.updateByPrimaryKey(supplierPush)>0;
        }
        catch (Exception e) {
            logger.error("异常："+e.getMessage());
            bool=false;
        }
        return bool;
    }
    
}
