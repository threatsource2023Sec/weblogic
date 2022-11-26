package weblogic.j2ee.descriptor.wl;

public interface OutboundResourceAdapterBean {
   ConnectionDefinitionPropertiesBean getDefaultConnectionProperties();

   boolean isDefaultConnectionPropertiesSet();

   ConnectionDefinitionBean[] getConnectionDefinitionGroups();

   ConnectionDefinitionBean createConnectionDefinitionGroup();

   void destroyConnectionDefinitionGroup(ConnectionDefinitionBean var1);

   String getId();

   void setId(String var1);
}
