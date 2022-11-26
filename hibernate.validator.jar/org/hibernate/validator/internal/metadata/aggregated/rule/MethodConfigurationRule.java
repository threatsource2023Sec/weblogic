package org.hibernate.validator.internal.metadata.aggregated.rule;

import java.lang.invoke.MethodHandles;
import org.hibernate.validator.internal.metadata.raw.ConstrainedExecutable;
import org.hibernate.validator.internal.util.logging.Log;
import org.hibernate.validator.internal.util.logging.LoggerFactory;

public abstract class MethodConfigurationRule {
   protected static final Log LOG = LoggerFactory.make(MethodHandles.lookup());

   public abstract void apply(ConstrainedExecutable var1, ConstrainedExecutable var2);

   protected boolean isStrictSubType(Class clazz, Class otherClazz) {
      return clazz.isAssignableFrom(otherClazz) && !clazz.equals(otherClazz);
   }

   protected boolean isDefinedOnSubType(ConstrainedExecutable executable, ConstrainedExecutable otherExecutable) {
      Class clazz = executable.getExecutable().getDeclaringClass();
      Class otherClazz = otherExecutable.getExecutable().getDeclaringClass();
      return this.isStrictSubType(clazz, otherClazz);
   }

   protected boolean isDefinedOnParallelType(ConstrainedExecutable executable, ConstrainedExecutable otherExecutable) {
      Class clazz = executable.getExecutable().getDeclaringClass();
      Class otherClazz = otherExecutable.getExecutable().getDeclaringClass();
      return !clazz.isAssignableFrom(otherClazz) && !otherClazz.isAssignableFrom(clazz);
   }
}
