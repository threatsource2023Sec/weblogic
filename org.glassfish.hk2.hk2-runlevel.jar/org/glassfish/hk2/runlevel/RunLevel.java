package org.glassfish.hk2.runlevel;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.inject.Scope;
import org.glassfish.hk2.api.Metadata;
import org.jvnet.hk2.annotations.Contract;

@Scope
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE, ElementType.METHOD})
@Documented
@Inherited
@Contract
public @interface RunLevel {
   String RUNLEVEL_VAL_META_TAG = "runLevelValue";
   String RUNLEVEL_MODE_META_TAG = "runLevelMode";
   int RUNLEVEL_VAL_INITIAL = -2;
   int RUNLEVEL_VAL_IMMEDIATE = -1;
   int RUNLEVEL_MODE_NON_VALIDATING = 0;
   int RUNLEVEL_MODE_VALIDATING = 1;

   @Metadata("runLevelValue")
   int value() default 0;

   @Metadata("runLevelMode")
   int mode() default 1;
}
