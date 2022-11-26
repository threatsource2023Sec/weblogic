package com.oracle.wls.shaded.org.apache.xpath.functions;

import com.oracle.wls.shaded.org.apache.xalan.res.XSLMessages;
import com.oracle.wls.shaded.org.apache.xpath.XPathContext;
import com.oracle.wls.shaded.org.apache.xpath.objects.XObject;
import com.oracle.wls.shaded.org.apache.xpath.objects.XString;
import javax.xml.transform.TransformerException;

public class FuncConcat extends FunctionMultiArgs {
   static final long serialVersionUID = 1737228885202314413L;

   public XObject execute(XPathContext xctxt) throws TransformerException {
      StringBuffer sb = new StringBuffer();
      sb.append(this.m_arg0.execute(xctxt).str());
      sb.append(this.m_arg1.execute(xctxt).str());
      if (null != this.m_arg2) {
         sb.append(this.m_arg2.execute(xctxt).str());
      }

      if (null != this.m_args) {
         for(int i = 0; i < this.m_args.length; ++i) {
            sb.append(this.m_args[i].execute(xctxt).str());
         }
      }

      return new XString(sb.toString());
   }

   public void checkNumberArgs(int argNum) throws WrongNumberArgsException {
      if (argNum < 2) {
         this.reportWrongNumberArgs();
      }

   }

   protected void reportWrongNumberArgs() throws WrongNumberArgsException {
      throw new WrongNumberArgsException(XSLMessages.createXPATHMessage("gtone", (Object[])null));
   }
}
