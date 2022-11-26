package com.oracle.wls.shaded.org.apache.xpath.functions;

import com.oracle.wls.shaded.org.apache.xml.utils.XMLString;
import com.oracle.wls.shaded.org.apache.xpath.XPathContext;
import com.oracle.wls.shaded.org.apache.xpath.objects.XObject;
import com.oracle.wls.shaded.org.apache.xpath.objects.XString;
import javax.xml.transform.TransformerException;

public class FuncSubstringAfter extends Function2Args {
   static final long serialVersionUID = -8119731889862512194L;

   public XObject execute(XPathContext xctxt) throws TransformerException {
      XMLString s1 = this.m_arg0.execute(xctxt).xstr();
      XMLString s2 = this.m_arg1.execute(xctxt).xstr();
      int index = s1.indexOf(s2);
      return -1 == index ? XString.EMPTYSTRING : (XString)s1.substring(index + s2.length());
   }
}
