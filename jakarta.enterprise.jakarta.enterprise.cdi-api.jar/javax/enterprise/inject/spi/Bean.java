package javax.enterprise.inject.spi;

import java.util.Set;
import javax.enterprise.context.spi.Contextual;

public interface Bean extends Contextual, BeanAttributes {
   Class getBeanClass();

   Set getInjectionPoints();

   boolean isNullable();
}
