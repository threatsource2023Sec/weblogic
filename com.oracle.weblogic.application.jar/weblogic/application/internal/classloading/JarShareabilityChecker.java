package weblogic.application.internal.classloading;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import weblogic.j2ee.descriptor.wl.ShareableBean;

class JarShareabilityChecker implements ShareabilityChecker {
   private final boolean shareAll;
   private final boolean shareSelective;
   private final boolean excludeSelective;
   private final List includes;
   private final List excludes;
   private final String parent;

   JarShareabilityChecker(ShareableBean bean) {
      this(bean, (String)null);
   }

   JarShareabilityChecker(ShareableBean bean, String parent) {
      this(bean.getIncludes(), bean.getExcludes(), parent);
   }

   JarShareabilityChecker(String[] includes, String[] excludes, String parent) {
      if (includes != null && includes.length > 0) {
         this.includes = Arrays.asList(includes);
      } else {
         this.includes = null;
      }

      if (excludes != null && excludes.length > 0) {
         this.excludes = Arrays.asList(excludes);
      } else {
         this.excludes = null;
      }

      this.shareAll = this.includes == null && this.excludes == null;
      this.shareSelective = this.excludes == null;
      this.excludeSelective = this.includes == null;
      this.parent = parent;
   }

   public boolean doShare(File file) {
      if (this.parent != null && !file.getParent().endsWith(this.parent)) {
         return false;
      } else if (this.shareAll) {
         return true;
      } else if (this.shareSelective) {
         return this.includes.contains(file.getName());
      } else if (this.excludeSelective) {
         return !this.excludes.contains(file.getName());
      } else {
         return this.includes.contains(file.getName()) && !this.excludes.contains(file.getName());
      }
   }
}
