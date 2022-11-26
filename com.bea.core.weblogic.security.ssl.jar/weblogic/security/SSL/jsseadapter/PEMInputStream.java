package weblogic.security.SSL.jsseadapter;

import java.io.ByteArrayInputStream;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

/** @deprecated */
@Deprecated
public class PEMInputStream extends FilterInputStream {
   private PEMUtils.PEMData pd;

   public PEMInputStream(InputStream in) throws IOException {
      this(PEMUtils.parsePEM(in));
   }

   public PEMInputStream(PEMUtils.PEMData pd) {
      super((InputStream)null);
      this.pd = null;
      this.pd = new PEMUtils.PEMData(pd);
      this.in = new ByteArrayInputStream(this.pd.getData());
   }

   public PEMUtils.PEMData getPEMData() {
      return this.pd;
   }
}
