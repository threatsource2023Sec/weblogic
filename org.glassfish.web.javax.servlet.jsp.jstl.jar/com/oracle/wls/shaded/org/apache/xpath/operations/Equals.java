package com.oracle.wls.shaded.org.apache.xpath.operations;

import com.oracle.wls.shaded.org.apache.xpath.XPathContext;
import com.oracle.wls.shaded.org.apache.xpath.objects.XBoolean;
import com.oracle.wls.shaded.org.apache.xpath.objects.XObject;
import javax.xml.transform.TransformerException;

public class Equals extends Operation {
   static final long serialVersionUID = -2658315633903426134L;

   public XObject operate(XObject left, XObject right) throws TransformerException {
      return left.equals(right) ? XBoolean.S_TRUE : XBoolean.S_FALSE;
   }

   public boolean bool(XPathContext xctxt) throws TransformerException {
      XObject left = this.m_left.execute(xctxt, true);
      XObject right = this.m_right.execute(xctxt, true);
      boolean result = left.equals(right);
      left.detach();
      right.detach();
      return result;
   }
}
