package com.sun.faces.context;

import com.sun.faces.el.ELContextImpl;
import com.sun.faces.util.FacesLogger;
import com.sun.faces.util.RequestStateManager;
import com.sun.faces.util.Util;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.el.ELContext;
import javax.el.ELContextEvent;
import javax.el.ELContextListener;
import javax.faces.FactoryFinder;
import javax.faces.application.Application;
import javax.faces.application.ApplicationFactory;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseStream;
import javax.faces.context.ResponseWriter;
import javax.faces.lifecycle.Lifecycle;
import javax.faces.render.RenderKit;
import javax.faces.render.RenderKitFactory;

public class FacesContextImpl extends FacesContext {
   private static Logger LOGGER;
   private boolean released;
   private ResponseStream responseStream = null;
   private ResponseWriter responseWriter = null;
   private ExternalContext externalContext = null;
   private Application application = null;
   private UIViewRoot viewRoot = null;
   private ELContext elContext = null;
   private RenderKitFactory rkFactory;
   private RenderKit lastRk;
   private String lastRkId;
   private Map componentMessageLists;
   private boolean renderResponse = false;
   private boolean responseComplete = false;

   public FacesContextImpl() {
   }

   public FacesContextImpl(ExternalContext ec, Lifecycle lifecycle) {
      Util.notNull("ec", ec);
      Util.notNull("lifecycle", lifecycle);
      this.externalContext = ec;
      setCurrentInstance(this);
      this.rkFactory = (RenderKitFactory)FactoryFinder.getFactory("javax.faces.render.RenderKitFactory");
   }

   public ExternalContext getExternalContext() {
      this.assertNotReleased();
      return this.externalContext;
   }

   public Application getApplication() {
      this.assertNotReleased();
      if (null != this.application) {
         return this.application;
      } else {
         ApplicationFactory aFactory = (ApplicationFactory)FactoryFinder.getFactory("javax.faces.application.ApplicationFactory");
         this.application = aFactory.getApplication();

         assert null != this.application;

         return this.application;
      }
   }

   public ELContext getELContext() {
      this.assertNotReleased();
      if (this.elContext == null) {
         Application app = this.getApplication();
         this.elContext = new ELContextImpl(app.getELResolver());
         this.elContext.putContext(FacesContext.class, this);
         UIViewRoot root = this.getViewRoot();
         if (null != root) {
            this.elContext.setLocale(root.getLocale());
         }

         ELContextListener[] listeners = app.getELContextListeners();
         if (listeners.length > 0) {
            ELContextEvent event = new ELContextEvent(this.elContext);
            ELContextListener[] arr$ = listeners;
            int len$ = listeners.length;

            for(int i$ = 0; i$ < len$; ++i$) {
               ELContextListener listener = arr$[i$];
               listener.contextCreated(event);
            }
         }
      }

      return this.elContext;
   }

   public Iterator getClientIdsWithMessages() {
      this.assertNotReleased();
      return this.componentMessageLists == null ? Collections.emptyList().iterator() : this.componentMessageLists.keySet().iterator();
   }

   public FacesMessage.Severity getMaximumSeverity() {
      this.assertNotReleased();
      FacesMessage.Severity result = null;
      if (this.componentMessageLists != null && !this.componentMessageLists.isEmpty()) {
         Iterator i = new ComponentMessagesIterator(this.componentMessageLists);

         while(i.hasNext()) {
            FacesMessage.Severity severity = ((FacesMessage)i.next()).getSeverity();
            if (result == null || severity.compareTo(result) > 0) {
               result = severity;
            }

            if (result == FacesMessage.SEVERITY_FATAL) {
               break;
            }
         }
      }

      return result;
   }

   public Iterator getMessages() {
      this.assertNotReleased();
      List emptyList;
      if (null == this.componentMessageLists) {
         emptyList = Collections.emptyList();
         return emptyList.iterator();
      } else {
         if (RequestStateManager.containsKey(this, "com.sun.faces.clientIdMessagesNotDisplayed")) {
            Set pendingClientIds = (Set)RequestStateManager.get(this, "com.sun.faces.clientIdMessagesNotDisplayed");
            pendingClientIds.clear();
         }

         if (this.componentMessageLists.size() > 0) {
            return new ComponentMessagesIterator(this.componentMessageLists);
         } else {
            emptyList = Collections.emptyList();
            return emptyList.iterator();
         }
      }
   }

   public Iterator getMessages(String clientId) {
      this.assertNotReleased();
      Set pendingClientIds = (Set)RequestStateManager.get(this, "com.sun.faces.clientIdMessagesNotDisplayed");
      if (pendingClientIds != null && !pendingClientIds.isEmpty()) {
         pendingClientIds.remove(clientId);
      }

      List list;
      if (null == this.componentMessageLists) {
         list = Collections.emptyList();
         return list.iterator();
      } else {
         list = (List)this.componentMessageLists.get(clientId);
         if (list == null) {
            List emptyList = Collections.emptyList();
            return emptyList.iterator();
         } else {
            return list.iterator();
         }
      }
   }

