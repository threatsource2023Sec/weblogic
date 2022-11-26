package com.oracle.wls.shaded.org.apache.xpath.functions;

import com.oracle.wls.shaded.org.apache.xpath.XPathContext;
import com.oracle.wls.shaded.org.apache.xpath.objects.XNumber;
import com.oracle.wls.shaded.org.apache.xpath.objects.XObject;
import javax.xml.transform.TransformerException;

public class FuncFloor extends FunctionOneArg {
   static final long serialVersionUID = 2326752233236309265L;

   public XObject execute(XPathContext xctxt) throws TransformerException {
      return new XNumber(Math.floor(this.m_arg0.execute(xctxt).num()));
   }
}
