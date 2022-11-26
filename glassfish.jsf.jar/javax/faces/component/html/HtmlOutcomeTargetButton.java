package javax.faces.component.html;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import javax.el.ValueExpression;
import javax.faces.component.UIOutcomeTarget;
import javax.faces.component.behavior.ClientBehaviorHolder;

public class HtmlOutcomeTargetButton extends UIOutcomeTarget implements ClientBehaviorHolder {
   private static final String OPTIMIZED_PACKAGE = "javax.faces.component.";
   public static final String COMPONENT_TYPE = "javax.faces.HtmlOutcomeTargetButton";
   private static final Collection EVENT_NAMES = Collections.unmodifiableCollection(Arrays.asList("blur", "click", "dblclick", "focus", "keydown", "keypress", "keyup", "mousedown", "mousemove", "mouseout", "mouseover", "mouseup"));

   public HtmlOutcomeTargetButton() {
      this.setRendererType("javax.faces.Button");
   }

   public String getAccesskey() {
      return (String)this.getStateHelper().eval(HtmlOutcomeTargetButton.PropertyKeys.accesskey);
   }

   public void setAccesskey(String accesskey) {
      this.getStateHelper().put(HtmlOutcomeTargetButton.PropertyKeys.accesskey, accesskey);
      this.handleAttribute("accesskey", accesskey);
   }

   public String getAlt() {
      return (String)this.getStateHelper().eval(HtmlOutcomeTargetButton.PropertyKeys.alt);
   }

   public void setAlt(String alt) {
      this.getStateHelper().put(HtmlOutcomeTargetButton.PropertyKeys.alt, alt);
      this.handleAttribute("alt", alt);
   }

   public String getDir() {
      return (String)this.getStateHelper().eval(HtmlOutcomeTargetButton.PropertyKeys.dir);
   }

   public void setDir(String dir) {
      this.getStateHelper().put(HtmlOutcomeTargetButton.PropertyKeys.dir, dir);
      this.handleAttribute("dir", dir);
   }

   public boolean isDisabled() {
      return (Boolean)this.getStateHelper().eval(HtmlOutcomeTargetButton.PropertyKeys.disabled, false);
   }

   public void setDisabled(boolean disabled) {
      this.getStateHelper().put(HtmlOutcomeTargetButton.PropertyKeys.disabled, disabled);
   }

   public String getImage() {
      return (String)this.getStateHelper().eval(HtmlOutcomeTargetButton.PropertyKeys.image);
   }

   public void setImage(String image) {
      this.getStateHelper().put(HtmlOutcomeTargetButton.PropertyKeys.image, image);
   }

   public String getLang() {
      return (String)this.getStateHelper().eval(HtmlOutcomeTargetButton.PropertyKeys.lang);
   }

   public void setLang(String lang) {
      this.getStateHelper().put(HtmlOutcomeTargetButton.PropertyKeys.lang, lang);
      this.handleAttribute("lang", lang);
   }

   public String getOnblur() {
      return (String)this.getStateHelper().eval(HtmlOutcomeTargetButton.PropertyKeys.onblur);
   }

   public void setOnblur(String onblur) {
      this.getStateHelper().put(HtmlOutcomeTargetButton.PropertyKeys.onblur, onblur);
      this.handleAttribute("onblur", onblur);
   }

   public String getOnclick() {
      return (String)this.getStateHelper().eval(HtmlOutcomeTargetButton.PropertyKeys.onclick);
   }

   public void setOnclick(String onclick) {
      this.getStateHelper().put(HtmlOutcomeTargetButton.PropertyKeys.onclick, onclick);
      this.handleAttribute("onclick", onclick);
   }

   public String getOndblclick() {
      return (String)this.getStateHelper().eval(HtmlOutcomeTargetButton.PropertyKeys.ondblclick);
   }

   public void setOndblclick(String ondblclick) {
      this.getStateHelper().put(HtmlOutcomeTargetButton.PropertyKeys.ondblclick, ondblclick);
      this.handleAttribute("ondblclick", ondblclick);
   }

   public String getOnfocus() {
      return (String)this.getStateHelper().eval(HtmlOutcomeTargetButton.PropertyKeys.onfocus);
   }

   public void setOnfocus(String onfocus) {
      this.getStateHelper().put(HtmlOutcomeTargetButton.PropertyKeys.onfocus, onfocus);
      this.handleAttribute("onfocus", onfocus);
   }

   public String getOnkeydown() {
      return (String)this.getStateHelper().eval(HtmlOutcomeTargetButton.PropertyKeys.onkeydown);
   }

   public void setOnkeydown(String onkeydown) {
      this.getStateHelper().put(HtmlOutcomeTargetButton.PropertyKeys.onkeydown, onkeydown);
      this.handleAttribute("onkeydown", onkeydown);
   }

