package javax.faces.component.html;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import javax.el.ValueExpression;
import javax.faces.component.UIData;
import javax.faces.component.behavior.ClientBehaviorHolder;

public class HtmlDataTable extends UIData implements ClientBehaviorHolder {
   private static final String OPTIMIZED_PACKAGE = "javax.faces.component.";
   public static final String COMPONENT_TYPE = "javax.faces.HtmlDataTable";
   private static final Collection EVENT_NAMES = Collections.unmodifiableCollection(Arrays.asList("click", "dblclick", "keydown", "keypress", "keyup", "mousedown", "mousemove", "mouseout", "mouseover", "mouseup"));

   public HtmlDataTable() {
      this.setRendererType("javax.faces.Table");
   }

   public String getBgcolor() {
      return (String)this.getStateHelper().eval(HtmlDataTable.PropertyKeys.bgcolor);
   }

   public void setBgcolor(String bgcolor) {
      this.getStateHelper().put(HtmlDataTable.PropertyKeys.bgcolor, bgcolor);
      this.handleAttribute("bgcolor", bgcolor);
   }

   public String getBodyrows() {
      return (String)this.getStateHelper().eval(HtmlDataTable.PropertyKeys.bodyrows);
   }

   public void setBodyrows(String bodyrows) {
      this.getStateHelper().put(HtmlDataTable.PropertyKeys.bodyrows, bodyrows);
   }

   public int getBorder() {
      return (Integer)this.getStateHelper().eval(HtmlDataTable.PropertyKeys.border, Integer.MIN_VALUE);
   }

   public void setBorder(int border) {
      this.getStateHelper().put(HtmlDataTable.PropertyKeys.border, border);
      this.handleAttribute("border", border);
   }

   public String getCaptionClass() {
      return (String)this.getStateHelper().eval(HtmlDataTable.PropertyKeys.captionClass);
   }

   public void setCaptionClass(String captionClass) {
      this.getStateHelper().put(HtmlDataTable.PropertyKeys.captionClass, captionClass);
   }

   public String getCaptionStyle() {
      return (String)this.getStateHelper().eval(HtmlDataTable.PropertyKeys.captionStyle);
   }

   public void setCaptionStyle(String captionStyle) {
      this.getStateHelper().put(HtmlDataTable.PropertyKeys.captionStyle, captionStyle);
   }

   public String getCellpadding() {
      return (String)this.getStateHelper().eval(HtmlDataTable.PropertyKeys.cellpadding);
   }

   public void setCellpadding(String cellpadding) {
      this.getStateHelper().put(HtmlDataTable.PropertyKeys.cellpadding, cellpadding);
      this.handleAttribute("cellpadding", cellpadding);
   }

   public String getCellspacing() {
      return (String)this.getStateHelper().eval(HtmlDataTable.PropertyKeys.cellspacing);
   }

   public void setCellspacing(String cellspacing) {
      this.getStateHelper().put(HtmlDataTable.PropertyKeys.cellspacing, cellspacing);
      this.handleAttribute("cellspacing", cellspacing);
   }

   public String getColumnClasses() {
      return (String)this.getStateHelper().eval(HtmlDataTable.PropertyKeys.columnClasses);
   }

   public void setColumnClasses(String columnClasses) {
      this.getStateHelper().put(HtmlDataTable.PropertyKeys.columnClasses, columnClasses);
   }

   public String getDir() {
      return (String)this.getStateHelper().eval(HtmlDataTable.PropertyKeys.dir);
   }

   public void setDir(String dir) {
      this.getStateHelper().put(HtmlDataTable.PropertyKeys.dir, dir);
      this.handleAttribute("dir", dir);
   }

   public String getFooterClass() {
      return (String)this.getStateHelper().eval(HtmlDataTable.PropertyKeys.footerClass);
   }

   public void setFooterClass(String footerClass) {
      this.getStateHelper().put(HtmlDataTable.PropertyKeys.footerClass, footerClass);
   }

