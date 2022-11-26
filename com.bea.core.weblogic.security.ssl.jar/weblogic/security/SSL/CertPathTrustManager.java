package weblogic.security.SSL;

import java.security.cert.X509Certificate;
import weblogic.management.configuration.TLSMBean;
import weblogic.protocol.ServerChannel;
import weblogic.security.utils.CertPathTrustManagerUtils;

public final class CertPathTrustManager implements TrustManager {
   private int certPathValStyle = 0;
   private final ServerChannel serverChannel;
   private TLSMBean tlsmBean = null;

   public CertPathTrustManager() {
      this.serverChannel = null;
   }

   public CertPathTrustManager(ServerChannel serverChannel) {
      this.serverChannel = serverChannel;
   }

   public CertPathTrustManager(TLSMBean tlsmBean) {
      this.tlsmBean = tlsmBean;
      this.serverChannel = null;
   }

   public void setBuiltinSSLValidationAndCertPathValidators() {
      this.certPathValStyle = 1;
   }

   public void setBuiltinSSLValidationOnly() {
      this.certPathValStyle = 2;
   }

   public void setUseConfiguredSSLValidation() {
      this.certPathValStyle = 0;
   }

   public boolean certificateCallback(X509Certificate[] chain, int validateErr) {
      if (this.tlsmBean != null) {
         return CertPathTrustManagerUtils.certificateCallback(this.certPathValStyle, chain, validateErr, this.tlsmBean);
      } else {
         return this.serverChannel != null ? CertPathTrustManagerUtils.certificateCallback(this.certPathValStyle, chain, validateErr, this.serverChannel) : CertPathTrustManagerUtils.certificateCallback(this.certPathValStyle, chain, validateErr, (String)null, (String)null);
      }
   }
}
