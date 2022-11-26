package weblogic.diagnostics.context;

import java.util.HashSet;
import java.util.logging.Level;
import weblogic.diagnostics.type.DiagnosticException;
import weblogic.kernel.KernelStatus;

public final class CorrelationHelper {
   private static DyeInfo[] dyeInfos = new DyeInfo[]{new DyeInfo("ADDR1", 1L), new DyeInfo("ADDR2", 2L), new DyeInfo("ADDR3", 4L), new DyeInfo("ADDR4", 8L), new DyeInfo("USER1", 16L), new DyeInfo("USER2", 32L), new DyeInfo("USER3", 64L), new DyeInfo("USER4", 128L), new DyeInfo("COOKIE1", 256L), new DyeInfo("COOKIE2", 512L), new DyeInfo("COOKIE3", 1024L), new DyeInfo("COOKIE4", 2048L), new DyeInfo("EXECQ1", 4096L), new DyeInfo("EXECQ2", 8192L), new DyeInfo("EXECQ3", 16384L), new DyeInfo("EXECQ4", 32768L), new DyeInfo("THREADGROUP1", 65536L), new DyeInfo("THREADGROUP2", 131072L), new DyeInfo("THREADGROUP3", 262144L), new DyeInfo("THREADGROUP4", 524288L), new DyeInfo("PROTOCOL_T3", 1048576L), new DyeInfo("PROTOCOL_HTTP", 2097152L), new DyeInfo("PROTOCOL_RMI", 4194304L), new DyeInfo("PROTOCOL_SOAP", 8388608L), new DyeInfo("PROTOCOL_IIOP", 16777216L), new DyeInfo("PROTOCOL_JRMP", 33554432L), new DyeInfo("PROTOCOL_SSL", 67108864L), new DyeInfo("CONNECTOR1", 134217728L), new DyeInfo("CONNECTOR2", 268435456L), new DyeInfo("CONNECTOR3", 536870912L), new DyeInfo("CONNECTOR4", 1073741824L), new DyeInfo("THROTTLE", 4294967296L), new DyeInfo("JFR_THROTTLE", 8589934592L), new DyeInfo("DYE_0", 72057594037927936L), new DyeInfo("DYE_1", 144115188075855872L), new DyeInfo("DYE_2", 288230376151711744L), new DyeInfo("DYE_3", 576460752303423488L), new DyeInfo("DYE_4", 1152921504606846976L), new DyeInfo("DYE_5", 2305843009213693952L), new DyeInfo("DYE_6", 4611686018427387904L), new DyeInfo("DYE_7", Long.MIN_VALUE)};

   public static String getContextId() {
      Correlation ctx = CorrelationFactory.findOrCreateCorrelation();
      return ctx != null ? ctx.getECID() : null;
   }

   public static void setContextId(String dcid, String rid) throws DiagnosticException {
      if (KernelStatus.isServer()) {
         throw new DiagnosticException("Operation not allowed on server");
      } else if (dcid == null) {
         throw new IllegalArgumentException("ID must not be null");
      } else {
         JavaSECorrelationImpl ctx = (JavaSECorrelationImpl)CorrelationFactory.findCorrelation();
         if (ctx != null) {
            throw new DiagnosticException("Existing DiagnosticContext can not be overridden");
         } else {
            ctx = new JavaSECorrelationImpl(dcid, rid);
            CorrelationFactory.setCorrelation(ctx);
         }
      }
   }

   public static void setApplicationDye(byte dye, boolean enable) throws InvalidDyeException {
      if (dye >= 56 && dye <= 63) {
         Correlation ctx = CorrelationFactory.findOrCreateCorrelation();
         if (ctx != null) {
            ctx.setDye(dye, enable);
         }

      } else {
         throw new InvalidDyeException("Invalid dye index " + dye);
      }
   }

   public static boolean isDyedWith(byte dye) throws InvalidDyeException {
      Correlation ctx = CorrelationFactory.findOrCreateCorrelation();
      return ctx != null ? ctx.isDyedWith(dye) : false;
   }

   /** @deprecated */
   @Deprecated
   public static String getPayload() {
      Correlation ctx = CorrelationFactory.findOrCreateCorrelation();
      return ctx != null ? ctx.getPayload() : null;
   }

