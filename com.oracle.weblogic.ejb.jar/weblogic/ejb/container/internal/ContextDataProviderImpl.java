package weblogic.ejb.container.internal;

import com.oracle.pitchfork.interfaces.ContextDataProvider;
import java.util.Map;

public class ContextDataProviderImpl implements ContextDataProvider {
   private static final ContextDataProvider instance = new ContextDataProviderImpl();

   public static ContextDataProvider getInstance() {
      return instance;
   }

   private ContextDataProviderImpl() {
   }

   public Map getContextData() {
      return InvocationContextStack.get().getContextData();
   }
}
