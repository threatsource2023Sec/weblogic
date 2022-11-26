package javax.faces.component.behavior;

import java.util.Collections;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.render.ClientBehaviorRenderer;
import javax.faces.render.RenderKit;

public class ClientBehaviorBase extends BehaviorBase implements ClientBehavior {
   private static final Logger logger = Logger.getLogger("javax.faces.component.behavior", "javax.faces.LogStrings");

   public String getScript(ClientBehaviorContext behaviorContext) {
      if (null == behaviorContext) {
         throw new NullPointerException();
      } else {
         ClientBehaviorRenderer renderer = this.getRenderer(behaviorContext.getFacesContext());
         String script = null;
         if (null != renderer) {
            script = renderer.getScript(behaviorContext, this);
         }

         return script;
      }
   }

   public void decode(FacesContext context, UIComponent component) {
      if (null != context && null != component) {
         ClientBehaviorRenderer renderer = this.getRenderer(context);
         if (null != renderer) {
            renderer.decode(context, component, this);
         }

      } else {
         throw new NullPointerException();
      }
   }

   public String getRendererType() {
      return null;
   }

   public Set getHints() {
      return Collections.emptySet();
   }

   protected ClientBehaviorRenderer getRenderer(FacesContext context) {
      if (null == context) {
         throw new NullPointerException();
      } else {
         ClientBehaviorRenderer renderer = null;
         String rendererType = this.getRendererType();
         if (null != rendererType) {
            RenderKit renderKit = context.getRenderKit();
            if (null != renderKit) {
               renderer = renderKit.getClientBehaviorRenderer(rendererType);
            }

            if (null == renderer && logger.isLoggable(Level.FINE)) {
               logger.fine("Can't get  behavior renderer for type " + rendererType);
            }
         } else if (logger.isLoggable(Level.FINE)) {
            logger.fine("No renderer-type for behavior " + this.getClass().getName());
         }

         return renderer;
      }
   }
}
