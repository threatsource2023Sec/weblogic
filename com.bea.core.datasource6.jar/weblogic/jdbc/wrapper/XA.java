package weblogic.jdbc.wrapper;

import javax.transaction.xa.XAException;
import javax.transaction.xa.Xid;

public class XA {
   public static String xidToString(Xid xid) {
      return Integer.toHexString(xid.getFormatId()) + "-" + byteArrayToString(xid.getGlobalTransactionId()) + "-" + byteArrayToString(xid.getBranchQualifier());
   }

   public static String flagsToString(int flags) {
      String rtn = "";
      if (flags == 0) {
         return "XAResource.TMNOFLAGS";
      } else if ((flags & 134217728) == 134217728) {
         return "XAResource.TMRESUME";
      } else if ((flags & 2097152) == 2097152) {
         return "XAResource.TMJOIN";
      } else if ((flags & 33554432) == 33554432) {
         return "XAResource.TMSUSPEND";
      } else if ((flags & 67108864) == 67108864) {
         return "XAResource.TMSUCCESS";
      } else if ((flags & 536870912) == 536870912) {
         return "XAResource.TMFAIL";
      } else if ((flags & 1073741824) == 1073741824) {
         return "XAResource.TMONEPHASE";
      } else {
         if ((flags & 16777216) == 16777216) {
            rtn = rtn + (rtn.equals("") ? "XAResource.TMSTARTRSCAN" : "|XAResource.TMSTARTRSCAN");
         }

         if ((flags & 8388608) == 8388608) {
            rtn = rtn + (rtn.equals("") ? "XAResource.TMENDRSCAN" : "|XAResource.TMENDRSCAN");
         }

         return rtn;
      }
   }

   public static String errToString(int errorCode) {
      switch (errorCode) {
         case -9:
            return "XAResource.XAER_OUTSIDE";
         case -8:
            return "XAResource.XAER_DUPID";
         case -7:
            return "XAResource.XAER_RMFAIL";
         case -6:
            return "XAResource.XAER_PROTO";
         case -5:
            return "XAResource.XAER_INVAL";
         case -4:
            return "XAResource.XAER_NOTA";
         case -3:
            return "XAResource.XAER_RMERR";
         case -2:
            return "XAResource.XAER_ASYNC";
         case 0:
            return "XAResource.XA_OK";
         case 3:
            return "XAResource.XA_RDONLY";
         case 4:
            return "XAResource.XA_RETRY";
         case 5:
            return "XAResource.XA_HEURMIX";
         case 6:
            return "XAResource.XA_HEURRB";
         case 7:
            return "XAResource.XA_HEURCOM";
         case 8:
            return "XAResource.XA_HEURHAZ";
         case 9:
            return "XAResource.XA_NOMIGRATE";
         case 100:
            return "XAResource.XA_RBROLLBACK";
         case 101:
            return "XAResource.XA_RBCOMMFAIL";
         case 102:
            return "XAResource.XA_RBDEADLOCK";
         case 103:
            return "XAResource.XA_RBINTEGRITY";
         case 104:
            return "XAResource.XA_RBEND";
         case 105:
            return "XAResource.XA_RBPROTO";
         case 106:
            return "XAResource.XA_RBTIMEOUT";
         case 107:
            return "XAResource.XA_RBTRANSIENT";
         default:
            return "" + errorCode;
      }
   }

   public static XAException createException(String msg, int errorCode) {
      XAException ex = new XAException(msg);
      ex.errorCode = errorCode;
      return ex;
   }

   public static XAException createException(Throwable t, String msg, int errorCode) {
      XAException ex = new XAException(msg);
      ex.errorCode = errorCode;
      ex.initCause(t);
      return ex;
   }

   private static String byteArrayToString(byte[] barray) {
      StringBuffer buf = new StringBuffer();

      for(int i = 0; i < barray.length; ++i) {
         buf.append(Integer.toHexString((barray[i] & 240) >>> 4));
         buf.append(Integer.toHexString(barray[i] & 15));
      }

      return buf.toString();
   }
}
