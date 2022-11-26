package com.oracle.wls.shaded.org.apache.xpath.operations;

import com.oracle.wls.shaded.org.apache.xml.dtm.DTMManager;
import com.oracle.wls.shaded.org.apache.xpath.Expression;
import com.oracle.wls.shaded.org.apache.xpath.XPathContext;
import com.oracle.wls.shaded.org.apache.xpath.objects.XNodeSet;
import com.oracle.wls.shaded.org.apache.xpath.objects.XObject;
import javax.xml.transform.TransformerException;

public class VariableSafeAbsRef extends Variable {
   static final long serialVersionUID = -9174661990819967452L;

   public XObject execute(XPathContext xctxt, boolean destructiveOK) throws TransformerException {
      XNodeSet xns = (XNodeSet)super.execute(xctxt, destructiveOK);
      DTMManager dtmMgr = xctxt.getDTMManager();
      int context = xctxt.getContextNode();
      if (dtmMgr.getDTM(xns.getRoot()).getDocument() != dtmMgr.getDTM(context).getDocument()) {
         Expression expr = (Expression)xns.getContainedIter();
         xns = (XNodeSet)expr.asIterator(xctxt, context);
      }

      return xns;
   }
}
