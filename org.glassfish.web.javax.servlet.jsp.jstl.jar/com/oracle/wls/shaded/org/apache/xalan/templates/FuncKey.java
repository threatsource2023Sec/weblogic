package com.oracle.wls.shaded.org.apache.xalan.templates;

import com.oracle.wls.shaded.org.apache.xalan.transformer.KeyManager;
import com.oracle.wls.shaded.org.apache.xalan.transformer.TransformerImpl;
import com.oracle.wls.shaded.org.apache.xml.dtm.DTM;
import com.oracle.wls.shaded.org.apache.xml.dtm.DTMIterator;
import com.oracle.wls.shaded.org.apache.xml.utils.QName;
import com.oracle.wls.shaded.org.apache.xml.utils.XMLString;
import com.oracle.wls.shaded.org.apache.xpath.XPathContext;
import com.oracle.wls.shaded.org.apache.xpath.axes.UnionPathIterator;
import com.oracle.wls.shaded.org.apache.xpath.functions.Function2Args;
import com.oracle.wls.shaded.org.apache.xpath.objects.XNodeSet;
import com.oracle.wls.shaded.org.apache.xpath.objects.XObject;
import java.util.Hashtable;
import javax.xml.transform.TransformerException;

public class FuncKey extends Function2Args {
   static final long serialVersionUID = 9089293100115347340L;
   private static Boolean ISTRUE = new Boolean(true);

   public XObject execute(XPathContext xctxt) throws TransformerException {
      TransformerImpl transformer = (TransformerImpl)xctxt.getOwnerObject();
      XNodeSet nodes = null;
      int context = xctxt.getCurrentNode();
      DTM dtm = xctxt.getDTM(context);
      int docContext = dtm.getDocumentRoot(context);
      if (-1 == docContext) {
      }

      String xkeyname = this.getArg0().execute(xctxt).str();
      QName keyname = new QName(xkeyname, xctxt.getNamespaceContext());
      XObject arg = this.getArg1().execute(xctxt);
      boolean argIsNodeSetDTM = 4 == arg.getType();
      KeyManager kmgr = transformer.getKeyManager();
      if (argIsNodeSetDTM) {
         XNodeSet ns = (XNodeSet)arg;
         ns.setShouldCacheNodes(true);
         int len = ns.getLength();
         if (len <= 1) {
            argIsNodeSetDTM = false;
         }
      }

      if (argIsNodeSetDTM) {
         Hashtable usedrefs = null;
         DTMIterator ni = arg.iter();
         UnionPathIterator upi = new UnionPathIterator();
         upi.exprSetParent(this);

         int pos;
         while(-1 != (pos = ni.nextNode())) {
            dtm = xctxt.getDTM(pos);
            XMLString ref = dtm.getStringValue(pos);
            if (null != ref) {
               if (null == usedrefs) {
                  usedrefs = new Hashtable();
               }

               if (usedrefs.get(ref) == null) {
                  usedrefs.put(ref, ISTRUE);
                  XNodeSet nl = kmgr.getNodeSetDTMByKey(xctxt, docContext, keyname, ref, xctxt.getNamespaceContext());
                  nl.setRoot(xctxt.getCurrentNode(), xctxt);
                  upi.addIterator(nl);
               }
            }
         }

         int current = xctxt.getCurrentNode();
         upi.setRoot(current, xctxt);
         nodes = new XNodeSet(upi);
      } else {
         XMLString ref = arg.xstr();
         nodes = kmgr.getNodeSetDTMByKey(xctxt, docContext, keyname, ref, xctxt.getNamespaceContext());
         nodes.setRoot(xctxt.getCurrentNode(), xctxt);
      }

      return nodes;
   }
}
