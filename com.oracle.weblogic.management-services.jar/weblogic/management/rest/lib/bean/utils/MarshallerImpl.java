package weblogic.management.rest.lib.bean.utils;

import org.codehaus.jettison.json.JSONObject;
import org.glassfish.admin.rest.model.ResponseBody;
import org.glassfish.admin.rest.model.TypeInfo;

public abstract class MarshallerImpl extends BaseMarshallerImpl implements Marshaller {
   protected MarshallerImpl(String jsonTypeDescription, Class javaType) {
      super(jsonTypeDescription, javaType);
   }

   protected MarshallerImpl(String jsonTypeDescription, Class javaType, TypeInfo docType) {
      super(jsonTypeDescription, javaType, docType);
   }

   public Object marshal(InvocationContext ic, ResponseBody rb, String property, Object javaVal) throws Exception {
      return javaVal != null ? this.marshalNonNull(ic, rb, property, javaVal) : JSONObject.NULL;
   }

   protected Object marshalNonNull(InvocationContext ic, ResponseBody rb, String property, Object javaVal) throws Exception {
      return javaVal;
   }

   public Object getDefaultValue() throws Exception {
      return null;
   }
}
