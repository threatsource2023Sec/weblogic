package javax.faces.component.html;

import java.util.ArrayList;
import java.util.List;
import javax.el.ValueExpression;
import javax.faces.component.UIColumn;
import javax.faces.context.FacesContext;

public class HtmlColumn extends UIColumn {
   private static final String OPTIMIZED_PACKAGE = "javax.faces.component.";
   public static final String COMPONENT_TYPE = "javax.faces.HtmlColumn";
   private String footerClass;
   private String headerClass;
   private Object[] _values;

   public String getFooterClass() {
      if (null != this.footerClass) {
         return this.footerClass;
      } else {
         ValueExpression _ve = this.getValueExpression("footerClass");
         return _ve != null ? (String)_ve.getValue(this.getFacesContext().getELContext()) : null;
      }
   }

   public void setFooterClass(String footerClass) {
      this.footerClass = footerClass;
   }

   public String getHeaderClass() {
      if (null != this.headerClass) {
         return this.headerClass;
      } else {
         ValueExpression _ve = this.getValueExpression("headerClass");
         return _ve != null ? (String)_ve.getValue(this.getFacesContext().getELContext()) : null;
      }
   }

   public void setHeaderClass(String headerClass) {
      this.headerClass = headerClass;
   }

   public Object saveState(FacesContext _context) {
      if (this._values == null) {
         this._values = new Object[3];
      }

      this._values[0] = super.saveState(_context);
      this._values[1] = this.footerClass;
      this._values[2] = this.headerClass;
      return this._values;
   }

   public void restoreState(FacesContext _context, Object _state) {
      this._values = (Object[])((Object[])_state);
      super.restoreState(_context, this._values[0]);
      this.footerClass = (String)this._values[1];
      this.headerClass = (String)this._values[2];
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
}
