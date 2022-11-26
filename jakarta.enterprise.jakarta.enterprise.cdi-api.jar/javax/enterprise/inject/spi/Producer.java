package javax.enterprise.inject.spi;

import java.util.Set;
import javax.enterprise.context.spi.CreationalContext;

public interface Producer {
   Object produce(CreationalContext var1);

   void dispose(Object var1);

   Set getInjectionPoints();
}
