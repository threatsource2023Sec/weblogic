package javax.faces.component.html;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import javax.el.ValueExpression;
import javax.faces.component.UIPanel;
import javax.faces.component.behavior.ClientBehaviorHolder;

public class HtmlPanelGrid extends UIPanel implements ClientBehaviorHolder {
   private static final String OPTIMIZED_PACKAGE = "javax.faces.component.";
   public static final String COMPONENT_TYPE = "javax.faces.HtmlPanelGrid";
   private static final Collection EVENT_NAMES = Collections.unmodifiableCollection(Arrays.asList("click", "dblclick", "keydown", "keypress", "keyup", "mousedown", "mousemove", "mouseout", "mouseover", "mouseup"));

   public HtmlPanelGrid() {
      this.setRendererType("javax.faces.Grid");
   }

   public String getBgcolor() {
      return (String)this.getStateHelper().eval(HtmlPanelGrid.PropertyKeys.bgcolor);
   }

   public void setBgcolor(String bgcolor) {
      this.getStateHelper().put(HtmlPanelGrid.PropertyKeys.bgcolor, bgcolor);
      this.handleAttribute("bgcolor", bgcolor);
   }

   public String getBodyrows() {
      return (String)this.getStateHelper().eval(HtmlPanelGrid.PropertyKeys.bodyrows);
   }

   public void setBodyrows(String bodyrows) {
      this.getStateHelper().put(HtmlPanelGrid.PropertyKeys.bodyrows, bodyrows);
   }

   public int getBorder() {
      return (Integer)this.getStateHelper().eval(HtmlPanelGrid.PropertyKeys.border, Integer.MIN_VALUE);
   }

   public void setBorder(int border) {
      this.getStateHelper().put(HtmlPanelGrid.PropertyKeys.border, border);
      this.handleAttribute("border", border);
   }

   public String getCaptionClass() {
      return (String)this.getStateHelper().eval(HtmlPanelGrid.PropertyKeys.captionClass);
   }

   public void setCaptionClass(String captionClass) {
      this.getStateHelper().put(HtmlPanelGrid.PropertyKeys.captionClass, captionClass);
   }

   public String getCaptionStyle() {
      return (String)this.getStateHelper().eval(HtmlPanelGrid.PropertyKeys.captionStyle);
   }

   public void setCaptionStyle(String captionStyle) {
      this.getStateHelper().put(HtmlPanelGrid.PropertyKeys.captionStyle, captionStyle);
   }

   public String getCellpadding() {
      return (String)this.getStateHelper().eval(HtmlPanelGrid.PropertyKeys.cellpadding);
   }

   public void setCellpadding(String cellpadding) {
      this.getStateHelper().put(HtmlPanelGrid.PropertyKeys.cellpadding, cellpadding);
      this.handleAttribute("cellpadding", cellpadding);
   }

   public String getCellspacing() {
      return (String)this.getStateHelper().eval(HtmlPanelGrid.PropertyKeys.cellspacing);
   }

   public void setCellspacing(String cellspacing) {
      this.getStateHelper().put(HtmlPanelGrid.PropertyKeys.cellspacing, cellspacing);
      this.handleAttribute("cellspacing", cellspacing);
   }

   public String getColumnClasses() {
      return (String)this.getStateHelper().eval(HtmlPanelGrid.PropertyKeys.columnClasses);
   }

   public void setColumnClasses(String columnClasses) {
      this.getStateHelper().put(HtmlPanelGrid.PropertyKeys.columnClasses, columnClasses);
   }

   public int getColumns() {
      return (Integer)this.getStateHelper().eval(HtmlPanelGrid.PropertyKeys.columns, Integer.MIN_VALUE);
   }

   public void setColumns(int columns) {
      this.getStateHelper().put(HtmlPanelGrid.PropertyKeys.columns, columns);
   }

   public String getDir() {
      return (String)this.getStateHelper().eval(HtmlPanelGrid.PropertyKeys.dir);
   }

