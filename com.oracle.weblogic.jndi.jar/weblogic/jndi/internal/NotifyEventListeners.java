package weblogic.jndi.internal;

import java.util.Iterator;
import java.util.List;
import javax.naming.event.NamespaceChangeListener;
import javax.naming.event.NamingEvent;
import javax.naming.event.NamingListener;
import javax.naming.event.ObjectChangeListener;

public final class NotifyEventListeners {
   private final List listeners;
   private final NamingEvent namingEvent;
   private final int action;

   NotifyEventListeners(List listeners, NamingEvent namingEvent, int action) {
      this.listeners = listeners;
      this.namingEvent = namingEvent;
      this.action = action;
   }

   public void notifyListeners() {
      Iterator var1 = this.listeners.iterator();

      while(var1.hasNext()) {
         NamingListener listener = (NamingListener)var1.next();
         if (listener instanceof ObjectChangeListener) {
            this.handleListener((ObjectChangeListener)listener);
         } else {
            if (!(listener instanceof NamespaceChangeListener)) {
               throw new AssertionError(" Unknown event listener " + listener + '\t' + (listener != null ? listener.getClass().getName() : "null"));
            }

            this.handleListener(this.action, (NamespaceChangeListener)listener);
         }
      }

   }

   private void handleListener(ObjectChangeListener l) {
      l.objectChanged(this.namingEvent);
   }

   private void handleListener(int action, NamespaceChangeListener l) {
      switch (action) {
         case 0:
            l.objectAdded(this.namingEvent);
            break;
         case 1:
            l.objectRemoved(this.namingEvent);
            break;
         case 2:
            l.objectRenamed(this.namingEvent);
            break;
         default:
            throw new AssertionError("Unknown action: " + action + " on listener " + l);
      }

   }
}
