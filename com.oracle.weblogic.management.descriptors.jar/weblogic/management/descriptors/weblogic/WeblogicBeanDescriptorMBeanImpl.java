package weblogic.management.descriptors.weblogic;

import weblogic.management.descriptors.XMLElementMBeanDelegate;
import weblogic.management.tools.ToXML;

public class WeblogicBeanDescriptorMBeanImpl extends XMLElementMBeanDelegate implements WeblogicBeanDescriptorMBean {
   static final long serialVersionUID = 1L;

   public String toXML(int indentLevel) {
      StringBuffer result = new StringBuffer();
      result.append(ToXML.indent(indentLevel)).append("<weblogic-bean-descriptor");
      result.append(">\n");
      result.append(ToXML.indent(indentLevel)).append("</weblogic-bean-descriptor>\n");
      return result.toString();
   }
}
