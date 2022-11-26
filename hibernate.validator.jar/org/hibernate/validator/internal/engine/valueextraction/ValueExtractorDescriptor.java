package org.hibernate.validator.internal.engine.valueextraction;

import java.lang.invoke.MethodHandles;
import java.lang.reflect.AnnotatedArrayType;
import java.lang.reflect.AnnotatedParameterizedType;
import java.lang.reflect.AnnotatedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.validation.valueextraction.ExtractedValue;
import javax.validation.valueextraction.UnwrapByDefault;
import javax.validation.valueextraction.ValueExtractor;
import org.hibernate.validator.internal.util.ReflectionHelper;
import org.hibernate.validator.internal.util.StringHelper;
import org.hibernate.validator.internal.util.TypeHelper;
import org.hibernate.validator.internal.util.logging.Log;
import org.hibernate.validator.internal.util.logging.LoggerFactory;

public class ValueExtractorDescriptor {
   private static final Log LOG = LoggerFactory.make(MethodHandles.lookup());
   private final Key key;
   private final ValueExtractor valueExtractor;
   private final boolean unwrapByDefault;
   private final Optional extractedType;

   public ValueExtractorDescriptor(ValueExtractor valueExtractor) {
      AnnotatedParameterizedType valueExtractorDefinition = getValueExtractorDefinition(valueExtractor.getClass());
      this.key = new Key(getContainerType(valueExtractorDefinition, valueExtractor.getClass()), getExtractedTypeParameter(valueExtractorDefinition, valueExtractor.getClass()));
      this.valueExtractor = valueExtractor;
      this.unwrapByDefault = hasUnwrapByDefaultAnnotation(valueExtractor.getClass());
      this.extractedType = getExtractedType(valueExtractorDefinition);
   }

   private static TypeVariable getExtractedTypeParameter(AnnotatedParameterizedType valueExtractorDefinition, Class extractorImplementationType) {
      AnnotatedType containerType = valueExtractorDefinition.getAnnotatedActualTypeArguments()[0];
      Class containerTypeRaw = (Class)TypeHelper.getErasedType(containerType.getType());
      TypeVariable extractedTypeParameter = null;
      if (containerType.isAnnotationPresent(ExtractedValue.class)) {
         if (containerType instanceof AnnotatedArrayType) {
            extractedTypeParameter = new ArrayElement((AnnotatedArrayType)containerType);
         } else {
            extractedTypeParameter = AnnotatedObject.INSTANCE;
         }
      }

      if (containerType instanceof AnnotatedParameterizedType) {
         AnnotatedParameterizedType parameterizedExtractedType = (AnnotatedParameterizedType)containerType;
         int i = 0;
         AnnotatedType[] var7 = parameterizedExtractedType.getAnnotatedActualTypeArguments();
         int var8 = var7.length;

         for(int var9 = 0; var9 < var8; ++var9) {
            AnnotatedType typeArgument = var7[var9];
            if (!TypeHelper.isUnboundWildcard(typeArgument.getType())) {
               throw LOG.getOnlyUnboundWildcardTypeArgumentsSupportedForContainerTypeOfValueExtractorException(extractorImplementationType);
            }

            if (typeArgument.isAnnotationPresent(ExtractedValue.class)) {
               if (extractedTypeParameter != null) {
                  throw LOG.getValueExtractorDeclaresExtractedValueMultipleTimesException(extractorImplementationType);
               }

               if (!Void.TYPE.equals(((ExtractedValue)typeArgument.getAnnotation(ExtractedValue.class)).type())) {
                  throw LOG.getExtractedValueOnTypeParameterOfContainerTypeMayNotDefineTypeAttributeException(extractorImplementationType);
               }

               extractedTypeParameter = containerTypeRaw.getTypeParameters()[i];
            }

            ++i;
         }
      }

      if (extractedTypeParameter == null) {
         throw LOG.getValueExtractorFailsToDeclareExtractedValueException(extractorImplementationType);
      } else {
         return (TypeVariable)extractedTypeParameter;
      }
   }

   private static Optional getExtractedType(AnnotatedParameterizedType valueExtractorDefinition) {
      AnnotatedType containerType = valueExtractorDefinition.getAnnotatedActualTypeArguments()[0];
      if (containerType.isAnnotationPresent(ExtractedValue.class)) {
         Class extractedType = ((ExtractedValue)containerType.getAnnotation(ExtractedValue.class)).type();
         if (!Void.TYPE.equals(extractedType)) {
            return Optional.of(ReflectionHelper.boxedType(extractedType));
         }
      }

      return Optional.empty();
   }

