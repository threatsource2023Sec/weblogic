package com.bea.util.jam.mutable;

import com.bea.util.jam.JClass;
import com.bea.util.jam.JField;

public interface MField extends JField, MMember {
   void setType(String var1);

   void setUnqualifiedType(String var1);

   void setType(JClass var1);

   void setConstantValue(Object var1);

   void setConstantValueExpression(String var1);
}
