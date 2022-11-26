package weblogic.protocol;

import java.lang.reflect.Method;
import java.security.AccessController;
import java.util.Locale;
import javax.inject.Inject;
import javax.inject.Named;
import org.glassfish.hk2.runlevel.RunLevel;
import org.jvnet.hk2.annotations.Service;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.server.AbstractServerService;
import weblogic.server.ServerLogger;
import weblogic.server.ServerService;
import weblogic.server.ServiceFailureException;

@Service
@Named
@RunLevel(5)
public class ProtocolService extends AbstractServerService {
   @Inject
   @Named("BootService")
   private ServerService dependencyOnBootService;
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

   public void start() throws ServiceFailureException {
      loadProtocol("t3", "weblogic.rjvm.t3.ProtocolHandlerT3", true);
      loadProtocol("http", "weblogic.servlet.internal.ProtocolHandlerHTTP", true);
      loadProtocol("iiop", true);
      loadProtocol("ldap", true);
      loadProtocol("ons", true);
      loadProtocol("cluster", "weblogic.cluster.messaging.protocol.ProtocolHandlerClusterBroadcast", true);
      loadProtocol("snmp", "weblogic.diagnostics.snmp.muxer.ProtocolHandlerSNMP", false);
      loadProtocol("admin", "weblogic.protocol.ProtocolHandlerAdmin");
      ServerLogger.logAdminProtocolConfigured(ProtocolManager.getDefaultAdminProtocol().getAsURLPrefix());
   }

   public static void loadProtocol(String name, String clz) {
      try {
         Class c = Class.forName(clz);
         Method m = c.getMethod("getProtocolHandler");
         m.setAccessible(true);
         m.invoke((Object)null);
         ServerLogger.logProtocolConfigured(name);
      } catch (Exception var4) {
      }

   }

   public static void loadProtocol(String name, String clz, boolean secure) {
      loadProtocol(name, clz);
      if (secure) {
         loadProtocol(name + "s", clz + "S");
      }

   }

   public static void loadProtocol(String name, boolean secure) {
      loadProtocol(name, "weblogic." + name.toLowerCase(Locale.ENGLISH) + ".ProtocolHandler" + name.toUpperCase(Locale.ENGLISH), secure);
   }

   public static void loadProtocol(String name) {
      loadProtocol(name, false);
   }

   public static boolean legalProtocol(String val) {
      return true;
   }
}
