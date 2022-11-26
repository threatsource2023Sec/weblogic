package weblogic.servlet.internal.dd;

import org.w3c.dom.Element;
import weblogic.management.descriptors.webapp.ServletMBean;
import weblogic.management.descriptors.webappext.DestroyAsMBean;
import weblogic.xml.dom.DOMProcessingException;

public final class DestroyAsDescriptor extends InitAsDescriptor implements DestroyAsMBean {
   public DestroyAsDescriptor(WebAppDescriptor wad, Element parentElement) throws DOMProcessingException {
      super(wad, parentElement);
   }

   public void setIdentity() {
      ServletMBean s = this.getServlet();
      String pName = this.getPrincipalName();
      if (s != null && pName != null) {
         s.setDestroyAs(pName);
      }

   }

   public String toString() {
      return this.getServletName();
   }

   public String toXML(int indent) {
      String result = "";
      result = result + this.indentStr(indent) + "<destroy-as>\n";
      indent += 2;
      result = result + this.indentStr(indent) + "<servlet-name>" + this.getServletName() + "</servlet-name>\n";
      result = result + this.indentStr(indent) + "<principal-name>" + this.getPrincipalName() + "</principal-name>\n";
      indent -= 2;
      result = result + this.indentStr(indent) + "</destroy-as>\n";
      return result;
   }
}
