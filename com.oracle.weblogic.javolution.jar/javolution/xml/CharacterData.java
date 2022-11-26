package javolution.xml;

import java.io.Serializable;
import javolution.lang.PersistentReference;
import javolution.lang.Text;
import javolution.realtime.RealtimeObject;
import javolution.util.FastComparator;

public final class CharacterData extends RealtimeObject implements Serializable, CharSequence {
   private static final RealtimeObject.Factory FACTORY = new RealtimeObject.Factory() {
      protected Object create() {
         return new CharacterData();
      }

      protected void cleanup(Object var1) {
         CharacterData.access$102((CharacterData)var1, (char[])null);
      }
   };
   private static final PersistentReference LENGTH = new PersistentReference("javolution.xml.CharacterData#LENGTH", new Integer(0));
   private char[] _chars;
   private int _offset;
   private int _length;
   private char[] _buffer;

   private CharacterData() {
      this._buffer = new char[(Integer)LENGTH.get()];
   }

   public static CharacterData valueOf(char[] var0, int var1, int var2) {
      if (var1 >= 0 && var2 >= 0 && var1 + var2 <= var0.length) {
         CharacterData var3 = (CharacterData)FACTORY.object();
         var3._chars = var0;
         var3._offset = var1;
         var3._length = var2;
         return var3;
      } else {
         throw new IndexOutOfBoundsException();
      }
   }

   public static CharacterData valueOf(CharSequence var0) {
      int var1 = var0.length();
      CharacterData var2 = (CharacterData)FACTORY.object();
      if (var1 > var2._buffer.length) {
         var2._buffer = new char[var1];
         LENGTH.setMinimum(new Integer(var1));
      }

      var2._chars = var2._buffer;
      var2._offset = 0;
      var2._length = var1;

      for(int var3 = 0; var3 < var1; var2._chars[var3] = var0.charAt(var3++)) {
      }

      return var2;
   }

   public char[] chars() {
      return this._chars;
   }

   public int offset() {
      return this._offset;
   }

   public int length() {
      return this._length;
   }

   public char charAt(int var1) {
      if (var1 >= this._length) {
         throw new IndexOutOfBoundsException();
      } else {
         return this._chars[var1];
      }
   }

   public CharSequence subSequence(int var1, int var2) {
      if (var1 >= 0 && var1 <= var2 && var2 <= this._length) {
         return valueOf(this._chars, this._offset + var1, var2 - var1);
      } else {
         throw new IndexOutOfBoundsException();
      }
   }

   public final boolean equals(Object var1) {
      if (this == var1) {
         return true;
      } else {
         return !(var1 instanceof CharacterData) ? false : FastComparator.LEXICAL.areEqual(this, var1);
      }
   }

   public final int hashCode() {
      return FastComparator.LEXICAL.hashCodeOf(this);
   }

   public Text toText() {
      return Text.valueOf(this._chars, this._offset, this._length);
   }

   CharacterData(Object var1) {
      this();
   }

   static char[] access$102(CharacterData var0, char[] var1) {
      return var0._chars = var1;
   }
}
