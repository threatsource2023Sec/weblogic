package org.mozilla.javascript;

public interface SourceTextItem {
   int INITED = 0;
   int PARTIAL = 1;
   int COMPLETED = 2;
   int ABORTED = 3;
   int FAILED = 4;
   int CLEARED = 5;
   int INVALID = 6;

   boolean abort();

   boolean append(char var1);

   boolean append(String var1);

   boolean append(char[] var1, int var2, int var3);

   boolean clear();

   boolean complete();

   boolean fail();

   int getAlterCount();

   String getName();

   int getStatus();

   String getText();

   boolean invalidate();

   boolean isValid();
}
