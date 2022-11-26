package weblogic.management.patching;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class ExtensionProperties implements Serializable {
   private static final long serialVersionUID = 1L;
   public static final String EXTENSION_JAR_LOCATION_KEY = "extensionJar";
   public static final String EXTENSION_PARAMS_KEY = "extensionParameters";
   public static final String EXTENSION_SEPARATOR = ":";
   public static final String EXTENSION_PARAM_SEPARATOR = ",";
   public static final String EXTENSION_KEY_VAL_SEPARATOR = "=";
   Map extensionParameters;
   String extensionJarLocation;

   public ExtensionProperties() {
      this("");
   }

   public ExtensionProperties(String extensionJarLocation) {
      this.extensionJarLocation = extensionJarLocation;
      this.extensionParameters = new HashMap();
   }

   public String getExtensionJarLocation() {
      return this.extensionJarLocation;
   }

   public Map getExtensionParameters() {
      return this.extensionParameters;
   }

   public void addParameter(String paramKey, String paramValue) {
      this.extensionParameters.put(paramKey, paramValue);
   }

   public boolean hasParameter(String paramKey) {
      return this.extensionParameters.containsKey(paramKey);
   }

   public String getParameterValue(String paramKey) {
      return (String)this.extensionParameters.get(paramKey);
   }

   public void removeParameter(String paramKey) {
      this.extensionParameters.remove(paramKey);
   }

   public String toString() {
      return this.extensionJarLocation + "," + this.extensionParameters.toString();
   }
}