   /** @deprecated */
   @Deprecated
   public static void setPayload(String payload) {
      Correlation ctx = CorrelationFactory.findOrCreateCorrelation();
      if (ctx != null) {
         ctx.setPayload(payload);
      }

   }

   public static long parseDyeMask(String mask) {
      if (mask == null) {
         return 0L;
      } else {
         String[] maskNames = mask.split(",");
         return parseDyeMask(maskNames);
      }
   }

   public static long parseDyeMask(String[] maskNames) {
      long maskValue = 0L;
      int size = maskNames != null ? maskNames.length : 0;

      for(int i = 0; i < size; ++i) {
         String mName = maskNames[i].trim();
         maskValue |= getDyeValue(mName);
      }

      return maskValue;
   }

   public static String[] getDyeFlagNames() {
      String[] dyeNames = new String[dyeInfos.length];

      for(int i = 0; i < dyeInfos.length; ++i) {
         dyeNames[i] = dyeInfos[i].dyeName;
      }

      return dyeNames;
   }

   public static void validateDyeFlagNames(String[] maskNames) {
      if (maskNames != null && maskNames.length != 0) {
         HashSet dyeNames = new HashSet();

         int j;
         for(j = 0; j < dyeInfos.length; ++j) {
            dyeNames.add(dyeInfos[j].dyeName);
         }

         for(j = 0; j < maskNames.length; ++j) {
            if (!dyeNames.contains(maskNames[j])) {
               throw new IllegalArgumentException("Dye name is invalid " + maskNames[j]);
            }
         }

      }
   }

   private static long getDyeValue(String dyeName) {
      for(int i = 0; i < dyeInfos.length; ++i) {
         if (dyeName.equals(dyeInfos[i].dyeName)) {
            return dyeInfos[i].dyeValue;
         }
      }

      return 0L;
   }

   public static synchronized void registerDye(String dyeName, int index) throws InvalidDyeException {
      if (index >= 0 && index <= 63) {
         long dyeValue = 1L << index;
         int size = dyeInfos.length;

         for(int i = 0; i < size; ++i) {
            DyeInfo dyeInfo = dyeInfos[i];
            if (dyeName.equals(dyeInfo.dyeName)) {
               throw new InvalidDyeException("Dye " + dyeName + " is already defined");
            }

            if (dyeInfo.dyeValue == dyeValue) {
               throw new InvalidDyeException("Dye " + dyeInfo.dyeName + " is already defined as dye " + index);
            }
         }

         DyeInfo[] tmp = new DyeInfo[size + 1];
         System.arraycopy(dyeInfos, 0, tmp, 0, size);
         tmp[size] = new DyeInfo(dyeName, dyeValue);
         dyeInfos = tmp;
      } else {
         throw new InvalidDyeException("Invalid dye index " + index);
      }
   }

   public static String getRID() {
      Correlation ctx = CorrelationFactory.findOrCreateCorrelation();
      return ctx != null ? ctx.getRID() : null;
   }

   public static Level getLogLevel() {
      Correlation ctx = CorrelationFactory.findOrCreateCorrelation();
      return ctx != null ? ctx.getLogLevel() : null;
   }

   public static void setLogLevel(Level level) {
      Correlation ctx = CorrelationFactory.findOrCreateCorrelation();
      if (ctx != null) {
         ctx.setLogLevel(level);
      }

   }

   public static long getDyeVector() {
      Correlation ctx = CorrelationFactory.findOrCreateCorrelation();
      return ctx != null ? ctx.getDyeVector() : 0L;
   }

   public static void handleLocalContextAsNonInheritable() {
      CorrelationFactory.handleLocalContextAsNonInheritable();
   }

