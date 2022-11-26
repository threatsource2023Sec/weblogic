package com.oracle.wls.shaded.org.apache.xpath.functions;

import com.oracle.wls.shaded.org.apache.xml.dtm.DTMIterator;
import com.oracle.wls.shaded.org.apache.xpath.XPathContext;
import com.oracle.wls.shaded.org.apache.xpath.objects.XNumber;
import com.oracle.wls.shaded.org.apache.xpath.objects.XObject;
import javax.xml.transform.TransformerException;

public class FuncCount extends FunctionOneArg {
   static final long serialVersionUID = -7116225100474153751L;

   public XObject execute(XPathContext xctxt) throws TransformerException {
      DTMIterator nl = this.m_arg0.asIterator(xctxt, xctxt.getCurrentNode());
      int i = nl.getLength();
      nl.detach();
      return new XNumber((double)i);
   }
}
