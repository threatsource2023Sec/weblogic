package javax.enterprise.inject.spi;

import java.lang.reflect.Type;
import java.util.Set;

public interface EventMetadata {
   Set getQualifiers();

   InjectionPoint getInjectionPoint();

   Type getType();
}
