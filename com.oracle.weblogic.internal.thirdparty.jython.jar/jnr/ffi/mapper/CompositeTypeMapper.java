package jnr.ffi.mapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

public final class CompositeTypeMapper implements SignatureTypeMapper {
   private final Collection signatureTypeMappers;

   public CompositeTypeMapper(SignatureTypeMapper... signatureTypeMappers) {
      this.signatureTypeMappers = Collections.unmodifiableList(Arrays.asList((Object[])signatureTypeMappers.clone()));
   }

   public CompositeTypeMapper(Collection signatureTypeMappers) {
      this.signatureTypeMappers = Collections.unmodifiableList(new ArrayList(signatureTypeMappers));
   }

   public FromNativeType getFromNativeType(SignatureType type, FromNativeContext context) {
      Iterator var3 = this.signatureTypeMappers.iterator();

      FromNativeType fromNativeType;
      do {
         if (!var3.hasNext()) {
            return null;
         }

         SignatureTypeMapper m = (SignatureTypeMapper)var3.next();
         fromNativeType = m.getFromNativeType(type, context);
      } while(fromNativeType == null);

      return fromNativeType;
   }

   public ToNativeType getToNativeType(SignatureType type, ToNativeContext context) {
      Iterator var3 = this.signatureTypeMappers.iterator();

      ToNativeType toNativeType;
      do {
         if (!var3.hasNext()) {
            return null;
         }

         SignatureTypeMapper m = (SignatureTypeMapper)var3.next();
         toNativeType = m.getToNativeType(type, context);
      } while(toNativeType == null);

      return toNativeType;
   }
}
