package org.mozilla.javascript;

public interface ClassNameHelper {
   ClassOutput getClassOutput();

   String getGeneratingDirectory();

   String getTargetClassFileName();

   String getTargetClassFileName(String var1);

   String getTargetPackage();

   void reset();

   void setClassOutput(ClassOutput var1);

   void setTargetClassFileName(String var1);

   void setTargetExtends(Class var1);

   void setTargetImplements(Class[] var1);

   void setTargetPackage(String var1);
}
