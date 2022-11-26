package weblogic.management.rest.lib.bean.utils;

import org.glassfish.admin.rest.model.ResponseBody;

public interface PropertyMarshaller {
   Object marshal(InvocationContext var1, ResponseBody var2, AttributeType var3, boolean var4) throws Exception;

   Marshaller getValueMarshaller() throws Exception;
}
