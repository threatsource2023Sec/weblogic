package jnr.ffi.mapper;

public final class ToNativeTypes {
   public static ToNativeType create(ToNativeConverter converter) {
      if (converter == null) {
         return null;
      } else {
         return (ToNativeType)(converter.getClass().isAnnotationPresent(ToNativeConverter.Cacheable.class) ? new Cacheable(converter) : new UnCacheable(converter));
      }
   }

   static class UnCacheable extends AbstractToNativeType {
      public UnCacheable(ToNativeConverter converter) {
         super(converter);
      }
   }

   @ToNativeType.Cacheable
   static class Cacheable extends AbstractToNativeType {
      public Cacheable(ToNativeConverter converter) {
         super(converter);
      }
   }
}
