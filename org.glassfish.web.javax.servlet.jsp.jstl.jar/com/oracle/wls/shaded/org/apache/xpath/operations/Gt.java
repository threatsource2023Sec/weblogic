package com.oracle.wls.shaded.org.apache.xpath.operations;

import com.oracle.wls.shaded.org.apache.xpath.objects.XBoolean;
import com.oracle.wls.shaded.org.apache.xpath.objects.XObject;
import javax.xml.transform.TransformerException;

public class Gt extends Operation {
   static final long serialVersionUID = 8927078751014375950L;

   public XObject operate(XObject left, XObject right) throws TransformerException {
      return left.greaterThan(right) ? XBoolean.S_TRUE : XBoolean.S_FALSE;
   }
}
