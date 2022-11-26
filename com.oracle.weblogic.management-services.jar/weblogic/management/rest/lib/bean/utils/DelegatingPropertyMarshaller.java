package weblogic.management.rest.lib.bean.utils;

import org.glassfish.admin.rest.model.ResponseBody;

public class DelegatingPropertyMarshaller extends PropertyMarshallerImpl {
   private Marshaller marshaller;

   public DelegatingPropertyMarshaller(Marshaller marshaller) {
      this.marshaller = marshaller;
   }

   protected Object _marshal(InvocationContext ic, ResponseBody rb, AttributeType attrType, boolean doAtz) throws Exception {
      return this.getValueMarshaller().marshal(ic, rb, attrType.getName(), BeanUtils.getBeanProperty(ic, attrType, doAtz));
   }

   public Marshaller getValueMarshaller() {
      return this.marshaller;
   }
}
