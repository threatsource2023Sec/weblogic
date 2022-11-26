package org.antlr.stringtemplate;

import java.io.IOException;

public interface StringTemplateWriter {
   int NO_WRAP = -1;

   void pushIndentation(String var1);

   String popIndentation();

   void pushAnchorPoint();

   void popAnchorPoint();

   void setLineWidth(int var1);

   int write(String var1) throws IOException;

   int write(String var1, String var2) throws IOException;

   int writeWrapSeparator(String var1) throws IOException;

   int writeSeparator(String var1) throws IOException;
}
