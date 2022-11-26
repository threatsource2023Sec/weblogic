package javax.enterprise.inject.spi.configurator;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Function;
import javax.enterprise.inject.spi.AnnotatedType;
import javax.enterprise.inject.spi.BeanAttributes;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.enterprise.util.TypeLiteral;

public interface BeanConfigurator {
   BeanConfigurator beanClass(Class var1);

   BeanConfigurator addInjectionPoint(InjectionPoint var1);

   BeanConfigurator addInjectionPoints(InjectionPoint... var1);

   BeanConfigurator addInjectionPoints(Set var1);

   BeanConfigurator injectionPoints(InjectionPoint... var1);

   BeanConfigurator injectionPoints(Set var1);

   BeanConfigurator id(String var1);

   BeanConfigurator createWith(Function var1);

   BeanConfigurator produceWith(Function var1);

   BeanConfigurator destroyWith(BiConsumer var1);

   BeanConfigurator disposeWith(BiConsumer var1);

   BeanConfigurator read(AnnotatedType var1);

   BeanConfigurator read(BeanAttributes var1);

   BeanConfigurator addType(Type var1);

   BeanConfigurator addType(TypeLiteral var1);

   BeanConfigurator addTypes(Type... var1);

   BeanConfigurator addTypes(Set var1);

   BeanConfigurator addTransitiveTypeClosure(Type var1);

   BeanConfigurator types(Type... var1);

   BeanConfigurator types(Set var1);

   BeanConfigurator scope(Class var1);

   BeanConfigurator addQualifier(Annotation var1);

   BeanConfigurator addQualifiers(Annotation... var1);

   BeanConfigurator addQualifiers(Set var1);

   BeanConfigurator qualifiers(Annotation... var1);

   BeanConfigurator qualifiers(Set var1);

   BeanConfigurator addStereotype(Class var1);

   BeanConfigurator addStereotypes(Set var1);

   BeanConfigurator stereotypes(Set var1);

   BeanConfigurator name(String var1);

   BeanConfigurator alternative(boolean var1);
}
