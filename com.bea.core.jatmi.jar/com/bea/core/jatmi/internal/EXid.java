package com.bea.core.jatmi.internal;

import javax.transaction.xa.Xid;

public class EXid implements Xid {
   private Xid xid;
   private Xid foreignXid;

   public EXid(Xid xid, Xid foreignXid) {
      this.xid = xid;
      this.foreignXid = foreignXid;
   }

   public byte[] getBranchQualifier() {
      return this.xid.getBranchQualifier();
   }

   public int getFormatId() {
      return this.xid.getFormatId();
   }

   public byte[] getGlobalTransactionId() {
      return this.xid.getGlobalTransactionId();
   }

   public Xid getForeignXid() {
      return this.foreignXid;
   }

   public Xid getXid() {
      return this.xid;
   }

   public String toString() {
      return "EXid [xid=" + this.xid + ", foreignXid=" + this.foreignXid + "]";
   }
}
