package weblogic.descriptor;

public class BeanAlreadyExistsException extends IllegalArgumentException {
   private final transient DescriptorBean existingBean;
   private static final BeangenApiTextFormatter textFormatter = BeangenApiTextFormatter.getInstance();

   public BeanAlreadyExistsException(String s) {
      super(s);
      this.existingBean = null;
   }

   public BeanAlreadyExistsException(DescriptorBean existingBean) {
      super(textFormatter.getBeanAlreadyExistsString(existingBean.toString()));
      this.existingBean = existingBean;
   }

   public BeanAlreadyExistsException(String s, DescriptorBean existingBean) {
      super(s);
      this.existingBean = existingBean;
   }

   public DescriptorBean getExistingBean() {
      return this.existingBean;
   }
}
