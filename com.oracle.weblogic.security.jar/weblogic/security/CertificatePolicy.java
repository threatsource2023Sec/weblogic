package weblogic.security;

public interface CertificatePolicy {
   String getID();

   CertificatePolicyQualifier[] getPolicyQualifiers();
}
