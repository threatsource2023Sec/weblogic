package com.oracle.wls.shaded.org.apache.xpath.operations;

import com.oracle.wls.shaded.org.apache.xpath.objects.XNumber;
import com.oracle.wls.shaded.org.apache.xpath.objects.XObject;
import javax.xml.transform.TransformerException;

/** @deprecated */
public class Quo extends Operation {
   static final long serialVersionUID = 693765299196169905L;

   public XObject operate(XObject left, XObject right) throws TransformerException {
      return new XNumber((double)((int)(left.num() / right.num())));
   }
}
