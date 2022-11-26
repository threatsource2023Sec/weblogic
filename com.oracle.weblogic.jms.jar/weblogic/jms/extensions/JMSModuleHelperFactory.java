package weblogic.jms.extensions;

import javax.naming.Context;
import weblogic.jms.common.JMSException;
import weblogic.management.configuration.DomainMBean;

public class JMSModuleHelperFactory {
   public IJMSModuleHelper getHelper(Context ctx, IJMSModuleHelper.ScopeType scopeType, String scopeName) throws JMSException {
      if (ctx == null) {
         throw new JMSException("ERROR: context cannot be null");
      } else if (scopeType == null) {
         throw new JMSException("ERROR: scope type cannot be null");
      } else if (scopeType == IJMSModuleHelper.ScopeType.DOMAIN || scopeName != null && !scopeName.trim().equals("")) {
         return new JMSPartitionModuleHelperImpl(ctx, scopeType, scopeName);
      } else {
         throw new JMSException("ERROR: scope name cannot be null or empty in RG/RGT scope");
      }
   }

   public IJMSModuleHelper getHelper(DomainMBean domain, String partitionName, IJMSModuleHelper.ScopeType scopeType, String scopeName) throws JMSException {
      if (domain == null) {
         throw new JMSException("ERROR: DomainMBean cannot be null");
      } else if (scopeType == null) {
         throw new JMSException("ERROR: scope type cannot be null");
      } else if (scopeType == IJMSModuleHelper.ScopeType.DOMAIN || scopeName != null && !scopeName.trim().equals("")) {
         return new JMSPartitionModuleHelperImpl(domain, partitionName, scopeType, scopeName);
      } else {
         throw new JMSException("ERROR: scope name cannot be null or empty in RG/RGT scope");
      }
   }
}
