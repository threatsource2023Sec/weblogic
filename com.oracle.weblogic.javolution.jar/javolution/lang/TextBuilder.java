package javolution.lang;

import java.io.IOException;
import java.io.Serializable;
import javax.realtime.MemoryArea;
import javolution.JavolutionError;
import javolution.realtime.Realtime;
import javolution.realtime.RealtimeObject;

public class TextBuilder extends RealtimeObject implements Appendable, CharSequence, Reusable, Serializable {
   private static final RealtimeObject.Factory FACTORY = new RealtimeObject.Factory() {
      public Object create() {
         return new TextBuilder();
      }

      public void cleanup(Object var1) {
         ((TextBuilder)var1).reset();
      }
   };
   private static final int D0 = 5;
   private static final int M0 = 31;
   private static final int C0 = 32;
   private static final int D1 = 7;
   private static final int R1 = 5;
   private static final int M1 = 127;
   private static final int C1 = 4096;
   private static final int D2 = 9;
   private static final int R2 = 12;
   private static final int M2 = 511;
   private static final int C2 = 2097152;
   private static final int D3 = 11;
   private static final int R3 = 21;
   private final char[] _chars0 = new char[32];
   private char[][] _chars1;
   private char[][][] _chars2;
   private char[][][][] _chars3;
   private int _capacity = 32;
   private int _length;

   public TextBuilder() {
   }

   public TextBuilder(CharSequence var1) {
      this.append(var1);
   }

   public TextBuilder(int var1) {
      while(var1 > this._capacity) {
         this.increaseCapacity();
      }

   }

   public static TextBuilder newInstance() {
      return (TextBuilder)FACTORY.object();
   }

   public final int length() {
      return this._length;
   }

   public final char charAt(int var1) {
      if (var1 >= 0 && var1 < this._length) {
         if (var1 < 32) {
            return this._chars0[var1];
         } else if (var1 < 4096) {
            return this._chars1[var1 >> 5][var1 & 31];
         } else {
            return var1 < 2097152 ? this._chars2[var1 >> 12][var1 >> 5 & 127][var1 & 31] : this._chars3[var1 >> 21][var1 >> 12 & 511][var1 >> 5 & 127][var1 & 31];
         }
      } else {
         throw new IndexOutOfBoundsException("index: " + var1);
      }
   }

   public final void getChars(int var1, int var2, char[] var3, int var4) {
      if (var1 >= 0 && var4 >= 0 && var1 <= var2 && var2 <= this.length() && var4 + var2 - var1 <= var3.length) {
         int var5 = var1;

         for(int var6 = var4; var5 < var2; var3[var6++] = this.charAt(var5++)) {
         }

      } else {
         throw new IndexOutOfBoundsException();
      }
   }

   public final void setCharAt(int var1, char var2) {
      if (var1 >= 0 && var1 < this._length) {
         if (var1 < 32) {
            this._chars0[var1] = var2;
         } else if (var1 < 4096) {
            this._chars1[var1 >> 5][var1 & 31] = var2;
         } else if (var1 < 2097152) {
            this._chars2[var1 >> 12][var1 >> 5 & 127][var1 & 31] = var2;
         } else {
            this._chars3[var1 >> 21][var1 >> 12 & 511][var1 >> 5 & 127][var1 & 31] = var2;
         }

      } else {
         throw new IndexOutOfBoundsException("index: " + var1);
      }
   }

   public final void setLength(int var1) {
      if (var1 < 0) {
         throw new IndexOutOfBoundsException();
      } else {
         if (var1 <= this._length) {
            this._length = var1;
         } else {
            int var2 = this._length;

            while(var2++ < var1) {
               this.append('\u0000');
            }
         }

      }
   }

   public final CharSequence subSequence(int var1, int var2) {
      if (var1 >= 0 && var2 >= 0 && var1 <= var2 && var2 <= this._length) {
         return Text.valueOf((CharSequence)this, var1, var2);
      } else {
         throw new IndexOutOfBoundsException();
      }
   }

   public final TextBuilder append(char var1) {
      if (this._length >= this._capacity) {
         this.increaseCapacity();
      }

      int var2 = this._length++;
      if (var2 < 32) {
         this._chars0[var2] = var1;
      } else if (var2 < 4096) {
         this._chars1[var2 >> 5][var2 & 31] = var1;
      } else if (var2 < 2097152) {
         this._chars2[var2 >> 12][var2 >> 5 & 127][var2 & 31] = var1;
      } else {
         this._chars3[var2 >> 21][var2 >> 12 & 511][var2 >> 5 & 127][var2 & 31] = var1;
      }

      return this;
   }

