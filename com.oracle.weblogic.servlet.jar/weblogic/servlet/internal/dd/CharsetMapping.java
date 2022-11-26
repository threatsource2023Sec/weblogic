package weblogic.servlet.internal.dd;

import org.w3c.dom.Element;
import weblogic.management.ManagementException;
import weblogic.management.descriptors.DescriptorValidationException;
import weblogic.management.descriptors.webappext.CharsetMappingMBean;
import weblogic.xml.dom.DOMProcessingException;
import weblogic.xml.dom.DOMUtils;

public final class CharsetMapping extends BaseServletDescriptor implements CharsetMappingMBean {
   private static final long serialVersionUID = -5431702633984221708L;
   private static String IANA_NAME = "iana-charset-name";
   private static String JAVA_NAME = "java-charset-name";
   String ianaName;
   String javaName;

   public String getIANACharsetName() {
      return this.ianaName;
   }

   public void setIANACharsetName(String n) {
      String old = this.ianaName;
      this.ianaName = n;
      if (!comp(old, n)) {
         this.firePropertyChange("IANACharsetName", old, n);
      }

   }

   public String getJavaCharsetName() {
      return this.javaName;
   }

   public void setJavaCharsetName(String n) {
      String old = this.javaName;
      this.javaName = n;
      if (!comp(old, n)) {
         this.firePropertyChange("JavaCharsetName", old, n);
      }

   }

   public CharsetMapping() {
   }

   public CharsetMapping(Element parentElement) throws DOMProcessingException {
      this.ianaName = DOMUtils.getValueByTagName(parentElement, IANA_NAME);
      this.javaName = DOMUtils.getValueByTagName(parentElement, JAVA_NAME);
   }

   public void validate() throws DescriptorValidationException {
      this.removeDescriptorErrors();
   }

   public void register() throws ManagementException {
      super.register();
   }

   public String toXML(int indent) {
      StringBuffer sb = new StringBuffer();
      sb.append(this.indentStr(indent));
      sb.append("<charset-mapping>\n");
      indent += 2;
      sb.append(this.indentStr(indent));
      sb.append("<iana-charset-name>");
      sb.append(this.ianaName);
      sb.append("</iana-charset-name>\n");
      sb.append(this.indentStr(indent));
      sb.append("<java-charset-name>" + this.javaName + "</java-charset-name>\n");
      indent -= 2;
      sb.append(this.indentStr(indent));
      sb.append("</charset-mapping>\n");
      return sb.toString();
   }
}
