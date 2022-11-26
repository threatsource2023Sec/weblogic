package com.oracle.wls.shaded.org.apache.xpath.functions;

import com.oracle.wls.shaded.org.apache.xml.dtm.DTM;
import com.oracle.wls.shaded.org.apache.xpath.XPathContext;
import com.oracle.wls.shaded.org.apache.xpath.objects.XObject;
import com.oracle.wls.shaded.org.apache.xpath.objects.XString;
import javax.xml.transform.TransformerException;

public class FuncUnparsedEntityURI extends FunctionOneArg {
   static final long serialVersionUID = 845309759097448178L;

   public XObject execute(XPathContext xctxt) throws TransformerException {
      String name = this.m_arg0.execute(xctxt).str();
      int context = xctxt.getCurrentNode();
      DTM dtm = xctxt.getDTM(context);
      int doc = dtm.getDocument();
      String uri = dtm.getUnparsedEntityURI(name);
      return new XString(uri);
   }
}
