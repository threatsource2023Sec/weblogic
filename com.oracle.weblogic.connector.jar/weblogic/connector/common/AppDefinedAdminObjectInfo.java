package weblogic.connector.common;

import java.util.Iterator;
import java.util.List;
import javax.resource.ResourceException;
import weblogic.connector.ConnectorLogger;
import weblogic.j2ee.descriptor.AdministeredObjectBean;
import weblogic.j2ee.descriptor.PropertyBean;

public class AppDefinedAdminObjectInfo extends AppDefinedObjectInfo {
   final AdminObjectMetaInfo metaInfo;
   final AdministeredObjectBean administeredObjectBean;
   private static final List AdministeredObjectBeanProperties;

   public AppDefinedAdminObjectInfo(UniversalResourceKey key, AdminObjectMetaInfo metaInfo, AdministeredObjectBean administeredObjectBean) {
      super(key, administeredObjectBean.getResourceAdapter());
      this.metaInfo = metaInfo;
      this.administeredObjectBean = administeredObjectBean;
   }

   public void checkCompatible(UniversalResourceKey targetKey, PropertyBean targetBean) throws ResourceException {
      super.checkCompatible(targetKey, targetBean);
      AdministeredObjectBean targetAdminObjBean = (AdministeredObjectBean)targetBean;
      if (!this.compareProperties(this.administeredObjectBean.getProperties(), targetAdminObjBean.getProperties())) {
         throw new ResourceException(ConnectorLogger.getExceptionDuplicatedResourceDefinition(this.resourceAdapter, targetKey.getDefApp(), targetKey.getDefComp(), targetKey.getDefComp(), targetKey.getJndi()));
      } else {
         Iterator var4 = AdministeredObjectBeanProperties.iterator();

         Object expectValue;
         Object value;
         do {
            if (!var4.hasNext()) {
               return;
            }

            PropertyItem property = (PropertyItem)var4.next();
            expectValue = property.invoke(this.administeredObjectBean);
            value = property.invoke(targetBean);
         } while(this.compare(expectValue, value));

         throw new ResourceException(ConnectorLogger.getExceptionDuplicatedResourceDefinition(this.resourceAdapter, targetKey.getDefApp(), targetKey.getDefComp(), targetKey.getDefComp(), targetKey.getJndi()));
      }
   }

   static {
      String[] properties = new String[]{"className", "resourceAdapter", "description", "interfaceName"};
      AdministeredObjectBeanProperties = createBeanProperties(properties, AdministeredObjectBean.class);
   }
}
