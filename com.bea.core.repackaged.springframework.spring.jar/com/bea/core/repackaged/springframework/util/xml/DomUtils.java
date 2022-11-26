package com.bea.core.repackaged.springframework.util.xml;

import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import org.w3c.dom.CharacterData;
import org.w3c.dom.Comment;
import org.w3c.dom.Element;
import org.w3c.dom.EntityReference;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.ContentHandler;

public abstract class DomUtils {
   public static List getChildElementsByTagName(Element ele, String... childEleNames) {
      Assert.notNull(ele, (String)"Element must not be null");
      Assert.notNull(childEleNames, (String)"Element names collection must not be null");
      List childEleNameList = Arrays.asList(childEleNames);
      NodeList nl = ele.getChildNodes();
      List childEles = new ArrayList();

      for(int i = 0; i < nl.getLength(); ++i) {
         Node node = nl.item(i);
         if (node instanceof Element && nodeNameMatch(node, (Collection)childEleNameList)) {
            childEles.add((Element)node);
         }
      }

      return childEles;
   }

   public static List getChildElementsByTagName(Element ele, String childEleName) {
      return getChildElementsByTagName(ele, childEleName);
   }

   @Nullable
   public static Element getChildElementByTagName(Element ele, String childEleName) {
      Assert.notNull(ele, (String)"Element must not be null");
      Assert.notNull(childEleName, (String)"Element name must not be null");
      NodeList nl = ele.getChildNodes();

      for(int i = 0; i < nl.getLength(); ++i) {
         Node node = nl.item(i);
         if (node instanceof Element && nodeNameMatch(node, childEleName)) {
            return (Element)node;
         }
      }

      return null;
   }

   @Nullable
   public static String getChildElementValueByTagName(Element ele, String childEleName) {
      Element child = getChildElementByTagName(ele, childEleName);
      return child != null ? getTextValue(child) : null;
   }

   public static List getChildElements(Element ele) {
      Assert.notNull(ele, (String)"Element must not be null");
      NodeList nl = ele.getChildNodes();
      List childEles = new ArrayList();

      for(int i = 0; i < nl.getLength(); ++i) {
         Node node = nl.item(i);
         if (node instanceof Element) {
            childEles.add((Element)node);
         }
      }

      return childEles;
   }

   public static String getTextValue(Element valueEle) {
      Assert.notNull(valueEle, (String)"Element must not be null");
      StringBuilder sb = new StringBuilder();
      NodeList nl = valueEle.getChildNodes();

      for(int i = 0; i < nl.getLength(); ++i) {
         Node item = nl.item(i);
         if (item instanceof CharacterData && !(item instanceof Comment) || item instanceof EntityReference) {
            sb.append(item.getNodeValue());
         }
      }

      return sb.toString();
   }

   public static boolean nodeNameEquals(Node node, String desiredName) {
      Assert.notNull(node, (String)"Node must not be null");
      Assert.notNull(desiredName, (String)"Desired name must not be null");
      return nodeNameMatch(node, desiredName);
   }

   public static ContentHandler createContentHandler(Node node) {
      return new DomContentHandler(node);
   }

   private static boolean nodeNameMatch(Node node, String desiredName) {
      return desiredName.equals(node.getNodeName()) || desiredName.equals(node.getLocalName());
   }

   private static boolean nodeNameMatch(Node node, Collection desiredNames) {
      return desiredNames.contains(node.getNodeName()) || desiredNames.contains(node.getLocalName());
   }
}
