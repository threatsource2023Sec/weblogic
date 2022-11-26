package oracle.jrockit.jfr.parser;

import java.util.List;

public interface FLRProducer {
   int getId();

   String getName();

   String getDescription();

   String getURIString();

   List getEventInfos();
}
