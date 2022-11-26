package weblogic.application.env.bindings;

import java.util.Map;
import org.jvnet.hk2.annotations.Contract;

@Contract
public interface DefaultBindings {
   String JavaCompNS = "java:comp";

   Map getDefaultBindings();
}
