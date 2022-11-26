package weblogic.jms.common;

import weblogic.jms.frontend.FEConnection;
import weblogic.messaging.ID;
import weblogic.messaging.dispatcher.Dispatcher;

public final class DestinationPeerGoneAdapter implements JMSPeerGoneListener {
   private final DestinationImpl destination;
   private final FEConnection feConnection;
   private transient int refCount;

   public DestinationPeerGoneAdapter(DestinationImpl destination, FEConnection feConnection) {
      this.destination = destination;
      this.feConnection = feConnection;
   }

   public DestinationImpl getDestinationImpl() {
      return this.destination;
   }

   public int incrementRefCount() {
      return ++this.refCount;
   }

   public int decrementRefCount() {
      return --this.refCount;
   }

   public ID getId() {
      return this.destination.getId();
   }

   public void dispatcherPeerGone(Exception e, Dispatcher dispatcher) {
      if (JMSDebug.JMSDispatcher.isDebugEnabled()) {
         JMSDebug.JMSDispatcher.debug("dispatcherPeerGone() for destination " + this.destination.getName() + " dispatcherId was " + this.destination.dispatcherId + " changing to null. " + e);
      }

      this.destination.dispatcherId = null;
      if (this.feConnection != null) {
         this.feConnection.removeTemporaryDestination(this.destination.destinationId);
         this.feConnection.getFrontEnd().removeBackEndDestination(this.destination);
      }

   }

   public int hashCode() {
      return this.destination.hashCode();
   }

   public boolean equals(Object o) {
      if (!(o instanceof DestinationPeerGoneAdapter)) {
         return false;
      } else {
         DestinationPeerGoneAdapter other = (DestinationPeerGoneAdapter)o;
         return this.destination.equals(other.destination);
      }
   }
}
