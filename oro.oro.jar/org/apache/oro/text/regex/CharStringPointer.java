package org.apache.oro.text.regex;

final class CharStringPointer {
   static final char _END_OF_STRING = '\uffff';
   int _offset;
   char[] _array;

   CharStringPointer(char[] var1, int var2) {
      this._array = var1;
      this._offset = var2;
   }

   CharStringPointer(char[] var1) {
      this(var1, 0);
   }

   char _getValue() {
      return this._getValue(this._offset);
   }

   char _getValue(int var1) {
      return var1 < this._array.length && var1 >= 0 ? this._array[var1] : '\uffff';
   }

   char _getValueRelative(int var1) {
      return this._getValue(this._offset + var1);
   }

   int _getLength() {
      return this._array.length;
   }

   int _getOffset() {
      return this._offset;
   }

   void _setOffset(int var1) {
      this._offset = var1;
   }

   boolean _isAtEnd() {
      return this._offset >= this._array.length;
   }

   char _increment(int var1) {
      this._offset += var1;
      if (this._isAtEnd()) {
         this._offset = this._array.length;
         return '\uffff';
      } else {
         return this._array[this._offset];
      }
   }

   char _increment() {
      return this._increment(1);
   }

   char _decrement(int var1) {
      this._offset -= var1;
      if (this._offset < 0) {
         this._offset = 0;
      }

      return this._array[this._offset];
   }

   char _decrement() {
      return this._decrement(1);
   }

   char _postIncrement() {
      char var1 = this._getValue();
      this._increment();
      return var1;
   }

   char _postDecrement() {
      char var1 = this._getValue();
      this._decrement();
      return var1;
   }

   String _toString(int var1) {
      return new String(this._array, var1, this._array.length - var1);
   }

   public String toString() {
      return this._toString(0);
   }
}
