package javax.faces.component.html;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import javax.el.ValueExpression;
import javax.faces.component.UIInput;
import javax.faces.component.behavior.ClientBehaviorHolder;

public class HtmlInputText extends UIInput implements ClientBehaviorHolder {
   private static final String OPTIMIZED_PACKAGE = "javax.faces.component.";
   public static final String COMPONENT_TYPE = "javax.faces.HtmlInputText";
   private static final Collection EVENT_NAMES = Collections.unmodifiableCollection(Arrays.asList("blur", "change", "valueChange", "click", "dblclick", "focus", "keydown", "keypress", "keyup", "mousedown", "mousemove", "mouseout", "mouseover", "mouseup", "select"));

   public HtmlInputText() {
      this.setRendererType("javax.faces.Text");
   }

   public String getAccesskey() {
      return (String)this.getStateHelper().eval(HtmlInputText.PropertyKeys.accesskey);
   }

   public void setAccesskey(String accesskey) {
      this.getStateHelper().put(HtmlInputText.PropertyKeys.accesskey, accesskey);
      this.handleAttribute("accesskey", accesskey);
   }

   public String getAlt() {
      return (String)this.getStateHelper().eval(HtmlInputText.PropertyKeys.alt);
   }

   public void setAlt(String alt) {
      this.getStateHelper().put(HtmlInputText.PropertyKeys.alt, alt);
      this.handleAttribute("alt", alt);
   }

   public String getAutocomplete() {
      return (String)this.getStateHelper().eval(HtmlInputText.PropertyKeys.autocomplete);
   }

   public void setAutocomplete(String autocomplete) {
      this.getStateHelper().put(HtmlInputText.PropertyKeys.autocomplete, autocomplete);
   }

   public String getDir() {
      return (String)this.getStateHelper().eval(HtmlInputText.PropertyKeys.dir);
   }

   public void setDir(String dir) {
      this.getStateHelper().put(HtmlInputText.PropertyKeys.dir, dir);
      this.handleAttribute("dir", dir);
   }

   public boolean isDisabled() {
      return (Boolean)this.getStateHelper().eval(HtmlInputText.PropertyKeys.disabled, false);
   }

   public void setDisabled(boolean disabled) {
      this.getStateHelper().put(HtmlInputText.PropertyKeys.disabled, disabled);
   }

   public String getLabel() {
      return (String)this.getStateHelper().eval(HtmlInputText.PropertyKeys.label);
   }

   public void setLabel(String label) {
      this.getStateHelper().put(HtmlInputText.PropertyKeys.label, label);
   }

   public String getLang() {
      return (String)this.getStateHelper().eval(HtmlInputText.PropertyKeys.lang);
   }

   public void setLang(String lang) {
      this.getStateHelper().put(HtmlInputText.PropertyKeys.lang, lang);
      this.handleAttribute("lang", lang);
   }

   public int getMaxlength() {
      return (Integer)this.getStateHelper().eval(HtmlInputText.PropertyKeys.maxlength, Integer.MIN_VALUE);
   }

   public void setMaxlength(int maxlength) {
      this.getStateHelper().put(HtmlInputText.PropertyKeys.maxlength, maxlength);
      this.handleAttribute("maxlength", maxlength);
   }

   public String getOnblur() {
      return (String)this.getStateHelper().eval(HtmlInputText.PropertyKeys.onblur);
   }

   public void setOnblur(String onblur) {
      this.getStateHelper().put(HtmlInputText.PropertyKeys.onblur, onblur);
      this.handleAttribute("onblur", onblur);
   }

   public String getOnchange() {
      return (String)this.getStateHelper().eval(HtmlInputText.PropertyKeys.onchange);
   }

   public void setOnchange(String onchange) {
      this.getStateHelper().put(HtmlInputText.PropertyKeys.onchange, onchange);
   }

   public String getOnclick() {
      return (String)this.getStateHelper().eval(HtmlInputText.PropertyKeys.onclick);
   }

   public void setOnclick(String onclick) {
      this.getStateHelper().put(HtmlInputText.PropertyKeys.onclick, onclick);
      this.handleAttribute("onclick", onclick);
   }

   public String getOndblclick() {
      return (String)this.getStateHelper().eval(HtmlInputText.PropertyKeys.ondblclick);
   }

   public void setOndblclick(String ondblclick) {
      this.getStateHelper().put(HtmlInputText.PropertyKeys.ondblclick, ondblclick);
      this.handleAttribute("ondblclick", ondblclick);
   }

   public String getOnfocus() {
      return (String)this.getStateHelper().eval(HtmlInputText.PropertyKeys.onfocus);
   }

   public void setOnfocus(String onfocus) {
      this.getStateHelper().put(HtmlInputText.PropertyKeys.onfocus, onfocus);
      this.handleAttribute("onfocus", onfocus);
   }

   public String getOnkeydown() {
      return (String)this.getStateHelper().eval(HtmlInputText.PropertyKeys.onkeydown);
   }

