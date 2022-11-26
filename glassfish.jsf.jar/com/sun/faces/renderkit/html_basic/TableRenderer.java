package com.sun.faces.renderkit.html_basic;

import com.sun.faces.renderkit.Attribute;
import com.sun.faces.renderkit.AttributeManager;
import com.sun.faces.util.Util;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.faces.component.UIColumn;
import javax.faces.component.UIComponent;
import javax.faces.component.UIData;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

public class TableRenderer extends BaseTableRenderer {
   private static final Attribute[] ATTRIBUTES;

   public void encodeBegin(FacesContext context, UIComponent component) throws IOException {
      this.rendererParamsNotNull(context, component);
      if (this.shouldEncode(component)) {
         UIData data = (UIData)component;
         data.setRowIndex(-1);
         ResponseWriter writer = context.getResponseWriter();
         this.renderTableStart(context, component, writer, ATTRIBUTES);
         this.renderCaption(context, data, writer);
         this.renderColumnGroups(context, data);
         this.renderHeader(context, component, writer);
         this.renderFooter(context, component, writer);
      }
   }

   public void encodeChildren(FacesContext context, UIComponent component) throws IOException {
      this.rendererParamsNotNull(context, component);
      if (this.shouldEncodeChildren(component)) {
         UIData data = (UIData)component;
         ResponseWriter writer = context.getResponseWriter();
         BaseTableRenderer.TableMetaInfo info = this.getMetaInfo(context, data);
         if (info.columns.isEmpty()) {
            this.renderEmptyTableBody(writer, data);
         } else {
            int processed = 0;
            int rowIndex = data.getFirst() - 1;
            int rows = data.getRows();
            List bodyRows = this.getBodyRows(context.getExternalContext().getApplicationMap(), data);
            boolean hasBodyRows = bodyRows != null && !bodyRows.isEmpty();
            boolean wroteTableBody = false;
            if (!hasBodyRows) {
               this.renderTableBodyStart(context, component, writer);
            }

            boolean renderedRow = false;

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

               if (hasBodyRows && bodyRows.contains(data.getRowIndex())) {
                  if (wroteTableBody) {
                     writer.endElement("tbody");
                  }

                  writer.startElement("tbody", data);
                  wroteTableBody = true;
               }

               this.renderRowStart(context, component, writer);
               this.renderRow(context, component, (UIComponent)null, writer);
               this.renderRowEnd(context, component, writer);
               renderedRow = true;
            }

            if (!renderedRow) {
               this.renderEmptyTableRow(writer, data);
            }

            this.renderTableBodyEnd(context, component, writer);
            data.setRowIndex(-1);
         }
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

   private List getBodyRows(Map appMap, UIData data) {
      List result = null;
      String bodyRows = (String)data.getAttributes().get("bodyrows");
      if (bodyRows != null) {
         String[] rows = Util.split(appMap, bodyRows, ",");
         if (rows != null) {
            result = new ArrayList(rows.length);
            String[] var6 = rows;
            int var7 = rows.length;

            for(int var8 = 0; var8 < var7; ++var8) {
               String curRow = var6[var8];
               result.add(Integer.valueOf(curRow));
            }
         }
      }

      return result;
   }

   protected void renderColumnGroups(FacesContext context, UIComponent table) throws IOException {
      UIComponent colGroups = this.getFacet(table, "colgroups");
      if (colGroups != null) {
         this.encodeRecursive(context, colGroups);
      }

   }

   protected void renderFooter(FacesContext context, UIComponent table, ResponseWriter writer) throws IOException {
      BaseTableRenderer.TableMetaInfo info = this.getMetaInfo(context, table);
      UIComponent footer = this.getFacet(table, "footer");
      if (footer != null || info.hasFooterFacets) {
         String footerClass = (String)table.getAttributes().get("footerClass");
         writer.startElement("tfoot", table);
         writer.writeText("\n", table, (String)null);
         if (info.hasFooterFacets) {
            writer.startElement("tr", table);
            writer.writeText("\n", table, (String)null);
            Iterator var7 = info.columns.iterator();

            while(var7.hasNext()) {
               UIColumn column = (UIColumn)var7.next();
               String columnFooterClass = (String)column.getAttributes().get("footerClass");
               writer.startElement("td", column);
               if (columnFooterClass != null) {
                  writer.writeAttribute("class", columnFooterClass, "columnFooterClass");
               } else if (footerClass != null) {
                  writer.writeAttribute("class", footerClass, "footerClass");
               }

               UIComponent facet = this.getFacet(column, "footer");
               if (facet != null) {
                  writer.writeText("", table, (String)null);
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

            if (info.columns.size() > 1) {
               writer.writeAttribute("colspan", String.valueOf(info.columns.size()), (String)null);
            }

            this.encodeRecursive(context, footer);
            writer.endElement("td");
            this.renderRowEnd(context, table, writer);
         }

         writer.endElement("tfoot");
         writer.writeText("\n", table, (String)null);
      }
   }

   protected void renderHeader(FacesContext context, UIComponent table, ResponseWriter writer) throws IOException {
      BaseTableRenderer.TableMetaInfo info = this.getMetaInfo(context, table);
      UIComponent header = this.getFacet(table, "header");
      if (header != null || info.hasHeaderFacets) {
         String headerClass = (String)table.getAttributes().get("headerClass");
         writer.startElement("thead", table);
         writer.writeText("\n", table, (String)null);
         if (header != null) {
            writer.startElement("tr", header);
            writer.startElement("th", header);
            if (headerClass != null) {
               writer.writeAttribute("class", headerClass, "headerClass");
            }

            if (info.columns.size() > 1) {
               writer.writeAttribute("colspan", String.valueOf(info.columns.size()), (String)null);
            }

            writer.writeAttribute("scope", "colgroup", (String)null);
            this.encodeRecursive(context, header);
            writer.endElement("th");
            this.renderRowEnd(context, table, writer);
         }

         if (info.hasHeaderFacets) {
            writer.startElement("tr", table);
            writer.writeText("\n", table, (String)null);
            Iterator var7 = info.columns.iterator();

            while(var7.hasNext()) {
               UIColumn column = (UIColumn)var7.next();
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

         writer.endElement("thead");
         writer.writeText("\n", table, (String)null);
      }
   }

   protected void renderRow(FacesContext context, UIComponent table, UIComponent child, ResponseWriter writer) throws IOException {
      BaseTableRenderer.TableMetaInfo info = this.getMetaInfo(context, table);
      info.newRow();

      for(Iterator var6 = info.columns.iterator(); var6.hasNext(); writer.writeText("\n", table, (String)null)) {
         UIColumn column = (UIColumn)var6.next();
         boolean isRowHeader = false;
         Object rowHeaderValue = column.getAttributes().get("rowHeader");
         if (null != rowHeaderValue) {
            isRowHeader = Boolean.valueOf(rowHeaderValue.toString());
         }

         if (isRowHeader) {
            writer.startElement("th", column);
            writer.writeAttribute("scope", "row", (String)null);
         } else {
            writer.startElement("td", column);
         }

         String tableColumnStyleClass = info.getCurrentColumnClass();
         String columnStyleClass = (String)column.getAttributes().get("styleClass");
         if (tableColumnStyleClass != null) {
            if (columnStyleClass != null) {
               throw new IOException("Cannot define both columnClasses on a table and styleClass on a column");
            }

            writer.writeAttribute("class", tableColumnStyleClass, "columnClasses");
         }

         if (columnStyleClass != null) {
            if (tableColumnStyleClass != null) {
               throw new IOException("Cannot define both columnClasses on a table and styleClass on a column");
            }

            writer.writeAttribute("class", columnStyleClass, "styleClass");
         }

         Iterator gkids = this.getChildren(column);

         while(gkids.hasNext()) {
            this.encodeRecursive(context, (UIComponent)gkids.next());
         }

         if (isRowHeader) {
            writer.endElement("th");
         } else {
            writer.endElement("td");
         }
      }

   }

   private void renderEmptyTableBody(ResponseWriter writer, UIComponent component) throws IOException {
      writer.startElement("tbody", component);
      this.renderEmptyTableRow(writer, component);
      writer.endElement("tbody");
   }

   private void renderEmptyTableRow(ResponseWriter writer, UIComponent component) throws IOException {
      writer.startElement("tr", component);
      List columns = this.getColumns(component);
      Iterator var4 = columns.iterator();

      while(var4.hasNext()) {
         UIColumn column = (UIColumn)var4.next();
         if (column.isRendered()) {
            writer.startElement("td", component);
            writer.endElement("td");
         }
      }

      writer.endElement("tr");
   }

   private List getColumns(UIComponent table) {
      int childCount = table.getChildCount();
      if (childCount > 0) {
         List results = new ArrayList(childCount);
         Iterator var4 = table.getChildren().iterator();

         while(var4.hasNext()) {
            UIComponent kid = (UIComponent)var4.next();
            if (kid instanceof UIColumn && kid.isRendered()) {
               results.add((UIColumn)kid);
            }
         }

         return results;
      } else {
         return Collections.emptyList();
      }
   }

   static {
      ATTRIBUTES = AttributeManager.getAttributes(AttributeManager.Key.DATATABLE);
   }
}
