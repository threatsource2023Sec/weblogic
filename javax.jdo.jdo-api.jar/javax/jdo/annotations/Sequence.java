package javax.jdo.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Sequence {
   String name();

   SequenceStrategy strategy();

   String datastoreSequence() default "";

   Class factoryClass() default void.class;

   Extension[] extensions() default {};

   int initialValue() default 1;

   int allocationSize() default 50;
}