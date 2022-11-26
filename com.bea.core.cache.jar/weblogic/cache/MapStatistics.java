package weblogic.cache;

import java.io.Serializable;

public interface MapStatistics extends Serializable {
   long getQueryCount();

   long getReadCount();

   long getCreateCount();

   long getUpdateCount();

   long getDeleteCount();

   long getClearCount();

   void reset();
}
