package weblogic.servlet.internal;

import java.io.File;
import java.util.List;
import weblogic.kernel.KernelStatus;

public class ManagementServicesExtensionProcessor extends AbstractWarExtensionProcessor {
   private static final String EXTENSION_EXTRACT_ROOT = "management-services-ext";
   private static final String WAR_URI = "wls-management-services.war";
   private War war;

   public ManagementServicesExtensionProcessor(War war) {
      this.war = war;
   }

   protected List getExtensionJarFiles() {
      return ManagementServicesExtensionManager.getInstance().getExtensions();
   }

   protected File getWarExtractRoot() {
      return this.war.getExtractDir();
   }

   protected String getExtensionExtractRoot() {
      return ManagementServicesExtensionManager.getInstance().getExtensionDirectory();
   }

   protected String getWarUri() {
      return this.war.getURI();
   }

   public boolean isSupport() {
      return support(this.getWarUri());
   }

   public static boolean support(String uri) {
      return KernelStatus.isServer() && "wls-management-services.war".equals(uri);
   }
}
