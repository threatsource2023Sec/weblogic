package javax.faces.component.html;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import javax.el.ValueExpression;
import javax.faces.component.UIOutcomeTarget;
import javax.faces.component.behavior.ClientBehaviorHolder;

public class HtmlOutcomeTargetLink extends UIOutcomeTarget implements ClientBehaviorHolder {
   private static final String OPTIMIZED_PACKAGE = "javax.faces.component.";
   public static final String COMPONENT_TYPE = "javax.faces.HtmlOutcomeTargetLink";
   private static final Collection EVENT_NAMES = Collections.unmodifiableCollection(Arrays.asList("blur", "click", "action", "dblclick", "focus", "keydown", "keypress", "keyup", "mousedown", "mousemove", "mouseout", "mouseover", "mouseup"));

   public HtmlOutcomeTargetLink() {
      this.setRendererType("javax.faces.Link");
   }

   public String getAccesskey() {
      return (String)this.getStateHelper().eval(HtmlOutcomeTargetLink.PropertyKeys.accesskey);
   }

   public void setAccesskey(String accesskey) {
      this.getStateHelper().put(HtmlOutcomeTargetLink.PropertyKeys.accesskey, accesskey);
      this.handleAttribute("accesskey", accesskey);
   }

   public String getCharset() {
      return (String)this.getStateHelper().eval(HtmlOutcomeTargetLink.PropertyKeys.charset);
   }

   public void setCharset(String charset) {
      this.getStateHelper().put(HtmlOutcomeTargetLink.PropertyKeys.charset, charset);
      this.handleAttribute("charset", charset);
   }

   public String getCoords() {
      return (String)this.getStateHelper().eval(HtmlOutcomeTargetLink.PropertyKeys.coords);
   }

   public void setCoords(String coords) {
      this.getStateHelper().put(HtmlOutcomeTargetLink.PropertyKeys.coords, coords);
      this.handleAttribute("coords", coords);
   }

   public String getDir() {
      return (String)this.getStateHelper().eval(HtmlOutcomeTargetLink.PropertyKeys.dir);
   }

   public void setDir(String dir) {
      this.getStateHelper().put(HtmlOutcomeTargetLink.PropertyKeys.dir, dir);
      this.handleAttribute("dir", dir);
   }

   public boolean isDisabled() {
      return (Boolean)this.getStateHelper().eval(HtmlOutcomeTargetLink.PropertyKeys.disabled, false);
   }

   public void setDisabled(boolean disabled) {
      this.getStateHelper().put(HtmlOutcomeTargetLink.PropertyKeys.disabled, disabled);
   }

   public String getHreflang() {
      return (String)this.getStateHelper().eval(HtmlOutcomeTargetLink.PropertyKeys.hreflang);
   }

   public void setHreflang(String hreflang) {
      this.getStateHelper().put(HtmlOutcomeTargetLink.PropertyKeys.hreflang, hreflang);
      this.handleAttribute("hreflang", hreflang);
   }

   public String getLang() {
      return (String)this.getStateHelper().eval(HtmlOutcomeTargetLink.PropertyKeys.lang);
   }

   public void setLang(String lang) {
      this.getStateHelper().put(HtmlOutcomeTargetLink.PropertyKeys.lang, lang);
      this.handleAttribute("lang", lang);
   }

   public String getOnblur() {
      return (String)this.getStateHelper().eval(HtmlOutcomeTargetLink.PropertyKeys.onblur);
   }

   public void setOnblur(String onblur) {
      this.getStateHelper().put(HtmlOutcomeTargetLink.PropertyKeys.onblur, onblur);
      this.handleAttribute("onblur", onblur);
   }

   public String getOnclick() {
      return (String)this.getStateHelper().eval(HtmlOutcomeTargetLink.PropertyKeys.onclick);
   }

   public void setOnclick(String onclick) {
      this.getStateHelper().put(HtmlOutcomeTargetLink.PropertyKeys.onclick, onclick);
   }

   public String getOndblclick() {
      return (String)this.getStateHelper().eval(HtmlOutcomeTargetLink.PropertyKeys.ondblclick);
   }

   public void setOndblclick(String ondblclick) {
      this.getStateHelper().put(HtmlOutcomeTargetLink.PropertyKeys.ondblclick, ondblclick);
      this.handleAttribute("ondblclick", ondblclick);
   }

   public String getOnfocus() {
      return (String)this.getStateHelper().eval(HtmlOutcomeTargetLink.PropertyKeys.onfocus);
   }

   public void setOnfocus(String onfocus) {
      this.getStateHelper().put(HtmlOutcomeTargetLink.PropertyKeys.onfocus, onfocus);
      this.handleAttribute("onfocus", onfocus);
   }

   public String getOnkeydown() {
      return (String)this.getStateHelper().eval(HtmlOutcomeTargetLink.PropertyKeys.onkeydown);
   }

   public void setOnkeydown(String onkeydown) {
      this.getStateHelper().put(HtmlOutcomeTargetLink.PropertyKeys.onkeydown, onkeydown);
      this.handleAttribute("onkeydown", onkeydown);
   }

   public String getOnkeypress() {
      return (String)this.getStateHelper().eval(HtmlOutcomeTargetLink.PropertyKeys.onkeypress);
   }

   public void setOnkeypress(String onkeypress) {
      this.getStateHelper().put(HtmlOutcomeTargetLink.PropertyKeys.onkeypress, onkeypress);
      this.handleAttribute("onkeypress", onkeypress);
   }

   public String getOnkeyup() {
      return (String)this.getStateHelper().eval(HtmlOutcomeTargetLink.PropertyKeys.onkeyup);
   }

