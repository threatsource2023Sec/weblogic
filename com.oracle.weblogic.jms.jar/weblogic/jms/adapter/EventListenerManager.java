package weblogic.jms.adapter;

import java.util.Vector;
import javax.resource.spi.ConnectionEvent;
import javax.resource.spi.ConnectionEventListener;
import javax.resource.spi.ManagedConnection;

public class EventListenerManager implements ConnectionEventListener {
   private Vector listeners = new Vector();
   private ManagedConnection mc;

   public EventListenerManager(ManagedConnection mc) {
      this.mc = mc;
   }

   public void sendEvent(int eventType, Exception ex, Object connectionHandle) {
      Vector list = null;
      synchronized(this) {
         list = (Vector)this.listeners.clone();
      }

      ConnectionEvent ce = null;
      if (ex == null) {
         ce = new ConnectionEvent(this.mc, eventType);
      } else {
         ce = new ConnectionEvent(this.mc, eventType, ex);
      }

      if (connectionHandle != null) {
         ce.setConnectionHandle(connectionHandle);
      }

      int size = list.size();

      for(int i = 0; i < size; ++i) {
         ConnectionEventListener l = (ConnectionEventListener)list.elementAt(i);
         switch (eventType) {
            case 1:
               l.connectionClosed(ce);
               break;
            case 2:
               l.localTransactionStarted(ce);
               break;
            case 3:
               l.localTransactionCommitted(ce);
               break;
            case 4:
               l.localTransactionRolledback(ce);
               break;
            case 5:
               l.connectionErrorOccurred(ce);
               break;
            default:
               throw new IllegalArgumentException("Illegal eventType: " + eventType);
         }
      }

   }

   public synchronized void addConnectorListener(ConnectionEventListener l) {
      this.listeners.addElement(l);
   }

   public synchronized void removeConnectorListener(ConnectionEventListener l) {
      this.listeners.removeElement(l);
   }

   public void connectionClosed(ConnectionEvent event) {
   }

   public void connectionErrorOccurred(ConnectionEvent event) {
      this.sendEvent(5, event.getException(), (Object)null);
   }

   public void localTransactionStarted(ConnectionEvent event) {
   }

   public void localTransactionRolledback(ConnectionEvent event) {
   }

   public void localTransactionCommitted(ConnectionEvent event) {
   }
}
