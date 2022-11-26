package com.bea.xbean.regex;

public class ParseException extends RuntimeException {
   int location;

   public ParseException(String mes, int location) {
      super(mes);
      this.location = location;
   }

   public int getLocation() {
      return this.location;
   }
}
