package javolution.xml.pull;

import javolution.realtime.ObjectFactory;
import javolution.util.FastComparator;

final class CharSequenceImpl implements CharSequence, Comparable {
   private static final ObjectFactory FACTORY = new ObjectFactory() {
      protected Object create() {
         return new CharSequenceImpl();
      }
   };
   static final CharSequenceImpl EMPTY = new CharSequenceImpl("");
   char[] data;
   int offset;
   int length;

   CharSequenceImpl() {
   }

   CharSequenceImpl(String var1) {
      this.data = var1.toCharArray();
      this.offset = 0;
      this.length = var1.length();
   }

   public int length() {
      return this.length;
   }

   public char charAt(int var1) {
      if (var1 >= 0 && var1 < this.length) {
         return this.data[this.offset + var1];
      } else {
         throw new IndexOutOfBoundsException("index: " + var1);
      }
   }

   public CharSequence subSequence(int var1, int var2) {
      if (var1 >= 0 && var2 >= 0 && var1 <= var2 && var2 <= this.length()) {
         CharSequenceImpl var3 = (CharSequenceImpl)FACTORY.object();
         var3.data = this.data;
         var3.offset = this.offset + var1;
         var3.length = var2 - var1;
         return var3;
      } else {
         throw new IndexOutOfBoundsException();
      }
   }

   public String toString() {
      return new String(this.data, this.offset, this.length);
   }

   public int hashCode() {
      int var1 = 0;
      int var2 = 0;

      for(int var3 = this.offset; var2 < this.length; ++var2) {
         var1 = 31 * var1 + this.data[var3++];
      }

      return var1;
   }

   public boolean equals(Object var1) {
      if (var1 instanceof CharSequenceImpl) {
         return this.equals((CharSequenceImpl)var1);
      } else if (var1 instanceof String) {
         return this.equals((String)var1);
      } else {
         return var1 instanceof CharSequence ? this.equals((CharSequence)var1) : false;
      }
   }

   public boolean equals(CharSequenceImpl var1) {
      if (var1 == null) {
         return false;
      } else if (this.length != var1.length) {
         return false;
      } else {
         char[] var2 = var1.data;
         int var3 = this.offset + this.length;
         int var4 = this.offset;
         int var5 = var1.offset;

         do {
            if (var4 >= var3) {
               return true;
            }
         } while(this.data[var4++] == var2[var5++]);

         return false;
      }
   }

   public boolean equals(String var1) {
      if (var1 == null) {
         return false;
      } else if (this.length != var1.length()) {
         return false;
      } else {
         int var2 = 0;
         int var3 = this.offset;

         do {
            if (var2 >= this.length) {
               return true;
            }
         } while(this.data[var3++] == var1.charAt(var2++));

         return false;
      }
   }

   public boolean equals(CharSequence var1) {
      if (var1 == null) {
         return false;
      } else if (this.length != var1.length()) {
         return false;
      } else {
         int var2 = 0;
         int var3 = this.offset;

         do {
            if (var2 >= this.length) {
               return true;
            }
         } while(this.data[var3++] == var1.charAt(var2++));

         return false;
      }
   }

   public int compareTo(Object var1) {
      return FastComparator.LEXICAL.compare(this, var1);
   }
}
