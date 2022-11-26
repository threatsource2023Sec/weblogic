package weblogic.diagnostics.healthcheck;

import java.util.Properties;

public interface HealthCheckMBean {
   String[] getHealthCheckNames();

   int execute(String var1, String[] var2, Properties var3);

   int execute(String var1, Properties var2);
}
