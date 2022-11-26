package weblogic.transaction.internal;

import java.rmi.Remote;
import javax.naming.Context;

public class OpenCoordinatorFactory extends CoordinatorFactory {
   public Object runAction(String coName, String serverURL, Context ctx) throws Exception {
      return null;
   }

   public void addDisconnectListenerToDisconnectMonitor(Remote o, String coID) {
   }
}
