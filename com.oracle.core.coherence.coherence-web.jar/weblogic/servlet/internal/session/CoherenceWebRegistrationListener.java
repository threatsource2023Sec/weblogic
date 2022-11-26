package weblogic.servlet.internal.session;

import com.tangosol.coherence.servlet.HttpSessionCollection;
import com.tangosol.coherence.servlet.SessionHelper;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionListener;
import weblogic.servlet.internal.EventsManager;

public class CoherenceWebRegistrationListener implements EventsManager.RegistrationListener {
   protected HttpSessionCollection collection;
   protected CoherenceWebSessionContextImpl sessionContext;
   protected SessionHelper sessionHelper;

   public CoherenceWebRegistrationListener(HttpSessionCollection collection, CoherenceWebSessionContextImpl sessionContext, SessionHelper sessionHelper) {
      this.collection = collection;
      this.sessionContext = sessionContext;
      this.sessionHelper = sessionHelper;
   }

   public void eventListenerAdded(Object o) {
      if (o instanceof HttpSessionListener) {
         this.collection.addHttpSessionListener((HttpSessionListener)o);
      }

      if (o instanceof HttpSessionAttributeListener) {
         HttpSessionAttributeListener listener = (HttpSessionAttributeListener)o;
         CoherenceWebSessionAttributeListenerWrapper wrapper = new CoherenceWebSessionAttributeListenerWrapper(listener, this.sessionContext);
         this.collection.addHttpSessionAttributeListener(wrapper);
      }

      this.sessionHelper.logWarningIfRemoteDeleteMechanismConfigured();
   }

   public void eventListenerRemoved(Object o) {
      if (o instanceof HttpSessionListener) {
         this.collection.removeHttpSessionListener((HttpSessionListener)o);
      }

      if (o instanceof HttpSessionAttributeListener) {
         HttpSessionAttributeListener listener = (HttpSessionAttributeListener)o;
         CoherenceWebSessionAttributeListenerWrapper wrapper = new CoherenceWebSessionAttributeListenerWrapper(listener, this.sessionContext);
         this.collection.removeHttpSessionAttributeListener(wrapper);
      }

   }
}
