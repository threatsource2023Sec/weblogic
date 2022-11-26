package weblogic.security.pk;

import com.bea.common.security.SecurityLogger;
import java.security.cert.CertPath;
import java.security.cert.CertPathBuilderResult;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import weblogic.security.service.SecurityServiceRuntimeException;

public class WLSCertPathBuilderResult implements CertPathBuilderResult {
   private CertPath certPath;

   public WLSCertPathBuilderResult(CertPath certPath) {
      this.certPath = certPath;
      if (this.certPath == null || this.certPath.getCertificates() == null || this.certPath.getCertificates().size() < 1) {
         throw new IllegalArgumentException(SecurityLogger.getWLSCertPathBuilderResultIllegalCertPath());
      }
   }

   public WLSCertPathBuilderResult(X509Certificate[] chain) {
      if (chain != null && chain.length >= 1) {
         try {
            ArrayList list = new ArrayList(chain.length);

            for(int i = 0; i < chain.length; ++i) {
               list.add(chain[i]);
            }

            this.certPath = CertificateFactory.getInstance("X509").generateCertPath(list);
         } catch (CertificateException var4) {
            throw new SecurityServiceRuntimeException(SecurityLogger.getX509CreateCertPathError(var4));
         }
      } else {
         throw new IllegalArgumentException(SecurityLogger.getWLSCertPathBuilderResultIllegalCertPath());
      }
   }

   public CertPath getCertPath() {
      return this.certPath;
   }

   public Object clone() {
      throw new UnsupportedOperationException();
   }
}
