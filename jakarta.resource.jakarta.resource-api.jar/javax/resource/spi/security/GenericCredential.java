package javax.resource.spi.security;

import javax.resource.spi.SecurityException;

/** @deprecated */
public interface GenericCredential {
   String getName();

   String getMechType();

   byte[] getCredentialData() throws SecurityException;

   boolean equals(Object var1);

   int hashCode();
}
