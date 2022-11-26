package javax.faces.component.html;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import javax.el.ValueExpression;
import javax.faces.component.UIOutput;
import javax.faces.component.behavior.ClientBehaviorHolder;

public class HtmlOutputLink extends UIOutput implements ClientBehaviorHolder {
   private static final String OPTIMIZED_PACKAGE = "javax.faces.component.";
   public static final String COMPONENT_TYPE = "javax.faces.HtmlOutputLink";
   private static final Collection EVENT_NAMES = Collections.unmodifiableCollection(Arrays.asList("blur", "click", "action", "dblclick", "focus", "keydown", "keypress", "keyup", "mousedown", "mousemove", "mouseout", "mouseover", "mouseup"));

   public HtmlOutputLink() {
      this.setRendererType("javax.faces.Link");
   }

   public String getAccesskey() {
      return (String)this.getStateHelper().eval(HtmlOutputLink.PropertyKeys.accesskey);
   }

   public void setAccesskey(String accesskey) {
      this.getStateHelper().put(HtmlOutputLink.PropertyKeys.accesskey, accesskey);
      this.handleAttribute("accesskey", accesskey);
   }

   public String getCharset() {
      return (String)this.getStateHelper().eval(HtmlOutputLink.PropertyKeys.charset);
   }

   public void setCharset(String charset) {
      this.getStateHelper().put(HtmlOutputLink.PropertyKeys.charset, charset);
      this.handleAttribute("charset", charset);
   }

   public String getCoords() {
      return (String)this.getStateHelper().eval(HtmlOutputLink.PropertyKeys.coords);
   }

   public void setCoords(String coords) {
      this.getStateHelper().put(HtmlOutputLink.PropertyKeys.coords, coords);
      this.handleAttribute("coords", coords);
   }

   public String getDir() {
      return (String)this.getStateHelper().eval(HtmlOutputLink.PropertyKeys.dir);
   }

   public void setDir(String dir) {
      this.getStateHelper().put(HtmlOutputLink.PropertyKeys.dir, dir);
      this.handleAttribute("dir", dir);
   }

   public boolean isDisabled() {
      return (Boolean)this.getStateHelper().eval(HtmlOutputLink.PropertyKeys.disabled, false);
   }

   public void setDisabled(boolean disabled) {
      this.getStateHelper().put(HtmlOutputLink.PropertyKeys.disabled, disabled);
   }

   public String getFragment() {
      return (String)this.getStateHelper().eval(HtmlOutputLink.PropertyKeys.fragment);
   }

   public void setFragment(String fragment) {
      this.getStateHelper().put(HtmlOutputLink.PropertyKeys.fragment, fragment);
   }

   public String getHreflang() {
      return (String)this.getStateHelper().eval(HtmlOutputLink.PropertyKeys.hreflang);
   }

   public void setHreflang(String hreflang) {
      this.getStateHelper().put(HtmlOutputLink.PropertyKeys.hreflang, hreflang);
      this.handleAttribute("hreflang", hreflang);
   }

   public String getLang() {
      return (String)this.getStateHelper().eval(HtmlOutputLink.PropertyKeys.lang);
   }

   public void setLang(String lang) {
      this.getStateHelper().put(HtmlOutputLink.PropertyKeys.lang, lang);
      this.handleAttribute("lang", lang);
   }

   public String getOnblur() {
      return (String)this.getStateHelper().eval(HtmlOutputLink.PropertyKeys.onblur);
   }

   public void setOnblur(String onblur) {
      this.getStateHelper().put(HtmlOutputLink.PropertyKeys.onblur, onblur);
      this.handleAttribute("onblur", onblur);
   }

   public String getOnclick() {
      return (String)this.getStateHelper().eval(HtmlOutputLink.PropertyKeys.onclick);
   }

   public void setOnclick(String onclick) {
      this.getStateHelper().put(HtmlOutputLink.PropertyKeys.onclick, onclick);
      this.handleAttribute("onclick", onclick);
   }

   public String getOndblclick() {
      return (String)this.getStateHelper().eval(HtmlOutputLink.PropertyKeys.ondblclick);
   }

   public void setOndblclick(String ondblclick) {
      this.getStateHelper().put(HtmlOutputLink.PropertyKeys.ondblclick, ondblclick);
      this.handleAttribute("ondblclick", ondblclick);
   }

   public String getOnfocus() {
      return (String)this.getStateHelper().eval(HtmlOutputLink.PropertyKeys.onfocus);
   }

   public void setOnfocus(String onfocus) {
      this.getStateHelper().put(HtmlOutputLink.PropertyKeys.onfocus, onfocus);
      this.handleAttribute("onfocus", onfocus);
   }

   public String getOnkeydown() {
      return (String)this.getStateHelper().eval(HtmlOutputLink.PropertyKeys.onkeydown);
   }

   public void setOnkeydown(String onkeydown) {
      this.getStateHelper().put(HtmlOutputLink.PropertyKeys.onkeydown, onkeydown);
      this.handleAttribute("onkeydown", onkeydown);
   }

   public String getOnkeypress() {
      return (String)this.getStateHelper().eval(HtmlOutputLink.PropertyKeys.onkeypress);
   }

