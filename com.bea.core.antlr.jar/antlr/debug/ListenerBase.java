package antlr.debug;

import java.util.EventListener;

public interface ListenerBase extends EventListener {
   void doneParsing(TraceEvent var1);

   void refresh();
}