   public static String getStringifiedVectorForDebug(long vector) {
      StringBuffer sb = new StringBuffer();
      sb.append("dyeVector[");
      if ((vector & 1L) != 0L) {
         sb.append("M_ADDR1 ");
      }

      if ((vector & 2L) != 0L) {
         sb.append("M_ADDR2 ");
      }

      if ((vector & 4L) != 0L) {
         sb.append("M_ADDR3 ");
      }

      if ((vector & 8L) != 0L) {
         sb.append("M_ADDR4 ");
      }

      if ((vector & 16L) != 0L) {
         sb.append("M_USER1 ");
      }

      if ((vector & 32L) != 0L) {
         sb.append("M_USER2 ");
      }

      if ((vector & 64L) != 0L) {
         sb.append("M_USER3 ");
      }

      if ((vector & 128L) != 0L) {
         sb.append("M_USER4 ");
      }

      if ((vector & 256L) != 0L) {
         sb.append("M_COOKIE1 ");
      }

      if ((vector & 512L) != 0L) {
         sb.append("M_COOKIE2 ");
      }

      if ((vector & 1024L) != 0L) {
         sb.append("M_COOKIE3 ");
      }

      if ((vector & 2048L) != 0L) {
         sb.append("M_COOKIE4 ");
      }

      if ((vector & 4096L) != 0L) {
         sb.append("M_EXECQ1 ");
      }

      if ((vector & 8192L) != 0L) {
         sb.append("M_EXECQ2 ");
      }

      if ((vector & 16384L) != 0L) {
         sb.append("M_EXECQ3 ");
      }

      if ((vector & 32768L) != 0L) {
         sb.append("M_EXECQ4 ");
      }

      if ((vector & 65536L) != 0L) {
         sb.append("M_THREADGROUP1 ");
      }

      if ((vector & 131072L) != 0L) {
         sb.append("M_THREADGROUP2 ");
      }

      if ((vector & 262144L) != 0L) {
         sb.append("M_THREADGROUP3 ");
      }

      if ((vector & 524288L) != 0L) {
         sb.append("M_THREADGROUP4 ");
      }

      if ((vector & 1048576L) != 0L) {
         sb.append("M_PROTOCOL_T3 ");
      }

      if ((vector & 2097152L) != 0L) {
         sb.append("M_PROTOCOL_HTTP ");
      }

      if ((vector & 4194304L) != 0L) {
         sb.append("M_PROTOCOL_RMI ");
      }

      if ((vector & 8388608L) != 0L) {
         sb.append("M_PROTOCOL_SOAP ");
      }

      if ((vector & 16777216L) != 0L) {
         sb.append("M_PROTOCOL_IIOP ");
      }

      if ((vector & 33554432L) != 0L) {
         sb.append("M_PROTOCOL_JRMP ");
      }

      if ((vector & 67108864L) != 0L) {
         sb.append("M_PROTOCOL_SSL ");
      }

      if ((vector & 134217728L) != 0L) {
         sb.append("M_CONNECTOR1 ");
      }

      if ((vector & 268435456L) != 0L) {
         sb.append("M_CONNECTOR2 ");
      }

      if ((vector & 536870912L) != 0L) {
         sb.append("M_CONNECTOR3 ");
      }

      if ((vector & 1073741824L) != 0L) {
         sb.append("M_CONNECTOR4 ");
      }

      if ((vector & 2147483648L) != 0L) {
         sb.append("M_EVENT_BEING_PERSISTED ");
      }

      if ((vector & 4294967296L) != 0L) {
         sb.append("M_THROTTLE ");
      }

      if ((vector & 8589934592L) != 0L) {
         sb.append("M_JFR_THROTTLE ");
      }

      if ((vector & 72057594037927936L) != 0L) {
         sb.append("M_DYE_0 ");
      }

      if ((vector & 144115188075855872L) != 0L) {
         sb.append("M_DYE_1 ");
      }

      if ((vector & 288230376151711744L) != 0L) {
         sb.append("M_DYE_2 ");
      }

      if ((vector & 576460752303423488L) != 0L) {
         sb.append("M_DYE_3 ");
      }

      if ((vector & 1152921504606846976L) != 0L) {
         sb.append("M_DYE_4 ");
      }

      if ((vector & 2305843009213693952L) != 0L) {
         sb.append("M_DYE_5 ");
      }

      if ((vector & 4611686018427387904L) != 0L) {
         sb.append("M_DYE_6 ");
      }

      if ((vector & Long.MIN_VALUE) != 0L) {
         sb.append("M_DYE_7 ");
      }

      sb.append("]");
      return sb.toString();
   }

   private static class DyeInfo {
      String dyeName;
      long dyeValue;

      DyeInfo(String dyeName, long dyeValue) {
         this.dyeName = dyeName;
         this.dyeValue = dyeValue;
      }
   }
}
