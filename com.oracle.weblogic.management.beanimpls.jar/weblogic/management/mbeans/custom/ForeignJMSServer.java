package weblogic.management.mbeans.custom;

import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import javax.management.InvalidAttributeValueException;
import weblogic.j2ee.descriptor.wl.ForeignServerBean;
import weblogic.j2ee.descriptor.wl.PropertyBean;
import weblogic.management.DistributedManagementException;
import weblogic.management.EncryptionHelper;
import weblogic.management.WebLogicMBean;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.ForeignJMSConnectionFactoryMBean;
import weblogic.management.configuration.ForeignJMSDestinationMBean;
import weblogic.management.configuration.ForeignJMSServerMBean;
import weblogic.management.configuration.SubDeploymentMBean;
import weblogic.management.configuration.TargetMBean;
import weblogic.management.provider.custom.ConfigurationMBeanCustomized;
import weblogic.management.provider.custom.ConfigurationMBeanCustomizer;
import weblogic.utils.ArrayUtils;

public class ForeignJMSServer extends ConfigurationMBeanCustomizer {
   private static final String TARGETS = "Targets";
   private transient ForeignServerBean delegate;
   private transient SubDeploymentMBean subDeployment;

   public ForeignJMSServer(ConfigurationMBeanCustomized base) {
      super(base);
   }

   public void useDelegates(ForeignServerBean paramDelegate, SubDeploymentMBean paramSubDeployment) {
      this.delegate = paramDelegate;
      this.subDeployment = paramSubDeployment;
   }

   public TargetMBean[] getTargets() {
      if (this.subDeployment == null) {
         Object retVal = this.getValue("Targets");
         if (retVal == null) {
            return new TargetMBean[0];
         } else {
            if (!(retVal instanceof TargetMBean)) {
               if (!(retVal instanceof WebLogicMBean[])) {
                  return new TargetMBean[0];
               }

               WebLogicMBean[] webBeans = (WebLogicMBean[])((WebLogicMBean[])retVal);
               TargetMBean[] converted = new TargetMBean[webBeans.length];

               for(int lcv = 0; lcv < webBeans.length; ++lcv) {
                  WebLogicMBean webBean = webBeans[lcv];
                  if (!(webBean instanceof TargetMBean)) {
                     return new TargetMBean[0];
                  }

                  converted[lcv] = (TargetMBean)webBean;
               }

               retVal = converted;
            }

            return (TargetMBean[])((TargetMBean[])retVal);
         }
      } else {
         return this.subDeployment.getTargets();
      }
   }

   public void setTargets(TargetMBean[] targets) throws InvalidAttributeValueException, DistributedManagementException {
      if (this.subDeployment == null) {
         this.putValueNotify("Targets", targets);
      } else {
         this.subDeployment.setTargets(targets);
      }

   }

   public String getInitialContextFactory() {
      if (this.delegate == null) {
         Object retVal = this.getValue("InitialContextFactory");
         return retVal != null && retVal instanceof String ? (String)retVal : null;
      } else {
         return this.delegate.getInitialContextFactory();
      }
   }

   public void setInitialContextFactory(String value) throws InvalidAttributeValueException {
      if (this.delegate == null) {
         this.putValue("InitialContextFactory", value);
      } else {
         this.delegate.setInitialContextFactory(value);
      }

   }

   public String getConnectionURL() {
      if (this.delegate == null) {
         Object retVal = this.getValue("ConnectionURL");
         return retVal != null && retVal instanceof String ? (String)retVal : null;
      } else {
         return this.delegate.getConnectionURL();
      }
   }

   public void setConnectionURL(String value) throws InvalidAttributeValueException {
      if (this.delegate == null) {
         this.putValue("ConnectionURL", value);
      } else {
         this.delegate.setConnectionURL(value);
      }

   }

   public Properties getJNDIProperties() {
      if (this.delegate == null) {
         Object retVal = this.getValue("JNDIProperties");
         return retVal != null && retVal instanceof Properties ? (Properties)retVal : null;
      } else {
         Properties retVal = new Properties();
         PropertyBean[] properties = this.delegate.getJNDIProperties();
         if (properties != null) {
            for(int lcv = 0; lcv < properties.length; ++lcv) {
               PropertyBean property = properties[lcv];
               retVal.setProperty(property.getKey(), property.getValue());
            }
         }

         return retVal;
      }
   }

   public void setJNDIProperties(Properties value) throws InvalidAttributeValueException {
      String credential;
      PropertyBean property;
      if (this.delegate == null) {
         if (value == null) {
            return;
         }

         Properties localprops = new Properties();
         localprops.putAll(value);
         credential = (String)localprops.remove("java.naming.security.credentials");
         this.putValue("JNDIProperties", localprops);
         property = null;
         if (credential != null) {
            byte[] eBytes = EncryptionHelper.encryptString(credential);
            this.putValue("JNDIPropertiesCredentialEncrypted", eBytes);
         }
      } else {
         PropertyBean[] properties = this.delegate.getJNDIProperties();

         for(int lcv = 0; lcv < properties.length; ++lcv) {
            property = properties[lcv];
            this.delegate.destroyJNDIProperty(property);
         }

         if (value == null) {
            return;
         }

         credential = (String)value.remove("java.naming.security.credentials");
         Set entrySet = value.entrySet();
         Iterator it = entrySet.iterator();

         while(it.hasNext()) {
            Map.Entry mapEntry = (Map.Entry)it.next();
            Object key = mapEntry.getKey();
            String keyString = key == null ? "" : (!(key instanceof String) ? key.toString() : (String)key);
            Object propValue = mapEntry.getValue();
            String valueString = propValue == null ? null : (!(propValue instanceof String) ? propValue.toString() : (String)propValue);
            PropertyBean property = this.delegate.createJNDIProperty(keyString);
            property.setValue(valueString);
         }

         if (credential != null) {
            this.delegate.setJNDIPropertiesCredential(credential);
         }
      }

   }

