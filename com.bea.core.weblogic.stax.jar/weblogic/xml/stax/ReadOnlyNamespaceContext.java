package weblogic.xml.stax;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import javax.xml.namespace.NamespaceContext;

public class ReadOnlyNamespaceContext implements NamespaceContext {
   Map internal;

   public ReadOnlyNamespaceContext(Map map) {
      this.internal = map;
   }

   public String getNamespaceURI(String prefix) {
      if (prefix == null) {
         throw new IllegalArgumentException("The prefix may not be null");
      } else {
         return (String)this.internal.get(prefix);
      }
   }

   public String getPrefix(String namespaceURI) {
      if (namespaceURI == null) {
         throw new IllegalArgumentException("The uri may not be null.");
      } else if ("".equals(namespaceURI)) {
         throw new IllegalArgumentException("The uri may not be theempty string.");
      } else if (!this.internal.containsValue(namespaceURI)) {
         return null;
      } else {
         Iterator i = this.internal.entrySet().iterator();

         Map.Entry e;
         do {
            if (!i.hasNext()) {
               return null;
            }

            e = (Map.Entry)i.next();
         } while(!e.getValue().equals(namespaceURI));

         return (String)e.getKey();
      }
   }

   public Iterator getPrefixes(String namespaceURI) {
      ArrayList list = new ArrayList();
      Iterator i = this.internal.entrySet().iterator();

      while(i.hasNext()) {
         Map.Entry e = (Map.Entry)i.next();
         if (e.getValue().equals(namespaceURI)) {
            list.add(e.getKey());
         }
      }

      return list.iterator();
   }
}
