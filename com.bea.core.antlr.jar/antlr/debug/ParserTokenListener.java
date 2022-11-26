package antlr.debug;

public interface ParserTokenListener extends ListenerBase {
   void parserConsume(ParserTokenEvent var1);

   void parserLA(ParserTokenEvent var1);
}
