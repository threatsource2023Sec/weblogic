package javax.faces.component.html;

import java.util.ArrayList;
import java.util.List;
import javax.el.ValueExpression;
import javax.faces.component.UIOutput;

public class HtmlOutputFormat extends UIOutput {
   private static final String OPTIMIZED_PACKAGE = "javax.faces.component.";
   public static final String COMPONENT_TYPE = "javax.faces.HtmlOutputFormat";

   public HtmlOutputFormat() {
      this.setRendererType("javax.faces.Format");
   }

   public String getDir() {
      return (String)this.getStateHelper().eval(HtmlOutputFormat.PropertyKeys.dir);
   }

   public void setDir(String dir) {
      this.getStateHelper().put(HtmlOutputFormat.PropertyKeys.dir, dir);
      this.handleAttribute("dir", dir);
   }

   public boolean isEscape() {
      return (Boolean)this.getStateHelper().eval(HtmlOutputFormat.PropertyKeys.escape, true);
   }

   public void setEscape(boolean escape) {
      this.getStateHelper().put(HtmlOutputFormat.PropertyKeys.escape, escape);
   }

   public String getLang() {
      return (String)this.getStateHelper().eval(HtmlOutputFormat.PropertyKeys.lang);
   }

   public void setLang(String lang) {
      this.getStateHelper().put(HtmlOutputFormat.PropertyKeys.lang, lang);
      this.handleAttribute("lang", lang);
   }

   public String getRole() {
      return (String)this.getStateHelper().eval(HtmlOutputFormat.PropertyKeys.role);
   }

   public void setRole(String role) {
      this.getStateHelper().put(HtmlOutputFormat.PropertyKeys.role, role);
      this.handleAttribute("role", role);
   }

   public String getStyle() {
      return (String)this.getStateHelper().eval(HtmlOutputFormat.PropertyKeys.style);
   }

   public void setStyle(String style) {
      this.getStateHelper().put(HtmlOutputFormat.PropertyKeys.style, style);
      this.handleAttribute("style", style);
   }

   public String getStyleClass() {
      return (String)this.getStateHelper().eval(HtmlOutputFormat.PropertyKeys.styleClass);
   }

   public void setStyleClass(String styleClass) {
      this.getStateHelper().put(HtmlOutputFormat.PropertyKeys.styleClass, styleClass);
   }

   public String getTitle() {
      return (String)this.getStateHelper().eval(HtmlOutputFormat.PropertyKeys.title);
   }

   public void setTitle(String title) {
      this.getStateHelper().put(HtmlOutputFormat.PropertyKeys.title, title);
      this.handleAttribute("title", title);
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
      escape,
      lang,
      role,
      style,
      styleClass,
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
