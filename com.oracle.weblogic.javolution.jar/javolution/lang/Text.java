package javolution.lang;

import java.io.IOException;
import java.io.Serializable;
import javax.realtime.MemoryArea;
import javolution.JavolutionError;
import javolution.realtime.HeapContext;
import javolution.realtime.Realtime;
import javolution.realtime.RealtimeObject;
import javolution.util.FastComparator;
import javolution.util.FastMap;

public abstract class Text extends RealtimeObject implements CharSequence, Comparable, Serializable, Immutable {
   private static final FastMap INTERN_TEXT;
   public static final Text EMPTY;
   public static final Text NULL;
   int _count;
   int _hashCode;
   private static final Text TRUE;
   private static final Text FALSE;
   private static final Text[] ASCII;

   private Text() {
   }

   public static Text valueOf(Object var0) {
      if (var0 instanceof String) {
         return Text.StringWrapper.access$000((String)var0);
      } else if (var0 instanceof Realtime) {
         return ((Realtime)var0).toText();
      } else if (var0 instanceof CharSequence) {
         CharSequence var1 = (CharSequence)var0;
         return valueOf((CharSequence)var1, 0, var1.length());
      } else {
         return (Text)(var0 != null ? Text.StringWrapper.access$000(var0.toString()) : NULL);
      }
   }

   public static Text valueOf(CharSequence var0, int var1, int var2) {
      if (var1 >= 0 && var2 >= 0 && var1 <= var2 && var2 <= var0.length()) {
         int var3 = var2 - var1;
         if (var3 > 32) {
            int var6 = var1 + (var3 >> 1);
            Composite var7 = Text.Composite.access$300(valueOf(var0, var1, var6), valueOf(var0, var6, var2));
            return var7;
         } else {
            Primitive var4 = Text.Primitive.access$100(var3);

            for(int var5 = 0; var5 < var3; Text.Primitive.access$200(var4)[var5] = var0.charAt(var1 + var5++)) {
            }

            return var4;
         }
      } else {
         throw new IndexOutOfBoundsException();
      }
   }

   public static Text valueOf(char[] var0) {
      return valueOf((char[])var0, 0, var0.length);
   }

   public static Text valueOf(char[] var0, int var1, int var2) {
      if (var1 >= 0 && var2 >= 0 && var1 + var2 <= var0.length) {
         if (var2 > 32) {
            int var5 = var1 + (var2 >> 1);
            Composite var6 = Text.Composite.access$300(valueOf(var0, var1, var5 - var1), valueOf(var0, var5, var1 + var2 - var5));
            return var6;
         } else {
            Primitive var3 = Text.Primitive.access$100(var2);

            for(int var4 = 0; var4 < var2; Text.Primitive.access$200(var3)[var4] = var0[var1 + var4++]) {
            }

            return var3;
         }
      } else {
         throw new IndexOutOfBoundsException();
      }
   }

   public static Text valueOf(boolean var0) {
      return var0 ? TRUE : FALSE;
   }

   public static Text valueOf(char var0) {
      if (var0 < 128 && ASCII[var0] != null) {
         return ASCII[var0];
      } else {
         Primitive var1 = Text.Primitive.access$100(1);
         Text.Primitive.access$200(var1)[0] = var0;
         Text var2 = intern((CharSequence)var1);
         if (var0 < 128) {
            ASCII[var0] = var2;
         }

         return var2;
      }
   }

   public static Text valueOf(int var0) {
      try {
         Primitive var1 = Text.Primitive.access$100(0);
         TypeFormat.format(var0, var1);
         return var1;
      } catch (IOException var2) {
         throw new JavolutionError(var2);
      }
   }

   public static Text valueOf(int var0, int var1) {
      try {
         Primitive var2 = Text.Primitive.access$100(0);
         TypeFormat.format(var0, var1, var2);
         return var2;
      } catch (IOException var3) {
         throw new JavolutionError(var3);
      }
   }

   public static Text valueOf(long var0) {
      try {
         Primitive var2 = Text.Primitive.access$100(0);
         TypeFormat.format(var0, var2);
         return var2;
      } catch (IOException var3) {
         throw new JavolutionError(var3);
      }
   }

   public static Text valueOf(long var0, int var2) {
      try {
         Primitive var3 = Text.Primitive.access$100(0);
         TypeFormat.format(var0, var2, var3);
         return var3;
      } catch (IOException var4) {
         throw new JavolutionError(var4);
      }
   }

