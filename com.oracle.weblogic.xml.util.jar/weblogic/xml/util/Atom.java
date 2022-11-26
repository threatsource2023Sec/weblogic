package weblogic.xml.util;

import java.util.Hashtable;

public class Atom {
   String s;
   int hash;
   static Hashtable atoms = new Hashtable(500);

   Atom(String s, int h) {
      this.s = s;
      this.hash = h;
   }

   Atom() {
   }

   public static Atom create(String s) {
      if (s == null) {
         return null;
      } else {
         Object o = atoms.get(s);
         if (o == null) {
            Atom n = new Atom(s, s.hashCode());
            atoms.put(s, n);
            return n;
         } else {
            return (Atom)o;
         }
      }
   }

   public int hashCode() {
      return this.hash;
   }

   public String toString() {
      return this.s;
   }

   public boolean equals(Object that) {
      if (this == that) {
         return true;
      } else {
         return that != null && this.getClass() == that.getClass() ? this.s.equals(((Atom)that).s) : false;
      }
   }
}
