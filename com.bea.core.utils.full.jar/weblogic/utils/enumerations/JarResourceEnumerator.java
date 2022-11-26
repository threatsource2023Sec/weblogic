package weblogic.utils.enumerations;

import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

class JarResourceEnumerator extends ResourceEnumerator {
   ZipFile zf;
   Enumeration entries;

   public void close() {
      try {
         this.zf.close();
      } catch (Exception var2) {
      }

   }

   JarResourceEnumerator(ZipFile z, String[] ignore, String[] match) {
      super(ignore, match);
      this.zf = z;
      this.entries = this.zf.entries();
   }

   public String getNextURI() {
      while(true) {
         if (this.entries.hasMoreElements()) {
            ZipEntry ze = (ZipEntry)this.entries.nextElement();
            String name = ze.getName();
            if (!this.shouldMatch(name) || this.shouldIgnore(name)) {
               continue;
            }

            return name;
         }

         return null;
      }
   }
}
