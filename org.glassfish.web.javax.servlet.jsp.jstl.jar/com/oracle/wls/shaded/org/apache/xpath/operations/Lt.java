package com.oracle.wls.shaded.org.apache.xpath.operations;

import com.oracle.wls.shaded.org.apache.xpath.objects.XBoolean;
import com.oracle.wls.shaded.org.apache.xpath.objects.XObject;
import javax.xml.transform.TransformerException;

public class Lt extends Operation {
   static final long serialVersionUID = 3388420509289359422L;

   public XObject operate(XObject left, XObject right) throws TransformerException {
      return left.lessThan(right) ? XBoolean.S_TRUE : XBoolean.S_FALSE;
   }
}
