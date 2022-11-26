package weblogic.management.rest.lib.bean.utils;

import org.codehaus.jettison.json.JSONObject;
import org.glassfish.admin.rest.model.ResponseBody;

public abstract class PropertyMarshallerImpl implements PropertyMarshaller {
   public Object marshal(InvocationContext ic, ResponseBody rb, AttributeType attrType, boolean doAtz) throws Exception {
      Object unwrappedValue = this._marshal(ic, rb, attrType, doAtz);
      if (ic.expandedValues()) {
         JSONObject wrappedValue = new JSONObject();
         wrappedValue.put("set", this.isSet(ic, attrType, doAtz));
         wrappedValue.put("value", unwrappedValue);
         return wrappedValue;
      } else {
         return unwrappedValue;
      }
   }

   protected boolean isSet(InvocationContext ic, AttributeType attrType, boolean doAtz) throws Exception {
      return BeanUtils.isBeanPropertySet(ic, attrType, doAtz);
   }

   protected abstract Object _marshal(InvocationContext var1, ResponseBody var2, AttributeType var3, boolean var4) throws Exception;
}
