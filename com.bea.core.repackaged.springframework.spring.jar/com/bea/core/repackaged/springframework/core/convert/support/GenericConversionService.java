package com.bea.core.repackaged.springframework.core.convert.support;

import com.bea.core.repackaged.springframework.core.DecoratingProxy;
import com.bea.core.repackaged.springframework.core.ResolvableType;
import com.bea.core.repackaged.springframework.core.convert.ConversionFailedException;
import com.bea.core.repackaged.springframework.core.convert.ConverterNotFoundException;
import com.bea.core.repackaged.springframework.core.convert.TypeDescriptor;
import com.bea.core.repackaged.springframework.core.convert.converter.ConditionalConverter;
import com.bea.core.repackaged.springframework.core.convert.converter.ConditionalGenericConverter;
import com.bea.core.repackaged.springframework.core.convert.converter.Converter;
import com.bea.core.repackaged.springframework.core.convert.converter.ConverterFactory;
import com.bea.core.repackaged.springframework.core.convert.converter.GenericConverter;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;
import com.bea.core.repackaged.springframework.util.ClassUtils;
import com.bea.core.repackaged.springframework.util.ConcurrentReferenceHashMap;
import com.bea.core.repackaged.springframework.util.StringUtils;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class GenericConversionService implements ConfigurableConversionService {
   private static final GenericConverter NO_OP_CONVERTER = new NoOpConverter("NO_OP");
   private static final GenericConverter NO_MATCH = new NoOpConverter("NO_MATCH");
   private final Converters converters = new Converters();
   private final Map converterCache = new ConcurrentReferenceHashMap(64);

   public void addConverter(Converter converter) {
      ResolvableType[] typeInfo = this.getRequiredTypeInfo(converter.getClass(), Converter.class);
      if (typeInfo == null && converter instanceof DecoratingProxy) {
         typeInfo = this.getRequiredTypeInfo(((DecoratingProxy)converter).getDecoratedClass(), Converter.class);
      }

      if (typeInfo == null) {
         throw new IllegalArgumentException("Unable to determine source type <S> and target type <T> for your Converter [" + converter.getClass().getName() + "]; does the class parameterize those types?");
      } else {
         this.addConverter((GenericConverter)(new ConverterAdapter(converter, typeInfo[0], typeInfo[1])));
      }
   }

   public void addConverter(Class sourceType, Class targetType, Converter converter) {
      this.addConverter((GenericConverter)(new ConverterAdapter(converter, ResolvableType.forClass(sourceType), ResolvableType.forClass(targetType))));
   }

   public void addConverter(GenericConverter converter) {
      this.converters.add(converter);
      this.invalidateCache();
   }

   public void addConverterFactory(ConverterFactory factory) {
      ResolvableType[] typeInfo = this.getRequiredTypeInfo(factory.getClass(), ConverterFactory.class);
      if (typeInfo == null && factory instanceof DecoratingProxy) {
         typeInfo = this.getRequiredTypeInfo(((DecoratingProxy)factory).getDecoratedClass(), ConverterFactory.class);
      }

      if (typeInfo == null) {
         throw new IllegalArgumentException("Unable to determine source type <S> and target type <T> for your ConverterFactory [" + factory.getClass().getName() + "]; does the class parameterize those types?");
      } else {
         this.addConverter((GenericConverter)(new ConverterFactoryAdapter(factory, new GenericConverter.ConvertiblePair(typeInfo[0].toClass(), typeInfo[1].toClass()))));
      }
   }

   public void removeConvertible(Class sourceType, Class targetType) {
      this.converters.remove(sourceType, targetType);
      this.invalidateCache();
   }

   public boolean canConvert(@Nullable Class sourceType, Class targetType) {
      Assert.notNull(targetType, (String)"Target type to convert to cannot be null");
      return this.canConvert(sourceType != null ? TypeDescriptor.valueOf(sourceType) : null, TypeDescriptor.valueOf(targetType));
   }

   public boolean canConvert(@Nullable TypeDescriptor sourceType, TypeDescriptor targetType) {
      Assert.notNull(targetType, (String)"Target type to convert to cannot be null");
      if (sourceType == null) {
         return true;
      } else {
         GenericConverter converter = this.getConverter(sourceType, targetType);
         return converter != null;
      }
   }

   public boolean canBypassConvert(@Nullable TypeDescriptor sourceType, TypeDescriptor targetType) {
      Assert.notNull(targetType, (String)"Target type to convert to cannot be null");
      if (sourceType == null) {
         return true;
      } else {
         GenericConverter converter = this.getConverter(sourceType, targetType);
         return converter == NO_OP_CONVERTER;
      }
   }

   @Nullable
   public Object convert(@Nullable Object source, Class targetType) {
      Assert.notNull(targetType, (String)"Target type to convert to cannot be null");
      return this.convert(source, TypeDescriptor.forObject(source), TypeDescriptor.valueOf(targetType));
   }

   @Nullable
   public Object convert(@Nullable Object source, @Nullable TypeDescriptor sourceType, TypeDescriptor targetType) {
      Assert.notNull(targetType, (String)"Target type to convert to cannot be null");
      if (sourceType == null) {
         Assert.isTrue(source == null, "Source must be [null] if source type == [null]");
         return this.handleResult((TypeDescriptor)null, targetType, this.convertNullSource((TypeDescriptor)null, targetType));
      } else if (source != null && !sourceType.getObjectType().isInstance(source)) {
         throw new IllegalArgumentException("Source to convert from must be an instance of [" + sourceType + "]; instead it was a [" + source.getClass().getName() + "]");
      } else {
         GenericConverter converter = this.getConverter(sourceType, targetType);
         if (converter != null) {
            Object result = ConversionUtils.invokeConverter(converter, source, sourceType, targetType);
            return this.handleResult(sourceType, targetType, result);
         } else {
            return this.handleConverterNotFound(source, sourceType, targetType);
         }
      }
   }

   @Nullable
   public Object convert(@Nullable Object source, TypeDescriptor targetType) {
      return this.convert(source, TypeDescriptor.forObject(source), targetType);
   }

   public String toString() {
      return this.converters.toString();
   }

   @Nullable
   protected Object convertNullSource(@Nullable TypeDescriptor sourceType, TypeDescriptor targetType) {
      return targetType.getObjectType() == Optional.class ? Optional.empty() : null;
   }

   @Nullable
   protected GenericConverter getConverter(TypeDescriptor sourceType, TypeDescriptor targetType) {
      ConverterCacheKey key = new ConverterCacheKey(sourceType, targetType);
      GenericConverter converter = (GenericConverter)this.converterCache.get(key);
      if (converter != null) {
         return converter != NO_MATCH ? converter : null;
      } else {
         converter = this.converters.find(sourceType, targetType);
         if (converter == null) {
            converter = this.getDefaultConverter(sourceType, targetType);
         }

         if (converter != null) {
            this.converterCache.put(key, converter);
            return converter;
         } else {
            this.converterCache.put(key, NO_MATCH);
            return null;
         }
      }
   }

   @Nullable
   protected GenericConverter getDefaultConverter(TypeDescriptor sourceType, TypeDescriptor targetType) {
      return sourceType.isAssignableTo(targetType) ? NO_OP_CONVERTER : null;
   }

   @Nullable
   private ResolvableType[] getRequiredTypeInfo(Class converterClass, Class genericIfc) {
      ResolvableType resolvableType = ResolvableType.forClass(converterClass).as(genericIfc);
      ResolvableType[] generics = resolvableType.getGenerics();
      if (generics.length < 2) {
         return null;
      } else {
         Class sourceType = generics[0].resolve();
         Class targetType = generics[1].resolve();
         return sourceType != null && targetType != null ? generics : null;
      }
   }

   private void invalidateCache() {
      this.converterCache.clear();
   }

   @Nullable
   private Object handleConverterNotFound(@Nullable Object source, @Nullable TypeDescriptor sourceType, TypeDescriptor targetType) {
      if (source == null) {
         this.assertNotPrimitiveTargetType(sourceType, targetType);
         return null;
      } else if ((sourceType == null || sourceType.isAssignableTo(targetType)) && targetType.getObjectType().isInstance(source)) {
         return source;
      } else {
         throw new ConverterNotFoundException(sourceType, targetType);
      }
   }

   @Nullable
   private Object handleResult(@Nullable TypeDescriptor sourceType, TypeDescriptor targetType, @Nullable Object result) {
      if (result == null) {
         this.assertNotPrimitiveTargetType(sourceType, targetType);
      }

      return result;
   }

   private void assertNotPrimitiveTargetType(@Nullable TypeDescriptor sourceType, TypeDescriptor targetType) {
      if (targetType.isPrimitive()) {
         throw new ConversionFailedException(sourceType, targetType, (Object)null, new IllegalArgumentException("A null value cannot be assigned to a primitive type"));
      }
   }

   private static class NoOpConverter implements GenericConverter {
      private final String name;

      public NoOpConverter(String name) {
         this.name = name;
      }

      public Set getConvertibleTypes() {
         return null;
      }

      @Nullable
      public Object convert(@Nullable Object source, TypeDescriptor sourceType, TypeDescriptor targetType) {
         return source;
      }

      public String toString() {
         return this.name;
      }
   }

   private static class ConvertersForPair {
      private final LinkedList converters;

      private ConvertersForPair() {
         this.converters = new LinkedList();
      }

      public void add(GenericConverter converter) {
         this.converters.addFirst(converter);
      }

      @Nullable
      public GenericConverter getConverter(TypeDescriptor sourceType, TypeDescriptor targetType) {
         Iterator var3 = this.converters.iterator();

         GenericConverter converter;
         do {
            if (!var3.hasNext()) {
               return null;
            }

            converter = (GenericConverter)var3.next();
         } while(converter instanceof ConditionalGenericConverter && !((ConditionalGenericConverter)converter).matches(sourceType, targetType));

         return converter;
      }

      public String toString() {
         return StringUtils.collectionToCommaDelimitedString(this.converters);
      }

      // $FF: synthetic method
      ConvertersForPair(Object x0) {
         this();
      }
   }

   private static class Converters {
      private final Set globalConverters;
      private final Map converters;

      private Converters() {
         this.globalConverters = new LinkedHashSet();
         this.converters = new LinkedHashMap(36);
      }

      public void add(GenericConverter converter) {
         Set convertibleTypes = converter.getConvertibleTypes();
         if (convertibleTypes == null) {
            Assert.state(converter instanceof ConditionalConverter, "Only conditional converters may return null convertible types");
            this.globalConverters.add(converter);
         } else {
            Iterator var3 = convertibleTypes.iterator();

            while(var3.hasNext()) {
               GenericConverter.ConvertiblePair convertiblePair = (GenericConverter.ConvertiblePair)var3.next();
               ConvertersForPair convertersForPair = this.getMatchableConverters(convertiblePair);
               convertersForPair.add(converter);
            }
         }

      }

      private ConvertersForPair getMatchableConverters(GenericConverter.ConvertiblePair convertiblePair) {
         ConvertersForPair convertersForPair = (ConvertersForPair)this.converters.get(convertiblePair);
         if (convertersForPair == null) {
            convertersForPair = new ConvertersForPair();
            this.converters.put(convertiblePair, convertersForPair);
         }

         return convertersForPair;
      }

      public void remove(Class sourceType, Class targetType) {
         this.converters.remove(new GenericConverter.ConvertiblePair(sourceType, targetType));
      }

      @Nullable
      public GenericConverter find(TypeDescriptor sourceType, TypeDescriptor targetType) {
         List sourceCandidates = this.getClassHierarchy(sourceType.getType());
         List targetCandidates = this.getClassHierarchy(targetType.getType());
         Iterator var5 = sourceCandidates.iterator();

         while(var5.hasNext()) {
            Class sourceCandidate = (Class)var5.next();
            Iterator var7 = targetCandidates.iterator();

            while(var7.hasNext()) {
               Class targetCandidate = (Class)var7.next();
               GenericConverter.ConvertiblePair convertiblePair = new GenericConverter.ConvertiblePair(sourceCandidate, targetCandidate);
               GenericConverter converter = this.getRegisteredConverter(sourceType, targetType, convertiblePair);
               if (converter != null) {
                  return converter;
               }
            }
         }

         return null;
      }

      @Nullable
      private GenericConverter getRegisteredConverter(TypeDescriptor sourceType, TypeDescriptor targetType, GenericConverter.ConvertiblePair convertiblePair) {
         ConvertersForPair convertersForPair = (ConvertersForPair)this.converters.get(convertiblePair);
         if (convertersForPair != null) {
            GenericConverter converter = convertersForPair.getConverter(sourceType, targetType);
            if (converter != null) {
               return converter;
            }
         }

         Iterator var7 = this.globalConverters.iterator();

         GenericConverter globalConverter;
         do {
            if (!var7.hasNext()) {
               return null;
            }

            globalConverter = (GenericConverter)var7.next();
         } while(!((ConditionalConverter)globalConverter).matches(sourceType, targetType));

         return globalConverter;
      }

      private List getClassHierarchy(Class type) {
         List hierarchy = new ArrayList(20);
         Set visited = new HashSet(20);
         this.addToClassHierarchy(0, ClassUtils.resolvePrimitiveIfNecessary(type), false, hierarchy, visited);
         boolean array = type.isArray();

         for(int i = 0; i < hierarchy.size(); ++i) {
            Class candidate = (Class)hierarchy.get(i);
            candidate = array ? candidate.getComponentType() : ClassUtils.resolvePrimitiveIfNecessary(candidate);
            Class superclass = candidate.getSuperclass();
            if (superclass != null && superclass != Object.class && superclass != Enum.class) {
               this.addToClassHierarchy(i + 1, candidate.getSuperclass(), array, hierarchy, visited);
            }

            this.addInterfacesToClassHierarchy(candidate, array, hierarchy, visited);
         }

         if (Enum.class.isAssignableFrom(type)) {
            this.addToClassHierarchy(hierarchy.size(), Enum.class, array, hierarchy, visited);
            this.addToClassHierarchy(hierarchy.size(), Enum.class, false, hierarchy, visited);
            this.addInterfacesToClassHierarchy(Enum.class, array, hierarchy, visited);
         }

         this.addToClassHierarchy(hierarchy.size(), Object.class, array, hierarchy, visited);
         this.addToClassHierarchy(hierarchy.size(), Object.class, false, hierarchy, visited);
         return hierarchy;
      }

      private void addInterfacesToClassHierarchy(Class type, boolean asArray, List hierarchy, Set visited) {
         Class[] var5 = type.getInterfaces();
         int var6 = var5.length;

         for(int var7 = 0; var7 < var6; ++var7) {
            Class implementedInterface = var5[var7];
            this.addToClassHierarchy(hierarchy.size(), implementedInterface, asArray, hierarchy, visited);
         }

      }

      private void addToClassHierarchy(int index, Class type, boolean asArray, List hierarchy, Set visited) {
         if (asArray) {
            type = Array.newInstance(type, 0).getClass();
         }

         if (visited.add(type)) {
            hierarchy.add(index, type);
         }

      }

      public String toString() {
         StringBuilder builder = new StringBuilder();
         builder.append("ConversionService converters =\n");
         Iterator var2 = this.getConverterStrings().iterator();

         while(var2.hasNext()) {
            String converterString = (String)var2.next();
            builder.append('\t').append(converterString).append('\n');
         }

         return builder.toString();
      }

      private List getConverterStrings() {
         List converterStrings = new ArrayList();
         Iterator var2 = this.converters.values().iterator();

         while(var2.hasNext()) {
            ConvertersForPair convertersForPair = (ConvertersForPair)var2.next();
            converterStrings.add(convertersForPair.toString());
         }

         Collections.sort(converterStrings);
         return converterStrings;
      }

      // $FF: synthetic method
      Converters(Object x0) {
         this();
      }
   }

   private static final class ConverterCacheKey implements Comparable {
      private final TypeDescriptor sourceType;
      private final TypeDescriptor targetType;

      public ConverterCacheKey(TypeDescriptor sourceType, TypeDescriptor targetType) {
         this.sourceType = sourceType;
         this.targetType = targetType;
      }

      public boolean equals(Object other) {
         if (this == other) {
            return true;
         } else if (!(other instanceof ConverterCacheKey)) {
            return false;
         } else {
            ConverterCacheKey otherKey = (ConverterCacheKey)other;
            return this.sourceType.equals(otherKey.sourceType) && this.targetType.equals(otherKey.targetType);
         }
      }

      public int hashCode() {
         return this.sourceType.hashCode() * 29 + this.targetType.hashCode();
      }

      public String toString() {
         return "ConverterCacheKey [sourceType = " + this.sourceType + ", targetType = " + this.targetType + "]";
      }

      public int compareTo(ConverterCacheKey other) {
         int result = this.sourceType.getResolvableType().toString().compareTo(other.sourceType.getResolvableType().toString());
         if (result == 0) {
            result = this.targetType.getResolvableType().toString().compareTo(other.targetType.getResolvableType().toString());
         }

         return result;
      }
   }

   private final class ConverterFactoryAdapter implements ConditionalGenericConverter {
      private final ConverterFactory converterFactory;
      private final GenericConverter.ConvertiblePair typeInfo;

      public ConverterFactoryAdapter(ConverterFactory converterFactory, GenericConverter.ConvertiblePair typeInfo) {
         this.converterFactory = converterFactory;
         this.typeInfo = typeInfo;
      }

      public Set getConvertibleTypes() {
         return Collections.singleton(this.typeInfo);
      }

      public boolean matches(TypeDescriptor sourceType, TypeDescriptor targetType) {
         boolean matches = true;
         if (this.converterFactory instanceof ConditionalConverter) {
            matches = ((ConditionalConverter)this.converterFactory).matches(sourceType, targetType);
         }

         if (matches) {
            Converter converter = this.converterFactory.getConverter(targetType.getType());
            if (converter instanceof ConditionalConverter) {
               matches = ((ConditionalConverter)converter).matches(sourceType, targetType);
            }
         }

         return matches;
      }

      @Nullable
      public Object convert(@Nullable Object source, TypeDescriptor sourceType, TypeDescriptor targetType) {
         return source == null ? GenericConversionService.this.convertNullSource(sourceType, targetType) : this.converterFactory.getConverter(targetType.getObjectType()).convert(source);
      }

      public String toString() {
         return this.typeInfo + " : " + this.converterFactory;
      }
   }

   private final class ConverterAdapter implements ConditionalGenericConverter {
      private final Converter converter;
      private final GenericConverter.ConvertiblePair typeInfo;
      private final ResolvableType targetType;

      public ConverterAdapter(Converter converter, ResolvableType sourceType, ResolvableType targetType) {
         this.converter = converter;
         this.typeInfo = new GenericConverter.ConvertiblePair(sourceType.toClass(), targetType.toClass());
         this.targetType = targetType;
      }

      public Set getConvertibleTypes() {
         return Collections.singleton(this.typeInfo);
      }

      public boolean matches(TypeDescriptor sourceType, TypeDescriptor targetType) {
         if (this.typeInfo.getTargetType() != targetType.getObjectType()) {
            return false;
         } else {
            ResolvableType rt = targetType.getResolvableType();
            if (!(rt.getType() instanceof Class) && !rt.isAssignableFrom(this.targetType) && !this.targetType.hasUnresolvableGenerics()) {
               return false;
            } else {
               return !(this.converter instanceof ConditionalConverter) || ((ConditionalConverter)this.converter).matches(sourceType, targetType);
            }
         }
      }

      @Nullable
      public Object convert(@Nullable Object source, TypeDescriptor sourceType, TypeDescriptor targetType) {
         return source == null ? GenericConversionService.this.convertNullSource(sourceType, targetType) : this.converter.convert(source);
      }

      public String toString() {
         return this.typeInfo + " : " + this.converter;
      }
   }
}
