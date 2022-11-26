package javax.faces.component.html;

import java.util.ArrayList;
import java.util.List;
import javax.el.ValueExpression;
import javax.faces.component.UIMessages;
import javax.faces.context.FacesContext;

public class HtmlMessages extends UIMessages {
   private static final String OPTIMIZED_PACKAGE = "javax.faces.component.";
   public static final String COMPONENT_TYPE = "javax.faces.HtmlMessages";
   private String dir;
   private String errorClass;
   private String errorStyle;
   private String fatalClass;
   private String fatalStyle;
   private String infoClass;
   private String infoStyle;
   private String lang;
   private String layout;
   private String style;
   private String styleClass;
   private String title;
   private Boolean tooltip;
   private String warnClass;
   private String warnStyle;
   private Object[] _values;

   public HtmlMessages() {
      this.setRendererType("javax.faces.Messages");
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

   public String getErrorClass() {
      if (null != this.errorClass) {
         return this.errorClass;
      } else {
         ValueExpression _ve = this.getValueExpression("errorClass");
         return _ve != null ? (String)_ve.getValue(this.getFacesContext().getELContext()) : null;
      }
   }

   public void setErrorClass(String errorClass) {
      this.errorClass = errorClass;
   }

   public String getErrorStyle() {
      if (null != this.errorStyle) {
         return this.errorStyle;
      } else {
         ValueExpression _ve = this.getValueExpression("errorStyle");
         return _ve != null ? (String)_ve.getValue(this.getFacesContext().getELContext()) : null;
      }
   }

   public void setErrorStyle(String errorStyle) {
      this.errorStyle = errorStyle;
   }

   public String getFatalClass() {
      if (null != this.fatalClass) {
         return this.fatalClass;
      } else {
         ValueExpression _ve = this.getValueExpression("fatalClass");
         return _ve != null ? (String)_ve.getValue(this.getFacesContext().getELContext()) : null;
      }
   }

   public void setFatalClass(String fatalClass) {
      this.fatalClass = fatalClass;
   }

   public String getFatalStyle() {
      if (null != this.fatalStyle) {
         return this.fatalStyle;
      } else {
         ValueExpression _ve = this.getValueExpression("fatalStyle");
         return _ve != null ? (String)_ve.getValue(this.getFacesContext().getELContext()) : null;
      }
   }

   public void setFatalStyle(String fatalStyle) {
      this.fatalStyle = fatalStyle;
   }

   public String getInfoClass() {
      if (null != this.infoClass) {
         return this.infoClass;
      } else {
         ValueExpression _ve = this.getValueExpression("infoClass");
         return _ve != null ? (String)_ve.getValue(this.getFacesContext().getELContext()) : null;
      }
   }

   public void setInfoClass(String infoClass) {
      this.infoClass = infoClass;
   }

   public String getInfoStyle() {
      if (null != this.infoStyle) {
         return this.infoStyle;
      } else {
         ValueExpression _ve = this.getValueExpression("infoStyle");
         return _ve != null ? (String)_ve.getValue(this.getFacesContext().getELContext()) : null;
      }
   }

   public void setInfoStyle(String infoStyle) {
      this.infoStyle = infoStyle;
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

   public String getLayout() {
      if (null != this.layout) {
         return this.layout;
      } else {
         ValueExpression _ve = this.getValueExpression("layout");
         return _ve != null ? (String)_ve.getValue(this.getFacesContext().getELContext()) : "list";
      }
   }

   public void setLayout(String layout) {
      this.layout = layout;
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

   public boolean isTooltip() {
      if (null != this.tooltip) {
         return this.tooltip;
      } else {
         ValueExpression _ve = this.getValueExpression("tooltip");
         return _ve != null ? (Boolean)_ve.getValue(this.getFacesContext().getELContext()) : false;
      }
   }

   public void setTooltip(boolean tooltip) {
      this.tooltip = tooltip;
   }

   public String getWarnClass() {
      if (null != this.warnClass) {
         return this.warnClass;
      } else {
         ValueExpression _ve = this.getValueExpression("warnClass");
         return _ve != null ? (String)_ve.getValue(this.getFacesContext().getELContext()) : null;
      }
   }

   public void setWarnClass(String warnClass) {
      this.warnClass = warnClass;
   }

   public String getWarnStyle() {
      if (null != this.warnStyle) {
         return this.warnStyle;
      } else {
         ValueExpression _ve = this.getValueExpression("warnStyle");
         return _ve != null ? (String)_ve.getValue(this.getFacesContext().getELContext()) : null;
      }
   }

   public void setWarnStyle(String warnStyle) {
      this.warnStyle = warnStyle;
   }

   public Object saveState(FacesContext _context) {
      if (this._values == null) {
         this._values = new Object[16];
      }

      this._values[0] = super.saveState(_context);
      this._values[1] = this.dir;
      this._values[2] = this.errorClass;
      this._values[3] = this.errorStyle;
      this._values[4] = this.fatalClass;
      this._values[5] = this.fatalStyle;
      this._values[6] = this.infoClass;
      this._values[7] = this.infoStyle;
      this._values[8] = this.lang;
      this._values[9] = this.layout;
      this._values[10] = this.style;
      this._values[11] = this.styleClass;
      this._values[12] = this.title;
      this._values[13] = this.tooltip;
      this._values[14] = this.warnClass;
      this._values[15] = this.warnStyle;
      return this._values;
   }

   public void restoreState(FacesContext _context, Object _state) {
      this._values = (Object[])((Object[])_state);
      super.restoreState(_context, this._values[0]);
      this.dir = (String)this._values[1];
      this.errorClass = (String)this._values[2];
      this.errorStyle = (String)this._values[3];
      this.fatalClass = (String)this._values[4];
      this.fatalStyle = (String)this._values[5];
      this.infoClass = (String)this._values[6];
      this.infoStyle = (String)this._values[7];
      this.lang = (String)this._values[8];
      this.layout = (String)this._values[9];
      this.style = (String)this._values[10];
      this.styleClass = (String)this._values[11];
      this.title = (String)this._values[12];
      this.tooltip = (Boolean)this._values[13];
      this.warnClass = (String)this._values[14];
      this.warnStyle = (String)this._values[15];
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
