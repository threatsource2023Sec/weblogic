package javax.faces.component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Inherited
public @interface FacesComponent {
   String NAMESPACE = "http://xmlns.jcp.org/jsf/component";

   String value() default "";

   boolean createTag() default false;

   String tagName() default "";

   String namespace() default "http://xmlns.jcp.org/jsf/component";
}
