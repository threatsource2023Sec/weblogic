package weblogic.connector.configuration.validation;

import java.util.Iterator;
import java.util.List;
import weblogic.j2ee.descriptor.ConfigPropertyBean;
import weblogic.j2ee.descriptor.MessageListenerBean;

class MessageListenerValidator extends PropertyBaseValidator {
   private static final String[] requiredASInterfaces = new String[]{"javax.resource.spi.ActivationSpec"};
   private final MessageListenerBean listenerBean;

   MessageListenerValidator(ValidationContext context, MessageListenerBean listenerBean) {
      super(context);
      this.listenerBean = listenerBean;
   }

   public void doValidate() {
      if (this.listenerBean == null) {
         this.error("General", "General", fmt.ELEMENT_IS_EMPTY("META-INF/ra.xml", "<messagelistener-type>"));
      } else {
         String listenerClass = this.listenerBean.getMessageListenerType();
         String activationSpecClass = this.listenerBean.getActivationSpec().getActivationSpecClass();
         Class asClass;
         String elementName;
         String ddName;
         if (this.isReadFromAnnotation("messageListener", new String[]{listenerClass})) {
            elementName = "@javax.resource.spi.Activation";
            ddName = "Class " + activationSpecClass;
            this.checkClass(elementName, ddName, listenerClass, new String[0], new String[0], false);
            asClass = this.checkClass("@javax.resource.spi.Activation", "Class " + activationSpecClass, activationSpecClass, requiredASInterfaces, new String[0], true, Boolean.FALSE);
            this.validateProperties("General", "General", asClass, this.listenerBean.getActivationSpec().getConfigProperties(), this.getRAValidationInfo().getActivationSpecPropSetterTable(activationSpecClass), "@javax.resource.spi.Activation", activationSpecClass);
         } else {
            elementName = "<messagelistener-type>";
            ddName = "META-INF/ra.xml";
            this.checkClass(elementName, ddName, listenerClass, new String[0], new String[0], false);
            asClass = this.checkClass("<activationspec-class>", "META-INF/ra.xml", activationSpecClass, requiredASInterfaces, new String[0], true, Boolean.FALSE);
            List duplicatedProperties = this.checkForDuplicateProperty("General", "General", "<messagelistener-type>", this.listenerBean.getActivationSpec().getConfigProperties());
            Iterator var7 = duplicatedProperties.iterator();

            while(var7.hasNext()) {
               ConfigPropertyBean bean = (ConfigPropertyBean)var7.next();
               this.listenerBean.getActivationSpec().destroyConfigProperty(bean);
            }

            this.validateProperties("General", "General", asClass, this.listenerBean.getActivationSpec().getConfigProperties(), this.getRAValidationInfo().getActivationSpecPropSetterTable(activationSpecClass), "<messagelistener-type>", "META-INF/ra.xml");
         }

         this.validateAnnotations(asClass, elementName, ddName);
      }
   }

   public int order() {
      return 40;
   }
}
