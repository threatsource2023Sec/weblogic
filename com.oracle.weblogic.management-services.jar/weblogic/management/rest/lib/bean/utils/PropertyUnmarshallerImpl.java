package weblogic.management.rest.lib.bean.utils;

import org.codehaus.jettison.json.JSONObject;
import org.glassfish.admin.rest.utils.ExceptionUtil;

public abstract class PropertyUnmarshallerImpl implements PropertyUnmarshaller {
   public PropertyUnmarshaller.Action action(InvocationContext ic, AttributeType attrType, Object jsonValue) throws Exception {
      if (ic.expandedValues()) {
         return this.isSet(this.getWrappedValue(ic, jsonValue)) ? PropertyUnmarshaller.Action.SET : PropertyUnmarshaller.Action.UNSET;
      } else {
         return this._action(ic, attrType, jsonValue);
      }
   }

   protected PropertyUnmarshaller.Action _action(InvocationContext ic, AttributeType attrType, Object jsonValue) throws Exception {
      if (!String.class.equals(attrType.getPropertyDescriptor().getPropertyType())) {
         return PropertyUnmarshaller.Action.SET;
      } else if (DescriptorUtils.getBooleanField(attrType.getPropertyDescriptor(), "legalNull")) {
         return PropertyUnmarshaller.Action.SET;
      } else if (jsonValue == JSONObject.NULL) {
         return PropertyUnmarshaller.Action.UNSET;
      } else if (!(jsonValue instanceof String)) {
         return PropertyUnmarshaller.Action.SET;
      } else {
         String stringValue = (String)jsonValue;
         return stringValue.length() < 1 ? PropertyUnmarshaller.Action.UNSET : PropertyUnmarshaller.Action.SET;
      }
   }

   public Object unmarshal(InvocationContext ic, AttributeType attrType, Object jsonValue) throws Exception {
      PropertyUnmarshaller.Action action = this.action(ic, attrType, jsonValue);
      if (action != PropertyUnmarshaller.Action.SET) {
         throw new AssertionError("Can't unmarshal for action " + action);
      } else if (ic.expandedValues()) {
         JSONObject wrappedValue = this.getWrappedValue(ic, jsonValue);
         if (!wrappedValue.has("value")) {
            ExceptionUtil.badRequest(MessageUtils.beanFormatter(ic.request()).msgMissingValueProperty());
         }

         boolean isSet = wrappedValue.optBoolean("set", true);
         return isSet ? this._unmarshal(ic, attrType, wrappedValue.get("value")) : true;
      } else {
         return this._unmarshal(ic, attrType, jsonValue);
      }
   }

   protected JSONObject getWrappedValue(InvocationContext ic, Object jsonValue) throws Exception {
      if (!this.checkWrappedValue(jsonValue)) {
         String s = jsonValue != null ? jsonValue.toString() : null;
         ExceptionUtil.badRequest(MessageUtils.beanFormatter(ic.request()).msgInvalidExpandedValue(s));
      }

      return (JSONObject)jsonValue;
   }

   private boolean checkWrappedValue(Object jsonValue) throws Exception {
      if (!(jsonValue instanceof JSONObject)) {
         return false;
      } else {
         JSONObject j = (JSONObject)jsonValue;
         if (j.has("set")) {
            try {
               j.getBoolean("set");
            } catch (Exception var4) {
               return false;
            }
         }

         return !this.isSet(j) || j.has("value");
      }
   }

   protected boolean isSet(JSONObject wrappedValue) throws Exception {
      return wrappedValue.optBoolean("set", true);
   }

   protected abstract Object _unmarshal(InvocationContext var1, AttributeType var2, Object var3) throws Exception;
}
