package weblogic.management.rest.lib.bean.utils;

import org.glassfish.admin.rest.model.PrimitiveTypeInfo;
import org.glassfish.admin.rest.model.ResponseBody;

public class EncryptedValueMarshaller extends MarshallerImpl {
   private static EncryptedValueMarshaller _instance = new EncryptedValueMarshaller();

   public static EncryptedValueMarshaller instance() {
      return _instance;
   }

   private EncryptedValueMarshaller() {
      super("string", String.class, PrimitiveTypeInfo.ENCRYPTED_STRING);
   }

   protected Object marshalNonNull(InvocationContext ic, ResponseBody rb, String property, Object javaVal) throws Exception {
      return "%1arbitraryvalue1%";
   }
}
