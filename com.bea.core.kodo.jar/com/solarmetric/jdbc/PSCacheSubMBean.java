package com.solarmetric.jdbc;

import com.solarmetric.manage.jmx.SubMBeanOperations;
import javax.management.MBeanAttributeInfo;
import javax.management.MBeanOperationInfo;
import javax.management.MBeanParameterInfo;

public class PSCacheSubMBean implements SubMBeanOperations {
   PSCacheConnectionDecorator _pscache;
   String _prefix;

   public PSCacheSubMBean(PSCacheConnectionDecorator pscache, String prefix) {
      this._pscache = pscache;
      this._prefix = prefix;
   }

   public String getPrefix() {
      return this._prefix;
   }

   public Object getSub() {
      return this._pscache;
   }

   public MBeanAttributeInfo[] createMBeanAttributeInfo() {
      return new MBeanAttributeInfo[]{new MBeanAttributeInfo("MaxCachedStatements", "int", "Maximum number of cached PreparedStatements.", true, true, false)};
   }

   public MBeanOperationInfo[] createMBeanOperationInfo() {
      return new MBeanOperationInfo[]{new MBeanOperationInfo("clear", "Clears cache", new MBeanParameterInfo[0], Void.class.getName(), 1)};
   }
}
