package com.oracle.wls.shaded.org.apache.xpath.operations;

import com.oracle.wls.shaded.org.apache.xpath.objects.XBoolean;
import com.oracle.wls.shaded.org.apache.xpath.objects.XObject;
import javax.xml.transform.TransformerException;

public class NotEquals extends Operation {
   static final long serialVersionUID = -7869072863070586900L;

   public XObject operate(XObject left, XObject right) throws TransformerException {
      return left.notEquals(right) ? XBoolean.S_TRUE : XBoolean.S_FALSE;
   }
}
