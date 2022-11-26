package weblogic.servlet.internal.dd;

import org.w3c.dom.Element;
import weblogic.management.descriptors.webapp.EjbRefMBean;
import weblogic.xml.dom.DOMProcessingException;
import weblogic.xml.dom.DOMUtils;

public class EJBLocalReference extends EJBReference implements EjbRefMBean {
   public EJBLocalReference() {
   }

   public EJBLocalReference(String ejbRefDescription, String ejbRefName, String ejbRefType, String homeInterfaceName, String remoteInterfaceName, String linkedEjbName) {
      super(ejbRefDescription, ejbRefName, ejbRefType, homeInterfaceName, remoteInterfaceName, linkedEjbName);
   }

   public EJBLocalReference(EjbRefMBean mbean) {
      super(mbean);
   }

   public EJBLocalReference(Element parentElement) throws DOMProcessingException {
      this.setDescription(DOMUtils.getOptionalValueByTagName(parentElement, "description"));
      this.setEJBRefName(DOMUtils.getValueByTagName(parentElement, "ejb-ref-name"));
      this.setEJBRefType(DOMUtils.getValueByTagName(parentElement, "ejb-ref-type"));
      this.setHomeInterfaceName(DOMUtils.getValueByTagName(parentElement, "local-home"));
      this.setRemoteInterfaceName(DOMUtils.getValueByTagName(parentElement, "local"));
      this.setEJBLinkName(DOMUtils.getOptionalValueByTagName(parentElement, "ejb-link"));
      this.setRunAs(DOMUtils.getOptionalValueByTagName(parentElement, "run-as"));
   }

   public String toString() {
      return "EJBLocalReference(" + this.hashCode() + "," + this.getEJBRefName() + ")";
   }

   public String toXML(int indent) {
      String result = "";
      result = result + this.indentStr(indent) + "<ejb-local-ref>\n";
      indent += 2;
      String d = this.getDescription();
      if (d != null) {
         result = result + this.indentStr(indent) + "<description>" + d + "</description>\n";
      }

      result = result + this.indentStr(indent) + "<ejb-ref-name>" + this.getEJBRefName() + "</ejb-ref-name>\n";
      result = result + this.indentStr(indent) + "<ejb-ref-type>" + this.getEJBRefType() + "</ejb-ref-type>\n";
      result = result + this.indentStr(indent) + "<local-home>" + this.getHomeInterfaceName() + "</local-home>\n";
      result = result + this.indentStr(indent) + "<local>" + this.getRemoteInterfaceName() + "</local>\n";
      String link = this.getEJBLinkName();
      if (link != null) {
         result = result + this.indentStr(indent) + "<ejb-link>" + link + "</ejb-link>\n";
      }

      indent -= 2;
      result = result + this.indentStr(indent) + "</ejb-local-ref>\n";
      return result;
   }

   public boolean isLocalLink() {
      return true;
   }
}
