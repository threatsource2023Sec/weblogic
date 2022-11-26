package com.bea.wls.redef.ant;

import java.util.Hashtable;
import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;

public class FastSwapTask extends Task {
   private static final String MBEANSERVER_JNDI_NAME = "weblogic.management.mbeanservers.runtime";
   private static final int DEFAULT_TIMEOUT = 300;
   private static final int DEFAULT_POLLING_INTERVAL = 1000;
   private static final String REDEFINE = "redefineClasses";
   private static final String CANCEL = "cancel";
   private static final String CANDIDATE_COUNT = "CandidateClassesCount";
   private static final String PROCESSED_COUNT = "ProcessedClassesCount";
   private String adminUrl;
   private String user;
   private String password;
   private String server;
   private String application;
   private String module;
   private String[] classNames;
   private int timeout = 300;
   private boolean failonerror = true;
   private int pollingInterval = 1000;

   public void setAdminUrl(String adminUrl) {
      this.adminUrl = adminUrl;
   }

   public void setUser(String user) {
      this.user = user;
   }

   public void setPassword(String password) {
      this.password = password;
   }

   public void setServer(String server) {
      this.server = server;
   }

   public void setApplication(String application) {
      this.application = application;
   }

   public void setModule(String module) {
      this.module = module;
   }

   public void setClassNames(String names) {
      this.classNames = names.split(",");
      int size = this.classNames != null ? this.classNames.length : 0;

      for(int i = 0; i < size; ++i) {
         this.classNames[i] = this.classNames[i].trim();
      }

   }

   public void setTimeout(int timeout) {
      this.timeout = timeout;
   }

   public void setPollingInterval(int pollingInterval) {
      this.pollingInterval = pollingInterval;
   }

   public void setFailonerror(boolean failonerror) {
      this.failonerror = failonerror;
   }

   private void checkAttribute(StringBuffer buf, String name, String val) {
      if (val == null) {
         if (buf.length() > 0) {
            buf.append(", ");
         }

         buf.append(name);
      }

   }

   private void checkAttributes() throws BuildException {
      StringBuffer buf = new StringBuffer();
      this.checkAttribute(buf, "adminUrl", this.adminUrl);
      this.checkAttribute(buf, "user", this.user);
      this.checkAttribute(buf, "password", this.password);
      this.checkAttribute(buf, "server", this.server);
      this.checkAttribute(buf, "application", this.application);
      if (buf.length() > 0) {
         throw new BuildException("Following required attribute(s) " + buf.toString() + " are not specified.");
      }
   }

   private void printAttributes() {
      this.inform("   adminUrl: " + this.adminUrl);
      this.inform("     server: " + this.server);
      this.inform("application: " + this.application);
      this.inform("     module: " + this.module);
      this.inform("    classes: ");
      int size = this.classNames != null ? this.classNames.length : 0;

      for(int i = 0; i < size; ++i) {
         this.inform("      " + this.classNames[i]);
      }

   }

   private void inform(String msg) {
      System.out.println(msg);
   }

   private JMXConnector getJMXConnector() throws Exception {
      JMXServiceURL serviceURL = new JMXServiceURL("service:jmx:" + this.adminUrl + "/jndi/" + "weblogic.management.mbeanservers.runtime");
      Hashtable h = new Hashtable();
      h.put("java.naming.security.principal", this.user);
      h.put("java.naming.security.credentials", this.password);
      h.put("jmx.remote.protocol.provider.pkgs", "weblogic.management.remote");
      this.inform("Using JMX Connector to connect to " + serviceURL);
      JMXConnector connector = JMXConnectorFactory.connect(serviceURL, h);
      return connector;
   }

   private boolean inProgress(String status) {
      return "RUNNING".equals(status) || "SCHEDULED".equals(status);
   }

   public void execute() throws BuildException {
      this.inform("Performing FastSwap.");
      this.checkAttributes();
      this.printAttributes();
      JMXConnector connector = null;

      try {
         connector = this.getJMXConnector();
         MBeanServerConnection conn = connector.getMBeanServerConnection();
         ObjectName appObj = new ObjectName("com.bea:ServerRuntime=" + this.server + ",Name=" + this.application + ",Type=ApplicationRuntime");
         ObjectName redefObj = (ObjectName)conn.getAttribute(appObj, "ClassRedefinitionRuntime");
         if (redefObj == null) {
            throw new BuildException("Application " + this.application + " not configured for FastSwap.");
         }

         Object[] params = new Object[]{this.module, this.classNames};
         String[] paramTypes = new String[]{String.class.getName(), String[].class.getName()};
         ObjectName task = (ObjectName)conn.invoke(redefObj, "redefineClasses", params, paramTypes);
         String status = (String)conn.getAttribute(task, "Status");
         long now = System.currentTimeMillis();

         for(long endTime = this.timeout > 0 ? now + (long)(this.timeout * 1000) : Long.MAX_VALUE; now < endTime && this.inProgress(status); status = (String)conn.getAttribute(task, "Status")) {
            Thread.sleep((long)this.pollingInterval);
            now = System.currentTimeMillis();
         }

         Integer candidtes = (Integer)conn.getAttribute(task, "CandidateClassesCount");
         Integer processed = (Integer)conn.getAttribute(task, "ProcessedClassesCount");
         this.inform("Processed " + processed + " classes from " + candidtes + " candidate classes.");
         if (this.inProgress(status)) {
            conn.invoke(task, "cancel", new Object[0], new String[0]);
            throw new BuildException("FastSwap task timed out with status " + status);
         }

         if (!"FINISHED".equals(status)) {
            throw new BuildException("FastSwap failed with status: " + status);
         }

         this.inform("FastSwap operation completed successfully.");
      } catch (Exception var23) {
         var23.printStackTrace();
         if (this.failonerror) {
            throw new BuildException(var23);
         }
      } finally {
         if (connector != null) {
            try {
               connector.close();
            } catch (Exception var22) {
            }
         }

         this.inform("----------------------------------------------------");
      }

   }
}
