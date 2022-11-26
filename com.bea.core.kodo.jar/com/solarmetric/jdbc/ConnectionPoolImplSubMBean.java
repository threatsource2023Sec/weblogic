package com.solarmetric.jdbc;

import com.solarmetric.manage.jmx.SubMBean;
import javax.management.MBeanAttributeInfo;
import org.apache.openjpa.lib.util.Localizer;

public class ConnectionPoolImplSubMBean implements SubMBean {
   private static final Localizer s_loc = Localizer.forPackage(ConnectionPoolImplSubMBean.class);
   private ConnectionPoolImpl _cp;
   private String _prefix;

   public ConnectionPoolImplSubMBean(ConnectionPoolImpl cp, String prefix) {
      this._cp = cp;
      this._prefix = prefix;
   }

   public Object getSub() {
      return this._cp;
   }

   public String getPrefix() {
      return this._prefix;
   }

   public MBeanAttributeInfo[] createMBeanAttributeInfo() {
      return new MBeanAttributeInfo[]{new MBeanAttributeInfo("ValidationSQL", String.class.getName(), this.getMsg("validationsql-desc"), true, false, false), new MBeanAttributeInfo("ValidationTimeout", "int", this.getMsg("validation-timeout-desc"), true, true, false), new MBeanAttributeInfo("RollbackOnReturn", "boolean", this.getMsg("rollback-on-return-desc"), true, true, false), new MBeanAttributeInfo("MaxActive", "int", this.getMsg("max-active-desc"), true, true, false), new MBeanAttributeInfo("MaxIdle", "int", this.getMsg("max-idle-desc"), true, true, false), new MBeanAttributeInfo("MaxWait", "long", this.getMsg("max-wait-desc"), true, true, false), new MBeanAttributeInfo("TestOnBorrow", "boolean", this.getMsg("test-on-borrow-desc"), true, true, false), new MBeanAttributeInfo("TestOnReturn", "boolean", this.getMsg("test-on-return-desc"), true, true, false), new MBeanAttributeInfo("TimeBetweenEvictionRunsMillis", "long", this.getMsg("time-between-eviction-runs-millis-desc"), true, true, false), new MBeanAttributeInfo("NumTestsPerEvictionRun", "int", this.getMsg("num-tests-per-eviction-run-desc"), true, true, false), new MBeanAttributeInfo("MinEvictableIdleTimeMillis", "long", this.getMsg("min-evictable-idle-time-millis-desc"), true, true, false), new MBeanAttributeInfo("TestWhileIdle", "boolean", this.getMsg("test-while-idle-desc"), true, true, false), new MBeanAttributeInfo("WhenExhaustedAction", "byte", "fail=0, block=1, grow=2", true, true, false), new MBeanAttributeInfo("ExceptionAction", "int", "destroy=0, validate=1, none=2", true, true, false), new MBeanAttributeInfo("NumActive", "int", this.getMsg("num-active-desc"), true, false, false), new MBeanAttributeInfo("NumIdle", "int", this.getMsg("num-idle-desc"), true, false, false)};
   }

   private String getMsg(String key) {
      return s_loc.get(key).getMessage();
   }
}
