package com.oracle.wls.shaded.org.apache.xpath.operations;

import com.oracle.wls.shaded.org.apache.xpath.objects.XObject;
import com.oracle.wls.shaded.org.apache.xpath.objects.XString;
import javax.xml.transform.TransformerException;

public class String extends UnaryOperation {
   static final long serialVersionUID = 2973374377453022888L;

   public XObject operate(XObject right) throws TransformerException {
      return (XString)right.xstr();
   }
}
