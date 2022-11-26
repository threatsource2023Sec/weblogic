package weblogic.j2ee.dd;

import weblogic.application.ApplicationFileManager;
import weblogic.management.descriptors.application.ConnectorModuleMBean;
import weblogic.utils.io.XMLWriter;

public final class ResourceAdapterModuleDescriptor extends ModuleDescriptor implements ConnectorModuleMBean {
   private static final long serialVersionUID = -6519032324695892753L;

   public ResourceAdapterModuleDescriptor() {
   }

   public ResourceAdapterModuleDescriptor(String uri) {
      super(uri);
   }

   public void toXML(XMLWriter x) {
      x.println("<module>");
      x.incrIndent();
      x.println("<connector>" + this.getURI() + "</connector>");
      if (this.getAltDDURI() != null) {
         x.println("<alt-dd>" + this.getAltDDURI() + "</alt-dd>");
      }

      x.decrIndent();
      x.println("</module>");
   }

   public String getAdminMBeanType(ApplicationFileManager afm) {
      return "ConnectorComponent";
   }
}
