package com.sun.faces.renderkit.html_basic;

import com.sun.faces.renderkit.Attribute;
import com.sun.faces.renderkit.AttributeManager;
import com.sun.faces.util.MessageUtils;
import com.sun.faces.util.Util;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import javax.faces.application.NavigationCase;
import javax.faces.application.ProjectStage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

public class OutcomeTargetLinkRenderer extends OutcomeTargetRenderer {
   private static final Attribute[] ATTRIBUTES;
   private static final String NO_NAV_CASE;
   private static final List EXCLUDED_ATTRIBUTES;

   public void encodeBegin(FacesContext context, UIComponent component) throws IOException {
      this.rendererParamsNotNull(context, component);
      if (this.shouldEncode(component)) {
         NavigationCase navCase = null;
         boolean failedToResolveNavigationCase = false;
         boolean disabled = Util.componentIsDisabled(component);
         if (!disabled) {
            navCase = this.getNavigationCase(context, component);
            if (navCase == null) {
               failedToResolveNavigationCase = true;
               context.getAttributes().put(NO_NAV_CASE, true);
            }
         }

         if (!disabled && navCase != null) {
            this.renderAsActive(context, navCase, component);
         } else {
            this.renderAsDisabled(context, component, failedToResolveNavigationCase);
         }

      }
   }

   public void encodeEnd(FacesContext context, UIComponent component) throws IOException {
      this.rendererParamsNotNull(context, component);
      if (this.shouldEncode(component)) {
         ResponseWriter writer = context.getResponseWriter();

         assert writer != null;

         String endElement = !Util.componentIsDisabled(component) && context.getAttributes().remove(NO_NAV_CASE) == null ? "a" : "span";
         writer.endElement(endElement);
      }
   }

   protected void renderAsDisabled(FacesContext context, UIComponent component, boolean failedToResolveNavigationCase) throws IOException {
      ResponseWriter writer = context.getResponseWriter();

      assert writer != null;

      writer.startElement("span", component);
      this.writeIdAndNameAttributes(context, writer, component);
      this.renderLinkCommonAttributes(writer, component);
      this.renderPassThruAttributes(context, writer, component, ATTRIBUTES, EXCLUDED_ATTRIBUTES);
      this.writeValue(writer, component);
      if (failedToResolveNavigationCase && !context.isProjectStage(ProjectStage.Production)) {
         writer.write(MessageUtils.getExceptionMessageString("com.sun.faces.OUTCOME_TARGET_LINK_NO_MATCH"));
      }

   }

   protected void renderAsActive(FacesContext context, NavigationCase navCase, UIComponent component) throws IOException {
      ResponseWriter writer = context.getResponseWriter();

      assert writer != null;

      writer.startElement("a", component);
      this.writeIdAndNameAttributes(context, writer, component);
      String hrefVal = this.getEncodedTargetURL(context, component, navCase);
      hrefVal = hrefVal + this.getFragment(component);
      writer.writeURIAttribute("href", hrefVal, "outcome");
      this.renderLinkCommonAttributes(writer, component);
      this.renderPassThruAttributes(context, writer, component, ATTRIBUTES, (List)null);
      this.writeValue(writer, component);
   }

   protected void writeIdAndNameAttributes(FacesContext context, ResponseWriter writer, UIComponent component) throws IOException {
      String writtenId = this.writeIdAttributeIfNecessary(context, writer, component);
      if (null != writtenId) {
         writer.writeAttribute("name", writtenId, "name");
      }

   }

   protected void writeValue(ResponseWriter writer, UIComponent component) throws IOException {
      writer.writeText(this.getLabel(component), component, (String)null);
      writer.flush();
   }

   protected void renderLinkCommonAttributes(ResponseWriter writer, UIComponent component) throws IOException {
      String styleClass = (String)component.getAttributes().get("styleClass");
      if (styleClass != null && styleClass.length() > 0) {
         writer.writeAttribute("class", styleClass, "styleClass");
      }

      String target = (String)component.getAttributes().get("target");
      if (target != null && target.length() > 0) {
         writer.writeAttribute("target", target, "target");
      }

      String onclick = (String)component.getAttributes().get("onclick");
      if (onclick != null && onclick.length() > 0) {
         writer.writeAttribute("onclick", onclick, "onclick");
      }

   }

   static {
      ATTRIBUTES = AttributeManager.getAttributes(AttributeManager.Key.OUTCOMETARGETLINK);
      NO_NAV_CASE = OutcomeTargetLinkRenderer.class.getName() + "_NO_NAV_CASE";
      EXCLUDED_ATTRIBUTES = Arrays.asList("disabled");
   }
}
