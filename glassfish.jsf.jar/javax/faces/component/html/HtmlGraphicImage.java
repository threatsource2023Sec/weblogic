package javax.faces.component.html;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import javax.el.ValueExpression;
import javax.faces.component.UIGraphic;
import javax.faces.component.behavior.ClientBehaviorHolder;

public class HtmlGraphicImage extends UIGraphic implements ClientBehaviorHolder {
   private static final String OPTIMIZED_PACKAGE = "javax.faces.component.";
   public static final String COMPONENT_TYPE = "javax.faces.HtmlGraphicImage";
   private static final Collection EVENT_NAMES = Collections.unmodifiableCollection(Arrays.asList("click", "dblclick", "keydown", "keypress", "keyup", "mousedown", "mousemove", "mouseout", "mouseover", "mouseup"));

   public HtmlGraphicImage() {
      this.setRendererType("javax.faces.Image");
   }

   public String getAlt() {
      return (String)this.getStateHelper().eval(HtmlGraphicImage.PropertyKeys.alt);
   }

   public void setAlt(String alt) {
      this.getStateHelper().put(HtmlGraphicImage.PropertyKeys.alt, alt);
      this.handleAttribute("alt", alt);
   }

   public String getDir() {
      return (String)this.getStateHelper().eval(HtmlGraphicImage.PropertyKeys.dir);
   }

   public void setDir(String dir) {
      this.getStateHelper().put(HtmlGraphicImage.PropertyKeys.dir, dir);
      this.handleAttribute("dir", dir);
   }

   public String getHeight() {
      return (String)this.getStateHelper().eval(HtmlGraphicImage.PropertyKeys.height);
   }

   public void setHeight(String height) {
      this.getStateHelper().put(HtmlGraphicImage.PropertyKeys.height, height);
      this.handleAttribute("height", height);
   }

   public boolean isIsmap() {
      return (Boolean)this.getStateHelper().eval(HtmlGraphicImage.PropertyKeys.ismap, false);
   }

   public void setIsmap(boolean ismap) {
      this.getStateHelper().put(HtmlGraphicImage.PropertyKeys.ismap, ismap);
   }

   public String getLang() {
      return (String)this.getStateHelper().eval(HtmlGraphicImage.PropertyKeys.lang);
   }

   public void setLang(String lang) {
      this.getStateHelper().put(HtmlGraphicImage.PropertyKeys.lang, lang);
      this.handleAttribute("lang", lang);
   }

   public String getLongdesc() {
      return (String)this.getStateHelper().eval(HtmlGraphicImage.PropertyKeys.longdesc);
   }

   public void setLongdesc(String longdesc) {
      this.getStateHelper().put(HtmlGraphicImage.PropertyKeys.longdesc, longdesc);
      this.handleAttribute("longdesc", longdesc);
   }

   public String getOnclick() {
      return (String)this.getStateHelper().eval(HtmlGraphicImage.PropertyKeys.onclick);
   }

   public void setOnclick(String onclick) {
      this.getStateHelper().put(HtmlGraphicImage.PropertyKeys.onclick, onclick);
      this.handleAttribute("onclick", onclick);
   }

   public String getOndblclick() {
      return (String)this.getStateHelper().eval(HtmlGraphicImage.PropertyKeys.ondblclick);
   }

   public void setOndblclick(String ondblclick) {
      this.getStateHelper().put(HtmlGraphicImage.PropertyKeys.ondblclick, ondblclick);
      this.handleAttribute("ondblclick", ondblclick);
   }

   public String getOnkeydown() {
      return (String)this.getStateHelper().eval(HtmlGraphicImage.PropertyKeys.onkeydown);
   }

   public void setOnkeydown(String onkeydown) {
      this.getStateHelper().put(HtmlGraphicImage.PropertyKeys.onkeydown, onkeydown);
      this.handleAttribute("onkeydown", onkeydown);
   }

   public String getOnkeypress() {
      return (String)this.getStateHelper().eval(HtmlGraphicImage.PropertyKeys.onkeypress);
   }

   public void setOnkeypress(String onkeypress) {
      this.getStateHelper().put(HtmlGraphicImage.PropertyKeys.onkeypress, onkeypress);
      this.handleAttribute("onkeypress", onkeypress);
   }

