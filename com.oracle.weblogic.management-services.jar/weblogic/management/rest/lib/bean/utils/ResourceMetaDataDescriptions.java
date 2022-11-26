package weblogic.management.rest.lib.bean.utils;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import javax.servlet.http.HttpServletRequest;

public class ResourceMetaDataDescriptions {
   private Map bundles = new HashMap();
   private String className;
   private ClassLoader classLoader;

   public ResourceMetaDataDescriptions(String className, ClassLoader classLoader) {
      this.className = className;
      this.classLoader = classLoader;
   }

   public ResourceBundle bundle(HttpServletRequest request) throws Exception {
      Locale locale = request.getLocale();
      ResourceBundle bundle = (ResourceBundle)this.bundles.get(locale);
      if (bundle == null) {
         bundle = ResourceBundle.getBundle(this.className, locale, this.classLoader);
         this.bundles.put(locale, bundle);
      }

      return bundle;
   }
}
