package com.oracle.wls.shaded.org.apache.xpath.functions;

import com.oracle.wls.shaded.org.apache.xpath.XPathContext;
import com.oracle.wls.shaded.org.apache.xpath.objects.XBoolean;
import com.oracle.wls.shaded.org.apache.xpath.objects.XObject;
import java.util.Vector;
import javax.xml.transform.TransformerException;

public class FuncTrue extends Function {
   static final long serialVersionUID = 5663314547346339447L;

   public XObject execute(XPathContext xctxt) throws TransformerException {
      return XBoolean.S_TRUE;
   }

   public void fixupVariables(Vector vars, int globalsSize) {
   }
}
