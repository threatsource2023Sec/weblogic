package weblogic.utils.enumerations;

import java.io.File;
import java.util.Stack;

class DirectoryResourceEnumerator extends ResourceEnumerator {
   File dir;
   String[] list;
   int index;
   String fullpath;
   int fplen;
   Stack subdirs = null;

   public void close() {
   }

   DirectoryResourceEnumerator(File dir, String[] ignore, String[] match) {
      super(ignore, match);
      this.dir = dir;
      this.list = dir.list();
      this.index = 0;
      this.fullpath = this.fix(dir.getAbsolutePath());
      this.fplen = this.fullpath.length();
      if (this.fplen > 0 && this.fullpath.endsWith("/")) {
         this.fullpath = this.fullpath.substring(0, this.fplen - 1);
         --this.fplen;
      }

      this.subdirs = new Stack();
   }

   protected String toURI(File f) {
      return this.fix(f.getAbsolutePath().substring(this.fplen));
   }

   public String getNextURI() {
      while(true) {
         if (this.list != null && this.index < this.list.length) {
            File f = new File(this.dir, this.list[this.index++]);
            if (f.isDirectory()) {
               this.subdirs.push(f);
            } else {
               String uri = this.toURI(f);
               if (this.shouldMatch(uri) && !this.shouldIgnore(uri) && f.canRead()) {
                  return uri;
               }
            }
         } else {
            this.list = null;
            this.index = 0;
            if (this.subdirs.empty()) {
               return null;
            }

            this.dir = (File)this.subdirs.pop();
            this.list = this.dir.list();
         }
      }
   }
}
