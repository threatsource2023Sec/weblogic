package javolution.lang;

import java.io.IOException;
import java.text.ParsePosition;
import javolution.realtime.ObjectFactory;

public abstract class TextFormat {
   protected TextFormat() {
   }

   public abstract Appendable format(Object var1, Appendable var2) throws IOException;

   public abstract Object parse(CharSequence var1, Cursor var2);

   public final Text format(Object var1) {
      try {
         TextBuilder var2 = TextBuilder.newInstance();
         this.format(var1, var2);
         return var2.toText();
      } catch (IOException var3) {
         throw new Error();
      }
   }

   public final Object parse(CharSequence var1) {
      Cursor var2 = TextFormat.Cursor.newInstance();

      Object var4;
      try {
         Object var3 = this.parse(var1, var2);
         if (var2.getIndex() != var1.length()) {
            throw new IllegalArgumentException("Parsing of " + var1 + " incomplete (terminated at index: " + var2.getIndex() + ")");
         }

         var4 = var3;
      } catch (IllegalArgumentException var9) {
         throw var9;
      } catch (RuntimeException var10) {
         throw new IllegalArgumentException("Cannot parse \"" + var1 + "\" (" + var10.toString() + ")");
      } finally {
         var2.recycle();
      }

      return var4;
   }

   public static class Cursor extends ParsePosition {
      private static final ObjectFactory FACTORY = new ObjectFactory() {
         public Object create() {
            return new Cursor();
         }
      };
      private int _index;

      private Cursor() {
         super(0);
      }

      public static Cursor newInstance() {
         Cursor var0 = (Cursor)FACTORY.object();
         var0._index = 0;
         return var0;
      }

      public final int getIndex() {
         return this._index;
      }

      public final void setIndex(int var1) {
         this._index = var1;
      }

      public final boolean hasNext(CharSequence var1) {
         return this._index < var1.length();
      }

      public final char next(CharSequence var1) {
         return var1.charAt(this._index++);
      }

      public final boolean skip(char var1, CharSequence var2) {
         int var3;
         for(var3 = var2.length(); this._index < var3 && var2.charAt(this._index) == var1; ++this._index) {
         }

         return this._index < var3;
      }

      public final boolean skip(CharSet var1, CharSequence var2) {
         int var3;
         for(var3 = var2.length(); this._index < var3 && var1.contains(var2.charAt(this._index)); ++this._index) {
         }

         return this._index < var3;
      }

      public final void increment() {
         ++this._index;
      }

      public final void increment(int var1) {
         this._index += var1;
      }

      public final void recycle() {
         FACTORY.currentPool().recycle(this);
      }

      Cursor(Object var1) {
         this();
      }
   }
}
