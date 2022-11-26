package weblogic.diagnostics.context;

import java.util.Map;

public interface CorrelationIntegrationManager {
   void clearCorrelation();

   Correlation findCorrelation();

   Correlation findOrCreateCorrelation();

   void setCorrelationEnabled(boolean var1);

   void setDMSCorrelationCallback(CorrelationCallback var1);

   Correlation newCorrelation();

   Correlation newCorrelation(String var1, int[] var2, int var3, Map var4, long var5, boolean var7);

   void activateCorrelation(Correlation var1);

   public static final class Factory {
      public static CorrelationIntegrationManager getInstance() {
         return CorrelationIntegrationManagerImpl.getInstance();
      }
   }
}
