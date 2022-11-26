package weblogic.connector.configuration.validation.wl;

import java.util.ArrayList;
import java.util.List;
import weblogic.connector.configuration.validation.ValidationContext;
import weblogic.j2ee.descriptor.wl.AdminObjectsBean;
import weblogic.j2ee.descriptor.wl.OutboundResourceAdapterBean;
import weblogic.j2ee.descriptor.wl.ResourceAdapterSecurityBean;
import weblogic.j2ee.descriptor.wl.SecurityWorkContextBean;

public class WLRAValidatorFactory {
   public static List createWLRAValidator(ValidationContext context) {
      List validators = new ArrayList(4);
      WLValidationUtils utils = new WLValidationUtilsImpl();
      WLAdapterValidator wladapterValidator = new WLAdapterValidator(context);
      validators.add(wladapterValidator);
      if (context.getWlraConnectorBean().isAdminObjectsSet()) {
         AdminObjectsBean adminObjs = context.getWlraConnectorBean().getAdminObjects();
         WLAdminObjectValidator adminObjectValidator = new WLAdminObjectValidator(context, adminObjs, utils);
         validators.add(adminObjectValidator);
      }

      if (context.getWlraConnectorBean().isOutboundResourceAdapterSet() && !context.getRaValidationInfo().isLinkRef()) {
         OutboundResourceAdapterBean wlOutboundResourceAdapter = context.getWlraConnectorBean().getOutboundResourceAdapter();
         WLOutboundValidator outboundValidator = new WLOutboundValidator(context, wlOutboundResourceAdapter, utils);
         validators.add(outboundValidator);
      }

      if (context.getWlraConnectorBean().isSecuritySet()) {
         ResourceAdapterSecurityBean securityBean = context.getWlraConnectorBean().getSecurity();
         if (securityBean.isSecurityWorkContextSet()) {
            SecurityWorkContextBean securityWorkContext = securityBean.getSecurityWorkContext();
            validators.add(new WLSecurityWorkContextValidator(context, securityWorkContext));
         }
      }

      return validators;
   }
}
