package weblogic.servlet.ejb2jsp.dd;

import java.io.Serializable;
import org.w3c.dom.Element;
import weblogic.servlet.internal.dd.ToXML;
import weblogic.utils.io.XMLWriter;
import weblogic.xml.dom.DOMProcessingException;
import weblogic.xml.dom.DOMUtils;

public class MethodParamDescriptor implements ToXML, Serializable {
   private String type;
   private String name;
   private String defalt;
   private String defaultValue;
   private String defaultMethod;

   static void p(String s) {
      System.err.println("[MethodParamDesc]: " + s);
   }

   public MethodParamDescriptor() {
      this.type = this.name = this.defalt = this.defaultValue = this.defaultMethod;
      this.setDefault("NONE");
   }

   public MethodParamDescriptor(Element e) throws DOMProcessingException {
      this.type = DOMUtils.getValueByTagName(e, "param-type");
      this.name = DOMUtils.getValueByTagName(e, "param-name");
      this.defalt = DOMUtils.getValueByTagName(e, "enable-default");
      this.defaultValue = DOMUtils.getValueByTagName(e, "default-value").trim();
      this.defaultMethod = DOMUtils.getValueByTagName(e, "default-method-body").trim();
   }

   public String getType() {
      return this.type;
   }

   public void setType(String s) {
      this.type = s;
   }

   public String getName() {
      return this.name;
   }

   public void setName(String s) {
      this.name = s;
   }

   public String getDefault() {
      return this.defalt;
   }

   public void setDefault(String s) {
      this.defalt = s;
   }

   public String getDefaultValue() {
      return this.defaultValue;
   }

   public void setDefaultValue(String s) {
      this.defaultValue = s;
   }

   public String getDefaultMethod() {
      return this.defaultMethod;
   }

   public void setDefaultMethod(String s) {
      this.defaultMethod = s;
   }

   public String toString() {
      return "Attribute: " + this.name;
   }

   public void toXML(XMLWriter x) {
      x.println("<parameter>");
      x.incrIndent();
      x.println("<param-type>" + this.type + "</param-type>");
      x.println("<param-name>" + this.name + "</param-name>");
      x.println("<enable-default>" + this.defalt + "</enable-default>");
      if (this.defaultValue != null) {
         x.println("<default-value><![CDATA[" + this.defaultValue + "]]></default-value>");
      }

      if (this.defaultMethod != null) {
         x.println("<default-method-body><![CDATA[");
         x.printNoIndent(this.defaultMethod);
         x.println("]]></default-method-body>");
      }

      x.decrIndent();
      x.println("</parameter>");
   }
}
