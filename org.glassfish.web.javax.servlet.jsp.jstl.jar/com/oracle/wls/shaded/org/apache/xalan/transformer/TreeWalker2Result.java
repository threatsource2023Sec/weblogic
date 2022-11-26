package com.oracle.wls.shaded.org.apache.xalan.transformer;

import com.oracle.wls.shaded.org.apache.xalan.serialize.SerializerUtils;
import com.oracle.wls.shaded.org.apache.xml.dtm.DTM;
import com.oracle.wls.shaded.org.apache.xml.dtm.ref.DTMTreeWalker;
import com.oracle.wls.shaded.org.apache.xml.serializer.SerializationHandler;
import com.oracle.wls.shaded.org.apache.xpath.XPathContext;
import javax.xml.transform.TransformerException;
import org.xml.sax.SAXException;

public class TreeWalker2Result extends DTMTreeWalker {
   TransformerImpl m_transformer;
   SerializationHandler m_handler;
   int m_startNode;

   public TreeWalker2Result(TransformerImpl transformer, SerializationHandler handler) {
      super(handler, (DTM)null);
      this.m_transformer = transformer;
      this.m_handler = handler;
   }

   public void traverse(int pos) throws SAXException {
      this.m_dtm = this.m_transformer.getXPathContext().getDTM(pos);
      this.m_startNode = pos;
      super.traverse(pos);
   }

   protected void endNode(int node) throws SAXException {
      super.endNode(node);
      if (1 == this.m_dtm.getNodeType(node)) {
         this.m_transformer.getXPathContext().popCurrentNode();
      }

   }

   protected void startNode(int node) throws SAXException {
      XPathContext xcntxt = this.m_transformer.getXPathContext();

      try {
         if (1 == this.m_dtm.getNodeType(node)) {
            xcntxt.pushCurrentNode(node);
            if (this.m_startNode != node) {
               super.startNode(node);
            } else {
               String elemName = this.m_dtm.getNodeName(node);
               String localName = this.m_dtm.getLocalName(node);
               String namespace = this.m_dtm.getNamespaceURI(node);
               this.m_handler.startElement(namespace, localName, elemName);
               boolean hasNSDecls = false;
               DTM dtm = this.m_dtm;

               int attr;
               for(attr = dtm.getFirstNamespaceNode(node, true); -1 != attr; attr = dtm.getNextNamespaceNode(node, attr, true)) {
                  SerializerUtils.ensureNamespaceDeclDeclared(this.m_handler, dtm, attr);
               }

               for(attr = dtm.getFirstAttribute(node); -1 != attr; attr = dtm.getNextAttribute(attr)) {
                  SerializerUtils.addAttribute(this.m_handler, attr);
               }
            }
         } else {
            xcntxt.pushCurrentNode(node);
            super.startNode(node);
            xcntxt.popCurrentNode();
         }

      } catch (TransformerException var9) {
         throw new SAXException(var9);
      }
   }
}
