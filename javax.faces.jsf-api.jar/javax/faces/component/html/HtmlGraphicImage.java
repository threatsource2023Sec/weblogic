package javax.faces.component.html;

import java.util.ArrayList;
import java.util.List;
import javax.el.ValueExpression;
import javax.faces.component.UIGraphic;
import javax.faces.context.FacesContext;

public class HtmlGraphicImage extends UIGraphic {
   private static final String OPTIMIZED_PACKAGE = "javax.faces.component.";
   public static final String COMPONENT_TYPE = "javax.faces.HtmlGraphicImage";
   private String alt;
   private String dir;
   private String height;
   private Boolean ismap;
   private String lang;
   private String longdesc;
   private String onclick;
   private String ondblclick;
   private String onkeydown;
   private String onkeypress;
   private String onkeyup;
   private String onmousedown;
   private String onmousemove;
   private String onmouseout;
   private String onmouseover;
   private String onmouseup;
   private String style;
   private String styleClass;
   private String title;
   private String usemap;
   private String width;
   private Object[] _values;

   public HtmlGraphicImage() {
      this.setRendererType("javax.faces.Image");
   }

   public String getAlt() {
      if (null != this.alt) {
         return this.alt;
      } else {
         ValueExpression _ve = this.getValueExpression("alt");
         return _ve != null ? (String)_ve.getValue(this.getFacesContext().getELContext()) : null;
      }
   }

