package com.oracle.wls.shaded.org.apache.xpath.operations;

import com.oracle.wls.shaded.org.apache.xpath.XPathContext;
import com.oracle.wls.shaded.org.apache.xpath.objects.XNumber;
import com.oracle.wls.shaded.org.apache.xpath.objects.XObject;
import javax.xml.transform.TransformerException;

public class Neg extends UnaryOperation {
   static final long serialVersionUID = -6280607702375702291L;

   public XObject operate(XObject right) throws TransformerException {
      return new XNumber(-right.num());
   }

   public double num(XPathContext xctxt) throws TransformerException {
      return -this.m_right.num(xctxt);
   }
}
