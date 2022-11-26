package javax.faces.component.html;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import javax.el.ValueExpression;
import javax.faces.component.UISelectMany;
import javax.faces.component.behavior.ClientBehaviorHolder;

public class HtmlSelectManyCheckbox extends UISelectMany implements ClientBehaviorHolder {
   private static final String OPTIMIZED_PACKAGE = "javax.faces.component.";
   public static final String COMPONENT_TYPE = "javax.faces.HtmlSelectManyCheckbox";
   private static final Collection EVENT_NAMES = Collections.unmodifiableCollection(Arrays.asList("blur", "change", "click", "valueChange", "dblclick", "focus", "keydown", "keypress", "keyup", "mousedown", "mousemove", "mouseout", "mouseover", "mouseup", "select"));

   public HtmlSelectManyCheckbox() {
      this.setRendererType("javax.faces.Checkbox");
   }

   public String getAccesskey() {
      return (String)this.getStateHelper().eval(HtmlSelectManyCheckbox.PropertyKeys.accesskey);
   }

   public void setAccesskey(String accesskey) {
      this.getStateHelper().put(HtmlSelectManyCheckbox.PropertyKeys.accesskey, accesskey);
      this.handleAttribute("accesskey", accesskey);
   }

   public int getBorder() {
      return (Integer)this.getStateHelper().eval(HtmlSelectManyCheckbox.PropertyKeys.border, Integer.MIN_VALUE);
   }

   public void setBorder(int border) {
      this.getStateHelper().put(HtmlSelectManyCheckbox.PropertyKeys.border, border);
      this.handleAttribute("border", border);
   }

   public String getDir() {
      return (String)this.getStateHelper().eval(HtmlSelectManyCheckbox.PropertyKeys.dir);
   }

   public void setDir(String dir) {
      this.getStateHelper().put(HtmlSelectManyCheckbox.PropertyKeys.dir, dir);
      this.handleAttribute("dir", dir);
   }

   public boolean isDisabled() {
      return (Boolean)this.getStateHelper().eval(HtmlSelectManyCheckbox.PropertyKeys.disabled, false);
   }

   public void setDisabled(boolean disabled) {
      this.getStateHelper().put(HtmlSelectManyCheckbox.PropertyKeys.disabled, disabled);
   }

   public String getDisabledClass() {
      return (String)this.getStateHelper().eval(HtmlSelectManyCheckbox.PropertyKeys.disabledClass);
   }

   public void setDisabledClass(String disabledClass) {
      this.getStateHelper().put(HtmlSelectManyCheckbox.PropertyKeys.disabledClass, disabledClass);
   }

   public String getEnabledClass() {
      return (String)this.getStateHelper().eval(HtmlSelectManyCheckbox.PropertyKeys.enabledClass);
   }

   public void setEnabledClass(String enabledClass) {
      this.getStateHelper().put(HtmlSelectManyCheckbox.PropertyKeys.enabledClass, enabledClass);
   }

   public String getLabel() {
      return (String)this.getStateHelper().eval(HtmlSelectManyCheckbox.PropertyKeys.label);
   }

   public void setLabel(String label) {
      this.getStateHelper().put(HtmlSelectManyCheckbox.PropertyKeys.label, label);
   }

   public String getLang() {
      return (String)this.getStateHelper().eval(HtmlSelectManyCheckbox.PropertyKeys.lang);
   }

   public void setLang(String lang) {
      this.getStateHelper().put(HtmlSelectManyCheckbox.PropertyKeys.lang, lang);
      this.handleAttribute("lang", lang);
   }

   public String getLayout() {
      return (String)this.getStateHelper().eval(HtmlSelectManyCheckbox.PropertyKeys.layout);
   }

   public void setLayout(String layout) {
      this.getStateHelper().put(HtmlSelectManyCheckbox.PropertyKeys.layout, layout);
   }

   public String getOnblur() {
      return (String)this.getStateHelper().eval(HtmlSelectManyCheckbox.PropertyKeys.onblur);
   }

