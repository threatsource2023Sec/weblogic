package com.oracle.wls.shaded.org.apache.xpath.functions;

import com.oracle.wls.shaded.org.apache.xalan.res.XSLMessages;
import com.oracle.wls.shaded.org.apache.xpath.XPathContext;
import com.oracle.wls.shaded.org.apache.xpath.axes.LocPathIterator;
import com.oracle.wls.shaded.org.apache.xpath.axes.PredicatedNodeTest;
import com.oracle.wls.shaded.org.apache.xpath.axes.SubContextList;
import com.oracle.wls.shaded.org.apache.xpath.objects.XNodeSet;
import com.oracle.wls.shaded.org.apache.xpath.objects.XObject;
import com.oracle.wls.shaded.org.apache.xpath.patterns.StepPattern;
import java.util.Vector;
import javax.xml.transform.TransformerException;

public class FuncCurrent extends Function {
   static final long serialVersionUID = 5715316804877715008L;

   public XObject execute(XPathContext xctxt) throws TransformerException {
      SubContextList subContextList = xctxt.getCurrentNodeList();
      int currentNode = -1;
      if (null != subContextList) {
         if (subContextList instanceof PredicatedNodeTest) {
            LocPathIterator iter = ((PredicatedNodeTest)subContextList).getLocPathIterator();
            currentNode = iter.getCurrentContextNode();
         } else if (subContextList instanceof StepPattern) {
            throw new RuntimeException(XSLMessages.createMessage("ER_PROCESSOR_ERROR", (Object[])null));
         }
      } else {
         currentNode = xctxt.getContextNode();
      }

      return new XNodeSet(currentNode, xctxt.getDTMManager());
   }

   public void fixupVariables(Vector vars, int globalsSize) {
   }
}
