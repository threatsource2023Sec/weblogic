package weblogic.application.utils;

import java.io.File;
import java.io.IOException;
import weblogic.application.archive.ApplicationArchive;
import weblogic.application.archive.navigator.ApplicationArchiveFactory;
import weblogic.application.archive.navigator.ApplicationArchiveImpl;
import weblogic.utils.jars.VirtualJarFile;

public enum CachedApplicationArchiveFactory {
   instance;

   CacheMap cache = new CacheMap(50, 300000L);

   public ApplicationArchive create(File archive) throws IOException {
      ApplicationArchiveImpl aa = (ApplicationArchiveImpl)this.cache.get(archive.getCanonicalPath());
      if (aa == null) {
         aa = (ApplicationArchiveImpl)ApplicationArchiveFactory.instance.create(archive);
         this.cache.put(archive.getCanonicalPath(), aa);
      }

      return aa;
   }

   public ApplicationArchive create(File archive, File appRoot) throws IOException {
      ApplicationArchiveImpl aa = (ApplicationArchiveImpl)this.cache.remove(archive.getCanonicalPath());
      if (aa == null) {
         aa = (ApplicationArchiveImpl)ApplicationArchiveFactory.instance.create(archive, appRoot);
      } else {
         aa.resetWritableRoot(appRoot);
      }

      return aa;
   }

   public VirtualJarFile createVirtualJarFile(ApplicationArchive app) throws IOException {
      return ApplicationArchiveFactory.instance.createVirtualJarFile(app);
   }
}
