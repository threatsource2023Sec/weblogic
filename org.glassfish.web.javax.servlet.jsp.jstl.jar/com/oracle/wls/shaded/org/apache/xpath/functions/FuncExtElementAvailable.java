package com.oracle.wls.shaded.org.apache.xpath.functions;

import com.oracle.wls.shaded.org.apache.xalan.transformer.TransformerImpl;
import com.oracle.wls.shaded.org.apache.xml.utils.QName;
import com.oracle.wls.shaded.org.apache.xpath.ExtensionsProvider;
import com.oracle.wls.shaded.org.apache.xpath.XPathContext;
import com.oracle.wls.shaded.org.apache.xpath.objects.XBoolean;
import com.oracle.wls.shaded.org.apache.xpath.objects.XObject;
import javax.xml.transform.TransformerException;

public class FuncExtElementAvailable extends FunctionOneArg {
   static final long serialVersionUID = -472533699257968546L;

   public XObject execute(XPathContext xctxt) throws TransformerException {
      String fullName = this.m_arg0.execute(xctxt).str();
      int indexOfNSSep = fullName.indexOf(58);
      String prefix;
      String namespace;
      String methName;
      if (indexOfNSSep < 0) {
         prefix = "";
         namespace = "http://www.w3.org/1999/XSL/Transform";
         methName = fullName;
      } else {
         prefix = fullName.substring(0, indexOfNSSep);
         namespace = xctxt.getNamespaceContext().getNamespaceForPrefix(prefix);
         if (null == namespace) {
            return XBoolean.S_FALSE;
         }

         methName = fullName.substring(indexOfNSSep + 1);
      }

      if (!namespace.equals("http://www.w3.org/1999/XSL/Transform") && !namespace.equals("http://xml.apache.org/xalan")) {
         ExtensionsProvider extProvider = (ExtensionsProvider)xctxt.getOwnerObject();
         return extProvider.elementAvailable(namespace, methName) ? XBoolean.S_TRUE : XBoolean.S_FALSE;
      } else {
         try {
            TransformerImpl transformer = (TransformerImpl)xctxt.getOwnerObject();
            return transformer.getStylesheet().getAvailableElements().containsKey(new QName(namespace, methName)) ? XBoolean.S_TRUE : XBoolean.S_FALSE;
         } catch (Exception var8) {
            return XBoolean.S_FALSE;
         }
      }
   }
}
