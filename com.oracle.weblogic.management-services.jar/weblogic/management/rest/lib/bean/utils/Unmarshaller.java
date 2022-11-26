package weblogic.management.rest.lib.bean.utils;

public interface Unmarshaller extends BaseMarshaller {
   boolean matches(InvocationContext var1, Object var2) throws Exception;

   Object unmarshal(InvocationContext var1, Object var2) throws Exception;
}
