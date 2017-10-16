package com.betterjr.modules.ledger;

import static com.betterjr.common.web.ControllerExceptionHandler.exec;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;
import com.betterjr.common.mapper.JsonMapper;
import com.betterjr.common.web.Servlets;

/***
 * 贸易合同台账
 * @author hubl
 *
 */
@Controller
@RequestMapping(value = "/Scf/ContractLedger")
public class ContractLedgerController {

    private static final Logger logger = LoggerFactory.getLogger(ContractLedgerController.class);

    @Reference(interfaceClass = IContractLedgerService.class)
    public IContractLedgerService contractLedgerService;

    @RequestMapping(value = "/queryContractLedger", method = RequestMethod.POST)
    public @ResponseBody String queryContractLedger(HttpServletRequest request, String queryType, int pageNum,
            int pageSize) {
        Map<String, Object> map = Servlets.getParametersStartingWith(request, "");
        logger.info("分页查询贸易合同台账信息，入参:" + map.toString() + "，queryType=" + queryType);
        return exec(() -> contractLedgerService.webQueryContractLedgerByPage(map, queryType, pageNum, pageSize),
                "分页查询贸易合同台账", logger);
    }

    @RequestMapping(value = "/findContractLedgerCustInfoByCustNo", method = RequestMethod.POST)
    public @ResponseBody String findContractLedgerCustInfoByCustNo(Long custNo) {
        logger.info("查询合同台账客户信息，入参:" + custNo);
        return exec(() -> contractLedgerService.webFindCustInfoByCustNo(custNo), "查询合同台账客户信息", logger);
    }

    @RequestMapping(value = "/addContractLedger", method = RequestMethod.POST)
    public @ResponseBody String addContractLedger(String params) {
        logger.info("params:" + params);
        Map<String, Object> infoMap = Servlets.convertDate(JsonMapper.parserJson(params));
        String fileList = (String) infoMap.get("fileList");
        logger.info("添加合同台账，入参:" + infoMap + "，fileList:" + fileList);
        return exec(() -> contractLedgerService.webAddContractLedger(infoMap, fileList), "添加合同台账", logger);
    }

    @RequestMapping(value = "/findContractLedgerCustInfoByCustNoAndContractId", method = RequestMethod.POST)
    public @ResponseBody String findContractLedgerCustInfoByCustNoAndContractId(Long custNo, Long contractId) {
        logger.info(
                "findContractLedgerCustInfoByCustNoAndContractId，入参，custNo:" + custNo + "，contractid:" + contractId);
        return exec(() -> contractLedgerService.webFindCustInfoByCustNoAndContractId(custNo, contractId), "查询合同台账客户信息",
                logger);
    }

    @RequestMapping(value = "/findContractLedgerByContractId", method = RequestMethod.POST)
    public @ResponseBody String findContractLedgerByContractId(Long contractId) {
        logger.info("findContractLedgerByContractId，入参：contractid:" + contractId);
        return exec(() -> contractLedgerService.webFindContractLedgerByContractId(contractId), "查询合同台账信息", logger);
    }

    @RequestMapping(value = "/findContractLedgerCustFileItems", method = RequestMethod.POST)
    public @ResponseBody String findContractLedgerCustFileItems(Long contractId) {
        logger.info("findContractLedgerCustFileItems，入参：contractid:" + contractId);
        return exec(() -> contractLedgerService.webFindCustFileItems(contractId), "查询合同台账附件列表信息", logger);
    }

    @RequestMapping(value = "/saveContractLedger", method = RequestMethod.POST)
    public @ResponseBody String saveContractLedger(String params) {
        logger.info("saveContractLedger params:" + params);
        Map<String, Object> infoMap = Servlets.convertDate(JsonMapper.parserJson(params));
        String fileList = (String) infoMap.get("fileList");
        logger.info("修改合同台账，入参:" + infoMap + "，fileList:" + fileList);
        return exec(() -> contractLedgerService.webSaveContractLedger(infoMap, fileList), "修改合同台账", logger);
    }

    @RequestMapping(value = "/saveContractLedgerStatus", method = RequestMethod.POST)
    public @ResponseBody String saveContractLedgerStatus(Long contractId, String status) {
        logger.info("contractId:" + contractId + "，status：" + status);
        return exec(() -> contractLedgerService.webSaveContractLedgerStatus(contractId, status), "合同状态变更", logger);
    }

    @RequestMapping(value = "/findContractLedgerRecode", method = RequestMethod.POST)
    public @ResponseBody String findContractLedgerRecode(Long contractId) {
        logger.info("findContractLedgerRecode,contractId:" + contractId);
        return exec(() -> contractLedgerService.webFindContractLedgerRecode(contractId), "查询合同台账记录信息", logger);
    }

}
