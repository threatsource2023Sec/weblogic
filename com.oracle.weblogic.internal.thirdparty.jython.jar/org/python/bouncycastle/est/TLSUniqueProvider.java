package org.python.bouncycastle.est;

public interface TLSUniqueProvider {
   boolean isTLSUniqueAvailable();

   byte[] getTLSUnique();
}
