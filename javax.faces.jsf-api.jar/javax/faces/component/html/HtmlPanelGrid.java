package javax.faces.component.html;

import java.util.ArrayList;
import java.util.List;
import javax.el.ValueExpression;
import javax.faces.component.UIPanel;
import javax.faces.context.FacesContext;

public class HtmlPanelGrid extends UIPanel {
   private static final String OPTIMIZED_PACKAGE = "javax.faces.component.";
   public static final String COMPONENT_TYPE = "javax.faces.HtmlPanelGrid";
   private String bgcolor;
   private Integer border;
   private String captionClass;
   private String captionStyle;
   private String cellpadding;
   private String cellspacing;
   private String columnClasses;
   private Integer columns;
   private String dir;
   private String footerClass;
   private String frame;
   private String headerClass;
   private String lang;
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
   private String rowClasses;
   private String rules;
   private String style;
   private String styleClass;
   private String summary;
   private String title;
   private String width;
   private Object[] _values;

   public HtmlPanelGrid() {
      this.setRendererType("javax.faces.Grid");
   }

   public String getBgcolor() {
      if (null != this.bgcolor) {
         return this.bgcolor;
      } else {
         ValueExpression _ve = this.getValueExpression("bgcolor");
         return _ve != null ? (String)_ve.getValue(this.getFacesContext().getELContext()) : null;
      }
   }

   public void setBgcolor(String bgcolor) {
      this.bgcolor = bgcolor;
      this.handleAttribute("bgcolor", bgcolor);
   }

   public int getBorder() {
      if (null != this.border) {
         return this.border;
      } else {
         ValueExpression _ve = this.getValueExpression("border");
         return _ve != null ? (Integer)_ve.getValue(this.getFacesContext().getELContext()) : Integer.MIN_VALUE;
      }
   }

   public void setBorder(int border) {
      this.border = border;
      this.handleAttribute("border", border);
   }

   public String getCaptionClass() {
      if (null != this.captionClass) {
         return this.captionClass;
      } else {
         ValueExpression _ve = this.getValueExpression("captionClass");
         return _ve != null ? (String)_ve.getValue(this.getFacesContext().getELContext()) : null;
      }
   }

   public void setCaptionClass(String captionClass) {
      this.captionClass = captionClass;
   }

   public String getCaptionStyle() {
      if (null != this.captionStyle) {
         return this.captionStyle;
      } else {
         ValueExpression _ve = this.getValueExpression("captionStyle");
         return _ve != null ? (String)_ve.getValue(this.getFacesContext().getELContext()) : null;
      }
   }

   public void setCaptionStyle(String captionStyle) {
      this.captionStyle = captionStyle;
   }

   public String getCellpadding() {
      if (null != this.cellpadding) {
         return this.cellpadding;
      } else {
         ValueExpression _ve = this.getValueExpression("cellpadding");
         return _ve != null ? (String)_ve.getValue(this.getFacesContext().getELContext()) : null;
      }
   }

   public void setCellpadding(String cellpadding) {
      this.cellpadding = cellpadding;
      this.handleAttribute("cellpadding", cellpadding);
   }

   public String getCellspacing() {
      if (null != this.cellspacing) {
         return this.cellspacing;
      } else {
         ValueExpression _ve = this.getValueExpression("cellspacing");
         return _ve != null ? (String)_ve.getValue(this.getFacesContext().getELContext()) : null;
      }
   }

   public void setCellspacing(String cellspacing) {
      this.cellspacing = cellspacing;
      this.handleAttribute("cellspacing", cellspacing);
   }

   public String getColumnClasses() {
      if (null != this.columnClasses) {
         return this.columnClasses;
      } else {
         ValueExpression _ve = this.getValueExpression("columnClasses");
         return _ve != null ? (String)_ve.getValue(this.getFacesContext().getELContext()) : null;
      }
   }

   public void setColumnClasses(String columnClasses) {
      this.columnClasses = columnClasses;
   }

   public int getColumns() {
      if (null != this.columns) {
         return this.columns;
      } else {
         ValueExpression _ve = this.getValueExpression("columns");
         return _ve != null ? (Integer)_ve.getValue(this.getFacesContext().getELContext()) : Integer.MIN_VALUE;
      }
   }

