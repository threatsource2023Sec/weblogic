package com.sun.faces.renderkit.html_basic;

import com.sun.faces.renderkit.RenderKitUtils;
import java.io.IOException;
import java.util.Iterator;
import java.util.logging.Level;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIMessage;
import javax.faces.component.UIOutput;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

public class MessageRenderer extends HtmlBasicRenderer {
   private OutputMessageRenderer omRenderer = null;

   public MessageRenderer() {
      this.omRenderer = new OutputMessageRenderer();
   }

   public void encodeBegin(FacesContext context, UIComponent component) throws IOException {
      this.rendererParamsNotNull(context, component);
      if (component instanceof UIOutput) {
         this.omRenderer.encodeBegin(context, component);
      }

   }

   public void encodeChildren(FacesContext context, UIComponent component) throws IOException {
      this.rendererParamsNotNull(context, component);
      if (component instanceof UIOutput) {
         this.omRenderer.encodeChildren(context, component);
      }

   }

   public void encodeEnd(FacesContext context, UIComponent component) throws IOException {
      this.rendererParamsNotNull(context, component);
      if (component instanceof UIOutput) {
         this.omRenderer.encodeEnd(context, component);
      } else if (this.shouldEncode(component)) {
         ResponseWriter writer = context.getResponseWriter();

         assert writer != null;

         UIMessage message = (UIMessage)component;
         String clientId = message.getFor();
         if (clientId == null) {
            if (logger.isLoggable(Level.WARNING)) {
               logger.warning("'for' attribute cannot be null");
            }

         } else {
            clientId = this.augmentIdReference(clientId, component);
            Iterator messageIter = this.getMessageIter(context, clientId, component);

            assert messageIter != null;

            if (messageIter.hasNext()) {
               FacesMessage curMessage = (FacesMessage)messageIter.next();
               String severityStyle = null;
               String severityStyleClass = null;
               boolean showSummary = message.isShowSummary();
               boolean showDetail = message.isShowDetail();
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

               String style = (String)component.getAttributes().get("style");
               String styleClass = (String)component.getAttributes().get("styleClass");
               String dir = (String)component.getAttributes().get("dir");
               String lang = (String)component.getAttributes().get("lang");
               String title = (String)component.getAttributes().get("title");
               if (style != null && severityStyle != null) {
                  style = severityStyle;
               } else if (style == null && severityStyle != null) {
                  style = severityStyle;
               }

               if (styleClass != null && severityStyleClass != null) {
                  styleClass = severityStyleClass;
               } else if (styleClass == null && severityStyleClass != null) {
                  styleClass = severityStyleClass;
               }

               boolean wroteSpan = false;
               if (styleClass != null || style != null || dir != null || lang != null || title != null || this.shouldWriteIdAttribute(component)) {
                  writer.startElement("span", component);
                  this.writeIdAttributeIfNecessary(context, writer, component);
                  wroteSpan = true;
                  if (style != null) {
                     writer.writeAttribute("style", style, "style");
                  }

                  if (styleClass != null) {
                     writer.writeAttribute("class", styleClass, "styleClass");
                  }

                  if (dir != null) {
                     writer.writeAttribute("dir", dir, "dir");
                  }

                  if (lang != null) {
                     writer.writeAttribute(RenderKitUtils.prefixAttribute("lang", writer), lang, "lang");
                  }

                  if (title != null) {
                     writer.writeAttribute("title", title, "title");
                  }
               }

               Object val = component.getAttributes().get("tooltip");
               boolean isTooltip = val != null && Boolean.valueOf(val.toString());
               boolean wroteTooltip = false;
               if (showSummary && showDetail && isTooltip) {
                  if (!wroteSpan) {
                     writer.startElement("span", component);
                  }

                  if (title == null || title.length() == 0) {
                     writer.writeAttribute("title", summary, "title");
                  }

                  writer.flush();
                  writer.writeText("\t", component, (String)null);
                  wroteTooltip = true;
               } else if (wroteSpan) {
                  writer.flush();
               }

               if (!wroteTooltip && showSummary) {
                  writer.writeText("\t", component, (String)null);
                  writer.writeText(summary, component, (String)null);
                  writer.writeText(" ", component, (String)null);
               }

               if (showDetail) {
                  writer.writeText(detail, component, (String)null);
               }

               if (wroteSpan || wroteTooltip) {
                  writer.endElement("span");
               }

            }
         }
      }
   }
}
