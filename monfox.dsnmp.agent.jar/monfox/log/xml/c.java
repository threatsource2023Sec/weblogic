package monfox.log.xml;

import java.util.List;
import java.util.Vector;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

class c {
   public static void addTextChild(Element var0, String var1, String var2) {
      Document var3 = var0.getOwnerDocument();
      Element var4 = var3.createElement(var1);
      Text var5 = var3.createTextNode(var2);
      var4.appendChild(var5);
      var0.appendChild(var4);
   }

   public static void addText(Element var0, String var1) {
      Document var2 = var0.getOwnerDocument();
      Text var3 = var2.createTextNode(var1);
      var0.appendChild(var3);
   }

   public static String getAttribute(Element var0, String var1, String var2) {
      if (var0 == null) {
         return var2;
      } else {
         String var3 = var0.getAttribute(var1);
         return var3 == null ? var2 : var3;
      }
   }

   public static int getIntAttribute(Element var0, String var1, int var2) throws NumberFormatException {
      String var3 = var0.getAttribute(var1);
      if (var3 == null) {
         return var2;
      } else {
         return var3.trim().length() == 0 ? var2 : Integer.parseInt(var3);
      }
   }

   public static String getText(Element var0) {
      Node var1 = var0.getFirstChild();
      return var1 == null ? "" : var1.getNodeValue();
   }

   public static String getChildText(Element var0, String var1) {
      Element var2 = getChild(var0, var1);
      if (var2 == null) {
         return null;
      } else {
         Node var3 = var2.getFirstChild();
         return var3 == null ? "" : var3.getNodeValue();
      }
   }

   public static String getChildText(Element var0, String var1, String var2) {
      Element var3 = getChild(var0, var1);
      if (var3 == null) {
         return var2;
      } else {
         Node var4 = var3.getFirstChild();
         if (var4 == null) {
            return var2;
         } else {
            String var5 = var4.getNodeValue();
            return var5 == null ? var2 : var5;
         }
      }
   }

   public static Element getChild(Element var0, String var1) {
      NodeList var2 = var0.getChildNodes();
      int var3 = 0;

      while(var3 < var2.getLength()) {
         Node var4 = var2.item(var3);
         if (var4 instanceof Element && var4.getNodeName().equals(var1)) {
            return (Element)var4;
         }

         ++var3;
         if (XmlLoggerConfigurator.a) {
            break;
         }
      }

      return null;
   }

   public static List getChildren(Element var0, String var1) {
      Vector var2 = new Vector();
      NodeList var3 = var0.getChildNodes();
      int var4 = 0;

      while(var4 < var3.getLength()) {
         Node var5 = var3.item(var4);
         if (var5 instanceof Element && var5.getNodeName().equals(var1)) {
            var2.add(var5);
         }

         ++var4;
         if (XmlLoggerConfigurator.a) {
            break;
         }
      }

      return var2;
   }
}
