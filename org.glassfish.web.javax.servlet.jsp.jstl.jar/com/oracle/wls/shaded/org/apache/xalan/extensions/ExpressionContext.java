package com.oracle.wls.shaded.org.apache.xalan.extensions;

import com.oracle.wls.shaded.org.apache.xml.utils.QName;
import com.oracle.wls.shaded.org.apache.xpath.XPathContext;
import com.oracle.wls.shaded.org.apache.xpath.objects.XObject;
import javax.xml.transform.ErrorListener;
import javax.xml.transform.TransformerException;
import org.w3c.dom.Node;
import org.w3c.dom.traversal.NodeIterator;

public interface ExpressionContext {
   Node getContextNode();

   NodeIterator getContextNodes();

   ErrorListener getErrorListener();

   double toNumber(Node var1);

   String toString(Node var1);

   XObject getVariableOrParam(QName var1) throws TransformerException;

   XPathContext getXPathContext() throws TransformerException;
}
