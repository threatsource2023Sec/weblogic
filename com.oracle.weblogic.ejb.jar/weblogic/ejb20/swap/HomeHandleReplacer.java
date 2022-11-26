package weblogic.ejb20.swap;

import java.io.Serializable;
import javax.ejb.HomeHandle;

public class HomeHandleReplacer implements Serializable {
   private static final long serialVersionUID = -2900575846063107655L;
   private HomeHandle handle;

   public HomeHandleReplacer() {
   }

   public HomeHandleReplacer(HomeHandle theHandle) {
      this.handle = theHandle;
   }

   public HomeHandle getHomeHandle() {
      return this.handle;
   }
}
