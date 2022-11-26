package com.sun.faces.renderkit.html_basic;

import com.sun.faces.config.WebConfiguration;
import com.sun.faces.util.FacesLogger;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.application.ProjectStage;
import javax.faces.application.Resource;
import javax.faces.application.ResourceHandler;
import javax.faces.component.UIComponent;
import javax.faces.component.UINamingContainer;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.event.ComponentSystemEventListener;
import javax.faces.event.ListenerFor;
import javax.faces.event.PostAddToViewEvent;
import javax.faces.render.Renderer;

@ListenerFor(
   systemEventClass = PostAddToViewEvent.class
)
public abstract class ScriptStyleBaseRenderer extends Renderer implements ComponentSystemEventListener {
   private static final String COMP_KEY = ScriptStyleBaseRenderer.class.getName() + "_COMPOSITE_COMPONENT";
   protected static final Logger logger;

   public void processEvent(ComponentSystemEvent event) throws AbortProcessingException {
      UIComponent component = event.getComponent();
      FacesContext context = FacesContext.getCurrentInstance();
      String target = this.verifyTarget((String)component.getAttributes().get("target"));
      if (target != null) {
         UIComponent cc = UIComponent.getCurrentCompositeComponent(context);
         if (cc != null) {
            component.getAttributes().put(COMP_KEY, cc.getClientId(context));
         }

         context.getViewRoot().addComponentResource(context, component, target);
      }

   }

   public final void decode(FacesContext context, UIComponent component) {
   }

   public final boolean getRendersChildren() {
      return true;
   }

   public void encodeBegin(FacesContext context, UIComponent component) throws IOException {
      String ccID = (String)component.getAttributes().get(COMP_KEY);
      if (null != ccID) {
         char sep = UINamingContainer.getSeparatorChar(context);
         UIComponent cc;
         if (-1 != ccID.indexOf(sep)) {
            cc = context.getViewRoot().findComponent(':' + ccID);
         } else {
            cc = findComponentIgnoringNamingContainers(context.getViewRoot(), ccID, true);
         }

         UIComponent curCC = UIComponent.getCurrentCompositeComponent(context);
         if (cc != curCC) {
            component.popComponentFromEL(context);
            component.pushComponentToEL(context, cc);
            component.pushComponentToEL(context, component);
         }
      }

   }

   public final void encodeChildren(FacesContext context, UIComponent component) throws IOException {
      Map attributes = component.getAttributes();
      String name = (String)attributes.get("name");
      int childCount = component.getChildCount();
      boolean renderChildren = 0 < childCount;
      if (null == name) {
         if (0 == childCount) {
            if (context.isProjectStage(ProjectStage.Development)) {
               FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, "outputScript or outputStylesheet with no library, no name, and no body content", "Is body content intended?");
               context.addMessage(component.getClientId(context), message);
            }

            renderChildren = false;
         }
      } else if (0 < childCount) {
         logger.info("outputScript or outputStylesheet with name attribute and nested content. Ignoring nested content.");
         renderChildren = false;
      }

      if (renderChildren) {
         ResponseWriter writer = context.getResponseWriter();
         this.startInlineElement(writer, component);
         super.encodeChildren(context, component);
         this.endInlineElement(writer, component);
      }

   }

   public void encodeEnd(FacesContext context, UIComponent component) throws IOException {
      Map attributes = component.getAttributes();
      String name = (String)attributes.get("name");
      if (null != name) {
         int queryPos = name.indexOf("?");
         String query = null;
         if (queryPos > -1 && name.length() > queryPos) {
            query = name.substring(queryPos + 1);
            name = name.substring(0, queryPos);
         }

         String library = (String)attributes.get("library");
         ResourceHandler resourceHandler = context.getApplication().getResourceHandler();
         if (!resourceHandler.isResourceRendered(context, name, library)) {
            Resource resource = resourceHandler.createResource(name, library);
            String resourceUrl = "RES_NOT_FOUND";
            ResponseWriter writer = context.getResponseWriter();
            this.startExternalElement(writer, component);
            WebConfiguration webConfig = WebConfiguration.getInstance();
            String ccID;
            if (library == null && name != null && name.startsWith(webConfig.getOptionValue(WebConfiguration.WebContextInitParameter.WebAppContractsDirectory))) {
               if (context.isProjectStage(ProjectStage.Development)) {
                  ccID = "Illegal path, direct contract references are not allowed: " + name;
                  context.addMessage(component.getClientId(context), new FacesMessage(FacesMessage.SEVERITY_ERROR, ccID, ccID));
               }

               resource = null;
            }

            if (resource == null) {
               if (context.isProjectStage(ProjectStage.Development)) {
                  ccID = "Unable to find resource " + (library == null ? "" : library + ", ") + name;
                  context.addMessage(component.getClientId(context), new FacesMessage(FacesMessage.SEVERITY_ERROR, ccID, ccID));
               }
            } else {
               resourceUrl = resource.getRequestPath();
               if (query != null) {
                  resourceUrl = resourceUrl + (resourceUrl.indexOf("?") > -1 ? "&amp;" : "?") + query;
               }

               resourceUrl = context.getExternalContext().encodeResourceURL(resourceUrl);
            }

            this.endExternalElement(writer, component, resourceUrl);
            resourceHandler.markResourceRendered(context, name, library);
            ccID = (String)component.getAttributes().get(COMP_KEY);
            if (ccID != null) {
               component.popComponentFromEL(context);
               component.popComponentFromEL(context);
               component.pushComponentToEL(context, component);
            }

         }
      }
   }

   private static UIComponent findComponentIgnoringNamingContainers(UIComponent base, String id, boolean checkId) {
      if (checkId && id.equals(base.getId())) {
         return base;
      } else {
         UIComponent result = null;
         Iterator i = base.getFacetsAndChildren();

         while(i.hasNext()) {
            UIComponent kid = (UIComponent)i.next();
            if (checkId && id.equals(kid.getId())) {
               result = kid;
               break;
            }

            result = findComponentIgnoringNamingContainers(kid, id, true);
            if (result != null) {
               break;
            }

            if (id.equals(kid.getId())) {
               result = kid;
               break;
            }
         }

         return result;
      }
   }

   protected abstract void startInlineElement(ResponseWriter var1, UIComponent var2) throws IOException;

   protected abstract void endInlineElement(ResponseWriter var1, UIComponent var2) throws IOException;

   protected abstract void startExternalElement(ResponseWriter var1, UIComponent var2) throws IOException;

   protected abstract void endExternalElement(ResponseWriter var1, UIComponent var2, String var3) throws IOException;

   protected String verifyTarget(String toVerify) {
      return toVerify;
   }

   static {
      logger = FacesLogger.RENDERKIT.getLogger();
   }
}
