package com.bea.core.repackaged.springframework.cglib.core;

public class SpringNamingPolicy extends DefaultNamingPolicy {
   public static final SpringNamingPolicy INSTANCE = new SpringNamingPolicy();

   protected String getTag() {
      return "BySpringCGLIB";
   }
}
