package weblogic.connector.configuration.meta;

import javax.resource.spi.ConnectionDefinition;
import javax.resource.spi.ManagedConnectionFactory;
import weblogic.connector.exception.RAException;
import weblogic.connector.utils.ValidationMessage;
import weblogic.j2ee.descriptor.ConnectionDefinitionBean;
import weblogic.j2ee.descriptor.OutboundResourceAdapterBean;

@AnnotationProcessorDescription(
   targetAnnotation = ConnectionDefinition.class
)
class ConnectionDefinitionProcessor implements TypeAnnotationProcessor {
   private final ConnectorBeanNavigator beanNavigator;

   ConnectionDefinitionProcessor(ConnectorBeanNavigator connectorBeanNav) {
      this.beanNavigator = connectorBeanNav;
   }

   public void processClass(Class clz, ConnectionDefinition annotation) throws RAException {
      if (!ManagedConnectionFactory.class.isAssignableFrom(clz)) {
         this.beanNavigator.context.warning(ValidationMessage.RAComplianceTextMsg.CONNECTION_MUST_ON_MCF(clz.getName()));
      }

      ConnectionDefinitionBean connectionBean = this.getOrCreateConnectionDefinition(annotation.connectionFactory().getName());
      this.mergeDD(annotation, connectionBean, clz.getName());
   }

   void mergeDD(ConnectionDefinition annotation, ConnectionDefinitionBean connectionBean, String mfc) {
      if (!MetaUtils.isPropertySet(connectionBean, "ManagedConnectionFactoryClass")) {
         connectionBean.setManagedConnectionFactoryClass(mfc);
      }

      if (!MetaUtils.isPropertySet(connectionBean, "ConnectionFactoryImplClass") && annotation.connectionFactoryImpl() != null) {
         connectionBean.setConnectionFactoryImplClass(annotation.connectionFactoryImpl().getName());
      }

      if (!MetaUtils.isPropertySet(connectionBean, "ConnectionInterface") && annotation.connection() != null) {
         connectionBean.setConnectionInterface(annotation.connection().getName());
      }

      if (!MetaUtils.isPropertySet(connectionBean, "ConnectionImplClass") && annotation.connectionImpl() != null) {
         connectionBean.setConnectionImplClass(annotation.connectionImpl().getName());
      }

   }

   ConnectionDefinitionBean getOrCreateConnectionDefinition(String cfInterface) {
      OutboundResourceAdapterBean outboundResourceAdapter = this.beanNavigator.getOrCreateOutboundResourceAdapter();
      ConnectionDefinitionBean[] connectionDefinitions = outboundResourceAdapter.getConnectionDefinitions();
      if (connectionDefinitions == null) {
         connectionDefinitions = new ConnectionDefinitionBean[0];
      }

      ConnectionDefinitionBean[] var4 = connectionDefinitions;
      int var5 = connectionDefinitions.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         ConnectionDefinitionBean connectionDefinitionBean = var4[var6];
         if (cfInterface.equals(connectionDefinitionBean.getConnectionFactoryInterface())) {
            return connectionDefinitionBean;
         }
      }

      ConnectionDefinitionBean createConnectionDefinition = outboundResourceAdapter.createConnectionDefinition();
      createConnectionDefinition.setConnectionFactoryInterface(cfInterface);
      this.beanNavigator.context.readPath("connectionDef", cfInterface);
      return createConnectionDefinition;
   }
}
