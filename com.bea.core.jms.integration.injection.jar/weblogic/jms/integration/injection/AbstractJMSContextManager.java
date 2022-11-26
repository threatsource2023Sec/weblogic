package weblogic.jms.integration.injection;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.annotation.PreDestroy;
import javax.jms.ConnectionFactory;
import javax.jms.JMSContext;

public abstract class AbstractJMSContextManager {
   protected final Map contexts = new HashMap();

   public synchronized JMSContext getContext(String id, JMSContextMetadata metadata, ConnectionFactory connectionFactory) {
      JMSContextEntry contextEntry = (JMSContextEntry)this.contexts.get(id);
      JMSContext context;
      if (contextEntry == null) {
         context = this.createContext(metadata, connectionFactory);
         this.contexts.put(id, new JMSContextEntry(context));
      } else {
         context = contextEntry.getContext();
      }

      return context;
   }

   protected JMSContext createContext(JMSContextMetadata metadata, ConnectionFactory connectionFactory) {
      int sessionMode = metadata.getSessionMode();
      String userName = metadata.getUserName();
      JMSContext context;
      if (userName == null) {
         context = connectionFactory.createContext(sessionMode);
      } else {
         String password = metadata.getPassword();
         context = connectionFactory.createContext(userName, password, sessionMode);
      }

      return context;
   }

   public JMSContext getContext(String id) {
      JMSContextEntry entry = (JMSContextEntry)this.contexts.get(id);
      JMSContext context = null;
      if (entry != null) {
         context = entry.getContext();
      }

      return context;
   }

   @PreDestroy
   public synchronized void cleanup() {
      Iterator var1 = this.contexts.entrySet().iterator();

      while(var1.hasNext()) {
         Map.Entry entry = (Map.Entry)var1.next();
         JMSContextEntry contextEntry = (JMSContextEntry)entry.getValue();
         JMSContext context = contextEntry.getContext();
         if (context != null) {
            context.close();
         }
      }

      this.contexts.clear();
   }
}
