package com.oracle.wls.shaded.org.apache.xpath.functions;

import com.oracle.wls.shaded.org.apache.xpath.XPathContext;
import com.oracle.wls.shaded.org.apache.xpath.objects.XObject;
import com.oracle.wls.shaded.org.apache.xpath.objects.XString;
import javax.xml.transform.TransformerException;

public class FuncGenerateId extends FunctionDef1Arg {
   static final long serialVersionUID = 973544842091724273L;

   public XObject execute(XPathContext xctxt) throws TransformerException {
      int which = this.getArg0AsNode(xctxt);
      return -1 != which ? new XString("N" + Integer.toHexString(which).toUpperCase()) : XString.EMPTYSTRING;
   }
}
