package weblogic.servlet.internal.session;

import java.io.PrintStream;
import weblogic.management.ManagementException;
import weblogic.management.runtime.RuntimeMBeanDelegate;
import weblogic.management.runtime.ServletSessionRuntimeMBean;

public final class ServletSessionRuntimeMBeanImpl extends RuntimeMBeanDelegate implements ServletSessionRuntimeMBean {
   private static final long serialVersionUID = -1298228652729291451L;
   private SessionData session;
   public static final String TYPE = "ServletSessionRuntime";

   public ServletSessionRuntimeMBeanImpl(SessionData s) throws ManagementException {
      super(ServletSessionRuntimeManager.getSessionKey(s), s.getWebAppServletContext().getRuntimeMBean());
      this.session = s;
   }

   public void invalidate() {
      this.session.invalidate();
   }

   public long getTimeLastAccessed() {
      return this.session.getLAT();
   }

   public long getMaxInactiveInterval() {
      return (long)this.session.getMaxInactiveInterval();
   }

   public String getMainAttribute() {
      return this.session.getMainAttributeValue();
   }

   public static void dumpSession(PrintStream p, ServletSessionRuntimeMBean mb) {
      println(p, "    SESSION NAME: " + mb.getName());
      println(p, "        TimeLastAccessed: " + mb.getTimeLastAccessed());
      println(p, "        MainAttribute: " + mb.getMainAttribute());
   }

   private static void println(PrintStream p, String s) {
      p.println(s + "<br>");
   }
}
