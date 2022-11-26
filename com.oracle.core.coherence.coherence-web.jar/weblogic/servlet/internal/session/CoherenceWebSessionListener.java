package weblogic.servlet.internal.session;

import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import weblogic.servlet.internal.WebAppServletContext;

public class CoherenceWebSessionListener implements HttpSessionListener, HttpSessionAttributeListener {
   protected WebAppServletContext servletContext;
   protected CoherenceWebSessionContextImpl sessionContext;

   public CoherenceWebSessionListener(WebAppServletContext servletContext, CoherenceWebSessionContextImpl sessionContext) {
      if (servletContext != null && sessionContext != null) {
         this.servletContext = servletContext;
         this.sessionContext = sessionContext;
      } else {
         throw new IllegalArgumentException("CoherenceWebSessionData constructor arguments can not be null");
      }
   }

   public void sessionCreated(HttpSessionEvent event) {
      this.servletContext.getEventsManager().notifySessionLifetimeEvent(event.getSession(), true);
   }

   public void sessionDestroyed(HttpSessionEvent event) {
      this.servletContext.getEventsManager().notifySessionLifetimeEvent(event.getSession(), false);
   }

   public void attributeAdded(HttpSessionBindingEvent event) {
      String name = event.getName();
      if (this.sessionContext.internalAttributeFilter.evaluate(name)) {
         this.sessionContext.notifySessionAttributeChange(event.getSession(), name, (Object)null, event.getValue());
      }

   }

   public void attributeRemoved(HttpSessionBindingEvent event) {
      String name = event.getName();
      if (this.sessionContext.internalAttributeFilter.evaluate(name)) {
         this.sessionContext.notifySessionAttributeChange(event.getSession(), name, event.getValue(), (Object)null);
      }

   }

   public void attributeReplaced(HttpSessionBindingEvent event) {
      String name = event.getName();
      if (this.sessionContext.internalAttributeFilter.evaluate(name)) {
         Object value = event.getValue();
         this.sessionContext.notifySessionAttributeChange(event.getSession(), name, value, value);
      }

   }
}
