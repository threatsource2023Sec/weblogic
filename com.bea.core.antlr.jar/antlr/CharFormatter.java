package antlr;

public interface CharFormatter {
   String escapeChar(int var1, boolean var2);

   String escapeString(String var1);

   String literalChar(int var1);

   String literalString(String var1);
}
