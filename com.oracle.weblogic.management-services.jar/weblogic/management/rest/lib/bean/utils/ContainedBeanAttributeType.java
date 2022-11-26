package weblogic.management.rest.lib.bean.utils;

import javax.servlet.http.HttpServletRequest;

public interface ContainedBeanAttributeType extends BeanAttributeType, ResourceType {
   MethodType getCreator(HttpServletRequest var1) throws Exception;

   MethodType getCreator(HttpServletRequest var1, String var2) throws Exception;

   MethodType getDestroyer(HttpServletRequest var1) throws Exception;

   ResourceDef getCreateFormResourceDef(String var1) throws Exception;

   String getCreateFormResourceName() throws Exception;
}
