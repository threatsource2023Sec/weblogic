package com.bea.core.repackaged.springframework.core.convert.support;

import com.bea.core.repackaged.springframework.core.convert.ConversionService;
import com.bea.core.repackaged.springframework.core.convert.TypeDescriptor;
import com.bea.core.repackaged.springframework.core.convert.converter.ConditionalGenericConverter;
import com.bea.core.repackaged.springframework.core.convert.converter.GenericConverter;
import com.bea.core.repackaged.springframework.lang.Nullable;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class StreamConverter implements ConditionalGenericConverter {
   private static final TypeDescriptor STREAM_TYPE = TypeDescriptor.valueOf(Stream.class);
   private static final Set CONVERTIBLE_TYPES = createConvertibleTypes();
   private final ConversionService conversionService;

   public StreamConverter(ConversionService conversionService) {
      this.conversionService = conversionService;
   }

   public Set getConvertibleTypes() {
      return CONVERTIBLE_TYPES;
   }

   public boolean matches(TypeDescriptor sourceType, TypeDescriptor targetType) {
      if (sourceType.isAssignableTo(STREAM_TYPE)) {
         return this.matchesFromStream(sourceType.getElementTypeDescriptor(), targetType);
      } else {
         return targetType.isAssignableTo(STREAM_TYPE) ? this.matchesToStream(targetType.getElementTypeDescriptor(), sourceType) : false;
      }
   }

   public boolean matchesFromStream(@Nullable TypeDescriptor elementType, TypeDescriptor targetType) {
      TypeDescriptor collectionOfElement = TypeDescriptor.collection(Collection.class, elementType);
      return this.conversionService.canConvert(collectionOfElement, targetType);
   }

   public boolean matchesToStream(@Nullable TypeDescriptor elementType, TypeDescriptor sourceType) {
      TypeDescriptor collectionOfElement = TypeDescriptor.collection(Collection.class, elementType);
      return this.conversionService.canConvert(sourceType, collectionOfElement);
   }

   @Nullable
   public Object convert(@Nullable Object source, TypeDescriptor sourceType, TypeDescriptor targetType) {
      if (sourceType.isAssignableTo(STREAM_TYPE)) {
         return this.convertFromStream((Stream)source, sourceType, targetType);
      } else if (targetType.isAssignableTo(STREAM_TYPE)) {
         return this.convertToStream(source, sourceType, targetType);
      } else {
         throw new IllegalStateException("Unexpected source/target types");
      }
   }

   @Nullable
   private Object convertFromStream(@Nullable Stream source, TypeDescriptor streamType, TypeDescriptor targetType) {
      List content = source != null ? (List)source.collect(Collectors.toList()) : Collections.emptyList();
      TypeDescriptor listType = TypeDescriptor.collection(List.class, streamType.getElementTypeDescriptor());
      return this.conversionService.convert(content, listType, targetType);
   }

   private Object convertToStream(@Nullable Object source, TypeDescriptor sourceType, TypeDescriptor streamType) {
      TypeDescriptor targetCollection = TypeDescriptor.collection(List.class, streamType.getElementTypeDescriptor());
      List target = (List)this.conversionService.convert(source, sourceType, targetCollection);
      if (target == null) {
         target = Collections.emptyList();
      }

      return target.stream();
   }

   private static Set createConvertibleTypes() {
      Set convertiblePairs = new HashSet();
      convertiblePairs.add(new GenericConverter.ConvertiblePair(Stream.class, Collection.class));
      convertiblePairs.add(new GenericConverter.ConvertiblePair(Stream.class, Object[].class));
      convertiblePairs.add(new GenericConverter.ConvertiblePair(Collection.class, Stream.class));
      convertiblePairs.add(new GenericConverter.ConvertiblePair(Object[].class, Stream.class));
      return convertiblePairs;
   }
}
