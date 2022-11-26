package com.oracle.wls.shaded.org.apache.xpath.functions;

import com.oracle.wls.shaded.org.apache.xpath.XPathContext;
import com.oracle.wls.shaded.org.apache.xpath.objects.XBoolean;
import com.oracle.wls.shaded.org.apache.xpath.objects.XObject;
import java.util.Vector;
import javax.xml.transform.TransformerException;

public class FuncFalse extends Function {
   static final long serialVersionUID = 6150918062759769887L;

   public XObject execute(XPathContext xctxt) throws TransformerException {
      return XBoolean.S_FALSE;
   }

   public void fixupVariables(Vector vars, int globalsSize) {
   }
}
