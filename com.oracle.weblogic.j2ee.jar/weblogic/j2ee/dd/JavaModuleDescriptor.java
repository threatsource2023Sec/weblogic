package weblogic.j2ee.dd;

import weblogic.application.ApplicationFileManager;
import weblogic.management.descriptors.application.JavaModuleMBean;
import weblogic.utils.io.XMLWriter;

public final class JavaModuleDescriptor extends ModuleDescriptor implements JavaModuleMBean {
   private static final long serialVersionUID = -5838871575860088884L;

   public JavaModuleDescriptor() {
   }

   public JavaModuleDescriptor(String uri) {
      super(uri);
   }

   public void toXML(XMLWriter x) {
      x.println("<module>");
      x.incrIndent();
      x.println("<java>" + this.getURI() + "</java>");
      if (this.getAltDDURI() != null) {
         x.println("<alt-dd>" + this.getAltDDURI() + "</alt-dd>");
      }

      x.decrIndent();
      x.println("</module>");
   }

   public String getAdminMBeanType(ApplicationFileManager afm) {
      return "AppClientComponent";
   }
}
