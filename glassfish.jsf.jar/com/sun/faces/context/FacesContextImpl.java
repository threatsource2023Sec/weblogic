package com.sun.faces.context;

import com.sun.faces.el.ELContextImpl;
import com.sun.faces.el.ELUtils;
import com.sun.faces.renderkit.RenderKitUtils;
import com.sun.faces.util.FacesLogger;
import com.sun.faces.util.RequestStateManager;
import com.sun.faces.util.Util;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.el.ELContext;
import javax.el.ELContextEvent;
import javax.el.ELContextListener;
import javax.el.ExpressionFactory;
import javax.faces.FactoryFinder;
import javax.faces.application.Application;
import javax.faces.application.ApplicationFactory;
import javax.faces.application.FacesMessage;
import javax.faces.application.ViewHandler;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.PartialViewContext;
import javax.faces.context.PartialViewContextFactory;
import javax.faces.context.ResponseStream;
import javax.faces.context.ResponseWriter;
import javax.faces.event.PhaseId;
import javax.faces.lifecycle.Lifecycle;
import javax.faces.render.RenderKit;
import javax.faces.render.RenderKitFactory;

public class FacesContextImpl extends FacesContext {
   private static final String POST_BACK_MARKER = FacesContextImpl.class.getName() + "_POST_BACK";
   private static final ThreadLocal DEFAULT_FACES_CONTEXT = new ThreadLocal();
   private static final Logger LOGGER;
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
   private FacesMessage.Severity maxSeverity;
   private boolean renderResponse = false;
   private boolean responseComplete = false;
   private boolean validationFailed = false;
   private Map attributes;
   private List resourceLibraryContracts;
   private PhaseId currentPhaseId;
   private PartialViewContext partialViewContext = null;
   private ExceptionHandler exceptionHandler = null;
   private Map componentMessageLists;

