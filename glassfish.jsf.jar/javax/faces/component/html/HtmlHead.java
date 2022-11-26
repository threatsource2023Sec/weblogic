package javax.faces.component.html;

import java.util.ArrayList;
import java.util.List;
import javax.el.ValueExpression;
import javax.faces.component.UIOutput;

public class HtmlHead extends UIOutput {
   private static final String OPTIMIZED_PACKAGE = "javax.faces.component.";
   public static final String COMPONENT_TYPE = "javax.faces.OutputHead";

   public HtmlHead() {
      this.setRendererType("javax.faces.Head");
   }

   public String getDir() {
      return (String)this.getStateHelper().eval(HtmlHead.PropertyKeys.dir);
   }

   public void setDir(String dir) {
      this.getStateHelper().put(HtmlHead.PropertyKeys.dir, dir);
      this.handleAttribute("dir", dir);
   }

   public String getLang() {
      return (String)this.getStateHelper().eval(HtmlHead.PropertyKeys.lang);
   }

   public void setLang(String lang) {
      this.getStateHelper().put(HtmlHead.PropertyKeys.lang, lang);
      this.handleAttribute("lang", lang);
   }

   public String getXmlns() {
      return (String)this.getStateHelper().eval(HtmlHead.PropertyKeys.xmlns);
   }

   public void setXmlns(String xmlns) {
      this.getStateHelper().put(HtmlHead.PropertyKeys.xmlns, xmlns);
      this.handleAttribute("xmlns", xmlns);
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
      lang,
      xmlns;

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
