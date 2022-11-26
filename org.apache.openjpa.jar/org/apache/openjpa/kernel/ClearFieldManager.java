package org.apache.openjpa.kernel;

class ClearFieldManager extends AbstractFieldManager {
   private static final ClearFieldManager _single = new ClearFieldManager();

   public static ClearFieldManager getInstance() {
      return _single;
   }

   protected ClearFieldManager() {
   }

   public boolean fetchBooleanField(int field) {
      return false;
   }

   public byte fetchByteField(int field) {
      return 0;
   }

   public char fetchCharField(int field) {
      return '\u0000';
   }

   public double fetchDoubleField(int field) {
      return 0.0;
   }

   public float fetchFloatField(int field) {
      return 0.0F;
   }

   public int fetchIntField(int field) {
      return 0;
   }

   public long fetchLongField(int field) {
      return 0L;
   }

   public Object fetchObjectField(int field) {
      return null;
   }

   public short fetchShortField(int field) {
      return 0;
   }

   public String fetchStringField(int field) {
      return null;
   }
}
