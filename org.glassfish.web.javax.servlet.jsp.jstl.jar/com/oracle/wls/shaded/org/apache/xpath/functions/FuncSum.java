package com.oracle.wls.shaded.org.apache.xpath.functions;

import com.oracle.wls.shaded.org.apache.xml.dtm.DTM;
import com.oracle.wls.shaded.org.apache.xml.dtm.DTMIterator;
import com.oracle.wls.shaded.org.apache.xml.utils.XMLString;
import com.oracle.wls.shaded.org.apache.xpath.XPathContext;
import com.oracle.wls.shaded.org.apache.xpath.objects.XNumber;
import com.oracle.wls.shaded.org.apache.xpath.objects.XObject;
import javax.xml.transform.TransformerException;

public class FuncSum extends FunctionOneArg {
   static final long serialVersionUID = -2719049259574677519L;

   public XObject execute(XPathContext xctxt) throws TransformerException {
      DTMIterator nodes = this.m_arg0.asIterator(xctxt, xctxt.getCurrentNode());
      double sum = 0.0;

      int pos;
      while(-1 != (pos = nodes.nextNode())) {
         DTM dtm = nodes.getDTM(pos);
         XMLString s = dtm.getStringValue(pos);
         if (null != s) {
            sum += s.toDouble();
         }
      }

      nodes.detach();
      return new XNumber(sum);
   }
}
