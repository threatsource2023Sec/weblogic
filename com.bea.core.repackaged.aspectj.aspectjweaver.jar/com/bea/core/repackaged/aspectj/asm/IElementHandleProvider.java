package com.bea.core.repackaged.aspectj.asm;

import com.bea.core.repackaged.aspectj.bridge.ISourceLocation;
import java.io.File;

public interface IElementHandleProvider {
   String createHandleIdentifier(ISourceLocation var1);

   String createHandleIdentifier(File var1, int var2, int var3, int var4);

   String createHandleIdentifier(IProgramElement var1);

   String getFileForHandle(String var1);

   int getLineNumberForHandle(String var1);

   int getOffSetForHandle(String var1);

   void initialize();
}
