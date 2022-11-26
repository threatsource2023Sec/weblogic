package weblogic.security.pki.revocation.common;

import java.io.InputStream;
import java.util.Date;
import javax.security.auth.x500.X500Principal;

interface CrlCacheAccessor {
   boolean isCrlCacheUpdatable();

   boolean loadCrl(InputStream var1) throws Exception;

   void deleteCrl(X500Principal var1, Date var2) throws Exception;
}
