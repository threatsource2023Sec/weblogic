package weblogic.j2ee.dd;

import weblogic.application.ApplicationFileManager;
import weblogic.management.descriptors.application.EJBModuleMBean;
import weblogic.utils.io.XMLWriter;

public final class EJBModuleDescriptor extends ModuleDescriptor implements EJBModuleMBean {
   private static final long serialVersionUID = -3810058314921396582L;

   public EJBModuleDescriptor() {
   }

   public EJBModuleDescriptor(String uri) {
      super(uri);
   }

   public void toXML(XMLWriter x) {
      x.println("<module>");
      x.incrIndent();
      x.println("<ejb>" + this.getURI() + "</ejb>");
      if (this.getAltDDURI() != null) {
         x.println("<alt-dd>" + this.getAltDDURI() + "</alt-dd>");
      }

      x.decrIndent();
      x.println("</module>");
   }

   public String getAdminMBeanType(ApplicationFileManager afm) {
      return "EJBComponent";
   }
}
