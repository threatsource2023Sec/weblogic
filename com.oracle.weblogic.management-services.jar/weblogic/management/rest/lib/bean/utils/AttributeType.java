package weblogic.management.rest.lib.bean.utils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;

public interface AttributeType extends MemberType {
   PropertyDescriptor getPropertyDescriptor();

   Method getReader();

   Method getWriter();

   boolean isWritable();

   boolean isRestartRequired();

   String getDescription();
}
