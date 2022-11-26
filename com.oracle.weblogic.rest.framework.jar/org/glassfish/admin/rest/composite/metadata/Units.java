package org.glassfish.admin.rest.composite.metadata;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.inject.Qualifier;

@Qualifier
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface Units {
   String SECONDS = "seconds";
   String MINUTES = "minutes";
   String HOURS = "hours";
   String MILLISECONDS = "milliseconds";
   String BYTES = "bytes";
   String KILOBYTES = "kilobytes";
   String MEGABYTES = "megabytes";
   String DATE = "date";
   String PERCENT = "percent";

   String units();
}
