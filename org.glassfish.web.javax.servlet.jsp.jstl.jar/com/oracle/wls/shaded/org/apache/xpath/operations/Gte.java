package com.oracle.wls.shaded.org.apache.xpath.operations;

import com.oracle.wls.shaded.org.apache.xpath.objects.XBoolean;
import com.oracle.wls.shaded.org.apache.xpath.objects.XObject;
import javax.xml.transform.TransformerException;

public class Gte extends Operation {
   static final long serialVersionUID = 9142945909906680220L;

   public XObject operate(XObject left, XObject right) throws TransformerException {
      return left.greaterThanOrEqual(right) ? XBoolean.S_TRUE : XBoolean.S_FALSE;
   }
}
