package weblogic.diagnostics.debug;

import java.io.Serializable;
import java.util.Map;

public class DebugConfigImpl implements DebugConfig, Serializable {
   private static final long serialVersionUID = -822001747417086460L;
   private static final boolean DEBUG = false;
   private transient SimpleDebugProvider simpleDebugProvider = SimpleDebugProvider.getInstance();

   public String getName() {
      return this.simpleDebugProvider.getName();
   }

   public void setName(String name) {
      this.simpleDebugProvider.setName(name);
   }

   public Map getDebugProperties() {
      return this.simpleDebugProvider.getDebugProperties();
   }

   public void setDebugProperties(Map debugProps) {
      this.simpleDebugProvider.setDebugProperties(debugProps);
   }
}
