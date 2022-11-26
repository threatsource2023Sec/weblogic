package com.oracle.weblogic.diagnostics.expressions;

import java.beans.BeanInfo;
import java.util.Locale;
import org.jvnet.hk2.annotations.Contract;

@Contract
public interface ExtensionBeanInfoProvider {
   String getName();

   BeanInfo getBeanInfo(String var1, String var2, Locale var3);

   BeanInfo getBeanInfo(Class var1, Locale var2);
}
