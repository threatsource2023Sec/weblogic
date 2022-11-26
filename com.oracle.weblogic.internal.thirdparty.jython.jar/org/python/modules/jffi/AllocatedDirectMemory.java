package org.python.modules.jffi;

public interface AllocatedDirectMemory extends DirectMemory {
   void free();

   void setAutoRelease(boolean var1);
}
