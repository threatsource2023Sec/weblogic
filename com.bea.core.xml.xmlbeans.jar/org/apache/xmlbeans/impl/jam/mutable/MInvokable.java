package org.apache.xmlbeans.impl.jam.mutable;

import org.apache.xmlbeans.impl.jam.JClass;
import org.apache.xmlbeans.impl.jam.JInvokable;

public interface MInvokable extends JInvokable, MMember {
   void addException(String var1);

   void addException(JClass var1);

   void removeException(String var1);

   void removeException(JClass var1);

   MParameter addNewParameter();

   void removeParameter(MParameter var1);

   MParameter[] getMutableParameters();
}
