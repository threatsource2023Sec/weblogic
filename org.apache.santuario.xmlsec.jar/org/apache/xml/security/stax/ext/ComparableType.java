package org.apache.xml.security.stax.ext;

public abstract class ComparableType implements Comparable {
   private String name;

   public ComparableType(String name) {
      this.name = name;
   }

   public String getName() {
      return this.name;
   }

   public String toString() {
      return this.name;
   }

   public boolean equals(Object obj) {
      if (obj == null) {
         return false;
      } else if (this == obj) {
         return true;
      } else {
         if (this.getClass().isAssignableFrom(obj.getClass())) {
            ComparableType other = (ComparableType)obj;
            if (this.getName().equals(other.getName())) {
               return true;
            }
         }

         return false;
      }
   }

   public int hashCode() {
      return this.name.hashCode();
   }

   public int compareTo(ComparableType o) {
      return this.getName().compareTo(o.getName());
   }
}
