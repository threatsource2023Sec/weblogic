package com.sun.faces.renderkit.html_basic;

import com.sun.faces.renderkit.RenderKitUtils;
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

public abstract class BaseTableRenderer extends HtmlBasicRenderer {
   protected abstract void renderHeader(FacesContext var1, UIComponent var2, ResponseWriter var3) throws IOException;

   protected abstract void renderFooter(FacesContext var1, UIComponent var2, ResponseWriter var3) throws IOException;

   protected abstract void renderRow(FacesContext var1, UIComponent var2, UIComponent var3, ResponseWriter var4) throws IOException;

   protected void renderTableStart(FacesContext context, UIComponent table, ResponseWriter writer, String[] passThroughAttributes) throws IOException {
      writer.startElement("table", table);
      this.writeIdAttributeIfNecessary(context, writer, table);
      String styleClass = (String)table.getAttributes().get("styleClass");
      if (styleClass != null) {
         writer.writeAttribute("class", styleClass, "styleClass");
      }

      RenderKitUtils.renderPassThruAttributes(writer, table, passThroughAttributes);
      writer.writeText("\n", table, (String)null);
   }

   protected void renderTableEnd(FacesContext context, UIComponent table, ResponseWriter writer) throws IOException {
      writer.endElement("table");
      writer.writeText("\n", table, (String)null);
   }

   protected void renderCaption(FacesContext context, UIComponent table, ResponseWriter writer) throws IOException {
      UIComponent caption = this.getFacet(table, "caption");
      if (caption != null) {
         String captionClass = (String)table.getAttributes().get("captionClass");
         String captionStyle = (String)table.getAttributes().get("captionStyle");
         writer.startElement("caption", table);
         if (captionClass != null) {
            writer.writeAttribute("class", captionClass, "captionClass");
         }

         if (captionStyle != null) {
            writer.writeAttribute("style", captionStyle, "captionStyle");
         }

         this.encodeRecursive(context, caption);
         writer.endElement("caption");
      }

   }

   protected void renderTableBodyStart(FacesContext context, UIComponent table, ResponseWriter writer) throws IOException {
      writer.startElement("tbody", table);
      writer.writeText("\n", table, (String)null);
   }

   protected void renderTableBodyEnd(FacesContext context, UIComponent table, ResponseWriter writer) throws IOException {
      writer.endElement("tbody");
      writer.writeText("\n", table, (String)null);
   }

   protected void renderRowStart(FacesContext context, UIComponent table, ResponseWriter writer) throws IOException {
      TableMetaInfo info = this.getMetaInfo(context, table);
      writer.startElement("tr", table);
      if (info.rowClasses.length > 0) {
         writer.writeAttribute("class", info.getCurrentRowClass(), "rowClasses");
      }

      writer.writeText("\n", table, (String)null);
   }

   protected void renderRowEnd(FacesContext context, UIComponent table, ResponseWriter writer) throws IOException {
      writer.endElement("tr");
      writer.writeText("\n", table, (String)null);
   }

   protected TableMetaInfo getMetaInfo(FacesContext context, UIComponent table) {
      String key = this.createKey(table);
      Map reqMap = context.getExternalContext().getRequestMap();
      TableMetaInfo info = (TableMetaInfo)reqMap.get(key);
      if (info == null) {
         info = new TableMetaInfo(table);
         reqMap.put(key, info);
      }

      return info;
   }

   protected void clearMetaInfo(FacesContext context, UIComponent table) {
      context.getExternalContext().getRequestMap().remove(this.createKey(table));
   }

   protected String createKey(UIComponent table) {
      return BaseTableRenderer.TableMetaInfo.KEY + table.hashCode();
   }

   protected static class TableMetaInfo {
      private static final UIColumn PLACE_HOLDER_COLUMN = new UIColumn();
      private static final String[] EMPTY_STRING_ARRAY = new String[0];
      public static final String KEY = TableMetaInfo.class.getName();
      public final String[] rowClasses;
      public final String[] columnClasses;
      public final List columns;
      public final boolean hasHeaderFacets;
      public final boolean hasFooterFacets;
      public final int columnCount;
      public int columnStyleCounter;
      public int rowStyleCounter;

      public TableMetaInfo(UIComponent table) {
         this.rowClasses = getRowClasses(table);
         this.columnClasses = getColumnClasses(table);
         this.columns = getColumns(table);
         this.columnCount = this.columns.size();
         this.hasHeaderFacets = hasFacet("header", this.columns);
         this.hasFooterFacets = hasFacet("footer", this.columns);
      }

      public void newRow() {
         this.columnStyleCounter = 0;
      }

      public String getCurrentColumnClass() {
         String style = null;
         if (this.columnStyleCounter < this.columnClasses.length && this.columnStyleCounter <= this.columnCount) {
            style = this.columnClasses[this.columnStyleCounter++];
         }

         return style != null && style.length() > 0 ? style : null;
      }

      public String getCurrentRowClass() {
         String style = this.rowClasses[this.rowStyleCounter++];
         if (this.rowStyleCounter >= this.rowClasses.length) {
            this.rowStyleCounter = 0;
         }

         return style;
      }

      private static String[] getColumnClasses(UIComponent table) {
         String values = (String)table.getAttributes().get("columnClasses");
         return values == null ? EMPTY_STRING_ARRAY : Util.split(values.trim(), ",");
      }

      private static List getColumns(UIComponent table) {
         int count;
         if (table instanceof UIData) {
            count = table.getChildCount();
            if (count > 0) {
               List results = new ArrayList(count);
               Iterator i$ = table.getChildren().iterator();

               while(i$.hasNext()) {
                  UIComponent kid = (UIComponent)i$.next();
                  if (kid instanceof UIColumn && kid.isRendered()) {
                     results.add((UIColumn)kid);
                  }
               }

               return results;
            } else {
               return Collections.emptyList();
            }
         } else {
            Object value = table.getAttributes().get("columns");
            if (value != null && value instanceof Integer) {
               count = (Integer)value;
            } else {
               count = 2;
            }

            if (count < 1) {
               count = 1;
            }

            List result = new ArrayList(count);

            for(int i = 0; i < count; ++i) {
               result.add(PLACE_HOLDER_COLUMN);
            }

            return result;
         }
      }

      private static boolean hasFacet(String name, List columns) {
         if (!columns.isEmpty()) {
            Iterator i$ = columns.iterator();

            while(i$.hasNext()) {
               UIColumn column = (UIColumn)i$.next();
               if (column.getFacetCount() > 0 && column.getFacets().containsKey(name)) {
                  return true;
               }
            }
         }

         return false;
      }

      private static String[] getRowClasses(UIComponent table) {
         String values = (String)table.getAttributes().get("rowClasses");
         return values == null ? EMPTY_STRING_ARRAY : Util.split(values.trim(), ",");
      }
   }
}
