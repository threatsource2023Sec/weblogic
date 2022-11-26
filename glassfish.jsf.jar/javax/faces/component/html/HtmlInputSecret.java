package javax.faces.component.html;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import javax.el.ValueExpression;
import javax.faces.component.UIInput;
import javax.faces.component.behavior.ClientBehaviorHolder;

public class HtmlInputSecret extends UIInput implements ClientBehaviorHolder {
   private static final String OPTIMIZED_PACKAGE = "javax.faces.component.";
   public static final String COMPONENT_TYPE = "javax.faces.HtmlInputSecret";
   private static final Collection EVENT_NAMES = Collections.unmodifiableCollection(Arrays.asList("blur", "change", "valueChange", "click", "dblclick", "focus", "keydown", "keypress", "keyup", "mousedown", "mousemove", "mouseout", "mouseover", "mouseup", "select"));

   public HtmlInputSecret() {
      this.setRendererType("javax.faces.Secret");
   }

   public String getAccesskey() {
      return (String)this.getStateHelper().eval(HtmlInputSecret.PropertyKeys.accesskey);
   }

   public void setAccesskey(String accesskey) {
      this.getStateHelper().put(HtmlInputSecret.PropertyKeys.accesskey, accesskey);
      this.handleAttribute("accesskey", accesskey);
   }

   public String getAlt() {
      return (String)this.getStateHelper().eval(HtmlInputSecret.PropertyKeys.alt);
   }

   public void setAlt(String alt) {
      this.getStateHelper().put(HtmlInputSecret.PropertyKeys.alt, alt);
      this.handleAttribute("alt", alt);
   }

   public String getAutocomplete() {
      return (String)this.getStateHelper().eval(HtmlInputSecret.PropertyKeys.autocomplete);
   }

   public void setAutocomplete(String autocomplete) {
      this.getStateHelper().put(HtmlInputSecret.PropertyKeys.autocomplete, autocomplete);
   }

   public String getDir() {
      return (String)this.getStateHelper().eval(HtmlInputSecret.PropertyKeys.dir);
   }

   public void setDir(String dir) {
      this.getStateHelper().put(HtmlInputSecret.PropertyKeys.dir, dir);
      this.handleAttribute("dir", dir);
   }

   public boolean isDisabled() {
      return (Boolean)this.getStateHelper().eval(HtmlInputSecret.PropertyKeys.disabled, false);
   }

   public void setDisabled(boolean disabled) {
      this.getStateHelper().put(HtmlInputSecret.PropertyKeys.disabled, disabled);
   }

   public String getLabel() {
      return (String)this.getStateHelper().eval(HtmlInputSecret.PropertyKeys.label);
   }

   public void setLabel(String label) {
      this.getStateHelper().put(HtmlInputSecret.PropertyKeys.label, label);
   }

   public String getLang() {
      return (String)this.getStateHelper().eval(HtmlInputSecret.PropertyKeys.lang);
   }

   public void setLang(String lang) {
      this.getStateHelper().put(HtmlInputSecret.PropertyKeys.lang, lang);
      this.handleAttribute("lang", lang);
   }

   public int getMaxlength() {
      return (Integer)this.getStateHelper().eval(HtmlInputSecret.PropertyKeys.maxlength, Integer.MIN_VALUE);
   }

   public void setMaxlength(int maxlength) {
      this.getStateHelper().put(HtmlInputSecret.PropertyKeys.maxlength, maxlength);
      this.handleAttribute("maxlength", maxlength);
   }

   public String getOnblur() {
      return (String)this.getStateHelper().eval(HtmlInputSecret.PropertyKeys.onblur);
   }

   public void setOnblur(String onblur) {
      this.getStateHelper().put(HtmlInputSecret.PropertyKeys.onblur, onblur);
      this.handleAttribute("onblur", onblur);
   }

   public String getOnchange() {
      return (String)this.getStateHelper().eval(HtmlInputSecret.PropertyKeys.onchange);
   }

   public void setOnchange(String onchange) {
      this.getStateHelper().put(HtmlInputSecret.PropertyKeys.onchange, onchange);
   }

   public String getOnclick() {
      return (String)this.getStateHelper().eval(HtmlInputSecret.PropertyKeys.onclick);
   }

   public void setOnclick(String onclick) {
      this.getStateHelper().put(HtmlInputSecret.PropertyKeys.onclick, onclick);
      this.handleAttribute("onclick", onclick);
   }

   public String getOndblclick() {
      return (String)this.getStateHelper().eval(HtmlInputSecret.PropertyKeys.ondblclick);
   }

   public void setOndblclick(String ondblclick) {
      this.getStateHelper().put(HtmlInputSecret.PropertyKeys.ondblclick, ondblclick);
      this.handleAttribute("ondblclick", ondblclick);
   }

   public String getOnfocus() {
      return (String)this.getStateHelper().eval(HtmlInputSecret.PropertyKeys.onfocus);
   }

   public void setOnfocus(String onfocus) {
      this.getStateHelper().put(HtmlInputSecret.PropertyKeys.onfocus, onfocus);
      this.handleAttribute("onfocus", onfocus);
   }

   public String getOnkeydown() {
      return (String)this.getStateHelper().eval(HtmlInputSecret.PropertyKeys.onkeydown);
   }

   public void setOnkeydown(String onkeydown) {
      this.getStateHelper().put(HtmlInputSecret.PropertyKeys.onkeydown, onkeydown);
      this.handleAttribute("onkeydown", onkeydown);
   }

   public String getOnkeypress() {
      return (String)this.getStateHelper().eval(HtmlInputSecret.PropertyKeys.onkeypress);
   }

