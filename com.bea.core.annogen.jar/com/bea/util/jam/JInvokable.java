package com.bea.util.jam;

public interface JInvokable extends JMember {
   JParameter[] getParameters();

   JClass[] getExceptionTypes();
}
