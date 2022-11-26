package weblogic.j2ee.dd;

import weblogic.application.ApplicationFileManager;
import weblogic.management.descriptors.application.InterceptionModuleMBean;
import weblogic.utils.io.XMLWriter;

public final class InterceptionModuleDescriptor extends ModuleDescriptor implements InterceptionModuleMBean {
   public InterceptionModuleDescriptor() {
   }

   public InterceptionModuleDescriptor(String uri) {
      super(uri);
   }

   public void toXML(XMLWriter x) {
      x.println("<module>");
      x.incrIndent();
      x.println("<jms>" + this.getURI() + "</jms>");
      if (this.getAltDDURI() != null) {
         x.println("<alt-dd>" + this.getAltDDURI() + "</alt-uri>");
      }

      x.decrIndent();
      x.println("</module>");
   }

   public String getAdminMBeanType(ApplicationFileManager afm) {
      return null;
   }
}