   public void setOnkeypress(String onkeypress) {
      this.getStateHelper().put(HtmlInputSecret.PropertyKeys.onkeypress, onkeypress);
      this.handleAttribute("onkeypress", onkeypress);
   }

   public String getOnkeyup() {
      return (String)this.getStateHelper().eval(HtmlInputSecret.PropertyKeys.onkeyup);
   }

   public void setOnkeyup(String onkeyup) {
      this.getStateHelper().put(HtmlInputSecret.PropertyKeys.onkeyup, onkeyup);
      this.handleAttribute("onkeyup", onkeyup);
   }

   public String getOnmousedown() {
      return (String)this.getStateHelper().eval(HtmlInputSecret.PropertyKeys.onmousedown);
   }

   public void setOnmousedown(String onmousedown) {
      this.getStateHelper().put(HtmlInputSecret.PropertyKeys.onmousedown, onmousedown);
      this.handleAttribute("onmousedown", onmousedown);
   }

   public String getOnmousemove() {
      return (String)this.getStateHelper().eval(HtmlInputSecret.PropertyKeys.onmousemove);
   }

   public void setOnmousemove(String onmousemove) {
      this.getStateHelper().put(HtmlInputSecret.PropertyKeys.onmousemove, onmousemove);
      this.handleAttribute("onmousemove", onmousemove);
   }

   public String getOnmouseout() {
      return (String)this.getStateHelper().eval(HtmlInputSecret.PropertyKeys.onmouseout);
   }

   public void setOnmouseout(String onmouseout) {
      this.getStateHelper().put(HtmlInputSecret.PropertyKeys.onmouseout, onmouseout);
      this.handleAttribute("onmouseout", onmouseout);
   }

   public String getOnmouseover() {
      return (String)this.getStateHelper().eval(HtmlInputSecret.PropertyKeys.onmouseover);
   }

   public void setOnmouseover(String onmouseover) {
      this.getStateHelper().put(HtmlInputSecret.PropertyKeys.onmouseover, onmouseover);
      this.handleAttribute("onmouseover", onmouseover);
   }

   public String getOnmouseup() {
      return (String)this.getStateHelper().eval(HtmlInputSecret.PropertyKeys.onmouseup);
   }

   public void setOnmouseup(String onmouseup) {
      this.getStateHelper().put(HtmlInputSecret.PropertyKeys.onmouseup, onmouseup);
      this.handleAttribute("onmouseup", onmouseup);
   }

   public String getOnselect() {
      return (String)this.getStateHelper().eval(HtmlInputSecret.PropertyKeys.onselect);
   }

   public void setOnselect(String onselect) {
      this.getStateHelper().put(HtmlInputSecret.PropertyKeys.onselect, onselect);
      this.handleAttribute("onselect", onselect);
   }

   public boolean isReadonly() {
      return (Boolean)this.getStateHelper().eval(HtmlInputSecret.PropertyKeys.readonly, false);
   }

   public void setReadonly(boolean readonly) {
      this.getStateHelper().put(HtmlInputSecret.PropertyKeys.readonly, readonly);
   }

   public boolean isRedisplay() {
      return (Boolean)this.getStateHelper().eval(HtmlInputSecret.PropertyKeys.redisplay, false);
   }

   public void setRedisplay(boolean redisplay) {
      this.getStateHelper().put(HtmlInputSecret.PropertyKeys.redisplay, redisplay);
   }

   public String getRole() {
      return (String)this.getStateHelper().eval(HtmlInputSecret.PropertyKeys.role);
   }

   public void setRole(String role) {
      this.getStateHelper().put(HtmlInputSecret.PropertyKeys.role, role);
      this.handleAttribute("role", role);
   }

   public int getSize() {
      return (Integer)this.getStateHelper().eval(HtmlInputSecret.PropertyKeys.size, Integer.MIN_VALUE);
   }

   public void setSize(int size) {
      this.getStateHelper().put(HtmlInputSecret.PropertyKeys.size, size);
      this.handleAttribute("size", size);
   }

   public String getStyle() {
      return (String)this.getStateHelper().eval(HtmlInputSecret.PropertyKeys.style);
   }

   public void setStyle(String style) {
      this.getStateHelper().put(HtmlInputSecret.PropertyKeys.style, style);
      this.handleAttribute("style", style);
   }

   public String getStyleClass() {
      return (String)this.getStateHelper().eval(HtmlInputSecret.PropertyKeys.styleClass);
   }

   public void setStyleClass(String styleClass) {
      this.getStateHelper().put(HtmlInputSecret.PropertyKeys.styleClass, styleClass);
   }

   public String getTabindex() {
      return (String)this.getStateHelper().eval(HtmlInputSecret.PropertyKeys.tabindex);
   }

   public void setTabindex(String tabindex) {
      this.getStateHelper().put(HtmlInputSecret.PropertyKeys.tabindex, tabindex);
      this.handleAttribute("tabindex", tabindex);
   }

   public String getTitle() {
      return (String)this.getStateHelper().eval(HtmlInputSecret.PropertyKeys.title);
   }

   public void setTitle(String title) {
      this.getStateHelper().put(HtmlInputSecret.PropertyKeys.title, title);
      this.handleAttribute("title", title);
   }

   public Collection getEventNames() {
      return EVENT_NAMES;
   }

   public String getDefaultEventName() {
      return "valueChange";
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
      autocomplete,
      dir,
      disabled,
      label,
      lang,
      maxlength,
      onblur,
      onchange,
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
      onselect,
      readonly,
      redisplay,
      role,
      size,
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
