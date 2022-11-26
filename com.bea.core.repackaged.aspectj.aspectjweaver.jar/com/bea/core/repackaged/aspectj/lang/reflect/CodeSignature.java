package com.bea.core.repackaged.aspectj.lang.reflect;

public interface CodeSignature extends MemberSignature {
   Class[] getParameterTypes();

   String[] getParameterNames();

   Class[] getExceptionTypes();
}
