package weblogic.j2ee.dd;

import weblogic.application.ApplicationFileManager;
import weblogic.management.descriptors.application.CustomModuleMBean;
import weblogic.utils.io.XMLWriter;

public final class CustomModuleDescriptor extends ModuleDescriptor implements CustomModuleMBean {
   private String providerName;

   public CustomModuleDescriptor() {
   }

   public CustomModuleDescriptor(String uri) {
      super(uri);
   }

   public String getProviderName() {
      return this.providerName;
   }

   public void setProviderName(String providerName) {
      this.providerName = providerName;
   }

   public void toXML(XMLWriter x) {
      throw new AssertionError("should be removed soon...");
   }

   public String getAdminMBeanType(ApplicationFileManager afm) {
      return null;
   }
}
