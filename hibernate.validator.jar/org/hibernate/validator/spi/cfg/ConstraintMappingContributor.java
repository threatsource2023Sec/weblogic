package org.hibernate.validator.spi.cfg;

import org.hibernate.validator.cfg.ConstraintMapping;

public interface ConstraintMappingContributor {
   void createConstraintMappings(ConstraintMappingBuilder var1);

   public interface ConstraintMappingBuilder {
      ConstraintMapping addConstraintMapping();
   }
}
