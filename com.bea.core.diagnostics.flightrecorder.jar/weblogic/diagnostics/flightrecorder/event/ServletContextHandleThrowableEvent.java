package weblogic.diagnostics.flightrecorder.event;

import com.oracle.jrockit.jfr.EventDefinition;
import com.oracle.jrockit.jfr.UseConstantPool;
import com.oracle.jrockit.jfr.ValueDefinition;
import weblogic.diagnostics.instrumentation.DynamicJoinPoint;

@EventDefinition(
   name = "Servlet Context Handle Throwable",
   description = "This event covers the servlet context handling a throwable, including the elapsed time",
   path = "wls/Servlet/Servlet_Context_Handle_Throwable",
   thread = true
)
public class ServletContextHandleThrowableEvent extends ServletBaseTimedEvent {
   @UseConstantPool(
      name = "GlobalPool"
   )
   @ValueDefinition(
      name = "Throwable Message",
      description = "The Throwable Message",
      relationKey = "http://www.oracle.com/wls/Servlet/throwable"
   )
   protected String throwableMessage = null;

   public String getThrowableMessage() {
      return this.throwableMessage;
   }

   public void setThrowableMessage(String throwableMessage) {
      this.throwableMessage = throwableMessage;
   }

   public void populateExtensions(Object retVal, Object[] args, DynamicJoinPoint djp, boolean isAfter) {
      super.populateExtensions((Object)null, args, djp, isAfter);
      if (args != null && args.length >= 2 && args[1] != null) {
         this.throwableMessage = args[1].toString();
      }

   }
}
