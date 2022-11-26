package weblogic.management.rest.lib.bean.utils;

import org.glassfish.admin.rest.model.TypeInfo;
import org.glassfish.admin.rest.utils.ExceptionUtil;

public abstract class UnmarshallerImpl extends BaseMarshallerImpl implements Unmarshaller {
   protected UnmarshallerImpl(String jsonTypeDescription, Class javaType) {
      super(jsonTypeDescription, javaType);
   }

   protected UnmarshallerImpl(String jsonTypeDescription, Class javaType, TypeInfo docType) {
      super(jsonTypeDescription, javaType, docType);
   }

   public boolean matches(InvocationContext ic, Object jsonVal) throws Exception {
      return this.isJsonNull(jsonVal) ? true : this.matchesNonNull(ic, jsonVal);
   }

   protected boolean matchesNonNull(InvocationContext ic, Object jsonVal) throws Exception {
      return false;
   }

   public Object unmarshal(InvocationContext ic, Object jsonVal) throws Exception {
      if (!this.matches(ic, jsonVal)) {
         ExceptionUtil.badRequest(this.getTypeMismatchMessage(ic.request(), jsonVal, this.describeJavaType()));
      }

      return this._unmarshal(ic, jsonVal);
   }

   protected Object _unmarshal(InvocationContext ic, Object jsonVal) throws Exception {
      return this.isJsonNull(jsonVal) ? null : this.unmarshalNonNull(ic, jsonVal);
   }

   protected Object unmarshalNonNull(InvocationContext ic, Object jsonVal) throws Exception {
      return jsonVal;
   }
}
