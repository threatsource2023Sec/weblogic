package weblogic.management.rest.lib.bean.utils;

import javax.servlet.http.HttpServletRequest;

public interface BeanAttributeType extends AttributeType {
   String getTypeName();

   BeanType getType(HttpServletRequest var1) throws Exception;
}
