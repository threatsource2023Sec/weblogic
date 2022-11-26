package weblogic.servlet.internal.dd;

import org.w3c.dom.Element;
import weblogic.management.ManagementException;
import weblogic.management.descriptors.DescriptorValidationException;
import weblogic.management.descriptors.webappext.InputCharsetDescriptorMBean;
import weblogic.xml.dom.DOMProcessingException;
import weblogic.xml.dom.DOMUtils;

public final class InputCharsetDescriptor extends BaseServletDescriptor implements InputCharsetDescriptorMBean {
   private static final long serialVersionUID = 379779722485658071L;
   private static String RESOURCE_PATH = "resource-path";
   private static String JAVA_NAME = "java-charset-name";
   String resourcePath;
   String javaName;

   public String getResourcePath() {
      return this.resourcePath;
   }

   public void setResourcePath(String n) {
      String old = this.resourcePath;
      this.resourcePath = n;
      if (!comp(old, n)) {
         this.firePropertyChange("resourcePath", old, n);
      }

   }

   public String getJavaCharsetName() {
      return this.javaName;
   }

   public void setJavaCharsetName(String n) {
      String old = this.javaName;
      this.javaName = n;
      if (!comp(old, n)) {
         this.firePropertyChange("javaCharsetName", old, n);
      }

   }

   public InputCharsetDescriptor() {
   }

   public InputCharsetDescriptor(Element parentElement) throws DOMProcessingException {
      this.resourcePath = DOMUtils.getValueByTagName(parentElement, RESOURCE_PATH);
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
      sb.append("<input-charset>\n");
      indent += 2;
      sb.append(this.indentStr(indent));
      sb.append("<resource-path>");
      sb.append(this.resourcePath);
      sb.append("</resource-path>\n");
      sb.append(this.indentStr(indent));
      sb.append("<java-charset-name>");
      sb.append(this.javaName);
      sb.append("</java-charset-name>\n");
      indent -= 2;
      sb.append(this.indentStr(indent));
      sb.append("</input-charset>\n");
      return sb.toString();
   }
}
