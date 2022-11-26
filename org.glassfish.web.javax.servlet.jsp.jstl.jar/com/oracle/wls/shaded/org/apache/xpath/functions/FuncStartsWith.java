package com.oracle.wls.shaded.org.apache.xpath.functions;

import com.oracle.wls.shaded.org.apache.xpath.XPathContext;
import com.oracle.wls.shaded.org.apache.xpath.objects.XBoolean;
import com.oracle.wls.shaded.org.apache.xpath.objects.XObject;
import javax.xml.transform.TransformerException;

public class FuncStartsWith extends Function2Args {
   static final long serialVersionUID = 2194585774699567928L;

   public XObject execute(XPathContext xctxt) throws TransformerException {
      return this.m_arg0.execute(xctxt).xstr().startsWith(this.m_arg1.execute(xctxt).xstr()) ? XBoolean.S_TRUE : XBoolean.S_FALSE;
   }
}
