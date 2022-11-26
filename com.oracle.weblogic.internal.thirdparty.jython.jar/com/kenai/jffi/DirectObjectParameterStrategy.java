package com.kenai.jffi;

public abstract class DirectObjectParameterStrategy extends ObjectParameterStrategy {
   public DirectObjectParameterStrategy(boolean isDirect, ObjectParameterType parameterType) {
      super(isDirect, parameterType);
   }

   public abstract long getAddress(Object var1);

   public final Object object(Object parameter) {
      throw new RuntimeException("direct object " + (parameter != null ? parameter.getClass() : "null") + " has no array");
   }

   public final int offset(Object parameter) {
      throw new RuntimeException("direct object " + (parameter != null ? parameter.getClass() : "null") + "has no offset");
   }

   public final int length(Object parameter) {
      throw new RuntimeException("direct object " + (parameter != null ? parameter.getClass() : "null") + "has no length");
   }
}
