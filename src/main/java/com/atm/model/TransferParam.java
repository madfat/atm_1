package com.atm.model;

public class TransferParam {
    private String reference;
    private String dstAccountNo;
    private Double trxAmount;

    public TransferParam() {
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getDstAccountNo() {
        return dstAccountNo;
    }

    public void setDstAccountNo(String dstAccountNo) {
        this.dstAccountNo = dstAccountNo;
    }

    public Double getTrxAmount() {
        return trxAmount;
    }

    public void setTrxAmount(Double trxAmount) {
        this.trxAmount = trxAmount;
    }

    @Override
    public String toString() {
        return "TransferParam{" +
                "reference='" + reference + '\'' +
                ", dstAccountNo='" + dstAccountNo + '\'' +
                ", trxAmount=" + trxAmount +
                '}';
    }
}
