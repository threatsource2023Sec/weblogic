package com.sun.faces.renderkit.html_basic;

import com.sun.faces.renderkit.RenderKitUtils;
import java.io.IOException;
import java.util.logging.Level;
import java.util.regex.Pattern;
import javax.faces.component.UICommand;
import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlCommandScript;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.event.ActionEvent;
import javax.faces.event.PhaseId;

public class CommandScriptRenderer extends HtmlBasicRenderer {
   private static final Pattern PATTERN_NAME = Pattern.compile("[$a-z_](\\.?[$\\w])*", 2);

   public void decode(FacesContext context, UIComponent component) {
      this.rendererParamsNotNull(context, component);
      if (this.shouldDecode(component)) {
         String clientId = component.getClientId(context);
         if (RenderKitUtils.isPartialOrBehaviorAction(context, clientId)) {
            UICommand command = (UICommand)component;
            ActionEvent event = new ActionEvent(command);
            event.setPhaseId(command.isImmediate() ? PhaseId.APPLY_REQUEST_VALUES : PhaseId.INVOKE_APPLICATION);
            command.queueEvent(event);
            if (logger.isLoggable(Level.FINE)) {
               logger.fine("This commandScript resulted in form submission ActionEvent queued.");
            }
         }

      }
   }

   public void encodeBegin(FacesContext context, UIComponent component) throws IOException {
      this.rendererParamsNotNull(context, component);
      if (this.shouldEncode(component)) {
         HtmlCommandScript commandScript = (HtmlCommandScript)component;
         String clientId = commandScript.getClientId(context);
         if (RenderKitUtils.getForm(commandScript, context) == null) {
            throw new IllegalArgumentException("commandScript ID " + clientId + " must be placed in UIForm");
         } else {
            String name = commandScript.getName();
            if (name != null && PATTERN_NAME.matcher(name).matches()) {
               RenderKitUtils.renderJsfJsIfNecessary(context);
               ResponseWriter writer = context.getResponseWriter();

               assert writer != null;

               writer.startElement("span", commandScript);
               writer.writeAttribute("id", clientId, "id");
               writer.startElement("script", commandScript);
               writer.writeAttribute("type", "text/javascript", "type");
               RenderKitUtils.renderFunction(context, component, this.getBehaviorParameters(commandScript), clientId);
            } else {
               throw new IllegalArgumentException("commandScript ID " + clientId + " has an illegal name: '" + name + "'");
            }
         }
      }
   }

   public void encodeEnd(FacesContext context, UIComponent component) throws IOException {
      this.rendererParamsNotNull(context, component);
      if (this.shouldEncode(component)) {
         ResponseWriter writer = context.getResponseWriter();

         assert writer != null;

         writer.endElement("script");
         writer.endElement("span");
      }
   }

   public boolean getRendersChildren() {
      return false;
   }
}
