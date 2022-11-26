package oracle.jrockit.jfr.parser;

import java.net.URI;
import java.util.List;

public interface FLREventInfo {
   int getId();

   String getName();

   String getDescription();

   String getPath();

   URI getURI();

   boolean hasStackTrace();

   boolean hasThread();

   boolean hasStartTime();

   List getValueInfos();
}
