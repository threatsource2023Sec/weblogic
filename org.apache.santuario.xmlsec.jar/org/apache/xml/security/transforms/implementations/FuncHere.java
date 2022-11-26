package org.apache.xml.security.transforms.implementations;

import java.util.Vector;
import javax.xml.transform.TransformerException;
import org.apache.xml.dtm.DTM;
import org.apache.xml.security.utils.I18n;
import org.apache.xml.security.utils.XMLUtils;
import org.apache.xpath.NodeSetDTM;
import org.apache.xpath.XPathContext;
import org.apache.xpath.functions.Function;
import org.apache.xpath.objects.XNodeSet;
import org.apache.xpath.objects.XObject;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

public class FuncHere extends Function {
   private static final long serialVersionUID = 1L;

   public XObject execute(XPathContext xctxt) throws TransformerException {
      Node xpathOwnerNode = (Node)xctxt.getOwnerObject();
      if (xpathOwnerNode == null) {
         return null;
      } else {
         int xpathOwnerNodeDTM = xctxt.getDTMHandleFromNode(xpathOwnerNode);
         int currentNode = xctxt.getCurrentNode();
         DTM dtm = xctxt.getDTM(currentNode);
         int docContext = dtm.getDocument();
         if (-1 == docContext) {
            this.error(xctxt, "ER_CONTEXT_HAS_NO_OWNERDOC", (Object[])null);
         }

         Document currentDoc = XMLUtils.getOwnerDocument(dtm.getNode(currentNode));
         Document xpathOwnerDoc = XMLUtils.getOwnerDocument(xpathOwnerNode);
         if (currentDoc != xpathOwnerDoc) {
            throw new TransformerException(I18n.translate("xpath.funcHere.documentsDiffer"));
         } else {
            XNodeSet nodes = new XNodeSet(xctxt.getDTMManager());
            NodeSetDTM nodeSet = nodes.mutableNodeset();
            int hereNode = true;
            switch (dtm.getNodeType(xpathOwnerNodeDTM)) {
               case 2:
               case 7:
                  nodeSet.addNode(xpathOwnerNodeDTM);
                  break;
               case 3:
                  int hereNode = dtm.getParent(xpathOwnerNodeDTM);
                  nodeSet.addNode(hereNode);
            }

            nodeSet.detach();
            return nodes;
         }
      }
   }

   public void fixupVariables(Vector vars, int globalsSize) {
   }
}
