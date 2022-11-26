package net.shibboleth.utilities.java.support.xml;

public enum XMLSpace {
   DEFAULT,
   PRESERVE;

   public String toString() {
      return super.toString().toLowerCase();
   }

   public static XMLSpace parseValue(String value) {
      return valueOf(value.toUpperCase());
   }
}
