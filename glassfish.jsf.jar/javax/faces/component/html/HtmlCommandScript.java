package javax.faces.component.html;

import java.util.ArrayList;
import java.util.List;
import javax.el.ValueExpression;
import javax.faces.component.UICommand;

public class HtmlCommandScript extends UICommand {
   private static final String OPTIMIZED_PACKAGE = "javax.faces.component.";
   public static final String COMPONENT_TYPE = "javax.faces.HtmlCommandScript";

   public HtmlCommandScript() {
      this.setRendererType("javax.faces.Script");
   }

   public boolean isAutorun() {
      return (Boolean)this.getStateHelper().eval(HtmlCommandScript.PropertyKeys.autorun, false);
   }

   public void setAutorun(boolean autorun) {
      this.getStateHelper().put(HtmlCommandScript.PropertyKeys.autorun, autorun);
   }

   public String getExecute() {
      return (String)this.getStateHelper().eval(HtmlCommandScript.PropertyKeys.execute);
   }

   public void setExecute(String execute) {
      this.getStateHelper().put(HtmlCommandScript.PropertyKeys.execute, execute);
   }

   public String getName() {
      return (String)this.getStateHelper().eval(HtmlCommandScript.PropertyKeys.name);
   }

   public void setName(String name) {
      this.getStateHelper().put(HtmlCommandScript.PropertyKeys.name, name);
   }

   public String getOnerror() {
      return (String)this.getStateHelper().eval(HtmlCommandScript.PropertyKeys.onerror);
   }

   public void setOnerror(String onerror) {
      this.getStateHelper().put(HtmlCommandScript.PropertyKeys.onerror, onerror);
   }

   public String getOnevent() {
      return (String)this.getStateHelper().eval(HtmlCommandScript.PropertyKeys.onevent);
   }

   public void setOnevent(String onevent) {
      this.getStateHelper().put(HtmlCommandScript.PropertyKeys.onevent, onevent);
   }

   public String getRender() {
      return (String)this.getStateHelper().eval(HtmlCommandScript.PropertyKeys.render);
   }

   public void setRender(String render) {
      this.getStateHelper().put(HtmlCommandScript.PropertyKeys.render, render);
   }

   public Boolean getResetValues() {
      return (Boolean)this.getStateHelper().eval(HtmlCommandScript.PropertyKeys.resetValues);
   }

   public void setResetValues(Boolean resetValues) {
      this.getStateHelper().put(HtmlCommandScript.PropertyKeys.resetValues, resetValues);
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
      autorun,
      execute,
      name,
      onerror,
      onevent,
      render,
      resetValues;

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