   public static Text valueOf(float var0) {
      try {
         Primitive var1 = Text.Primitive.access$100(0);
         TypeFormat.format(var0, var1);
         return var1;
      } catch (IOException var2) {
         throw new JavolutionError(var2);
      }
   }

   public static Text valueOf(double var0) {
      try {
         Primitive var2 = Text.Primitive.access$100(0);
         TypeFormat.format(var0, var2);
         return var2;
      } catch (IOException var3) {
         throw new JavolutionError(var3);
      }
   }

   public static Text valueOf(double var0, int var2, boolean var3, boolean var4) {
      try {
         Primitive var5 = Text.Primitive.access$100(0);
         TypeFormat.format(var0, var2, var3, var4, var5);
         return var5;
      } catch (IOException var6) {
         throw new JavolutionError(var6);
      }
   }

   public final int length() {
      return this._count;
   }

   public final Text plus(Object var1) {
      return this.concat(valueOf(var1));
   }

   public final Text concat(Text var1) {
      if (this._count == 0) {
         return var1;
      } else if (var1._count == 0) {
         return this;
      } else {
         Composite var2;
         if (var1._count << 1 < this._count && this instanceof Composite) {
            var2 = (Composite)this;
            return Text.Composite.access$400(var2)._count > Text.Composite.access$500(var2)._count ? Text.Composite.access$300(Text.Composite.access$400(var2), Text.Composite.access$500(var2).concat(var1)) : Text.Composite.access$300(this, var1);
         } else if (this._count << 1 < var1._count && var1 instanceof Composite) {
            var2 = (Composite)var1;
            return Text.Composite.access$400(var2)._count < Text.Composite.access$500(var2)._count ? Text.Composite.access$300(this.concat(Text.Composite.access$400(var2)), Text.Composite.access$500(var2)) : Text.Composite.access$300(this, var1);
         } else {
            return Text.Composite.access$300(this, var1);
         }
      }
   }

   public final Text subtext(int var1) {
      return this.subtext(var1, this.length());
   }

   public final Text insert(int var1, Text var2) {
      return var1 << 1 < this._count ? this.subtext(0, var1).concat(var2).concat(this.subtext(var1)) : this.subtext(0, var1).concat(var2.concat(this.subtext(var1)));
   }

   public final Text delete(int var1, int var2) {
      return this.subtext(0, var1).concat(this.subtext(var2));
   }

   public final Text replace(Text var1, Text var2) {
      int var3 = this.indexOf(var1);
      return var3 < 0 ? this : this.subtext(0, var3).concat(var2).concat(this.subtext(var3 + var1.length()).replace(var1, var2));
   }

   public final CharSequence subSequence(int var1, int var2) {
      return this.subtext(var1, var2);
   }

   public final int indexOf(CharSequence var1) {
      return this.indexOf(var1, 0);
   }

   public final int indexOf(CharSequence var1, int var2) {
      int var3 = var1.length();
      int var4 = Math.max(0, var2);
      int var5 = this._count - var3;
      if (var3 == 0) {
         return var4 > var5 ? -1 : var4;
      } else {
         char var6 = var1.charAt(0);

         for(int var7 = this.indexOf(var6, var4); var7 >= 0 && var7 <= var5; var7 = this.indexOf(var6, var7)) {
            boolean var8 = true;

            for(int var9 = 1; var9 < var3; ++var9) {
               if (this.charAt(var7 + var9) != var1.charAt(var9)) {
                  var8 = false;
                  break;
               }
            }

            if (var8) {
               return var7;
            }

            ++var7;
         }

         return -1;
      }
   }

   public final int lastIndexOf(CharSequence var1) {
      return this.lastIndexOf(var1, this._count);
   }

   public final int lastIndexOf(CharSequence var1, int var2) {
      int var3 = var1.length();
      int var5 = Math.min(var2, this._count - var3);
      if (var3 == 0) {
         return 0 > var5 ? -1 : var5;
      } else {
         char var6 = var1.charAt(0);

         for(int var7 = this.lastIndexOf(var6, var5); var7 >= 0; var7 = this.lastIndexOf(var6, var7)) {
            boolean var8 = true;

            for(int var9 = 1; var9 < var3; ++var9) {
               if (this.charAt(var7 + var9) != var1.charAt(var9)) {
                  var8 = false;
                  break;
               }
            }

            if (var8) {
               return var7;
            }

            --var7;
         }

         return -1;
      }
   }

