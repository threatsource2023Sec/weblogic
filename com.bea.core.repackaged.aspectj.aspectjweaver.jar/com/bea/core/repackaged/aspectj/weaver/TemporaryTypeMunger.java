package com.bea.core.repackaged.aspectj.weaver;

import java.util.Map;

public class TemporaryTypeMunger extends ConcreteTypeMunger {
   public TemporaryTypeMunger(ResolvedTypeMunger munger, ResolvedType aspectType) {
      super(munger, aspectType);
   }

   public ConcreteTypeMunger parameterizeWith(Map parameterizationMap, World world) {
      throw new UnsupportedOperationException("Cannot be called on a TemporaryTypeMunger");
   }

   public ConcreteTypeMunger parameterizedFor(ResolvedType targetType) {
      throw new UnsupportedOperationException("Cannot be called on a TemporaryTypeMunger");
   }
}