   public void setOnblur(String onblur) {
      this.getStateHelper().put(HtmlSelectManyCheckbox.PropertyKeys.onblur, onblur);
      this.handleAttribute("onblur", onblur);
   }

   public String getOnchange() {
      return (String)this.getStateHelper().eval(HtmlSelectManyCheckbox.PropertyKeys.onchange);
   }

   public void setOnchange(String onchange) {
      this.getStateHelper().put(HtmlSelectManyCheckbox.PropertyKeys.onchange, onchange);
      this.handleAttribute("onchange", onchange);
   }

   public String getOnclick() {
      return (String)this.getStateHelper().eval(HtmlSelectManyCheckbox.PropertyKeys.onclick);
   }

   public void setOnclick(String onclick) {
      this.getStateHelper().put(HtmlSelectManyCheckbox.PropertyKeys.onclick, onclick);
   }

   public String getOndblclick() {
      return (String)this.getStateHelper().eval(HtmlSelectManyCheckbox.PropertyKeys.ondblclick);
   }

   public void setOndblclick(String ondblclick) {
      this.getStateHelper().put(HtmlSelectManyCheckbox.PropertyKeys.ondblclick, ondblclick);
      this.handleAttribute("ondblclick", ondblclick);
   }

   public String getOnfocus() {
      return (String)this.getStateHelper().eval(HtmlSelectManyCheckbox.PropertyKeys.onfocus);
   }

   public void setOnfocus(String onfocus) {
      this.getStateHelper().put(HtmlSelectManyCheckbox.PropertyKeys.onfocus, onfocus);
      this.handleAttribute("onfocus", onfocus);
   }

   public String getOnkeydown() {
      return (String)this.getStateHelper().eval(HtmlSelectManyCheckbox.PropertyKeys.onkeydown);
   }

   public void setOnkeydown(String onkeydown) {
      this.getStateHelper().put(HtmlSelectManyCheckbox.PropertyKeys.onkeydown, onkeydown);
      this.handleAttribute("onkeydown", onkeydown);
   }

   public String getOnkeypress() {
      return (String)this.getStateHelper().eval(HtmlSelectManyCheckbox.PropertyKeys.onkeypress);
   }

   public void setOnkeypress(String onkeypress) {
      this.getStateHelper().put(HtmlSelectManyCheckbox.PropertyKeys.onkeypress, onkeypress);
      this.handleAttribute("onkeypress", onkeypress);
   }

   public String getOnkeyup() {
      return (String)this.getStateHelper().eval(HtmlSelectManyCheckbox.PropertyKeys.onkeyup);
   }

   public void setOnkeyup(String onkeyup) {
      this.getStateHelper().put(HtmlSelectManyCheckbox.PropertyKeys.onkeyup, onkeyup);
      this.handleAttribute("onkeyup", onkeyup);
   }

   public String getOnmousedown() {
      return (String)this.getStateHelper().eval(HtmlSelectManyCheckbox.PropertyKeys.onmousedown);
   }

   public void setOnmousedown(String onmousedown) {
      this.getStateHelper().put(HtmlSelectManyCheckbox.PropertyKeys.onmousedown, onmousedown);
      this.handleAttribute("onmousedown", onmousedown);
   }

   public String getOnmousemove() {
      return (String)this.getStateHelper().eval(HtmlSelectManyCheckbox.PropertyKeys.onmousemove);
   }

   public void setOnmousemove(String onmousemove) {
      this.getStateHelper().put(HtmlSelectManyCheckbox.PropertyKeys.onmousemove, onmousemove);
      this.handleAttribute("onmousemove", onmousemove);
   }

   public String getOnmouseout() {
      return (String)this.getStateHelper().eval(HtmlSelectManyCheckbox.PropertyKeys.onmouseout);
   }

   public void setOnmouseout(String onmouseout) {
      this.getStateHelper().put(HtmlSelectManyCheckbox.PropertyKeys.onmouseout, onmouseout);
      this.handleAttribute("onmouseout", onmouseout);
   }