   public final boolean startsWith(CharSequence var1) {
      return this.startsWith(var1, 0);
   }

   public final boolean endsWith(CharSequence var1) {
      return this.startsWith(var1, this.length() - var1.length());
   }

   public final boolean startsWith(CharSequence var1, int var2) {
      int var3 = var1.length();
      if (var2 >= 0 && var2 <= this.length() - var3) {
         int var4 = 0;
         int var5 = var2;

         do {
            if (var4 >= var3) {
               return true;
            }
         } while(var1.charAt(var4++) == this.charAt(var5++));

         return false;
      } else {
         return false;
      }
   }

   public final Text trim() {
      int var1 = 0;

      int var2;
      for(var2 = this.length() - 1; var1 <= var2 && this.charAt(var1) <= ' '; ++var1) {
      }

      while(var2 >= var1 && this.charAt(var2) <= ' ') {
         --var2;
      }

      return this.subtext(var1, var2 + 1);
   }

   public static Text intern(final CharSequence var0) {
      Text var1 = (Text)INTERN_TEXT.get(var0);
      if (var1 != null) {
         return var1;
      } else {
         synchronized(INTERN_TEXT) {
            var1 = (Text)INTERN_TEXT.get(var0);
            if (var1 != null) {
               return var1;
            }

            MemoryArea.getMemoryArea(INTERN_TEXT).executeInArea(new Runnable() {
               public void run() {
                  HeapContext.enter();

                  try {
                     Text var1 = Text.valueOf((CharSequence)var0, 0, var0.length());
                     Text.access$600().put(var1, var1);
                  } finally {
                     HeapContext.exit();
                  }

               }
            });
         }

         return (Text)INTERN_TEXT.get(var0);
      }
   }

   public static Text intern(final String var0) {
      Text var1 = (Text)INTERN_TEXT.get(var0);
      if (var1 != null) {
         return var1;
      } else {
         synchronized(INTERN_TEXT) {
            var1 = (Text)INTERN_TEXT.get(var0);
            if (var1 != null) {
               return var1;
            }

            MemoryArea.getMemoryArea(INTERN_TEXT).executeInArea(new Runnable() {
               public void run() {
                  HeapContext.enter();

                  try {
                     Text var1 = Text.valueOf((Object)(new String(var0)));
                     Text.access$600().put(var1, var1);
                  } finally {
                     HeapContext.exit();
                  }

               }
            });
         }

         return (Text)INTERN_TEXT.get(var0);
      }
   }

   public final boolean contentEquals(CharSequence var1) {
      if (var1.length() != this._count) {
         return false;
      } else {
         int var2 = 0;

         do {
            if (var2 >= this._count) {
               return true;
            }
         } while(this.charAt(var2) == var1.charAt(var2++));

         return false;
      }
   }

   public final boolean contentEqualsIgnoreCase(CharSequence var1) {
      if (this._count != var1.length()) {
         return false;
      } else {
         int var2 = 0;

         while(var2 < this._count) {
            char var3 = this.charAt(var2);
            char var4 = var1.charAt(var2++);
            if (var3 != var4) {
               var3 = Character.toUpperCase(var3);
               var4 = Character.toUpperCase(var4);
               if (var3 != var4 && Character.toLowerCase(var3) != Character.toLowerCase(var4)) {
                  return false;
               }
            }
         }

         return true;
      }
   }

   public final boolean equals(Object var1) {
      if (this == var1) {
         return true;
      } else if (!(var1 instanceof Text)) {
         return false;
      } else {
         Text var2 = (Text)var1;
         if (this._count != var2._count) {
            return false;
         } else {
            int var3 = 0;

            do {
               if (var3 >= this._count) {
                  return true;
               }
            } while(this.charAt(var3) == var2.charAt(var3++));

            return false;
         }
      }
   }

   public final int hashCode() {
      if (this._hashCode != 0) {
         return this._hashCode;
      } else {
         int var1 = this._hashCode;
         int var2 = this.length();

         for(int var3 = 0; var3 < var2; var1 = 31 * var1 + this.charAt(var3++)) {
         }

         return this._hashCode = var1;
      }
   }

   public final int compareTo(Object var1) {
      return FastComparator.LEXICAL.compare(this, var1);
   }

