package com.bea.core.repackaged.aspectj.weaver;

import java.util.Collection;

public interface CustomMungerFactory {
   Collection createCustomShadowMungers(ResolvedType var1);

   Collection createCustomTypeMungers(ResolvedType var1);

   Collection getAllCreatedCustomShadowMungers();

   Collection getAllCreatedCustomTypeMungers();
}
