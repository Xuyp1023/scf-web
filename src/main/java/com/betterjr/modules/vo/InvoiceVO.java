package com.betterjr.modules.vo;

import java.util.List;

import com.betterjr.modules.order.entity.ScfInvoiceDOItem;

public class InvoiceVO {

    private List<ScfInvoiceDOItem> invoiceItemList;

    public List<ScfInvoiceDOItem> getInvoiceItemList() {
        return this.invoiceItemList;
    }

    public void setInvoiceItemList(List<ScfInvoiceDOItem> anInvoiceItemList) {
        this.invoiceItemList = anInvoiceItemList;
    }

}
