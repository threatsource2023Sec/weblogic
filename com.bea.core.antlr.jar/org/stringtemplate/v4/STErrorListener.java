package org.stringtemplate.v4;

import org.stringtemplate.v4.misc.STMessage;

public interface STErrorListener {
   void compileTimeError(STMessage var1);

   void runTimeError(STMessage var1);

   void IOError(STMessage var1);

   void internalError(STMessage var1);
}
