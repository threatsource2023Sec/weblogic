package com.googlecode.cqengine.persistence.support.serialization;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface PersistenceConfig {
   PersistenceConfig DEFAULT_CONFIG = new PersistenceConfig() {
      public Class annotationType() {
         return PersistenceConfig.class;
      }

      public Class serializer() {
         return KryoSerializer.class;
      }

      public boolean polymorphic() {
         return false;
      }
   };

   Class serializer() default KryoSerializer.class;

   boolean polymorphic() default false;
}
