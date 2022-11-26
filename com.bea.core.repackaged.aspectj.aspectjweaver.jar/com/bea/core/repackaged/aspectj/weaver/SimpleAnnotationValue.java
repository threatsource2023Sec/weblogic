package com.bea.core.repackaged.aspectj.weaver;

public class SimpleAnnotationValue extends AnnotationValue {
   private byte theByte;
   private char theChar;
   private int theInt;
   private String theString;
   private double theDouble;
   private float theFloat;
   private long theLong;
   private short theShort;
   private boolean theBoolean;

   public SimpleAnnotationValue(int kind) {
      super(kind);
   }

   public SimpleAnnotationValue(int kind, Object value) {
      super(kind);
      switch (kind) {
         case 66:
            this.theByte = (Byte)value;
            break;
         case 67:
            this.theChar = (Character)value;
            break;
         case 68:
            this.theDouble = (Double)value;
            break;
         case 70:
            this.theFloat = (Float)value;
            break;
         case 73:
            this.theInt = (Integer)value;
            break;
         case 74:
            this.theLong = (Long)value;
            break;
         case 83:
            this.theShort = (Short)value;
            break;
         case 90:
            this.theBoolean = (Boolean)value;
            break;
         case 115:
            this.theString = (String)value;
            break;
         default:
            throw new BCException("Not implemented for this kind: " + whatKindIsThis(kind));
      }

   }

   public void setValueString(String s) {
      this.theString = s;
   }

   public void setValueByte(byte b) {
      this.theByte = b;
   }

   public void setValueChar(char c) {
      this.theChar = c;
   }

   public void setValueInt(int i) {
      this.theInt = i;
   }

   public String stringify() {
      switch (this.valueKind) {
         case 66:
            return Byte.toString(this.theByte);
         case 67:
            return (new Character(this.theChar)).toString();
         case 68:
            return Double.toString(this.theDouble);
         case 70:
            return Float.toString(this.theFloat);
         case 73:
            return Integer.toString(this.theInt);
         case 74:
            return Long.toString(this.theLong);
         case 83:
            return Short.toString(this.theShort);
         case 90:
            return (new Boolean(this.theBoolean)).toString();
         case 115:
            return this.theString;
         default:
            throw new BCException("Do not understand this kind: " + this.valueKind);
      }
   }

   public String toString() {
      return this.stringify();
   }
}