   public void setAlt(String alt) {
      this.alt = alt;
      this.handleAttribute("alt", alt);
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

   public String getHeight() {
      if (null != this.height) {
         return this.height;
      } else {
         ValueExpression _ve = this.getValueExpression("height");
         return _ve != null ? (String)_ve.getValue(this.getFacesContext().getELContext()) : null;
      }
   }

   public void setHeight(String height) {
      this.height = height;
      this.handleAttribute("height", height);
   }

   public boolean isIsmap() {
      if (null != this.ismap) {
         return this.ismap;
      } else {
         ValueExpression _ve = this.getValueExpression("ismap");
         return _ve != null ? (Boolean)_ve.getValue(this.getFacesContext().getELContext()) : false;
      }
   }

   public void setIsmap(boolean ismap) {
      this.ismap = ismap;
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

   public String getLongdesc() {
      if (null != this.longdesc) {
         return this.longdesc;
      } else {
         ValueExpression _ve = this.getValueExpression("longdesc");
         return _ve != null ? (String)_ve.getValue(this.getFacesContext().getELContext()) : null;
      }
   }

   public void setLongdesc(String longdesc) {
      this.longdesc = longdesc;
      this.handleAttribute("longdesc", longdesc);
   }

   public String getOnclick() {
      if (null != this.onclick) {
         return this.onclick;
      } else {
         ValueExpression _ve = this.getValueExpression("onclick");
         return _ve != null ? (String)_ve.getValue(this.getFacesContext().getELContext()) : null;
      }
   }

   public void setOnclick(String onclick) {
      this.onclick = onclick;
      this.handleAttribute("onclick", onclick);
   }

   public String getOndblclick() {
      if (null != this.ondblclick) {
         return this.ondblclick;
      } else {
         ValueExpression _ve = this.getValueExpression("ondblclick");
         return _ve != null ? (String)_ve.getValue(this.getFacesContext().getELContext()) : null;
      }
   }

   public void setOndblclick(String ondblclick) {
      this.ondblclick = ondblclick;
      this.handleAttribute("ondblclick", ondblclick);
   }

   public String getOnkeydown() {
      if (null != this.onkeydown) {
         return this.onkeydown;
      } else {
         ValueExpression _ve = this.getValueExpression("onkeydown");
         return _ve != null ? (String)_ve.getValue(this.getFacesContext().getELContext()) : null;
      }
   }

   public void setOnkeydown(String onkeydown) {
      this.onkeydown = onkeydown;
      this.handleAttribute("onkeydown", onkeydown);
   }

   public String getOnkeypress() {
      if (null != this.onkeypress) {
         return this.onkeypress;
      } else {
         ValueExpression _ve = this.getValueExpression("onkeypress");
         return _ve != null ? (String)_ve.getValue(this.getFacesContext().getELContext()) : null;
      }
   }

   public void setOnkeypress(String onkeypress) {
      this.onkeypress = onkeypress;
      this.handleAttribute("onkeypress", onkeypress);
   }

   public String getOnkeyup() {
      if (null != this.onkeyup) {
         return this.onkeyup;
      } else {
         ValueExpression _ve = this.getValueExpression("onkeyup");
         return _ve != null ? (String)_ve.getValue(this.getFacesContext().getELContext()) : null;
      }
   }

   public void setOnkeyup(String onkeyup) {
      this.onkeyup = onkeyup;
      this.handleAttribute("onkeyup", onkeyup);
   }

   public String getOnmousedown() {
      if (null != this.onmousedown) {
         return this.onmousedown;
      } else {
         ValueExpression _ve = this.getValueExpression("onmousedown");
         return _ve != null ? (String)_ve.getValue(this.getFacesContext().getELContext()) : null;
      }
   }

   public void setOnmousedown(String onmousedown) {
      this.onmousedown = onmousedown;
      this.handleAttribute("onmousedown", onmousedown);
   }

   public String getOnmousemove() {
      if (null != this.onmousemove) {
         return this.onmousemove;
      } else {
         ValueExpression _ve = this.getValueExpression("onmousemove");
         return _ve != null ? (String)_ve.getValue(this.getFacesContext().getELContext()) : null;
      }
   }

   public void setOnmousemove(String onmousemove) {
      this.onmousemove = onmousemove;
      this.handleAttribute("onmousemove", onmousemove);
   }

   public String getOnmouseout() {
      if (null != this.onmouseout) {
         return this.onmouseout;
      } else {
         ValueExpression _ve = this.getValueExpression("onmouseout");
         return _ve != null ? (String)_ve.getValue(this.getFacesContext().getELContext()) : null;
      }
   }

   public void setOnmouseout(String onmouseout) {
      this.onmouseout = onmouseout;
      this.handleAttribute("onmouseout", onmouseout);
   }

   public String getOnmouseover() {
      if (null != this.onmouseover) {
         return this.onmouseover;
      } else {
         ValueExpression _ve = this.getValueExpression("onmouseover");
         return _ve != null ? (String)_ve.getValue(this.getFacesContext().getELContext()) : null;
      }
   }

   public void setOnmouseover(String onmouseover) {
      this.onmouseover = onmouseover;
      this.handleAttribute("onmouseover", onmouseover);
   }

   public String getOnmouseup() {
      if (null != this.onmouseup) {
         return this.onmouseup;
      } else {
         ValueExpression _ve = this.getValueExpression("onmouseup");
         return _ve != null ? (String)_ve.getValue(this.getFacesContext().getELContext()) : null;
      }
   }

   public void setOnmouseup(String onmouseup) {
      this.onmouseup = onmouseup;
      this.handleAttribute("onmouseup", onmouseup);
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

   public String getUsemap() {
      if (null != this.usemap) {
         return this.usemap;
      } else {
         ValueExpression _ve = this.getValueExpression("usemap");
         return _ve != null ? (String)_ve.getValue(this.getFacesContext().getELContext()) : null;
      }
   }

   public void setUsemap(String usemap) {
      this.usemap = usemap;
      this.handleAttribute("usemap", usemap);
   }

   public String getWidth() {
      if (null != this.width) {
         return this.width;
      } else {
         ValueExpression _ve = this.getValueExpression("width");
         return _ve != null ? (String)_ve.getValue(this.getFacesContext().getELContext()) : null;
      }
   }

   public void setWidth(String width) {
      this.width = width;
      this.handleAttribute("width", width);
   }

   public Object saveState(FacesContext _context) {
      if (this._values == null) {
         this._values = new Object[22];
      }

      this._values[0] = super.saveState(_context);
      this._values[1] = this.alt;
      this._values[2] = this.dir;
      this._values[3] = this.height;
      this._values[4] = this.ismap;
      this._values[5] = this.lang;
      this._values[6] = this.longdesc;
      this._values[7] = this.onclick;
      this._values[8] = this.ondblclick;
      this._values[9] = this.onkeydown;
      this._values[10] = this.onkeypress;
      this._values[11] = this.onkeyup;
      this._values[12] = this.onmousedown;
      this._values[13] = this.onmousemove;
      this._values[14] = this.onmouseout;
      this._values[15] = this.onmouseover;
      this._values[16] = this.onmouseup;
      this._values[17] = this.style;
      this._values[18] = this.styleClass;
      this._values[19] = this.title;
      this._values[20] = this.usemap;
      this._values[21] = this.width;
      return this._values;
   }

   public void restoreState(FacesContext _context, Object _state) {
      this._values = (Object[])((Object[])_state);
      super.restoreState(_context, this._values[0]);
      this.alt = (String)this._values[1];
      this.dir = (String)this._values[2];
      this.height = (String)this._values[3];
      this.ismap = (Boolean)this._values[4];
      this.lang = (String)this._values[5];
      this.longdesc = (String)this._values[6];
      this.onclick = (String)this._values[7];
      this.ondblclick = (String)this._values[8];
      this.onkeydown = (String)this._values[9];
      this.onkeypress = (String)this._values[10];
      this.onkeyup = (String)this._values[11];
      this.onmousedown = (String)this._values[12];
      this.onmousemove = (String)this._values[13];
      this.onmouseout = (String)this._values[14];
      this.onmouseover = (String)this._values[15];
      this.onmouseup = (String)this._values[16];
      this.style = (String)this._values[17];
      this.styleClass = (String)this._values[18];
      this.title = (String)this._values[19];
      this.usemap = (String)this._values[20];
      this.width = (String)this._values[21];
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
