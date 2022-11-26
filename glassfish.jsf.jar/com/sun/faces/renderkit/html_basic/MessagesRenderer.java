package com.sun.faces.renderkit.html_basic;

import com.sun.faces.renderkit.Attribute;
import com.sun.faces.renderkit.AttributeManager;
import com.sun.faces.renderkit.RenderKitUtils;
import java.io.IOException;
import java.util.Iterator;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIMessages;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

public class MessagesRenderer extends HtmlBasicRenderer {
   private static final Attribute[] ATTRIBUTES;

   public void encodeBegin(FacesContext context, UIComponent component) throws IOException {
      this.rendererParamsNotNull(context, component);
   }

   public void encodeEnd(FacesContext context, UIComponent component) throws IOException {
      this.rendererParamsNotNull(context, component);
      if (this.shouldEncode(component)) {
         boolean mustRender = this.shouldWriteIdAttribute(component);
         UIMessages messages = (UIMessages)component;
         ResponseWriter writer = context.getResponseWriter();

         assert writer != null;

         String clientId = ((UIMessages)component).getFor();
         if (clientId == null && messages.isGlobalOnly()) {
            clientId = "";
         }

         Iterator messageIter = this.getMessageIter(context, clientId, component);

         assert messageIter != null;

         if (!messageIter.hasNext()) {
            if (mustRender) {
               if ("javax_faces_developmentstage_messages".equals(component.getId())) {
                  return;
               }

               writer.startElement("div", component);
               this.writeIdAttributeIfNecessary(context, writer, component);
               writer.endElement("div");
            }

         } else {
            String layout = (String)component.getAttributes().get("layout");
            boolean showSummary = messages.isShowSummary();
            boolean showDetail = messages.isShowDetail();
            String styleClass = (String)component.getAttributes().get("styleClass");
            boolean wroteTable = false;
            if (layout != null && layout.equals("table")) {
               writer.startElement("table", component);
               wroteTable = true;
            } else {
               writer.startElement("ul", component);
            }

            this.writeIdAttributeIfNecessary(context, writer, component);
            if (null != styleClass) {
               writer.writeAttribute("class", styleClass, "styleClass");
            }

            RenderKitUtils.renderPassThruAttributes(context, writer, component, ATTRIBUTES);

            while(true) {
               FacesMessage curMessage;
               do {
                  if (!messageIter.hasNext()) {
                     if (wroteTable) {
                        writer.endElement("table");
                     } else {
                        writer.endElement("ul");
                     }

                     return;
                  }

                  curMessage = (FacesMessage)messageIter.next();
               } while(curMessage.isRendered() && !messages.isRedisplay());

               curMessage.rendered();
               String severityStyle = null;
               String severityStyleClass = null;
               String summary = null != (summary = curMessage.getSummary()) ? summary : "";
               String detail = null != (detail = curMessage.getDetail()) ? detail : summary;
               if (curMessage.getSeverity() == FacesMessage.SEVERITY_INFO) {
                  severityStyle = (String)component.getAttributes().get("infoStyle");
                  severityStyleClass = (String)component.getAttributes().get("infoClass");
               } else if (curMessage.getSeverity() == FacesMessage.SEVERITY_WARN) {
                  severityStyle = (String)component.getAttributes().get("warnStyle");
                  severityStyleClass = (String)component.getAttributes().get("warnClass");
               } else if (curMessage.getSeverity() == FacesMessage.SEVERITY_ERROR) {
                  severityStyle = (String)component.getAttributes().get("errorStyle");
                  severityStyleClass = (String)component.getAttributes().get("errorClass");
               } else if (curMessage.getSeverity() == FacesMessage.SEVERITY_FATAL) {
                  severityStyle = (String)component.getAttributes().get("fatalStyle");
                  severityStyleClass = (String)component.getAttributes().get("fatalClass");
               }

               if (wroteTable) {
                  writer.startElement("tr", component);
               } else {
                  writer.startElement("li", component);
               }

               if (severityStyle != null) {
                  writer.writeAttribute("style", severityStyle, "style");
               }

               if (severityStyleClass != null) {
                  writer.writeAttribute("class", severityStyleClass, "styleClass");
               }

               if (wroteTable) {
                  writer.startElement("td", component);
               }

               Object val = component.getAttributes().get("tooltip");
               boolean isTooltip = val != null && Boolean.valueOf(val.toString());
               boolean wroteTooltip = false;
               if (isTooltip) {
                  writer.startElement("span", component);
                  String title = (String)component.getAttributes().get("title");
                  if (title == null || title.length() == 0) {
                     writer.writeAttribute("title", detail, "title");
                  }

                  writer.flush();
                  writer.writeText("\t", component, (String)null);
                  wroteTooltip = true;
               }

               if (showSummary) {
                  writer.writeText("\t", component, (String)null);
                  writer.writeText(summary, component, (String)null);
                  writer.writeText(" ", component, (String)null);
               }

               if (showDetail) {
                  writer.writeText(detail, component, (String)null);
               }

               if (wroteTooltip) {
                  writer.endElement("span");
               }

               if (wroteTable) {
                  writer.endElement("td");
                  writer.endElement("tr");
               } else {
                  writer.endElement("li");
               }
            }
         }
      }
   }

   static {
      ATTRIBUTES = AttributeManager.getAttributes(AttributeManager.Key.MESSAGESMESSAGES);
   }
}
