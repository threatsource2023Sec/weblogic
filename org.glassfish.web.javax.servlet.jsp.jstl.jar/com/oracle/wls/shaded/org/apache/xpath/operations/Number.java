package com.oracle.wls.shaded.org.apache.xpath.operations;

import com.oracle.wls.shaded.org.apache.xpath.XPathContext;
import com.oracle.wls.shaded.org.apache.xpath.objects.XNumber;
import com.oracle.wls.shaded.org.apache.xpath.objects.XObject;
import javax.xml.transform.TransformerException;

public class Number extends UnaryOperation {
   static final long serialVersionUID = 7196954482871619765L;

   public XObject operate(XObject right) throws TransformerException {
      return (XObject)(2 == right.getType() ? right : new XNumber(right.num()));
   }

   public double num(XPathContext xctxt) throws TransformerException {
      return this.m_right.num(xctxt);
   }
}
