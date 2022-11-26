package jnr.ffi.mapper;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public final class CachingTypeMapper extends AbstractSignatureTypeMapper implements SignatureTypeMapper {
   private final SignatureTypeMapper mapper;
   private volatile Map toNativeTypeMap = Collections.emptyMap();
   private volatile Map fromNativeTypeMap = Collections.emptyMap();
   private static final InvalidType UNCACHEABLE_TYPE = new InvalidType();
   private static final InvalidType NO_TYPE = new InvalidType();

   public CachingTypeMapper(SignatureTypeMapper mapper) {
      this.mapper = mapper;
   }

   public FromNativeType getFromNativeType(SignatureType type, FromNativeContext context) {
      FromNativeType fromNativeType = (FromNativeType)this.fromNativeTypeMap.get(type);
      if (fromNativeType == UNCACHEABLE_TYPE) {
         return this.mapper.getFromNativeType(type, context);
      } else if (fromNativeType == NO_TYPE) {
         return null;
      } else {
         return fromNativeType != null ? fromNativeType : this.lookupAndCacheFromNativeType(type, context);
      }
   }

   public ToNativeType getToNativeType(SignatureType type, ToNativeContext context) {
      ToNativeType toNativeType = (ToNativeType)this.toNativeTypeMap.get(type);
      if (toNativeType == UNCACHEABLE_TYPE) {
         return this.mapper.getToNativeType(type, context);
      } else if (toNativeType == NO_TYPE) {
         return null;
      } else {
         return toNativeType != null ? toNativeType : this.lookupAndCacheToNativeType(type, context);
      }
   }

   private synchronized FromNativeType lookupAndCacheFromNativeType(SignatureType signature, FromNativeContext context) {
      FromNativeType fromNativeType = (FromNativeType)this.fromNativeTypeMap.get(signature);
      if (fromNativeType == null) {
         fromNativeType = this.mapper.getFromNativeType(signature, context);
         FromNativeType typeForCaching = fromNativeType;
         if (fromNativeType == null) {
            typeForCaching = NO_TYPE;
         } else if (!fromNativeType.getClass().isAnnotationPresent(FromNativeType.Cacheable.class)) {
            typeForCaching = UNCACHEABLE_TYPE;
         }

         Map m = new HashMap(this.fromNativeTypeMap.size() + 1);
         m.putAll(this.fromNativeTypeMap);
         m.put(signature, typeForCaching);
         this.fromNativeTypeMap = Collections.unmodifiableMap(m);
      }

      return fromNativeType != NO_TYPE ? fromNativeType : null;
   }

   private synchronized ToNativeType lookupAndCacheToNativeType(SignatureType signature, ToNativeContext context) {
      ToNativeType toNativeType = (ToNativeType)this.toNativeTypeMap.get(signature);
      if (toNativeType == null) {
         toNativeType = this.mapper.getToNativeType(signature, context);
         ToNativeType typeForCaching = toNativeType;
         if (toNativeType == null) {
            typeForCaching = NO_TYPE;
         } else if (!toNativeType.getClass().isAnnotationPresent(ToNativeType.Cacheable.class)) {
            typeForCaching = UNCACHEABLE_TYPE;
         }

         Map m = new HashMap(this.toNativeTypeMap.size() + 1);
         m.putAll(this.toNativeTypeMap);
         m.put(signature, typeForCaching);
         this.toNativeTypeMap = Collections.unmodifiableMap(m);
      }

      return toNativeType != NO_TYPE ? toNativeType : null;
   }

   private static final class InvalidType implements ToNativeType, FromNativeType {
      private InvalidType() {
      }

      public FromNativeConverter getFromNativeConverter() {
         return null;
      }

      public ToNativeConverter getToNativeConverter() {
         return null;
      }

      // $FF: synthetic method
      InvalidType(Object x0) {
         this();
      }
   }
}
