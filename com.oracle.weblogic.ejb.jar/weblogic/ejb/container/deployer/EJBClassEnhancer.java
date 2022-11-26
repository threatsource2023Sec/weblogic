package weblogic.ejb.container.deployer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class EJBClassEnhancer {
   protected String debug = null;

   public EJBClassEnhancer() {
      try {
         this.debug = System.getProperty("weblogic.ejb.enhancement.debug");
      } catch (Exception var2) {
      }

   }

   protected void writeEnhancedClassBack(String cname, byte[] b) {
      if (this.debug != null) {
         File file = new File(System.getProperty("java.io.tmpdir") + File.separatorChar + "enhanced_classes", cname.replace('.', File.separatorChar) + ".class");
         file.getParentFile().mkdirs();
         FileOutputStream writer = null;

         try {
            if (!file.exists()) {
               file.createNewFile();
            }

            writer = new FileOutputStream(file);
            writer.write(b);
         } catch (FileNotFoundException var16) {
            var16.printStackTrace();
         } catch (IOException var17) {
            var17.printStackTrace();
         } finally {
            if (writer != null) {
               try {
                  writer.close();
               } catch (IOException var15) {
                  var15.printStackTrace();
               }
            }

         }

      }
   }
}
