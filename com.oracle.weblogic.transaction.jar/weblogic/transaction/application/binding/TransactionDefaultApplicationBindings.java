package weblogic.transaction.application.binding;

import java.util.HashMap;
import java.util.Map;
import javax.naming.LinkRef;
import org.jvnet.hk2.annotations.Service;
import weblogic.application.env.bindings.DefaultBindings;

@Service
public class TransactionDefaultApplicationBindings implements DefaultBindings {
   public static final String LOCAL_USER_TX_BINDING = "java:comp/UserTransaction";
   private String USER_TX_BINDING = "javax/transaction/UserTransaction";
   public static final String LOCAL_TM_BINDING = "java:comp/TransactionSynchronizationRegistry";
   private String TM_BINDING = "javax/transaction/TransactionManager";
   private Map defaultBindings = new HashMap();

   public TransactionDefaultApplicationBindings() {
      this.defaultBindings.put("java:comp/UserTransaction", new LinkRef(this.USER_TX_BINDING));
      this.defaultBindings.put("java:comp/TransactionSynchronizationRegistry", new LinkRef(this.TM_BINDING));
   }

   public Map getDefaultBindings() {
      return this.defaultBindings;
   }
}