   public final Text toText() {
      return this;
   }

   public final Text copy() {
      return valueOf((CharSequence)this, 0, this._count);
   }

   public abstract int depth();

   public abstract char charAt(int var1);

   public abstract int indexOf(char var1, int var2);

   public abstract int lastIndexOf(char var1, int var2);

   public abstract Text subtext(int var1, int var2);

   public abstract void getChars(int var1, int var2, char[] var3, int var4);

   public abstract Text toLowerCase();

   public abstract Text toUpperCase();

   public abstract String stringValue();

   public static Text valueOf(char var0, int var1) {
      if (var1 < 0) {
         throw new IndexOutOfBoundsException();
      } else if (var1 > 32) {
         int var4 = var1 >> 1;
         return Text.Composite.access$300(valueOf(var0, var4), valueOf(var0, var1 - var4));
      } else {
         Primitive var2 = Text.Primitive.access$100(var1);

         for(int var3 = 0; var3 < var1; Text.Primitive.access$200(var2)[var3++] = var0) {
         }

         return var2;
      }
   }

   public final boolean isBlank() {
      return this.isBlank(0, this.length());
   }

   public final boolean isBlank(int var1, int var2) {
      while(var1 < var2) {
         if (this.charAt(var1) > ' ') {
            return false;
         }

         ++var1;
      }

      return true;
   }

   public final Text trimStart() {
      int var1 = 0;

      int var2;
      for(var2 = this.length() - 1; var1 <= var2 && this.charAt(var1) <= ' '; ++var1) {
      }

      return this.subtext(var1, var2 + 1);
   }

   public final Text trimEnd() {
      byte var1 = 0;

      int var2;
      for(var2 = this.length() - 1; var2 >= var1 && this.charAt(var2) <= ' '; --var2) {
      }

      return this.subtext(var1, var2 + 1);
   }

   public final Text padLeft(int var1) {
      return this.padLeft(var1, ' ');
   }

   public final Text padLeft(int var1, char var2) {
      int var3 = var1 <= this.length() ? 0 : var1 - this.length();
      return this.insert(0, valueOf(var2, var3));
   }

   public final Text padRight(int var1) {
      return this.padRight(var1, ' ');
   }

   public final Text padRight(int var1, char var2) {
      int var3 = var1 <= this.length() ? 0 : var1 - this.length();
      return this.concat(valueOf(var2, var3));
   }

   public final int indexOfAny(CharSet var1) {
      return this.indexOfAny(var1, 0, this.length());
   }

   public final int indexOfAny(CharSet var1, int var2) {
      return this.indexOfAny(var1, var2, this.length());
   }

   public final int indexOfAny(CharSet var1, int var2, int var3) {
      int var4 = var2 + var3;

      for(int var5 = var2; var5 < var4; ++var5) {
         if (var1.contains(this.charAt(var5))) {
            return var5;
         }
      }

      return -1;
   }

   public final int lastIndexOfAny(CharSet var1) {
      return this.lastIndexOfAny(var1, 0, this.length());
   }

   public final int lastIndexOfAny(CharSet var1, int var2) {
      return this.lastIndexOfAny(var1, var2, this.length() - var2);
   }

   public final int lastIndexOfAny(CharSet var1, int var2, int var3) {
      int var4 = var2 + var3;

      do {
         --var4;
         if (var4 < var2) {
            return -1;
         }
      } while(!var1.contains(this.charAt(var4)));

      return var4;
   }

   static FastMap access$600() {
      return INTERN_TEXT;
   }

   Text(Object var1) {
      this();
   }

   static {
      INTERN_TEXT = (new FastMap()).setKeyComparator(FastComparator.LEXICAL);
      EMPTY = intern("");
      NULL = intern("null");
      TRUE = intern("true");
      FALSE = intern("false");
      ASCII = new Text[128];
   }

   private static final class StringWrapper extends Text {
      private static final RealtimeObject.Factory FACTORY = new RealtimeObject.Factory() {
         public Object create() {
            return new StringWrapper();
         }
      };
      private String _string;
      private int _offset;

      private StringWrapper() {
         super(null);
      }

      private static StringWrapper newInstance(String var0) {
         StringWrapper var1 = (StringWrapper)FACTORY.object();
         var1._count = var0.length();
         var1._hashCode = 0;
         var1._string = var0;
         var1._offset = 0;
         return var1;
      }

