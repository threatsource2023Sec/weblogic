package com.oracle.wls.shaded.org.apache.xpath.functions;

import com.oracle.wls.shaded.org.apache.xpath.XPathContext;
import com.oracle.wls.shaded.org.apache.xpath.objects.XBoolean;
import com.oracle.wls.shaded.org.apache.xpath.objects.XObject;
import javax.xml.transform.TransformerException;

public class FuncBoolean extends FunctionOneArg {
   static final long serialVersionUID = 4328660760070034592L;

   public XObject execute(XPathContext xctxt) throws TransformerException {
      return this.m_arg0.execute(xctxt).bool() ? XBoolean.S_TRUE : XBoolean.S_FALSE;
   }
}
