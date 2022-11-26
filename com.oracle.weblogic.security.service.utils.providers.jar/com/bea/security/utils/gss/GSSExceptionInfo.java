package com.bea.security.utils.gss;

import com.bea.common.logger.spi.LoggerSpi;
import org.ietf.jgss.GSSException;

public class GSSExceptionInfo {
   private static final String RC4_WITH_HMAC = "Invalid argument (400) - Cannot find key of appropriate type to decrypt AP REP - RC4 with HMAC";

   private GSSExceptionInfo() {
   }

   public static void logInterpretedFailureInfo(LoggerSpi logger, GSSException exc) {
      if (logger != null && exc != null) {
         int major = exc.getMajor();
         int minor = exc.getMinor();
         if (logger.isDebugEnabled()) {
            logger.debug("GSSExceptionInfo:");
            logger.debug("   major: (" + major + ") : " + exc.getMajorString());
            logger.debug("   minor: (" + minor + ") : " + (minor == 0 ? "NO DETAILS" : exc.getMinorString()));
         }

         switch (major) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
            default:
         }
      }
   }
}
