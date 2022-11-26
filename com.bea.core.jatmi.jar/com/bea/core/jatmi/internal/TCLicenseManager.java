package com.bea.core.jatmi.internal;

import com.bea.core.jatmi.common.ntrace;
import com.bea.core.jatmi.intf.TCLicenseService;
import weblogic.wtc.jatmi.TPException;

public final class TCLicenseManager {
   private static TCLicenseService _svc = null;
   private static TCLicenseManager _mgr = null;
   public static final int TC_LIC_SHUTDOWN_NORMAL = 0;
   public static final int TC_LIC_SHUTDOWN_FORCE = 1;

   public static void initialize(TCLicenseService tcls) throws TPException {
      if (tcls == null) {
         throw new TPException(12, "null license service");
      } else if (_mgr != null) {
         throw new TPException(12, "Security Manager already configured!");
      } else {
         _svc = tcls;
         _mgr = new TCLicenseManager();
      }
   }

   public static TCLicenseManager getLicenseService() {
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      if (traceEnabled) {
         ntrace.doTrace("[/TCLicenseManager/getLicenseService()");
      }

      if (traceEnabled) {
         ntrace.doTrace("]/TCLicenseManager/getLicenseService/" + _mgr);
      }

      return _mgr;
   }

   public void shutdown(int type) {
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      if (traceEnabled) {
         ntrace.doTrace("[/TCLicenseManager/shutdown(" + type + ")");
      }

      if (_svc != null) {
         if (type < 0 || type > 1) {
            type = 1;
         }

         _svc.shutdown(type);
      }

      if (traceEnabled) {
         ntrace.doTrace("]/TCLicenseManager/shutdown/10");
      }

   }

   public static boolean isTCLicensed() {
      return _svc != null ? _svc.isTCLicensed() : false;
   }

   public static boolean updateLicenseInformation() {
      return _svc != null ? _svc.updateLicenseInformation() : false;
   }

   public static int updateInstalledEncryptionInfo() {
      return _svc != null ? _svc.updateInstalledEncryptionInfo() : 1;
   }

   public static int getInstalledEncryption() {
      return _svc != null ? _svc.getInstalledEncryption() : 1;
   }

   public static int decideEncryptionLevel(int release, int min, int max) {
      return _svc != null ? _svc.decideEncryptionLevel(release, min, max) : 1;
   }

   public static int acceptEncryptionLevel(int release, int min, int max, int flags) {
      return _svc != null ? _svc.acceptEncryptionLevel(release, min, max, flags) : 1;
   }
}
