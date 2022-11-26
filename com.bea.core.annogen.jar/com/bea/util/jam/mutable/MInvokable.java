package com.bea.util.jam.mutable;

import com.bea.util.jam.JClass;
import com.bea.util.jam.JInvokable;

public interface MInvokable extends JInvokable, MMember {
   void addException(String var1);

   void addException(JClass var1);

   void removeException(String var1);

   void removeException(JClass var1);

   MParameter addNewParameter();

   void removeParameter(MParameter var1);

   MParameter[] getMutableParameters();
}
