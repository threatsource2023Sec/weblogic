package org.jboss.weld.serialization;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Iterator;
import javax.enterprise.inject.spi.AnnotatedCallable;
import javax.enterprise.inject.spi.AnnotatedConstructor;
import javax.enterprise.inject.spi.AnnotatedField;
import javax.enterprise.inject.spi.AnnotatedMethod;
import javax.enterprise.inject.spi.AnnotatedParameter;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.InjectionPoint;
import org.jboss.weld.injection.EmptyInjectionPoint;
import org.jboss.weld.logging.BeanLogger;
import org.jboss.weld.logging.SerializationLogger;
import org.jboss.weld.util.Preconditions;
import org.jboss.weld.util.reflection.Reflections;

public class InjectionPointHolder extends AbstractSerializableHolder {
   private static final long serialVersionUID = -6128821485743815308L;
   private final InjectionPointIdentifier identifier;

   public InjectionPointHolder(String contextId, InjectionPoint ip) {
      super(ip);
      Preconditions.checkNotNull(ip);
      if (ip.getBean() == null) {
         if (ip instanceof Serializable) {
            this.identifier = new SerializableInjectionPointIdentifier(ip);
         } else {
            this.identifier = new TransientInjectionPointIdentifier(ip);
         }
      } else if (ip.getAnnotated() instanceof AnnotatedField) {
         AnnotatedField field = (AnnotatedField)Reflections.cast(ip.getAnnotated());
         this.identifier = new FieldInjectionPointIdentifier(contextId, ip.getBean(), field);
      } else {
         if (!(ip.getAnnotated() instanceof AnnotatedParameter)) {
            throw BeanLogger.LOG.invalidAnnotatedOfInjectionPoint(ip.getAnnotated(), ip);
         }

         AnnotatedParameter parameter = (AnnotatedParameter)Reflections.cast(ip.getAnnotated());
         if (parameter.getDeclaringCallable() instanceof AnnotatedConstructor) {
            AnnotatedConstructor constructor = (AnnotatedConstructor)Reflections.cast(parameter.getDeclaringCallable());
            this.identifier = new ConstructorParameterInjectionPointIdentifier(contextId, ip.getBean(), parameter.getPosition(), constructor);
         } else {
            if (!(parameter.getDeclaringCallable() instanceof AnnotatedMethod)) {
               throw BeanLogger.LOG.invalidAnnotatedCallable(parameter.getDeclaringCallable());
            }

            AnnotatedMethod method = (AnnotatedMethod)Reflections.cast(parameter.getDeclaringCallable());
            this.identifier = new MethodParameterInjectionPointIdentifier(contextId, ip.getBean(), parameter.getPosition(), method);
         }
      }

   }

   protected InjectionPoint initialize() {
      InjectionPoint ip = this.identifier.restoreInjectionPoint();
      if (ip == null) {
         SerializationLogger.LOG.debug("Unable to deserialize InjectionPoint metadata. Falling back to EmptyInjectionPoint");
         return EmptyInjectionPoint.INSTANCE;
      } else {
         return ip;
      }
   }

   private static class MethodParameterInjectionPointIdentifier extends AbstractParameterInjectionPointIdentifier {
      private static final long serialVersionUID = -3263543692438746424L;
      private final MethodHolder method;

      public MethodParameterInjectionPointIdentifier(String contextId, Bean bean, int position, AnnotatedMethod constructor) {
         super(contextId, bean, position);
         this.method = MethodHolder.of(constructor);
      }

      protected boolean matches(InjectionPoint ip, AnnotatedCallable annotatedCallable) {
         if (annotatedCallable instanceof AnnotatedMethod) {
            AnnotatedMethod annotatedMethod = (AnnotatedMethod)Reflections.cast(annotatedCallable);
            return ((Method)this.method.get()).equals(annotatedMethod.getJavaMember());
         } else {
            return false;
         }
      }
   }

   private static class ConstructorParameterInjectionPointIdentifier extends AbstractParameterInjectionPointIdentifier {
      private static final long serialVersionUID = 638702977751948835L;
      private final ConstructorHolder constructor;

      public ConstructorParameterInjectionPointIdentifier(String contextId, Bean bean, int position, AnnotatedConstructor constructor) {
         super(contextId, bean, position);
         this.constructor = ConstructorHolder.of(constructor.getJavaMember());
      }

