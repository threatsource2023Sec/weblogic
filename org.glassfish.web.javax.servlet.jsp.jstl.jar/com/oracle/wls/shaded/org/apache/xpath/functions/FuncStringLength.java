package com.oracle.wls.shaded.org.apache.xpath.functions;

import com.oracle.wls.shaded.org.apache.xpath.XPathContext;
import com.oracle.wls.shaded.org.apache.xpath.objects.XNumber;
import com.oracle.wls.shaded.org.apache.xpath.objects.XObject;
import javax.xml.transform.TransformerException;

public class FuncStringLength extends FunctionDef1Arg {
   static final long serialVersionUID = -159616417996519839L;

   public XObject execute(XPathContext xctxt) throws TransformerException {
      return new XNumber((double)this.getArg0AsString(xctxt).length());
   }
}
