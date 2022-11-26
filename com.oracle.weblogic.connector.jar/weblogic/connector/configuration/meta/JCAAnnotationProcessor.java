package weblogic.connector.configuration.meta;

import weblogic.connector.utils.ConnectorAPContext;

public final class JCAAnnotationProcessor extends AnnotationProcessorEngine {
   private final ConnectorBeanNavigator connectorBeanNavigator;

   public JCAAnnotationProcessor(ConnectorBeanNavigator connectorBeanNavigator) {
      this.connectorBeanNavigator = connectorBeanNavigator;
      this.initProcessorRegistry();
   }

   private void initProcessorRegistry() {
      this.registerProcessor(new ConnectorProcessor(this.connectorBeanNavigator));
      ConnectionDefinitionProcessor definitionProcessor = new ConnectionDefinitionProcessor(this.connectorBeanNavigator);
      this.registerProcessor(definitionProcessor);
      this.registerProcessor(new ConnectionDefinitionsProcessor(definitionProcessor));
      this.registerProcessor(new ActivationProcessor(this.connectorBeanNavigator));
      this.registerProcessor(new AdminObjectProcessor(this.connectorBeanNavigator));
   }

   public ConnectorAPContext getAPContext() {
      return this.connectorBeanNavigator.context;
   }
}
