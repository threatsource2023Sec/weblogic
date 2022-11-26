package javax.enterprise.inject.spi;

import java.lang.reflect.Type;
import java.util.Set;

public interface Decorator extends Bean {
   Type getDelegateType();

   Set getDelegateQualifiers();

   Set getDecoratedTypes();
}
