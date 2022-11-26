package weblogic.management.remote.common;

import java.io.IOException;
import java.util.Locale;
import javax.management.MBeanServerConnection;
import javax.management.remote.JMXConnector;
import javax.security.auth.Subject;

public interface WLSJMXConnector extends JMXConnector {
   MBeanServerConnection getMBeanServerConnection(Locale var1) throws IOException;

   MBeanServerConnection getMBeanServerConnection(Subject var1, Locale var2) throws IOException;
}