   public String getOnkeyup() {
      return (String)this.getStateHelper().eval(HtmlGraphicImage.PropertyKeys.onkeyup);
   }

   public void setOnkeyup(String onkeyup) {
      this.getStateHelper().put(HtmlGraphicImage.PropertyKeys.onkeyup, onkeyup);
      this.handleAttribute("onkeyup", onkeyup);
   }

   public String getOnmousedown() {
      return (String)this.getStateHelper().eval(HtmlGraphicImage.PropertyKeys.onmousedown);
   }

   public void setOnmousedown(String onmousedown) {
      this.getStateHelper().put(HtmlGraphicImage.PropertyKeys.onmousedown, onmousedown);
      this.handleAttribute("onmousedown", onmousedown);
   }

   public String getOnmousemove() {
      return (String)this.getStateHelper().eval(HtmlGraphicImage.PropertyKeys.onmousemove);
   }

   public void setOnmousemove(String onmousemove) {
      this.getStateHelper().put(HtmlGraphicImage.PropertyKeys.onmousemove, onmousemove);
      this.handleAttribute("onmousemove", onmousemove);
   }

   public String getOnmouseout() {
      return (String)this.getStateHelper().eval(HtmlGraphicImage.PropertyKeys.onmouseout);
   }

   public void setOnmouseout(String onmouseout) {
      this.getStateHelper().put(HtmlGraphicImage.PropertyKeys.onmouseout, onmouseout);
      this.handleAttribute("onmouseout", onmouseout);
   }

   public String getOnmouseover() {
      return (String)this.getStateHelper().eval(HtmlGraphicImage.PropertyKeys.onmouseover);
   }

   public void setOnmouseover(String onmouseover) {
      this.getStateHelper().put(HtmlGraphicImage.PropertyKeys.onmouseover, onmouseover);
      this.handleAttribute("onmouseover", onmouseover);
   }

   public String getOnmouseup() {
      return (String)this.getStateHelper().eval(HtmlGraphicImage.PropertyKeys.onmouseup);
   }

   public void setOnmouseup(String onmouseup) {
      this.getStateHelper().put(HtmlGraphicImage.PropertyKeys.onmouseup, onmouseup);
      this.handleAttribute("onmouseup", onmouseup);
   }

   public String getRole() {
      return (String)this.getStateHelper().eval(HtmlGraphicImage.PropertyKeys.role);
   }

   public void setRole(String role) {
      this.getStateHelper().put(HtmlGraphicImage.PropertyKeys.role, role);
      this.handleAttribute("role", role);
   }

   public String getStyle() {
      return (String)this.getStateHelper().eval(HtmlGraphicImage.PropertyKeys.style);
   }

   public void setStyle(String style) {
      this.getStateHelper().put(HtmlGraphicImage.PropertyKeys.style, style);
      this.handleAttribute("style", style);
   }

   public String getStyleClass() {
      return (String)this.getStateHelper().eval(HtmlGraphicImage.PropertyKeys.styleClass);
   }

   public void setStyleClass(String styleClass) {
      this.getStateHelper().put(HtmlGraphicImage.PropertyKeys.styleClass, styleClass);
   }

   public String getTitle() {
      return (String)this.getStateHelper().eval(HtmlGraphicImage.PropertyKeys.title);
   }

   public void setTitle(String title) {
      this.getStateHelper().put(HtmlGraphicImage.PropertyKeys.title, title);
      this.handleAttribute("title", title);
   }

   public String getUsemap() {
      return (String)this.getStateHelper().eval(HtmlGraphicImage.PropertyKeys.usemap);
   }

   public void setUsemap(String usemap) {
      this.getStateHelper().put(HtmlGraphicImage.PropertyKeys.usemap, usemap);
      this.handleAttribute("usemap", usemap);
   }

   public String getWidth() {
      return (String)this.getStateHelper().eval(HtmlGraphicImage.PropertyKeys.width);
   }

   public void setWidth(String width) {
      this.getStateHelper().put(HtmlGraphicImage.PropertyKeys.width, width);
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
      alt,
      dir,
      height,
      ismap,
      lang,
      longdesc,
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
      style,
      styleClass,
      title,
      usemap,
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
