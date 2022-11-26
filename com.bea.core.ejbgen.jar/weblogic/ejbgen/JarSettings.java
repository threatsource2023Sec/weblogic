package weblogic.ejbgen;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.CLASS)
@Target({ElementType.TYPE})
public @interface JarSettings {
   CreateTables createTables() default JarSettings.CreateTables.UNSPECIFIED;

   String ejbClientJar() default "UNSPECIFIED";

   Constants.Bool enableBeanClassRedeploy() default Constants.Bool.UNSPECIFIED;

   String disableWarning() default "UNSPECIFIED";

   public static enum CreateTables {
      CREATE_ONLY,
      DISABLED,
      DROP_AND_CREATE,
      DROP_AND_CREATE_ALWAYS,
      ALTER_OR_CREATE,
      UNSPECIFIED;
   }
}
