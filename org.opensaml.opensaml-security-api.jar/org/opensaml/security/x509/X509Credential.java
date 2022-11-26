package org.opensaml.security.x509;

import java.security.cert.X509Certificate;
import java.util.Collection;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.opensaml.security.credential.Credential;

public interface X509Credential extends Credential {
   @Nonnull
   X509Certificate getEntityCertificate();

   @Nonnull
   Collection getEntityCertificateChain();

   @Nullable
   Collection getCRLs();
}
