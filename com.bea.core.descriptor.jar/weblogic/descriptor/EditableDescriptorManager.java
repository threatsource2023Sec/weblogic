package weblogic.descriptor;

import weblogic.descriptor.internal.ProductionMode;

public class EditableDescriptorManager extends DescriptorManager {
   public EditableDescriptorManager() {
      super(DescriptorClassLoader.getClassLoader(), true);
      this.addInitialNamespace("xsi", "http://www.w3.org/2001/XMLSchema-instance");
   }

   public EditableDescriptorManager(ClassLoader cl) {
      super(cl, true);
   }

   public EditableDescriptorManager(String marshallerConfig) {
      super(getMarshallerClassLoader(marshallerConfig), true);
      this.setProductionMode(ProductionMode.instance().getProductionMode());
   }
}
