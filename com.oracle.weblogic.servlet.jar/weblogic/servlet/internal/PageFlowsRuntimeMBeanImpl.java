package weblogic.servlet.internal;

import java.util.HashMap;
import java.util.Iterator;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.management.ManagementException;
import weblogic.management.runtime.PageFlowRuntimeMBean;
import weblogic.management.runtime.PageFlowsRuntimeMBean;
import weblogic.management.runtime.RuntimeMBean;
import weblogic.management.runtime.RuntimeMBeanDelegate;

public class PageFlowsRuntimeMBeanImpl extends RuntimeMBeanDelegate implements PageFlowsRuntimeMBean {
   private static final long serialVersionUID = 1L;
   private HashMap _pageFlows;
   private String _serverName;
   private String _httpServerName;
   private String _contextPath;
   private String _applicationName;
   static DebugLogger logger = DebugLogger.getDebugLogger("DebugPageFlowMonitoring");

   public PageFlowsRuntimeMBeanImpl(String serverName, String httpServerName, String contextPath, String applicationName, RuntimeMBean parent) throws ManagementException {
      super(serverName + "_" + httpServerName + "_" + contextPath, parent);
      this._serverName = serverName;
      this._httpServerName = httpServerName;
      this._contextPath = contextPath;
      this._applicationName = applicationName;
      this._pageFlows = new HashMap();
   }

   public void reset() {
      synchronized(this._pageFlows) {
         Iterator iter = this._pageFlows.values().iterator();

         while(iter.hasNext()) {
            PageFlowRuntimeMBean pageFlow = (PageFlowRuntimeMBean)iter.next();
            pageFlow.reset();
         }

      }
   }

   public PageFlowRuntimeMBean[] getPageFlows() {
      synchronized(this._pageFlows) {
         return (PageFlowRuntimeMBean[])((PageFlowRuntimeMBean[])this._pageFlows.values().toArray(new PageFlowRuntimeMBean[this._pageFlows.size()]));
      }
   }

   public PageFlowRuntimeMBean getPageFlow(String pageFlowClassName) {
      synchronized(this._pageFlows) {
         return (PageFlowRuntimeMBean)this._pageFlows.get(pageFlowClassName);
      }
   }

   public void addPageFlow(String pageFlowClassName, String[] actions) {
      synchronized(this._pageFlows) {
         try {
            PageFlowRuntimeMBeanImpl pageFlowRuntimeMBean = (PageFlowRuntimeMBeanImpl)this._pageFlows.get(pageFlowClassName);
            if (pageFlowRuntimeMBean == null) {
               pageFlowRuntimeMBean = new PageFlowRuntimeMBeanImpl(this._serverName, this._httpServerName, pageFlowClassName, this);
               pageFlowRuntimeMBean.setActions(actions);
               this._pageFlows.put(pageFlowClassName, pageFlowRuntimeMBean);
            }
         } catch (ManagementException var6) {
            logger.debug("Unable to add PageFlow: " + pageFlowClassName, var6);
         }

      }
   }

   public String getServerName() {
      return this._serverName;
   }

   public String getHttpServerName() {
      return this._httpServerName;
   }

   public String getContextPath() {
      return this._contextPath;
   }

   public String getAppName() {
      return this._applicationName;
   }
}
