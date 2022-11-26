package jnr.ffi.mapper;

import java.lang.reflect.Type;
import java.util.Collection;

public interface SignatureType {
   Class getDeclaredType();

   Collection getAnnotations();

   Type getGenericType();
}
