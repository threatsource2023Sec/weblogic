package com.bea.core.repackaged.aspectj.weaver.patterns;

import com.bea.core.repackaged.aspectj.weaver.IHasPosition;

public class ParserException extends RuntimeException {
   private IHasPosition token;

   public ParserException(String message, IHasPosition token) {
      super(message);
      this.token = token;
   }

   public IHasPosition getLocation() {
      return this.token;
   }
}
