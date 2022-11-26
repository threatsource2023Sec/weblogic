package com.solarmetric.jdbc;

import com.solarmetric.manage.jmx.SubMBean;
import java.util.Properties;
import javax.management.MBeanAttributeInfo;
import org.apache.openjpa.lib.util.Localizer;

public class PoolingDataSourceSubMBean implements SubMBean {
   private static final Localizer s_loc = Localizer.forPackage(PoolingDataSourceSubMBean.class);
   PoolingDataSource _ds;
   String _prefix;

   public PoolingDataSourceSubMBean(PoolingDataSource ds, String prefix) {
      this._ds = ds;
      this._prefix = prefix;
   }

   public String getPrefix() {
      return this._prefix;
   }

   public Object getSub() {
      return this._ds;
   }

   public MBeanAttributeInfo[] createMBeanAttributeInfo() {
      return new MBeanAttributeInfo[]{new MBeanAttributeInfo("ConnectionUserName", "java.lang.String", this.getMsg("connection-user-name-desc"), true, false, false), new MBeanAttributeInfo("ConnectionURL", "java.lang.String", this.getMsg("connection-url-desc"), true, false, false), new MBeanAttributeInfo("ConnectionProperties", Properties.class.getName(), this.getMsg("connection-properties-desc"), true, false, false), new MBeanAttributeInfo("ConnectionDriverName", "java.lang.String", this.getMsg("connection-driver-name-desc"), true, false, false), new MBeanAttributeInfo("LoginTimeout", "int", this.getMsg("login-timeout-desc"), true, false, false)};
   }

   private String getMsg(String key) {
      return s_loc.get(key).getMessage();
   }
}