   public final TextBuilder append(CharSequence var1) {
      return var1 == null ? this.append("null") : this.append((CharSequence)var1, 0, var1.length());
   }

   public final TextBuilder append(CharSequence var1, int var2, int var3) {
      if (var1 == null) {
         return this.append("null");
      } else if (var2 >= 0 && var3 >= 0 && var2 <= var3 && var3 <= var1.length()) {
         int var4 = var2;

         while(var4 < var3) {
            this.append(var1.charAt(var4++));
         }

         return this;
      } else {
         throw new IndexOutOfBoundsException();
      }
   }

   public final TextBuilder append(Object var1) {
      if (var1 instanceof String) {
         return this.append((String)var1);
      } else if (var1 instanceof CharSequence) {
         return this.append((CharSequence)var1);
      } else if (var1 instanceof Realtime) {
         return this.append(((Realtime)var1).toText());
      } else {
         return var1 != null ? this.append(var1.toString()) : this.append("null");
      }
   }

   public final TextBuilder append(String var1) {
      if (var1 == null) {
         return this.append("null");
      } else {
         int var2 = var1.length();
         int var3 = 0;

         while(var3 < var2) {
            this.append(var1.charAt(var3++));
         }

         return this;
      }
   }

   public final TextBuilder append(String var1, int var2, int var3) {
      if (var1 == null) {
         return this.append("null");
      } else if (var2 >= 0 && var3 >= 0 && var2 <= var3 && var3 <= var1.length()) {
         int var4 = var2;

         while(var4 < var3) {
            this.append(var1.charAt(var4++));
         }

         return this;
      } else {
         throw new IndexOutOfBoundsException();
      }
   }

   public TextBuilder append(Text var1) {
      if (var1 == null) {
         return this.append("null");
      } else {
         int var2 = var1.length();
         int var3 = 0;

         while(var3 < var2) {
            this.append(var1.charAt(var3++));
         }

         return this;
      }
   }

   public final TextBuilder append(char[] var1) {
      return this.append((char[])var1, 0, var1.length);
   }

   public final TextBuilder append(char[] var1, int var2, int var3) {
      if (var2 >= 0 && var3 >= 0 && var2 + var3 <= var1.length) {
         int var4 = var2 + var3;
         int var5 = var2;

         while(var5 < var4) {
            this.append(var1[var5++]);
         }

         return this;
      } else {
         throw new IndexOutOfBoundsException();
      }
   }

   public final TextBuilder append(boolean var1) {
      try {
         TypeFormat.format(var1, this);
         return this;
      } catch (IOException var3) {
         throw new JavolutionError(var3);
      }
   }

   public final TextBuilder append(int var1) {
      try {
         TypeFormat.format(var1, this);
         return this;
      } catch (IOException var3) {
         throw new JavolutionError(var3);
      }
   }

   public final TextBuilder append(int var1, int var2) {
      try {
         TypeFormat.format(var1, var2, this);
         return this;
      } catch (IOException var4) {
         throw new JavolutionError(var4);
      }
   }

   public final TextBuilder append(long var1) {
      try {
         TypeFormat.format(var1, this);
         return this;
      } catch (IOException var4) {
         throw new JavolutionError(var4);
      }
   }

   public final TextBuilder append(long var1, int var3) {
      try {
         TypeFormat.format(var1, var3, this);
         return this;
      } catch (IOException var5) {
         throw new JavolutionError(var5);
      }
   }

   public final TextBuilder append(float var1) {
      try {
         TypeFormat.format(var1, this);
         return this;
      } catch (IOException var3) {
         throw new JavolutionError(var3);
      }
   }

   public final TextBuilder append(double var1) {
      try {
         TypeFormat.format(var1, this);
         return this;
      } catch (IOException var4) {
         throw new JavolutionError(var4);
      }
   }

   public final TextBuilder append(double var1, int var3, boolean var4, boolean var5) {
      try {
         TypeFormat.format(var1, var3, var4, var5, this);
         return this;
      } catch (IOException var7) {
         throw new JavolutionError(var7);
      }
   }

   public final TextBuilder insert(int var1, CharSequence var2) {
      if (var1 >= 0 && var1 <= this._length) {
         int var3 = var2.length();
         this._length += var3;

         while(this._length >= this._capacity) {
            this.increaseCapacity();
         }

         int var4 = this._length - var3;

         while(true) {
            --var4;
            if (var4 < var1) {
               var4 = var2.length();

               while(true) {
                  --var4;
                  if (var4 < 0) {
                     return this;
                  }

                  this.setCharAt(var1 + var4, var2.charAt(var4));
               }
            }

            this.setCharAt(var4 + var3, this.charAt(var4));
         }
      } else {
         throw new IndexOutOfBoundsException("index: " + var1);
      }
   }

