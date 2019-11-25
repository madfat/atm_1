package com.atm.model;

public class TransferParam {
    private String reference;
    private String srcAccountNo;
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

    public String getSrcAccountNo() {
        return srcAccountNo;
    }

    public void setSrcAccountNo(String srcAccountNo) {
        this.srcAccountNo = srcAccountNo;
    }

    @Override
    public String toString() {
        return "TransferParam{" +
                "reference='" + reference + '\'' +
                ", srcAccountNo='" + srcAccountNo + '\'' +
                ", dstAccountNo='" + dstAccountNo + '\'' +
                ", trxAmount=" + trxAmount +
                '}';
    }
}
