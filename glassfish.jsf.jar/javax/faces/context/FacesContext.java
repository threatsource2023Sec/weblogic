package javax.faces.context;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.el.ELContext;
import javax.faces.FactoryFinder;
import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.application.ProjectStage;
import javax.faces.component.UINamingContainer;
import javax.faces.component.UIViewRoot;
import javax.faces.event.PhaseId;
import javax.faces.lifecycle.Lifecycle;
import javax.faces.render.RenderKit;

public abstract class FacesContext {
   private FacesContext defaultFacesContext;
   private boolean processingEvents = true;
   private boolean isCreatedFromValidFactory = true;
   private static ConcurrentHashMap threadInitContext = new ConcurrentHashMap(2);
   private static ConcurrentHashMap initContextServletContext = new ConcurrentHashMap(2);
   private Map attributesForInvalidFactoryConstruction;
   private PartialViewContext partialViewContextForInvalidFactoryConstruction = null;
   private PhaseId currentPhaseIdForInvalidFactoryConstruction;
   private static ThreadLocal instance = new ThreadLocal() {
      protected FacesContext initialValue() {
         return null;
      }
   };

   public FacesContext() {
      Thread curThread = Thread.currentThread();
      StackTraceElement[] callstack = curThread.getStackTrace();
      if (null != callstack) {
         String declaringClassName = callstack[3].getClassName();

         try {
            ClassLoader curLoader = curThread.getContextClassLoader();
            Class declaringClass = curLoader.loadClass(declaringClassName);
            if (!FacesContextFactory.class.isAssignableFrom(declaringClass)) {
               this.isCreatedFromValidFactory = false;
            }
         } catch (ClassNotFoundException var6) {
         }
      }

   }

   public abstract Application getApplication();

   public Map getAttributes() {
      if (this.defaultFacesContext != null) {
         return this.defaultFacesContext.getAttributes();
      } else if (!this.isCreatedFromValidFactory) {
         if (this.attributesForInvalidFactoryConstruction == null) {
            this.attributesForInvalidFactoryConstruction = new HashMap();
         }

         return this.attributesForInvalidFactoryConstruction;
      } else {
         throw new UnsupportedOperationException();
      }
   }

   public PartialViewContext getPartialViewContext() {
      if (this.defaultFacesContext != null) {
         return this.defaultFacesContext.getPartialViewContext();
      } else if (!this.isCreatedFromValidFactory) {
         if (this.partialViewContextForInvalidFactoryConstruction == null) {
            PartialViewContextFactory f = (PartialViewContextFactory)FactoryFinder.getFactory("javax.faces.context.PartialViewContextFactory");
            this.partialViewContextForInvalidFactoryConstruction = f.getPartialViewContext(getCurrentInstance());
         }

         return this.partialViewContextForInvalidFactoryConstruction;
      } else {
         throw new UnsupportedOperationException();
      }
   }

   public abstract Iterator getClientIdsWithMessages();

   public ELContext getELContext() {
      if (this.defaultFacesContext != null) {
         return this.defaultFacesContext.getELContext();
      } else {
         throw new UnsupportedOperationException();
      }
   }

   public ExceptionHandler getExceptionHandler() {
      if (this.defaultFacesContext != null) {
         return this.defaultFacesContext.getExceptionHandler();
      } else {
         throw new UnsupportedOperationException();
      }
   }

   public void setExceptionHandler(ExceptionHandler exceptionHandler) {
      if (this.defaultFacesContext != null) {
         this.defaultFacesContext.setExceptionHandler(exceptionHandler);
      } else {
         throw new UnsupportedOperationException();
      }
   }

   public abstract ExternalContext getExternalContext();

   public abstract FacesMessage.Severity getMaximumSeverity();

   public abstract Iterator getMessages();

   public List getMessageList() {
      if (this.defaultFacesContext != null) {
         return this.defaultFacesContext.getMessageList();
      } else {
         throw new UnsupportedOperationException();
      }
   }

