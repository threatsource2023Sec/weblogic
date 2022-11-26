package weblogic.application.internal.library;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import weblogic.application.internal.library.util.DeweyDecimal;
import weblogic.application.library.LibraryDefinition;
import weblogic.application.library.LibraryReference;

class RegistryKey implements Serializable, Comparable {
   private final DeweyDecimal comparableKey;
   private final String keyStringRepr;
   private final boolean isComparable;

   static RegistryKey[] newInstance(LibraryReference ref) {
      return newInstance(ref.getLibData(), true);
   }

   static RegistryKey[] newInstance(LibraryDefinition def) {
      return newInstance(def.getLibData(), false);
   }

   static RegistryKey[] newInstance(BasicLibraryData d, boolean includeNulls) {
      List l = new ArrayList(3);
      l.add(new RegistryKey(d.getName()));
      if (d.getSpecificationVersion() != null) {
         l.add(new RegistryKey(d.getSpecificationVersion()));
      } else if (includeNulls) {
         l.add((Object)null);
      }

      if (d.getImplementationVersion() != null) {
         l.add(new RegistryKey(d.getImplementationVersion()));
      } else if (includeNulls) {
         l.add((Object)null);
      }

      return (RegistryKey[])((RegistryKey[])l.toArray(new RegistryKey[l.size()]));
   }

   private RegistryKey(DeweyDecimal d) {
      if (d == null) {
         throw new IllegalArgumentException("Cannot make RegistryKey with null DeweyDecimal");
      } else {
         this.comparableKey = d;
         this.isComparable = true;
         this.keyStringRepr = d.toString();
      }
   }

   private RegistryKey(String s) {
      if (s == null) {
         throw new IllegalArgumentException("Cannot make RegistryKey with null String");
      } else {
         DeweyDecimal d = initDeweyDecimal(s);
         if (d == null) {
            this.comparableKey = null;
            this.isComparable = false;
            this.keyStringRepr = s;
         } else {
            this.comparableKey = d;
            this.isComparable = true;
            this.keyStringRepr = d.toString();
         }

      }
   }

   public int compareTo(Object o) {
      return this.compareTo((RegistryKey)o);
   }

   public int compareTo(RegistryKey k) {
      return this.isComparable() && k.isComparable() ? this.getComparableKey().compareTo(k.getComparableKey()) : this.toString().compareTo(k.toString());
   }

   public boolean isComparable() {
      return this.isComparable;
   }

   public int hashCode() {
      return this.toString().hashCode();
   }

   public boolean equals(Object o) {
      if (!(o instanceof RegistryKey)) {
         return false;
      } else {
         RegistryKey otherKey = (RegistryKey)o;
         return this.keyStringRepr.equals(otherKey.toString());
      }
   }

   public String toString() {
      return this.keyStringRepr;
   }

   private DeweyDecimal getComparableKey() {
      return this.comparableKey;
   }

   private static DeweyDecimal initDeweyDecimal(String s) {
      try {
         return new DeweyDecimal(s);
      } catch (NumberFormatException var2) {
         return null;
      }
   }
}
