package org.jboss.weld.resolution;

import java.lang.annotation.Annotation;
import java.lang.annotation.Repeatable;
import java.lang.reflect.Executable;
import java.lang.reflect.Member;
import java.lang.reflect.Parameter;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import javax.enterprise.event.Event;
import javax.enterprise.inject.Instance;
import javax.enterprise.inject.New;
import javax.enterprise.inject.New.Literal;
import javax.enterprise.inject.spi.AnnotatedParameter;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.Decorator;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.enterprise.inject.spi.InterceptionFactory;
import javax.enterprise.inject.spi.Interceptor;
import javax.inject.Named;
import javax.inject.Provider;
import org.jboss.weld.events.WeldEvent;
import org.jboss.weld.inject.WeldInstance;
import org.jboss.weld.literal.NamedLiteral;
import org.jboss.weld.logging.BeanManagerLogger;
import org.jboss.weld.logging.ResolutionLogger;
import org.jboss.weld.manager.BeanManagerImpl;
import org.jboss.weld.metadata.cache.MetaAnnotationStore;
import org.jboss.weld.util.reflection.Reflections;

public class ResolvableBuilder {
   private static final Class[] FACADE_TYPES = new Class[]{Event.class, Instance.class, WeldEvent.class, WeldInstance.class, Provider.class, InterceptionFactory.class};
   private static final Class[] METADATA_TYPES = new Class[]{Interceptor.class, Decorator.class, Bean.class};
   private static final Set ANY_SINGLETON;
   protected Class rawType;
   protected final Set types;
   protected final Set qualifierInstances;
   protected Bean declaringBean;
   private final MetaAnnotationStore store;
   protected boolean delegate;

   public ResolvableBuilder(MetaAnnotationStore store) {
      this.store = store;
      this.types = new HashSet();
      this.qualifierInstances = new HashSet();
   }

   public ResolvableBuilder(BeanManagerImpl manager) {
      this((MetaAnnotationStore)manager.getServices().get(MetaAnnotationStore.class));
   }

   public ResolvableBuilder(Type type, BeanManagerImpl beanManager) {
      this(beanManager);
      if (type != null) {
         this.rawType = Reflections.getRawType(type);
         if (this.rawType == null || type instanceof TypeVariable) {
            throw ResolutionLogger.LOG.cannotExtractRawType(type);
         }

         this.types.add(type);
      }

   }

   public ResolvableBuilder(InjectionPoint injectionPoint, BeanManagerImpl manager) {
      this(injectionPoint.getType(), manager);
      this.addQualifiers(injectionPoint.getQualifiers(), injectionPoint);
      this.setDeclaringBean(injectionPoint.getBean());
      this.delegate = injectionPoint.isDelegate();
   }

   public ResolvableBuilder setDeclaringBean(Bean declaringBean) {
      this.declaringBean = declaringBean;
      return this;
   }

   public ResolvableBuilder addType(Type type) {
      this.types.add(type);
      return this;
   }

   public ResolvableBuilder addTypes(Set types) {
      this.types.addAll(types);
      return this;
   }

   public boolean isDelegate() {
      return this.delegate;
   }

   public void setDelegate(boolean delegate) {
      this.delegate = delegate;
   }

   public Resolvable create() {
      if (this.qualifierInstances.isEmpty()) {
         this.qualifierInstances.add(QualifierInstance.DEFAULT);
      }

      Iterator var1 = this.types.iterator();

      while(var1.hasNext()) {
         Type type = (Type)var1.next();
         Class rawType = Reflections.getRawType(type);
         Class[] var4 = FACADE_TYPES;
         int var5 = var4.length;

         int var6;
         Class metadataType;
         for(var6 = 0; var6 < var5; ++var6) {
            metadataType = var4[var6];
            if (metadataType.equals(rawType)) {
               return this.createFacade(metadataType);
            }
         }

         var4 = METADATA_TYPES;
         var5 = var4.length;

         for(var6 = 0; var6 < var5; ++var6) {
            metadataType = var4[var6];
            if (metadataType.equals(rawType)) {
               return this.createMetadataProvider(metadataType);
            }
         }
      }

      return new ResolvableImpl(this.rawType, this.types, this.declaringBean, this.qualifierInstances, this.delegate);
   }

   private Resolvable createFacade(Class rawType) {
      Set types = Collections.singleton(rawType);
      return new ResolvableImpl(rawType, types, this.declaringBean, ANY_SINGLETON, this.delegate);
   }

   private Resolvable createMetadataProvider(Class rawType) {
      Set types = Collections.singleton(rawType);
      return new ResolvableImpl(rawType, types, this.declaringBean, this.qualifierInstances, this.delegate);
   }

   public ResolvableBuilder addQualifier(Annotation qualifier) {
      return this.addQualifier(qualifier, (InjectionPoint)null);
   }

