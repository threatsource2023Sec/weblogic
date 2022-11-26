package javax.faces.component.html;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import javax.el.ValueExpression;
import javax.faces.component.UISelectMany;
import javax.faces.component.behavior.ClientBehaviorHolder;

public class HtmlSelectManyListbox extends UISelectMany implements ClientBehaviorHolder {
   private static final String OPTIMIZED_PACKAGE = "javax.faces.component.";
   public static final String COMPONENT_TYPE = "javax.faces.HtmlSelectManyListbox";
   private static final Collection EVENT_NAMES = Collections.unmodifiableCollection(Arrays.asList("blur", "change", "valueChange", "click", "dblclick", "focus", "keydown", "keypress", "keyup", "mousedown", "mousemove", "mouseout", "mouseover", "mouseup"));

   public HtmlSelectManyListbox() {
      this.setRendererType("javax.faces.Listbox");
   }

   public String getAccesskey() {
      return (String)this.getStateHelper().eval(HtmlSelectManyListbox.PropertyKeys.accesskey);
   }

   public void setAccesskey(String accesskey) {
      this.getStateHelper().put(HtmlSelectManyListbox.PropertyKeys.accesskey, accesskey);
      this.handleAttribute("accesskey", accesskey);
   }

   public String getDir() {
      return (String)this.getStateHelper().eval(HtmlSelectManyListbox.PropertyKeys.dir);
   }

   public void setDir(String dir) {
      this.getStateHelper().put(HtmlSelectManyListbox.PropertyKeys.dir, dir);
      this.handleAttribute("dir", dir);
   }

   public boolean isDisabled() {
      return (Boolean)this.getStateHelper().eval(HtmlSelectManyListbox.PropertyKeys.disabled, false);
   }

   public void setDisabled(boolean disabled) {
      this.getStateHelper().put(HtmlSelectManyListbox.PropertyKeys.disabled, disabled);
   }

   public String getDisabledClass() {
      return (String)this.getStateHelper().eval(HtmlSelectManyListbox.PropertyKeys.disabledClass);
   }

   public void setDisabledClass(String disabledClass) {
      this.getStateHelper().put(HtmlSelectManyListbox.PropertyKeys.disabledClass, disabledClass);
   }

   public String getEnabledClass() {
      return (String)this.getStateHelper().eval(HtmlSelectManyListbox.PropertyKeys.enabledClass);
   }

   public void setEnabledClass(String enabledClass) {
      this.getStateHelper().put(HtmlSelectManyListbox.PropertyKeys.enabledClass, enabledClass);
   }

   public String getLabel() {
      return (String)this.getStateHelper().eval(HtmlSelectManyListbox.PropertyKeys.label);
   }

   public void setLabel(String label) {
      this.getStateHelper().put(HtmlSelectManyListbox.PropertyKeys.label, label);
   }

   public String getLang() {
      return (String)this.getStateHelper().eval(HtmlSelectManyListbox.PropertyKeys.lang);
   }

   public void setLang(String lang) {
      this.getStateHelper().put(HtmlSelectManyListbox.PropertyKeys.lang, lang);
      this.handleAttribute("lang", lang);
   }

   public String getOnblur() {
      return (String)this.getStateHelper().eval(HtmlSelectManyListbox.PropertyKeys.onblur);
   }

   public void setOnblur(String onblur) {
      this.getStateHelper().put(HtmlSelectManyListbox.PropertyKeys.onblur, onblur);
      this.handleAttribute("onblur", onblur);
   }

   public String getOnchange() {
      return (String)this.getStateHelper().eval(HtmlSelectManyListbox.PropertyKeys.onchange);
   }

   public void setOnchange(String onchange) {
      this.getStateHelper().put(HtmlSelectManyListbox.PropertyKeys.onchange, onchange);
   }

   public String getOnclick() {
      return (String)this.getStateHelper().eval(HtmlSelectManyListbox.PropertyKeys.onclick);
   }

   public void setOnclick(String onclick) {
      this.getStateHelper().put(HtmlSelectManyListbox.PropertyKeys.onclick, onclick);
      this.handleAttribute("onclick", onclick);
   }

   public String getOndblclick() {
      return (String)this.getStateHelper().eval(HtmlSelectManyListbox.PropertyKeys.ondblclick);
   }

   public void setOndblclick(String ondblclick) {
      this.getStateHelper().put(HtmlSelectManyListbox.PropertyKeys.ondblclick, ondblclick);
      this.handleAttribute("ondblclick", ondblclick);
   }

   public String getOnfocus() {
      return (String)this.getStateHelper().eval(HtmlSelectManyListbox.PropertyKeys.onfocus);
   }

   public void setOnfocus(String onfocus) {
      this.getStateHelper().put(HtmlSelectManyListbox.PropertyKeys.onfocus, onfocus);
      this.handleAttribute("onfocus", onfocus);
   }

   public String getOnkeydown() {
      return (String)this.getStateHelper().eval(HtmlSelectManyListbox.PropertyKeys.onkeydown);
   }

