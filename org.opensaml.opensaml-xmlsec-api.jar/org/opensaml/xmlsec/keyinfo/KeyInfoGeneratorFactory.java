package org.opensaml.xmlsec.keyinfo;

import javax.annotation.Nonnull;
import org.opensaml.security.credential.Credential;

public interface KeyInfoGeneratorFactory {
   @Nonnull
   KeyInfoGenerator newInstance();

   boolean handles(@Nonnull Credential var1);

   @Nonnull
   Class getCredentialType();
}
