package org.jboss.weld.injection.producer;

import java.lang.reflect.Member;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.util.Iterator;
import java.util.Set;
import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.event.Observes;
import javax.enterprise.event.ObservesAsync;
import javax.enterprise.inject.CreationException;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.spi.AnnotatedMember;
import org.jboss.weld.annotated.enhanced.EnhancedAnnotatedMethod;
import org.jboss.weld.bean.DisposalMethod;
import org.jboss.weld.bean.SessionBean;
import org.jboss.weld.exceptions.DefinitionException;
import org.jboss.weld.injection.InjectionPointFactory;
import org.jboss.weld.injection.MethodInjectionPoint;
import org.jboss.weld.logging.BeanLogger;
import org.jboss.weld.security.GetMethodAction;
import org.jboss.weld.util.reflection.Formats;
import org.jboss.weld.util.reflection.Reflections;

public abstract class ProducerMethodProducer extends AbstractMemberProducer {
   private static final String PRODUCER_ANNOTATION = "@Produces";
   private final MethodInjectionPoint method;

   public ProducerMethodProducer(EnhancedAnnotatedMethod enhancedAnnotatedMethod, DisposalMethod disposalMethod) {
      super(enhancedAnnotatedMethod, disposalMethod);
      this.method = InjectionPointFactory.instance().createMethodInjectionPoint(MethodInjectionPoint.MethodInjectionPointType.PRODUCER, enhancedAnnotatedMethod, this.getBean(), enhancedAnnotatedMethod.getDeclaringType().getJavaClass(), (Set)null, this.getBeanManager());
      this.checkProducerMethod(enhancedAnnotatedMethod);
      this.checkDelegateInjectionPoints();
   }

   protected void checkProducerMethod(EnhancedAnnotatedMethod method) {
      if (method.getEnhancedParameters(Observes.class).size() > 0) {
         throw BeanLogger.LOG.inconsistentAnnotationsOnMethod("@Produces", "@Observes", this.method, Formats.formatAsStackTraceElement((Member)method.getJavaMember()));
      } else if (method.getEnhancedParameters(ObservesAsync.class).size() > 0) {
         throw BeanLogger.LOG.inconsistentAnnotationsOnMethod("@Produces", "@ObservesAsync", this.method, Formats.formatAsStackTraceElement((Member)method.getJavaMember()));
      } else if (method.getEnhancedParameters(Disposes.class).size() > 0) {
         throw BeanLogger.LOG.inconsistentAnnotationsOnMethod("@Produces", "@Disposes", this.method, Formats.formatAsStackTraceElement((Member)method.getJavaMember()));
      } else {
         if (this.getDeclaringBean() instanceof SessionBean && !Modifier.isStatic(method.slim().getJavaMember().getModifiers())) {
            boolean methodDeclaredOnTypes = false;
            Iterator var3 = this.getDeclaringBean().getTypes().iterator();

            while(var3.hasNext()) {
               Type type = (Type)var3.next();
               Class clazz = Reflections.getRawType(type);

               try {
                  AccessController.doPrivileged(new GetMethodAction(clazz, method.getName(), method.getParameterTypesAsArray()));
                  methodDeclaredOnTypes = true;
                  break;
               } catch (PrivilegedActionException var7) {
               }
            }

            if (!methodDeclaredOnTypes) {
               throw BeanLogger.LOG.methodNotBusinessMethod("Producer", this, this.getDeclaringBean(), Formats.formatAsStackTraceElement((Member)method.getJavaMember()));
            }
         }

      }
   }

   public Set getInjectionPoints() {
      return this.method.getInjectionPoints();
   }

   protected Object produce(Object receiver, CreationalContext ctx) {
      return this.method.invoke(receiver, (Object)null, this.getBeanManager(), ctx, CreationException.class);
   }

   public AnnotatedMember getAnnotated() {
      return this.method.getAnnotated();
   }

   protected DefinitionException producerWithInvalidTypeVariable(AnnotatedMember member) {
      return BeanLogger.LOG.producerMethodReturnTypeInvalidTypeVariable(member, Formats.formatAsStackTraceElement(member.getJavaMember()));
   }

   protected DefinitionException producerWithInvalidWildcard(AnnotatedMember member) {
      return BeanLogger.LOG.producerMethodCannotHaveAWildcardReturnType(member, Formats.formatAsStackTraceElement(member.getJavaMember()));
   }

   protected DefinitionException producerWithParameterizedTypeWithTypeVariableBeanTypeMustBeDependent(AnnotatedMember member) {
      return BeanLogger.LOG.producerMethodWithTypeVariableReturnTypeMustBeDependent(member, Formats.formatAsStackTraceElement(member.getJavaMember()));
   }
}
