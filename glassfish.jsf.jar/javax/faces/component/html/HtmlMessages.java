package javax.faces.component.html;

import java.util.ArrayList;
import java.util.List;
import javax.el.ValueExpression;
import javax.faces.component.UIMessages;

public class HtmlMessages extends UIMessages {
   private static final String OPTIMIZED_PACKAGE = "javax.faces.component.";
   public static final String COMPONENT_TYPE = "javax.faces.HtmlMessages";

   public HtmlMessages() {
      this.setRendererType("javax.faces.Messages");
   }

   public String getDir() {
      return (String)this.getStateHelper().eval(HtmlMessages.PropertyKeys.dir);
   }

   public void setDir(String dir) {
      this.getStateHelper().put(HtmlMessages.PropertyKeys.dir, dir);
      this.handleAttribute("dir", dir);
   }

   public String getErrorClass() {
      return (String)this.getStateHelper().eval(HtmlMessages.PropertyKeys.errorClass);
   }

   public void setErrorClass(String errorClass) {
      this.getStateHelper().put(HtmlMessages.PropertyKeys.errorClass, errorClass);
   }

   public String getErrorStyle() {
      return (String)this.getStateHelper().eval(HtmlMessages.PropertyKeys.errorStyle);
   }

   public void setErrorStyle(String errorStyle) {
      this.getStateHelper().put(HtmlMessages.PropertyKeys.errorStyle, errorStyle);
   }

   public String getFatalClass() {
      return (String)this.getStateHelper().eval(HtmlMessages.PropertyKeys.fatalClass);
   }

   public void setFatalClass(String fatalClass) {
      this.getStateHelper().put(HtmlMessages.PropertyKeys.fatalClass, fatalClass);
   }

   public String getFatalStyle() {
      return (String)this.getStateHelper().eval(HtmlMessages.PropertyKeys.fatalStyle);
   }

   public void setFatalStyle(String fatalStyle) {
      this.getStateHelper().put(HtmlMessages.PropertyKeys.fatalStyle, fatalStyle);
   }

   public String getInfoClass() {
      return (String)this.getStateHelper().eval(HtmlMessages.PropertyKeys.infoClass);
   }

   public void setInfoClass(String infoClass) {
      this.getStateHelper().put(HtmlMessages.PropertyKeys.infoClass, infoClass);
   }

   public String getInfoStyle() {
      return (String)this.getStateHelper().eval(HtmlMessages.PropertyKeys.infoStyle);
   }

   public void setInfoStyle(String infoStyle) {
      this.getStateHelper().put(HtmlMessages.PropertyKeys.infoStyle, infoStyle);
   }

   public String getLang() {
      return (String)this.getStateHelper().eval(HtmlMessages.PropertyKeys.lang);
   }

   public void setLang(String lang) {
      this.getStateHelper().put(HtmlMessages.PropertyKeys.lang, lang);
      this.handleAttribute("lang", lang);
   }

   public String getLayout() {
      return (String)this.getStateHelper().eval(HtmlMessages.PropertyKeys.layout, "list");
   }

   public void setLayout(String layout) {
      this.getStateHelper().put(HtmlMessages.PropertyKeys.layout, layout);
   }

   public String getRole() {
      return (String)this.getStateHelper().eval(HtmlMessages.PropertyKeys.role);
   }

   public void setRole(String role) {
      this.getStateHelper().put(HtmlMessages.PropertyKeys.role, role);
      this.handleAttribute("role", role);
   }

   public String getStyle() {
      return (String)this.getStateHelper().eval(HtmlMessages.PropertyKeys.style);
   }

   public void setStyle(String style) {
      this.getStateHelper().put(HtmlMessages.PropertyKeys.style, style);
      this.handleAttribute("style", style);
   }

   public String getStyleClass() {
      return (String)this.getStateHelper().eval(HtmlMessages.PropertyKeys.styleClass);
   }

   public void setStyleClass(String styleClass) {
      this.getStateHelper().put(HtmlMessages.PropertyKeys.styleClass, styleClass);
   }

   public String getTitle() {
      return (String)this.getStateHelper().eval(HtmlMessages.PropertyKeys.title);
   }

   public void setTitle(String title) {
      this.getStateHelper().put(HtmlMessages.PropertyKeys.title, title);
      this.handleAttribute("title", title);
   }

   public boolean isTooltip() {
      return (Boolean)this.getStateHelper().eval(HtmlMessages.PropertyKeys.tooltip, false);
   }

   public void setTooltip(boolean tooltip) {
      this.getStateHelper().put(HtmlMessages.PropertyKeys.tooltip, tooltip);
   }

   public String getWarnClass() {
      return (String)this.getStateHelper().eval(HtmlMessages.PropertyKeys.warnClass);
   }

   public void setWarnClass(String warnClass) {
      this.getStateHelper().put(HtmlMessages.PropertyKeys.warnClass, warnClass);
   }

   public String getWarnStyle() {
      return (String)this.getStateHelper().eval(HtmlMessages.PropertyKeys.warnStyle);
   }

   public void setWarnStyle(String warnStyle) {
      this.getStateHelper().put(HtmlMessages.PropertyKeys.warnStyle, warnStyle);
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
      errorClass,
      errorStyle,
      fatalClass,
      fatalStyle,
      infoClass,
      infoStyle,
      lang,
      layout,
      role,
      style,
      styleClass,
      title,
      tooltip,
      warnClass,
      warnStyle;

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
