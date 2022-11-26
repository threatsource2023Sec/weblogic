package javolution.xml.pull;

import javolution.lang.PersistentReference;

final class Namespaces {
   private static final PersistentReference SIZE = new PersistentReference("javolution.xml.pull.Namespaces#SIZE", new Integer(64));
   private int[] _nspCounts;
   private int _mapCount;
   private CharSequenceImpl[] _namespaces;
   private int _depth;
   private CharSequenceImpl _default;
   private static final CharSequenceImpl XML_PREFIX = new CharSequenceImpl("xml");
   private static final CharSequenceImpl XML_URI = new CharSequenceImpl("http://www.w3.org/XML/1998/namespace");
   private static final CharSequenceImpl XMLNS_PREFIX = new CharSequenceImpl("xmlns");
   private static final CharSequenceImpl XMLNS_URI = new CharSequenceImpl("http://www.w3.org/2000/xmlns/");

   public Namespaces() {
      this._nspCounts = new int[(Integer)SIZE.get()];
      this._namespaces = new CharSequenceImpl[(Integer)SIZE.get()];
      this._default = CharSequenceImpl.EMPTY;
   }

   public CharSequenceImpl getDefault() {
      return this._default;
   }

   public int getNamespaceCount(int var1) {
      return var1 > this._depth ? this._nspCounts[this._depth] : this._nspCounts[var1];
   }

   public CharSequenceImpl getNamespacePrefix(int var1) {
      return this._namespaces[var1 << 1];
   }

   public CharSequenceImpl getNamespaceUri(int var1) {
      return this._namespaces[(var1 << 1) + 1];
   }

   public CharSequenceImpl getNamespaceUri(String var1) {
      if (var1 == null) {
         return this._default;
      } else {
         int var2 = this._nspCounts[this._depth] + this._mapCount;

         CharSequenceImpl var3;
         do {
            if (var2 <= 0) {
               if (XML_PREFIX.equals(var1)) {
                  return XML_URI;
               }

               if (XMLNS_PREFIX.equals(var1)) {
                  return XMLNS_URI;
               }

               return null;
            }

            --var2;
            var3 = this._namespaces[var2 << 1];
         } while(var3 == null || !var3.equals(var1));

         return this._namespaces[(var2 << 1) + 1];
      }
   }

   CharSequenceImpl getNamespaceUri(CharSequenceImpl var1) {
      int var2 = this._nspCounts[this._depth] + this._mapCount;

      CharSequenceImpl var3;
      do {
         if (var2 <= 0) {
            if (XML_PREFIX.equals(var1)) {
               return XML_URI;
            }

            if (XMLNS_PREFIX.equals(var1)) {
               return XMLNS_URI;
            }

            return null;
         }

         --var2;
         var3 = this._namespaces[var2 << 1];
      } while(var3 == null || !var3.equals(var1));

      return this._namespaces[(var2 << 1) + 1];
   }

   public void map(CharSequenceImpl var1, CharSequenceImpl var2) {
      int var3 = this._nspCounts[this._depth] + this._mapCount++ << 1;
      if (var3 + 1 >= this._namespaces.length) {
         this.resize();
      }

      this._namespaces[var3] = var1;
      this._namespaces[var3 + 1] = var2;
      if (var1 == null) {
         this._default = var2;
      }

   }

   public void flush() {
      if (this._mapCount != 0) {
         this.push();
         this.pop();
      }

   }

   public void push() {
      if (++this._depth >= this._nspCounts.length) {
         this.resize();
      }

      this._nspCounts[this._depth] = this._nspCounts[this._depth - 1] + this._mapCount;
      this._mapCount = 0;
   }

   public void pop() {
      this._mapCount = 0;
      int var1 = this._nspCounts[this._depth];
      int var2 = this._nspCounts[--this._depth];
      int var3 = var1;

      while(true) {
         while(true) {
            do {
               if (var3 <= var2) {
                  return;
               }

               --var3;
            } while(this._namespaces[var3 << 1] != null);

            this._default = CharSequenceImpl.EMPTY;
            int var4 = var3;

            while(var4 > 0) {
               --var4;
               if (this._namespaces[var4 << 1] == null) {
                  this._default = this._namespaces[(var4 << 1) + 1];
                  break;
               }
            }
         }
      }
   }

   public void reset() {
      this._depth = 0;
      this._nspCounts[0] = 0;
      this._default = CharSequenceImpl.EMPTY;
   }

   private void resize() {
      int var1 = this._nspCounts.length;
      int[] var2 = new int[var1 * 2];
      System.arraycopy(this._nspCounts, 0, var2, 0, var1);
      this._nspCounts = var2;
      CharSequenceImpl[] var3 = new CharSequenceImpl[var1 * 2];
      System.arraycopy(this._namespaces, 0, var3, 0, var1);
      this._namespaces = var3;
      SIZE.setMinimum(new Integer(this._namespaces.length));
   }
}
