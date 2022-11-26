package com.oracle.wls.shaded.org.apache.xpath.functions;

import com.oracle.wls.shaded.org.apache.xalan.res.XSLMessages;
import com.oracle.wls.shaded.org.apache.xml.dtm.DTM;
import com.oracle.wls.shaded.org.apache.xml.utils.XMLString;
import com.oracle.wls.shaded.org.apache.xpath.XPathContext;
import com.oracle.wls.shaded.org.apache.xpath.objects.XString;
import javax.xml.transform.TransformerException;

public class FunctionDef1Arg extends FunctionOneArg {
   static final long serialVersionUID = 2325189412814149264L;

   protected int getArg0AsNode(XPathContext xctxt) throws TransformerException {
      return null == this.m_arg0 ? xctxt.getCurrentNode() : this.m_arg0.asNode(xctxt);
   }

   public boolean Arg0IsNodesetExpr() {
      return null == this.m_arg0 ? true : this.m_arg0.isNodesetExpr();
   }

   protected XMLString getArg0AsString(XPathContext xctxt) throws TransformerException {
      if (null == this.m_arg0) {
         int currentNode = xctxt.getCurrentNode();
         if (-1 == currentNode) {
            return XString.EMPTYSTRING;
         } else {
            DTM dtm = xctxt.getDTM(currentNode);
            return dtm.getStringValue(currentNode);
         }
      } else {
         return this.m_arg0.execute(xctxt).xstr();
      }
   }

   protected double getArg0AsNumber(XPathContext xctxt) throws TransformerException {
      if (null == this.m_arg0) {
         int currentNode = xctxt.getCurrentNode();
         if (-1 == currentNode) {
            return 0.0;
         } else {
            DTM dtm = xctxt.getDTM(currentNode);
            XMLString str = dtm.getStringValue(currentNode);
            return str.toDouble();
         }
      } else {
         return this.m_arg0.execute(xctxt).num();
      }
   }

   public void checkNumberArgs(int argNum) throws WrongNumberArgsException {
      if (argNum > 1) {
         this.reportWrongNumberArgs();
      }

   }

   protected void reportWrongNumberArgs() throws WrongNumberArgsException {
      throw new WrongNumberArgsException(XSLMessages.createXPATHMessage("ER_ZERO_OR_ONE", (Object[])null));
   }

   public boolean canTraverseOutsideSubtree() {
      return null == this.m_arg0 ? false : super.canTraverseOutsideSubtree();
   }
}
