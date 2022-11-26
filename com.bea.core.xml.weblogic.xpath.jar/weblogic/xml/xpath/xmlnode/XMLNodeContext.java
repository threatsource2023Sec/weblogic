package weblogic.xml.xpath.xmlnode;

import weblogic.xml.xmlnode.XMLNode;
import weblogic.xml.xpath.common.Context;

public final class XMLNodeContext extends Context {
   public XMLNode documentRoot;

   public static Context create(XMLNode documentElement) {
      XMLNodeContext out = new XMLNodeContext();
      out.documentRoot = new XMLNode();
      out.documentRoot.addChild(documentElement);
      out.node = out.documentRoot;
      return out;
   }

   private XMLNodeContext() {
   }
}
