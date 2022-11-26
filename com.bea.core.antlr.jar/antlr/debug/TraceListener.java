package antlr.debug;

public interface TraceListener extends ListenerBase {
   void enterRule(TraceEvent var1);

   void exitRule(TraceEvent var1);
}
