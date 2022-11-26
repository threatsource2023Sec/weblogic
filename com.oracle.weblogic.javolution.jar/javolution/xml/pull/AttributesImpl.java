package javolution.xml.pull;

import javax.realtime.MemoryArea;
import javolution.lang.Reusable;
import javolution.lang.Text;
import javolution.xml.sax.Attributes;

final class AttributesImpl implements Attributes, Reusable {
   private CharSequenceImpl[] _localNames = new CharSequenceImpl[16];
   private CharSequenceImpl[] _prefixes = new CharSequenceImpl[16];
   private CharSequenceImpl[] _qNames = new CharSequenceImpl[16];
   private CharSequenceImpl[] _values = new CharSequenceImpl[16];
   private final Namespaces _namespaces;
   private int _length;

   public AttributesImpl(Namespaces var1) {
      this._namespaces = var1;
   }

   public int getLength() {
      return this._length;
   }

   public CharSequence getURI(int var1) {
      return var1 >= 0 && var1 < this._length ? this._namespaces.getNamespaceUri(this._prefixes[var1]) : null;
   }

   public CharSequence getLocalName(int var1) {
      return var1 >= 0 && var1 < this._length ? this._localNames[var1] : null;
   }

   public CharSequence getPrefix(int var1) {
      return var1 >= 0 && var1 < this._length ? this._prefixes[var1] : null;
   }

   public CharSequence getQName(int var1) {
      return var1 >= 0 && var1 < this._length ? this._qNames[var1] : null;
   }

   public String getType(int var1) {
      return var1 >= 0 && var1 < this._length ? "CDATA" : null;
   }

   public CharSequence getValue(int var1) {
      return var1 >= 0 && var1 < this._length ? this._values[var1] : null;
   }

   public int getIndex(String var1, String var2) {
      for(int var3 = 0; var3 < this._length; ++var3) {
         if (this._localNames[var3].equals(var2) && this._namespaces.getNamespaceUri(this._prefixes[var3]).equals(var1)) {
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

   public void addAttribute(CharSequenceImpl var1, CharSequenceImpl var2, CharSequenceImpl var3, CharSequenceImpl var4) {
      if (this._length >= this._localNames.length) {
         this.increaseCapacity();
      }

      this._localNames[this._length] = var1;
      this._prefixes[this._length] = var2;
      this._qNames[this._length] = var3;
      this._values[this._length++] = var4;
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

   private void increaseCapacity() {
      MemoryArea.getMemoryArea(this).executeInArea(new Runnable() {
         public void run() {
            int var1 = AttributesImpl.access$000(AttributesImpl.this) * 2;
            CharSequenceImpl[] var2 = new CharSequenceImpl[var1];
            System.arraycopy(AttributesImpl.access$100(AttributesImpl.this), 0, var2, 0, AttributesImpl.access$000(AttributesImpl.this));
            AttributesImpl.access$102(AttributesImpl.this, var2);
            var2 = new CharSequenceImpl[var1];
            System.arraycopy(AttributesImpl.access$200(AttributesImpl.this), 0, var2, 0, AttributesImpl.access$000(AttributesImpl.this));
            AttributesImpl.access$202(AttributesImpl.this, var2);
            var2 = new CharSequenceImpl[var1];
            System.arraycopy(AttributesImpl.access$300(AttributesImpl.this), 0, var2, 0, AttributesImpl.access$000(AttributesImpl.this));
            AttributesImpl.access$302(AttributesImpl.this, var2);
            var2 = new CharSequenceImpl[var1];
            System.arraycopy(AttributesImpl.access$400(AttributesImpl.this), 0, var2, 0, AttributesImpl.access$000(AttributesImpl.this));
            AttributesImpl.access$402(AttributesImpl.this, var2);
         }
      });
   }

   static int access$000(AttributesImpl var0) {
      return var0._length;
   }

   static CharSequenceImpl[] access$100(AttributesImpl var0) {
      return var0._localNames;
   }

   static CharSequenceImpl[] access$102(AttributesImpl var0, CharSequenceImpl[] var1) {
      return var0._localNames = var1;
   }

   static CharSequenceImpl[] access$200(AttributesImpl var0) {
      return var0._prefixes;
   }

   static CharSequenceImpl[] access$202(AttributesImpl var0, CharSequenceImpl[] var1) {
      return var0._prefixes = var1;
   }

   static CharSequenceImpl[] access$300(AttributesImpl var0) {
      return var0._qNames;
   }

   static CharSequenceImpl[] access$302(AttributesImpl var0, CharSequenceImpl[] var1) {
      return var0._qNames = var1;
   }

   static CharSequenceImpl[] access$400(AttributesImpl var0) {
      return var0._values;
   }

   static CharSequenceImpl[] access$402(AttributesImpl var0, CharSequenceImpl[] var1) {
      return var0._values = var1;
   }
}
