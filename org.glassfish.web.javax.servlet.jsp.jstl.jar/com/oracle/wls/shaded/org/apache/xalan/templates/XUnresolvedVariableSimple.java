package com.oracle.wls.shaded.org.apache.xalan.templates;

import com.oracle.wls.shaded.org.apache.xpath.Expression;
import com.oracle.wls.shaded.org.apache.xpath.XPathContext;
import com.oracle.wls.shaded.org.apache.xpath.objects.XObject;
import javax.xml.transform.TransformerException;

public class XUnresolvedVariableSimple extends XObject {
   static final long serialVersionUID = -1224413807443958985L;

   public XUnresolvedVariableSimple(ElemVariable obj) {
      super(obj);
   }

   public XObject execute(XPathContext xctxt) throws TransformerException {
      Expression expr = ((ElemVariable)this.m_obj).getSelect().getExpression();
      XObject xobj = expr.execute(xctxt);
      xobj.allowDetachToRelease(false);
      return xobj;
   }

   public int getType() {
      return 600;
   }

   public String getTypeString() {
      return "XUnresolvedVariableSimple (" + this.object().getClass().getName() + ")";
   }
}
