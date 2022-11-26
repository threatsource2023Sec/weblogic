package weblogic.restart;

import java.util.Set;
import org.jvnet.hk2.annotations.Contract;

@Contract
public interface RPManager {
   void addRPGroup(String var1, int var2, int var3) throws RPException;

   void removeRPGroup(String var1) throws RPException;

   void addServiceToGroup(String var1, RPService var2) throws RPException;

   void removeServiceFromGroup(String var1, RPService var2) throws RPException;

   Set getServicesFromGroup(String var1) throws RPException;
}
