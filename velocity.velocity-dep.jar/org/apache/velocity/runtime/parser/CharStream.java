package org.apache.velocity.runtime.parser;

import java.io.IOException;

public interface CharStream {
   char readChar() throws IOException;

   /** @deprecated */
   int getColumn();

   /** @deprecated */
   int getLine();

   int getEndColumn();

   int getEndLine();

   int getBeginColumn();

   int getBeginLine();

   void backup(int var1);

   char BeginToken() throws IOException;

   String GetImage();

   char[] GetSuffix(int var1);

   void Done();
}
