package weblogic.transaction.internal;

import java.util.Locale;
import javax.transaction.xa.XAException;
import javax.transaction.xa.Xid;

public class XAResourceHelper extends XAException {
   static final long serialVersionUID = -4564404288722853600L;
   private static final char[] DIGITS = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

   static String xidToString(Xid xid) {
      return xidToString(xid, true);
   }

   static String xidToString(Xid xid, boolean includeBranchQualifier) {
      if (xid == null) {
         return "";
      } else {
         StringBuffer sb = (new StringBuffer()).append(Integer.toHexString(xid.getFormatId()).toUpperCase(Locale.ENGLISH)).append("-").append(byteArrayToString(xid.getGlobalTransactionId()));
         if (includeBranchQualifier) {
            String bqual = byteArrayToString(xid.getBranchQualifier());
            if (!bqual.equals("")) {
               sb.append("-").append(byteArrayToString(xid.getBranchQualifier()));
            }
         }

         return sb.toString();
      }
   }

   static String byteArrayToString(byte[] barray) {
      if (barray == null) {
         return "";
      } else {
         char[] res = new char[barray.length * 2];
         int j = 0;

         for(int i = 0; i < barray.length; ++i) {
            res[j++] = DIGITS[(barray[i] & 240) >>> 4];
            res[j++] = DIGITS[barray[i] & 15];
         }

         return new String(res);
      }
   }

   protected static String flagsToString(int flags) {
      switch (flags) {
         case 0:
            return "TMNOFLAGS";
         case 1001001:
            return "TMCLUSTERSCAN";
         case 1001002:
            return "TMCLUSTERSCANPASSTHROUGH";
         case 1001003:
            return "DETERMINERXIDSCAN";
         case 2097152:
            return "TMJOIN";
         case 33554432:
            return "TMSUSPEND";
         case 67108864:
            return "TMSUCCESS";
         case 134217728:
            return "TMRESUME";
         case 536870912:
            return "TMFAIL";
         case 1073741824:
            return "TMONEPHASE";
         default:
            return Integer.toHexString(flags).toUpperCase(Locale.ENGLISH);
      }
   }

   static String xaErrorCodeToString(int err) {
      return xaErrorCodeToString(err, true);
   }

   static String xaErrorCodeToString(int err, boolean detail) {
      StringBuffer msg = new StringBuffer(10);
      switch (err) {
         case -9:
            msg.append("XAER_OUTSIDE");
            if (detail) {
               msg.append(" : The resource manager is doing work outside global transaction");
            }

            return msg.toString();
         case -8:
            msg.append("XAER_DUPID");
            if (detail) {
               msg.append(" : The XID already exists");
            }

            return msg.toString();
         case -7:
            msg.append("XAER_RMFAIL");
            if (detail) {
               msg.append(" : Resource manager is unavailable");
            }

            return msg.toString();
         case -6:
            msg.append("XAER_PROTO");
            if (detail) {
               msg.append(" : Routine was invoked in an improper context");
            }

            return msg.toString();
         case -5:
            msg.append("XAER_INVAL");
            if (detail) {
               msg.append(" : Invalid arguments were given");
            }

            return msg.toString();
         case -4:
            msg.append("XAER_NOTA");
            if (detail) {
               msg.append(" : The XID is not valid");
            }

            return msg.toString();
         case -3:
            msg.append("XAER_RMERR");
            if (detail) {
               msg.append(" : A resource manager error has occurred in the transaction branch");
            }

            return msg.toString();
         case -2:
            msg.append("XAER_ASYNC");
            if (detail) {
               msg.append(" : Asynchronous operation already outstanding");
            }

            return msg.toString();
         case 0:
            return "XA_OK";
         case 3:
            return "XA_RDONLY";
         case 5:
            msg.append("XA_HEURMIX");
            if (detail) {
               msg.append(" : The transaction branch has been heuristically committed and rolled back");
            }

            return msg.toString();
         case 6:
            msg.append("XA_HEURRB");
            if (detail) {
               msg.append(" : The transaction branch has been heuristically rolled back");
            }

            return msg.toString();
         case 7:
            msg.append("XA_HEURCOM");
            if (detail) {
               msg.append(" : The transaction branch has been heuristically committed");
            }

            return msg.toString();
         case 8:
            msg.append("XA_HEURHAZ");
            if (detail) {
               msg.append(" : The transaction branch may have been heuristically completed");
            }

            return msg.toString();
         case 100:
            msg.append("XA_RBROLLBACK");
            if (detail) {
               msg.append(" : Rollback was caused by unspecified reason");
            }

            return msg.toString();
         case 101:
            msg.append("XA_RBCOMMFAIL");
            if (detail) {
               msg.append(" : Rollback was caused by communication failure");
            }

            return msg.toString();
         case 102:
            msg.append("XA_RBDEADLOCK");
            if (detail) {
               msg.append(" : A deadlock was detected");
            }

            return msg.toString();
         case 103:
            msg.append("XA_RBINTEGRITY");
            if (detail) {
               msg.append(" : A condition that violates the integrity of the resource was detected");
            }

            return msg.toString();
         case 104:
            msg.append("XA_RBOTHER");
            if (detail) {
               msg.append(" : The resource manager rolled back the transaction branch for a reason not on this list");
            }

            return msg.toString();
         case 105:
            msg.append("XA_RBPROTO");
            if (detail) {
               msg.append(" : A protocol error occured in the resource manager");
            }

            return msg.toString();
         case 106:
            msg.append("XA_RBTIMEOUT");
            if (detail) {
               msg.append(" : A transaction branch took too long");
            }

            return msg.toString();
         case 107:
            msg.append("XA_RBTRANSIENT");
            if (detail) {
               msg.append(" : May retry the transaction branch");
            }

            return msg.toString();
         default:
            return Integer.toHexString(err).toUpperCase(Locale.ENGLISH);
      }
   }

   protected static void throwXAException(int errCode, String errMsg) throws XAException {
      XAException ex = new weblogic.transaction.XAException(errCode, xaErrorCodeToString(errCode) + ".  " + errMsg, (Throwable)null);
      throw ex;
   }

   protected static void throwXAException(int errCode, String errMsg, Throwable t) throws XAException {
      weblogic.transaction.XAException ex = new weblogic.transaction.XAException(errCode, xaErrorCodeToString(errCode) + ".  " + errMsg, t);
      throw ex;
   }

   static boolean isHeuristicErrorCode(int err) {
      return err == 7 || err == 8 || err == 5 || err == 6;
   }

   protected static boolean isRollbackErrorCode(int err) {
      return err >= 100 && err <= 107;
   }
}
