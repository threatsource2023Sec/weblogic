package weblogic.application.naming;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import javax.naming.Context;
import javax.naming.NamingException;
import weblogic.common.ResourceException;
import weblogic.j2ee.descriptor.JavaEEPropertyBean;
import weblogic.j2ee.descriptor.MailSessionBean;

public class MailSessionBinder {
   private final Context globalContext;
   private final Context appContext;
   private final Context moduleContext;
   private final Context compContext;
   private final String applicationName;
   private final String moduleName;
   private final String componentName;
   private final List mailSessions;

   public MailSessionBinder(Context globalContext, Context appContext, Context moduleContext, Context compContext, String applicationName, String moduleName, String componentName) {
      this.globalContext = globalContext;
      this.appContext = appContext;
      this.moduleContext = moduleContext;
      this.compContext = compContext;
      this.applicationName = applicationName;
      this.moduleName = moduleName;
      this.componentName = componentName;
      this.mailSessions = new ArrayList();
   }

   public void bindMailSessions(MailSessionBean[] mailSessionBeans) throws NamingException, ResourceException {
      if (mailSessionBeans != null && mailSessionBeans.length != 0) {
         Properties props = new Properties();
         MailSessionBean[] var3 = mailSessionBeans;
         int var4 = mailSessionBeans.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            MailSessionBean msb = var3[var5];
            String name = msb.getName();
            if (!this.mailSessions.contains(name)) {
               JavaEEPropertyBean[] var8 = msb.getProperties();
               int var9 = var8.length;

               for(int var10 = 0; var10 < var9; ++var10) {
                  JavaEEPropertyBean prop = var8[var10];
                  props.put(prop.getName(), prop.getValue());
               }

               String from = msb.getFrom();
               if (from != null && from.length() != 0) {
                  props.put("mail.from", from);
               }

               String host = msb.getHost();
               if (host != null && host.length() != 0) {
                  props.put("mail.host", host);
               }

               String user = msb.getUser();
               if (user != null && user.length() != 0) {
                  props.put("mail.user", user);
               }

               String storeProtocol = msb.getStoreProtocol();
               String transportProtocol;
               if (storeProtocol != null && storeProtocol.length() != 0) {
                  props.put("mail.store.protocol", storeProtocol);
                  transportProtocol = msb.getStoreProtocolClass();
                  if (transportProtocol != null && transportProtocol.length() != 0) {
                     props.put("mail." + storeProtocol + ".class", transportProtocol);
                  }
               }

               transportProtocol = msb.getTransportProtocol();
               if (transportProtocol != null && transportProtocol.length() != 0) {
                  props.put("mail.transport.protocol", transportProtocol);
                  String transportProtocolClass = msb.getTransportProtocolClass();
                  if (transportProtocolClass != null && transportProtocolClass.length() != 0) {
                     props.put("mail." + transportProtocol + ".class", transportProtocolClass);
                  }
               }

               this.bindWithPortableJNDIName(name, new MailSessionReference(msb.getUser(), msb.getPassword(), props));
               this.mailSessions.add(name);
            }
         }

      }
   }

   public void unbindMailSessions() throws NamingException, ResourceException {
      if (!this.mailSessions.isEmpty()) {
         Iterator var1 = this.mailSessions.iterator();

         while(var1.hasNext()) {
            String mailSession = (String)var1.next();
            this.unbindWithPortableJNDIName(mailSession);
         }

      }
   }

   private void bindWithPortableJNDIName(String name, Object value) throws NamingException {
      if (this.isValidJavaCompName(name)) {
         this.compContext.bind(name.substring("java:comp".length() + 1), value);
      } else if (this.isValidJavaModuleName(name)) {
         this.moduleContext.bind(name.substring("java:module".length() + 1), value);
      } else if (this.isValidJavaAppName(name)) {
         this.appContext.bind(name.substring("java:app".length() + 1), value);
      } else if (this.isJavaGlobalName(name)) {
         this.globalContext.bind(name.substring("java:global".length() + 1), value);
      } else {
         Context compEnvContext = this.findCompEnvContext();
         if (compEnvContext != null) {
            compEnvContext.bind(name, value);
         }
      }

   }

   private void unbindWithPortableJNDIName(String name) throws NamingException {
      if (this.isValidJavaCompName(name)) {
         this.compContext.unbind(name.substring("java:comp".length() + 1));
      } else if (this.isValidJavaModuleName(name)) {
         this.moduleContext.unbind(name.substring("java:module".length() + 1));
      } else if (this.isValidJavaAppName(name)) {
         this.appContext.unbind(name.substring("java:app".length() + 1));
      } else if (this.isJavaGlobalName(name)) {
         this.globalContext.unbind(name.substring("java:global".length() + 1));
      } else {
         Context compEnvContext = this.findCompEnvContext();
         if (compEnvContext != null) {
            compEnvContext.unbind(name);
         }
      }

   }

   private boolean isJavaGlobalName(String name) {
      return name.startsWith("java:global") && this.globalContext != null;
   }

   private boolean isValidJavaAppName(String name) {
      return name.startsWith("java:app") && this.appContext != null;
   }

   private boolean isValidJavaModuleName(String name) {
      return name.startsWith("java:module") && this.moduleContext != null;
   }

   private boolean isValidJavaCompName(String name) {
      return name.startsWith("java:comp") && this.compContext != null;
   }

   private Context findCompEnvContext() {
      if (this.compContext == null) {
         return null;
      } else {
         try {
            return (Context)this.compContext.lookup("env");
         } catch (NamingException var2) {
            return null;
         }
      }
   }
}
