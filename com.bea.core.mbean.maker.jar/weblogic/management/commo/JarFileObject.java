package weblogic.management.commo;

import java.io.File;
import java.io.IOException;
import weblogic.utils.jars.Manifest;
import weblogic.utils.jars.ManifestEntry;

final class JarFileObject {
   private static weblogic.utils.jars.JarFileObject delegate = null;

   static JarFileObject makeJar(String jarPath, File directory, String[] ignoreTypes) throws IOException {
      Manifest manifest = new Manifest();
      addFilesToManifest(manifest, directory, directory, ignoreTypes);
      JarFileObject self = new JarFileObject();
      File jarFile = new File(jarPath);
      delegate = new weblogic.utils.jars.JarFileObject(jarFile, manifest);
      return self;
   }

   private static void addFilesToManifest(Manifest manifest, File root, File dir, String[] ignoreTypes) {
      String[] fileNames = dir.list();
      String path = dir.getAbsolutePath();

      for(int i = 0; i < fileNames.length; ++i) {
         String thisFile = fileNames[i];

         int j;
         for(j = 0; j < ignoreTypes.length && !thisFile.endsWith("." + ignoreTypes[j]); ++j) {
         }

         if (j >= ignoreTypes.length) {
            File f = new File(path + File.separator + thisFile);
            if (!f.getName().equals("MANIFEST.MF")) {
               if (f.isDirectory()) {
                  addFilesToManifest(manifest, root, f, ignoreTypes);
               } else {
                  ManifestEntry entry = new ManifestEntry(root, f);
                  manifest.addEntry(entry);
               }
            }
         }
      }

   }

   public void save() throws IOException {
      delegate.save();
   }
}
