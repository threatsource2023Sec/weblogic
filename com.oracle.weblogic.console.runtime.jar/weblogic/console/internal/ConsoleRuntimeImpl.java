package weblogic.console.internal;

import java.io.File;
import java.net.MalformedURLException;
import java.security.AccessController;
import weblogic.Home;
import weblogic.management.ManagementException;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.provider.ManagementService;
import weblogic.management.runtime.ConsoleRuntimeMBean;
import weblogic.management.runtime.DomainRuntimeMBean;
import weblogic.management.runtime.RuntimeMBeanDelegate;
import weblogic.protocol.ServerURL;
import weblogic.protocol.configuration.ChannelHelper;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.utils.http.HttpParsing;

public class ConsoleRuntimeImpl extends RuntimeMBeanDelegate implements ConsoleRuntimeMBean {
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static final char SPECIAL = '\\';
   private static final char SEPARATOR = ';';
   private static final char NULL = '0';

   static void initialize() throws ManagementException {
      if (ManagementService.getRuntimeAccess(kernelId).isAdminServer()) {
         new ConsoleRuntimeImpl(getDomainRuntimeMBean());
      }

   }

   private ConsoleRuntimeImpl(DomainRuntimeMBean parent) throws ManagementException {
      super(parent.getName(), parent);
      parent.setConsoleRuntime(this);
   }

   public boolean isEnabled() {
      File consoleapp = new File(Home.getPath() + File.separator + "lib" + File.separator + "consoleapp");
      return !consoleapp.exists() ? false : getDomainMBean().isConsoleEnabled();
   }

   public String getHomePageURL() {
      return this.getBaseConsoleURL() + this.getPage("HomePage1");
   }

   public String getSpecificPageURL(String page, String[] context) {
      if (page != null && page.length() >= 1) {
         String url = this.getBaseConsoleURL() + this.getPage(page);
         if (context != null && context.length > 1) {
            url = url + this.getHandle(context);
         } else {
            url = url + this.getHandle(this.getObjectNameContext(getDomainObjectName()));
         }

         return url;
      } else {
         throw new IllegalArgumentException("Page must not be null or empty.");
      }
   }

   public String[] getSpecificPageURLs(String page, String[][] contexts) {
      if (page != null && page.length() >= 1) {
         if (contexts == null) {
            throw new IllegalArgumentException("Contexts must not be null.");
         } else {
            String[] urls = new String[contexts.length];

            for(int i = 0; i < contexts.length; ++i) {
               try {
                  urls[i] = this.getSpecificPageURL(page, contexts[i]);
               } catch (IllegalArgumentException var6) {
                  throw new IllegalArgumentException("Problem with contexts[" + i + "]", var6);
               }
            }

            return urls;
         }
      } else {
         throw new IllegalArgumentException("Page must not be null or empty.");
      }
   }

   public String[] getSpecificPageURLs(String[] pages, String[][] contexts) {
      if (pages != null && contexts != null && pages.length == contexts.length) {
         String[] urls = new String[contexts.length];

         for(int i = 0; i < contexts.length; ++i) {
            try {
               urls[i] = this.getSpecificPageURL(pages[i], contexts[i]);
            } catch (IllegalArgumentException var6) {
               throw new IllegalArgumentException("Problem with pages/contexts[" + i + "]", var6);
            }
         }

         return urls;
      } else {
         throw new IllegalArgumentException("Pages and contexts must not be null and must be the same length.");
      }
   }

   public String getDefaultPageURL(String[] context, String perspective) {
      if (context != null && context.length >= 1) {
         String url = this.getBaseConsoleURL() + this.getPage("DispatcherPage") + this.getHandle(context);
         if (perspective != null && perspective.length() > 0) {
            url = url + "&perspective=" + perspective;
         }

         return url;
      } else {
         throw new IllegalArgumentException("Context must not be null or empty.");
      }
   }

