package weblogic.apache.org.apache.velocity.anakia;

import com.werken.xpath.XPath;
import java.util.Map;
import java.util.WeakHashMap;

class XPathCache {
   private static final Map XPATH_CACHE = new WeakHashMap();

   private XPathCache() {
   }

   static XPath getXPath(String xpathString) {
      XPath xpath = null;
      Map var2 = XPATH_CACHE;
      synchronized(var2) {
         xpath = (XPath)XPATH_CACHE.get(xpathString);
         if (xpath == null) {
            xpath = new XPath(xpathString);
            XPATH_CACHE.put(xpathString, xpath);
         }

         return xpath;
      }
   }
}
