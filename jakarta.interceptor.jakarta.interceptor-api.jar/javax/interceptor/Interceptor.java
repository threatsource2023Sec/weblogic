package javax.interceptor;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
public @interface Interceptor {
   public static class Priority {
      public static final int PLATFORM_BEFORE = 0;
      public static final int LIBRARY_BEFORE = 1000;
      public static final int APPLICATION = 2000;
      public static final int LIBRARY_AFTER = 3000;
      public static final int PLATFORM_AFTER = 4000;

      private Priority() {
      }
   }
}
