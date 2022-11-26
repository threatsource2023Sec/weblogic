package javax.faces.component.html;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import javax.el.ValueExpression;
import javax.faces.component.UIOutput;
import javax.faces.component.behavior.ClientBehaviorHolder;

public class HtmlBody extends UIOutput implements ClientBehaviorHolder {
   private static final String OPTIMIZED_PACKAGE = "javax.faces.component.";
   public static final String COMPONENT_TYPE = "javax.faces.OutputBody";
   private static final Collection EVENT_NAMES = Collections.unmodifiableCollection(Arrays.asList("click", "dblclick", "keydown", "keypress", "keyup", "load", "mousedown", "mousemove", "mouseout", "mouseover", "mouseup", "unload"));

   public HtmlBody() {
      this.setRendererType("javax.faces.Body");
   }

   public String getDir() {
      return (String)this.getStateHelper().eval(HtmlBody.PropertyKeys.dir);
   }

   public void setDir(String dir) {
      this.getStateHelper().put(HtmlBody.PropertyKeys.dir, dir);
      this.handleAttribute("dir", dir);
   }

   public String getLang() {
      return (String)this.getStateHelper().eval(HtmlBody.PropertyKeys.lang);
   }

   public void setLang(String lang) {
      this.getStateHelper().put(HtmlBody.PropertyKeys.lang, lang);
      this.handleAttribute("lang", lang);
   }

   public String getOnclick() {
      return (String)this.getStateHelper().eval(HtmlBody.PropertyKeys.onclick);
   }

   public void setOnclick(String onclick) {
      this.getStateHelper().put(HtmlBody.PropertyKeys.onclick, onclick);
      this.handleAttribute("onclick", onclick);
   }

   public String getOndblclick() {
      return (String)this.getStateHelper().eval(HtmlBody.PropertyKeys.ondblclick);
   }

   public void setOndblclick(String ondblclick) {
      this.getStateHelper().put(HtmlBody.PropertyKeys.ondblclick, ondblclick);
      this.handleAttribute("ondblclick", ondblclick);
   }

   public String getOnkeydown() {
      return (String)this.getStateHelper().eval(HtmlBody.PropertyKeys.onkeydown);
   }

   public void setOnkeydown(String onkeydown) {
      this.getStateHelper().put(HtmlBody.PropertyKeys.onkeydown, onkeydown);
      this.handleAttribute("onkeydown", onkeydown);
   }

   public String getOnkeypress() {
      return (String)this.getStateHelper().eval(HtmlBody.PropertyKeys.onkeypress);
   }

   public void setOnkeypress(String onkeypress) {
      this.getStateHelper().put(HtmlBody.PropertyKeys.onkeypress, onkeypress);
      this.handleAttribute("onkeypress", onkeypress);
   }

   public String getOnkeyup() {
      return (String)this.getStateHelper().eval(HtmlBody.PropertyKeys.onkeyup);
   }

   public void setOnkeyup(String onkeyup) {
      this.getStateHelper().put(HtmlBody.PropertyKeys.onkeyup, onkeyup);
      this.handleAttribute("onkeyup", onkeyup);
   }

   public String getOnload() {
      return (String)this.getStateHelper().eval(HtmlBody.PropertyKeys.onload);
   }

   public void setOnload(String onload) {
      this.getStateHelper().put(HtmlBody.PropertyKeys.onload, onload);
      this.handleAttribute("onload", onload);
   }

   public String getOnmousedown() {
      return (String)this.getStateHelper().eval(HtmlBody.PropertyKeys.onmousedown);
   }

   public void setOnmousedown(String onmousedown) {
      this.getStateHelper().put(HtmlBody.PropertyKeys.onmousedown, onmousedown);
      this.handleAttribute("onmousedown", onmousedown);
   }

   public String getOnmousemove() {
      return (String)this.getStateHelper().eval(HtmlBody.PropertyKeys.onmousemove);
   }

   public void setOnmousemove(String onmousemove) {
      this.getStateHelper().put(HtmlBody.PropertyKeys.onmousemove, onmousemove);
      this.handleAttribute("onmousemove", onmousemove);
   }

   public String getOnmouseout() {
      return (String)this.getStateHelper().eval(HtmlBody.PropertyKeys.onmouseout);
   }

   public void setOnmouseout(String onmouseout) {
      this.getStateHelper().put(HtmlBody.PropertyKeys.onmouseout, onmouseout);
      this.handleAttribute("onmouseout", onmouseout);
   }

   public String getOnmouseover() {
      return (String)this.getStateHelper().eval(HtmlBody.PropertyKeys.onmouseover);
   }

   public void setOnmouseover(String onmouseover) {
      this.getStateHelper().put(HtmlBody.PropertyKeys.onmouseover, onmouseover);
      this.handleAttribute("onmouseover", onmouseover);
   }

   public String getOnmouseup() {
      return (String)this.getStateHelper().eval(HtmlBody.PropertyKeys.onmouseup);
   }

   public void setOnmouseup(String onmouseup) {
      this.getStateHelper().put(HtmlBody.PropertyKeys.onmouseup, onmouseup);
      this.handleAttribute("onmouseup", onmouseup);
   }

   public String getOnunload() {
      return (String)this.getStateHelper().eval(HtmlBody.PropertyKeys.onunload);
   }

   public void setOnunload(String onunload) {
      this.getStateHelper().put(HtmlBody.PropertyKeys.onunload, onunload);
      this.handleAttribute("onunload", onunload);
   }

   public String getRole() {
      return (String)this.getStateHelper().eval(HtmlBody.PropertyKeys.role);
   }

   public void setRole(String role) {
      this.getStateHelper().put(HtmlBody.PropertyKeys.role, role);
      this.handleAttribute("role", role);
   }

   public String getStyle() {
      return (String)this.getStateHelper().eval(HtmlBody.PropertyKeys.style);
   }

   public void setStyle(String style) {
      this.getStateHelper().put(HtmlBody.PropertyKeys.style, style);
      this.handleAttribute("style", style);
   }

   public String getStyleClass() {
      return (String)this.getStateHelper().eval(HtmlBody.PropertyKeys.styleClass);
   }

   public void setStyleClass(String styleClass) {
      this.getStateHelper().put(HtmlBody.PropertyKeys.styleClass, styleClass);
   }

   public String getTitle() {
      return (String)this.getStateHelper().eval(HtmlBody.PropertyKeys.title);
   }

   public void setTitle(String title) {
      this.getStateHelper().put(HtmlBody.PropertyKeys.title, title);
      this.handleAttribute("title", title);
   }

   public String getXmlns() {
      return (String)this.getStateHelper().eval(HtmlBody.PropertyKeys.xmlns);
   }

   public void setXmlns(String xmlns) {
      this.getStateHelper().put(HtmlBody.PropertyKeys.xmlns, xmlns);
      this.handleAttribute("xmlns", xmlns);
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
      dir,
      lang,
      onclick,
      ondblclick,
      onkeydown,
      onkeypress,
      onkeyup,
      onload,
      onmousedown,
      onmousemove,
      onmouseout,
      onmouseover,
      onmouseup,
      onunload,
      role,
      style,
      styleClass,
      title,
      xmlns;

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
