package weblogic.management.rest.lib.bean.utils;

import javax.servlet.http.HttpServletRequest;

public interface ContainedBeansType extends BeansAttributeType, ContainedBeanAttributeType {
   ResourceDef getChildResourceDef(String var1) throws Exception;

   MethodType getFinder(HttpServletRequest var1) throws Exception;
}
