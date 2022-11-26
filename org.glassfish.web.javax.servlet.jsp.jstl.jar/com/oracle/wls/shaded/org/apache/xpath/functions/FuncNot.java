package com.oracle.wls.shaded.org.apache.xpath.functions;

import com.oracle.wls.shaded.org.apache.xpath.XPathContext;
import com.oracle.wls.shaded.org.apache.xpath.objects.XBoolean;
import com.oracle.wls.shaded.org.apache.xpath.objects.XObject;
import javax.xml.transform.TransformerException;

public class FuncNot extends FunctionOneArg {
   static final long serialVersionUID = 7299699961076329790L;

   public XObject execute(XPathContext xctxt) throws TransformerException {
      return this.m_arg0.execute(xctxt).bool() ? XBoolean.S_FALSE : XBoolean.S_TRUE;
   }
}
