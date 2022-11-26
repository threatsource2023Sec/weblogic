package org.apache.xml.security.utils;

import javax.xml.transform.TransformerException;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public interface XPathAPI {
   NodeList selectNodeList(Node var1, Node var2, String var3, Node var4) throws TransformerException;

   boolean evaluate(Node var1, Node var2, String var3, Node var4) throws TransformerException;

   void clear();
}
