package com.bea.xml_.impl.jam;

public interface JInvokable extends JMember {
   JParameter[] getParameters();

   JClass[] getExceptionTypes();
}
