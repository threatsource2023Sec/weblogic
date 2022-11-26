package com.sun.faces.taglib.html_basic;

import com.sun.faces.util.FacesLogger;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.el.ValueExpression;
import javax.faces.component.UIColumn;
import javax.faces.component.UIComponent;
import javax.faces.webapp.UIComponentELTag;
import javax.servlet.jsp.JspException;

public class ColumnTag extends UIComponentELTag {
   private static final Logger logger;
   private ValueExpression footerClass;
   private ValueExpression headerClass;

   public void setFooterClass(ValueExpression footerClass) {
      this.footerClass = footerClass;
   }

   public void setHeaderClass(ValueExpression headerClass) {
      this.headerClass = headerClass;
   }

   public String getRendererType() {
      return null;
   }

   public String getComponentType() {
      return "javax.faces.Column";
   }

   protected void setProperties(UIComponent component) {
      super.setProperties(component);

      UIColumn column;
      try {
         column = (UIColumn)component;
      } catch (ClassCastException var4) {
         throw new IllegalStateException("Component " + component.toString() + " not expected type.  Expected: UIColumn.  Perhaps you're missing a tag?");
      }

      if (this.footerClass != null) {
         if (!this.footerClass.isLiteralText()) {
            column.setValueExpression("footerClass", this.footerClass);
         } else {
            column.getAttributes().put("footerClass", this.footerClass.getExpressionString());
         }
      }

      if (this.headerClass != null) {
         if (!this.headerClass.isLiteralText()) {
            column.setValueExpression("headerClass", this.headerClass);
         } else {
            column.getAttributes().put("headerClass", this.headerClass.getExpressionString());
         }
      }

   }

   public int doStartTag() throws JspException {
      try {
         return super.doStartTag();
      } catch (JspException var2) {
         if (logger.isLoggable(Level.WARNING)) {
            logger.log(Level.WARNING, this.getDebugString(), var2);
         }

         throw var2;
      } catch (Throwable var3) {
         if (logger.isLoggable(Level.WARNING)) {
            logger.log(Level.WARNING, this.getDebugString(), var3);
         }

         throw new JspException(var3);
      }
   }

   public int doEndTag() throws JspException {
      try {
         return super.doEndTag();
      } catch (JspException var2) {
         if (logger.isLoggable(Level.WARNING)) {
            logger.log(Level.WARNING, this.getDebugString(), var2);
         }

         throw var2;
      } catch (Throwable var3) {
         if (logger.isLoggable(Level.WARNING)) {
            logger.log(Level.WARNING, this.getDebugString(), var3);
         }

         throw new JspException(var3);
      }
   }

   public void release() {
      super.release();
      this.headerClass = null;
      this.footerClass = null;
   }

   public String getDebugString() {
      return "id: " + this.getId() + " class: " + this.getClass().getName();
   }

   static {
      logger = FacesLogger.TAGLIB.getLogger();
   }
}
