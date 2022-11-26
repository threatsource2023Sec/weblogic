package weblogic.messaging.util;

import java.net.URL;

public class DelegatingLoader extends ClassLoader {
   private final ClassLoader loader;

   public DelegatingLoader(ClassLoader loader1, ClassLoader loader2) {
      super(loader2);
      this.loader = loader1;
   }

   protected Class findClass(String name) throws ClassNotFoundException {
      return this.loader.loadClass(name);
   }

   protected URL findResource(String name) {
      return this.loader.getResource(name);
   }

   public int hashCode() {
      int prime = true;
      int result = 1;
      result = 31 * result + (this.loader == null ? 0 : this.loader.hashCode());
      result = 31 * result + (this.getParent() == null ? 0 : this.getParent().hashCode());
      return result;
   }

   public boolean equals(Object obj) {
      if (this == obj) {
         return true;
      } else if (obj == null) {
         return false;
      } else if (this.getClass() != obj.getClass()) {
         return false;
      } else {
         DelegatingLoader other = (DelegatingLoader)obj;
         if (this.loader == null) {
            if (other.loader != null) {
               return false;
            }
         } else if (!this.loader.equals(other.loader)) {
            return false;
         }

         if (this.getParent() == null) {
            if (other.getParent() != null) {
               return false;
            }
         } else if (!this.getParent().equals(other.getParent())) {
            return false;
         }

         return true;
      }
   }
}
