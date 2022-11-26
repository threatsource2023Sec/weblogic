package weblogic.corba.iiop.http;

import java.lang.annotation.Annotation;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import weblogic.cluster.ClusterMemberInfo;
import weblogic.management.runtime.SocketRuntime;
import weblogic.protocol.ServerChannel;
import weblogic.server.GlobalServiceLocator;

public abstract class ServletTunnelingSupport {
   @Inject
   private static ServletTunnelingSupport servletTunnelingSupport;

   public static ServletTunnelingSupport getServletTunnelingSupport() {
      if (servletTunnelingSupport == null) {
         loadServletTunnelingSupportImpl();
      }

      return servletTunnelingSupport;
   }

   private static synchronized void loadServletTunnelingSupportImpl() {
      if (servletTunnelingSupport == null) {
         try {
            servletTunnelingSupport = (ServletTunnelingSupport)GlobalServiceLocator.getServiceLocator().getService(ServletTunnelingSupport.class, new Annotation[0]);
            if (servletTunnelingSupport == null) {
               doHk2Fallback();
            }
         } catch (Exception var1) {
            doHk2Fallback();
         }

      }
   }

   private static void doHk2Fallback() {
      try {
         System.out.println("!!! HK2 Did not fill in weblogic.corba.iiop.http.ServletTunnelingSupport.servletTunnelingSupport; using reflection instead");
         servletTunnelingSupport = (ServletTunnelingSupport)Class.forName("weblogic.servlet.internal.utils.ServletTunnelingSupportImpl").newInstance();
      } catch (Exception var1) {
         System.out.println("!!! Unable to fill in field using reflection: " + var1);
      }

   }

   public static void setServletTunnelingSupport(ServletTunnelingSupport servletTunnelingSupport) {
      ServletTunnelingSupport.servletTunnelingSupport = servletTunnelingSupport;
   }

   public abstract String getServerEntry(HttpServletRequest var1, ClusterMemberInfo var2);

   public abstract String getProtocolName(HttpServletRequest var1);

   public abstract ServerChannel getChannel(HttpServletRequest var1);

   public abstract SocketRuntime getSocketRuntime(HttpServletRequest var1);

   public abstract String getHostAddress(HttpServletRequest var1);
}
