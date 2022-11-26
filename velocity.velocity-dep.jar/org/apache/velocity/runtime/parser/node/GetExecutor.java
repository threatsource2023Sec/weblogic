package org.apache.velocity.runtime.parser.node;

import java.lang.reflect.InvocationTargetException;
import org.apache.velocity.context.InternalContextAdapter;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.runtime.RuntimeLogger;
import org.apache.velocity.util.introspection.Introspector;

public class GetExecutor extends AbstractExecutor {
   private Object[] args = new Object[1];

   public GetExecutor(RuntimeLogger r, Introspector ispect, Class c, String key) throws Exception {
      super.rlog = r;
      this.args[0] = key;
      super.method = ispect.getMethod(c, "get", this.args);
   }

   public Object execute(Object o) throws IllegalAccessException, InvocationTargetException {
      return super.method == null ? null : super.method.invoke(o, this.args);
   }

   public Object OLDexecute(Object o, InternalContextAdapter context) throws IllegalAccessException, MethodInvocationException {
      if (super.method == null) {
         return null;
      } else {
         try {
            return super.method.invoke(o, this.args);
         } catch (InvocationTargetException var5) {
            throw new MethodInvocationException("Invocation of method 'get(\"" + this.args[0] + "\")'" + " in  " + o.getClass() + " threw exception " + var5.getTargetException().getClass(), var5.getTargetException(), "get");
         } catch (IllegalArgumentException var6) {
            return null;
         }
      }
   }
}
