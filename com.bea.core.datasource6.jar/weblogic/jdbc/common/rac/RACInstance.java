package weblogic.jdbc.common.rac;

import java.io.Serializable;
import java.util.Properties;

public interface RACInstance extends Serializable {
   String getService();

   String getInstance();

   String getHost();

   String getDatabase();

   int getPercent();

   void setPercent(int var1);

   boolean getAff();

   void setAff(boolean var1);

   Properties getProperties();

   int getCurrentDrainCount();

   void setCurrentDrainCount(int var1);

   int getIntervalDrainCount();

   void setIntervalDrainCount(int var1);

   RACModuleFailoverEvent getDownEvent();

   void setDownEvent(RACModuleFailoverEvent var1);
}
