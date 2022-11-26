package kodo.remote;

import org.apache.openjpa.kernel.OpCallbacks;
import org.apache.openjpa.kernel.OpenJPAStateManager;

class ServerOpCallbacks implements OpCallbacks {
   private static final ServerOpCallbacks INSTANCE = new ServerOpCallbacks();

   public static ServerOpCallbacks getInstance() {
      return INSTANCE;
   }

   private ServerOpCallbacks() {
   }

   public int processArgument(int op, Object arg, OpenJPAStateManager sm) {
      return 4;
   }
}
