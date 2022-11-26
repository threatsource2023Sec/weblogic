package weblogic.connector.configuration.validation;

import weblogic.connector.external.RAValidationInfo;
import weblogic.connector.external.impl.RAValidationInfoImpl;
import weblogic.connector.utils.ConnectorAPContext;
import weblogic.connector.utils.PropertyNameNormalizer;
import weblogic.connector.utils.PropertyNameNormalizerFactory;
import weblogic.j2ee.descriptor.ConnectorBean;
import weblogic.j2ee.descriptor.wl.WeblogicConnectorBean;

public class ValidationContext {
   private final ClassLoader rarClassloader;
   private final WeblogicConnectorBean wlraConnectorBean;
   private final ValidatingMessageImpl validatingMessages;
   private final RAValidationInfo raValidationInfo;
   private final ConnectorBean connector;
   private final ConnectorAPContext nav;
   private PropertyNameNormalizer propertyNameNormalizer = null;

   public ValidationContext(ClassLoader rarClassloader, ValidatingMessageImpl validatingMessages, RAValidationInfo raValidationInfo, ConnectorBean connector, ConnectorAPContext nav, WeblogicConnectorBean wlraConnectorBean) {
      this.rarClassloader = rarClassloader;
      this.validatingMessages = validatingMessages;
      this.raValidationInfo = raValidationInfo;
      this.connector = connector;
      this.nav = nav;
      this.wlraConnectorBean = wlraConnectorBean;
      String version = "1.6";
      if (connector != null) {
         version = connector.getVersion();
      }

      this.propertyNameNormalizer = PropertyNameNormalizerFactory.getPropertyNameNormalizer(version);
      ((RAValidationInfoImpl)raValidationInfo).setPropertyNameNormalizer(this.propertyNameNormalizer);
   }

   public boolean isJCA16() {
      return this.propertyNameNormalizer.isJCA16();
   }

   public WeblogicConnectorBean getWlraConnectorBean() {
      return this.wlraConnectorBean;
   }

   public ClassLoader getRarClassloader() {
      return this.rarClassloader;
   }

   public ValidatingMessageImpl getValidatingMessages() {
      return this.validatingMessages;
   }

   public RAValidationInfo getRaValidationInfo() {
      return this.raValidationInfo;
   }

   public ConnectorBean getConnector() {
      return this.connector;
   }

   public ConnectorAPContext getConnectorAPContext() {
      return this.nav;
   }

   public PropertyNameNormalizer getPropertyNameNormalizer() {
      return this.propertyNameNormalizer;
   }
}
