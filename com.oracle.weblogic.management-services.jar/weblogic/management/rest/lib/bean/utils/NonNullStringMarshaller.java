package weblogic.management.rest.lib.bean.utils;

import org.codehaus.jettison.json.JSONObject;
import org.glassfish.admin.rest.model.PrimitiveTypeInfo;
import org.glassfish.admin.rest.model.ResponseBody;
import org.glassfish.admin.rest.utils.ExceptionUtil;

public class NonNullStringMarshaller extends BaseMarshallerImpl implements Marshaller, Unmarshaller {
   private static NonNullStringMarshaller _instance = new NonNullStringMarshaller();

   public static NonNullStringMarshaller instance() {
      return _instance;
   }

   private NonNullStringMarshaller() {
      super("string", String.class, PrimitiveTypeInfo.STRING);
   }

   public boolean matches(InvocationContext ic, Object jsonVal) throws Exception {
      return this.isJsonNull(jsonVal) ? true : jsonVal instanceof String;
   }

   public Object unmarshal(InvocationContext ic, Object jsonVal) throws Exception {
      if (!this.matches(ic, jsonVal)) {
         ExceptionUtil.badRequest(this.getTypeMismatchMessage(ic.request(), jsonVal, this.describeJavaType()));
      }

      return this.isJsonNull(jsonVal) ? null : jsonVal;
   }

   public Object marshal(InvocationContext ic, ResponseBody rb, String property, Object javaVal) throws Exception {
      return javaVal == null ? JSONObject.NULL : javaVal;
   }

   public Object getDefaultValue() throws Exception {
      return null;
   }
}