   public void setDir(String dir) {
      this.getStateHelper().put(HtmlPanelGrid.PropertyKeys.dir, dir);
      this.handleAttribute("dir", dir);
   }

   public String getFooterClass() {
      return (String)this.getStateHelper().eval(HtmlPanelGrid.PropertyKeys.footerClass);
   }

   public void setFooterClass(String footerClass) {
      this.getStateHelper().put(HtmlPanelGrid.PropertyKeys.footerClass, footerClass);
   }

   public String getFrame() {
      return (String)this.getStateHelper().eval(HtmlPanelGrid.PropertyKeys.frame);
   }

   public void setFrame(String frame) {
      this.getStateHelper().put(HtmlPanelGrid.PropertyKeys.frame, frame);
      this.handleAttribute("frame", frame);
   }

   public String getHeaderClass() {
      return (String)this.getStateHelper().eval(HtmlPanelGrid.PropertyKeys.headerClass);
   }

   public void setHeaderClass(String headerClass) {
      this.getStateHelper().put(HtmlPanelGrid.PropertyKeys.headerClass, headerClass);
   }

   public String getLang() {
      return (String)this.getStateHelper().eval(HtmlPanelGrid.PropertyKeys.lang);
   }

   public void setLang(String lang) {
      this.getStateHelper().put(HtmlPanelGrid.PropertyKeys.lang, lang);
      this.handleAttribute("lang", lang);
   }

   public String getOnclick() {
      return (String)this.getStateHelper().eval(HtmlPanelGrid.PropertyKeys.onclick);
   }

   public void setOnclick(String onclick) {
      this.getStateHelper().put(HtmlPanelGrid.PropertyKeys.onclick, onclick);
      this.handleAttribute("onclick", onclick);
   }

   public String getOndblclick() {
      return (String)this.getStateHelper().eval(HtmlPanelGrid.PropertyKeys.ondblclick);
   }

   public void setOndblclick(String ondblclick) {
      this.getStateHelper().put(HtmlPanelGrid.PropertyKeys.ondblclick, ondblclick);
      this.handleAttribute("ondblclick", ondblclick);
   }

   public String getOnkeydown() {
      return (String)this.getStateHelper().eval(HtmlPanelGrid.PropertyKeys.onkeydown);
   }

   public void setOnkeydown(String onkeydown) {
      this.getStateHelper().put(HtmlPanelGrid.PropertyKeys.onkeydown, onkeydown);
      this.handleAttribute("onkeydown", onkeydown);
   }

   public String getOnkeypress() {
      return (String)this.getStateHelper().eval(HtmlPanelGrid.PropertyKeys.onkeypress);
   }

   public void setOnkeypress(String onkeypress) {
      this.getStateHelper().put(HtmlPanelGrid.PropertyKeys.onkeypress, onkeypress);
      this.handleAttribute("onkeypress", onkeypress);
   }

   public String getOnkeyup() {
      return (String)this.getStateHelper().eval(HtmlPanelGrid.PropertyKeys.onkeyup);
   }

   public void setOnkeyup(String onkeyup) {
      this.getStateHelper().put(HtmlPanelGrid.PropertyKeys.onkeyup, onkeyup);
      this.handleAttribute("onkeyup", onkeyup);
   }

   public String getOnmousedown() {
      return (String)this.getStateHelper().eval(HtmlPanelGrid.PropertyKeys.onmousedown);
   }

   public void setOnmousedown(String onmousedown) {
      this.getStateHelper().put(HtmlPanelGrid.PropertyKeys.onmousedown, onmousedown);
      this.handleAttribute("onmousedown", onmousedown);
   }

   public String getOnmousemove() {
      return (String)this.getStateHelper().eval(HtmlPanelGrid.PropertyKeys.onmousemove);
   }

   public void setOnmousemove(String onmousemove) {
      this.getStateHelper().put(HtmlPanelGrid.PropertyKeys.onmousemove, onmousemove);
      this.handleAttribute("onmousemove", onmousemove);
   }

