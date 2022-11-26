package weblogic.application.library;

import java.io.File;
import weblogic.application.internal.library.BasicLibraryData;

public class J2EELibraryReference extends LibraryReference {
   private final String contextRoot;

   J2EELibraryReference(BasicLibraryData data, boolean exactMatch, String contextRoot) {
      super(data, exactMatch);
      this.contextRoot = contextRoot;
   }

   public String getContextRoot() {
      return this.contextRoot;
   }

   public int hashCode() {
      return super.hashCode() ^ String.valueOf(this.contextRoot).hashCode();
   }

   public boolean equals(Object o) {
      if (!(o instanceof J2EELibraryReference)) {
         return false;
      } else {
         return super.equals(o) && String.valueOf(this.contextRoot).equals(String.valueOf(((J2EELibraryReference)o).contextRoot));
      }
   }

   protected void moreToString(StringBuffer sb) {
      sb.append(", ").append("exact-match").append(": ").append(this.getExactMatch());
      if (this.contextRoot != null) {
         sb.append(", ").append("context-root").append(": ").append(this.contextRoot);
      }

   }

   LibraryReference.LibEntry getCompositeEntry(Library lib) {
      return new J2EELibEntry(lib.getLocation(), this.getContextRoot());
   }

   static class J2EELibEntry extends LibraryReference.LibEntry {
      private String contextRoot;

      J2EELibEntry(File location, String cpath) {
         super(location);
         this.contextRoot = cpath;
      }

      public boolean equals(Object o) {
         if (o.getClass() != this.getClass() && !(o instanceof J2EELibEntry)) {
            return false;
         } else {
            J2EELibEntry e = (J2EELibEntry)o;
            return this.location.equals(e.location) && String.valueOf(this.contextRoot).equals(String.valueOf(e.contextRoot));
         }
      }

      public int hashCode() {
         return this.location.hashCode() * 17 ^ String.valueOf(this.contextRoot).hashCode();
      }
   }
}
