package weblogic.apache.org.apache.velocity.runtime.parser.node;

import java.lang.reflect.InvocationTargetException;
import weblogic.apache.org.apache.velocity.app.event.EventCartridge;
import weblogic.apache.org.apache.velocity.context.InternalContextAdapter;
import weblogic.apache.org.apache.velocity.exception.MethodInvocationException;
import weblogic.apache.org.apache.velocity.runtime.parser.Parser;
import weblogic.apache.org.apache.velocity.util.introspection.Info;
import weblogic.apache.org.apache.velocity.util.introspection.IntrospectionCacheData;
import weblogic.apache.org.apache.velocity.util.introspection.VelMethod;

public class ASTMethod extends SimpleNode {
   private String methodName = "";
   private int paramCount = 0;

   public ASTMethod(int id) {
      super(id);
   }

   public ASTMethod(Parser p, int id) {
      super(p, id);
   }

   public Object jjtAccept(ParserVisitor visitor, Object data) {
      return visitor.visit(this, data);
   }

   public Object init(InternalContextAdapter context, Object data) throws Exception {
      super.init(context, data);
      this.methodName = this.getFirstToken().image;
      this.paramCount = this.jjtGetNumChildren() - 1;
      return data;
   }

   public Object execute(Object o, InternalContextAdapter context) throws MethodInvocationException {
      VelMethod method = null;
      Object[] params = new Object[this.paramCount];

      try {
         IntrospectionCacheData icd = context.icacheGet(this);
         Class c = o.getClass();
         int j;
         if (icd != null && icd.contextData == c) {
            for(j = 0; j < this.paramCount; ++j) {
               params[j] = this.jjtGetChild(j + 1).value(context);
            }

            method = (VelMethod)icd.thingy;
         } else {
            for(j = 0; j < this.paramCount; ++j) {
               params[j] = this.jjtGetChild(j + 1).value(context);
            }

            method = super.rsvc.getUberspect().getMethod(o, this.methodName, params, new Info("", 1, 1));
            if (method != null) {
               icd = new IntrospectionCacheData();
               icd.contextData = c;
               icd.thingy = method;
               context.icachePut(this, icd);
            }
         }

         if (method == null) {
            return null;
         }
      } catch (MethodInvocationException var11) {
         throw var11;
      } catch (Exception var12) {
         super.rsvc.error("ASTMethod.execute() : exception from introspection : " + var12);
         return null;
      }

      try {
         Object obj = method.invoke(o, params);
         return obj == null && method.getReturnType() == Void.TYPE ? new String("") : obj;
      } catch (InvocationTargetException var9) {
         InvocationTargetException ite = var9;
         EventCartridge ec = context.getEventCartridge();
         if (ec != null && var9.getTargetException() instanceof Exception) {
            try {
               return ec.methodException(o.getClass(), this.methodName, (Exception)ite.getTargetException());
            } catch (Exception var8) {
               throw new MethodInvocationException("Invocation of method '" + this.methodName + "' in  " + o.getClass() + " threw exception " + var8.getClass() + " : " + var8.getMessage(), var8, this.methodName);
            }
         } else {
            throw new MethodInvocationException("Invocation of method '" + this.methodName + "' in  " + o.getClass() + " threw exception " + var9.getTargetException().getClass() + " : " + var9.getTargetException().getMessage(), var9.getTargetException(), this.methodName);
         }
      } catch (Exception var10) {
         super.rsvc.error("ASTMethod.execute() : exception invoking method '" + this.methodName + "' in " + o.getClass() + " : " + var10);
         return null;
      }
   }
}