   public String getOnmouseout() {
      return (String)this.getStateHelper().eval(HtmlPanelGrid.PropertyKeys.onmouseout);
   }

   public void setOnmouseout(String onmouseout) {
      this.getStateHelper().put(HtmlPanelGrid.PropertyKeys.onmouseout, onmouseout);
      this.handleAttribute("onmouseout", onmouseout);
   }

   public String getOnmouseover() {
      return (String)this.getStateHelper().eval(HtmlPanelGrid.PropertyKeys.onmouseover);
   }

   public void setOnmouseover(String onmouseover) {
      this.getStateHelper().put(HtmlPanelGrid.PropertyKeys.onmouseover, onmouseover);
      this.handleAttribute("onmouseover", onmouseover);
   }

   public String getOnmouseup() {
      return (String)this.getStateHelper().eval(HtmlPanelGrid.PropertyKeys.onmouseup);
   }

   public void setOnmouseup(String onmouseup) {
      this.getStateHelper().put(HtmlPanelGrid.PropertyKeys.onmouseup, onmouseup);
      this.handleAttribute("onmouseup", onmouseup);
   }

   public String getRole() {
      return (String)this.getStateHelper().eval(HtmlPanelGrid.PropertyKeys.role);
   }

   public void setRole(String role) {
      this.getStateHelper().put(HtmlPanelGrid.PropertyKeys.role, role);
      this.handleAttribute("role", role);
   }

   public String getRowClass() {
      return (String)this.getStateHelper().eval(HtmlPanelGrid.PropertyKeys.rowClass);
   }

   public void setRowClass(String rowClass) {
      this.getStateHelper().put(HtmlPanelGrid.PropertyKeys.rowClass, rowClass);
   }

   public String getRowClasses() {
      return (String)this.getStateHelper().eval(HtmlPanelGrid.PropertyKeys.rowClasses);
   }

   public void setRowClasses(String rowClasses) {
      this.getStateHelper().put(HtmlPanelGrid.PropertyKeys.rowClasses, rowClasses);
   }

   public String getRules() {
      return (String)this.getStateHelper().eval(HtmlPanelGrid.PropertyKeys.rules);
   }

   public void setRules(String rules) {
      this.getStateHelper().put(HtmlPanelGrid.PropertyKeys.rules, rules);
      this.handleAttribute("rules", rules);
   }

   public String getStyle() {
      return (String)this.getStateHelper().eval(HtmlPanelGrid.PropertyKeys.style);
   }

   public void setStyle(String style) {
      this.getStateHelper().put(HtmlPanelGrid.PropertyKeys.style, style);
      this.handleAttribute("style", style);
   }

   public String getStyleClass() {
      return (String)this.getStateHelper().eval(HtmlPanelGrid.PropertyKeys.styleClass);
   }

   public void setStyleClass(String styleClass) {
      this.getStateHelper().put(HtmlPanelGrid.PropertyKeys.styleClass, styleClass);
   }

   public String getSummary() {
      return (String)this.getStateHelper().eval(HtmlPanelGrid.PropertyKeys.summary);
   }

   public void setSummary(String summary) {
      this.getStateHelper().put(HtmlPanelGrid.PropertyKeys.summary, summary);
      this.handleAttribute("summary", summary);
   }

   public String getTitle() {
      return (String)this.getStateHelper().eval(HtmlPanelGrid.PropertyKeys.title);
   }

   public void setTitle(String title) {
      this.getStateHelper().put(HtmlPanelGrid.PropertyKeys.title, title);
      this.handleAttribute("title", title);
   }

   public String getWidth() {
      return (String)this.getStateHelper().eval(HtmlPanelGrid.PropertyKeys.width);
   }

   public void setWidth(String width) {
      this.getStateHelper().put(HtmlPanelGrid.PropertyKeys.width, width);
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
      columns,
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
