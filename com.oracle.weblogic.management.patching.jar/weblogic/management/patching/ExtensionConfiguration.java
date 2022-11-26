package weblogic.management.patching;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ExtensionConfiguration {
   public static final String EXTENSION_POINT_NAME_KEY = "extensionPoint";
   public static final String EXTENSION_POINT_CLASS_KEY = "extensionClass";
   public static final String EXTENSION_PARAMS_KEY = "extensionParameters";
   public static final String EXTENSION_PARAM_KEY_VAL_SEPARATOR = "=";
   public static final String EXTENSION_PARAM_SEPARATOR = ",";
   public ExtensionPoint extensionPoint;
   public String extensionPointClassName;
   public Map extensionPointParams;

   public ExtensionConfiguration(String extensionPointName, String extensionPointClass) {
      this.extensionPoint = ExtensionPoint.get(extensionPointName);
      this.extensionPointClassName = extensionPointClass;
      this.extensionPointParams = new HashMap();
   }

   public ExtensionPoint getExtensionPoint() {
      return this.extensionPoint;
   }

   public String getExtensionPointClassName() {
      return this.extensionPointClassName;
   }

   public void addParameter(String paramKey, String paramValue) {
      this.extensionPointParams.put(paramKey, paramValue);
   }

   public boolean hasParameter(String paramKey) {
      return this.extensionPointParams.containsKey(paramKey);
   }

   public String getParameterValue(String paramKey) {
      return (String)this.extensionPointParams.get(paramKey);
   }

   public void removeParameter(String paramKey) {
      this.extensionPointParams.remove(paramKey);
   }

   public Map getExtensionPointParams() {
      return this.extensionPointParams;
   }

   public void overrideExtensionParams(Map newParams) {
      Iterator var2 = newParams.keySet().iterator();

      while(var2.hasNext()) {
         String paramKey = (String)var2.next();
         if (this.extensionPointParams.containsKey(paramKey)) {
            this.extensionPointParams.put(paramKey, newParams.get(paramKey));
         }
      }

   }

   public String toString() {
      return "Extension{extensionPoint=" + this.extensionPoint + ", extensionPointClassName='" + this.extensionPointClassName + '\'' + ", extensionPointParams=" + this.extensionPointParams + '}';
   }
}
