package weblogic.management.rest.lib.bean.utils;

import java.beans.MethodDescriptor;
import java.lang.reflect.Method;

public interface MethodType extends MemberType {
   MethodDescriptor getMethodDescriptor();

   Method getMethod();

   String getDescription();

   String getImpact();
}
