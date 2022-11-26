package weblogic.security;

import java.io.IOException;
import java.io.InputStream;

/** @deprecated */
@Deprecated
public final class PEMInputStream extends weblogic.security.SSL.jsseadapter.PEMInputStream {
   public PEMInputStream(InputStream in) throws IOException {
      super(in);
   }
}
