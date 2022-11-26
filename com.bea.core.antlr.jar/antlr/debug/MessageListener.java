package antlr.debug;

public interface MessageListener extends ListenerBase {
   void reportError(MessageEvent var1);

   void reportWarning(MessageEvent var1);
}
