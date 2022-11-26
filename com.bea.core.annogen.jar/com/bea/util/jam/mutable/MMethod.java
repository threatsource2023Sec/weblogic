package com.bea.util.jam.mutable;

import com.bea.util.jam.JClass;
import com.bea.util.jam.JMethod;

public interface MMethod extends JMethod, MInvokable {
   void setReturnType(String var1);

   void setUnqualifiedReturnType(String var1);

   void setReturnType(JClass var1);
}
