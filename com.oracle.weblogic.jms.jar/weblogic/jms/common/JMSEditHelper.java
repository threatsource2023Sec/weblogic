package weblogic.jms.common;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import javax.naming.Context;
import javax.naming.NameNotFoundException;
import javax.naming.NamingException;
import weblogic.kernel.KernelStatus;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.NetworkAccessPointMBean;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.jmx.MBeanServerInvocationHandler;
import weblogic.management.mbeanservers.domainruntime.DomainRuntimeServiceMBean;
import weblogic.management.mbeanservers.edit.ConfigurationManagerMBean;
import weblogic.management.mbeanservers.edit.EditServiceMBean;
import weblogic.management.mbeanservers.runtime.RuntimeServiceMBean;
import weblogic.management.runtime.ServerRuntimeMBean;
import weblogic.rjvm.JVMID;
import weblogic.rmi.extensions.server.Stub;
import weblogic.rmi.spi.Channel;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.auth.login.PasswordCredential;

public class JMSEditHelper {
   private static final String PROTOCOL_PACKAGE = "weblogic.management.remote";
   private static final String EDIT_SERVER = "weblogic.management.mbeanservers.edit";
   private static final String RUNTIME_SERVER = "weblogic.management.mbeanservers.runtime";
   private static final String DOMAIN_RUNTIME_SERVER = "weblogic.management.mbeanservers.domainruntime";
   private static final String PARTITION_JNDINAME = "weblogic.partitionName";
   private static boolean DEBUG;
   private static boolean TEST_CONNECTION_FAILURE;
   private static HashMap connectors;
   private static Object accessLock;

   private static JMXConnector lookupMBeanServerConnection(String jndiName, Context ctx, ServerRuntimeMBean serverRuntimeMBean) throws javax.jms.JMSException {
      JMXConnector connector = null;
      HashMap mapEnv = new HashMap();
      mapEnv.put("jmx.remote.protocol.provider.pkgs", "weblogic.management.remote");
      if (!KernelStatus.isServer()) {
         mapEnv.put("weblogic.context", ctx);
      } else {
         try {
            Hashtable env = ctx.getEnvironment();
            Iterator i = env.entrySet().iterator();

            while(i.hasNext()) {
               Map.Entry e = (Map.Entry)i.next();
               String key = (String)e.getKey();
               Object value = e.getValue();
               if (key != null && value != null) {
                  if (key.equals("java.naming.security.credentials") && value != null && value instanceof PasswordCredential) {
                     value = ((PasswordCredential)value).getPassword();
                  }

                  mapEnv.put(key, value);
               }
            }
         } catch (Throwable var13) {
            var13.printStackTrace();
         }
      }

      try {
         JMXServiceURL serviceURL = populateJMXServiceURL(ctx, jndiName, serverRuntimeMBean);
         connector = findOrCreateJMXConnector(serviceURL, mapEnv);
      } catch (Exception var12) {
         if (serverRuntimeMBean != null) {
            try {
               connector = getJMXConnectorUsingChannels(ctx, getRuntimeService(ctx), mapEnv, populateJMXUrlPath(ctx, "weblogic.management.mbeanservers.edit"));
            } catch (Throwable var11) {
            }
         }

         if (connector == null) {
            throw new JMSException("ERROR: While trying to create a JMX connection: " + var12, var12);
         }
      }

      return connector;
   }

   private static EditServiceMBean getEditService(Context ctx) throws javax.jms.JMSException {
      JMXConnector conn = lookupMBeanServerConnection("weblogic.management.mbeanservers.edit", ctx, getRuntimeService(ctx).getServerRuntime());
      if (conn == null) {
         throw new JMSException("ERROR: Could not get the JMXConnector for MBeanServerConnnection");
      } else {
         ObjectName objectName;
         try {
            objectName = new ObjectName(EditServiceMBean.OBJECT_NAME);
         } catch (MalformedObjectNameException var6) {
            throw new JMSException("ERROR: While trying to get an object name got a malformed object name: " + EditServiceMBean.OBJECT_NAME + " due to: " + var6, var6);
         }

         try {
            EditServiceMBean editServiceMBean = (EditServiceMBean)MBeanServerInvocationHandler.newProxyInstance(conn, objectName);
            return editServiceMBean;
         } catch (Throwable var5) {
            throw new JMSException("ERROR: While trying to get the edit service got a throwable: " + var5, var5);
         }
      }
   }

