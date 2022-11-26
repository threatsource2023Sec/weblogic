package weblogic.application.archive.navigator;

import java.io.File;
import java.io.IOException;
import weblogic.application.archive.ApplicationArchive;
import weblogic.utils.jars.VirtualJarFile;

public enum ApplicationArchiveFactory {
   instance;

   public ApplicationArchive create(File archive, File appRoot) throws IOException {
      return new ApplicationArchiveImpl(this.createNavigator(archive, appRoot));
   }

   public ApplicationArchive create(File archive) throws IOException {
      return this.create(archive, (File)null);
   }

   public VirtualJarFile createVirtualJarFile(ApplicationArchive app) throws IOException {
      return new ApplicationArchiveVirtualJarFile(app);
   }

   private ApplicationNavigator createNavigator(File archive, File appRoot) throws IOException {
      return appRoot == null ? new ApplicationNavigatorImpl(archive) : new ApplicationNavigatorImpl(archive, appRoot);
   }
}
