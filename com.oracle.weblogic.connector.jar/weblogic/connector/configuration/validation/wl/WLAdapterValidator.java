package weblogic.connector.configuration.validation.wl;

import weblogic.connector.configuration.validation.ValidationContext;
import weblogic.connector.configuration.validation.ValidationUtils;
import weblogic.j2ee.descriptor.wl.ConfigPropertyBean;
import weblogic.j2ee.descriptor.wl.WeblogicConnectorBean;

class WLAdapterValidator extends DefaultWLRAValidator {
   private final WeblogicConnectorBean wlraConnectorBean;

   WLAdapterValidator(ValidationContext context) {
      super(context);
      this.wlraConnectorBean = context.getWlraConnectorBean();
   }

   public void doValidate() {
      String jndiName = this.wlraConnectorBean.getJNDIName();
      boolean isInbound = ValidationUtils.isInboundAdapter(this.raConnectorBean.getResourceAdapter());
      boolean hasRABean = ValidationUtils.hasResourceAdapterBean(this.raConnectorBean.getResourceAdapter());
      if (isInbound && hasRABean && jndiName == null) {
         this.warning(fmt.MISSING_JNDI_NAME());
      }

      if (jndiName != null && !hasRABean) {
         this.error("Adapter Bean", "General", fmt.MISSING_RA_BEAN());
      }

      this.validateWLProps("Adapter Bean", "General", "<weblogic-connector><properties>", this.wlraConnectorBean.getProperties(), this.raValidationInfo.getRAPropSetterTable());
   }

   public int order() {
      return 110;
   }

   protected void validateProp4AllSetterTable(String elementName, ConfigPropertyBean propBean) {
   }
}
