package weblogic.servlet.internal;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import weblogic.Home;
import weblogic.management.DomainDir;
import weblogic.utils.FileUtils;

public class ManagementServicesExtensionManager {
   private static final ManagementServicesExtensionManager INSTANCE = new ManagementServicesExtensionManager();
   private List extensions = null;

   public static ManagementServicesExtensionManager getInstance() {
      return INSTANCE;
   }

   private ManagementServicesExtensionManager() {
   }

   public String getExtensionDirectory() {
      return "management-services-ext";
   }

   public List getExtensions() {
      if (this.extensions == null) {
         this.findExtensions();
      }

      return this.extensions;
   }

   private void findExtensions() {
      List ext = new ArrayList();
      this.findExtensions(ext, Home.getPath() + File.separator + "lib");
      this.findExtensions(ext, DomainDir.getRootDir());
      this.extensions = ext;
   }

   private void findExtensions(List ext, String baseDir) {
      String extensionDir = baseDir + File.separator + this.getExtensionDirectory();
      File dir = new File(extensionDir);
      if (dir.exists() && dir.isDirectory()) {
         File[] var5 = dir.listFiles(FileUtils.makeExtensionFilter(".war"));
         int var6 = var5.length;

         for(int var7 = 0; var7 < var6; ++var7) {
            File extension = var5[var7];
            ext.add(extension);
         }

      }
   }
}
