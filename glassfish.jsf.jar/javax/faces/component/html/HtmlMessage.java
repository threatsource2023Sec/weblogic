package javax.faces.component.html;

import java.util.ArrayList;
import java.util.List;
import javax.el.ValueExpression;
import javax.faces.component.UIMessage;

public class HtmlMessage extends UIMessage {
   private static final String OPTIMIZED_PACKAGE = "javax.faces.component.";
   public static final String COMPONENT_TYPE = "javax.faces.HtmlMessage";

   public HtmlMessage() {
      this.setRendererType("javax.faces.Message");
   }

   public String getDir() {
      return (String)this.getStateHelper().eval(HtmlMessage.PropertyKeys.dir);
   }

   public void setDir(String dir) {
      this.getStateHelper().put(HtmlMessage.PropertyKeys.dir, dir);
      this.handleAttribute("dir", dir);
   }

   public String getErrorClass() {
      return (String)this.getStateHelper().eval(HtmlMessage.PropertyKeys.errorClass);
   }

   public void setErrorClass(String errorClass) {
      this.getStateHelper().put(HtmlMessage.PropertyKeys.errorClass, errorClass);
   }

   public String getErrorStyle() {
      return (String)this.getStateHelper().eval(HtmlMessage.PropertyKeys.errorStyle);
   }

   public void setErrorStyle(String errorStyle) {
      this.getStateHelper().put(HtmlMessage.PropertyKeys.errorStyle, errorStyle);
   }

   public String getFatalClass() {
      return (String)this.getStateHelper().eval(HtmlMessage.PropertyKeys.fatalClass);
   }

   public void setFatalClass(String fatalClass) {
      this.getStateHelper().put(HtmlMessage.PropertyKeys.fatalClass, fatalClass);
   }

   public String getFatalStyle() {
      return (String)this.getStateHelper().eval(HtmlMessage.PropertyKeys.fatalStyle);
   }

   public void setFatalStyle(String fatalStyle) {
      this.getStateHelper().put(HtmlMessage.PropertyKeys.fatalStyle, fatalStyle);
   }

   public String getInfoClass() {
      return (String)this.getStateHelper().eval(HtmlMessage.PropertyKeys.infoClass);
   }

   public void setInfoClass(String infoClass) {
      this.getStateHelper().put(HtmlMessage.PropertyKeys.infoClass, infoClass);
   }

   public String getInfoStyle() {
      return (String)this.getStateHelper().eval(HtmlMessage.PropertyKeys.infoStyle);
   }

   public void setInfoStyle(String infoStyle) {
      this.getStateHelper().put(HtmlMessage.PropertyKeys.infoStyle, infoStyle);
   }

   public String getLang() {
      return (String)this.getStateHelper().eval(HtmlMessage.PropertyKeys.lang);
   }

   public void setLang(String lang) {
      this.getStateHelper().put(HtmlMessage.PropertyKeys.lang, lang);
      this.handleAttribute("lang", lang);
   }

   public String getRole() {
      return (String)this.getStateHelper().eval(HtmlMessage.PropertyKeys.role);
   }

   public void setRole(String role) {
      this.getStateHelper().put(HtmlMessage.PropertyKeys.role, role);
      this.handleAttribute("role", role);
   }

   public String getStyle() {
      return (String)this.getStateHelper().eval(HtmlMessage.PropertyKeys.style);
   }

   public void setStyle(String style) {
      this.getStateHelper().put(HtmlMessage.PropertyKeys.style, style);
      this.handleAttribute("style", style);
   }

   public String getStyleClass() {
      return (String)this.getStateHelper().eval(HtmlMessage.PropertyKeys.styleClass);
   }

   public void setStyleClass(String styleClass) {
      this.getStateHelper().put(HtmlMessage.PropertyKeys.styleClass, styleClass);
   }

   public String getTitle() {
      return (String)this.getStateHelper().eval(HtmlMessage.PropertyKeys.title);
   }

   public void setTitle(String title) {
      this.getStateHelper().put(HtmlMessage.PropertyKeys.title, title);
      this.handleAttribute("title", title);
   }

   public boolean isTooltip() {
      return (Boolean)this.getStateHelper().eval(HtmlMessage.PropertyKeys.tooltip, false);
   }

   public void setTooltip(boolean tooltip) {
      this.getStateHelper().put(HtmlMessage.PropertyKeys.tooltip, tooltip);
   }

   public String getWarnClass() {
      return (String)this.getStateHelper().eval(HtmlMessage.PropertyKeys.warnClass);
   }

   public void setWarnClass(String warnClass) {
      this.getStateHelper().put(HtmlMessage.PropertyKeys.warnClass, warnClass);
   }

   public String getWarnStyle() {
      return (String)this.getStateHelper().eval(HtmlMessage.PropertyKeys.warnStyle);
   }

   public void setWarnStyle(String warnStyle) {
      this.getStateHelper().put(HtmlMessage.PropertyKeys.warnStyle, warnStyle);
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
