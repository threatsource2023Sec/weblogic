package javolution.xml;

import javolution.lang.PersistentReference;
import javolution.lang.Reusable;
import javolution.lang.Text;
import javolution.lang.TextBuilder;
import javolution.xml.sax.Attributes;

final class FormatAttributes implements Attributes, Reusable {
   private static final PersistentReference CAPACITY = new PersistentReference("javolution.xml.FormatAttributes#CAPACITY", new Integer(16));
   private String[] _qNames;
   private CharSequence[] _values;
   private TextBuilder[] _textBuilders;
   private int _length;

   public FormatAttributes() {
      this._qNames = new String[(Integer)CAPACITY.get()];
      this._values = new CharSequence[(Integer)CAPACITY.get()];
      this._textBuilders = new TextBuilder[(Integer)CAPACITY.get()];

      for(int var1 = 0; var1 < this._textBuilders.length; this._textBuilders[var1++] = new TextBuilder()) {
      }

   }

   public int getLength() {
      return this._length;
   }

   public CharSequence getURI(int var1) {
      return var1 >= 0 && var1 < this._length ? Text.EMPTY : null;
   }

   public CharSequence getLocalName(int var1) {
      return var1 >= 0 && var1 < this._length ? this.toCharSeq(this._qNames[var1]) : null;
   }

   public CharSequence getPrefix(int var1) {
      return var1 >= 0 && var1 < this._length ? Text.EMPTY : null;
   }

   public CharSequence getQName(int var1) {
      return var1 >= 0 && var1 < this._length ? this.toCharSeq(this._qNames[var1]) : null;
   }

   public String getType(int var1) {
      return var1 >= 0 && var1 < this._length ? "CDATA" : null;
   }

   public CharSequence getValue(int var1) {
      return var1 >= 0 && var1 < this._length ? this._values[var1] : null;
   }

   public int getIndex(String var1, String var2) {
      for(int var3 = 0; var3 < this._length; ++var3) {
         if (this._qNames[var3].equals(var2)) {
            return var3;
         }
      }

      return -1;
   }

   public int getIndex(String var1) {
      for(int var2 = 0; var2 < this._length; ++var2) {
         if (this._qNames[var2].equals(var1)) {
            return var2;
         }
      }

      return -1;
   }

   public String getType(String var1, String var2) {
      int var3 = this.getIndex(var1, var2);
      return var3 >= 0 ? "CDATA" : null;
   }

   public String getType(String var1) {
      int var2 = this.getIndex(var1);
      return var2 >= 0 ? "CDATA" : null;
   }

   public CharSequence getValue(String var1, String var2) {
      int var3 = this.getIndex(var1, var2);
      return var3 >= 0 ? this._values[var3] : null;
   }

   public CharSequence getValue(String var1) {
      int var2 = this.getIndex(var1);
      return var2 >= 0 ? this._values[var2] : null;
   }

   public void reset() {
      this._length = 0;
   }

   public void addAttribute(String var1, CharSequence var2) {
      if (this._length >= this._qNames.length) {
         int var3 = this._length * 2;
         CAPACITY.setMinimum(new Integer(var3));
         String[] var4 = new String[var3];
         System.arraycopy(this._qNames, 0, var4, 0, this._length);
         this._qNames = var4;
         CharSequence[] var5 = new CharSequence[var3];
         System.arraycopy(this._values, 0, var5, 0, this._length);
         this._values = var5;
         TextBuilder[] var6 = new TextBuilder[var3];
         System.arraycopy(this._textBuilders, 0, var6, 0, this._length);
         this._textBuilders = var6;

         for(int var7 = this._length; var7 < this._textBuilders.length; this._textBuilders[var7++] = new TextBuilder()) {
         }
      }

      this._qNames[this._length] = var1;
      this._values[this._length++] = var2;
   }

   public TextBuilder newAttribute(String var1) {
      this.addAttribute(var1, (CharSequence)null);
      TextBuilder var2 = this._textBuilders[this._length - 1];
      this._values[this._length - 1] = var2;
      var2.reset();
      return var2;
   }

   public void remove(int var1) {
      this._qNames[var1] = this._qNames[--this._length];
      this._values[var1] = this._values[this._length];
      TextBuilder var2 = this._textBuilders[this._length];
      this._textBuilders[this._length] = this._textBuilders[var1];
      this._textBuilders[var1] = var2;
   }

   private CharSequence toCharSeq(Object var1) {
      return (CharSequence)(var1 instanceof CharSequence ? (CharSequence)var1 : Text.valueOf((Object)((String)var1)));
   }

   public String toString() {
      Text var1 = Text.valueOf('[');
      Text var2 = Text.valueOf('=');
      Text var3 = Text.valueOf((Object)", ");
      int var4 = 0;

      while(var4 < this._length) {
         var1 = var1.concat(Text.valueOf((Object)this._qNames[var4]).concat(var2).concat(Text.valueOf((Object)this._values[var4])));
         ++var4;
         if (var4 != this._length) {
            var1 = var1.concat(var3);
         }
      }

      return var1.concat(Text.valueOf(']')).toString();
   }
}
