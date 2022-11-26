package weblogic.j2ee.dd;

import weblogic.application.ApplicationFileManager;
import weblogic.management.descriptors.application.AltDDModuleDescriptorMBean;
import weblogic.utils.io.XMLWriter;

public final class AltDDModuleDescriptor extends ModuleDescriptor implements AltDDModuleDescriptorMBean {
   private static final long serialVersionUID = 9003680643645690903L;

   public AltDDModuleDescriptor() {
   }

   public AltDDModuleDescriptor(String uri) {
      super(uri);
   }

   public void toXML(XMLWriter x) {
      if (this.getURI() != null) {
         x.println("<alt-dd>");
         x.incrIndent();
         x.println(this.getURI());
         x.decrIndent();
         x.println("</alt-dd>");
      }

   }

   public String getAdminMBeanType(ApplicationFileManager afm) {
      return null;
   }
}
