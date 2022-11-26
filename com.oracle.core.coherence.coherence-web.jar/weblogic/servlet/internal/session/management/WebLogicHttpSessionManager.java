package weblogic.servlet.internal.session.management;

import com.tangosol.coherence.servlet.SessionHelper;
import com.tangosol.coherence.servlet.SessionReaperStatistics;
import com.tangosol.coherence.servlet.management.HttpSessionManager;
import javax.management.MBeanServer;
import javax.management.ObjectName;
import javax.naming.Context;
import weblogic.servlet.internal.WebAppServletContext;

public class WebLogicHttpSessionManager extends HttpSessionManager implements WebLogicHttpSessionManagerMBean {
   protected WebAppServletContext sci;

   public WebLogicHttpSessionManager(SessionHelper helper, SessionReaperStatistics reaperStatistics, WebAppServletContext sci) {
      super(helper, reaperStatistics);
      this.sci = sci;
   }

   public String getDomainName() {
      try {
         Context envctx = this.sci.getEnvironmentContext();
         MBeanServer server = (MBeanServer)envctx.lookup("comp/env/jmx/runtime");
         ObjectName rs = new ObjectName("com.bea:Name=RuntimeService,Type=weblogic.management.mbeanservers.runtime.RuntimeServiceMBean");
         ObjectName d = (ObjectName)server.getAttribute(rs, "DomainConfiguration");
         return (String)server.getAttribute(d, "Name");
      } catch (Throwable var5) {
         return null;
      }
   }

   public String getServerName() {
      try {
         Context envctx = this.sci.getEnvironmentContext();
         MBeanServer server = (MBeanServer)envctx.lookup("comp/env/jmx/runtime");
         ObjectName rs = new ObjectName("com.bea:Name=RuntimeService,Type=weblogic.management.mbeanservers.runtime.RuntimeServiceMBean");
         ObjectName d = (ObjectName)server.getAttribute(rs, "ServerRuntime");
         return (String)server.getAttribute(d, "Name");
      } catch (Throwable var5) {
         return null;
      }
   }

   public Boolean isEar() {
      return new Boolean(this.sci.getApplicationContext().isEar());
   }

   public String getName() {
      return this.sci.getAppName();
   }

   public String getVersion() {
      return this.sci.getVersionId();
   }

   public String getDeploymentPlanName() {
      return null;
   }

   public String getApplicationId() {
      return this.sci.getApplicationId();
   }

   public String getListenAddress() {
      try {
         Context envctx = this.sci.getEnvironmentContext();
         MBeanServer server = (MBeanServer)envctx.lookup("comp/env/jmx/runtime");
         ObjectName rs = new ObjectName("com.bea:Name=RuntimeService,Type=weblogic.management.mbeanservers.runtime.RuntimeServiceMBean");
         ObjectName d = (ObjectName)server.getAttribute(rs, "ServerRuntime");
         return (String)server.getAttribute(d, "ListenAddress");
      } catch (Throwable var5) {
         return null;
      }
   }

   public Integer getListenPort() {
      try {
         Context envctx = this.sci.getEnvironmentContext();
         MBeanServer server = (MBeanServer)envctx.lookup("comp/env/jmx/runtime");
         ObjectName rs = new ObjectName("com.bea:Name=RuntimeService,Type=weblogic.management.mbeanservers.runtime.RuntimeServiceMBean");
         ObjectName d = (ObjectName)server.getAttribute(rs, "ServerRuntime");
         return (Integer)server.getAttribute(d, "ListenPort");
      } catch (Throwable var5) {
         return null;
      }
   }

   public Boolean isListenPortEnabled() {
      try {
         Context envctx = this.sci.getEnvironmentContext();
         MBeanServer server = (MBeanServer)envctx.lookup("comp/env/jmx/runtime");
         ObjectName rs = new ObjectName("com.bea:Name=RuntimeService,Type=weblogic.management.mbeanservers.runtime.RuntimeServiceMBean");
         ObjectName d = (ObjectName)server.getAttribute(rs, "ServerRuntime");
         return (Boolean)server.getAttribute(d, "ListenPortEnabled");
      } catch (Throwable var5) {
         return new Boolean(false);
      }
   }

   public Boolean isSSLListenPortEnabled() {
      try {
         Context envctx = this.sci.getEnvironmentContext();
         MBeanServer server = (MBeanServer)envctx.lookup("comp/env/jmx/runtime");
         ObjectName rs = new ObjectName("com.bea:Name=RuntimeService,Type=weblogic.management.mbeanservers.runtime.RuntimeServiceMBean");
         ObjectName d = (ObjectName)server.getAttribute(rs, "ServerRuntime");
         return (Boolean)server.getAttribute(d, "SSLListenPortEnabled");
      } catch (Throwable var5) {
         return new Boolean(false);
      }
   }

   public Integer getSSLListenPort() {
      try {
         Context envctx = this.sci.getEnvironmentContext();
         MBeanServer server = (MBeanServer)envctx.lookup("comp/env/jmx/runtime");
         ObjectName rs = new ObjectName("com.bea:Name=RuntimeService,Type=weblogic.management.mbeanservers.runtime.RuntimeServiceMBean");
         ObjectName d = (ObjectName)server.getAttribute(rs, "ServerRuntime");
         return (Integer)server.getAttribute(d, "SSLListenPort");
      } catch (Throwable var5) {
         return null;
      }
   }
}
