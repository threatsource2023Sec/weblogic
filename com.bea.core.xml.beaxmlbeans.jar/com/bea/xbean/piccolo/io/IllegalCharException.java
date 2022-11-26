package com.bea.xbean.piccolo.io;

import java.io.CharConversionException;

public class IllegalCharException extends CharConversionException {
   protected int line;
   protected int column;

   public IllegalCharException(String msg) {
      super(msg);
   }
}
