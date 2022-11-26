package com.bea.core.jatmi.internal;

import java.io.Serializable;
import javax.transaction.xa.Xid;
import weblogic.wtc.jatmi.TPException;
import weblogic.wtc.jatmi.TdomTranTcb;

public final class TuxedoXid implements Xid, Serializable {
   private static final long serialVersionUID = -5968813631234009188L;
   private byte[] myGlobalId;
   private byte[] myBranchQualifier;
   private Xid importedXid;
   private int myHashCode;
   private static final char[] DIGITS = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
   public static final int tuxedoXidFormatID = 40;

   private void calculateHash(String transactionParent) {
      int code = 0;
      int words = 8;

      for(int lcv = 0; lcv < words; ++lcv) {
         int tmpcode = this.myGlobalId[lcv * 4 + 3] << 24 & this.myGlobalId[lcv * 4 + 2] << 16 & this.myGlobalId[lcv * 4 + 1] << 8 & this.myGlobalId[lcv * 4];
         code ^= tmpcode;
      }

      if (transactionParent != null) {
         code ^= transactionParent.hashCode();
      }

      this.myHashCode = code;
   }

   public TuxedoXid(TdomTranTcb domainId) throws TPException {
      if (domainId == null) {
         throw new TPException(4, "Invalid null domain transaction");
      } else {
         byte[] fromDomain;
         if ((fromDomain = domainId.getGlobalTransactionId()) == null) {
            throw new TPException(4, "Invalid null transaction identifier");
         } else if (fromDomain.length != 32) {
            throw new TPException(4, "Invalid transaction format");
         } else {
            this.myGlobalId = new byte[32];

            for(int lcv = 0; lcv < 32; ++lcv) {
               this.myGlobalId[lcv] = fromDomain[lcv];
            }

            String transactionParent;
            if ((transactionParent = domainId.getNwtranidparent()) != null) {
               this.myBranchQualifier = transactionParent.getBytes();
            }

            this.importedXid = null;
            this.calculateHash(transactionParent);
         }
      }
   }

   public TuxedoXid(byte[] globalPortion, byte[] branchPortion) throws TPException {
      String transactionParent = null;
      if (globalPortion != null && globalPortion.length == 32) {
         this.myGlobalId = new byte[32];

         int lcv;
         for(lcv = 0; lcv < 32; ++lcv) {
            this.myGlobalId[lcv] = globalPortion[lcv];
         }

         if (branchPortion != null) {
            this.myBranchQualifier = new byte[branchPortion.length];

            for(lcv = 0; lcv < branchPortion.length; ++lcv) {
               this.myBranchQualifier[lcv] = branchPortion[lcv];
            }

            transactionParent = new String(this.myBranchQualifier);
         }

         this.importedXid = null;
         this.calculateHash(transactionParent);
      } else {
         throw new TPException(4, "Invalid transaction format");
      }
   }

   public int getFormatId() {
      return TCTransactionHelper.getXidFormatId();
   }

   public byte[] getGlobalTransactionId() {
      return this.myGlobalId;
   }

   public byte[] getBranchQualifier() {
      return this.myBranchQualifier;
   }

   public boolean equals(Object compareTo) {
      if (!(compareTo instanceof TuxedoXid)) {
         return false;
      } else {
         TuxedoXid myCompareTo = (TuxedoXid)compareTo;
         byte[] globalId;
         if ((globalId = myCompareTo.getGlobalTransactionId()) == null) {
            return false;
         } else if (globalId.length != 32) {
            return false;
         } else {
            int lcv;
            for(lcv = 0; lcv < 32; ++lcv) {
               if (globalId[lcv] != this.myGlobalId[lcv]) {
                  return false;
               }
            }

            byte[] branchId = myCompareTo.getBranchQualifier();
            if (branchId == null && this.myBranchQualifier == null) {
               return true;
            } else if ((branchId != null || this.myBranchQualifier == null) && (branchId == null || this.myBranchQualifier != null)) {
               if (branchId.length != this.myBranchQualifier.length) {
                  return false;
               } else {
                  for(lcv = 0; lcv < branchId.length; ++lcv) {
                     if (branchId[lcv] != this.myBranchQualifier[lcv]) {
                        return false;
                     }
                  }

                  return true;
               }
            } else {
               return false;
            }
         }
      }
   }

   public int hashCode() {
      return this.myHashCode;
   }

   public String toString() {
      StringBuffer sb = new StringBuffer(80);
      sb.append("bea2:");

      int i;
      int b;
      for(i = 0; i < this.myGlobalId.length; ++i) {
         b = this.myGlobalId[i] & 255;
         sb.append(DIGITS[b >> 4]);
         sb.append(DIGITS[b & 15]);
      }

      sb.append(":");

      for(i = 0; i < this.myBranchQualifier.length; ++i) {
         b = this.myBranchQualifier[i] & 255;
         sb.append(DIGITS[b >> 4]);
         sb.append(DIGITS[b & 15]);
      }

      return this.importedXid == null ? sb.toString() : sb.toString() + ",importedXid=" + this.importedXid;
   }

   public Xid getImportedXid() {
      return this.importedXid;
   }

   public void setImportedXid(Xid importedXid) {
      this.importedXid = importedXid;
   }
}
