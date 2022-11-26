package com.oracle.wls.shaded.org.apache.xalan.templates;

import com.oracle.wls.shaded.org.apache.xalan.serialize.SerializerUtils;
import com.oracle.wls.shaded.org.apache.xalan.transformer.ClonerToResultTree;
import com.oracle.wls.shaded.org.apache.xalan.transformer.TransformerImpl;
import com.oracle.wls.shaded.org.apache.xml.dtm.DTM;
import com.oracle.wls.shaded.org.apache.xml.serializer.SerializationHandler;
import com.oracle.wls.shaded.org.apache.xpath.XPathContext;
import javax.xml.transform.TransformerException;
import org.xml.sax.SAXException;

public class ElemCopy extends ElemUse {
   static final long serialVersionUID = 5478580783896941384L;

   public int getXSLToken() {
      return 9;
   }

   public String getNodeName() {
      return "copy";
   }

   public void execute(TransformerImpl transformer) throws TransformerException {
      XPathContext xctxt = transformer.getXPathContext();

      try {
         int sourceNode = xctxt.getCurrentNode();
         xctxt.pushCurrentNode(sourceNode);
         DTM dtm = xctxt.getDTM(sourceNode);
         short nodeType = dtm.getNodeType(sourceNode);
         if (9 != nodeType && 11 != nodeType) {
            SerializationHandler rthandler = transformer.getSerializationHandler();
            if (transformer.getDebug()) {
               transformer.getTraceManager().fireTraceEvent((ElemTemplateElement)this);
            }

            ClonerToResultTree.cloneToResultTree(sourceNode, nodeType, dtm, rthandler, false);
            if (1 == nodeType) {
               super.execute(transformer);
               SerializerUtils.processNSDecls(rthandler, sourceNode, nodeType, dtm);
               transformer.executeChildTemplates(this, true);
               String ns = dtm.getNamespaceURI(sourceNode);
               String localName = dtm.getLocalName(sourceNode);
               transformer.getResultTreeHandler().endElement(ns, localName, dtm.getNodeName(sourceNode));
            }

            if (transformer.getDebug()) {
               transformer.getTraceManager().fireTraceEndEvent((ElemTemplateElement)this);
            }
         } else {
            if (transformer.getDebug()) {
               transformer.getTraceManager().fireTraceEvent((ElemTemplateElement)this);
            }

            super.execute(transformer);
            transformer.executeChildTemplates(this, true);
            if (transformer.getDebug()) {
               transformer.getTraceManager().fireTraceEndEvent((ElemTemplateElement)this);
            }
         }
      } catch (SAXException var13) {
         throw new TransformerException(var13);
      } finally {
         xctxt.popCurrentNode();
      }

   }
}
