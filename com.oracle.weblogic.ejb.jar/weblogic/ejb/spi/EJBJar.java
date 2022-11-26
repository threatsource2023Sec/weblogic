package weblogic.ejb.spi;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import weblogic.application.ApplicationContextInternal;
import weblogic.application.io.Archive;
import weblogic.application.io.Ear;
import weblogic.application.io.Jar;
import weblogic.utils.classloaders.ClassFinder;

public class EJBJar {
   private final Archive archive;
   private final ClassFinder classfinder;

   public EJBJar(String uri, File f) throws IOException {
      if (f.isDirectory()) {
         this.archive = new ExplodedEJB(uri, new File[]{f});
      } else {
         this.archive = new Jar(uri, f);
      }

      this.classfinder = this.archive.getClassFinder();
   }

   public EJBJar(String uri, ApplicationContextInternal appCtx) throws IOException {
      Ear ear = appCtx.getEar();
      if (ear == null) {
         File f = new File(appCtx.getStagingPath());
         if (!f.exists()) {
            throw new FileNotFoundException("Unable to find ejb-jar for uri " + uri + " at path " + f.getAbsolutePath());
         }

         if (f.isDirectory()) {
            this.archive = new ExplodedEJB(uri, new File[]{f});
         } else {
            this.archive = new Jar(uri, f);
         }
      } else {
         File[] f = ear.getModuleRoots(uri);
         if (f.length == 0) {
            throw new FileNotFoundException("Unable to find ejb-jar with uri " + uri + " in ear at " + appCtx.getStagingPath());
         }

         if (f.length == 1 && !f[0].isDirectory()) {
            this.archive = new Jar(uri, f[0]);
         } else {
            this.archive = new ExplodedEJB(uri, f);
         }
      }

      this.classfinder = this.archive.getClassFinder();
   }

   public ClassFinder getClassFinder() {
      return this.classfinder;
   }

   public void remove() {
      this.archive.remove();
   }
}
