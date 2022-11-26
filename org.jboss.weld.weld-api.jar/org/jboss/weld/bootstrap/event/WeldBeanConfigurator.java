package org.jboss.weld.bootstrap.event;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Function;
import javax.enterprise.inject.spi.AnnotatedType;
import javax.enterprise.inject.spi.BeanAttributes;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.enterprise.inject.spi.configurator.BeanConfigurator;
import javax.enterprise.util.TypeLiteral;

public interface WeldBeanConfigurator extends BeanConfigurator {
   WeldBeanConfigurator alternative(boolean var1);

   WeldBeanConfigurator name(String var1);

   WeldBeanConfigurator stereotypes(Set var1);

   WeldBeanConfigurator addStereotypes(Set var1);

   WeldBeanConfigurator addStereotype(Class var1);

   WeldBeanConfigurator qualifiers(Set var1);

   WeldBeanConfigurator qualifiers(Annotation... var1);

   WeldBeanConfigurator addQualifiers(Set var1);

   WeldBeanConfigurator addQualifiers(Annotation... var1);

   WeldBeanConfigurator addQualifier(Annotation var1);

   WeldBeanConfigurator scope(Class var1);

   WeldBeanConfigurator types(Set var1);

   WeldBeanConfigurator types(Type... var1);

   WeldBeanConfigurator addTransitiveTypeClosure(Type var1);

   WeldBeanConfigurator addTypes(Set var1);

   WeldBeanConfigurator addTypes(Type... var1);

   WeldBeanConfigurator addType(TypeLiteral var1);

   WeldBeanConfigurator addType(Type var1);

   WeldBeanConfigurator read(BeanAttributes var1);

   WeldBeanConfigurator read(AnnotatedType var1);

   WeldBeanConfigurator disposeWith(BiConsumer var1);

   WeldBeanConfigurator destroyWith(BiConsumer var1);

   WeldBeanConfigurator produceWith(Function var1);

   WeldBeanConfigurator createWith(Function var1);

   WeldBeanConfigurator id(String var1);

   WeldBeanConfigurator injectionPoints(Set var1);

   WeldBeanConfigurator injectionPoints(InjectionPoint... var1);

   WeldBeanConfigurator addInjectionPoints(Set var1);

   WeldBeanConfigurator addInjectionPoints(InjectionPoint... var1);

   WeldBeanConfigurator addInjectionPoint(InjectionPoint var1);

   WeldBeanConfigurator beanClass(Class var1);

   WeldBeanConfigurator priority(int var1);
}
