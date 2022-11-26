package weblogic.ejb20.swap;

import java.io.Serializable;
import javax.ejb.Handle;

public class HandleReplacer implements Serializable {
   private static final long serialVersionUID = 6751205237903998559L;
   private Handle handle;

   public HandleReplacer() {
   }

   public HandleReplacer(Handle theHandle) {
      this.handle = theHandle;
   }

   public Handle getHandle() {
      return this.handle;
   }
}