   public void setOnkeypress(String onkeypress) {
      this.getStateHelper().put(HtmlOutputLink.PropertyKeys.onkeypress, onkeypress);
      this.handleAttribute("onkeypress", onkeypress);
   }

   public String getOnkeyup() {
      return (String)this.getStateHelper().eval(HtmlOutputLink.PropertyKeys.onkeyup);
   }

   public void setOnkeyup(String onkeyup) {
      this.getStateHelper().put(HtmlOutputLink.PropertyKeys.onkeyup, onkeyup);
      this.handleAttribute("onkeyup", onkeyup);
   }

   public String getOnmousedown() {
      return (String)this.getStateHelper().eval(HtmlOutputLink.PropertyKeys.onmousedown);
   }

   public void setOnmousedown(String onmousedown) {
      this.getStateHelper().put(HtmlOutputLink.PropertyKeys.onmousedown, onmousedown);
      this.handleAttribute("onmousedown", onmousedown);
   }

   public String getOnmousemove() {
      return (String)this.getStateHelper().eval(HtmlOutputLink.PropertyKeys.onmousemove);
   }

   public void setOnmousemove(String onmousemove) {
      this.getStateHelper().put(HtmlOutputLink.PropertyKeys.onmousemove, onmousemove);
      this.handleAttribute("onmousemove", onmousemove);
   }

   public String getOnmouseout() {
      return (String)this.getStateHelper().eval(HtmlOutputLink.PropertyKeys.onmouseout);
   }

   public void setOnmouseout(String onmouseout) {
      this.getStateHelper().put(HtmlOutputLink.PropertyKeys.onmouseout, onmouseout);
      this.handleAttribute("onmouseout", onmouseout);
   }

   public String getOnmouseover() {
      return (String)this.getStateHelper().eval(HtmlOutputLink.PropertyKeys.onmouseover);
   }

   public void setOnmouseover(String onmouseover) {
      this.getStateHelper().put(HtmlOutputLink.PropertyKeys.onmouseover, onmouseover);
      this.handleAttribute("onmouseover", onmouseover);
   }

   public String getOnmouseup() {
      return (String)this.getStateHelper().eval(HtmlOutputLink.PropertyKeys.onmouseup);
   }

   public void setOnmouseup(String onmouseup) {
      this.getStateHelper().put(HtmlOutputLink.PropertyKeys.onmouseup, onmouseup);
      this.handleAttribute("onmouseup", onmouseup);
   }

   public String getRel() {
      return (String)this.getStateHelper().eval(HtmlOutputLink.PropertyKeys.rel);
   }

   public void setRel(String rel) {
      this.getStateHelper().put(HtmlOutputLink.PropertyKeys.rel, rel);
      this.handleAttribute("rel", rel);
   }

   public String getRev() {
      return (String)this.getStateHelper().eval(HtmlOutputLink.PropertyKeys.rev);
   }

   public void setRev(String rev) {
      this.getStateHelper().put(HtmlOutputLink.PropertyKeys.rev, rev);
      this.handleAttribute("rev", rev);
   }

   public String getRole() {
      return (String)this.getStateHelper().eval(HtmlOutputLink.PropertyKeys.role);
   }

   public void setRole(String role) {
      this.getStateHelper().put(HtmlOutputLink.PropertyKeys.role, role);
      this.handleAttribute("role", role);
   }

   public String getShape() {
      return (String)this.getStateHelper().eval(HtmlOutputLink.PropertyKeys.shape);
   }

   public void setShape(String shape) {
      this.getStateHelper().put(HtmlOutputLink.PropertyKeys.shape, shape);
      this.handleAttribute("shape", shape);
   }

   public String getStyle() {
      return (String)this.getStateHelper().eval(HtmlOutputLink.PropertyKeys.style);
   }

   public void setStyle(String style) {
      this.getStateHelper().put(HtmlOutputLink.PropertyKeys.style, style);
      this.handleAttribute("style", style);
   }

   public String getStyleClass() {
      return (String)this.getStateHelper().eval(HtmlOutputLink.PropertyKeys.styleClass);
   }

   public void setStyleClass(String styleClass) {
      this.getStateHelper().put(HtmlOutputLink.PropertyKeys.styleClass, styleClass);
   }

   public String getTabindex() {
      return (String)this.getStateHelper().eval(HtmlOutputLink.PropertyKeys.tabindex);
   }

   public void setTabindex(String tabindex) {
      this.getStateHelper().put(HtmlOutputLink.PropertyKeys.tabindex, tabindex);
      this.handleAttribute("tabindex", tabindex);
   }

   public String getTarget() {
      return (String)this.getStateHelper().eval(HtmlOutputLink.PropertyKeys.target);
   }

   public void setTarget(String target) {
      this.getStateHelper().put(HtmlOutputLink.PropertyKeys.target, target);
   }

   public String getTitle() {
      return (String)this.getStateHelper().eval(HtmlOutputLink.PropertyKeys.title);
   }

   public void setTitle(String title) {
      this.getStateHelper().put(HtmlOutputLink.PropertyKeys.title, title);
      this.handleAttribute("title", title);
   }

   public String getType() {
      return (String)this.getStateHelper().eval(HtmlOutputLink.PropertyKeys.type);
   }

   public void setType(String type) {
      this.getStateHelper().put(HtmlOutputLink.PropertyKeys.type, type);
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
      fragment,
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