   public String getOnkeypress() {
      return (String)this.getStateHelper().eval(HtmlOutcomeTargetButton.PropertyKeys.onkeypress);
   }

   public void setOnkeypress(String onkeypress) {
      this.getStateHelper().put(HtmlOutcomeTargetButton.PropertyKeys.onkeypress, onkeypress);
      this.handleAttribute("onkeypress", onkeypress);
   }

   public String getOnkeyup() {
      return (String)this.getStateHelper().eval(HtmlOutcomeTargetButton.PropertyKeys.onkeyup);
   }

   public void setOnkeyup(String onkeyup) {
      this.getStateHelper().put(HtmlOutcomeTargetButton.PropertyKeys.onkeyup, onkeyup);
      this.handleAttribute("onkeyup", onkeyup);
   }

   public String getOnmousedown() {
      return (String)this.getStateHelper().eval(HtmlOutcomeTargetButton.PropertyKeys.onmousedown);
   }

   public void setOnmousedown(String onmousedown) {
      this.getStateHelper().put(HtmlOutcomeTargetButton.PropertyKeys.onmousedown, onmousedown);
      this.handleAttribute("onmousedown", onmousedown);
   }

   public String getOnmousemove() {
      return (String)this.getStateHelper().eval(HtmlOutcomeTargetButton.PropertyKeys.onmousemove);
   }

   public void setOnmousemove(String onmousemove) {
      this.getStateHelper().put(HtmlOutcomeTargetButton.PropertyKeys.onmousemove, onmousemove);
      this.handleAttribute("onmousemove", onmousemove);
   }

   public String getOnmouseout() {
      return (String)this.getStateHelper().eval(HtmlOutcomeTargetButton.PropertyKeys.onmouseout);
   }

   public void setOnmouseout(String onmouseout) {
      this.getStateHelper().put(HtmlOutcomeTargetButton.PropertyKeys.onmouseout, onmouseout);
      this.handleAttribute("onmouseout", onmouseout);
   }

   public String getOnmouseover() {
      return (String)this.getStateHelper().eval(HtmlOutcomeTargetButton.PropertyKeys.onmouseover);
   }

   public void setOnmouseover(String onmouseover) {
      this.getStateHelper().put(HtmlOutcomeTargetButton.PropertyKeys.onmouseover, onmouseover);
      this.handleAttribute("onmouseover", onmouseover);
   }

   public String getOnmouseup() {
      return (String)this.getStateHelper().eval(HtmlOutcomeTargetButton.PropertyKeys.onmouseup);
   }

   public void setOnmouseup(String onmouseup) {
      this.getStateHelper().put(HtmlOutcomeTargetButton.PropertyKeys.onmouseup, onmouseup);
      this.handleAttribute("onmouseup", onmouseup);
   }

   public String getRole() {
      return (String)this.getStateHelper().eval(HtmlOutcomeTargetButton.PropertyKeys.role);
   }

   public void setRole(String role) {
      this.getStateHelper().put(HtmlOutcomeTargetButton.PropertyKeys.role, role);
      this.handleAttribute("role", role);
   }

   public String getStyle() {
      return (String)this.getStateHelper().eval(HtmlOutcomeTargetButton.PropertyKeys.style);
   }

   public void setStyle(String style) {
      this.getStateHelper().put(HtmlOutcomeTargetButton.PropertyKeys.style, style);
      this.handleAttribute("style", style);
   }

   public String getStyleClass() {
      return (String)this.getStateHelper().eval(HtmlOutcomeTargetButton.PropertyKeys.styleClass);
   }

   public void setStyleClass(String styleClass) {
      this.getStateHelper().put(HtmlOutcomeTargetButton.PropertyKeys.styleClass, styleClass);
   }

   public String getTabindex() {
      return (String)this.getStateHelper().eval(HtmlOutcomeTargetButton.PropertyKeys.tabindex);
   }

   public void setTabindex(String tabindex) {
      this.getStateHelper().put(HtmlOutcomeTargetButton.PropertyKeys.tabindex, tabindex);
      this.handleAttribute("tabindex", tabindex);
   }

   public String getTitle() {
      return (String)this.getStateHelper().eval(HtmlOutcomeTargetButton.PropertyKeys.title);
   }

   public void setTitle(String title) {
      this.getStateHelper().put(HtmlOutcomeTargetButton.PropertyKeys.title, title);
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
      accesskey,
      alt,
      dir,
      disabled,
      image,
      lang,
      onblur,
      onclick,
      ondblclick,
      onfocus,
      onkeydown,
      onkeypress,
      onkeyup,
      onmousedown,
      onmousemove,
      onmouseout,
      onmouseover,
      onmouseup,
      role,
      style,
      styleClass,
      tabindex,
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
