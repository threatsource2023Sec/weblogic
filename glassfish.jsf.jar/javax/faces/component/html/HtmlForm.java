package javax.faces.component.html;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import javax.el.ValueExpression;
import javax.faces.component.UIForm;
import javax.faces.component.behavior.ClientBehaviorHolder;

public class HtmlForm extends UIForm implements ClientBehaviorHolder {
   private static final String OPTIMIZED_PACKAGE = "javax.faces.component.";
   public static final String COMPONENT_TYPE = "javax.faces.HtmlForm";
   private static final Collection EVENT_NAMES = Collections.unmodifiableCollection(Arrays.asList("click", "dblclick", "keydown", "keypress", "keyup", "mousedown", "mousemove", "mouseout", "mouseover", "mouseup"));

   public HtmlForm() {
      this.setRendererType("javax.faces.Form");
      this.handleAttribute("enctype", "application/x-www-form-urlencoded");
   }

   public String getAccept() {
      return (String)this.getStateHelper().eval(HtmlForm.PropertyKeys.accept);
   }

   public void setAccept(String accept) {
      this.getStateHelper().put(HtmlForm.PropertyKeys.accept, accept);
      this.handleAttribute("accept", accept);
   }

   public String getAcceptcharset() {
      return (String)this.getStateHelper().eval(HtmlForm.PropertyKeys.acceptcharset);
   }

   public void setAcceptcharset(String acceptcharset) {
      this.getStateHelper().put(HtmlForm.PropertyKeys.acceptcharset, acceptcharset);
   }

   public String getDir() {
      return (String)this.getStateHelper().eval(HtmlForm.PropertyKeys.dir);
   }

   public void setDir(String dir) {
      this.getStateHelper().put(HtmlForm.PropertyKeys.dir, dir);
      this.handleAttribute("dir", dir);
   }

   public String getEnctype() {
      return (String)this.getStateHelper().eval(HtmlForm.PropertyKeys.enctype, "application/x-www-form-urlencoded");
   }

   public void setEnctype(String enctype) {
      this.getStateHelper().put(HtmlForm.PropertyKeys.enctype, enctype);
   }

   public String getLang() {
      return (String)this.getStateHelper().eval(HtmlForm.PropertyKeys.lang);
   }

   public void setLang(String lang) {
      this.getStateHelper().put(HtmlForm.PropertyKeys.lang, lang);
      this.handleAttribute("lang", lang);
   }

   public String getOnclick() {
      return (String)this.getStateHelper().eval(HtmlForm.PropertyKeys.onclick);
   }

   public void setOnclick(String onclick) {
      this.getStateHelper().put(HtmlForm.PropertyKeys.onclick, onclick);
      this.handleAttribute("onclick", onclick);
   }

   public String getOndblclick() {
      return (String)this.getStateHelper().eval(HtmlForm.PropertyKeys.ondblclick);
   }

   public void setOndblclick(String ondblclick) {
      this.getStateHelper().put(HtmlForm.PropertyKeys.ondblclick, ondblclick);
      this.handleAttribute("ondblclick", ondblclick);
   }

   public String getOnkeydown() {
      return (String)this.getStateHelper().eval(HtmlForm.PropertyKeys.onkeydown);
   }

   public void setOnkeydown(String onkeydown) {
      this.getStateHelper().put(HtmlForm.PropertyKeys.onkeydown, onkeydown);
      this.handleAttribute("onkeydown", onkeydown);
   }

   public String getOnkeypress() {
      return (String)this.getStateHelper().eval(HtmlForm.PropertyKeys.onkeypress);
   }

   public void setOnkeypress(String onkeypress) {
      this.getStateHelper().put(HtmlForm.PropertyKeys.onkeypress, onkeypress);
      this.handleAttribute("onkeypress", onkeypress);
   }

   public String getOnkeyup() {
      return (String)this.getStateHelper().eval(HtmlForm.PropertyKeys.onkeyup);
   }

   public void setOnkeyup(String onkeyup) {
      this.getStateHelper().put(HtmlForm.PropertyKeys.onkeyup, onkeyup);
      this.handleAttribute("onkeyup", onkeyup);
   }