   public void setOnkeydown(String onkeydown) {
      this.getStateHelper().put(HtmlSelectManyListbox.PropertyKeys.onkeydown, onkeydown);
      this.handleAttribute("onkeydown", onkeydown);
   }

   public String getOnkeypress() {
      return (String)this.getStateHelper().eval(HtmlSelectManyListbox.PropertyKeys.onkeypress);
   }

   public void setOnkeypress(String onkeypress) {
      this.getStateHelper().put(HtmlSelectManyListbox.PropertyKeys.onkeypress, onkeypress);
      this.handleAttribute("onkeypress", onkeypress);
   }

   public String getOnkeyup() {
      return (String)this.getStateHelper().eval(HtmlSelectManyListbox.PropertyKeys.onkeyup);
   }

   public void setOnkeyup(String onkeyup) {
      this.getStateHelper().put(HtmlSelectManyListbox.PropertyKeys.onkeyup, onkeyup);
      this.handleAttribute("onkeyup", onkeyup);
   }

   public String getOnmousedown() {
      return (String)this.getStateHelper().eval(HtmlSelectManyListbox.PropertyKeys.onmousedown);
   }

   public void setOnmousedown(String onmousedown) {
      this.getStateHelper().put(HtmlSelectManyListbox.PropertyKeys.onmousedown, onmousedown);
      this.handleAttribute("onmousedown", onmousedown);
   }

   public String getOnmousemove() {
      return (String)this.getStateHelper().eval(HtmlSelectManyListbox.PropertyKeys.onmousemove);
   }

   public void setOnmousemove(String onmousemove) {
      this.getStateHelper().put(HtmlSelectManyListbox.PropertyKeys.onmousemove, onmousemove);
      this.handleAttribute("onmousemove", onmousemove);
   }

   public String getOnmouseout() {
      return (String)this.getStateHelper().eval(HtmlSelectManyListbox.PropertyKeys.onmouseout);
   }

   public void setOnmouseout(String onmouseout) {
      this.getStateHelper().put(HtmlSelectManyListbox.PropertyKeys.onmouseout, onmouseout);
      this.handleAttribute("onmouseout", onmouseout);
   }

   public String getOnmouseover() {
      return (String)this.getStateHelper().eval(HtmlSelectManyListbox.PropertyKeys.onmouseover);
   }

   public void setOnmouseover(String onmouseover) {
      this.getStateHelper().put(HtmlSelectManyListbox.PropertyKeys.onmouseover, onmouseover);
      this.handleAttribute("onmouseover", onmouseover);
   }

   public String getOnmouseup() {
      return (String)this.getStateHelper().eval(HtmlSelectManyListbox.PropertyKeys.onmouseup);
   }

   public void setOnmouseup(String onmouseup) {
      this.getStateHelper().put(HtmlSelectManyListbox.PropertyKeys.onmouseup, onmouseup);
      this.handleAttribute("onmouseup", onmouseup);
   }

   public boolean isReadonly() {
      return (Boolean)this.getStateHelper().eval(HtmlSelectManyListbox.PropertyKeys.readonly, false);
   }

   public void setReadonly(boolean readonly) {
      this.getStateHelper().put(HtmlSelectManyListbox.PropertyKeys.readonly, readonly);
   }

   public String getRole() {
      return (String)this.getStateHelper().eval(HtmlSelectManyListbox.PropertyKeys.role);
   }

   public void setRole(String role) {
      this.getStateHelper().put(HtmlSelectManyListbox.PropertyKeys.role, role);
      this.handleAttribute("role", role);
   }

   public int getSize() {
      return (Integer)this.getStateHelper().eval(HtmlSelectManyListbox.PropertyKeys.size, Integer.MIN_VALUE);
   }

   public void setSize(int size) {
      this.getStateHelper().put(HtmlSelectManyListbox.PropertyKeys.size, size);
   }

   public String getStyle() {
      return (String)this.getStateHelper().eval(HtmlSelectManyListbox.PropertyKeys.style);
   }

   public void setStyle(String style) {
      this.getStateHelper().put(HtmlSelectManyListbox.PropertyKeys.style, style);
      this.handleAttribute("style", style);
   }

   public String getStyleClass() {
      return (String)this.getStateHelper().eval(HtmlSelectManyListbox.PropertyKeys.styleClass);
   }

   public void setStyleClass(String styleClass) {
      this.getStateHelper().put(HtmlSelectManyListbox.PropertyKeys.styleClass, styleClass);
   }

   public String getTabindex() {
      return (String)this.getStateHelper().eval(HtmlSelectManyListbox.PropertyKeys.tabindex);
   }

   public void setTabindex(String tabindex) {
      this.getStateHelper().put(HtmlSelectManyListbox.PropertyKeys.tabindex, tabindex);
      this.handleAttribute("tabindex", tabindex);
   }

   public String getTitle() {
      return (String)this.getStateHelper().eval(HtmlSelectManyListbox.PropertyKeys.title);
   }

   public void setTitle(String title) {
      this.getStateHelper().put(HtmlSelectManyListbox.PropertyKeys.title, title);
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
      dir,
      disabled,
      disabledClass,
      enabledClass,
      label,
      lang,
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