   public RenderKit getRenderKit() {
      this.assertNotReleased();
      UIViewRoot vr = this.getViewRoot();
      if (vr == null) {
         return null;
      } else {
         String renderKitId = vr.getRenderKitId();
         if (renderKitId == null) {
            return null;
         } else if (renderKitId.equals(this.lastRkId)) {
            return this.lastRk;
         } else {
            this.lastRk = this.rkFactory.getRenderKit(this, renderKitId);
            this.lastRkId = renderKitId;
            return this.lastRk;
         }
      }
   }

   public ResponseStream getResponseStream() {
      this.assertNotReleased();
      return this.responseStream;
   }

   public void setResponseStream(ResponseStream responseStream) {
      this.assertNotReleased();
      Util.notNull("responseStream", responseStream);
      this.responseStream = responseStream;
   }

   public UIViewRoot getViewRoot() {
      this.assertNotReleased();
      return this.viewRoot;
   }

   public void setViewRoot(UIViewRoot viewRoot) {
      this.assertNotReleased();
      Util.notNull("viewRoot", viewRoot);
      this.viewRoot = viewRoot;
   }

   public ResponseWriter getResponseWriter() {
      this.assertNotReleased();
      return this.responseWriter;
   }

   public void setResponseWriter(ResponseWriter responseWriter) {
      this.assertNotReleased();
      Util.notNull("responseWriter", responseWriter);
      this.responseWriter = responseWriter;
   }

   public void addMessage(String clientId, FacesMessage message) {
      this.assertNotReleased();
      Util.notNull("message", message);
      if (this.componentMessageLists == null) {
         this.componentMessageLists = new LinkedHashMap();
      }

      List list = (List)this.componentMessageLists.get(clientId);
      if (list == null) {
         list = new ArrayList();
         this.componentMessageLists.put(clientId, list);
      }

      ((List)list).add(message);
      if (LOGGER.isLoggable(Level.FINE)) {
         LOGGER.fine("Adding Message[sourceId=" + (clientId != null ? clientId : "<<NONE>>") + ",summary=" + message.getSummary() + ")");
      }

   }

   public void release() {
      RequestStateManager.remove(this, "com.sun.faces.FacesContextImpl");
      RequestStateManager.remove(this, "com.sun.faces.clientIdMessagesNotDisplayed");
      this.released = true;
      this.externalContext = null;
      this.responseStream = null;
      this.responseWriter = null;
      this.componentMessageLists = null;
      this.renderResponse = false;
      this.responseComplete = false;
      this.viewRoot = null;
      setCurrentInstance((FacesContext)null);
   }

   public void renderResponse() {
      this.assertNotReleased();
      this.renderResponse = true;
   }

   public void responseComplete() {
      this.assertNotReleased();
      this.responseComplete = true;
   }

   public boolean getRenderResponse() {
      this.assertNotReleased();
      return this.renderResponse;
   }

   public boolean getResponseComplete() {
      this.assertNotReleased();
      return this.responseComplete;
   }

   private void assertNotReleased() {
      if (this.released) {
         throw new IllegalStateException();
      }
   }

   static {
      LOGGER = FacesLogger.CONTEXT.getLogger();
   }

   private static final class ComponentMessagesIterator implements Iterator {
      private Map messages;
      private int outerIndex = -1;
      private int messagesSize;
      private Iterator inner;
      private Iterator keys;

      ComponentMessagesIterator(Map messages) {
         this.messages = messages;
         this.messagesSize = messages.size();
         this.keys = messages.keySet().iterator();
      }

      public boolean hasNext() {
         if (this.outerIndex == -1) {
            ++this.outerIndex;
            this.inner = ((List)this.messages.get(this.keys.next())).iterator();
         }

         while(!this.inner.hasNext()) {
            ++this.outerIndex;
            if (this.outerIndex >= this.messagesSize) {
               return false;
            }

            this.inner = ((List)this.messages.get(this.keys.next())).iterator();
         }

         return this.inner.hasNext();
      }

      public FacesMessage next() {
         if (this.outerIndex >= this.messagesSize) {
            throw new NoSuchElementException();
         } else if (this.inner != null && this.inner.hasNext()) {
            return (FacesMessage)this.inner.next();
         } else if (!this.hasNext()) {
            throw new NoSuchElementException();
         } else {
            return (FacesMessage)this.inner.next();
         }
      }

      public void remove() {
         if (this.outerIndex == -1) {
            throw new IllegalStateException();
         } else {
            this.inner.remove();
         }
      }
   }
}
