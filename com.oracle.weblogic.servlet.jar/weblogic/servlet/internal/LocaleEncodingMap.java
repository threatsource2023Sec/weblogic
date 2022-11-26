package weblogic.servlet.internal;

import java.util.HashMap;
import java.util.Locale;
import weblogic.j2ee.descriptor.LocaleEncodingMappingBean;
import weblogic.j2ee.descriptor.LocaleEncodingMappingListBean;

final class LocaleEncodingMap {
   private final HashMap mapping = new HashMap();

   String getJavaCharset(Locale loc) {
      if (loc == null) {
         return null;
      } else {
         String enc = (String)this.mapping.get(loc.toString());
         if (enc != null) {
            return enc;
         } else {
            enc = (String)this.mapping.get(loc.getLanguage());
            return enc != null ? enc : weblogic.utils.CharsetMap.getJavaFromLocale(loc);
         }
      }
   }

   void registerLocaleEncodingMap(LocaleEncodingMappingListBean[] list) {
      if (list != null && list.length >= 1) {
         for(int i = 0; i < list.length; ++i) {
            this.registerLocaleEncodingMap(list[i].getLocaleEncodingMappings());
         }

      }
   }

   private void registerLocaleEncodingMap(LocaleEncodingMappingBean[] mappings) {
      if (mappings != null && mappings.length >= 1) {
         for(int i = 0; i < mappings.length; ++i) {
            LocaleEncodingMappingBean map = mappings[i];
            this.mapping.put(map.getLocale(), map.getEncoding());
         }

      }
   }
}