   public final TextBuilder delete(int var1, int var2) {
      if (var1 >= 0 && var2 >= 0 && var1 <= var2 && var2 <= this.length()) {
         int var3 = var2;
         int var4 = var1;

         while(var3 < this._length) {
            this.setCharAt(var4++, this.charAt(var3++));
         }

         this._length -= var2 - var1;
         return this;
      } else {
         throw new IndexOutOfBoundsException();
      }
   }

   public final TextBuilder reverse() {
      int var1 = this._length - 1;
      int var2 = var1 - 1 >> 1;

      while(var2 >= 0) {
         char var3 = this.charAt(var2);
         this.setCharAt(var2, this.charAt(var1 - var2));
         this.setCharAt(var1 - var2--, var3);
      }

      return this;
   }

   public final Text toText() {
      return Text.valueOf((CharSequence)this, 0, this.length());
   }

   public final void reset() {
      this.setLength(0);
   }

   public final int hashCode() {
      int var1 = 0;

      for(int var2 = 0; var2 < this._length; var1 = 31 * var1 + this.charAt(var2++)) {
      }

      return var1;
   }

   public final boolean equals(Object var1) {
      if (this == var1) {
         return true;
      } else if (!(var1 instanceof TextBuilder)) {
         return false;
      } else {
         TextBuilder var2 = (TextBuilder)var1;
         if (this._length != var2._length) {
            return false;
         } else {
            int var3 = 0;

            do {
               if (var3 >= this._length) {
                  return true;
               }
            } while(this.charAt(var3) == var2.charAt(var3++));

            return false;
         }
      }
   }

   private void increaseCapacity() {
      MemoryArea.getMemoryArea(this).executeInArea(new Runnable() {
         public void run() {
            int var1 = TextBuilder.access$000(TextBuilder.this);
            TextBuilder.access$012(TextBuilder.this, 32);
            if (var1 < 4096) {
               if (TextBuilder.access$100(TextBuilder.this) == null) {
                  TextBuilder.access$102(TextBuilder.this, new char[128][]);
               }

               TextBuilder.access$100(TextBuilder.this)[var1 >> 5] = new char[32];
            } else if (var1 < 2097152) {
               if (TextBuilder.access$200(TextBuilder.this) == null) {
                  TextBuilder.access$202(TextBuilder.this, new char[512][][]);
               }

               if (TextBuilder.access$200(TextBuilder.this)[var1 >> 12] == null) {
                  TextBuilder.access$200(TextBuilder.this)[var1 >> 12] = new char[128][];
               }

               TextBuilder.access$200(TextBuilder.this)[var1 >> 12][var1 >> 5 & 127] = new char[32];
            } else {
               if (TextBuilder.access$300(TextBuilder.this) == null) {
                  TextBuilder.access$302(TextBuilder.this, new char[2048][][][]);
               }

               if (TextBuilder.access$300(TextBuilder.this)[var1 >> 21] == null) {
                  TextBuilder.access$300(TextBuilder.this)[var1 >> 21] = new char[512][][];
               }

               if (TextBuilder.access$300(TextBuilder.this)[var1 >> 21][var1 >> 12 & 511] == null) {
                  TextBuilder.access$300(TextBuilder.this)[var1 >> 21][var1 >> 12 & 511] = new char[128][];
               }

               TextBuilder.access$300(TextBuilder.this)[var1 >> 21][var1 >> 12 & 511][var1 >> 5 & 127] = new char[32];
            }

         }
      });
   }

   public Appendable append(char var1) throws IOException {
      return this.append(var1);
   }

   public Appendable append(CharSequence var1, int var2, int var3) throws IOException {
      return this.append(var1, var2, var3);
   }

   public Appendable append(CharSequence var1) throws IOException {
      return this.append(var1);
   }

   static int access$000(TextBuilder var0) {
      return var0._capacity;
   }

   static int access$012(TextBuilder var0, int var1) {
      return var0._capacity += var1;
   }

   static char[][] access$100(TextBuilder var0) {
      return var0._chars1;
   }

   static char[][] access$102(TextBuilder var0, char[][] var1) {
      return var0._chars1 = var1;
   }

   static char[][][] access$200(TextBuilder var0) {
      return var0._chars2;
   }

   static char[][][] access$202(TextBuilder var0, char[][][] var1) {
      return var0._chars2 = var1;
   }

   static char[][][][] access$300(TextBuilder var0) {
      return var0._chars3;
   }

   static char[][][][] access$302(TextBuilder var0, char[][][][] var1) {
      return var0._chars3 = var1;
   }
}
