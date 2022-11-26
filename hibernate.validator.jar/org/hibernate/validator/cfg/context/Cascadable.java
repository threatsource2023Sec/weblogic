package org.hibernate.validator.cfg.context;

public interface Cascadable {
   Cascadable valid();

   GroupConversionTargetContext convertGroup(Class var1);
}
