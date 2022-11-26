package weblogic.xml.util;

import java.util.Hashtable;

public class Name {
   static Hashtable names = new Hashtable(500);
   static Hashtable snames = new Hashtable(500);
   Atom nameSpace;
   String name;
   int hash;

   Name(String name, Atom nameSpace, int hash) {
      this.name = name;
      this.nameSpace = nameSpace;
      this.hash = hash;
   }

   public String getName() {
      return this.name;
   }

   public Atom getNameSpace() {
      return this.nameSpace;
   }

   public boolean equals(Object that) {
      if (this == that) {
         return true;
      } else if (that != null && this.getClass() == that.getClass()) {
         Name t = (Name)that;
         if (this.nameSpace != null) {
            if (!this.nameSpace.equals(t.nameSpace)) {
               return false;
            }
         } else if (t.nameSpace != null) {
            return false;
         }

         return this.name.equals(t.name);
      } else {
         return false;
      }
   }

   public static Name create(String name) {
      if (name == null) {
         return null;
      } else {
         Object o = snames.get(name);
         if (o == null) {
            int h = name.hashCode();
            Name result = new Name(name, (Atom)null, h);
            snames.put(name, result);
            return result;
         } else {
            return (Name)o;
         }
      }
   }

   public static Name create(char[] val, int offset, int len) {
      Object o = snames.get(new String(val, offset, len));
      if (o == null) {
         String name = new String(val, offset, len);
         int h = name.hashCode();
         Name result = new Name(name, (Atom)null, h);
         snames.put(name, result);
         return result;
      } else {
         return (Name)o;
      }
   }

   public static Name create(String name, String ns) {
      if (name == null) {
         return null;
      } else {
         return ns == null ? create(name) : create(name, Atom.create(ns));
      }
   }

   public static Name create(String name, Atom nameSpace) {
      if (name == null) {
         return null;
      } else if (nameSpace == null) {
         return create(name);
      } else {
         int h = name.hashCode() + nameSpace.hashCode();
         Name result = new Name(name, nameSpace, h);
         Object o = names.get(result);
         if (o == null) {
            names.put(result, result);
            return result;
         } else {
            return (Name)o;
         }
      }
   }

   public String toString() {
      return this.nameSpace != null ? this.nameSpace.toString() + ":" + this.name : this.name;
   }

   public int hashCode() {
      return this.hash;
   }
}
