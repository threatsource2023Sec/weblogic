package weblogic.security.service.internal;

import weblogic.security.spi.WSPasswordDigest;

public interface WSPasswordDigestProvider {
   WSPasswordDigest getWSPasswordDigest();
}
