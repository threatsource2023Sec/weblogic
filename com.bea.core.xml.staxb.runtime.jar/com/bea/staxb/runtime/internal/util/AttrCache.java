package com.bea.staxb.runtime.internal.util;

import java.util.HashMap;
import java.util.Map;
import javax.xml.namespace.QName;
import org.w3c.dom.Attr;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class AttrCache {
   private final Map keyMap;

   public AttrCache(Element n, QName attrName) {
      this.keyMap = this.buildKeyMap(n, attrName);
   }

   public Node lookup(String key) {
      return (Node)this.keyMap.get(key);
   }

   private Map buildKeyMap(Element root, QName attrName) {
      assert root != null;

      assert attrName != null;

      String att_lname = attrName.getLocalPart();
      String att_uri = attrName.getNamespaceURI();
      if (att_uri.length() == 0) {
         att_uri = null;
      }

      Map keymap = new HashMap();
      Node curr = root;
      boolean just_came_up = false;

      while(true) {
         if (!just_came_up && 1 == ((Node)curr).getNodeType()) {
            Element elem = (Element)curr;
            Attr found_att = elem.getAttributeNodeNS(att_uri, att_lname);
            if (found_att != null) {
               keymap.put(found_att.getValue(), elem);
            }
         }

         Node firstChild = just_came_up ? null : ((Node)curr).getFirstChild();
         if (firstChild == null) {
            Node nextSibling = ((Node)curr).getNextSibling();
            if (nextSibling == null) {
               curr = ((Node)curr).getParentNode();
               if (curr == root) {
                  return keymap;
               }

               just_came_up = true;
            } else {
               curr = nextSibling;
               just_came_up = false;
            }
         } else {
            curr = firstChild;
            just_came_up = false;
         }
      }
   }
}
