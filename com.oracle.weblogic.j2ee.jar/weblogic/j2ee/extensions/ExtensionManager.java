package weblogic.j2ee.extensions;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public enum ExtensionManager {
   instance;

   private List diExtensions = new ArrayList();

   public void addInjectionExtension(String typeRegx, String jndiNameRegx, InjectionExtension e) {
      if (typeRegx == null) {
         throw new IllegalArgumentException("typeRegx may not be null");
      } else if (jndiNameRegx == null) {
         throw new IllegalArgumentException("jndiNameRegex may not be null");
      } else if (e == null) {
         throw new IllegalArgumentException("extension may not be null");
      } else {
         this.diExtensions.add(new ExtensionData(typeRegx, jndiNameRegx, e));
      }
   }

   public InjectionExtension getFirstMatchingExtension(String type, String name) {
      Iterator var3 = this.diExtensions.iterator();

      ExtensionData extension;
      do {
         do {
            if (!var3.hasNext()) {
               return null;
            }

            extension = (ExtensionData)var3.next();
         } while(!type.matches(extension.typeToMatch));
      } while(name != null && name.length() != 0 && !name.matches(extension.nameToMatch));

      return extension.e;
   }

   private class ExtensionData {
      private final String typeToMatch;
      private final String nameToMatch;
      private final InjectionExtension e;

      private ExtensionData(String typeToMatch, String nameToMatch, InjectionExtension e) {
         this.typeToMatch = typeToMatch;
         this.nameToMatch = nameToMatch;
         this.e = e;
      }

      // $FF: synthetic method
      ExtensionData(String x1, String x2, InjectionExtension x3, Object x4) {
         this(x1, x2, x3);
      }
   }
}
