package weblogic.connector.configuration.meta;

import weblogic.j2ee.descriptor.ConnectionDefinitionBean;
import weblogic.j2ee.descriptor.ConnectorBean;
import weblogic.j2ee.descriptor.InboundResourceAdapterBean;
import weblogic.j2ee.descriptor.MessageAdapterBean;
import weblogic.j2ee.descriptor.MessageListenerBean;
import weblogic.j2ee.descriptor.OutboundResourceAdapterBean;
import weblogic.j2ee.descriptor.ResourceAdapterBean;

public final class ConnectorBeanNavigator {
   private final ConnectorBean connectorBean;
   final ConnectorAPContextImpl context = new ConnectorAPContextImpl();

   public ConnectorBeanNavigator(ConnectorBean connectorBean) {
      this.connectorBean = connectorBean;
   }

   public ConnectorBean getConnectorBean() {
      return this.connectorBean;
   }

   public ResourceAdapterBean getOrCreateResourceAdapter() {
      ResourceAdapterBean resourceAdapter = this.connectorBean.getResourceAdapter();
      if (resourceAdapter == null) {
         resourceAdapter = this.connectorBean.createResourceAdapter();
      }

      return resourceAdapter;
   }

   public OutboundResourceAdapterBean getOrCreateOutboundResourceAdapter() {
      ResourceAdapterBean adapterBean = this.getOrCreateResourceAdapter();
      OutboundResourceAdapterBean outboundResourceAdapter = adapterBean.getOutboundResourceAdapter();
      if (outboundResourceAdapter == null) {
         outboundResourceAdapter = adapterBean.createOutboundResourceAdapter();
      }

      return outboundResourceAdapter;
   }

   public ConnectionDefinitionBean[] getConnections() {
      ResourceAdapterBean resourceAdapter = this.connectorBean.getResourceAdapter();
      if (resourceAdapter == null) {
         return null;
      } else {
         OutboundResourceAdapterBean outboundResourceAdapter = resourceAdapter.getOutboundResourceAdapter();
         return outboundResourceAdapter == null ? null : outboundResourceAdapter.getConnectionDefinitions();
      }
   }

   public InboundResourceAdapterBean getOrCreateInboundResourceAdapter() {
      ResourceAdapterBean adapterBean = this.getOrCreateResourceAdapter();
      InboundResourceAdapterBean inboundResourceAdapter = adapterBean.getInboundResourceAdapter();
      if (inboundResourceAdapter == null) {
         inboundResourceAdapter = adapterBean.createInboundResourceAdapter();
      }

      return inboundResourceAdapter;
   }

   public MessageAdapterBean getMessageAdapterBean() {
      ResourceAdapterBean resourceAdapter = this.connectorBean.getResourceAdapter();
      if (resourceAdapter == null) {
         return null;
      } else {
         InboundResourceAdapterBean inboundResourceAdapter = resourceAdapter.getInboundResourceAdapter();
         if (inboundResourceAdapter == null) {
            return null;
         } else {
            MessageAdapterBean messageAdapter = inboundResourceAdapter.getMessageAdapter();
            return messageAdapter;
         }
      }
   }

   public MessageListenerBean createMessageListenerBean() {
      InboundResourceAdapterBean inboundResourceAdapter = this.getOrCreateInboundResourceAdapter();
      MessageAdapterBean messageAdapter = inboundResourceAdapter.getMessageAdapter();
      if (messageAdapter == null) {
         messageAdapter = inboundResourceAdapter.createMessageAdapter();
      }

      MessageListenerBean messageListener = messageAdapter.createMessageListener();
      return messageListener;
   }
}