   public String getFrame() {
      return (String)this.getStateHelper().eval(HtmlDataTable.PropertyKeys.frame);
   }

   public void setFrame(String frame) {
      this.getStateHelper().put(HtmlDataTable.PropertyKeys.frame, frame);
      this.handleAttribute("frame", frame);
   }

   public String getHeaderClass() {
      return (String)this.getStateHelper().eval(HtmlDataTable.PropertyKeys.headerClass);
   }

   public void setHeaderClass(String headerClass) {
      this.getStateHelper().put(HtmlDataTable.PropertyKeys.headerClass, headerClass);
   }

   public String getLang() {
      return (String)this.getStateHelper().eval(HtmlDataTable.PropertyKeys.lang);
   }

   public void setLang(String lang) {
      this.getStateHelper().put(HtmlDataTable.PropertyKeys.lang, lang);
      this.handleAttribute("lang", lang);
   }

   public String getOnclick() {
      return (String)this.getStateHelper().eval(HtmlDataTable.PropertyKeys.onclick);
   }

   public void setOnclick(String onclick) {
      this.getStateHelper().put(HtmlDataTable.PropertyKeys.onclick, onclick);
      this.handleAttribute("onclick", onclick);
   }

   public String getOndblclick() {
      return (String)this.getStateHelper().eval(HtmlDataTable.PropertyKeys.ondblclick);
   }

   public void setOndblclick(String ondblclick) {
      this.getStateHelper().put(HtmlDataTable.PropertyKeys.ondblclick, ondblclick);
      this.handleAttribute("ondblclick", ondblclick);
   }

   public String getOnkeydown() {
      return (String)this.getStateHelper().eval(HtmlDataTable.PropertyKeys.onkeydown);
   }

   public void setOnkeydown(String onkeydown) {
      this.getStateHelper().put(HtmlDataTable.PropertyKeys.onkeydown, onkeydown);
      this.handleAttribute("onkeydown", onkeydown);
   }

   public String getOnkeypress() {
      return (String)this.getStateHelper().eval(HtmlDataTable.PropertyKeys.onkeypress);
   }

   public void setOnkeypress(String onkeypress) {
      this.getStateHelper().put(HtmlDataTable.PropertyKeys.onkeypress, onkeypress);
      this.handleAttribute("onkeypress", onkeypress);
   }

   public String getOnkeyup() {
      return (String)this.getStateHelper().eval(HtmlDataTable.PropertyKeys.onkeyup);
   }

   public void setOnkeyup(String onkeyup) {
      this.getStateHelper().put(HtmlDataTable.PropertyKeys.onkeyup, onkeyup);
      this.handleAttribute("onkeyup", onkeyup);
   }

   public String getOnmousedown() {
      return (String)this.getStateHelper().eval(HtmlDataTable.PropertyKeys.onmousedown);
   }

   public void setOnmousedown(String onmousedown) {
      this.getStateHelper().put(HtmlDataTable.PropertyKeys.onmousedown, onmousedown);
      this.handleAttribute("onmousedown", onmousedown);
   }

   public String getOnmousemove() {
      return (String)this.getStateHelper().eval(HtmlDataTable.PropertyKeys.onmousemove);
   }

   public void setOnmousemove(String onmousemove) {
      this.getStateHelper().put(HtmlDataTable.PropertyKeys.onmousemove, onmousemove);
      this.handleAttribute("onmousemove", onmousemove);
   }

   public String getOnmouseout() {
      return (String)this.getStateHelper().eval(HtmlDataTable.PropertyKeys.onmouseout);
   }

   public void setOnmouseout(String onmouseout) {
      this.getStateHelper().put(HtmlDataTable.PropertyKeys.onmouseout, onmouseout);
      this.handleAttribute("onmouseout", onmouseout);
   }

   public String getOnmouseover() {
      return (String)this.getStateHelper().eval(HtmlDataTable.PropertyKeys.onmouseover);
   }

