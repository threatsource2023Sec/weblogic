package com.bea.core.repackaged.aspectj.weaver;

public interface IUnwovenClassFile {
   String getFilename();

   String getClassName();

   byte[] getBytes();

   char[] getClassNameAsChars();
}
