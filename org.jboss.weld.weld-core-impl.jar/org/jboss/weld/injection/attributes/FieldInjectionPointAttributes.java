package org.jboss.weld.injection.attributes;

import java.lang.reflect.Field;
import javax.enterprise.inject.spi.AnnotatedField;

public interface FieldInjectionPointAttributes extends WeldInjectionPointAttributes {
   AnnotatedField getAnnotated();

   Field getMember();
}
