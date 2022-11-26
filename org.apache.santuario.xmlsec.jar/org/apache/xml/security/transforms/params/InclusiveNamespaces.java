package org.apache.xml.security.transforms.params;

import java.util.Iterator;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import org.apache.xml.security.exceptions.XMLSecurityException;
import org.apache.xml.security.transforms.TransformParam;
import org.apache.xml.security.utils.ElementProxy;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class InclusiveNamespaces extends ElementProxy implements TransformParam {
   public static final String _TAG_EC_INCLUSIVENAMESPACES = "InclusiveNamespaces";
   public static final String _ATT_EC_PREFIXLIST = "PrefixList";
   public static final String ExclusiveCanonicalizationNamespace = "http://www.w3.org/2001/10/xml-exc-c14n#";

   public InclusiveNamespaces(Document doc, String prefixList) {
      this((Document)doc, (Set)prefixStr2Set(prefixList));
   }

   public InclusiveNamespaces(Document doc, Set prefixes) {
      super(doc);
      SortedSet prefixList = null;
      if (prefixes instanceof SortedSet) {
         prefixList = (SortedSet)prefixes;
      } else {
         prefixList = new TreeSet(prefixes);
      }

      StringBuilder sb = new StringBuilder();
      Iterator var5 = ((SortedSet)prefixList).iterator();

      while(var5.hasNext()) {
         String prefix = (String)var5.next();
         if ("xmlns".equals(prefix)) {
            sb.append("#default ");
         } else {
            sb.append(prefix);
            sb.append(" ");
         }
      }

      this.setLocalAttribute("PrefixList", sb.toString().trim());
   }

   public InclusiveNamespaces(Element element, String baseURI) throws XMLSecurityException {
      super(element, baseURI);
   }

   public String getInclusiveNamespaces() {
      return this.getLocalAttribute("PrefixList");
   }

   public static SortedSet prefixStr2Set(String inclusiveNamespaces) {
      SortedSet prefixes = new TreeSet();
      if (inclusiveNamespaces != null && inclusiveNamespaces.length() != 0) {
         String[] tokens = inclusiveNamespaces.split("\\s");
         String[] var3 = tokens;
         int var4 = tokens.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            String prefix = var3[var5];
            if (prefix.equals("#default")) {
               prefixes.add("xmlns");
            } else {
               prefixes.add(prefix);
            }
         }

         return prefixes;
      } else {
         return prefixes;
      }
   }

   public String getBaseNamespace() {
      return "http://www.w3.org/2001/10/xml-exc-c14n#";
   }

   public String getBaseLocalName() {
      return "InclusiveNamespaces";
   }
}
