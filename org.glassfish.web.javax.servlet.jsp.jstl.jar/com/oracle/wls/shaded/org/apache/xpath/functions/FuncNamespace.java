package com.oracle.wls.shaded.org.apache.xpath.functions;

import com.oracle.wls.shaded.org.apache.xml.dtm.DTM;
import com.oracle.wls.shaded.org.apache.xpath.XPathContext;
import com.oracle.wls.shaded.org.apache.xpath.objects.XObject;
import com.oracle.wls.shaded.org.apache.xpath.objects.XString;
import javax.xml.transform.TransformerException;

public class FuncNamespace extends FunctionDef1Arg {
   static final long serialVersionUID = -4695674566722321237L;

   public XObject execute(XPathContext xctxt) throws TransformerException {
      int context = this.getArg0AsNode(xctxt);
      if (context == -1) {
         return XString.EMPTYSTRING;
      } else {
         DTM dtm = xctxt.getDTM(context);
         int t = dtm.getNodeType(context);
         String s;
         if (t == 1) {
            s = dtm.getNamespaceURI(context);
         } else {
            if (t != 2) {
               return XString.EMPTYSTRING;
            }

            s = dtm.getNodeName(context);
            if (s.startsWith("xmlns:") || s.equals("xmlns")) {
               return XString.EMPTYSTRING;
            }

            s = dtm.getNamespaceURI(context);
         }

         return null == s ? XString.EMPTYSTRING : new XString(s);
      }
   }
}
