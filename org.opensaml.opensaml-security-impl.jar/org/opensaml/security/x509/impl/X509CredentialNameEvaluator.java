package org.opensaml.security.x509.impl;

import java.util.Set;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.opensaml.security.SecurityException;
import org.opensaml.security.x509.X509Credential;

public interface X509CredentialNameEvaluator {
   boolean evaluate(@Nonnull X509Credential var1, @Nullable Set var2) throws SecurityException;
}
