package javax.enterprise.inject.spi.configurator;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Set;

public interface InjectionPointConfigurator {
   InjectionPointConfigurator type(Type var1);

   InjectionPointConfigurator addQualifier(Annotation var1);

   InjectionPointConfigurator addQualifiers(Annotation... var1);

   InjectionPointConfigurator addQualifiers(Set var1);

   InjectionPointConfigurator qualifiers(Annotation... var1);

   InjectionPointConfigurator qualifiers(Set var1);

   InjectionPointConfigurator delegate(boolean var1);

   InjectionPointConfigurator transientField(boolean var1);
}
