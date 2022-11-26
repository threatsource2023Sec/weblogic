package org.python.netty.handler.codec;

public final class UnsupportedValueConverter implements ValueConverter {
   private static final UnsupportedValueConverter INSTANCE = new UnsupportedValueConverter();

   private UnsupportedValueConverter() {
   }

   public static UnsupportedValueConverter instance() {
      return INSTANCE;
   }

   public Object convertObject(Object value) {
      throw new UnsupportedOperationException();
   }

   public Object convertBoolean(boolean value) {
      throw new UnsupportedOperationException();
   }

   public boolean convertToBoolean(Object value) {
      throw new UnsupportedOperationException();
   }

   public Object convertByte(byte value) {
      throw new UnsupportedOperationException();
   }

   public byte convertToByte(Object value) {
      throw new UnsupportedOperationException();
   }

   public Object convertChar(char value) {
      throw new UnsupportedOperationException();
   }

   public char convertToChar(Object value) {
      throw new UnsupportedOperationException();
   }

   public Object convertShort(short value) {
      throw new UnsupportedOperationException();
   }

   public short convertToShort(Object value) {
      throw new UnsupportedOperationException();
   }

   public Object convertInt(int value) {
      throw new UnsupportedOperationException();
   }

   public int convertToInt(Object value) {
      throw new UnsupportedOperationException();
   }

   public Object convertLong(long value) {
      throw new UnsupportedOperationException();
   }

   public long convertToLong(Object value) {
      throw new UnsupportedOperationException();
   }

   public Object convertTimeMillis(long value) {
      throw new UnsupportedOperationException();
   }

   public long convertToTimeMillis(Object value) {
      throw new UnsupportedOperationException();
   }

   public Object convertFloat(float value) {
      throw new UnsupportedOperationException();
   }

   public float convertToFloat(Object value) {
      throw new UnsupportedOperationException();
   }

   public Object convertDouble(double value) {
      throw new UnsupportedOperationException();
   }

   public double convertToDouble(Object value) {
      throw new UnsupportedOperationException();
   }
}
