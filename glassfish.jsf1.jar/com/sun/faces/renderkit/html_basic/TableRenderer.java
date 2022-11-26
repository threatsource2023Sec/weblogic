package com.sun.faces.renderkit.html_basic;

import com.sun.faces.renderkit.AttributeManager;
import java.io.IOException;
import java.util.Iterator;
import javax.faces.component.UIColumn;
import javax.faces.component.UIComponent;
import javax.faces.component.UIData;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

public class TableRenderer extends BaseTableRenderer {
   private static final String[] ATTRIBUTES;

   public void encodeBegin(FacesContext context, UIComponent component) throws IOException {
      this.rendererParamsNotNull(context, component);
      if (this.shouldEncode(component)) {
         UIData data = (UIData)component;
         data.setRowIndex(-1);
         ResponseWriter writer = context.getResponseWriter();
         this.renderTableStart(context, component, writer, ATTRIBUTES);
         this.renderCaption(context, data, writer);
         this.renderHeader(context, component, writer);
         this.renderFooter(context, component, writer);
      }
   }

   public void encodeChildren(FacesContext context, UIComponent component) throws IOException {
      this.rendererParamsNotNull(context, component);
      if (this.shouldEncodeChildren(component)) {
         UIData data = (UIData)component;
         ResponseWriter writer = context.getResponseWriter();
         int processed = 0;
         int rowIndex = data.getFirst() - 1;
         int rows = data.getRows();
         this.renderTableBodyStart(context, component, writer);

         while(true) {
            if (rows > 0) {
               ++processed;
               if (processed > rows) {
                  break;
               }
            }

            ++rowIndex;
            data.setRowIndex(rowIndex);
            if (!data.isRowAvailable()) {
               break;
            }

            this.renderRowStart(context, component, writer);
            this.renderRow(context, component, (UIComponent)null, writer);
            this.renderRowEnd(context, component, writer);
         }

         this.renderTableBodyEnd(context, component, writer);
         data.setRowIndex(-1);
      }
   }

   public void encodeEnd(FacesContext context, UIComponent component) throws IOException {
      this.rendererParamsNotNull(context, component);
      if (this.shouldEncode(component)) {
         this.clearMetaInfo(context, component);
         ((UIData)component).setRowIndex(-1);
         this.renderTableEnd(context, component, context.getResponseWriter());
      }
   }

   public boolean getRendersChildren() {
      return true;
   }

   protected void renderFooter(FacesContext context, UIComponent table, ResponseWriter writer) throws IOException {
      BaseTableRenderer.TableMetaInfo info = this.getMetaInfo(context, table);
      UIComponent footer = this.getFacet(table, "footer");
      String footerClass = (String)table.getAttributes().get("footerClass");
      if (footer != null || info.hasFooterFacets) {
         writer.startElement("tfoot", table);
         writer.writeText("\n", table, (String)null);
      }

      if (info.hasFooterFacets) {
         writer.startElement("tr", table);
         writer.writeText("\n", table, (String)null);
         Iterator i$ = info.columns.iterator();

         while(i$.hasNext()) {
            UIColumn column = (UIColumn)i$.next();
            String columnFooterClass = (String)column.getAttributes().get("footerClass");
            writer.startElement("td", column);
            if (columnFooterClass != null) {
               writer.writeAttribute("class", columnFooterClass, "columnFooterClass");
            } else if (footerClass != null) {
               writer.writeAttribute("class", footerClass, "footerClass");
            }

            UIComponent facet = this.getFacet(column, "footer");
            if (facet != null) {
               this.encodeRecursive(context, facet);
            }

            writer.endElement("td");
            writer.writeText("\n", table, (String)null);
         }

         this.renderRowEnd(context, table, writer);
      }

      if (footer != null) {
         writer.startElement("tr", footer);
         writer.startElement("td", footer);
         if (footerClass != null) {
            writer.writeAttribute("class", footerClass, "footerClass");
         }

         writer.writeAttribute("colspan", String.valueOf(info.columns.size()), (String)null);
         this.encodeRecursive(context, footer);
         writer.endElement("td");
         this.renderRowEnd(context, table, writer);
      }

      if (footer != null || info.hasFooterFacets) {
         writer.endElement("tfoot");
         writer.writeText("\n", table, (String)null);
      }

   }

   protected void renderHeader(FacesContext context, UIComponent table, ResponseWriter writer) throws IOException {
      BaseTableRenderer.TableMetaInfo info = this.getMetaInfo(context, table);
      UIComponent header = this.getFacet(table, "header");
      String headerClass = (String)table.getAttributes().get("headerClass");
      if (header != null || info.hasHeaderFacets) {
         writer.startElement("thead", table);
         writer.writeText("\n", table, (String)null);
      }

      if (header != null) {
         writer.startElement("tr", header);
         writer.startElement("th", header);
         if (headerClass != null) {
            writer.writeAttribute("class", headerClass, "headerClass");
         }

         writer.writeAttribute("colspan", String.valueOf(info.columns.size()), (String)null);
         writer.writeAttribute("scope", "colgroup", (String)null);
         this.encodeRecursive(context, header);
         writer.endElement("th");
         this.renderRowEnd(context, table, writer);
      }

      if (info.hasHeaderFacets) {
         writer.startElement("tr", table);
         writer.writeText("\n", table, (String)null);
         Iterator i$ = info.columns.iterator();

         while(i$.hasNext()) {
            UIColumn column = (UIColumn)i$.next();
            String columnHeaderClass = (String)column.getAttributes().get("headerClass");
            writer.startElement("th", column);
            if (columnHeaderClass != null) {
               writer.writeAttribute("class", columnHeaderClass, "columnHeaderClass");
            } else if (headerClass != null) {
               writer.writeAttribute("class", headerClass, "headerClass");
            }

            writer.writeAttribute("scope", "col", (String)null);
            UIComponent facet = this.getFacet(column, "header");
            if (facet != null) {
               this.encodeRecursive(context, facet);
            }

            writer.endElement("th");
            writer.writeText("\n", table, (String)null);
         }

         this.renderRowEnd(context, table, writer);
      }

      if (header != null || info.hasHeaderFacets) {
         writer.endElement("thead");
         writer.writeText("\n", table, (String)null);
      }

   }

   protected void renderRow(FacesContext context, UIComponent table, UIComponent child, ResponseWriter writer) throws IOException {
      BaseTableRenderer.TableMetaInfo info = this.getMetaInfo(context, table);
      info.newRow();
      Iterator i$ = info.columns.iterator();

      while(i$.hasNext()) {
         UIColumn column = (UIColumn)i$.next();
         writer.startElement("td", column);
         String columnClass = info.getCurrentColumnClass();
         if (columnClass != null) {
            writer.writeAttribute("class", columnClass, "columnClasses");
         }

         Iterator gkids = this.getChildren(column);

         while(gkids.hasNext()) {
            this.encodeRecursive(context, (UIComponent)gkids.next());
         }

         writer.endElement("td");
         writer.writeText("\n", table, (String)null);
      }

   }

   static {
      ATTRIBUTES = AttributeManager.getAttributes(AttributeManager.Key.DATATABLE);
   }
}
