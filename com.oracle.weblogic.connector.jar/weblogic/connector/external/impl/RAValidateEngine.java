package weblogic.connector.external.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import weblogic.connector.configuration.validation.RAValidatorFactory;
import weblogic.connector.configuration.validation.ValidatingMessageImpl;
import weblogic.connector.configuration.validation.ValidationContext;
import weblogic.connector.configuration.validation.Validator;
import weblogic.connector.configuration.validation.wl.WLRAValidatorFactory;
import weblogic.connector.external.RAValidationInfo;
import weblogic.connector.utils.ConnectorAPContext;
import weblogic.j2ee.descriptor.ConnectorBean;
import weblogic.j2ee.descriptor.wl.WeblogicConnectorBean;

public class RAValidateEngine {
   private final ConnectorBean connectorBean;
   private final ClassLoader loader;
   private final RAValidationInfo raValidationInfo;
   private final WeblogicConnectorBean wlraConnectorBean;
   private final ConnectorAPContext footPrint;
   private final String[] criticalSubComponents;
   private final Map subComponentsChild2ParentMap;

   public RAValidateEngine(ConnectorBean connectorBean, ClassLoader loader, RAValidationInfo raValidationInfo, WeblogicConnectorBean wlraConnectorBean, ConnectorAPContext footPrint, String[] criticalSubComponents, Map subComponentsChild2ParentMap) {
      this.connectorBean = connectorBean;
      this.loader = loader;
      this.raValidationInfo = raValidationInfo;
      this.wlraConnectorBean = wlraConnectorBean;
      this.footPrint = footPrint;
      this.criticalSubComponents = criticalSubComponents;
      this.subComponentsChild2ParentMap = subComponentsChild2ParentMap;
   }

   public ValidatingMessageImpl validate() {
      List validators = new ArrayList();
      ValidationContext context = this.context();
      validators.addAll(RAValidatorFactory.createValidators(context));
      validators.addAll(WLRAValidatorFactory.createWLRAValidator(context));
      Iterator var3 = validators.iterator();

      while(var3.hasNext()) {
         Validator validator = (Validator)var3.next();
         validator.validate();
      }

      return context.getValidatingMessages();
   }

   private ValidationContext context() {
      ValidatingMessageImpl messages = new ValidatingMessageImpl(this.footPrint, this.criticalSubComponents, this.subComponentsChild2ParentMap);
      return new ValidationContext(this.loader, messages, this.raValidationInfo, this.connectorBean, this.footPrint, this.wlraConnectorBean);
   }
}
