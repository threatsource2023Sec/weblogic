package weblogic.j2ee;

public interface TrackableConnection {
   boolean isLocalTransactionInProgress();

   void connectionClosed();
}
