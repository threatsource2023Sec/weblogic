package javolution.util;

import java.io.Serializable;
import java.util.Comparator;
import javolution.Configuration;
import javolution.lang.Text;

public abstract class FastComparator implements Comparator, Serializable {
   public static final FastComparator DEFAULT = new Default(Configuration.isPoorSystemHash());
   public static final FastComparator DIRECT = new Direct();
   public static final FastComparator REHASH = new Rehash();
   public static final FastComparator IDENTITY = new Identity();
   public static final FastComparator LEXICAL = new Lexical();

   public abstract int hashCodeOf(Object var1);

   public abstract boolean areEqual(Object var1, Object var2);

   public abstract int compare(Object var1, Object var2);

   static class Lexical extends FastComparator {
      public int hashCodeOf(Object var1) {
         if (!(var1 instanceof String) && !(var1 instanceof Text)) {
            CharSequence var2 = (CharSequence)var1;
            int var3 = 0;
            int var4 = var2.length();

            for(int var5 = 0; var5 < var4; var3 = 31 * var3 + var2.charAt(var5++)) {
            }

            return var3;
         } else {
            return var1.hashCode();
         }
      }

      public boolean areEqual(Object var1, Object var2) {
         if (var1 instanceof String && var2 instanceof String) {
            return var1.equals(var2);
         } else {
            CharSequence var3;
            int var5;
            int var6;
            String var7;
            if (var1 instanceof CharSequence && var2 instanceof String) {
               var3 = (CharSequence)var1;
               var7 = (String)var2;
               var5 = var7.length();
               if (var3.length() != var5) {
                  return false;
               } else {
                  var6 = 0;

                  do {
                     if (var6 >= var5) {
                        return true;
                     }
                  } while(var7.charAt(var6) == var3.charAt(var6++));

                  return false;
               }
            } else if (var1 instanceof String && var2 instanceof CharSequence) {
               var3 = (CharSequence)var2;
               var7 = (String)var1;
               var5 = var7.length();
               if (var3.length() != var5) {
                  return false;
               } else {
                  var6 = 0;

                  do {
                     if (var6 >= var5) {
                        return true;
                     }
                  } while(var7.charAt(var6) == var3.charAt(var6++));

                  return false;
               }
            } else {
               var3 = (CharSequence)var1;
               CharSequence var4 = (CharSequence)var2;
               var5 = var3.length();
               if (var4.length() != var5) {
                  return false;
               } else {
                  var6 = 0;

                  do {
                     if (var6 >= var5) {
                        return true;
                     }
                  } while(var3.charAt(var6) == var4.charAt(var6++));

                  return false;
               }
            }
         }
      }

      public int compare(Object var1, Object var2) {
         CharSequence var4;
         int var5;
         int var6;
         char var7;
         char var8;
         if (var1 instanceof String) {
            if (var2 instanceof String) {
               return ((String)var1).compareTo((String)var2);
            } else {
               String var9 = (String)var1;
               var4 = (CharSequence)var2;
               var5 = 0;
               var6 = Math.min(var9.length(), var4.length());

               do {
                  if (var6-- == 0) {
                     return var9.length() - var4.length();
                  }

                  var7 = var9.charAt(var5);
                  var8 = var4.charAt(var5++);
               } while(var7 == var8);

               return var7 - var8;
            }
         } else if (var2 instanceof String) {
            return -this.compare(var2, var1);
         } else {
            CharSequence var3 = (CharSequence)var1;
            var4 = (CharSequence)var2;
            var5 = 0;
            var6 = Math.min(var3.length(), var4.length());

            do {
               if (var6-- == 0) {
                  return var3.length() - var4.length();
               }

               var7 = var3.charAt(var5);
               var8 = var4.charAt(var5++);
            } while(var7 == var8);

            return var7 - var8;
         }
      }

      public String toString() {
         return "lexical";
      }
   }

   static class Identity extends FastComparator {
      private boolean _rehash = !Configuration.isPoorSystemHash();

      public int hashCodeOf(Object var1) {
         int var2 = System.identityHashCode(var1);
         if (!this._rehash) {
            return var2;
         } else {
            var2 += ~(var2 << 9);
            var2 ^= var2 >>> 14;
            var2 += var2 << 4;
            return var2 ^ var2 >>> 10;
         }
      }

      public boolean areEqual(Object var1, Object var2) {
         return var1 == var2;
      }

      public int compare(Object var1, Object var2) {
         return ((Comparable)var1).compareTo(var2);
      }

      public String toString() {
         return "identity";
      }
   }

   static class Rehash extends FastComparator {
      public int hashCodeOf(Object var1) {
         int var2 = var1.hashCode();
         var2 += ~(var2 << 9);
         var2 ^= var2 >>> 14;
         var2 += var2 << 4;
         return var2 ^ var2 >>> 10;
      }

      public boolean areEqual(Object var1, Object var2) {
         return var1 == null ? var2 == null : var1 == var2 || var1.equals(var2);
      }

      public int compare(Object var1, Object var2) {
         return ((Comparable)var1).compareTo(var2);
      }

      public String toString() {
         return "rehash";
      }
   }

   static class Direct extends FastComparator {
      public int hashCodeOf(Object var1) {
         return var1.hashCode();
      }

      public boolean areEqual(Object var1, Object var2) {
         return var1 == null ? var2 == null : var1 == var2 || var1.equals(var2);
      }

      public int compare(Object var1, Object var2) {
         return ((Comparable)var1).compareTo(var2);
      }

      public String toString() {
         return "direct";
      }
   }

   static class Default extends FastComparator {
      private boolean _isPoorSystemHash;

      public Default(boolean var1) {
         this._isPoorSystemHash = var1;
      }

      public int hashCodeOf(Object var1) {
         return this._isPoorSystemHash ? REHASH.hashCodeOf(var1) : var1.hashCode();
      }

      public boolean areEqual(Object var1, Object var2) {
         return var1 == null ? var2 == null : var1 == var2 || var1.equals(var2);
      }

      public int compare(Object var1, Object var2) {
         return ((Comparable)var1).compareTo(var2);
      }

      public String toString() {
         return "default";
      }
   }
}
