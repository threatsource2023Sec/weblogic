package com.bea.util.jam.mutable;

import com.bea.util.jam.JClass;
import com.bea.util.jam.JParameter;

public interface MParameter extends JParameter, MMember {
   void setType(String var1);

   void setType(JClass var1);

   void setUnqualifiedType(String var1);
}
