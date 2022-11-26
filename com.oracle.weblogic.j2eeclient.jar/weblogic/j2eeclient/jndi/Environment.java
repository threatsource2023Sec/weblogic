package weblogic.j2eeclient.jndi;

import java.util.Enumeration;
import java.util.HashSet;
import javax.naming.ConfigurationException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.LinkException;
import javax.naming.NameClassPair;
import javax.naming.NamingException;
import javax.naming.NoPermissionException;
import weblogic.application.naming.JavaAppNamingHelper;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.j2eeclient.AppClientTextTextFormatter;
import weblogic.j2eeclient.ClientSimpleContext;
import weblogic.protocol.ClientEnvironment;

public class Environment {
   private static final AppClientTextTextFormatter TEXT_FORMATTER = AppClientTextTextFormatter.getInstance();
   private static Environment instance;
   private final Context localRootCtx;
   private static final DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugAppClient");
   private final Context remoteRootCtx = this.getRemoteRootContext();
   private static HashSet contexts;
   private static final int MAX_DEPTH = 10;
   private static int depth;

   public Context getLocalRootCtx() {
      return this.localRootCtx;
   }

   public Context getRemoteRootCtx() {
      return this.remoteRootCtx;
   }

   public static Environment instance() {
      if (instance == null) {
         throw new IllegalStateException("Environment is not bootstrapped.");
      } else {
         return instance;
      }
   }

   public static Environment bootstrap(String applicationName) throws NamingException, EnvironmentException {
      if (instance == null) {
         instance = new Environment(applicationName);
         return instance;
      } else {
         throw new IllegalStateException("Bootstrapping can only occur once.");
      }
   }

   private Environment(String applicationName) throws NamingException, EnvironmentException {
      System.setProperty("java.naming.factory.url.pkgs", "weblogic.j2eeclient:weblogic.corba.j2ee.naming.url");
      this.localRootCtx = this.getLocalRootContext(applicationName, this.remoteRootCtx);
   }

   private Context getLocalRootContext(String applicationName, Context remoteRootContext) throws NamingException, EnvironmentException {
      Context remoteGlobalCtx = (Context)remoteRootContext.lookup("java:global");
      Context remoteAppNamingContext = null;
      if (applicationName != null) {
         remoteAppNamingContext = this.getRemoteAppNamingContext(applicationName, remoteRootContext, remoteGlobalCtx);
      } else if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("Application name is null. Skipping remote application naming context lookup");
      }

      return new ClientSimpleContext(remoteRootContext, remoteGlobalCtx, remoteAppNamingContext);
   }

   private Context getRemoteAppNamingContext(String applicationName, Context remoteRootContext, Context remoteGlobalCtx) throws EnvironmentException {
      try {
         Context appNameHolderContext = (Context)remoteRootContext.lookup("__WL_GlobalJavaApp/" + JavaAppNamingHelper.indivisableJndiApplicationName(applicationName));

         try {
            String activeApplicationId = (String)appNameHolderContext.lookup("__WL_Active_Application_Version");

            try {
               return (Context)appNameHolderContext.lookup(JavaAppNamingHelper.indivisableJndiApplicationName(activeApplicationId));
            } catch (NamingException var7) {
               throw new EnvironmentException(TEXT_FORMATTER.activeApplicationNotFoundOnServer(applicationName, activeApplicationId), var7);
            }
         } catch (NamingException var8) {
            throw new NamingException(TEXT_FORMATTER.noActiveServerApplicationFound(applicationName));
         }
      } catch (NamingException var9) {
         throw new EnvironmentException(TEXT_FORMATTER.noApplicationFoundOnServer(applicationName), var9);
      }
   }

   private Context getRemoteRootContext() throws NamingException {
      return new InitialContext();
   }

   public static void init(String url) {
      if (System.getProperty("java.naming.provider.url") == null) {
         System.setProperty("java.naming.provider.url", url);
      }

      if (System.getProperty("java.naming.factory.initial") == null) {
         System.setProperty("java.naming.factory.initial", "weblogic.jndi.WLInitialContextFactory");
      }

      ClientEnvironment.loadEnvironment();
   }

   public String toString() {
      return super.toString() + "REMOTE: " + getContextContents(this.remoteRootCtx);
   }

   public static String getContextContents(Context ctx) {
      StringBuffer sb = new StringBuffer();
      depth = 0;
      contexts = new HashSet();

      try {
         listContextContents(sb, "  ", "", ctx, System.getProperty("line.separator"));
      } catch (NamingException var3) {
         System.err.println("WARNING: Unable to dump the contents of the JNDI due to the following exception.");
         var3.printStackTrace();
      }

      return sb.toString();
   }

   public static void listContextContents(StringBuffer sb, String indent, String name, Context ctx, String lineSeparator) throws NamingException {
      try {
         Enumeration e = ctx.list(name);

         while(e.hasMoreElements()) {
            NameClassPair ncp = (NameClassPair)e.nextElement();
            name = ncp.getName();
            sb.append(indent + name);

            try {
               Object o = ctx.lookup(name);
               if (o instanceof Context) {
                  contexts.add((Context)o);
                  if (depth++ < 10) {
                     sb.append(lineSeparator);
                     listContextContents(sb, indent + "  ", "", (Context)o, lineSeparator);
                  }
               } else {
                  sb.append(": " + o.getClass().getName() + " -- " + shortDescription(o) + lineSeparator);
               }
            } catch (Throwable var8) {
               sb.append(indent + "While processing name, " + name + ", the following error occurred: " + var8.getMessage() + lineSeparator);
            }
         }
      } catch (NoPermissionException var9) {
         sb.append(indent + "no permission" + lineSeparator);
      } catch (LinkException var10) {
         sb.append(indent + var10.getMessage() + lineSeparator);
      } catch (ConfigurationException var11) {
         sb.append(indent + var11.getMessage() + lineSeparator);
      } catch (NamingException var12) {
         sb.append(indent + var12.getMessage() + lineSeparator);
      }

      --depth;
   }

   private static String shortDescription(Object o) {
      String descString = o.toString();
      int l = descString.length();
      return l < 40 ? descString : descString.substring(0, 39) + "...";
   }
}
