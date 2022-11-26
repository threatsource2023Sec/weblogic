package javax.faces.component.html;

import java.util.ArrayList;
import java.util.List;
import javax.el.ValueExpression;
import javax.faces.component.UIOutput;
import javax.faces.context.FacesContext;

public class HtmlOutputText extends UIOutput {
   private static final String OPTIMIZED_PACKAGE = "javax.faces.component.";
   public static final String COMPONENT_TYPE = "javax.faces.HtmlOutputText";
   private String dir;
   private Boolean escape;
   private String lang;
   private String style;
   private String styleClass;
   private String title;
   private Object[] _values;

   public HtmlOutputText() {
      this.setRendererType("javax.faces.Text");
   }

   public String getDir() {
      if (null != this.dir) {
         return this.dir;
      } else {
         ValueExpression _ve = this.getValueExpression("dir");
         return _ve != null ? (String)_ve.getValue(this.getFacesContext().getELContext()) : null;
      }
   }

   public void setDir(String dir) {
      this.dir = dir;
      this.handleAttribute("dir", dir);
   }

   public boolean isEscape() {
      if (null != this.escape) {
         return this.escape;
      } else {
         ValueExpression _ve = this.getValueExpression("escape");
         return _ve != null ? (Boolean)_ve.getValue(this.getFacesContext().getELContext()) : true;
      }
   }

   public void setEscape(boolean escape) {
      this.escape = escape;
   }

   public String getLang() {
      if (null != this.lang) {
         return this.lang;
      } else {
         ValueExpression _ve = this.getValueExpression("lang");
         return _ve != null ? (String)_ve.getValue(this.getFacesContext().getELContext()) : null;
      }
   }

   public void setLang(String lang) {
      this.lang = lang;
      this.handleAttribute("lang", lang);
   }

   public String getStyle() {
      if (null != this.style) {
         return this.style;
      } else {
         ValueExpression _ve = this.getValueExpression("style");
         return _ve != null ? (String)_ve.getValue(this.getFacesContext().getELContext()) : null;
      }
   }

   public void setStyle(String style) {
      this.style = style;
      this.handleAttribute("style", style);
   }

   public String getStyleClass() {
      if (null != this.styleClass) {
         return this.styleClass;
      } else {
         ValueExpression _ve = this.getValueExpression("styleClass");
         return _ve != null ? (String)_ve.getValue(this.getFacesContext().getELContext()) : null;
      }
   }

   public void setStyleClass(String styleClass) {
      this.styleClass = styleClass;
   }

   public String getTitle() {
      if (null != this.title) {
         return this.title;
      } else {
         ValueExpression _ve = this.getValueExpression("title");
         return _ve != null ? (String)_ve.getValue(this.getFacesContext().getELContext()) : null;
      }
   }

   public void setTitle(String title) {
      this.title = title;
      this.handleAttribute("title", title);
   }

   public Object saveState(FacesContext _context) {
      if (this._values == null) {
         this._values = new Object[7];
      }

      this._values[0] = super.saveState(_context);
      this._values[1] = this.dir;
      this._values[2] = this.escape;
      this._values[3] = this.lang;
      this._values[4] = this.style;
      this._values[5] = this.styleClass;
      this._values[6] = this.title;
      return this._values;
   }

   public void restoreState(FacesContext _context, Object _state) {
      this._values = (Object[])((Object[])_state);
      super.restoreState(_context, this._values[0]);
      this.dir = (String)this._values[1];
      this.escape = (Boolean)this._values[2];
      this.lang = (String)this._values[3];
      this.style = (String)this._values[4];
      this.styleClass = (String)this._values[5];
      this.title = (String)this._values[6];
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
