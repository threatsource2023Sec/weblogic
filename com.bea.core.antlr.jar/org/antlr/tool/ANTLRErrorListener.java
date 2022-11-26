package org.antlr.tool;

public interface ANTLRErrorListener {
   void info(String var1);

   void error(Message var1);

   void warning(Message var1);

   void error(ToolMessage var1);
}
