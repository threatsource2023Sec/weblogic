package javax.enterprise.inject.spi;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Set;
import javax.el.ELResolver;
import javax.el.ExpressionFactory;
import javax.enterprise.context.spi.Context;
import javax.enterprise.context.spi.Contextual;
import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.event.Event;
import javax.enterprise.inject.Instance;

public interface BeanManager {
   Object getReference(Bean var1, Type var2, CreationalContext var3);

   Object getInjectableReference(InjectionPoint var1, CreationalContext var2);

   CreationalContext createCreationalContext(Contextual var1);

   Set getBeans(Type var1, Annotation... var2);

   Set getBeans(String var1);

   Bean getPassivationCapableBean(String var1);

   Bean resolve(Set var1);

   void validate(InjectionPoint var1);

   void fireEvent(Object var1, Annotation... var2);

   Set resolveObserverMethods(Object var1, Annotation... var2);

   List resolveDecorators(Set var1, Annotation... var2);

   List resolveInterceptors(InterceptionType var1, Annotation... var2);

   boolean isScope(Class var1);

   boolean isNormalScope(Class var1);

   boolean isPassivatingScope(Class var1);

   boolean isQualifier(Class var1);

   boolean isInterceptorBinding(Class var1);

   boolean isStereotype(Class var1);

   Set getInterceptorBindingDefinition(Class var1);

   Set getStereotypeDefinition(Class var1);

   boolean areQualifiersEquivalent(Annotation var1, Annotation var2);

   boolean areInterceptorBindingsEquivalent(Annotation var1, Annotation var2);

   int getQualifierHashCode(Annotation var1);

   int getInterceptorBindingHashCode(Annotation var1);

   Context getContext(Class var1);

   ELResolver getELResolver();

   ExpressionFactory wrapExpressionFactory(ExpressionFactory var1);

   AnnotatedType createAnnotatedType(Class var1);

   InjectionTarget createInjectionTarget(AnnotatedType var1);

   InjectionTargetFactory getInjectionTargetFactory(AnnotatedType var1);

   ProducerFactory getProducerFactory(AnnotatedField var1, Bean var2);

   ProducerFactory getProducerFactory(AnnotatedMethod var1, Bean var2);

   BeanAttributes createBeanAttributes(AnnotatedType var1);

   BeanAttributes createBeanAttributes(AnnotatedMember var1);

   Bean createBean(BeanAttributes var1, Class var2, InjectionTargetFactory var3);

   Bean createBean(BeanAttributes var1, Class var2, ProducerFactory var3);

   InjectionPoint createInjectionPoint(AnnotatedField var1);

   InjectionPoint createInjectionPoint(AnnotatedParameter var1);

   Extension getExtension(Class var1);

   InterceptionFactory createInterceptionFactory(CreationalContext var1, Class var2);

   Event getEvent();

   Instance createInstance();
}
