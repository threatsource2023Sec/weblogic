package com.oracle.wls.shaded.org.apache.xpath.functions;

import com.oracle.wls.shaded.org.apache.xalan.res.XSLMessages;
import com.oracle.wls.shaded.org.apache.xpath.Expression;
import com.oracle.wls.shaded.org.apache.xpath.ExpressionOwner;
import com.oracle.wls.shaded.org.apache.xpath.XPathContext;
import com.oracle.wls.shaded.org.apache.xpath.XPathVisitor;
import com.oracle.wls.shaded.org.apache.xpath.compiler.Compiler;
import com.oracle.wls.shaded.org.apache.xpath.objects.XObject;
import javax.xml.transform.TransformerException;

public abstract class Function extends Expression {
   static final long serialVersionUID = 6927661240854599768L;

   public void setArg(Expression arg, int argNum) throws WrongNumberArgsException {
      this.reportWrongNumberArgs();
   }

   public void checkNumberArgs(int argNum) throws WrongNumberArgsException {
      if (argNum != 0) {
         this.reportWrongNumberArgs();
      }

   }

   protected void reportWrongNumberArgs() throws WrongNumberArgsException {
      throw new WrongNumberArgsException(XSLMessages.createXPATHMessage("zero", (Object[])null));
   }

   public XObject execute(XPathContext xctxt) throws TransformerException {
      System.out.println("Error! Function.execute should not be called!");
      return null;
   }

   public void callArgVisitors(XPathVisitor visitor) {
   }

   public void callVisitors(ExpressionOwner owner, XPathVisitor visitor) {
      if (visitor.visitFunction(owner, this)) {
         this.callArgVisitors(visitor);
      }

   }

   public boolean deepEquals(Expression expr) {
      return this.isSameClass(expr);
   }

   public void postCompileStep(Compiler compiler) {
   }
}