   private static Class getContainerType(AnnotatedParameterizedType valueExtractorDefinition, Class extractorImplementationType) {
      AnnotatedType containerType = valueExtractorDefinition.getAnnotatedActualTypeArguments()[0];
      return TypeHelper.getErasedReferenceType(containerType.getType());
   }

   private static AnnotatedParameterizedType getValueExtractorDefinition(Class extractorImplementationType) {
      List valueExtractorAnnotatedTypes = new ArrayList();
      determineValueExtractorDefinitions(valueExtractorAnnotatedTypes, extractorImplementationType);
      if (valueExtractorAnnotatedTypes.size() == 1) {
         return (AnnotatedParameterizedType)valueExtractorAnnotatedTypes.get(0);
      } else if (valueExtractorAnnotatedTypes.size() > 1) {
         throw LOG.getParallelDefinitionsOfValueExtractorsException(extractorImplementationType);
      } else {
         throw new AssertionError(extractorImplementationType.getName() + " should be a subclass of " + ValueExtractor.class.getSimpleName());
      }
   }

   private static void determineValueExtractorDefinitions(List valueExtractorDefinitions, Class extractorImplementationType) {
      if (ValueExtractor.class.isAssignableFrom(extractorImplementationType)) {
         Class superClass = extractorImplementationType.getSuperclass();
         if (superClass != null && !Object.class.equals(superClass)) {
            determineValueExtractorDefinitions(valueExtractorDefinitions, superClass);
         }

         Class[] var3 = extractorImplementationType.getInterfaces();
         int var4 = var3.length;

         int var5;
         for(var5 = 0; var5 < var4; ++var5) {
            Class implementedInterface = var3[var5];
            if (!ValueExtractor.class.equals(implementedInterface)) {
               determineValueExtractorDefinitions(valueExtractorDefinitions, implementedInterface);
            }
         }

         AnnotatedType[] var7 = extractorImplementationType.getAnnotatedInterfaces();
         var4 = var7.length;

         for(var5 = 0; var5 < var4; ++var5) {
            AnnotatedType annotatedInterface = var7[var5];
            if (ValueExtractor.class.equals(ReflectionHelper.getClassFromType(annotatedInterface.getType()))) {
               valueExtractorDefinitions.add(annotatedInterface);
            }
         }

      }
   }

   private static boolean hasUnwrapByDefaultAnnotation(Class extractorImplementationType) {
      return extractorImplementationType.isAnnotationPresent(UnwrapByDefault.class);
   }

   public Key getKey() {
      return this.key;
   }

   public Class getContainerType() {
      return this.key.containerType;
   }

   public TypeVariable getExtractedTypeParameter() {
      return this.key.extractedTypeParameter;
   }

   public Optional getExtractedType() {
      return this.extractedType;
   }

   public ValueExtractor getValueExtractor() {
      return this.valueExtractor;
   }

   public boolean isUnwrapByDefault() {
      return this.unwrapByDefault;
   }

   public String toString() {
      return "ValueExtractorDescriptor [key=" + this.key + ", valueExtractor=" + this.valueExtractor + ", unwrapByDefault=" + this.unwrapByDefault + "]";
   }

   public static class Key {
      private final Class containerType;
      private final TypeVariable extractedTypeParameter;
      private final int hashCode;

      public Key(Class containerType, TypeVariable extractedTypeParameter) {
         this.containerType = containerType;
         this.extractedTypeParameter = extractedTypeParameter;
         this.hashCode = buildHashCode(containerType, extractedTypeParameter);
      }

      private static int buildHashCode(Type containerType, TypeVariable extractedTypeParameter) {
         int prime = true;
         int result = 1;
         result = 31 * result + containerType.hashCode();
         result = 31 * result + extractedTypeParameter.hashCode();
         return result;
      }

      public int hashCode() {
         return this.hashCode;
      }

      public boolean equals(Object obj) {
         if (this == obj) {
            return true;
         } else if (obj == null) {
            return false;
         } else if (this.getClass() != obj.getClass()) {
            return false;
         } else {
            Key other = (Key)obj;
            return this.containerType.equals(other.containerType) && this.extractedTypeParameter.equals(other.extractedTypeParameter);
         }
      }

      public String toString() {
         return "Key [containerType=" + StringHelper.toShortString((Type)this.containerType) + ", extractedTypeParameter=" + this.extractedTypeParameter + "]";
      }
   }
}
