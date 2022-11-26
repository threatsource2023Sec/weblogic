package weblogic.application.internal.library;

import weblogic.application.Type;
import weblogic.application.library.LibraryReference;

public class OptionalPackageReference extends LibraryReference {
   private final String src;

   public OptionalPackageReference(BasicLibraryData d, String src) {
      super(d, false, (Type)null);
      this.src = src;
   }

   protected void moreToString(StringBuffer sb) {
      sb.append(", ").append("referenced from").append(": ").append(this.src);
   }
}
