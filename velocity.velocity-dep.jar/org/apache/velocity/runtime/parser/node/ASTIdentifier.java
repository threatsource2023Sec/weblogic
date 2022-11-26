package org.apache.velocity.runtime.parser.node;

import java.lang.reflect.InvocationTargetException;
import org.apache.velocity.app.event.EventCartridge;
import org.apache.velocity.context.InternalContextAdapter;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.runtime.parser.Parser;
import org.apache.velocity.util.introspection.Info;
import org.apache.velocity.util.introspection.IntrospectionCacheData;
import org.apache.velocity.util.introspection.VelPropertyGet;

public class ASTIdentifier extends SimpleNode {
   private String identifier = "";
   protected Info uberInfo;

   public ASTIdentifier(int id) {
      super(id);
   }

   public ASTIdentifier(Parser p, int id) {
      super(p, id);
   }

   public Object jjtAccept(ParserVisitor visitor, Object data) {
      return visitor.visit(this, data);
   }

   public Object init(InternalContextAdapter context, Object data) throws Exception {
      super.init(context, data);
      this.identifier = this.getFirstToken().image;
      this.uberInfo = new Info(context.getCurrentTemplateName(), this.getLine(), this.getColumn());
      return data;
   }

   public Object execute(Object o, InternalContextAdapter context) throws MethodInvocationException {
      VelPropertyGet vg = null;

      try {
         Class c = o.getClass();
         IntrospectionCacheData icd = context.icacheGet(this);
         if (icd != null && icd.contextData == c) {
            vg = (VelPropertyGet)icd.thingy;
         } else {
            vg = super.rsvc.getUberspect().getPropertyGet(o, this.identifier, this.uberInfo);
            if (vg != null && vg.isCacheable()) {
               icd = new IntrospectionCacheData();
               icd.contextData = c;
               icd.thingy = vg;
               context.icachePut(this, icd);
            }
         }
      } catch (Exception var11) {
         super.rsvc.error("ASTIdentifier.execute() : identifier = " + this.identifier + " : " + var11);
      }

      if (vg == null) {
         return null;
      } else {
         try {
            return vg.invoke(o);
         } catch (InvocationTargetException var8) {
            InvocationTargetException ite = var8;
            EventCartridge ec = context.getEventCartridge();
            if (ec != null && var8.getTargetException() instanceof Exception) {
               try {
                  return ec.methodException(o.getClass(), vg.getMethodName(), (Exception)ite.getTargetException());
               } catch (Exception var7) {
                  throw new MethodInvocationException("Invocation of method '" + vg.getMethodName() + "'" + " in  " + o.getClass() + " threw exception " + var8.getTargetException().getClass() + " : " + var8.getTargetException().getMessage(), var8.getTargetException(), vg.getMethodName());
               }
            } else {
               throw new MethodInvocationException("Invocation of method '" + vg.getMethodName() + "'" + " in  " + o.getClass() + " threw exception " + var8.getTargetException().getClass() + " : " + var8.getTargetException().getMessage(), var8.getTargetException(), vg.getMethodName());
            }
         } catch (IllegalArgumentException var9) {
            return null;
         } catch (Exception var10) {
            super.rsvc.error("ASTIdentifier() : exception invoking method for identifier '" + this.identifier + "' in " + o.getClass() + " : " + var10);
            return null;
         }
      }
   }
}
