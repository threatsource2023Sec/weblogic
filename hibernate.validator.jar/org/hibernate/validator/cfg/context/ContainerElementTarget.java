package org.hibernate.validator.cfg.context;

import org.hibernate.validator.Incubating;

@Incubating
public interface ContainerElementTarget {
   ContainerElementConstraintMappingContext containerElementType();

   ContainerElementConstraintMappingContext containerElementType(int var1, int... var2);
}
