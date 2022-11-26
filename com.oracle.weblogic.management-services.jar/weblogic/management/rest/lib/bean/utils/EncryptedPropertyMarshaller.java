package weblogic.management.rest.lib.bean.utils;

import org.codehaus.jettison.json.JSONObject;
import org.glassfish.admin.rest.model.ResponseBody;

public class EncryptedPropertyMarshaller extends PropertyMarshallerImpl {
   private static EncryptedPropertyMarshaller _instance = new EncryptedPropertyMarshaller();

   public static EncryptedPropertyMarshaller instance() {
      return _instance;
   }

   private EncryptedPropertyMarshaller() {
   }

   protected Object _marshal(InvocationContext ic, ResponseBody rb, AttributeType attrType, boolean doAtz) throws Exception {
      return BeanUtils.isBeanPropertySet(ic, attrType, doAtz) ? "%1arbitraryvalue1%" : JSONObject.NULL;
   }

   public Marshaller getValueMarshaller() throws Exception {
      return EncryptedValueMarshaller.instance();
   }
}
