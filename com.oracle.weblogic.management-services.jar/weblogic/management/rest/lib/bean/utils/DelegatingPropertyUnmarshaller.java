package weblogic.management.rest.lib.bean.utils;

public class DelegatingPropertyUnmarshaller extends PropertyUnmarshallerImpl {
   private Unmarshaller unmarshaller;

   public DelegatingPropertyUnmarshaller(Unmarshaller unmarshaller) {
      this.unmarshaller = unmarshaller;
   }

   public boolean matches(InvocationContext ic, Object jsonValue) throws Exception {
      return this.unmarshaller.matches(ic, jsonValue);
   }

   protected Object _unmarshal(InvocationContext ic, AttributeType attrType, Object jsonValue) throws Exception {
      return this.unmarshaller.unmarshal(ic, jsonValue);
   }
}
