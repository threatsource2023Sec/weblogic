package com.oracle.wls.shaded.org.apache.xpath.functions;

import com.oracle.wls.shaded.org.apache.xpath.XPathContext;
import com.oracle.wls.shaded.org.apache.xpath.objects.XNumber;
import com.oracle.wls.shaded.org.apache.xpath.objects.XObject;
import javax.xml.transform.TransformerException;

public class FuncRound extends FunctionOneArg {
   static final long serialVersionUID = -7970583902573826611L;

   public XObject execute(XPathContext xctxt) throws TransformerException {
      XObject obj = this.m_arg0.execute(xctxt);
      double val = obj.num();
      if (val >= -0.5 && val < 0.0) {
         return new XNumber(-0.0);
      } else {
         return val == 0.0 ? new XNumber(val) : new XNumber(Math.floor(val + 0.5));
      }
   }
}