   public FacesContextImpl(ExternalContext ec, Lifecycle lifecycle) {
      Util.notNull("ec", ec);
      Util.notNull("lifecycle", lifecycle);
      this.externalContext = ec;
      setCurrentInstance(this);
      DEFAULT_FACES_CONTEXT.set(this);
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

   public ExceptionHandler getExceptionHandler() {
      return this.exceptionHandler;
   }

   public void setExceptionHandler(ExceptionHandler exceptionHandler) {
      this.exceptionHandler = exceptionHandler;
   }

   public PartialViewContext getPartialViewContext() {
      this.assertNotReleased();
      if (this.partialViewContext == null) {
         PartialViewContextFactory f = (PartialViewContextFactory)FactoryFinder.getFactory("javax.faces.context.PartialViewContextFactory");
         this.partialViewContext = f.getPartialViewContext(FacesContext.getCurrentInstance());
      }

      return this.partialViewContext;
   }

   public boolean isPostback() {
      this.assertNotReleased();
      Boolean postback = (Boolean)this.getAttributes().get(POST_BACK_MARKER);
      if (postback == null) {
         RenderKit rk = this.getRenderKit();
         if (rk != null) {
            postback = rk.getResponseStateManager().isPostback(this);
         } else {
            ViewHandler vh = this.getApplication().getViewHandler();
            String rkId = vh.calculateRenderKitId(this);
            postback = RenderKitUtils.getResponseStateManager(this, rkId).isPostback(this);
         }

         this.getAttributes().put(POST_BACK_MARKER, postback);
      }

      return postback;
   }

   public boolean isReleased() {
      return this.released;
   }

   public Map getAttributes() {
      this.assertNotReleased();
      if (this.attributes == null) {
         this.attributes = new HashMap();
      }

      return this.attributes;
   }

   public ELContext getELContext() {
      this.assertNotReleased();
      if (this.elContext == null) {
         Application app = this.getApplication();
         this.elContext = new ELContextImpl(app.getELResolver());
         FacesContext facesContext = FacesContext.getCurrentInstance();
         this.elContext.putContext(FacesContext.class, facesContext);
         ExpressionFactory exFactory = ELUtils.getDefaultExpressionFactory(facesContext);
         if (null != exFactory) {
            this.elContext.putContext(ExpressionFactory.class, exFactory);
         }

         UIViewRoot root = this.getViewRoot();
         if (null != root) {
            this.elContext.setLocale(root.getLocale());
         }

         ELContextListener[] listeners = app.getELContextListeners();
         if (listeners.length > 0) {
            ELContextEvent event = new ELContextEvent(this.elContext);
            ELContextListener[] var7 = listeners;
            int var8 = listeners.length;

            for(int var9 = 0; var9 < var8; ++var9) {
               ELContextListener listener = var7[var9];
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

   public List getMessageList() {
      this.assertNotReleased();
      if (null == this.componentMessageLists) {
         return Collections.unmodifiableList(Collections.emptyList());
      } else {
         List messages = new ArrayList();
         Iterator var2 = this.componentMessageLists.values().iterator();

         while(var2.hasNext()) {
            List list = (List)var2.next();
            messages.addAll(list);
         }

         return Collections.unmodifiableList(messages);
      }
   }

   public List getMessageList(String clientId) {
      this.assertNotReleased();
      if (null == this.componentMessageLists) {
         return Collections.unmodifiableList(Collections.emptyList());
      } else {
         List list = (List)this.componentMessageLists.get(clientId);
         return Collections.unmodifiableList(list != null ? list : Collections.emptyList());
      }
   }

   public Iterator getMessages() {
      this.assertNotReleased();
      List emptyList;
      if (null == this.componentMessageLists) {
         emptyList = Collections.emptyList();
         return emptyList.iterator();
      } else if (this.componentMessageLists.size() > 0) {
         return new ComponentMessagesIterator(this.componentMessageLists);
      } else {
         emptyList = Collections.emptyList();
         return emptyList.iterator();
      }
   }

   public Iterator getMessages(String clientId) {
      this.assertNotReleased();
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
            if (this.lastRk == null && LOGGER.isLoggable(Level.SEVERE)) {
               LOGGER.log(Level.SEVERE, "Unable to locate renderkit instance for render-kit-id {0}.  Using {1} instead.", new String[]{renderKitId, "HTML_BASIC"});
            }

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
      Util.notNull("responseStrean", responseStream);
      this.responseStream = responseStream;
   }

   public UIViewRoot getViewRoot() {
      this.assertNotReleased();
      return this.viewRoot;
   }

   public void setViewRoot(UIViewRoot root) {
      this.assertNotReleased();
      Util.notNull("root", root);
      if (this.viewRoot != null && !this.viewRoot.equals(root)) {
         Map viewMap = this.viewRoot.getViewMap(false);
         if (viewMap != null) {
            this.viewRoot.getViewMap().clear();
         }

         RequestStateManager.clearAttributesOnChangeOfView(this);
      }

      this.viewRoot = root;
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
      if (this.maxSeverity == null) {
         this.maxSeverity = message.getSeverity();
      } else {
         FacesMessage.Severity sev = message.getSeverity();
         if (sev.getOrdinal() > this.maxSeverity.getOrdinal()) {
            this.maxSeverity = sev;
         }
      }

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

   public PhaseId getCurrentPhaseId() {
      this.assertNotReleased();
      return this.currentPhaseId;
   }

   public void setCurrentPhaseId(PhaseId currentPhaseId) {
      this.assertNotReleased();
      this.currentPhaseId = currentPhaseId;
   }

   public void release() {
      this.released = true;
      this.externalContext = null;
      this.responseStream = null;
      this.responseWriter = null;
      this.componentMessageLists = null;
      this.renderResponse = false;
      this.responseComplete = false;
      this.validationFailed = false;
      this.viewRoot = null;
      this.maxSeverity = null;
      this.application = null;
      this.currentPhaseId = null;
      if (this.attributes != null) {
         this.attributes.clear();
         this.attributes = null;
      }

      if (null != this.resourceLibraryContracts) {
         this.resourceLibraryContracts.clear();
         this.resourceLibraryContracts = null;
      }

      if (this.partialViewContext != null) {
         this.partialViewContext.release();
      }

      this.partialViewContext = null;
      this.exceptionHandler = null;
      this.elContext = null;
      this.rkFactory = null;
      this.lastRk = null;
      this.lastRkId = null;
      setCurrentInstance((FacesContext)null);
      DEFAULT_FACES_CONTEXT.remove();
   }

   public void renderResponse() {
      this.assertNotReleased();
      this.renderResponse = true;
   }

   public void responseComplete() {
      this.assertNotReleased();
      this.responseComplete = true;
   }

   public void validationFailed() {
      this.assertNotReleased();
      this.validationFailed = true;
   }

   public boolean getRenderResponse() {
      this.assertNotReleased();
      return this.renderResponse;
   }

   public List getResourceLibraryContracts() {
      this.assertNotReleased();
      return null == this.resourceLibraryContracts ? Collections.emptyList() : this.resourceLibraryContracts;
   }

   public void setResourceLibraryContracts(List contracts) {
      this.assertNotReleased();
      if (null != contracts && !contracts.isEmpty()) {
         this.resourceLibraryContracts = new ArrayList(contracts);
      } else if (null != this.resourceLibraryContracts) {
         this.resourceLibraryContracts.clear();
         this.resourceLibraryContracts = null;
      }

   }

   public boolean getResponseComplete() {
      this.assertNotReleased();
      return this.responseComplete;
   }

   public boolean isValidationFailed() {
      this.assertNotReleased();
      return this.validationFailed;
   }

   public static FacesContext getDefaultFacesContext() {
      return (FacesContext)DEFAULT_FACES_CONTEXT.get();
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
