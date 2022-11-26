package com.sun.faces.renderkit.html_basic;

import com.sun.faces.renderkit.Attribute;
import com.sun.faces.renderkit.AttributeManager;
import com.sun.faces.renderkit.RenderKitUtils;
import com.sun.faces.util.Util;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.logging.Level;
import javax.faces.component.UIComponent;
import javax.faces.component.UIOutput;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

public class OutputLinkRenderer extends LinkRenderer {
   private static final Attribute[] ATTRIBUTES;

   public void decode(FacesContext context, UIComponent component) {
      this.rendererParamsNotNull(context, component);
      if (this.shouldDecode(component)) {
         this.decodeBehaviors(context, component);
      }

   }

   public void encodeBegin(FacesContext context, UIComponent component) throws IOException {
      this.rendererParamsNotNull(context, component);
      UIOutput output = (UIOutput)component;
      boolean componentDisabled = false;
      if (output.getAttributes().get("disabled") != null && output.getAttributes().get("disabled").equals(Boolean.TRUE)) {
         componentDisabled = true;
      }

      if (componentDisabled) {
         this.renderAsDisabled(context, output);
      } else {
         this.renderAsActive(context, output);
      }

   }

   public void encodeChildren(FacesContext context, UIComponent component) throws IOException {
      this.rendererParamsNotNull(context, component);
      if (this.shouldEncodeChildren(component)) {
         if (component.getChildCount() > 0) {
            Iterator var3 = component.getChildren().iterator();

            while(var3.hasNext()) {
               UIComponent kid = (UIComponent)var3.next();
               this.encodeRecursive(context, kid);
            }
         }

      }
   }

   public void encodeEnd(FacesContext context, UIComponent component) throws IOException {
      this.rendererParamsNotNull(context, component);
      if (this.shouldEncode(component)) {
         ResponseWriter writer = context.getResponseWriter();

         assert writer != null;

         if (Boolean.TRUE.equals(component.getAttributes().get("disabled"))) {
            writer.endElement("span");
         } else {
            writer.endElement("a");
         }

      }
   }

   public boolean getRendersChildren() {
      return true;
   }

   protected String getFragment(UIComponent component) {
      String fragment = (String)component.getAttributes().get("fragment");
      fragment = fragment != null ? fragment.trim() : "";
      if (fragment.length() > 0) {
         fragment = "#" + fragment;
      }

      return fragment;
   }

   protected Object getValue(UIComponent component) {
      return Util.componentIsDisabled(component) ? null : ((UIOutput)component).getValue();
   }

   protected void renderAsActive(FacesContext context, UIComponent component) throws IOException {
      String hrefVal = this.getCurrentValue(context, component);
      if (logger.isLoggable(Level.FINE)) {
         logger.fine("Value to be rendered " + hrefVal);
      }

      if (!component.isRendered()) {
         if (logger.isLoggable(Level.FINE)) {
            logger.fine("End encoding component " + component.getId() + " since rendered attribute is set to false ");
         }

      } else {
         ResponseWriter writer = context.getResponseWriter();

         assert writer != null;

         writer.startElement("a", component);
         String writtenId = this.writeIdAttributeIfNecessary(context, writer, component);
         if (null != writtenId) {
            writer.writeAttribute("name", writtenId, "name");
         }

         if (null == hrefVal || 0 == hrefVal.length()) {
            hrefVal = "";
         }

         HtmlBasicRenderer.Param[] paramList = this.getParamList(component);
         StringBuffer sb = new StringBuffer();
         sb.append(hrefVal);
         boolean paramWritten = hrefVal.indexOf(63) > 0;
         int i = 0;

         for(int len = paramList.length; i < len; ++i) {
            String pn = paramList[i].name;
            if (pn != null && pn.length() != 0) {
               String pv = paramList[i].value;
               sb.append((char)(paramWritten ? '&' : '?'));
               sb.append(URLEncoder.encode(pn, "UTF-8"));
               sb.append('=');
               if (pv != null && pv.length() != 0) {
                  sb.append(URLEncoder.encode(pv, "UTF-8"));
               }

               paramWritten = true;
            }
         }

         sb.append(this.getFragment(component));
         writer.writeURIAttribute("href", context.getExternalContext().encodeResourceURL(sb.toString()), "href");
         RenderKitUtils.renderPassThruAttributes(context, writer, component, ATTRIBUTES);
         RenderKitUtils.renderXHTMLStyleBooleanAttributes(writer, component);
         String target = (String)component.getAttributes().get("target");
         if (target != null && target.trim().length() != 0) {
            writer.writeAttribute("target", target, "target");
         }

         this.writeCommonLinkAttributes(writer, component);
         writer.flush();
      }
   }

   static {
      ATTRIBUTES = AttributeManager.getAttributes(AttributeManager.Key.OUTPUTLINK);
   }
}
