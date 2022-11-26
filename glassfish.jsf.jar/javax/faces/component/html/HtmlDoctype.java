package javax.faces.component.html;

import java.util.ArrayList;
import java.util.List;
import javax.el.ValueExpression;
import javax.faces.component.UIOutput;

public class HtmlDoctype extends UIOutput {
   private static final String OPTIMIZED_PACKAGE = "javax.faces.component.";
   public static final String COMPONENT_TYPE = "javax.faces.OutputDoctype";

   public HtmlDoctype() {
      this.setRendererType("javax.faces.Doctype");
   }

   public String getPublic() {
      return (String)this.getStateHelper().eval(HtmlDoctype.PropertyKeys.publicVal);
   }

   public void setPublic(String _public) {
      this.getStateHelper().put(HtmlDoctype.PropertyKeys.publicVal, _public);
      this.handleAttribute("public", _public);
   }

   public String getRootElement() {
      return (String)this.getStateHelper().eval(HtmlDoctype.PropertyKeys.rootElement);
   }

   public void setRootElement(String rootElement) {
      this.getStateHelper().put(HtmlDoctype.PropertyKeys.rootElement, rootElement);
      this.handleAttribute("rootElement", rootElement);
   }

   public String getSystem() {
      return (String)this.getStateHelper().eval(HtmlDoctype.PropertyKeys.system);
   }

   public void setSystem(String system) {
      this.getStateHelper().put(HtmlDoctype.PropertyKeys.system, system);
      this.handleAttribute("system", system);
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
      publicVal("public"),
      rootElement,
      system;

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