   private ResolvableBuilder addQualifier(Annotation qualifier, InjectionPoint injectionPoint) {
      QualifierInstance qualifierInstance = QualifierInstance.of((Annotation)qualifier, this.store);
      Class annotationType = qualifierInstance.getAnnotationClass();
      if (annotationType.equals(New.class)) {
         New newQualifier = (New)New.class.cast(qualifier);
         if (newQualifier.value().equals(New.class) && this.rawType == null) {
            throw new IllegalStateException("Cannot transform @New when there is no known raw type");
         }

         if (newQualifier.value().equals(New.class)) {
            qualifier = Literal.of(this.rawType);
            qualifierInstance = QualifierInstance.of((Annotation)qualifier, this.store);
         }
      } else if (injectionPoint != null && annotationType.equals(Named.class)) {
         Named named = (Named)qualifier;
         if (named.value().equals("")) {
            Member member = injectionPoint.getMember();
            NamedLiteral named;
            if (member instanceof Executable) {
               Executable executable = (Executable)member;
               AnnotatedParameter annotatedParameter = (AnnotatedParameter)injectionPoint.getAnnotated();
               Parameter parameter = executable.getParameters()[annotatedParameter.getPosition()];
               named = new NamedLiteral(parameter.getName());
            } else {
               named = new NamedLiteral(injectionPoint.getMember().getName());
            }

            qualifier = named;
            qualifierInstance = QualifierInstance.of((Annotation)named, this.store);
         }
      }

      this.checkQualifier((Annotation)qualifier, qualifierInstance, annotationType);
      this.qualifierInstances.add(qualifierInstance);
      return this;
   }

   public ResolvableBuilder addQualifierUnchecked(QualifierInstance qualifier) {
      this.qualifierInstances.add(qualifier);
      return this;
   }

   public ResolvableBuilder addQualifiers(Annotation[] qualifiers) {
      Annotation[] var2 = qualifiers;
      int var3 = qualifiers.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         Annotation qualifier = var2[var4];
         this.addQualifier(qualifier);
      }

      return this;
   }

   public ResolvableBuilder addQualifiers(Collection qualifiers) {
      return this.addQualifiers(qualifiers, (InjectionPoint)null);
   }

   private ResolvableBuilder addQualifiers(Collection qualifiers, InjectionPoint injectionPoint) {
      Iterator var3 = qualifiers.iterator();

      while(var3.hasNext()) {
         Annotation qualifier = (Annotation)var3.next();
         this.addQualifier(qualifier, injectionPoint);
      }

      return this;
   }

   protected void checkQualifier(Annotation qualifier, QualifierInstance qualifierInstance, Class annotationType) {
      if (!this.store.getBindingTypeModel(annotationType).isValid()) {
         throw BeanManagerLogger.LOG.invalidQualifier(qualifierInstance);
      } else {
         if (!annotationType.isAnnotationPresent(Repeatable.class)) {
            Iterator var4 = this.qualifierInstances.iterator();

            while(var4.hasNext()) {
               QualifierInstance checkedQualifier = (QualifierInstance)var4.next();
               if (annotationType.equals(checkedQualifier.getAnnotationClass())) {
                  throw BeanManagerLogger.LOG.duplicateQualifiers(this.qualifierInstances);
               }
            }
         }

      }
   }

   protected MetaAnnotationStore getMetaAnnotationStore() {
      return this.store;
   }

   static {
      ANY_SINGLETON = Collections.singleton(QualifierInstance.ANY);
   }

   protected static class ResolvableImpl implements Resolvable {
      private final Set qualifierInstances;
      private final Set typeClosure;
      private final Class rawType;
      private final Bean declaringBean;
      private final boolean delegate;
      private final int hashCode;

      protected ResolvableImpl(Class rawType, Set typeClosure, Bean declaringBean, Set qualifierInstances, boolean delegate) {
         this.typeClosure = typeClosure;
         this.rawType = rawType;
         this.declaringBean = declaringBean;
         this.qualifierInstances = qualifierInstances;
         this.delegate = delegate;
         this.hashCode = this.calculateHashCode();
      }

      private int calculateHashCode() {
         int hashCode = 17;
         hashCode = 31 * hashCode + this.getTypes().hashCode();
         return 31 * hashCode + this.qualifierInstances.hashCode();
      }

      public Set getQualifiers() {
         return this.qualifierInstances;
      }

      public Set getTypes() {
         return this.typeClosure;
      }

      public Class getJavaClass() {
         return this.rawType;
      }

      public Bean getDeclaringBean() {
         return this.declaringBean;
      }

      public String toString() {
         return "Types: " + this.getTypes() + "; Bindings: " + this.getQualifiers();
      }

      public int hashCode() {
         return this.hashCode;
      }

      public boolean equals(Object o) {
         if (this == o) {
            return true;
         } else if (!(o instanceof ResolvableImpl)) {
            return false;
         } else {
            ResolvableImpl r = (ResolvableImpl)o;
            return this.getTypes().equals(r.getTypes()) && this.qualifierInstances.equals(r.qualifierInstances);
         }
      }

      public boolean isDelegate() {
         return this.delegate;
      }
   }
}
