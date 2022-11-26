package weblogic.servlet.internal.session;

import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;

public class CoherenceWebSessionAttributeListenerWrapper implements HttpSessionAttributeListener {
   HttpSessionAttributeListener listener;
   CoherenceWebSessionContextImpl sessionContext;

   public CoherenceWebSessionAttributeListenerWrapper(HttpSessionAttributeListener listener, CoherenceWebSessionContextImpl sessionContext) {
      if (listener != null && sessionContext != null) {
         this.listener = listener;
         this.sessionContext = sessionContext;
      } else {
         throw new IllegalArgumentException("CoherenceWebSessionAttributeListenerWrapper constructor arguments can not be null");
      }
   }

   public void attributeAdded(HttpSessionBindingEvent event) {
      if (this.sessionContext.internalAttributeFilter.evaluate(event.getName())) {
         this.listener.attributeAdded(event);
      }

   }

   public void attributeRemoved(HttpSessionBindingEvent event) {
      if (this.sessionContext.internalAttributeFilter.evaluate(event.getName())) {
         this.listener.attributeRemoved(event);
      }

   }

   public void attributeReplaced(HttpSessionBindingEvent event) {
      if (this.sessionContext.internalAttributeFilter.evaluate(event.getName())) {
         this.listener.attributeReplaced(event);
      }

   }

   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (o instanceof CoherenceWebSessionAttributeListenerWrapper) {
         return this.listener.equals(((CoherenceWebSessionAttributeListenerWrapper)o).listener);
      } else {
         return o instanceof HttpSessionAttributeListener ? this.listener.equals(o) : false;
      }
   }

   public int hashCode() {
      return this.listener.hashCode();
   }

   public String toString() {
      return this.listener.toString();
   }
}
