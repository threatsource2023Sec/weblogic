package weblogic.diagnostics.healthcheck;

import java.io.OutputStream;
import java.util.Properties;

public interface HealthCheck {
   String getType();

   String getExtension();

   void initialize();

   boolean execute(Properties var1, OutputStream var2);
}
