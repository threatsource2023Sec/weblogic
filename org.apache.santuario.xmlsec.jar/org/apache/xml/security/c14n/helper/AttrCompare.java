package org.apache.xml.security.c14n.helper;

import java.io.Serializable;
import java.util.Comparator;
import org.w3c.dom.Attr;

public class AttrCompare implements Comparator, Serializable {
   private static final long serialVersionUID = -7113259629930576230L;
   private static final int ATTR0_BEFORE_ATTR1 = -1;
   private static final int ATTR1_BEFORE_ATTR0 = 1;
   private static final String XMLNS = "http://www.w3.org/2000/xmlns/";

   public int compare(Attr attr0, Attr attr1) {
      String namespaceURI0 = attr0.getNamespaceURI();
      String namespaceURI1 = attr1.getNamespaceURI();
      boolean isNamespaceAttr0 = "http://www.w3.org/2000/xmlns/".equals(namespaceURI0);
      boolean isNamespaceAttr1 = "http://www.w3.org/2000/xmlns/".equals(namespaceURI1);
      String name1;
      String name0;
      if (isNamespaceAttr0) {
         if (isNamespaceAttr1) {
            name0 = attr0.getLocalName();
            name1 = attr1.getLocalName();
            if ("xmlns".equals(name0)) {
               name0 = "";
            }

            if ("xmlns".equals(name1)) {
               name1 = "";
            }

            return name0.compareTo(name1);
         } else {
            return -1;
         }
      } else if (isNamespaceAttr1) {
         return 1;
      } else if (namespaceURI0 == null) {
         if (namespaceURI1 == null) {
            name0 = attr0.getName();
            name1 = attr1.getName();
            return name0.compareTo(name1);
         } else {
            return -1;
         }
      } else if (namespaceURI1 == null) {
         return 1;
      } else {
         int a = namespaceURI0.compareTo(namespaceURI1);
         return a != 0 ? a : attr0.getLocalName().compareTo(attr1.getLocalName());
      }
   }
}
