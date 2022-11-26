package com.oracle.wls.shaded.org.apache.xpath.operations;

import com.oracle.wls.shaded.org.apache.xpath.XPathContext;
import com.oracle.wls.shaded.org.apache.xpath.objects.XBoolean;
import com.oracle.wls.shaded.org.apache.xpath.objects.XObject;
import javax.xml.transform.TransformerException;

public class Bool extends UnaryOperation {
   static final long serialVersionUID = 44705375321914635L;

   public XObject operate(XObject right) throws TransformerException {
      if (1 == right.getType()) {
         return right;
      } else {
         return right.bool() ? XBoolean.S_TRUE : XBoolean.S_FALSE;
      }
   }

   public boolean bool(XPathContext xctxt) throws TransformerException {
      return this.m_right.bool(xctxt);
   }
}
