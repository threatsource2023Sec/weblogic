package org.hibernate.validator.internal.cfg.context;

import org.hibernate.validator.cfg.context.GroupConversionTargetContext;

class GroupConversionTargetContextImpl implements GroupConversionTargetContext {
   private final Object cascadableContext;
   private final Class from;
   private final CascadableConstraintMappingContextImplBase target;

   GroupConversionTargetContextImpl(Class from, Object cascadableContext, CascadableConstraintMappingContextImplBase target) {
      this.from = from;
      this.cascadableContext = cascadableContext;
      this.target = target;
   }

   public Object to(Class to) {
      this.target.addGroupConversion(this.from, to);
      return this.cascadableContext;
   }
}
