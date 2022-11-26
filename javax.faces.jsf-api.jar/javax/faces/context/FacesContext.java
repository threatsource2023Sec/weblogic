package javax.faces.context;

import java.util.Iterator;
import java.util.Map;
import javax.el.ELContext;
import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIViewRoot;
import javax.faces.render.RenderKit;

public abstract class FacesContext {
   private static ThreadLocal instance = new ThreadLocal() {
      protected FacesContext initialValue() {
         return null;
      }
   };

   public abstract Application getApplication();

   public abstract Iterator getClientIdsWithMessages();

   public ELContext getELContext() {
      Map m = (Map)this.getExternalContext().getRequestMap().get("com.sun.faces.util.RequestStateManager");
      if (m != null) {
         FacesContext impl = (FacesContext)m.get("com.sun.faces.FacesContextImpl");
         if (impl != null) {
            return impl.getELContext();
         } else {
            throw new UnsupportedOperationException();
         }
      } else {
         throw new UnsupportedOperationException();
      }
   }

   public abstract ExternalContext getExternalContext();

   public abstract FacesMessage.Severity getMaximumSeverity();

   public abstract Iterator getMessages();

   public abstract Iterator getMessages(String var1);

   public abstract RenderKit getRenderKit();

   public abstract boolean getRenderResponse();

   public abstract boolean getResponseComplete();

   public abstract ResponseStream getResponseStream();

   public abstract void setResponseStream(ResponseStream var1);

   public abstract ResponseWriter getResponseWriter();

   public abstract void setResponseWriter(ResponseWriter var1);

   public abstract UIViewRoot getViewRoot();

   public abstract void setViewRoot(UIViewRoot var1);

   public abstract void addMessage(String var1, FacesMessage var2);

   public abstract void release();

   public abstract void renderResponse();

   public abstract void responseComplete();

   public static FacesContext getCurrentInstance() {
      return (FacesContext)instance.get();
   }

   protected static void setCurrentInstance(FacesContext context) {
      if (context == null) {
         instance.remove();
      } else {
         instance.set(context);
      }

   }
}