   public String[] getDefaultPageURLs(String[][] contexts, String perspective) {
      if (contexts == null) {
         throw new IllegalArgumentException("Contexts must not be null.");
      } else {
         String[] urls = new String[contexts.length];

         for(int i = 0; i < contexts.length; ++i) {
            try {
               urls[i] = this.getDefaultPageURL(contexts[i], perspective);
            } catch (IllegalArgumentException var6) {
               throw new IllegalArgumentException("Problem with contexts[" + i + "]", var6);
            }
         }

         return urls;
      }
   }

   public String[] getDefaultPageURLs(String[][] contexts, String[] perspectives) {
      if (contexts != null && perspectives != null && contexts.length == perspectives.length) {
         String[] urls = new String[contexts.length];

         for(int i = 0; i < contexts.length; ++i) {
            try {
               urls[i] = this.getDefaultPageURL(contexts[i], perspectives[i]);
            } catch (IllegalArgumentException var6) {
               throw new IllegalArgumentException("Problem with contexts/perspectives[" + i + "]", var6);
            }
         }

         return urls;
      } else {
         throw new IllegalArgumentException("Contexts and perspectives must not be null and must be the same length.");
      }
   }

   public String[] getObjectNameContext(String objectName) {
      if (objectName != null && objectName.length() >= 1) {
         return new String[]{"com.bea.console.handles.JMXHandle", objectName};
      } else {
         throw new IllegalArgumentException("ObjectName must not be null or empty.");
      }
   }

   private String getPage(String page) {
      return "?_nfpb=true&_pageLabel=" + page;
   }

   private String getHandle(String[] context) {
      if (context != null && context.length >= 1) {
         StringBuffer sb = new StringBuffer();
         sb.append(context[0]);
         sb.append("(\"");

         for(int i = 1; i < context.length; ++i) {
            if (i > 1) {
               sb.append(";");
            }

            String component = context[i];
            if (component == null) {
               sb.append('\\');
               sb.append('0');
            } else {
               for(int j = 0; j < component.length(); ++j) {
                  char c = component.charAt(j);
                  if (c == '\\') {
                     sb.append('\\');
                     sb.append('\\');
                  } else if (c == ';') {
                     sb.append('\\');
                     sb.append(';');
                  } else {
                     sb.append(c);
                  }
               }
            }
         }

         sb.append("\")");
         return "&handle=" + HttpParsing.escape(sb.toString(), "UTF-8");
      } else {
         throw new IllegalArgumentException("context must include at least one string : received " + this.getContextString(context));
      }
   }

   private String getBaseConsoleURL() {
      try {
         ServerURL url = new ServerURL(ChannelHelper.getLocalAdministrationURL());
         String protocol = url.getProtocol();
         String consoleProtocol = !"https".equalsIgnoreCase(protocol) && !"t3s".equalsIgnoreCase(protocol) && !"admin".equalsIgnoreCase(protocol) ? "http" : "https";
         return consoleProtocol + "://" + url.getHost() + ":" + url.getPort() + "/" + getDomainMBean().getConsoleContextPath() + "/console.portal";
      } catch (MalformedURLException var4) {
         throw new RuntimeException(var4);
      }
   }

   private static DomainRuntimeMBean getDomainRuntimeMBean() {
      return ManagementService.getDomainAccess(kernelId).getDomainRuntime();
   }

   private static DomainMBean getDomainMBean() {
      return ManagementService.getRuntimeAccess(kernelId).getDomain();
   }

   private static String getDomainObjectName() {
      return "com.bea:Name=" + ManagementService.getRuntimeAccess(kernelId).getDomainName() + ",Type=Domain";
   }

   private String getContextString(String[] context) {
      if (context == null) {
         return null;
      } else {
         StringBuffer sb = new StringBuffer();
         sb.append("[");

         for(int i = 0; i < context.length; ++i) {
            if (i > 0) {
               sb.append(",");
            }

            sb.append(context[i]);
         }

         sb.append("]");
         return sb.toString();
      }
   }
}
