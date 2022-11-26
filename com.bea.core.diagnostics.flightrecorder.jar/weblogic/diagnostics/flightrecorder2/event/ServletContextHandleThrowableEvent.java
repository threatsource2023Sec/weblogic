package weblogic.diagnostics.flightrecorder2.event;

import jdk.jfr.Category;
import jdk.jfr.Description;
import jdk.jfr.Label;
import jdk.jfr.Name;
import weblogic.diagnostics.flightrecorder2.impl.RelationKey;
import weblogic.diagnostics.instrumentation.DynamicJoinPoint;

@Label("Servlet Context Handle Throwable")
@Name("com.oracle.weblogic.servlet.ServletContextHandleThrowableEvent")
@Description("This event covers the servlet context handling a throwable, including the elapsed time")
@Category({"WebLogic Server", "Servlet"})
public class ServletContextHandleThrowableEvent extends ServletBaseEvent {
   @Label("Throwable Message")
   @Description("The Throwable Message")
   @RelationKey("http://www.oracle.com/wls/Servlet/throwable")
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
