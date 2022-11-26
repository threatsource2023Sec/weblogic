package weblogic.connector.outbound;

import java.util.Iterator;
import java.util.List;
import javax.resource.ResourceException;
import weblogic.connector.ConnectorLogger;
import weblogic.connector.common.AppDefinedObjectInfo;
import weblogic.connector.common.PropertyItem;
import weblogic.connector.common.UniversalResourceKey;
import weblogic.j2ee.descriptor.ConnectionFactoryResourceBean;
import weblogic.j2ee.descriptor.PropertyBean;

public class AppDefinedConnectionFactoryInfo extends AppDefinedObjectInfo {
   final ConnectionFactoryMetaInfo metaInfo;
   final ConnectionFactoryResourceBean cfBean;
   private static final List ConnectionFactoryBeanProperties;

   public AppDefinedConnectionFactoryInfo(UniversalResourceKey key, ConnectionFactoryMetaInfo metaInfo, ConnectionFactoryResourceBean cfBean) {
      super(key, cfBean.getResourceAdapter());
      this.metaInfo = metaInfo;
      this.cfBean = cfBean;
   }

   public void checkCompatible(UniversalResourceKey targetKey, PropertyBean targetBean) throws ResourceException {
      super.checkCompatible(targetKey, targetBean);
      ConnectionFactoryResourceBean targetCFBean = (ConnectionFactoryResourceBean)targetBean;
      if (!this.compareProperties(targetCFBean.getProperties(), this.cfBean.getProperties())) {
         throw new ResourceException(ConnectorLogger.getExceptionDuplicatedResourceDefinition(this.resourceAdapter, targetKey.getDefApp(), targetKey.getDefComp(), targetKey.getDefComp(), targetKey.getJndi()));
      } else {
         Iterator var4 = ConnectionFactoryBeanProperties.iterator();

         Object expectValue;
         Object value;
         do {
            if (!var4.hasNext()) {
               return;
            }

            PropertyItem property = (PropertyItem)var4.next();
            expectValue = property.invoke(this.cfBean);
            value = property.invoke(targetBean);
         } while(this.compare(expectValue, value));

         throw new ResourceException(ConnectorLogger.getExceptionDuplicatedResourceDefinition(this.resourceAdapter, targetKey.getDefApp(), targetKey.getDefComp(), targetKey.getDefComp(), targetKey.getJndi()));
      }
   }

   static {
      String[] properties = new String[]{"interfaceName", "resourceAdapter", "description", "maxPoolSize", "minPoolSize", "transactionSupport"};
      ConnectionFactoryBeanProperties = createBeanProperties(properties, ConnectionFactoryResourceBean.class);
   }
}
