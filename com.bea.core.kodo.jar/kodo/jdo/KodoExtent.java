package kodo.jdo;

import java.util.List;
import javax.jdo.Extent;

public interface KodoExtent extends Extent {
   List list();

   boolean getIgnoreCache();

   void setIgnoreCache(boolean var1);
}
