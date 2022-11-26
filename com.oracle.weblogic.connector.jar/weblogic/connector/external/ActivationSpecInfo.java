package weblogic.connector.external;

import java.util.List;
import java.util.Map;

public interface ActivationSpecInfo {
   String getActivationSpecClass();

   List getRequiredProps();

   Map getConfigProps();
}
