package com.sun.faces.renderkit.html_basic;

import com.sun.faces.renderkit.RenderKitUtils;
import java.io.IOException;
import java.text.FieldPosition;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import javax.faces.component.UIComponent;
import javax.faces.component.UIParameter;
import javax.faces.component.ValueHolder;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

public class OutputMessageRenderer extends HtmlBasicRenderer {
   public void encodeBegin(FacesContext context, UIComponent component) throws IOException {
      this.rendererParamsNotNull(context, component);
   }

   public void encodeEnd(FacesContext context, UIComponent component) throws IOException {
      this.rendererParamsNotNull(context, component);
      if (this.shouldEncode(component)) {
         Object currentObj = ((ValueHolder)component).getValue();
         if (currentObj != null) {
            String currentValue = currentObj.toString();
            int childCount = component.getChildCount();
            Object parameterList;
            if (childCount > 0) {
               parameterList = new ArrayList(childCount);
               Iterator i$ = component.getChildren().iterator();

               while(i$.hasNext()) {
                  UIComponent kid = (UIComponent)i$.next();
                  if (kid instanceof UIParameter) {
                     ((List)parameterList).add(((UIParameter)kid).getValue());
                  }
               }
            } else {
               parameterList = Collections.emptyList();
            }

            String message;
            if (((List)parameterList).size() > 0) {
               MessageFormat fmt = new MessageFormat(currentValue, context.getViewRoot().getLocale());
               StringBuffer buf = new StringBuffer(currentValue.length() * 2);
               fmt.format(((List)parameterList).toArray(new Object[((List)parameterList).size()]), buf, (FieldPosition)null);
               message = buf.toString();
            } else {
               message = currentValue;
            }

            ResponseWriter writer = context.getResponseWriter();

            assert writer != null;

            String style = (String)component.getAttributes().get("style");
            String styleClass = (String)component.getAttributes().get("styleClass");
            String lang = (String)component.getAttributes().get("lang");
            String dir = (String)component.getAttributes().get("dir");
            String title = (String)component.getAttributes().get("title");
            boolean wroteSpan = false;
            if (styleClass != null || style != null || dir != null || lang != null || title != null || this.shouldWriteIdAttribute(component)) {
               writer.startElement("span", component);
               this.writeIdAttributeIfNecessary(context, writer, component);
               wroteSpan = true;
               if (style != null) {
                  writer.writeAttribute("style", style, "style");
               }

               if (null != styleClass) {
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

            Object val = component.getAttributes().get("escape");
            boolean escape = val != null && Boolean.valueOf(val.toString());
            if (escape) {
               writer.writeText(message, component, "value");
            } else {
               writer.write(message);
            }

            if (wroteSpan) {
               writer.endElement("span");
            }

         }
      }
   }
}
