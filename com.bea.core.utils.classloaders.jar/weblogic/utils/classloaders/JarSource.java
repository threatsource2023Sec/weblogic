package weblogic.utils.classloaders;

import java.io.IOException;
import java.security.cert.Certificate;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

public class JarSource extends ZipSource {
   private final JarFile jarFile;
   private final JarEntry jarEntry;

   public JarSource(JarFile jf, JarEntry je) {
      super(jf, je);
      this.jarFile = jf;
      this.jarEntry = je;
   }

   public Manifest getManifest() throws IOException {
      return this.jarFile.getManifest();
   }

   public Certificate[] getCertificates() {
      return this.jarEntry.getCertificates();
   }
}
