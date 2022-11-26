package org.apache.openjpa.kernel;

import org.apache.openjpa.enhance.FieldManager;

class TransferFieldManager implements FieldManager {
   protected double dblval = 0.0;
   protected long longval = 0L;
   protected Object objval = null;
   protected int field = -1;

   public boolean fetchBooleanField(int field) {
      return this.longval == 1L;
   }

   public byte fetchByteField(int field) {
      return (byte)((int)this.longval);
   }

   public char fetchCharField(int field) {
      return (char)((int)this.longval);
   }

   public double fetchDoubleField(int field) {
      return this.dblval;
   }

   public float fetchFloatField(int field) {
      return (float)this.dblval;
   }

   public int fetchIntField(int field) {
      return (int)this.longval;
   }

   public long fetchLongField(int field) {
      return this.longval;
   }

   public Object fetchObjectField(int field) {
      Object val = this.objval;
      this.objval = null;
      return val;
   }

   public short fetchShortField(int field) {
      return (short)((int)this.longval);
   }

   public String fetchStringField(int field) {
      return (String)this.objval;
   }

   public void storeBooleanField(int field, boolean curVal) {
      this.field = field;
      this.longval = curVal ? 1L : 0L;
   }

   public void storeByteField(int field, byte curVal) {
      this.field = field;
      this.longval = (long)curVal;
   }

   public void storeCharField(int field, char curVal) {
      this.field = field;
      this.longval = (long)curVal;
   }

   public void storeDoubleField(int field, double curVal) {
      this.field = field;
      this.dblval = curVal;
   }

   public void storeFloatField(int field, float curVal) {
      this.field = field;
      this.dblval = (double)curVal;
   }

   public void storeIntField(int field, int curVal) {
      this.field = field;
      this.longval = (long)curVal;
   }

   public void storeLongField(int field, long curVal) {
      this.field = field;
      this.longval = curVal;
   }

   public void storeObjectField(int field, Object curVal) {
      this.field = field;
      this.objval = curVal;
   }

   public void storeShortField(int field, short curVal) {
      this.field = field;
      this.longval = (long)curVal;
   }

   public void storeStringField(int field, String curVal) {
      this.field = field;
      this.objval = curVal;
   }

   public void clear() {
      this.dblval = 0.0;
      this.longval = 0L;
      this.objval = null;
      this.field = -1;
   }
}
