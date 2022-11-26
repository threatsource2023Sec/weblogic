package weblogic.security;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.cert.CertificateParsingException;
import java.security.cert.X509Certificate;
import java.util.Set;

public class CertificatePoliciesExtension {
   private static final String EXTN_ID = "2.5.29.32";
   private static final String className = "com.bea.sslplus.extensions.CertificatePoliciesImpl";
   private CertificatePolicy[] policies;
   private boolean critical;

   public CertificatePoliciesExtension(boolean critical, CertificatePolicy[] policies) {
      this.critical = critical;
      this.policies = policies;
   }

   public static CertificatePoliciesExtension getCertificatePoliciesExtension(X509Certificate cert) throws CertificateParsingException {
      CertificatePoliciesExtension certificatePoliciesExtn = null;
      boolean criticalFlag = false;
      Set critSet = cert.getCriticalExtensionOIDs();
      if (critSet != null && !critSet.isEmpty()) {
         criticalFlag = critSet.contains("2.5.29.32");
      }

      try {
         Class classDefinition = Class.forName("com.bea.sslplus.extensions.CertificatePoliciesImpl");
         Method method = classDefinition.getMethod("getPolicies", X509Certificate.class);
         CertificatePolicy[] policies = (CertificatePolicy[])((CertificatePolicy[])method.invoke((Object)null, cert));
         return new CertificatePoliciesExtension(criticalFlag, policies);
      } catch (InvocationTargetException var7) {
         CertificateParsingException cpe = new CertificateParsingException("Cannot Parse the Certificate Policies Extension");
         cpe.initCause(var7.getCause());
         throw cpe;
      } catch (Exception var8) {
         throw new RuntimeException("Unexpected exception", var8);
      }
   }

   public boolean isCritical() {
      return this.critical;
   }

   public CertificatePolicy[] getPolicies() {
      CertificatePolicy[] policiesCopy = new CertificatePolicy[this.policies.length];
      System.arraycopy(this.policies, 0, policiesCopy, 0, this.policies.length);
      return policiesCopy;
   }
}
