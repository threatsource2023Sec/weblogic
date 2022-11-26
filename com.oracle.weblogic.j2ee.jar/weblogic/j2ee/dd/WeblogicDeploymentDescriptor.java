package weblogic.j2ee.dd;

import weblogic.management.descriptors.application.weblogic.EjbMBean;
import weblogic.management.descriptors.application.weblogic.SecurityMBean;
import weblogic.management.descriptors.application.weblogic.WeblogicApplicationMBean;
import weblogic.management.descriptors.application.weblogic.WeblogicApplicationMBeanImpl;
import weblogic.management.descriptors.application.weblogic.XMLMBean;
import weblogic.management.tools.ToXML;
import weblogic.utils.io.XMLWriter;

public class WeblogicDeploymentDescriptor extends WeblogicApplicationMBeanImpl implements WeblogicApplicationMBean {
   static final long serialVersionUID = 1L;
   static final String WL_DOCTYPE = "<!DOCTYPE weblogic-application PUBLIC '-//BEA Systems, Inc.//DTD WebLogic Application 9.0.0//EN' 'http://www.bea.com/servers/wls900/dtd/weblogic-application_3_0.dtd'>\n";

   public EjbMBean getEjb() {
      return super.getEjb();
   }

   public void setEjb(EjbMBean ejb) {
      super.setEjb(ejb);
   }

   public XMLMBean getXML() {
      return super.getXML();
   }

   public void setXML(XMLMBean xml) {
      super.setXML(xml);
   }

   public SecurityMBean getSecurity() {
      return super.getSecurity();
   }

   public void setSecurity(SecurityMBean sec) {
      super.setSecurity(sec);
   }

   public String toXML(int indentLevel) {
      StringBuffer result = new StringBuffer();
      String descrEncoding = super.getEncoding();
      if (descrEncoding != null) {
         result.append(ToXML.indent(indentLevel)).append("<?xml version=\"1.0\" encoding=\"" + descrEncoding + "\"?>\n");
      }

      result.append(ToXML.indent(indentLevel)).append("<!DOCTYPE weblogic-application PUBLIC '-//BEA Systems, Inc.//DTD WebLogic Application 9.0.0//EN' 'http://www.bea.com/servers/wls900/dtd/weblogic-application_3_0.dtd'>\n");
      result.append(super.toXML(indentLevel));
      return result.toString();
   }

   public void toXML(XMLWriter w) {
      w.println(this.toXML(2));
   }
}
