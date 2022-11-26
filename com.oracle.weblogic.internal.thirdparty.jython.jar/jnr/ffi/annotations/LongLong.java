package jnr.ffi.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import jnr.ffi.TypeAlias;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER, ElementType.METHOD})
@TypeDefinition(
   alias = TypeAlias.int64_t
)
public @interface LongLong {
}