   public static ConfigurationManagerMBean getConfigurationManager(Context ctx) throws javax.jms.JMSException {
      return getEditService(ctx).getConfigurationManager();
   }

   public static DomainMBean getDomain(Context ctx) throws javax.jms.JMSException {
      return getEditService(ctx).getDomainConfiguration();
   }

   public static RuntimeServiceMBean getRuntimeService(Context ctx) throws javax.jms.JMSException {
      JMXConnector conn = lookupMBeanServerConnection("weblogic.management.mbeanservers.runtime", ctx, (ServerRuntimeMBean)null);
      if (conn == null) {
         throw new JMSException("ERROR: Could not get the JMXConnector for MBeanServerConnnection");
      } else {
         ObjectName objectName;
         try {
            objectName = new ObjectName(RuntimeServiceMBean.OBJECT_NAME);
         } catch (MalformedObjectNameException var6) {
            throw new JMSException("ERROR: While trying to get an object name got a malformed object name: " + RuntimeServiceMBean.OBJECT_NAME + " due to: " + var6, var6);
         }

         try {
            RuntimeServiceMBean runtimeServiceMBean = (RuntimeServiceMBean)MBeanServerInvocationHandler.newProxyInstance(conn, objectName);
            return runtimeServiceMBean;
         } catch (Throwable var5) {
            throw new JMSException("ERROR: While trying to get the edit service got a throwable: " + var5, var5);
         }
      }
   }

   private static final boolean isSecure(String scheme) {
      return scheme.equals("admin") || scheme.endsWith("s");
   }

   private static final boolean isCompatible(NetworkAccessPointMBean nap, String protocol) {
      String napProtocol = nap.getProtocol();
      if (!napProtocol.equals(protocol) && !napProtocol.equals("admin")) {
         return napProtocol.contains(protocol);
      } else {
         return true;
      }
   }

   private static JMXConnector getJMXConnectorUsingChannels(Context ctx, RuntimeServiceMBean runtimeService, HashMap mapEnv, String urlPath) throws NamingException, URISyntaxException, javax.jms.JMSException {
      if (DEBUG) {
         JMSDebug.JMSCommon.debug("Looking for a custom channel to use");
      }

      ServerRuntimeMBean serverRuntimeMBean = runtimeService.getServerRuntime();
      String adminServerName = runtimeService.getDomainConfiguration().getAdminServerName();
      ServerMBean adminServer = runtimeService.getDomainConfiguration().lookupServer(adminServerName);
      String providerURL = (String)ctx.getEnvironment().get("java.naming.provider.url");
      String originalProtocol = providerURL.substring(0, providerURL.indexOf("://"));
      URI u = new URI(providerURL);
      boolean isOriginalURLSecure = isSecure(u.getScheme());
      NetworkAccessPointMBean[] channels = adminServer.getNetworkAccessPoints();
      JMXConnector connector = null;
      if (DEBUG) {
         JMSDebug.JMSCommon.debug("Found " + channels.length + " channels");
      }

      for(int i = 0; i < channels.length; ++i) {
         if ((!isOriginalURLSecure || isSecure(channels[i].getProtocol())) && isCompatible(channels[i], originalProtocol)) {
            String address = channels[i].getPublicAddress();
            int port = channels[i].getPublicPort();
            String protocol = channels[i].getProtocol();
            if (DEBUG) {
               JMSDebug.JMSCommon.debug("Try address = " + address + " port = " + port);
            }

            try {
               JMXServiceURL externalServiceURL = new JMXServiceURL(protocol, address, port, urlPath);
               connector = findOrCreateJMXConnector(externalServiceURL, mapEnv);
               break;
            } catch (Exception var20) {
               address = channels[i].getListenAddress();
               port = channels[i].getListenPort();
               if (DEBUG) {
                  JMSDebug.JMSCommon.debug("Try address = " + address + " port = " + port);
               }

               try {
                  JMXServiceURL externalServiceURL = new JMXServiceURL(protocol, address, port, urlPath);
                  connector = findOrCreateJMXConnector(externalServiceURL, mapEnv);
                  break;
               } catch (Exception var19) {
               }
            }
         }
      }

      if (connector == null) {
         throw new JMSException("ERROR: While trying to create a JMX connection");
      } else {
         return connector;
      }
   }

