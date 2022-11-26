package com.bea.xml_.impl.jam.mutable;

import com.bea.xml_.impl.jam.JClass;
import com.bea.xml_.impl.jam.JParameter;

public interface MParameter extends JParameter, MMember {
   void setType(String var1);

   void setType(JClass var1);

   void setUnqualifiedType(String var1);
}
