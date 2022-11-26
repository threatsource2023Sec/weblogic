package com.oracle.wls.shaded.org.apache.xpath.jaxp;

import com.oracle.wls.shaded.org.apache.xalan.res.XSLMessages;
import com.oracle.wls.shaded.org.apache.xml.utils.QName;
import com.oracle.wls.shaded.org.apache.xpath.VariableStack;
import com.oracle.wls.shaded.org.apache.xpath.XPathContext;
import com.oracle.wls.shaded.org.apache.xpath.objects.XObject;
import javax.xml.transform.TransformerException;
import javax.xml.xpath.XPathVariableResolver;

public class JAXPVariableStack extends VariableStack {
   private final XPathVariableResolver resolver;

   public JAXPVariableStack(XPathVariableResolver resolver) {
      super(2);
      this.resolver = resolver;
   }

   public XObject getVariableOrParam(XPathContext xctxt, QName qname) throws TransformerException, IllegalArgumentException {
      if (qname == null) {
         String fmsg = XSLMessages.createXPATHMessage("ER_ARG_CANNOT_BE_NULL", new Object[]{"Variable qname"});
         throw new IllegalArgumentException(fmsg);
      } else {
         javax.xml.namespace.QName name = new javax.xml.namespace.QName(qname.getNamespace(), qname.getLocalPart());
         Object varValue = this.resolver.resolveVariable(name);
         if (varValue == null) {
            String fmsg = XSLMessages.createXPATHMessage("ER_RESOLVE_VARIABLE_RETURNS_NULL", new Object[]{name.toString()});
            throw new TransformerException(fmsg);
         } else {
            return XObject.create(varValue, xctxt);
         }
      }
   }
}
