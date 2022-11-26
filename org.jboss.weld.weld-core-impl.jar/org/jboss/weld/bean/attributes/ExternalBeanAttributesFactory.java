package org.jboss.weld.bean.attributes;

import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import javax.enterprise.context.Dependent;
import javax.enterprise.inject.spi.BeanAttributes;
import javax.enterprise.inject.spi.BeanManager;
import org.jboss.weld.logging.MetadataLogger;
import org.jboss.weld.util.Bindings;

public class ExternalBeanAttributesFactory {
   private ExternalBeanAttributesFactory() {
   }

   public static BeanAttributes of(BeanAttributes source, BeanManager manager) {
      validateBeanAttributes(source, manager);
      BeanAttributes attributes = new ImmutableBeanAttributes(defensiveCopy(source.getStereotypes()), source.isAlternative(), source.getName(), defensiveCopy(source.getQualifiers()), defensiveCopy(source.getTypes()), source.getScope());
      return attributes;
   }

   private static Set defensiveCopy(Set set) {
      return new HashSet(set);
   }

   public static void validateBeanAttributes(BeanAttributes attributes, BeanManager manager) {
      validateStereotypes(attributes, manager);
      validateQualifiers(attributes, manager);
      validateTypes(attributes, manager);
      validateScope(attributes, manager);
   }

   public static void validateStereotypes(BeanAttributes attributes, BeanManager manager) {
      if (attributes.getStereotypes() == null) {
         throw MetadataLogger.LOG.stereotypesNull(attributes);
      } else {
         Iterator var2 = attributes.getStereotypes().iterator();

         Class annotation;
         do {
            if (!var2.hasNext()) {
               return;
            }

            annotation = (Class)var2.next();
         } while(manager.isStereotype(annotation));

         throw MetadataLogger.LOG.notAStereotype(annotation, attributes);
      }
   }

   public static void validateQualifiers(BeanAttributes attributes, BeanManager manager) {
      Set qualifiers = attributes.getQualifiers();
      Bindings.validateQualifiers(qualifiers, manager, attributes, "BeanAttributes.getQualifiers");
   }

   public static void validateTypes(BeanAttributes attributes, BeanManager manager) {
      if (attributes.getTypes() == null) {
         throw MetadataLogger.LOG.typesNull(attributes);
      } else if (attributes.getTypes().isEmpty()) {
         throw MetadataLogger.LOG.typesEmpty(attributes);
      } else {
         Iterator var2 = attributes.getTypes().iterator();

         while(var2.hasNext()) {
            Type type = (Type)var2.next();
            validateBeanType(type, attributes);
         }

      }
   }

   private static void validateBeanType(Type type, BeanAttributes attributes) {
      checkBeanTypeNotATypeVariable(type, type, attributes);
      checkBeanTypeForWildcardsAndTypeVariables(type, type, attributes);
   }

   private static void checkBeanTypeNotATypeVariable(Type beanType, Type type, BeanAttributes attributes) {
      if (type instanceof TypeVariable) {
         throw MetadataLogger.LOG.typeVariableIsNotAValidBeanType(beanType, attributes);
      } else {
         if (type instanceof GenericArrayType) {
            GenericArrayType arrayType = (GenericArrayType)type;
            checkBeanTypeNotATypeVariable(beanType, arrayType.getGenericComponentType(), attributes);
         }

      }
   }

   private static void checkBeanTypeForWildcardsAndTypeVariables(Type beanType, Type type, BeanAttributes attributes) {
      if (type instanceof TypeVariable) {
         if (!attributes.getScope().equals(Dependent.class)) {
            throw MetadataLogger.LOG.beanWithParameterizedTypeContainingTypeVariablesMustBeDependentScoped(beanType, attributes);
         }
      } else {
         if (type instanceof WildcardType) {
            throw MetadataLogger.LOG.parameterizedTypeContainingWildcardParameterIsNotAValidBeanType(beanType, attributes);
         }

         if (type instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType)type;
            Type[] var4 = parameterizedType.getActualTypeArguments();
            int var5 = var4.length;

            for(int var6 = 0; var6 < var5; ++var6) {
               Type typeArgument = var4[var6];
               checkBeanTypeForWildcardsAndTypeVariables(beanType, typeArgument, attributes);
            }
         } else if (type instanceof GenericArrayType) {
            GenericArrayType arrayType = (GenericArrayType)type;
            checkBeanTypeForWildcardsAndTypeVariables(beanType, arrayType.getGenericComponentType(), attributes);
         }
      }

   }

   public static void validateScope(BeanAttributes attributes, BeanManager manager) {
      if (attributes.getScope() == null) {
         throw MetadataLogger.LOG.scopeNull(attributes);
      } else if (!manager.isScope(attributes.getScope())) {
         throw MetadataLogger.LOG.notAScope(attributes.getScope(), attributes);
      }
   }
}