   public String getOnmouseover() {
      return (String)this.getStateHelper().eval(HtmlSelectManyCheckbox.PropertyKeys.onmouseover);
   }

   public void setOnmouseover(String onmouseover) {
      this.getStateHelper().put(HtmlSelectManyCheckbox.PropertyKeys.onmouseover, onmouseover);
      this.handleAttribute("onmouseover", onmouseover);
   }

   public String getOnmouseup() {
      return (String)this.getStateHelper().eval(HtmlSelectManyCheckbox.PropertyKeys.onmouseup);
   }

   public void setOnmouseup(String onmouseup) {
      this.getStateHelper().put(HtmlSelectManyCheckbox.PropertyKeys.onmouseup, onmouseup);
      this.handleAttribute("onmouseup", onmouseup);
   }

   public String getOnselect() {
      return (String)this.getStateHelper().eval(HtmlSelectManyCheckbox.PropertyKeys.onselect);
   }

   public void setOnselect(String onselect) {
      this.getStateHelper().put(HtmlSelectManyCheckbox.PropertyKeys.onselect, onselect);
      this.handleAttribute("onselect", onselect);
   }

   public boolean isReadonly() {
      return (Boolean)this.getStateHelper().eval(HtmlSelectManyCheckbox.PropertyKeys.readonly, false);
   }

   public void setReadonly(boolean readonly) {
      this.getStateHelper().put(HtmlSelectManyCheckbox.PropertyKeys.readonly, readonly);
   }

   public String getRole() {
      return (String)this.getStateHelper().eval(HtmlSelectManyCheckbox.PropertyKeys.role);
   }

   public void setRole(String role) {
      this.getStateHelper().put(HtmlSelectManyCheckbox.PropertyKeys.role, role);
      this.handleAttribute("role", role);
   }

   public String getSelectedClass() {
      return (String)this.getStateHelper().eval(HtmlSelectManyCheckbox.PropertyKeys.selectedClass);
   }

   public void setSelectedClass(String selectedClass) {
      this.getStateHelper().put(HtmlSelectManyCheckbox.PropertyKeys.selectedClass, selectedClass);
   }

   public String getStyle() {
      return (String)this.getStateHelper().eval(HtmlSelectManyCheckbox.PropertyKeys.style);
   }

   public void setStyle(String style) {
      this.getStateHelper().put(HtmlSelectManyCheckbox.PropertyKeys.style, style);
      this.handleAttribute("style", style);
   }

   public String getStyleClass() {
      return (String)this.getStateHelper().eval(HtmlSelectManyCheckbox.PropertyKeys.styleClass);
   }

   public void setStyleClass(String styleClass) {
      this.getStateHelper().put(HtmlSelectManyCheckbox.PropertyKeys.styleClass, styleClass);
   }

   public String getTabindex() {
      return (String)this.getStateHelper().eval(HtmlSelectManyCheckbox.PropertyKeys.tabindex);
   }

   public void setTabindex(String tabindex) {
      this.getStateHelper().put(HtmlSelectManyCheckbox.PropertyKeys.tabindex, tabindex);
      this.handleAttribute("tabindex", tabindex);
   }

   public String getTitle() {
      return (String)this.getStateHelper().eval(HtmlSelectManyCheckbox.PropertyKeys.title);
   }

   public void setTitle(String title) {
      this.getStateHelper().put(HtmlSelectManyCheckbox.PropertyKeys.title, title);
      this.handleAttribute("title", title);
   }

   public String getUnselectedClass() {
      return (String)this.getStateHelper().eval(HtmlSelectManyCheckbox.PropertyKeys.unselectedClass);
   }

   public void setUnselectedClass(String unselectedClass) {
      this.getStateHelper().put(HtmlSelectManyCheckbox.PropertyKeys.unselectedClass, unselectedClass);
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
      border,
      dir,
      disabled,
      disabledClass,
      enabledClass,
      label,
      lang,
      layout,
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
      selectedClass,
      style,
      styleClass,
      tabindex,
      title,
      unselectedClass;

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
