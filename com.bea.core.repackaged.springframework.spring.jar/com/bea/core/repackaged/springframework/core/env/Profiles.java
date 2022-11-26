package com.bea.core.repackaged.springframework.core.env;

import java.util.function.Predicate;

@FunctionalInterface
public interface Profiles {
   boolean matches(Predicate var1);

   static Profiles of(String... profiles) {
      return ProfilesParser.parse(profiles);
   }
}
