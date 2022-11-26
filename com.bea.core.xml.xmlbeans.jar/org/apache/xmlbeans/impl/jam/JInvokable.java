package org.apache.xmlbeans.impl.jam;

public interface JInvokable extends JMember {
   JParameter[] getParameters();

   JClass[] getExceptionTypes();
}