   public static DomainRuntimeServiceMBean getDomainRuntimeService(Context ctx) throws Exception {
      try {
         String username = (String)ctx.getEnvironment().get("java.naming.security.principal");
         String password = null;
         Object passwordObj = ctx.getEnvironment().get("java.naming.security.credentials");
         if (passwordObj != null && passwordObj instanceof PasswordCredential) {
            password = ((PasswordCredential)passwordObj).getPassword();
         } else {
            password = (String)passwordObj;
         }

         HashMap mapEnv = new HashMap();
         if (username != null) {
            mapEnv.put("java.naming.security.principal", username);
         }

         if (password != null) {
            mapEnv.put("java.naming.security.credentials", password);
         }

         mapEnv.put("jmx.remote.protocol.provider.pkgs", "weblogic.management.remote");
         String urlPath = populateJMXUrlPath(ctx, "weblogic.management.mbeanservers.domainruntime");
         RuntimeServiceMBean runtimeService = getRuntimeService(ctx);
         ServerRuntimeMBean serverRuntimeMBean = runtimeService.getServerRuntime();
         JMXConnector connector = null;

         try {
            String adminServerHost = serverRuntimeMBean.getAdminServerHost();
            int adminServerListenPort = serverRuntimeMBean.getAdminServerListenPort();
            boolean isSecure = serverRuntimeMBean.isAdminServerListenPortSecure();
            JMXServiceURL serviceURL = new JMXServiceURL(isSecure ? "t3s" : "t3", adminServerHost, adminServerListenPort, urlPath);
            connector = findOrCreateJMXConnector(serviceURL, mapEnv);
         } catch (Exception var13) {
            connector = getJMXConnectorUsingChannels(ctx, runtimeService, mapEnv, urlPath);
         }

         DomainRuntimeServiceMBean service = (DomainRuntimeServiceMBean)MBeanServerInvocationHandler.newProxyInstance(connector, new ObjectName(DomainRuntimeServiceMBean.OBJECT_NAME));
         return service;
      } catch (Throwable var14) {
         throw new Exception(var14);
      }
   }

   private static JMXServiceURL populateJMXServiceURL(Context ctx, String jndiName, ServerRuntimeMBean serverRuntimeMBean) throws NamingException, MalformedURLException, javax.jms.JMSException {
      String protocol = "wlx";
      String hostName = "localhost";
      int port = 7001;
      String urlPath = populateJMXUrlPath(ctx, jndiName);
      if (!KernelStatus.isServer()) {
         Object rumtimeMbean = ctx.lookup("weblogic.management.mbeanservers.runtime");
         Object hostID = ((Stub)rumtimeMbean).getRemoteRef().getHostID();
         String providerURL = (String)ctx.getEnvironment().get("java.naming.provider.url");
         protocol = providerURL.substring(0, providerURL.indexOf("://"));
         if (serverRuntimeMBean != null) {
            hostName = serverRuntimeMBean.getAdminServerHost();
            port = serverRuntimeMBean.getAdminServerListenPort();
            protocol = serverRuntimeMBean.isAdminServerListenPortSecure() ? "t3s" : "t3";
         } else if (hostID instanceof JVMID) {
            JVMID jvmID = (JVMID)hostID;
            hostName = jvmID.getHostAddress();
            port = jvmID.getPublicPort();
         } else {
            if (!(hostID instanceof Channel)) {
               throw new javax.jms.JMSException("ERROR: Protocol not supported when creating JMXJMXServiceURL.");
            }

            Channel channel = (Channel)hostID;
            hostName = channel.getPublicAddress();
            port = channel.getPublicPort();
         }
      }

      return new JMXServiceURL(protocol, hostName, port, urlPath);
   }

