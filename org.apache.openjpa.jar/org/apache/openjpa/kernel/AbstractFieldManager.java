package org.apache.openjpa.kernel;

import org.apache.openjpa.enhance.FieldManager;
import org.apache.openjpa.util.InternalException;

abstract class AbstractFieldManager implements FieldManager {
   public boolean fetchBooleanField(int field) {
      throw new InternalException();
   }

   public byte fetchByteField(int field) {
      throw new InternalException();
   }

   public char fetchCharField(int field) {
      throw new InternalException();
   }

   public double fetchDoubleField(int field) {
      throw new InternalException();
   }

   public float fetchFloatField(int field) {
      throw new InternalException();
   }

   public int fetchIntField(int field) {
      throw new InternalException();
   }

   public long fetchLongField(int field) {
      throw new InternalException();
   }

   public Object fetchObjectField(int field) {
      throw new InternalException();
   }

   public short fetchShortField(int field) {
      throw new InternalException();
   }

   public String fetchStringField(int field) {
      throw new InternalException();
   }

   public void storeBooleanField(int field, boolean curVal) {
      throw new InternalException();
   }

   public void storeByteField(int field, byte curVal) {
      throw new InternalException();
   }

   public void storeCharField(int field, char curVal) {
      throw new InternalException();
   }

   public void storeDoubleField(int field, double curVal) {
      throw new InternalException();
   }

   public void storeFloatField(int field, float curVal) {
      throw new InternalException();
   }

   public void storeIntField(int field, int curVal) {
      throw new InternalException();
   }

   public void storeLongField(int field, long curVal) {
      throw new InternalException();
   }

   public void storeObjectField(int field, Object curVal) {
      throw new InternalException();
   }

   public void storeShortField(int field, short curVal) {
      throw new InternalException();
   }

   public void storeStringField(int field, String curVal) {
      throw new InternalException();
   }
}
