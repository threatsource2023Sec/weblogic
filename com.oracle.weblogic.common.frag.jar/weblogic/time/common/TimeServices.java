package weblogic.time.common;

import org.jvnet.hk2.annotations.Contract;
import weblogic.common.T3Client;
import weblogic.common.T3ServicesDef;

@Contract
public interface TimeServices {
   void setT3Client(T3Client var1);

   void setT3ServicesDef(T3ServicesDef var1);
}
