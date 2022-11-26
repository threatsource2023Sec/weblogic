package weblogic.management.rest.lib.bean.utils;

import org.codehaus.jettison.json.JSONObject;

public class EncryptedPropertyUnmarshaller extends PropertyUnmarshallerImpl {
   public static final EncryptedPropertyUnmarshaller _instance = new EncryptedPropertyUnmarshaller();

   public static EncryptedPropertyUnmarshaller instance() {
      return _instance;
   }

   public boolean matches(InvocationContext ic, Object jsonValue) throws Exception {
      return JSONObject.NULL == jsonValue || jsonValue instanceof String;
   }

   protected PropertyUnmarshaller.Action _action(InvocationContext ic, AttributeType attrType, Object jsonValue) throws Exception {
      return "%1arbitraryvalue1%".equals(jsonValue) ? PropertyUnmarshaller.Action.IGNORE : super._action(ic, attrType, jsonValue);
   }

   protected Object _unmarshal(InvocationContext ic, AttributeType attrType, Object jsonValue) throws Exception {
      return JSONObject.NULL == jsonValue ? null : jsonValue;
   }
}