   public void setColumns(int columns) {
      this.columns = columns;
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

   public String getFrame() {
      if (null != this.frame) {
         return this.frame;
      } else {
         ValueExpression _ve = this.getValueExpression("frame");
         return _ve != null ? (String)_ve.getValue(this.getFacesContext().getELContext()) : null;
      }
   }

   public void setFrame(String frame) {
      this.frame = frame;
      this.handleAttribute("frame", frame);
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

   public String getRowClasses() {
      if (null != this.rowClasses) {
         return this.rowClasses;
      } else {
         ValueExpression _ve = this.getValueExpression("rowClasses");
         return _ve != null ? (String)_ve.getValue(this.getFacesContext().getELContext()) : null;
      }
   }

   public void setRowClasses(String rowClasses) {
      this.rowClasses = rowClasses;
   }

   public String getRules() {
      if (null != this.rules) {
         return this.rules;
      } else {
         ValueExpression _ve = this.getValueExpression("rules");
         return _ve != null ? (String)_ve.getValue(this.getFacesContext().getELContext()) : null;
      }
   }

   public void setRules(String rules) {
      this.rules = rules;
      this.handleAttribute("rules", rules);
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

   public String getSummary() {
      if (null != this.summary) {
         return this.summary;
      } else {
         ValueExpression _ve = this.getValueExpression("summary");
         return _ve != null ? (String)_ve.getValue(this.getFacesContext().getELContext()) : null;
      }
   }

   public void setSummary(String summary) {
      this.summary = summary;
      this.handleAttribute("summary", summary);
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
         this._values = new Object[31];
      }

      this._values[0] = super.saveState(_context);
      this._values[1] = this.bgcolor;
      this._values[2] = this.border;
      this._values[3] = this.captionClass;
      this._values[4] = this.captionStyle;
      this._values[5] = this.cellpadding;
      this._values[6] = this.cellspacing;
      this._values[7] = this.columnClasses;
      this._values[8] = this.columns;
      this._values[9] = this.dir;
      this._values[10] = this.footerClass;
      this._values[11] = this.frame;
      this._values[12] = this.headerClass;
      this._values[13] = this.lang;
      this._values[14] = this.onclick;
      this._values[15] = this.ondblclick;
      this._values[16] = this.onkeydown;
      this._values[17] = this.onkeypress;
      this._values[18] = this.onkeyup;
      this._values[19] = this.onmousedown;
      this._values[20] = this.onmousemove;
      this._values[21] = this.onmouseout;
      this._values[22] = this.onmouseover;
      this._values[23] = this.onmouseup;
      this._values[24] = this.rowClasses;
      this._values[25] = this.rules;
      this._values[26] = this.style;
      this._values[27] = this.styleClass;
      this._values[28] = this.summary;
      this._values[29] = this.title;
      this._values[30] = this.width;
      return this._values;
   }

   public void restoreState(FacesContext _context, Object _state) {
      this._values = (Object[])((Object[])_state);
      super.restoreState(_context, this._values[0]);
      this.bgcolor = (String)this._values[1];
      this.border = (Integer)this._values[2];
      this.captionClass = (String)this._values[3];
      this.captionStyle = (String)this._values[4];
      this.cellpadding = (String)this._values[5];
      this.cellspacing = (String)this._values[6];
      this.columnClasses = (String)this._values[7];
      this.columns = (Integer)this._values[8];
      this.dir = (String)this._values[9];
      this.footerClass = (String)this._values[10];
      this.frame = (String)this._values[11];
      this.headerClass = (String)this._values[12];
      this.lang = (String)this._values[13];
      this.onclick = (String)this._values[14];
      this.ondblclick = (String)this._values[15];
      this.onkeydown = (String)this._values[16];
      this.onkeypress = (String)this._values[17];
      this.onkeyup = (String)this._values[18];
      this.onmousedown = (String)this._values[19];
      this.onmousemove = (String)this._values[20];
      this.onmouseout = (String)this._values[21];
      this.onmouseover = (String)this._values[22];
      this.onmouseup = (String)this._values[23];
      this.rowClasses = (String)this._values[24];
      this.rules = (String)this._values[25];
      this.style = (String)this._values[26];
      this.styleClass = (String)this._values[27];
      this.summary = (String)this._values[28];
      this.title = (String)this._values[29];
      this.width = (String)this._values[30];
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