      public int depth() {
         return 0;
      }

      public char charAt(int var1) {
         if (var1 < this._count && var1 >= 0) {
            return this._string.charAt(this._offset + var1);
         } else {
            throw new IndexOutOfBoundsException();
         }
      }

      public int indexOf(char var1, int var2) {
         for(int var3 = Math.max(var2, 0); var3 < this._count; ++var3) {
            if (this._string.charAt(this._offset + var3) == var1) {
               return var3;
            }
         }

         return -1;
      }

      public int lastIndexOf(char var1, int var2) {
         for(int var3 = Math.min(var2, this._count - 1); var3 >= 0; --var3) {
            if (this._string.charAt(this._offset + var3) == var1) {
               return var3;
            }
         }

         return -1;
      }

      public Text subtext(int var1, int var2) {
         if (var1 == 0 && var2 == this._count) {
            return this;
         } else if (var1 >= 0 && var1 <= var2 && var2 <= this._count) {
            if (var1 == var2) {
               return Text.EMPTY;
            } else {
               StringWrapper var3 = (StringWrapper)FACTORY.object();
               var3._count = var2 - var1;
               var3._hashCode = 0;
               var3._string = this._string;
               var3._offset = this._offset + var1;
               return var3;
            }
         } else {
            throw new IndexOutOfBoundsException();
         }
      }

      public void getChars(int var1, int var2, char[] var3, int var4) {
         if (var2 <= this._count && var2 >= var1 && var1 >= 0) {
            this._string.getChars(var1 + this._offset, var2 + this._offset, var3, var4);
         } else {
            throw new IndexOutOfBoundsException();
         }
      }

      public Text toLowerCase() {
         return this.copy().toLowerCase();
      }

      public Text toUpperCase() {
         return this.copy().toUpperCase();
      }

      public String stringValue() {
         return this._offset == 0 && this._count == this._string.length() ? this._string : this._string.substring(this._offset, this._offset + this._count);
      }

      static StringWrapper access$000(String var0) {
         return newInstance(var0);
      }

      StringWrapper(Object var1) {
         this();
      }
   }

   private static final class Composite extends Text {
      private static final RealtimeObject.Factory FACTORY = new RealtimeObject.Factory() {
         public Object create() {
            return new Composite();
         }
      };
      private Text _head;
      private Text _tail;

      private Composite() {
         super(null);
      }

      private static Composite newInstance(Text var0, Text var1) {
         Composite var2 = (Composite)FACTORY.object();
         var2._hashCode = 0;
         var2._count = var0._count + var1._count;
         var2._head = var0;
         var2._tail = var1;
         return var2;
      }

      public int depth() {
         return Math.max(this._head.depth(), this._tail.depth()) + 1;
      }

      public char charAt(int var1) {
         return var1 < this._head._count ? this._head.charAt(var1) : this._tail.charAt(var1 - this._head._count);
      }

      public int indexOf(char var1, int var2) {
         int var3 = this._head._count;
         int var4;
         if (var2 < var3) {
            var4 = this._head.indexOf(var1, var2);
            if (var4 >= 0) {
               return var4;
            }
         }

         var4 = this._tail.indexOf(var1, var2 - var3);
         return var4 >= 0 ? var4 + var3 : -1;
      }

      public int lastIndexOf(char var1, int var2) {
         int var3 = this._head._count;
         if (var2 >= var3) {
            int var4 = this._tail.lastIndexOf(var1, var2 - var3);
            if (var4 >= 0) {
               return var4 + var3;
            }
         }

         return this._head.lastIndexOf(var1, var2);
      }

      public Text subtext(int var1, int var2) {
         int var3 = this._head._count;
         if (var2 <= var3) {
            return this._head.subtext(var1, var2);
         } else if (var1 >= var3) {
            return this._tail.subtext(var1 - var3, var2 - var3);
         } else {
            return (Text)(var1 == 0 && var2 == this._count ? this : this._head.subtext(var1, var3).concat(this._tail.subtext(0, var2 - var3)));
         }
      }

      public void getChars(int var1, int var2, char[] var3, int var4) {
         int var5 = this._head._count;
         if (var2 <= var5) {
            this._head.getChars(var1, var2, var3, var4);
         } else if (var1 >= var5) {
            this._tail.getChars(var1 - var5, var2 - var5, var3, var4);
         } else {
            this._head.getChars(var1, var5, var3, var4);
            this._tail.getChars(0, var2 - var5, var3, var4 + var5 - var1);
         }

      }

