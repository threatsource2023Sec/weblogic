package oracle.ucp;

import java.util.Properties;

public interface ConnectionLabelingCallback {
   int cost(Properties var1, Properties var2);

   boolean configure(Properties var1, Object var2);
}