   public List getMessageList(String clientId) {
      if (this.defaultFacesContext != null) {
         return this.defaultFacesContext.getMessageList(clientId);
      } else {
         throw new UnsupportedOperationException();
      }
   }

   public abstract Iterator getMessages(String var1);

   public char getNamingContainerSeparatorChar() {
      return UINamingContainer.getSeparatorChar(this);
   }

   public abstract RenderKit getRenderKit();

   public abstract boolean getRenderResponse();

   public abstract boolean getResponseComplete();

   public List getResourceLibraryContracts() {
      return Collections.emptyList();
   }

   public void setResourceLibraryContracts(List contracts) {
   }

   public boolean isValidationFailed() {
      if (this.defaultFacesContext != null) {
         return this.defaultFacesContext.isValidationFailed();
      } else {
         throw new UnsupportedOperationException();
      }
   }

   public abstract ResponseStream getResponseStream();

   public abstract void setResponseStream(ResponseStream var1);

   public abstract ResponseWriter getResponseWriter();

   public abstract void setResponseWriter(ResponseWriter var1);

   public abstract UIViewRoot getViewRoot();

   public abstract void setViewRoot(UIViewRoot var1);

   public abstract void addMessage(String var1, FacesMessage var2);

   public boolean isReleased() {
      if (this.defaultFacesContext != null) {
         return this.defaultFacesContext.isReleased();
      } else {
         throw new UnsupportedOperationException();
      }
   }

   public abstract void release();

   public abstract void renderResponse();

   public boolean isPostback() {
      if (this.defaultFacesContext != null) {
         return this.defaultFacesContext.isPostback();
      } else {
         throw new UnsupportedOperationException();
      }
   }

   public abstract void responseComplete();

   public void validationFailed() {
      if (this.defaultFacesContext != null) {
         this.defaultFacesContext.validationFailed();
      } else {
         throw new UnsupportedOperationException();
      }
   }

   public PhaseId getCurrentPhaseId() {
      if (this.defaultFacesContext != null) {
         return this.defaultFacesContext.getCurrentPhaseId();
      } else if (!this.isCreatedFromValidFactory) {
         return this.currentPhaseIdForInvalidFactoryConstruction;
      } else {
         throw new UnsupportedOperationException();
      }
   }

   public void setCurrentPhaseId(PhaseId currentPhaseId) {
      if (this.defaultFacesContext != null) {
         this.defaultFacesContext.setCurrentPhaseId(currentPhaseId);
      } else {
         if (this.isCreatedFromValidFactory) {
            throw new UnsupportedOperationException();
         }

         this.currentPhaseIdForInvalidFactoryConstruction = currentPhaseId;
      }

   }

   public void setProcessingEvents(boolean processingEvents) {
      this.processingEvents = processingEvents;
   }

   public boolean isProcessingEvents() {
      return this.processingEvents;
   }

   public boolean isProjectStage(ProjectStage stage) {
      if (stage == null) {
         throw new NullPointerException();
      } else {
         return stage.equals(this.getApplication().getProjectStage());
      }
   }

   public static FacesContext getCurrentInstance() {
      FacesContext facesContext = (FacesContext)instance.get();
      if (null == facesContext) {
         facesContext = (FacesContext)threadInitContext.get(Thread.currentThread());
      }

      if (null == facesContext) {
         ClassLoader cl = Thread.currentThread().getContextClassLoader();
         if (cl == null) {
            return null;
         }

         FacesContextFactory privateFacesContextFactory = (FacesContextFactory)FactoryFinder.getFactory("com.sun.faces.ServletContextFacesContextFactory");
         if (null != privateFacesContextFactory) {
            facesContext = privateFacesContextFactory.getFacesContext((Object)null, (Object)null, (Object)null, (Lifecycle)null);
         }
      }

      return facesContext;
   }

   protected static void setCurrentInstance(FacesContext context) {
      if (context == null) {
         instance.remove();
      } else {
         instance.set(context);
      }

   }
}
