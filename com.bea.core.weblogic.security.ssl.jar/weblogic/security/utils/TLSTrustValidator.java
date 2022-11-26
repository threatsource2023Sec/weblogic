package weblogic.security.utils;

import weblogic.kernel.Kernel;
import weblogic.management.configuration.TLSMBean;
import weblogic.security.SSL.CertPathTrustManager;

public class TLSTrustValidator extends SSLTrustValidator {
   public TLSTrustValidator(TLSMBean tlsmBean) {
      if (Kernel.isServer()) {
         this.setTrustManager(new CertPathTrustManager(tlsmBean));
      }

   }
}
