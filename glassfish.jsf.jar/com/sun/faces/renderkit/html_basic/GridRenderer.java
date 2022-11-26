package com.sun.faces.renderkit.html_basic;

import com.sun.faces.renderkit.Attribute;
import com.sun.faces.renderkit.AttributeManager;
import java.io.IOException;
import java.util.Iterator;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

public class GridRenderer extends BaseTableRenderer {
   private static final Attribute[] ATTRIBUTES;

   public void encodeBegin(FacesContext context, UIComponent component) throws IOException {
      this.rendererParamsNotNull(context, component);
      if (this.shouldEncode(component)) {
         ResponseWriter writer = context.getResponseWriter();
         this.renderTableStart(context, component, writer, ATTRIBUTES);
         this.renderCaption(context, component, writer);
         this.renderHeader(context, component, writer);
         this.renderFooter(context, component, writer);
      }
   }

   public void encodeChildren(FacesContext context, UIComponent component) throws IOException {
      this.rendererParamsNotNull(context, component);
      if (this.shouldEncodeChildren(component)) {
         ResponseWriter writer = context.getResponseWriter();
         BaseTableRenderer.TableMetaInfo info = this.getMetaInfo(context, component);
         int columnCount = info.columns.size();
         boolean open = false;
         int i = 0;
         this.renderTableBodyStart(context, component, writer);
         boolean rowRendered = false;
         Iterator kids = this.getChildren(component);

         while(kids.hasNext()) {
            UIComponent child = (UIComponent)kids.next();
            if (child.isRendered()) {
               if (i % columnCount == 0) {
                  if (open) {
                     this.renderRowEnd(context, component, writer);
                  }

                  this.renderRowStart(context, component, writer);
                  rowRendered = true;
                  open = true;
                  info.newRow();
               }

               this.renderRow(context, component, child, writer);
               ++i;
            }
         }

         if (open) {
            this.renderRowEnd(context, component, writer);
         }

         if (!rowRendered) {
            this.renderEmptyTableRow(writer, component);
         }

         this.renderTableBodyEnd(context, component, writer);
      }
   }

   public void encodeEnd(FacesContext context, UIComponent component) throws IOException {
      this.rendererParamsNotNull(context, component);
      if (this.shouldEncode(component)) {
         this.renderTableEnd(context, component, context.getResponseWriter());
         this.clearMetaInfo(context, component);
      }
   }

   public boolean getRendersChildren() {
      return true;
   }

   protected void renderRow(FacesContext context, UIComponent table, UIComponent child, ResponseWriter writer) throws IOException {
      BaseTableRenderer.TableMetaInfo info = this.getMetaInfo(context, table);
      writer.startElement("td", table);
      String columnClass = info.getCurrentColumnClass();
      if (columnClass != null) {
         writer.writeAttribute("class", columnClass, "columns");
      }

      this.encodeRecursive(context, child);
      writer.endElement("td");
      writer.writeText("\n", table, (String)null);
   }

   protected void renderHeader(FacesContext context, UIComponent table, ResponseWriter writer) throws IOException {
      BaseTableRenderer.TableMetaInfo info = this.getMetaInfo(context, table);
      UIComponent header = this.getFacet(table, "header");
      String headerClass = (String)table.getAttributes().get("headerClass");
      if (header != null) {
         writer.startElement("thead", table);
         writer.writeText("\n", table, (String)null);
         writer.startElement("tr", header);
         writer.startElement("th", header);
         if (headerClass != null) {
            writer.writeAttribute("class", headerClass, "headerClass");
         }

         writer.writeAttribute("colspan", String.valueOf(info.columns.size()), (String)null);
         writer.writeAttribute("scope", "colgroup", (String)null);
         this.encodeRecursive(context, header);
         writer.endElement("th");
         writer.endElement("tr");
         writer.writeText("\n", table, (String)null);
         writer.endElement("thead");
         writer.writeText("\n", table, (String)null);
      }

   }

   protected void renderFooter(FacesContext context, UIComponent table, ResponseWriter writer) throws IOException {
      BaseTableRenderer.TableMetaInfo info = this.getMetaInfo(context, table);
      UIComponent footer = this.getFacet(table, "footer");
      String footerClass = (String)table.getAttributes().get("footerClass");
      if (footer != null) {
         writer.startElement("tfoot", table);
         writer.writeText("\n", table, (String)null);
         writer.startElement("tr", footer);
         writer.startElement("td", footer);
         if (footerClass != null) {
            writer.writeAttribute("class", footerClass, "footerClass");
         }

         writer.writeAttribute("colspan", String.valueOf(info.columns.size()), (String)null);
         this.encodeRecursive(context, footer);
         writer.endElement("td");
         writer.endElement("tr");
         writer.writeText("\n", table, (String)null);
         writer.endElement("tfoot");
         writer.writeText("\n", table, (String)null);
      }

   }

   private void renderEmptyTableRow(ResponseWriter writer, UIComponent component) throws IOException {
      writer.startElement("tr", component);
      writer.startElement("td", component);
      writer.endElement("td");
      writer.endElement("tr");
   }

   static {
      ATTRIBUTES = AttributeManager.getAttributes(AttributeManager.Key.PANELGRID);
   }
}
