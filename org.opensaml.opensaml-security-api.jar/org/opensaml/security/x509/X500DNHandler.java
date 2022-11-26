package org.opensaml.security.x509;

import javax.annotation.Nonnull;
import javax.security.auth.x500.X500Principal;

public interface X500DNHandler {
   String FORMAT_RFC1779 = "RFC1779";
   String FORMAT_RFC2253 = "RFC2253";

   @Nonnull
   X500Principal parse(@Nonnull String var1);

   @Nonnull
   X500Principal parse(@Nonnull byte[] var1);

   @Nonnull
   String getName(@Nonnull X500Principal var1);

   @Nonnull
   String getName(@Nonnull X500Principal var1, @Nonnull String var2);

   @Nonnull
   byte[] getEncoded(@Nonnull X500Principal var1);

   @Nonnull
   X500DNHandler clone();
}
