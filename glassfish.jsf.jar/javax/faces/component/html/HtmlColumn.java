package javax.faces.component.html;

import java.util.ArrayList;
import java.util.List;
import javax.el.ValueExpression;
import javax.faces.component.UIColumn;

public class HtmlColumn extends UIColumn {
   private static final String OPTIMIZED_PACKAGE = "javax.faces.component.";
   public static final String COMPONENT_TYPE = "javax.faces.HtmlColumn";

   public String getFooterClass() {
      return (String)this.getStateHelper().eval(HtmlColumn.PropertyKeys.footerClass);
   }

   public void setFooterClass(String footerClass) {
      this.getStateHelper().put(HtmlColumn.PropertyKeys.footerClass, footerClass);
   }

   public String getHeaderClass() {
      return (String)this.getStateHelper().eval(HtmlColumn.PropertyKeys.headerClass);
   }

   public void setHeaderClass(String headerClass) {
      this.getStateHelper().put(HtmlColumn.PropertyKeys.headerClass, headerClass);
   }

   public boolean isRowHeader() {
      return (Boolean)this.getStateHelper().eval(HtmlColumn.PropertyKeys.rowHeader, false);
   }

   public void setRowHeader(boolean rowHeader) {
      this.getStateHelper().put(HtmlColumn.PropertyKeys.rowHeader, rowHeader);
   }

   public String getStyleClass() {
      return (String)this.getStateHelper().eval(HtmlColumn.PropertyKeys.styleClass);
   }

   public void setStyleClass(String styleClass) {
      this.getStateHelper().put(HtmlColumn.PropertyKeys.styleClass, styleClass);
   }

   private void handleAttribute(String name, Object value) {
      List setAttributes = (List)this.getAttributes().get("javax.faces.component.UIComponentBase.attributesThatAreSet");
      if (setAttributes == null) {
         String cname = this.getClass().getName();
         if (cname != null && cname.startsWith("javax.faces.component.")) {
            setAttributes = new ArrayList(6);
            this.getAttributes().put("javax.faces.component.UIComponentBase.attributesThatAreSet", setAttributes);
         }
      }

      if (setAttributes != null) {
         if (value == null) {
            ValueExpression ve = this.getValueExpression(name);
            if (ve == null) {
               ((List)setAttributes).remove(name);
            }
         } else if (!((List)setAttributes).contains(name)) {
            ((List)setAttributes).add(name);
         }
      }

   }

   protected static enum PropertyKeys {
      footerClass,
      headerClass,
      rowHeader,
      styleClass;

      String toString;

      private PropertyKeys(String toString) {
         this.toString = toString;
      }

      private PropertyKeys() {
      }

      public String toString() {
         return this.toString != null ? this.toString : super.toString();
      }
   }
}
