package weblogic.wtc.jatmi;

import java.io.Serializable;

public final class dreqid implements Serializable {
   private static final long serialVersionUID = -2059524409418486034L;
   public int reqid;

   public dreqid() {
      this.reqid = -1;
   }

   public dreqid(int r) {
      this.reqid = r;
   }
}
