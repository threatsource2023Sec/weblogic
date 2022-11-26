package javax.enterprise.event;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Observes {
   Reception notifyObserver() default Reception.ALWAYS;

   TransactionPhase during() default TransactionPhase.IN_PROGRESS;
}
