package weblogic.metadata.management;

import java.util.Collections;
import java.util.Map;
import weblogic.application.ApplicationAccess;
import weblogic.j2ee.descriptor.wl.AnnotationOverridesBean;

public class AnnotationOverridesFinder {
   public static final String ROOT_MODULE_URI = "__WLS ROOT MODULE URI__";
   static final String APP_PARM_KEY = "weblogic.metadata.management.AnnotationOverrideDescriptors";
   static final String APP_VERSION_KEY = "weblogic.metadata.management.AnnotationOverrideDescriptorsVersionID";

   public static final Map getAnnotationOverrideDescriptors() {
      Map descriptorsByUri = getDescriptors();
      return descriptorsByUri == null ? null : Collections.unmodifiableMap(descriptorsByUri);
   }

   public static final AnnotationOverridesBean getAnnotationOverrideDescriptor() {
      String currentModuleName = ApplicationAccess.getApplicationAccess().getCurrentModuleName();
      return getAnnotationOverrideDescriptor(currentModuleName);
   }

   public static final AnnotationOverridesBean getAnnotationOverrideDescriptor(String uri) {
      Map descriptorsByUri = getDescriptors();
      return descriptorsByUri == null ? null : (AnnotationOverridesBean)descriptorsByUri.get(uri);
   }

   public static final AnnotationOverridesBean getAppScopedAnnotationOverride() {
      return getAnnotationOverrideDescriptor("__WLS ROOT MODULE URI__");
   }

   public static final long getCurrentOverrideVersion() {
      Long version = (Long)ApplicationAccess.getApplicationAccess().getCurrentApplicationContext().getApplicationParameters().get("weblogic.metadata.management.AnnotationOverrideDescriptorsVersionID");
      return version == null ? 0L : version;
   }

   private static Map getDescriptors() {
      Map descriptors = (Map)ApplicationAccess.getApplicationAccess().getCurrentApplicationContext().getApplicationParameters().get("weblogic.metadata.management.AnnotationOverrideDescriptors");
      return descriptors;
   }
}
