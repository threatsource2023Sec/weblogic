package javax.enterprise.inject.spi;

import java.lang.reflect.Member;
import java.lang.reflect.Type;
import java.util.Set;

public interface InjectionPoint {
   Type getType();

   Set getQualifiers();

   Bean getBean();

   Member getMember();

   Annotated getAnnotated();

   boolean isDelegate();

   boolean isTransient();
}
