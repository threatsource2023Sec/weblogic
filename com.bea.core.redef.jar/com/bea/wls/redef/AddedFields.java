package com.bea.wls.redef;

public class AddedFields {
   private final int _start;
   private volatile Object[] _fields = new Object[3];

   public AddedFields(int startFieldIndex) {
      this._start = startFieldIndex;
   }

   public boolean fetchBooleanField(int fieldIndex) {
      Object o = this.fetchObjectField(fieldIndex);
      return o == null ? false : (Boolean)o;
   }

   public char fetchCharField(int fieldIndex) {
      Object o = this.fetchObjectField(fieldIndex);
      return o == null ? '\u0000' : (Character)o;
   }

   public byte fetchByteField(int fieldIndex) {
      Object o = this.fetchObjectField(fieldIndex);
      return o == null ? 0 : (Byte)o;
   }

   public short fetchShortField(int fieldIndex) {
      Object o = this.fetchObjectField(fieldIndex);
      return o == null ? 0 : (Short)o;
   }

   public int fetchIntField(int fieldIndex) {
      Object o = this.fetchObjectField(fieldIndex);
      return o == null ? 0 : (Integer)o;
   }

   public long fetchLongField(int fieldIndex) {
      Object o = this.fetchObjectField(fieldIndex);
      return o == null ? 0L : (Long)o;
   }

   public float fetchFloatField(int fieldIndex) {
      Object o = this.fetchObjectField(fieldIndex);
      return o == null ? 0.0F : (Float)o;
   }

   public double fetchDoubleField(int fieldIndex) {
      Object o = this.fetchObjectField(fieldIndex);
      return o == null ? 0.0 : (Double)o;
   }

   public Object fetchObjectField(int fieldIndex) {
      fieldIndex -= this._start;
      return this._fields.length <= fieldIndex ? null : this._fields[fieldIndex];
   }

   public void storeBooleanField(boolean value, int fieldIndex) {
      this.storeObjectField(value, fieldIndex);
   }

   public void storeCharField(char value, int fieldIndex) {
      this.storeObjectField(value, fieldIndex);
   }

   public void storeByteField(byte value, int fieldIndex) {
      this.storeObjectField(value, fieldIndex);
   }

   public void storeShortField(short value, int fieldIndex) {
      this.storeObjectField(value, fieldIndex);
   }

   public void storeIntField(int value, int fieldIndex) {
      this.storeObjectField(value, fieldIndex);
   }

   public void storeLongField(long value, int fieldIndex) {
      this.storeObjectField(value, fieldIndex);
   }

   public void storeFloatField(float value, int fieldIndex) {
      this.storeObjectField(value, fieldIndex);
   }

   public void storeDoubleField(double value, int fieldIndex) {
      this.storeObjectField(value, fieldIndex);
   }

   public void storeObjectField(Object value, int fieldIndex) {
      fieldIndex -= this._start;
      this.ensureCapacity(fieldIndex);
      this._fields[fieldIndex] = value;
   }

   private void ensureCapacity(int idx) {
      if (this._fields.length <= idx) {
         synchronized(this) {
            if (this._fields.length <= idx) {
               Object[] copy = new Object[Math.max(idx + 1, this._fields.length * 2)];
               System.arraycopy(this._fields, 0, copy, 0, this._fields.length);
               this._fields = copy;
            }
         }
      }
   }
}
