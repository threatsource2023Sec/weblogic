package com.bea.core.repackaged.springframework.core.convert.support;

import com.bea.core.repackaged.springframework.core.convert.ConversionService;
import com.bea.core.repackaged.springframework.core.convert.TypeDescriptor;
import com.bea.core.repackaged.springframework.core.convert.converter.ConditionalGenericConverter;
import com.bea.core.repackaged.springframework.core.convert.converter.GenericConverter;
import com.bea.core.repackaged.springframework.lang.Nullable;
import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

final class ByteBufferConverter implements ConditionalGenericConverter {
   private static final TypeDescriptor BYTE_BUFFER_TYPE = TypeDescriptor.valueOf(ByteBuffer.class);
   private static final TypeDescriptor BYTE_ARRAY_TYPE = TypeDescriptor.valueOf(byte[].class);
   private static final Set CONVERTIBLE_PAIRS;
   private final ConversionService conversionService;

   public ByteBufferConverter(ConversionService conversionService) {
      this.conversionService = conversionService;
   }

   public Set getConvertibleTypes() {
      return CONVERTIBLE_PAIRS;
   }

   public boolean matches(TypeDescriptor sourceType, TypeDescriptor targetType) {
      boolean byteBufferTarget = targetType.isAssignableTo(BYTE_BUFFER_TYPE);
      if (sourceType.isAssignableTo(BYTE_BUFFER_TYPE)) {
         return byteBufferTarget || this.matchesFromByteBuffer(targetType);
      } else {
         return byteBufferTarget && this.matchesToByteBuffer(sourceType);
      }
   }

   private boolean matchesFromByteBuffer(TypeDescriptor targetType) {
      return targetType.isAssignableTo(BYTE_ARRAY_TYPE) || this.conversionService.canConvert(BYTE_ARRAY_TYPE, targetType);
   }

   private boolean matchesToByteBuffer(TypeDescriptor sourceType) {
      return sourceType.isAssignableTo(BYTE_ARRAY_TYPE) || this.conversionService.canConvert(sourceType, BYTE_ARRAY_TYPE);
   }

   @Nullable
   public Object convert(@Nullable Object source, TypeDescriptor sourceType, TypeDescriptor targetType) {
      boolean byteBufferTarget = targetType.isAssignableTo(BYTE_BUFFER_TYPE);
      if (source instanceof ByteBuffer) {
         ByteBuffer buffer = (ByteBuffer)source;
         return byteBufferTarget ? buffer.duplicate() : this.convertFromByteBuffer(buffer, targetType);
      } else if (byteBufferTarget) {
         return this.convertToByteBuffer(source, sourceType);
      } else {
         throw new IllegalStateException("Unexpected source/target types");
      }
   }

   @Nullable
   private Object convertFromByteBuffer(ByteBuffer source, TypeDescriptor targetType) {
      byte[] bytes = new byte[source.remaining()];
      source.get(bytes);
      return targetType.isAssignableTo(BYTE_ARRAY_TYPE) ? bytes : this.conversionService.convert(bytes, BYTE_ARRAY_TYPE, targetType);
   }

   private Object convertToByteBuffer(@Nullable Object source, TypeDescriptor sourceType) {
      byte[] bytes = (byte[])((byte[])(source instanceof byte[] ? source : this.conversionService.convert(source, sourceType, BYTE_ARRAY_TYPE)));
      if (bytes == null) {
         return ByteBuffer.wrap(new byte[0]);
      } else {
         ByteBuffer byteBuffer = ByteBuffer.allocate(bytes.length);
         byteBuffer.put(bytes);
         return byteBuffer.rewind();
      }
   }

   static {
      Set convertiblePairs = new HashSet(4);
      convertiblePairs.add(new GenericConverter.ConvertiblePair(ByteBuffer.class, byte[].class));
      convertiblePairs.add(new GenericConverter.ConvertiblePair(byte[].class, ByteBuffer.class));
      convertiblePairs.add(new GenericConverter.ConvertiblePair(ByteBuffer.class, Object.class));
      convertiblePairs.add(new GenericConverter.ConvertiblePair(Object.class, ByteBuffer.class));
      CONVERTIBLE_PAIRS = Collections.unmodifiableSet(convertiblePairs);
   }
}
