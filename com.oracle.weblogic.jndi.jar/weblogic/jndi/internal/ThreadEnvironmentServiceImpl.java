package weblogic.jndi.internal;

import java.util.Collections;
import java.util.Hashtable;
import java.util.Map;
import org.jvnet.hk2.annotations.Service;
import weblogic.jndi.Environment;
import weblogic.jndi.api.ServerEnvironment;
import weblogic.jndi.api.ThreadEnvironmentService;

@Service
public class ThreadEnvironmentServiceImpl implements ThreadEnvironmentService {
   private ThreadEnvironmentServiceImpl() {
   }

   public void push(ServerEnvironment env) {
      ThreadEnvironment.push((Environment)env);
   }

   public ServerEnvironment pop() {
      return ThreadEnvironment.pop();
   }

   public ServerEnvironment get() {
      return ThreadEnvironment.get();
   }

   public Map getEnvironmentProperties() {
      Hashtable ht = ThreadEnvironment.getEnvironmentProperties();
      return ht == null ? null : Collections.unmodifiableMap(ht);
   }
}
