package weblogic.management.rest.lib.bean.utils;

import javax.servlet.http.HttpServletRequest;
import org.glassfish.admin.rest.model.ApiInfo;
import org.glassfish.admin.rest.model.TypeInfo;

public interface BaseMarshaller {
   Class javaType();

   String describeJavaType();

   String describeJsonType();

   TypeInfo getDocType(HttpServletRequest var1, ApiInfo var2) throws Exception;
}
