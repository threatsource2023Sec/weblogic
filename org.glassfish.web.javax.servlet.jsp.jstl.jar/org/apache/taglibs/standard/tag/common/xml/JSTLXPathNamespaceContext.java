package org.apache.taglibs.standard.tag.common.xml;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import javax.xml.namespace.NamespaceContext;

public class JSTLXPathNamespaceContext implements NamespaceContext {
   HashMap namespaces;

   public JSTLXPathNamespaceContext() {
      this.namespaces = new HashMap();
   }

   public JSTLXPathNamespaceContext(HashMap nses) {
      this.namespaces = nses;
   }

   public String getNamespaceURI(String prefix) throws IllegalArgumentException {
      if (prefix == null) {
         throw new IllegalArgumentException("Cannot get Namespace URI for null prefix");
      } else if (prefix.equals("xml")) {
         return "http://www.w3.org/XML/1998/namespace";
      } else if (prefix.equals("xmlns")) {
         return "http://www.w3.org/2000/xmlns/";
      } else {
         String namespaceURI = (String)this.namespaces.get(prefix);
         return namespaceURI != null ? namespaceURI : "";
      }
   }

   public String getPrefix(String namespaceURI) {
      if (namespaceURI == null) {
         throw new IllegalArgumentException("Cannot get prefix for null NamespaceURI");
      } else if (namespaceURI.equals("http://www.w3.org/XML/1998/namespace")) {
         return "xml";
      } else if (namespaceURI.equals("http://www.w3.org/2000/xmlns/")) {
         return "xmlns";
      } else {
         Iterator iter = this.namespaces.keySet().iterator();

         String value;
         do {
            if (!iter.hasNext()) {
               return null;
            }

            String key = (String)iter.next();
            value = (String)this.namespaces.get(key);
         } while(!value.equals(namespaceURI));

         return value;
      }
   }

   public Iterator getPrefixes(String namespaceURI) {
      if (namespaceURI == null) {
         throw new IllegalArgumentException("Cannot get prefix for null NamespaceURI");
      } else if (namespaceURI.equals("http://www.w3.org/XML/1998/namespace")) {
         return Arrays.asList("xml").iterator();
      } else if (namespaceURI.equals("http://www.w3.org/2000/xmlns/")) {
         return Arrays.asList("xmlns").iterator();
      } else {
         ArrayList prefixList = new ArrayList();
         Iterator iter = this.namespaces.keySet().iterator();

         while(iter.hasNext()) {
            String key = (String)iter.next();
            String value = (String)this.namespaces.get(key);
            if (value.equals(namespaceURI)) {
               prefixList.add(key);
            }
         }

         return prefixList.iterator();
      }
   }

   protected void addNamespace(String prefix, String uri) {
      this.namespaces.put(prefix, uri);
   }

   private static void p(String s) {
      System.out.println("[JSTLXPathNameContext] " + s);
   }
}
