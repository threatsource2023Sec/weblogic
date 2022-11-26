package com.oracle.wls.shaded.org.apache.xpath.functions;

import com.oracle.wls.shaded.org.apache.xpath.XPathContext;
import com.oracle.wls.shaded.org.apache.xpath.objects.XObject;
import com.oracle.wls.shaded.org.apache.xpath.objects.XString;
import javax.xml.transform.TransformerException;

public class FuncString extends FunctionDef1Arg {
   static final long serialVersionUID = -2206677149497712883L;

   public XObject execute(XPathContext xctxt) throws TransformerException {
      return (XString)this.getArg0AsString(xctxt);
   }
}
