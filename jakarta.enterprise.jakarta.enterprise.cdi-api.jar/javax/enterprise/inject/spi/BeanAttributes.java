package javax.enterprise.inject.spi;

import java.util.Set;

public interface BeanAttributes {
   Set getTypes();

   Set getQualifiers();

   Class getScope();

   String getName();

   Set getStereotypes();

   boolean isAlternative();
}
