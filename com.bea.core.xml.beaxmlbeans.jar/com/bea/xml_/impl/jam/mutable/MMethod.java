package com.bea.xml_.impl.jam.mutable;

import com.bea.xml_.impl.jam.JClass;
import com.bea.xml_.impl.jam.JMethod;

public interface MMethod extends JMethod, MInvokable {
   void setReturnType(String var1);

   void setUnqualifiedReturnType(String var1);

   void setReturnType(JClass var1);
}