   public void setOnkeydown(String onkeydown) {
      this.getStateHelper().put(HtmlInputText.PropertyKeys.onkeydown, onkeydown);
      this.handleAttribute("onkeydown", onkeydown);
   }

   public String getOnkeypress() {
      return (String)this.getStateHelper().eval(HtmlInputText.PropertyKeys.onkeypress);
   }

   public void setOnkeypress(String onkeypress) {
      this.getStateHelper().put(HtmlInputText.PropertyKeys.onkeypress, onkeypress);
      this.handleAttribute("onkeypress", onkeypress);
   }

   public String getOnkeyup() {
      return (String)this.getStateHelper().eval(HtmlInputText.PropertyKeys.onkeyup);
   }

   public void setOnkeyup(String onkeyup) {
      this.getStateHelper().put(HtmlInputText.PropertyKeys.onkeyup, onkeyup);
      this.handleAttribute("onkeyup", onkeyup);
   }

   public String getOnmousedown() {
      return (String)this.getStateHelper().eval(HtmlInputText.PropertyKeys.onmousedown);
   }

   public void setOnmousedown(String onmousedown) {
      this.getStateHelper().put(HtmlInputText.PropertyKeys.onmousedown, onmousedown);
      this.handleAttribute("onmousedown", onmousedown);
   }

   public String getOnmousemove() {
      return (String)this.getStateHelper().eval(HtmlInputText.PropertyKeys.onmousemove);
   }

   public void setOnmousemove(String onmousemove) {
      this.getStateHelper().put(HtmlInputText.PropertyKeys.onmousemove, onmousemove);
      this.handleAttribute("onmousemove", onmousemove);
   }

   public String getOnmouseout() {
      return (String)this.getStateHelper().eval(HtmlInputText.PropertyKeys.onmouseout);
   }

   public void setOnmouseout(String onmouseout) {
      this.getStateHelper().put(HtmlInputText.PropertyKeys.onmouseout, onmouseout);
      this.handleAttribute("onmouseout", onmouseout);
   }

   public String getOnmouseover() {
      return (String)this.getStateHelper().eval(HtmlInputText.PropertyKeys.onmouseover);
   }

   public void setOnmouseover(String onmouseover) {
      this.getStateHelper().put(HtmlInputText.PropertyKeys.onmouseover, onmouseover);
      this.handleAttribute("onmouseover", onmouseover);
   }

   public String getOnmouseup() {
      return (String)this.getStateHelper().eval(HtmlInputText.PropertyKeys.onmouseup);
   }

   public void setOnmouseup(String onmouseup) {
      this.getStateHelper().put(HtmlInputText.PropertyKeys.onmouseup, onmouseup);
      this.handleAttribute("onmouseup", onmouseup);
   }

   public String getOnselect() {
      return (String)this.getStateHelper().eval(HtmlInputText.PropertyKeys.onselect);
   }

   public void setOnselect(String onselect) {
      this.getStateHelper().put(HtmlInputText.PropertyKeys.onselect, onselect);
      this.handleAttribute("onselect", onselect);
   }

   public boolean isReadonly() {
      return (Boolean)this.getStateHelper().eval(HtmlInputText.PropertyKeys.readonly, false);
   }

   public void setReadonly(boolean readonly) {
      this.getStateHelper().put(HtmlInputText.PropertyKeys.readonly, readonly);
   }

   public String getRole() {
      return (String)this.getStateHelper().eval(HtmlInputText.PropertyKeys.role);
   }

   public void setRole(String role) {
      this.getStateHelper().put(HtmlInputText.PropertyKeys.role, role);
      this.handleAttribute("role", role);
   }

   public int getSize() {
      return (Integer)this.getStateHelper().eval(HtmlInputText.PropertyKeys.size, Integer.MIN_VALUE);
   }

   public void setSize(int size) {
      this.getStateHelper().put(HtmlInputText.PropertyKeys.size, size);
      this.handleAttribute("size", size);
   }

   public String getStyle() {
      return (String)this.getStateHelper().eval(HtmlInputText.PropertyKeys.style);
   }

   public void setStyle(String style) {
      this.getStateHelper().put(HtmlInputText.PropertyKeys.style, style);
      this.handleAttribute("style", style);
   }

   public String getStyleClass() {
      return (String)this.getStateHelper().eval(HtmlInputText.PropertyKeys.styleClass);
   }

   public void setStyleClass(String styleClass) {
      this.getStateHelper().put(HtmlInputText.PropertyKeys.styleClass, styleClass);
   }

   public String getTabindex() {
      return (String)this.getStateHelper().eval(HtmlInputText.PropertyKeys.tabindex);
   }

   public void setTabindex(String tabindex) {
      this.getStateHelper().put(HtmlInputText.PropertyKeys.tabindex, tabindex);
      this.handleAttribute("tabindex", tabindex);
   }

   public String getTitle() {
      return (String)this.getStateHelper().eval(HtmlInputText.PropertyKeys.title);
   }

   public void setTitle(String title) {
      this.getStateHelper().put(HtmlInputText.PropertyKeys.title, title);
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