      public Text toLowerCase() {
         return newInstance(this._head.toLowerCase(), this._tail.toLowerCase());
      }

      public Text toUpperCase() {
         return newInstance(this._head.toUpperCase(), this._tail.toUpperCase());
      }

      public String stringValue() {
         char[] var1 = new char[this._count];
         this.getChars(0, this._count, var1, 0);
         return new String(var1, 0, this._count);
      }

      public boolean move(Realtime.ObjectSpace var1) {
         if (super.move(var1)) {
            this._head.move(var1);
            this._tail.move(var1);
            return true;
         } else {
            return false;
         }
      }

      static Composite access$300(Text var0, Text var1) {
         return newInstance(var0, var1);
      }

      static Text access$400(Composite var0) {
         return var0._head;
      }

      static Text access$500(Composite var0) {
         return var0._tail;
      }

      Composite(Object var1) {
         this();
      }
   }

   private static final class Primitive extends Text implements Appendable {
      private static final int BLOCK_SIZE = 32;
      private static final RealtimeObject.Factory FACTORY = new RealtimeObject.Factory() {
         public Object create() {
            return new Primitive();
         }
      };
      private final char[] _data;

      private Primitive() {
         super(null);
         this._data = new char[32];
      }

      private static Primitive newInstance(int var0) {
         Primitive var1 = (Primitive)FACTORY.object();
         var1._count = var0;
         var1._hashCode = 0;
         return var1;
      }

      public int depth() {
         return 0;
      }

      public char charAt(int var1) {
         if (var1 >= this._count) {
            throw new IndexOutOfBoundsException();
         } else {
            return this._data[var1];
         }
      }

      public int indexOf(char var1, int var2) {
         for(int var3 = Math.max(var2, 0); var3 < this._count; ++var3) {
            if (this._data[var3] == var1) {
               return var3;
            }
         }

         return -1;
      }

      public int lastIndexOf(char var1, int var2) {
         for(int var3 = Math.min(var2, this._count - 1); var3 >= 0; --var3) {
            if (this._data[var3] == var1) {
               return var3;
            }
         }

         return -1;
      }

      public Text subtext(int var1, int var2) {
         if (var1 == 0 && var2 == this._count) {
            return this;
         } else if (var1 >= 0 && var1 <= var2 && var2 <= this._count) {
            if (var1 == var2) {
               return Text.EMPTY;
            } else {
               Primitive var3 = newInstance(var2 - var1);
               int var4 = var1;

               for(int var5 = 0; var4 < var2; var3._data[var5++] = this._data[var4++]) {
               }

               return var3;
            }
         } else {
            throw new IndexOutOfBoundsException();
         }
      }

      public void getChars(int var1, int var2, char[] var3, int var4) {
         if (var2 <= this._count && var2 >= var1) {
            int var5 = var1;

            for(int var6 = var4; var5 < var2; var3[var6++] = this._data[var5++]) {
            }

         } else {
            throw new IndexOutOfBoundsException();
         }
      }

      public Text toLowerCase() {
         Primitive var1 = newInstance(this._count);

         for(int var2 = 0; var2 < this._count; var1._data[var2] = Character.toLowerCase(this._data[var2++])) {
         }

         return var1;
      }

      public Text toUpperCase() {
         Primitive var1 = newInstance(this._count);

         for(int var2 = 0; var2 < this._count; var1._data[var2] = Character.toUpperCase(this._data[var2++])) {
         }

         return var1;
      }

      public String stringValue() {
         return new String(this._data, 0, this._count);
      }

      public Appendable append(char var1) throws IOException {
         this._data[this._count++] = var1;
         return this;
      }

      public Appendable append(CharSequence var1) throws IOException {
         return this.append(var1, 0, var1.length());
      }

      public Appendable append(CharSequence var1, int var2, int var3) throws IOException {
         for(int var4 = var2; var4 < var3; this._data[this._count++] = var1.charAt(var4++)) {
         }

         return this;
      }

      static Primitive access$100(int var0) {
         return newInstance(var0);
      }

      static char[] access$200(Primitive var0) {
         return var0._data;
      }

      Primitive(Object var1) {
         this();
      }
   }
}
