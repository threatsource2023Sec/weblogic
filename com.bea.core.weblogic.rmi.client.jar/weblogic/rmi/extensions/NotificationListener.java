package weblogic.rmi.extensions;

public interface NotificationListener {
   void notifyRemoteCallBegin();

   void notifyRemoteCallEnd();
}
