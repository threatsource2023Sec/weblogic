package weblogic.security.providers.authentication;

import java.security.cert.X509Certificate;

public interface UserNameMapper {
   String mapCertificateToUserName(X509Certificate[] var1, boolean var2);

   String mapDistinguishedNameToUserName(byte[] var1);
}
