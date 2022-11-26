package weblogic.wtc.wls;

import com.bea.core.jatmi.common.ntrace;
import com.bea.core.jatmi.intf.TCLicenseService;

public final class WlsLicenseService implements TCLicenseService {
   private static int myEncryptionLevel = 1;

   public WlsLicenseService() {
      this.updateInstalledEncryptionInfo();
   }

   public void shutdown(int type) {
   }

   public boolean isTCLicensed() {
      return true;
   }

   public boolean updateLicenseInformation() {
      return true;
   }

   public int updateInstalledEncryptionInfo() {
      boolean traceEnabled = ntrace.getTraceLevel() == 1000373;
      if (traceEnabled) {
         ntrace.doTrace("[/WlsLicenseService/updateInstalledEncryptionInfo/");
      }

      if (traceEnabled) {
         ntrace.doTrace("]/WlsLicenseService/updateInstalledEncryptionInfo/10/GPE128");
      }

      myEncryptionLevel = 4;
      return 4;
   }

   public int getInstalledEncryption() {
      boolean traceEnabled = ntrace.getTraceLevel() == 1000373;
      if (traceEnabled) {
         ntrace.doTrace("[/WlsLicenseService/getInstalledEncryption/");
         ntrace.doTrace("]/WlsLicenseService/getInstalledEncryption/10/" + myEncryptionLevel);
      }

      return myEncryptionLevel;
   }

   public int decideEncryptionLevel(int dom_release, int min, int max) {
      boolean traceEnabled = ntrace.getTraceLevel() == 1000373;
      if (traceEnabled) {
         ntrace.doTrace("[/WlsLicenseService/decideEncryptionLevel(" + min + ", " + max + ")/");
      }

      switch (myEncryptionLevel) {
         case 1:
            if (max > 0) {
               max = 0;
            }
         case 4:
         default:
            break;
         case 32:
            if (max > 56) {
               max = 56;
            }
      }

      if (min > max) {
         if (traceEnabled) {
            ntrace.doTrace("]/WlsLicenseService/decideEncryptionLevel/10/-1");
         }

         return -1;
      } else {
         int flags = 1;
         if (max >= 128) {
            flags |= 38;
         } else if (max >= 56) {
            flags |= 34;
         } else if (max >= 40) {
            flags |= 2;
         }

         if (min > 56) {
            flags &= -36;
         } else if (min > 40) {
            flags &= -4;
         } else if (min > 0) {
            flags &= -2;
         }

         if (traceEnabled) {
            ntrace.doTrace("]/WlsLicenseService/decideEncryptionLevel/20/" + flags);
         }

         return flags;
      }
   }

   public int acceptEncryptionLevel(int dom_release, int min, int max, int remote_flags) {
      boolean traceEnabled = ntrace.getTraceLevel() == 1000373;
      if (traceEnabled) {
         ntrace.doTrace("[/WlsLicenseService/acceptEncryptionLevel/");
      }

      switch (myEncryptionLevel) {
         case 1:
            if (max > 0) {
               max = 0;
            }
         case 4:
         default:
            break;
         case 32:
            if (max > 56) {
               max = 56;
            }
      }

      if (min > max) {
         if (traceEnabled) {
            ntrace.doTrace("]/WlsLicenseService/acceptEncryptionLevel/10/-1");
         }

         return -1;
      } else {
         int flags = 1;
         if (max >= 128) {
            flags |= 38;
         } else if (max >= 56) {
            flags |= 34;
         } else if (max >= 40) {
            flags |= 2;
         }

         if (min > 56) {
            flags &= -36;
         } else if (min > 40) {
            flags &= -4;
         } else if (min > 0) {
            flags &= -2;
         }

         int negflags = dom_release > 11 ? remote_flags : 1;
         negflags &= flags;
         if ((negflags & 4) != 0) {
            negflags = 4;
         } else if ((negflags & 32) != 0) {
            negflags = 32;
         } else if ((negflags & 2) != 0) {
            negflags = 2;
         }

         if (traceEnabled) {
            ntrace.doTrace("]/WlsLicenseService/acceptEncryptionLevel/20/" + negflags);
         }

         return negflags;
      }
   }
}
