package weblogic.security.service.internal;

import weblogic.security.spi.DigestNotAvailableException;

public interface WSPasswordDigestService {
   byte[] getPasswordDigest(String var1, byte[] var2, String var3) throws DigestNotAvailableException;

   byte[] getDerivedKey(String var1, byte[] var2, int var3) throws DigestNotAvailableException;
}
