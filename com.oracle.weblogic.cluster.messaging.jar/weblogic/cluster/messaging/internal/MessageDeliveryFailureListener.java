package weblogic.cluster.messaging.internal;

import java.rmi.RemoteException;

public interface MessageDeliveryFailureListener {
   void onMessageDeliveryFailure(String var1, RemoteException var2);
}