   public String getOnmousedown() {
      return (String)this.getStateHelper().eval(HtmlForm.PropertyKeys.onmousedown);
   }

   public void setOnmousedown(String onmousedown) {
      this.getStateHelper().put(HtmlForm.PropertyKeys.onmousedown, onmousedown);
      this.handleAttribute("onmousedown", onmousedown);
   }

   public String getOnmousemove() {
      return (String)this.getStateHelper().eval(HtmlForm.PropertyKeys.onmousemove);
   }

   public void setOnmousemove(String onmousemove) {
      this.getStateHelper().put(HtmlForm.PropertyKeys.onmousemove, onmousemove);
      this.handleAttribute("onmousemove", onmousemove);
   }

   public String getOnmouseout() {
      return (String)this.getStateHelper().eval(HtmlForm.PropertyKeys.onmouseout);
   }

   public void setOnmouseout(String onmouseout) {
      this.getStateHelper().put(HtmlForm.PropertyKeys.onmouseout, onmouseout);
      this.handleAttribute("onmouseout", onmouseout);
   }

   public String getOnmouseover() {
      return (String)this.getStateHelper().eval(HtmlForm.PropertyKeys.onmouseover);
   }

   public void setOnmouseover(String onmouseover) {
      this.getStateHelper().put(HtmlForm.PropertyKeys.onmouseover, onmouseover);
      this.handleAttribute("onmouseover", onmouseover);
   }

   public String getOnmouseup() {
      return (String)this.getStateHelper().eval(HtmlForm.PropertyKeys.onmouseup);
   }

   public void setOnmouseup(String onmouseup) {
      this.getStateHelper().put(HtmlForm.PropertyKeys.onmouseup, onmouseup);
      this.handleAttribute("onmouseup", onmouseup);
   }

   public String getOnreset() {
      return (String)this.getStateHelper().eval(HtmlForm.PropertyKeys.onreset);
   }

   public void setOnreset(String onreset) {
      this.getStateHelper().put(HtmlForm.PropertyKeys.onreset, onreset);
      this.handleAttribute("onreset", onreset);
   }

   public String getOnsubmit() {
      return (String)this.getStateHelper().eval(HtmlForm.PropertyKeys.onsubmit);
   }

   public void setOnsubmit(String onsubmit) {
      this.getStateHelper().put(HtmlForm.PropertyKeys.onsubmit, onsubmit);
      this.handleAttribute("onsubmit", onsubmit);
   }

   public String getRole() {
      return (String)this.getStateHelper().eval(HtmlForm.PropertyKeys.role);
   }

   public void setRole(String role) {
      this.getStateHelper().put(HtmlForm.PropertyKeys.role, role);
      this.handleAttribute("role", role);
   }

   public String getStyle() {
      return (String)this.getStateHelper().eval(HtmlForm.PropertyKeys.style);
   }

   public void setStyle(String style) {
      this.getStateHelper().put(HtmlForm.PropertyKeys.style, style);
      this.handleAttribute("style", style);
   }

   public String getStyleClass() {
      return (String)this.getStateHelper().eval(HtmlForm.PropertyKeys.styleClass);
   }

   public void setStyleClass(String styleClass) {
      this.getStateHelper().put(HtmlForm.PropertyKeys.styleClass, styleClass);
   }

   public String getTarget() {
      return (String)this.getStateHelper().eval(HtmlForm.PropertyKeys.target);
   }

   public void setTarget(String target) {
      this.getStateHelper().put(HtmlForm.PropertyKeys.target, target);
      this.handleAttribute("target", target);
   }

   public String getTitle() {
      return (String)this.getStateHelper().eval(HtmlForm.PropertyKeys.title);
   }

   public void setTitle(String title) {
      this.getStateHelper().put(HtmlForm.PropertyKeys.title, title);
      this.handleAttribute("title", title);
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
      accept,
      acceptcharset,
      dir,
      enctype,
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
      onreset,
      onsubmit,
      role,
      style,
      styleClass,
      target,
      title;

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