   public ForeignJMSDestinationMBean[] getDestinations() {
      return ((ForeignJMSServerMBean)this.getMbean()).getForeignJMSDestinations();
   }

   /** @deprecated */
   @Deprecated
   void setDestinations(ForeignJMSDestinationMBean[] destinations) {
      ArrayUtils.computeDiff(this.getDestinations(), destinations, new ArrayUtils.DiffHandler() {
         public void addObject(Object toAdd) {
            ForeignJMSServer.this.addDestination((ForeignJMSDestinationMBean)toAdd);
         }

         public void removeObject(Object toRemove) {
            ForeignJMSServer.this.removeDestination((ForeignJMSDestinationMBean)toRemove);
         }
      });
   }

   /** @deprecated */
   @Deprecated
   public boolean addDestination(ForeignJMSDestinationMBean destination) {
      ForeignJMSServerMBean bean = (ForeignJMSServerMBean)this.getMbean();
      if (bean.lookupForeignJMSDestination(destination.getName()) != null) {
         return true;
      } else {
         ForeignJMSDestination var10000 = (ForeignJMSDestination)this.getMbean().createChildCopyIncludingObsolete("ForeignJMSDestination", destination);
         DomainMBean domain = (DomainMBean)bean.getParentBean();
         domain.destroyForeignJMSDestination(destination);
         return true;
      }
   }

   /** @deprecated */
   @Deprecated
   boolean removeDestination(ForeignJMSDestinationMBean destination) {
      ForeignJMSServerMBean bean = (ForeignJMSServerMBean)this.getMbean();
      DomainMBean domain = (DomainMBean)bean.getParentBean();
      if (bean.lookupForeignJMSDestination(destination.getName()) == null) {
         return true;
      } else {
         domain.createForeignJMSDestination(destination.getName(), destination);
         bean.destroyForeignJMSDestination(destination);
         return true;
      }
   }

   ForeignJMSConnectionFactoryMBean[] getConnectionFactories() {
      return ((ForeignJMSServerMBean)this.getMbean()).getForeignJMSConnectionFactories();
   }

   void setConnectionFactories(ForeignJMSConnectionFactoryMBean[] factories) {
      ArrayUtils.computeDiff(this.getDestinations(), factories, new ArrayUtils.DiffHandler() {
         public void addObject(Object toAdd) {
            ForeignJMSServer.this.addConnectionFactory((ForeignJMSConnectionFactoryMBean)toAdd);
         }

         public void removeObject(Object toRemove) {
            ForeignJMSServer.this.removeConnectionFactory((ForeignJMSConnectionFactoryMBean)toRemove);
         }
      });
   }

   /** @deprecated */
   @Deprecated
   public boolean addConnectionFactory(ForeignJMSConnectionFactoryMBean factory) {
      ForeignJMSServerMBean bean = (ForeignJMSServerMBean)this.getMbean();
      if (bean.lookupForeignJMSConnectionFactory(factory.getName()) != null) {
         return true;
      } else {
         ForeignJMSDestination var10000 = (ForeignJMSDestination)this.getMbean().createChildCopyIncludingObsolete("ForeignConnectionFactory", factory);
         DomainMBean domain = (DomainMBean)bean.getParentBean();
         domain.destroyForeignJMSConnectionFactory(factory);
         return true;
      }
   }

   /** @deprecated */
   @Deprecated
   boolean removeConnectionFactory(ForeignJMSConnectionFactoryMBean factory) {
      ForeignJMSServerMBean bean = (ForeignJMSServerMBean)this.getMbean();
      DomainMBean domain = (DomainMBean)bean.getParentBean();
      if (bean.lookupForeignJMSDestination(factory.getName()) == null) {
         return true;
      } else {
         domain.createForeignJMSConnectionFactory(factory.getName(), factory);
         bean.destroyForeignJMSConnectionFactory(factory);
         return true;
      }
   }

   public ForeignJMSConnectionFactoryMBean createForeignJMSConnectionFactory(String name, ForeignJMSConnectionFactoryMBean toClone) {
      return (ForeignJMSConnectionFactoryMBean)this.getMbean().createChildCopyIncludingObsolete("ForeignJMSConnectionFactory", toClone);
   }

   public ForeignJMSDestinationMBean createForeignJMSDestination(String name, ForeignJMSDestinationMBean toClone) {
      return (ForeignJMSDestinationMBean)this.getMbean().createChildCopyIncludingObsolete("ForeignJMSDestination", toClone);
   }
}
