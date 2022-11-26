package weblogic.management.rest.lib.bean.utils;

import org.glassfish.admin.rest.model.ResponseBody;

public interface Marshaller extends BaseMarshaller {
   Object marshal(InvocationContext var1, ResponseBody var2, String var3, Object var4) throws Exception;

   Object getDefaultValue() throws Exception;
}
