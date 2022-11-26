package javax.faces.component.html;

import java.util.ArrayList;
import java.util.List;
import javax.el.ValueExpression;
import javax.faces.component.UIOutput;
import javax.faces.context.FacesContext;

public class HtmlOutputLabel extends UIOutput {
   private static final String OPTIMIZED_PACKAGE = "javax.faces.component.";
   public static final String COMPONENT_TYPE = "javax.faces.HtmlOutputLabel";
   private String accesskey;
   private String dir;
   private Boolean escape;
   private String _for;
   private String lang;
   private String onblur;
   private String onclick;
   private String ondblclick;
   private String onfocus;
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
   private String tabindex;
   private String title;
   private Object[] _values;

   public HtmlOutputLabel() {
      this.setRendererType("javax.faces.Label");
   }

   public String getAccesskey() {
      if (null != this.accesskey) {
         return this.accesskey;
      } else {
         ValueExpression _ve = this.getValueExpression("accesskey");
         return _ve != null ? (String)_ve.getValue(this.getFacesContext().getELContext()) : null;
      }
   }

   public void setAccesskey(String accesskey) {
      this.accesskey = accesskey;
      this.handleAttribute("accesskey", accesskey);
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

   public String getFor() {
      if (null != this._for) {
         return this._for;
      } else {
         ValueExpression _ve = this.getValueExpression("for");
         return _ve != null ? (String)_ve.getValue(this.getFacesContext().getELContext()) : null;
      }
   }

   public void setFor(String _for) {
      this._for = _for;
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

   public String getOnblur() {
      if (null != this.onblur) {
         return this.onblur;
      } else {
         ValueExpression _ve = this.getValueExpression("onblur");
         return _ve != null ? (String)_ve.getValue(this.getFacesContext().getELContext()) : null;
      }
   }

   public void setOnblur(String onblur) {
      this.onblur = onblur;
      this.handleAttribute("onblur", onblur);
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

   public String getOnfocus() {
      if (null != this.onfocus) {
         return this.onfocus;
      } else {
         ValueExpression _ve = this.getValueExpression("onfocus");
         return _ve != null ? (String)_ve.getValue(this.getFacesContext().getELContext()) : null;
      }
   }

   public void setOnfocus(String onfocus) {
      this.onfocus = onfocus;
      this.handleAttribute("onfocus", onfocus);
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

   public String getTabindex() {
      if (null != this.tabindex) {
         return this.tabindex;
      } else {
         ValueExpression _ve = this.getValueExpression("tabindex");
         return _ve != null ? (String)_ve.getValue(this.getFacesContext().getELContext()) : null;
      }
   }

   public void setTabindex(String tabindex) {
      this.tabindex = tabindex;
      this.handleAttribute("tabindex", tabindex);
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
         this._values = new Object[22];
      }

      this._values[0] = super.saveState(_context);
      this._values[1] = this.accesskey;
      this._values[2] = this.dir;
      this._values[3] = this.escape;
      this._values[4] = this._for;
      this._values[5] = this.lang;
      this._values[6] = this.onblur;
      this._values[7] = this.onclick;
      this._values[8] = this.ondblclick;
      this._values[9] = this.onfocus;
      this._values[10] = this.onkeydown;
      this._values[11] = this.onkeypress;
      this._values[12] = this.onkeyup;
      this._values[13] = this.onmousedown;
      this._values[14] = this.onmousemove;
      this._values[15] = this.onmouseout;
      this._values[16] = this.onmouseover;
      this._values[17] = this.onmouseup;
      this._values[18] = this.style;
      this._values[19] = this.styleClass;
      this._values[20] = this.tabindex;
      this._values[21] = this.title;
      return this._values;
   }

   public void restoreState(FacesContext _context, Object _state) {
      this._values = (Object[])((Object[])_state);
      super.restoreState(_context, this._values[0]);
      this.accesskey = (String)this._values[1];
      this.dir = (String)this._values[2];
      this.escape = (Boolean)this._values[3];
      this._for = (String)this._values[4];
      this.lang = (String)this._values[5];
      this.onblur = (String)this._values[6];
      this.onclick = (String)this._values[7];
      this.ondblclick = (String)this._values[8];
      this.onfocus = (String)this._values[9];
      this.onkeydown = (String)this._values[10];
      this.onkeypress = (String)this._values[11];
      this.onkeyup = (String)this._values[12];
      this.onmousedown = (String)this._values[13];
      this.onmousemove = (String)this._values[14];
      this.onmouseout = (String)this._values[15];
      this.onmouseover = (String)this._values[16];
      this.onmouseup = (String)this._values[17];
      this.style = (String)this._values[18];
      this.styleClass = (String)this._values[19];
      this.tabindex = (String)this._values[20];
      this.title = (String)this._values[21];
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
