package weblogic.security;

public interface CertificatePolicyQualifier {
   String getID();

   byte[] getQualifier();
}
