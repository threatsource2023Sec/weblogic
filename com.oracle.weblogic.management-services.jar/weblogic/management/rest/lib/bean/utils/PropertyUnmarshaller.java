package weblogic.management.rest.lib.bean.utils;

public interface PropertyUnmarshaller {
   Action action(InvocationContext var1, AttributeType var2, Object var3) throws Exception;

   boolean matches(InvocationContext var1, Object var2) throws Exception;

   Object unmarshal(InvocationContext var1, AttributeType var2, Object var3) throws Exception;

   public static enum Action {
      SET,
      UNSET,
      IGNORE;
   }
}
