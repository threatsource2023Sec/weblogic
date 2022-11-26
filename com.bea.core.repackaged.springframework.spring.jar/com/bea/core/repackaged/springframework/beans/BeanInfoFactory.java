package com.bea.core.repackaged.springframework.beans;

import com.bea.core.repackaged.springframework.lang.Nullable;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;

public interface BeanInfoFactory {
   @Nullable
   BeanInfo getBeanInfo(Class var1) throws IntrospectionException;
}
