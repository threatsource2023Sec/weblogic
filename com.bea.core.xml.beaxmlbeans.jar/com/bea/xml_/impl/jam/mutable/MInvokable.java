package com.bea.xml_.impl.jam.mutable;

import com.bea.xml_.impl.jam.JClass;
import com.bea.xml_.impl.jam.JInvokable;

public interface MInvokable extends JInvokable, MMember {
   void addException(String var1);

   void addException(JClass var1);

   void removeException(String var1);

   void removeException(JClass var1);

   MParameter addNewParameter();

   void removeParameter(MParameter var1);

   MParameter[] getMutableParameters();
}
