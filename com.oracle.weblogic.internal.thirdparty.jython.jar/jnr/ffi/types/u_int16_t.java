package jnr.ffi.types;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import jnr.ffi.TypeAlias;
import jnr.ffi.annotations.TypeDefinition;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER, ElementType.METHOD})
@TypeDefinition(
   alias = TypeAlias.u_int16_t
)
public @interface u_int16_t {
}
