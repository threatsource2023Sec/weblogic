package com.bea.core.repackaged.springframework.core.env;

public interface Environment extends PropertyResolver {
   String[] getActiveProfiles();

   String[] getDefaultProfiles();

   /** @deprecated */
   @Deprecated
   boolean acceptsProfiles(String... var1);

   boolean acceptsProfiles(Profiles var1);
}