   private static String populateJMXUrlPath(Context ctx, String jndiName) throws NamingException {
      StringBuilder urlPath = new StringBuilder();
      String partitionName = null;

      try {
         partitionName = (String)ctx.lookup("weblogic.partitionName");
      } catch (NameNotFoundException var5) {
         if (DEBUG) {
            JMSDebug.JMSCommon.debug("populateJMXUrlPath() NameNotFoundException: " + var5.getMessage(), var5);
            JMSDebug.JMSCommon.debug("populateJMXUrlPath() assume talking to server of pre-1221 release");
         }
      }

      if (!PartitionUtils.isDomain(partitionName)) {
         urlPath = urlPath.append("/?partitionName=").append(partitionName);
      }

      urlPath = urlPath.append("/jndi/").append(jndiName);
      return urlPath.toString();
   }

   private static JMXConnector findOrCreateJMXConnector(JMXServiceURL newServiceURL, HashMap newEnv) throws Exception {
      JMXConnector connector = null;
      if (newEnv.get("java.naming.security.principal") == null) {
         AuthenticatedSubject sub = JMSSecurityHelper.getCurrentSubject();
         newEnv.put("subject", sub);
      }

      synchronized(accessLock) {
         if (DEBUG) {
            JMSDebug.JMSCommon.debug("findOrCreateJMXConnector(): serviceURL " + newServiceURL + " newEnv " + newEnv + " connectors size " + connectors.size());
         }

         Iterator it = connectors.entrySet().iterator();
         boolean match = false;

         TableKey tkey;
         while(it.hasNext() && !match) {
            Map.Entry m = (Map.Entry)it.next();
            tkey = (TableKey)m.getKey();
            HashMap oldEnv = tkey.getEnv();
            JMXServiceURL serviceURL = tkey.getServiceURL();
            if (DEBUG) {
               JMSDebug.JMSCommon.debug("findOrCreateJMXConnector(): check match new JMXServiceURL : " + newServiceURL + " with : " + serviceURL + ", key: " + tkey + " oldEnv size:" + oldEnv.size() + " newEnv size:" + newEnv.size());
            }

            if (newServiceURL.equals(serviceURL) && oldEnv.size() == newEnv.size()) {
               match = true;
               Iterator hashIt = oldEnv.entrySet().iterator();
               if (DEBUG) {
                  JMSDebug.JMSCommon.debug("findOrCreateJMXConnector(): JMXServiceURL match " + serviceURL);
                  JMSDebug.JMSCommon.debug("findOrCreateJMXConnector(): compare " + newEnv + " against " + oldEnv);
               }

               while(hashIt.hasNext()) {
                  Map.Entry entry = (Map.Entry)hashIt.next();
                  boolean isSameKey = newEnv.containsKey(entry.getKey());
                  if (DEBUG) {
                     JMSDebug.JMSCommon.debug("findOrCreateJMXConnector(): entry key " + entry.getKey() + " entry value " + entry.getValue() + " isSameKey " + isSameKey);
                  }

                  if (isSameKey && newEnv.get(entry.getKey()).equals(entry.getValue())) {
                     if (DEBUG) {
                        JMSDebug.JMSCommon.debug("findOrCreateJMXConnection() MATCH Key: " + entry.getKey() + " Value " + entry.getValue());
                     }
                  } else {
                     match = false;
                     if (DEBUG) {
                        JMSDebug.JMSCommon.debug("findOrCreateJMXConnection() NO MATCH");
                     }
                  }
               }

               if (match) {
                  try {
                     connector = (JMXConnector)m.getValue();
                     if (TEST_CONNECTION_FAILURE) {
                        JMSDebug.JMSCommon.debug("TEST CONNECTION FAILURE " + connectors);
                        connector.close();
                     }

                     if (DEBUG) {
                        JMSDebug.JMSCommon.debug("findOrCreateJMXConnector() looking up id on  " + connector);
                     }

                     String conId = connector.getConnectionId();
                     if (DEBUG) {
                        JMSDebug.JMSCommon.debug("findOrCreateJMXConnector() found " + conId);
                     }
                  } catch (IOException var25) {
                     IOException ioe = var25;

                     try {
                        if (DEBUG) {
                           JMSDebug.JMSCommon.debug("findOrCreateJMXConnector() IOException: " + ioe.getMessage(), ioe);
                           JMSDebug.JMSCommon.debug("findOrCreateJMXConnector() destroy " + connector);
                        }

                        if (TEST_CONNECTION_FAILURE) {
                           JMSDebug.JMSCommon.debug("TEST CONNECTION FAILURE " + connectors);
                        }

                        connector.close();
                        it.remove();
                     } catch (Exception var23) {
                        if (DEBUG) {
                           JMSDebug.JMSCommon.debug("findOrCreateJMXConnector() ignored exception " + var23.getMessage());
                        }
                     } finally {
                        connector = null;
                        match = false;
                     }
                  } catch (Exception var26) {
                     if (DEBUG) {
                        JMSDebug.JMSCommon.debug("findOrCreateJMXConnector() exception " + var26.getMessage(), var26);
                     }

                     match = false;
                     connector = null;
                  }
               }
            }
         }

         if (DEBUG) {
            JMSDebug.JMSCommon.debug("Connector " + (connector == null ? "NOT " : "") + "found");
         }

         try {
            if (connector == null) {
               Hashtable ht = new Hashtable();
               ht.putAll(newEnv);
               if (DEBUG) {
                  JMSDebug.JMSCommon.debug("findOrCreateJMXConnector() JMXConnectorFactory.connect with serviceURL " + newServiceURL + " env " + ht);
               }

               connector = JMXConnectorFactory.connect(newServiceURL, ht);
               tkey = new TableKey(newServiceURL, newEnv);
               connectors.put(tkey, connector);
               if (DEBUG) {
                  if (connector == null) {
                     JMSDebug.JMSCommon.debug("findOrCreateJMXConnector() null connector returned by JMXConnectorFactory.connect()");
                  } else {
                     JMSDebug.JMSCommon.debug("findOrCreateJMXConnector() created; findOrCreateJMXConnector() connector.id = " + connector.getConnectionId());
                  }
               }
            }
         } catch (Exception var22) {
            if (DEBUG) {
               JMSDebug.JMSCommon.debug("findOrCreateJMXConnector() exception " + var22.getMessage(), var22);
            }

            throw var22;
         }

         if (DEBUG) {
            JMSDebug.JMSCommon.debug("findOrCreateJMXConnector() returning connector");
         }

         return connector;
      }
   }

   static {
      DEBUG = JMSDebug.JMSCommon.isDebugEnabled();
      TEST_CONNECTION_FAILURE = false;
      connectors = new HashMap();
      accessLock = new Object();
   }

   static class TableKey {
      final JMXServiceURL serviceURL;
      final HashMap env;

      TableKey(JMXServiceURL serviceURL, HashMap env) {
         this.serviceURL = serviceURL;
         this.env = env;
      }

      HashMap getEnv() {
         return this.env;
      }

      JMXServiceURL getServiceURL() {
         return this.serviceURL;
      }

      public boolean equals(Object o) {
         if (!(o instanceof TableKey)) {
            return false;
         } else {
            TableKey key = (TableKey)o;
            return key.serviceURL.equals(this.serviceURL) && key.env.equals(this.env);
         }
      }

      public int hashCode() {
         return this.toString().hashCode();
      }

      public String toString() {
         return this.serviceURL.hashCode() + "_" + this.env.hashCode();
      }
   }
}
