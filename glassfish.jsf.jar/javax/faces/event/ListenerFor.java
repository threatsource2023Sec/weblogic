package javax.faces.event;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Inherited
@Repeatable(ListenersFor.class)
public @interface ListenerFor {
   Class systemEventClass();

   Class sourceClass() default Void.class;
}
