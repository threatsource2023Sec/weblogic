package com.oracle.wls.shaded.org.apache.xpath.functions;

import com.oracle.wls.shaded.org.apache.xml.dtm.DTM;
import com.oracle.wls.shaded.org.apache.xpath.XPathContext;
import com.oracle.wls.shaded.org.apache.xpath.objects.XObject;
import com.oracle.wls.shaded.org.apache.xpath.objects.XString;
import javax.xml.transform.TransformerException;

public class FuncLocalPart extends FunctionDef1Arg {
   static final long serialVersionUID = 7591798770325814746L;

   public XObject execute(XPathContext xctxt) throws TransformerException {
      int context = this.getArg0AsNode(xctxt);
      if (-1 == context) {
         return XString.EMPTYSTRING;
      } else {
         DTM dtm = xctxt.getDTM(context);
         String s = context != -1 ? dtm.getLocalName(context) : "";
         return !s.startsWith("#") && !s.equals("xmlns") ? new XString(s) : XString.EMPTYSTRING;
      }
   }
}