      protected boolean matches(InjectionPoint ip, AnnotatedCallable annotatedCallable) {
         if (annotatedCallable instanceof AnnotatedConstructor) {
            AnnotatedConstructor annotatedConstructor = (AnnotatedConstructor)Reflections.cast(annotatedCallable);
            return ((Constructor)this.constructor.get()).equals(annotatedConstructor.getJavaMember());
         } else {
            return false;
         }
      }
   }

   private abstract static class AbstractParameterInjectionPointIdentifier extends AbstractInjectionPointIdentifier {
      private static final long serialVersionUID = -3618042716814281161L;
      private final int position;

      public AbstractParameterInjectionPointIdentifier(String contextId, Bean bean, int position) {
         super(contextId, bean);
         this.position = position;
      }

      protected boolean matches(InjectionPoint ip) {
         if (!(ip.getAnnotated() instanceof AnnotatedParameter)) {
            return false;
         } else {
            AnnotatedParameter annotatedParameter = (AnnotatedParameter)Reflections.cast(ip.getAnnotated());
            return this.position == annotatedParameter.getPosition() && this.matches(ip, annotatedParameter.getDeclaringCallable());
         }
      }

      protected abstract boolean matches(InjectionPoint var1, AnnotatedCallable var2);
   }

   private static class FieldInjectionPointIdentifier extends AbstractInjectionPointIdentifier {
      private static final long serialVersionUID = 4581216810217284043L;
      private final FieldHolder field;

      public FieldInjectionPointIdentifier(String contextId, Bean bean, AnnotatedField field) {
         super(contextId, bean);
         this.field = new FieldHolder(field.getJavaMember());
      }

      protected boolean matches(InjectionPoint ip) {
         if (ip.getAnnotated() instanceof AnnotatedField) {
            AnnotatedField annotatedField = (AnnotatedField)Reflections.cast(ip.getAnnotated());
            return ((Field)this.field.get()).equals(annotatedField.getJavaMember());
         } else {
            return false;
         }
      }
   }

   private abstract static class AbstractInjectionPointIdentifier implements InjectionPointIdentifier {
      private static final long serialVersionUID = -8167922066673252787L;
      private final BeanHolder bean;

      public AbstractInjectionPointIdentifier(String contextId, Bean bean) {
         this.bean = BeanHolder.of(contextId, bean);
      }

      public InjectionPoint restoreInjectionPoint() {
         InjectionPoint injectionPoint = null;
         Iterator var2 = ((Bean)this.bean.get()).getInjectionPoints().iterator();

         while(var2.hasNext()) {
            InjectionPoint ip = (InjectionPoint)var2.next();
            if (this.matches(ip)) {
               if (injectionPoint != null) {
                  throw BeanLogger.LOG.unableToRestoreInjectionPointMultiple(this.bean.get(), injectionPoint, ip);
               }

               injectionPoint = ip;
            }
         }

         if (injectionPoint == null) {
            throw BeanLogger.LOG.unableToRestoreInjectionPoint(this.bean.get());
         } else {
            return injectionPoint;
         }
      }

      protected abstract boolean matches(InjectionPoint var1);
   }

   private static class SerializableInjectionPointIdentifier implements InjectionPointIdentifier {
      private static final long serialVersionUID = 6952579330771485841L;
      private final InjectionPoint ip;

      public SerializableInjectionPointIdentifier(InjectionPoint ip) {
         this.ip = ip;
      }

      public InjectionPoint restoreInjectionPoint() {
         return this.ip;
      }
   }

   private static class TransientInjectionPointIdentifier implements InjectionPointIdentifier {
      private static final long serialVersionUID = 6952579330771485841L;
      @SuppressFBWarnings({"SE_TRANSIENT_FIELD_NOT_RESTORED"})
      private final transient InjectionPoint ip;

      public TransientInjectionPointIdentifier(InjectionPoint ip) {
         this.ip = ip;
      }

      public InjectionPoint restoreInjectionPoint() {
         return this.ip;
      }
   }

   private interface InjectionPointIdentifier extends Serializable {
      InjectionPoint restoreInjectionPoint();
   }
}
