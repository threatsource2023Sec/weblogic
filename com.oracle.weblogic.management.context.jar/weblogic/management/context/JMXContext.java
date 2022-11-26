package weblogic.management.context;

import java.io.Serializable;
import java.util.Locale;
import javax.security.auth.Subject;

public interface JMXContext extends Serializable {
   String JMX_CONTEXT_NAME = "weblogic.management.JMXContext";
   String GLOBAL_PARTITION_NAME = "DOMAIN";

   Locale getLocale();

   void setLocale(Locale var1);

   Subject getSubject();

   void setSubject(Subject var1);

   void setPartitionName(String var1);

   String getPartitionName();
}
