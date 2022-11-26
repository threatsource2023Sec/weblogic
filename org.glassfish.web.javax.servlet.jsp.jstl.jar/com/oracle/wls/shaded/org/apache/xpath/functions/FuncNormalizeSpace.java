package com.oracle.wls.shaded.org.apache.xpath.functions;

import com.oracle.wls.shaded.org.apache.xml.dtm.DTM;
import com.oracle.wls.shaded.org.apache.xml.utils.XMLString;
import com.oracle.wls.shaded.org.apache.xpath.XPathContext;
import com.oracle.wls.shaded.org.apache.xpath.objects.XObject;
import com.oracle.wls.shaded.org.apache.xpath.objects.XString;
import javax.xml.transform.TransformerException;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;

public class FuncNormalizeSpace extends FunctionDef1Arg {
   static final long serialVersionUID = -3377956872032190880L;

   public XObject execute(XPathContext xctxt) throws TransformerException {
      XMLString s1 = this.getArg0AsString(xctxt);
      return (XString)s1.fixWhiteSpace(true, true, false);
   }

   public void executeCharsToContentHandler(XPathContext xctxt, ContentHandler handler) throws TransformerException, SAXException {
      if (this.Arg0IsNodesetExpr()) {
         int node = this.getArg0AsNode(xctxt);
         if (-1 != node) {
            DTM dtm = xctxt.getDTM(node);
            dtm.dispatchCharactersEvents(node, handler, true);
         }
      } else {
         XObject obj = this.execute(xctxt);
         obj.dispatchCharactersEvents(handler);
      }

   }
}
