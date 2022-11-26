package weblogic.management.mbeans.custom;

import javax.management.InvalidAttributeValueException;
import weblogic.common.internal.VersionInfo;
import weblogic.diagnostics.i18n.DiagnosticsTextTextFormatter;
import weblogic.diagnostics.logging.LogVariablesImpl;
import weblogic.diagnostics.query.Query;
import weblogic.diagnostics.query.QueryException;
import weblogic.diagnostics.query.QueryFactory;
import weblogic.logging.LoggingConfigurationProcessor;
import weblogic.logging.Severities;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.LogFilterMBean;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.provider.custom.ConfigurationMBeanCustomized;
import weblogic.management.provider.custom.ConfigurationMBeanCustomizer;

public class LogFilter extends ConfigurationMBeanCustomizer {
   private int severityLevel = 16;
   private String[] userIds;
   private String[] subSystems;
   private String filterExpr = "";
   private Query query;
   private static final VersionInfo diabloVersion = new VersionInfo("9.0.0.0");

   public LogFilter(ConfigurationMBeanCustomized base) {
      super(base);
   }

   public int getSeverityLevel() {
      return this.severityLevel;
   }

   public void setSeverityLevel(int severity) {
      this.severityLevel = severity;
      LogFilterMBean filter = (LogFilterMBean)this.getMbean();
      if (this.isDelegateModeEnabled()) {
         String name = filter.getName();
         DomainMBean domain = (DomainMBean)((DomainMBean)this.getMbean().getParent());
         if (domain != null) {
            ServerMBean[] servers = domain.getServers();

            for(int i = 0; i < servers.length; ++i) {
               ServerMBean smb = servers[i];
               LogFilterMBean lf = smb.getLog().getDomainLogBroadcastFilter();
               if (lf != null && lf.getName().equals(name)) {
                  smb.getLog().setDomainLogBroadcastSeverity(Severities.severityNumToString(this.severityLevel));
               }
            }

         }
      }
   }

   public String[] getSubsystemNames() {
      return this.subSystems;
   }

   public void setSubsystemNames(String[] subsystemList) throws InvalidAttributeValueException {
      this.subSystems = subsystemList;
      if (this.isDelegateModeEnabled()) {
         this.computeFilterExpression();
      }

   }

   public String[] getUserIds() {
      return this.userIds;
   }

   public void setUserIds(String[] userIdList) throws InvalidAttributeValueException {
      this.userIds = userIdList;
      if (this.isDelegateModeEnabled()) {
         this.computeFilterExpression();
      }

   }

   protected boolean isDelegateModeEnabled() {
      DomainMBean root = (DomainMBean)this.getMbean().getDescriptor().getRootBean();
      String configurationVersionString = root.getConfigurationVersion();
      if (configurationVersionString == null) {
         return false;
      } else {
         VersionInfo configurationVersion = new VersionInfo(configurationVersionString);
         return !configurationVersion.earlierThan(diabloVersion);
      }
   }

   public String getFilterExpression() {
      return this.filterExpr == null ? "" : this.filterExpr;
   }

   public void setFilterExpression(String queryExp) throws InvalidAttributeValueException {
      try {
         this.query = createQuery(queryExp);
         this.filterExpr = queryExp;
      } catch (QueryException var4) {
         String msg = DiagnosticsTextTextFormatter.getInstance().getInvalidQueryExpressionText(queryExp);
         throw new InvalidAttributeValueException(msg);
      }
   }

   public Query getQuery() {
      return this.query;
   }

   private void computeFilterExpression() throws InvalidAttributeValueException {
      String filterExpr = LoggingConfigurationProcessor.convertOldAttrsToFilterExpression(this.userIds, this.subSystems);
      ((LogFilterMBean)this.getMbean()).setFilterExpression(filterExpr);
   }

   private static Query createQuery(String expr) throws QueryException {
      if (expr != null && expr.length() > 0) {
         LogVariablesImpl lv = LogVariablesImpl.getInstance();
         return QueryFactory.createQuery(lv, lv, expr);
      } else {
         return null;
      }
   }
}
