package weblogic.connector.configuration.validation;

import java.util.ArrayList;
import java.util.List;
import weblogic.j2ee.descriptor.AdminObjectBean;
import weblogic.j2ee.descriptor.ConnectionDefinitionBean;
import weblogic.j2ee.descriptor.InboundResourceAdapterBean;
import weblogic.j2ee.descriptor.MessageAdapterBean;
import weblogic.j2ee.descriptor.MessageListenerBean;
import weblogic.j2ee.descriptor.OutboundResourceAdapterBean;
import weblogic.j2ee.descriptor.ResourceAdapterBean;

public class RAValidatorFactory {
   public static List createValidators(ValidationContext context) {
      List validators = new ArrayList();
      validators.add(new SchemaValidator(context));
      validators.add(new ConnectorBeanValidator(context));
      ResourceAdapterBean resourceAdapter = context.getConnector().getResourceAdapter();
      if (resourceAdapter != null) {
         OutboundResourceAdapterBean outboundResourceAdapter = resourceAdapter.getOutboundResourceAdapter();
         if (outboundResourceAdapter != null) {
            createOutBoundValidators(context, validators, outboundResourceAdapter);
         }

         createAdminObjectValidators(context, validators, resourceAdapter);
         InboundResourceAdapterBean inboundBean = resourceAdapter.getInboundResourceAdapter();
         if (inboundBean != null) {
            createMessagerListenerValidator(context, validators, inboundBean);
         }
      }

      return validators;
   }

   private static void createMessagerListenerValidator(ValidationContext context, List validators, InboundResourceAdapterBean inboundBean) {
      MessageAdapterBean msgAdapterBean = inboundBean.getMessageAdapter();
      if (msgAdapterBean != null) {
         MessageListenerBean[] listenerBeans = msgAdapterBean.getMessageListeners();
         if (listenerBeans != null && listenerBeans.length > 0) {
            MessageListenerBean[] var5 = listenerBeans;
            int var6 = listenerBeans.length;

            for(int var7 = 0; var7 < var6; ++var7) {
               MessageListenerBean messageListenerBean = var5[var7];
               validators.add(new MessageListenerValidator(context, messageListenerBean));
            }
         }
      }

   }

   private static void createAdminObjectValidators(ValidationContext context, List validators, ResourceAdapterBean resourceAdapter) {
      AdminObjectBean[] adminObjects = resourceAdapter.getAdminObjects();
      if (adminObjects != null && adminObjects.length > 0) {
         AdminObjectBean[] var4 = adminObjects;
         int var5 = adminObjects.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            AdminObjectBean adminObjectBean = var4[var6];
            validators.add(new AdminObjectValidator(context, adminObjectBean));
         }
      }

   }

   private static void createOutBoundValidators(ValidationContext context, List validators, OutboundResourceAdapterBean outboundResourceAdapter) {
      ConnectionDefinitionBean[] connDefs = outboundResourceAdapter.getConnectionDefinitions();
      if (connDefs != null && connDefs.length > 0) {
         ConnectionDefinitionBean[] var4 = connDefs;
         int var5 = connDefs.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            ConnectionDefinitionBean connectionDefinitionBean = var4[var6];
            validators.add(new ConnectionDefinitionValidator(context, connectionDefinitionBean));
         }
      }

   }
}
