package com.oracle.weblogic.diagnostics.watch.beans.jmx;

import com.oracle.weblogic.diagnostics.expressions.AdminServer;
import com.oracle.weblogic.diagnostics.expressions.ExpressionBean;
import com.oracle.weblogic.diagnostics.expressions.ManagedServer;
import com.oracle.weblogic.diagnostics.expressions.WLDFI18n;
import com.oracle.weblogic.diagnostics.watch.MetricRuleType;
import java.lang.management.ManagementFactory;
import javax.inject.Singleton;
import javax.management.MBeanServerConnection;
import org.jvnet.hk2.annotations.Service;

@ExpressionBean(
   name = "platform",
   prefix = "wls"
)
@Service(
   name = "platform"
)
@Singleton
@AdminServer
@ManagedServer
@MetricRuleType
@WLDFI18n(
   resourceBundle = "com.oracle.weblogic.diagnostics.watch.beans.jmx.AbstractJMXMetricSource"
)
public class JMXPlatformMBeanServerSource extends AbstractJMXMetricSource {
   private JMXPlatformMBeanServerSource() {
   }

   protected MBeanServerConnection lookupMBeanServerReference() {
      return ManagementFactory.getPlatformMBeanServer();
   }

   public String getName() {
      return "platform";
   }
}
