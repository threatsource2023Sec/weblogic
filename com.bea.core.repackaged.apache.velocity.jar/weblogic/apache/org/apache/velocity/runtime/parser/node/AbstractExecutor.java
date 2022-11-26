package weblogic.apache.org.apache.velocity.runtime.parser.node;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import weblogic.apache.org.apache.velocity.runtime.RuntimeLogger;

public abstract class AbstractExecutor {
   protected RuntimeLogger rlog = null;
   protected Method method = null;

   public abstract Object execute(Object var1) throws IllegalAccessException, InvocationTargetException;

   public boolean isAlive() {
      return this.method != null;
   }

   public Method getMethod() {
      return this.method;
   }
}
