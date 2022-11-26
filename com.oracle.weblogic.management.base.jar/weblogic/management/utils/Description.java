package weblogic.management.utils;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.management.DescriptorKey;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD, ElementType.PARAMETER})
public @interface Description {
   String DESCRIPTION_RESOURCE_KEY = "descriptionResourceKey";
   String DESCRIPTION_DISPLAY_NAME_KEY = "descriptionDisplayNameKey";
   String DESCRIPTION_RESOURCE_BUNDLE_BASE_NAME = "descriptionResourceBundleBaseName";

   @DescriptorKey("descriptionResourceKey")
   String resourceKey();

   @DescriptorKey("descriptionDisplayNameKey")
   String displayNameKey() default "";

   @DescriptorKey("descriptionResourceBundleBaseName")
   String resourceBundleBaseName() default "";
}
