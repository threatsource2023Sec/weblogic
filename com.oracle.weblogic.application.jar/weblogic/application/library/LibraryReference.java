package weblogic.application.library;

import java.io.File;
import weblogic.application.Type;
import weblogic.application.internal.library.BasicLibraryData;

public abstract class LibraryReference {
   private final BasicLibraryData libData;
   private final boolean exactMatch;

   protected LibraryReference(BasicLibraryData libData, boolean exactMatch, Type type) {
      this(libData, exactMatch);
      this.libData.setType(type);
   }

   protected LibraryReference(BasicLibraryData libData, boolean exactMatch) {
      this.libData = libData;
      this.exactMatch = exactMatch;
   }

   public String getName() {
      return this.libData.getName();
   }

   public String getSpecificationVersion() {
      return this.libData.getSpecificationVersion() == null ? null : this.libData.getSpecificationVersion().toString();
   }

   public String getImplementationVersion() {
      return this.libData.getImplementationVersion();
   }

   public Type getType() {
      return this.libData.getType();
   }

   public boolean getExactMatch() {
      return this.exactMatch;
   }

   public BasicLibraryData getLibData() {
      return this.libData;
   }

   protected abstract void moreToString(StringBuffer var1);

   public int hashCode() {
      return String.valueOf(this.getName()).hashCode() ^ String.valueOf(this.getSpecificationVersion()).hashCode() ^ String.valueOf(this.getImplementationVersion()).hashCode() ^ String.valueOf(this.exactMatch).hashCode();
   }

   public boolean equals(Object o) {
      if (!(o instanceof LibraryReference)) {
         return false;
      } else {
         LibraryReference ref = (LibraryReference)o;
         return this.getName().equals(ref.getName()) && String.valueOf(this.getSpecificationVersion()).equals(String.valueOf(ref.getSpecificationVersion())) && String.valueOf(ref.getImplementationVersion()).equals(String.valueOf(ref.getImplementationVersion())) && this.exactMatch == ref.exactMatch;
      }
   }

   public String toString() {
      StringBuffer rtn = new StringBuffer();
      rtn.append(this.libData.toString());
      this.moreToString(rtn);
      return rtn.toString();
   }

   LibEntry getCompositeEntry(Library lib) {
      return new LibEntry(lib.getLocation());
   }

   static class LibEntry {
      protected File location;

      LibEntry(File f) {
         this.location = f;
      }

      public boolean equals(Object o) {
         if (this.getClass() != o.getClass() && !(o instanceof LibEntry)) {
            return false;
         } else {
            LibEntry e = (LibEntry)o;
            return this.location.equals(e.location);
         }
      }

      public int hashCode() {
         return this.location.hashCode();
      }
   }
}