   public void setOnmouseover(String onmouseover) {
      this.getStateHelper().put(HtmlDataTable.PropertyKeys.onmouseover, onmouseover);
      this.handleAttribute("onmouseover", onmouseover);
   }

   public String getOnmouseup() {
      return (String)this.getStateHelper().eval(HtmlDataTable.PropertyKeys.onmouseup);
   }

   public void setOnmouseup(String onmouseup) {
      this.getStateHelper().put(HtmlDataTable.PropertyKeys.onmouseup, onmouseup);
      this.handleAttribute("onmouseup", onmouseup);
   }

   public String getRole() {
      return (String)this.getStateHelper().eval(HtmlDataTable.PropertyKeys.role);
   }

   public void setRole(String role) {
      this.getStateHelper().put(HtmlDataTable.PropertyKeys.role, role);
      this.handleAttribute("role", role);
   }

   public String getRowClass() {
      return (String)this.getStateHelper().eval(HtmlDataTable.PropertyKeys.rowClass);
   }

   public void setRowClass(String rowClass) {
      this.getStateHelper().put(HtmlDataTable.PropertyKeys.rowClass, rowClass);
   }

   public String getRowClasses() {
      return (String)this.getStateHelper().eval(HtmlDataTable.PropertyKeys.rowClasses);
   }

   public void setRowClasses(String rowClasses) {
      this.getStateHelper().put(HtmlDataTable.PropertyKeys.rowClasses, rowClasses);
   }

   public String getRules() {
      return (String)this.getStateHelper().eval(HtmlDataTable.PropertyKeys.rules);
   }

   public void setRules(String rules) {
      this.getStateHelper().put(HtmlDataTable.PropertyKeys.rules, rules);
      this.handleAttribute("rules", rules);
   }

   public String getStyle() {
      return (String)this.getStateHelper().eval(HtmlDataTable.PropertyKeys.style);
   }

   public void setStyle(String style) {
      this.getStateHelper().put(HtmlDataTable.PropertyKeys.style, style);
      this.handleAttribute("style", style);
   }

   public String getStyleClass() {
      return (String)this.getStateHelper().eval(HtmlDataTable.PropertyKeys.styleClass);
   }

   public void setStyleClass(String styleClass) {
      this.getStateHelper().put(HtmlDataTable.PropertyKeys.styleClass, styleClass);
   }

   public String getSummary() {
      return (String)this.getStateHelper().eval(HtmlDataTable.PropertyKeys.summary);
   }

   public void setSummary(String summary) {
      this.getStateHelper().put(HtmlDataTable.PropertyKeys.summary, summary);
      this.handleAttribute("summary", summary);
   }

   public String getTitle() {
      return (String)this.getStateHelper().eval(HtmlDataTable.PropertyKeys.title);
   }

   public void setTitle(String title) {
      this.getStateHelper().put(HtmlDataTable.PropertyKeys.title, title);
      this.handleAttribute("title", title);
   }

   public String getWidth() {
      return (String)this.getStateHelper().eval(HtmlDataTable.PropertyKeys.width);
   }

   public void setWidth(String width) {
      this.getStateHelper().put(HtmlDataTable.PropertyKeys.width, width);
      this.handleAttribute("width", width);
   }

   public Collection getEventNames() {
      return EVENT_NAMES;
   }

   public String getDefaultEventName() {
      return null;
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
      bgcolor,
      bodyrows,
      border,
      captionClass,
      captionStyle,
      cellpadding,
      cellspacing,
      columnClasses,
      dir,
      footerClass,
      frame,
      headerClass,
      lang,
      onclick,
      ondblclick,
      onkeydown,
      onkeypress,
      onkeyup,
      onmousedown,
      onmousemove,
      onmouseout,
      onmouseover,
      onmouseup,
      role,
      rowClass,
      rowClasses,
      rules,
      style,
      styleClass,
      summary,
      title,
      width;

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
