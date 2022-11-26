package weblogic.corba.idl;

import org.omg.CORBA.Environment;

public class EnvironmentImpl extends Environment {
   private Exception ex;

   public void clear() {
      this.ex = null;
   }

   public void exception(Exception exception) {
      this.ex = exception;
   }

   public Exception exception() {
      return this.ex;
   }
}