   public void setOnkeyup(String onkeyup) {
      this.getStateHelper().put(HtmlOutcomeTargetLink.PropertyKeys.onkeyup, onkeyup);
      this.handleAttribute("onkeyup", onkeyup);
   }

   public String getOnmousedown() {
      return (String)this.getStateHelper().eval(HtmlOutcomeTargetLink.PropertyKeys.onmousedown);
   }

   public void setOnmousedown(String onmousedown) {
      this.getStateHelper().put(HtmlOutcomeTargetLink.PropertyKeys.onmousedown, onmousedown);
      this.handleAttribute("onmousedown", onmousedown);
   }

   public String getOnmousemove() {
      return (String)this.getStateHelper().eval(HtmlOutcomeTargetLink.PropertyKeys.onmousemove);
   }

   public void setOnmousemove(String onmousemove) {
      this.getStateHelper().put(HtmlOutcomeTargetLink.PropertyKeys.onmousemove, onmousemove);
      this.handleAttribute("onmousemove", onmousemove);
   }

   public String getOnmouseout() {
      return (String)this.getStateHelper().eval(HtmlOutcomeTargetLink.PropertyKeys.onmouseout);
   }

   public void setOnmouseout(String onmouseout) {
      this.getStateHelper().put(HtmlOutcomeTargetLink.PropertyKeys.onmouseout, onmouseout);
      this.handleAttribute("onmouseout", onmouseout);
   }

   public String getOnmouseover() {
      return (String)this.getStateHelper().eval(HtmlOutcomeTargetLink.PropertyKeys.onmouseover);
   }

   public void setOnmouseover(String onmouseover) {
      this.getStateHelper().put(HtmlOutcomeTargetLink.PropertyKeys.onmouseover, onmouseover);
      this.handleAttribute("onmouseover", onmouseover);
   }

   public String getOnmouseup() {
      return (String)this.getStateHelper().eval(HtmlOutcomeTargetLink.PropertyKeys.onmouseup);
   }

   public void setOnmouseup(String onmouseup) {
      this.getStateHelper().put(HtmlOutcomeTargetLink.PropertyKeys.onmouseup, onmouseup);
      this.handleAttribute("onmouseup", onmouseup);
   }

   public String getRel() {
      return (String)this.getStateHelper().eval(HtmlOutcomeTargetLink.PropertyKeys.rel);
   }

   public void setRel(String rel) {
      this.getStateHelper().put(HtmlOutcomeTargetLink.PropertyKeys.rel, rel);
      this.handleAttribute("rel", rel);
   }

   public String getRev() {
      return (String)this.getStateHelper().eval(HtmlOutcomeTargetLink.PropertyKeys.rev);
   }

   public void setRev(String rev) {
      this.getStateHelper().put(HtmlOutcomeTargetLink.PropertyKeys.rev, rev);
      this.handleAttribute("rev", rev);
   }

   public String getRole() {
      return (String)this.getStateHelper().eval(HtmlOutcomeTargetLink.PropertyKeys.role);
   }

   public void setRole(String role) {
      this.getStateHelper().put(HtmlOutcomeTargetLink.PropertyKeys.role, role);
      this.handleAttribute("role", role);
   }

   public String getShape() {
      return (String)this.getStateHelper().eval(HtmlOutcomeTargetLink.PropertyKeys.shape);
   }

   public void setShape(String shape) {
      this.getStateHelper().put(HtmlOutcomeTargetLink.PropertyKeys.shape, shape);
      this.handleAttribute("shape", shape);
   }

   public String getStyle() {
      return (String)this.getStateHelper().eval(HtmlOutcomeTargetLink.PropertyKeys.style);
   }

   public void setStyle(String style) {
      this.getStateHelper().put(HtmlOutcomeTargetLink.PropertyKeys.style, style);
      this.handleAttribute("style", style);
   }

   public String getStyleClass() {
      return (String)this.getStateHelper().eval(HtmlOutcomeTargetLink.PropertyKeys.styleClass);
   }

   public void setStyleClass(String styleClass) {
      this.getStateHelper().put(HtmlOutcomeTargetLink.PropertyKeys.styleClass, styleClass);
   }

   public String getTabindex() {
      return (String)this.getStateHelper().eval(HtmlOutcomeTargetLink.PropertyKeys.tabindex);
   }

   public void setTabindex(String tabindex) {
      this.getStateHelper().put(HtmlOutcomeTargetLink.PropertyKeys.tabindex, tabindex);
      this.handleAttribute("tabindex", tabindex);
   }

   public String getTarget() {
      return (String)this.getStateHelper().eval(HtmlOutcomeTargetLink.PropertyKeys.target);
   }

   public void setTarget(String target) {
      this.getStateHelper().put(HtmlOutcomeTargetLink.PropertyKeys.target, target);
   }

   public String getTitle() {
      return (String)this.getStateHelper().eval(HtmlOutcomeTargetLink.PropertyKeys.title);
   }

   public void setTitle(String title) {
      this.getStateHelper().put(HtmlOutcomeTargetLink.PropertyKeys.title, title);
      this.handleAttribute("title", title);
   }

   public String getType() {
      return (String)this.getStateHelper().eval(HtmlOutcomeTargetLink.PropertyKeys.type);
   }

   public void setType(String type) {
      this.getStateHelper().put(HtmlOutcomeTargetLink.PropertyKeys.type, type);
      this.handleAttribute("type", type);
   }

   public Collection getEventNames() {
      return EVENT_NAMES;
   }

   public String getDefaultEventName() {
      return "action";
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
      charset,
      coords,
      dir,
      disabled,
      hreflang,
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
      rel,
      rev,
      role,
      shape,
      style,
      styleClass,
      tabindex,
      target,
      title,
      type;

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
