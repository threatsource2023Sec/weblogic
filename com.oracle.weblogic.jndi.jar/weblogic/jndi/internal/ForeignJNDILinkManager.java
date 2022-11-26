package weblogic.jndi.internal;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NameNotFoundException;
import javax.naming.NamingException;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.BeanUpdateListener;
import weblogic.descriptor.BeanUpdateRejectedException;
import weblogic.jndi.JNDILogger;
import weblogic.management.configuration.ForeignJNDILinkMBean;
import weblogic.management.configuration.ForeignJNDIProviderMBean;
import weblogic.management.utils.ActiveBeanUtil;
import weblogic.security.internal.SerializedSystemIni;
import weblogic.security.internal.encryption.ClearOrEncryptedService;
import weblogic.server.GlobalServiceLocator;
import weblogic.utils.StackTraceUtils;

public class ForeignJNDILinkManager implements BeanUpdateListener {
   private Hashtable jndiEnvironment;
   private Map local2remote = new HashMap();
   private Hashtable pendingChanges = new Hashtable();
   private Context ic;
   private static ClearOrEncryptedService ces = new ClearOrEncryptedService(SerializedSystemIni.getEncryptionService());
   private String partitionName;
   private static final ActiveBeanUtil activeBeanUtil = (ActiveBeanUtil)GlobalServiceLocator.getServiceLocator().getService(ActiveBeanUtil.class, new Annotation[0]);

   public ForeignJNDILinkManager(ForeignJNDIProviderMBean providerMBean, ForeignJNDILinkMBean[] linkMBeans) {
      this.partitionName = getPartitionName(providerMBean);
      this.ic = this.createContext();
      Properties properties = providerMBean.getProperties();
      if (properties != null && properties.size() != 0) {
         this.jndiEnvironment = new Hashtable(properties);
      }

      if (this.jndiEnvironment == null) {
         this.jndiEnvironment = new Hashtable(4);
      }

      String icf = providerMBean.getInitialContextFactory();
      if (icf != null && icf.trim().length() != 0) {
         this.jndiEnvironment.put("java.naming.factory.initial", icf);
      }

      String url = providerMBean.getProviderURL();
      if (url != null && url.trim().length() != 0) {
         this.jndiEnvironment.put("java.naming.provider.url", url);
      }

      String userName = providerMBean.getUser();
      if (userName != null && userName.trim().length() != 0) {
         this.jndiEnvironment.put("java.naming.security.principal", ces.encrypt(userName));
      }

      String password = providerMBean.getPassword();
      if (password != null && password.trim().length() != 0) {
         this.jndiEnvironment.put("java.naming.security.credentials", ces.encrypt(password));
      }

      if (this.jndiEnvironment.size() == 0) {
         this.jndiEnvironment = null;
      }

      providerMBean.addBeanUpdateListener(this);
      if (linkMBeans != null) {
         for(int i = 0; i < linkMBeans.length; ++i) {
            this.local2remote.put(linkMBeans[i].getLocalJNDIName(), linkMBeans[i].getRemoteJNDIName());
            linkMBeans[i].addBeanUpdateListener(this);
         }

         this.bindAll(false);
      }

   }

   private Context createContext() {
      Context ctx = null;

      try {
         Hashtable table = new Hashtable();
         table.put("weblogic.jndi.createIntermediateContexts", "true");
         table.put("weblogic.jndi.replicateBindings", "false");
         table.put("weblogic.jndi.partitionInformation", this.partitionName);
         ctx = new InitialContext(table);
      } catch (NamingException var3) {
         JNDILogger.logCannotCreateInitialContext(StackTraceUtils.throwable2StackTrace(var3));
      }

      return ctx;
   }

   public void bindAll(boolean rebind) {
      Iterator mapEntry = this.local2remote.entrySet().iterator();

      while(mapEntry.hasNext()) {
         Map.Entry e = (Map.Entry)mapEntry.next();
         this.bind(rebind, (String)e.getKey(), (String)e.getValue());
      }

   }

   public void unbindAll() {
      Iterator keys = this.local2remote.keySet().iterator();

      while(keys.hasNext()) {
         String localJNDIName = (String)keys.next();
         this.unbind(localJNDIName);
         keys.remove();
      }

   }

   private void bind(boolean rebind, String localJNDIName, String remoteJNDIName) {
      ForeignOpaqueReference fopqr = new ForeignOpaqueReference(remoteJNDIName, this.jndiEnvironment);

      try {
         if (!rebind) {
            this.ic.bind(localJNDIName, fopqr);
         } else {
            this.ic.rebind(localJNDIName, fopqr);
         }
      } catch (NamingException var6) {
         JNDILogger.logUnableToBind(StackTraceUtils.throwable2StackTrace(var6));
      }

   }

   private void unbind(String localJNDIName) {
      try {
         this.ic.unbind(localJNDIName);
      } catch (NamingException var3) {
         JNDILogger.logUnableToUnBind(StackTraceUtils.throwable2StackTrace(var3));
      }

   }

   private void checkDuplicate(String jndiName) throws BeanUpdateRejectedException {
      if (this.local2remote.get(jndiName) != null) {
         throw new BeanUpdateRejectedException("A JNDI name already Exists");
      } else {
         try {
            if (this.ic.lookup(jndiName) != null) {
               throw new BeanUpdateRejectedException("A JNDI name already exists");
            }
         } catch (NamingException var3) {
            if (!(var3 instanceof NameNotFoundException)) {
               throw new BeanUpdateRejectedException("A JNDI name already exists");
            }
         }
      }
   }

   public void prepareUpdate(BeanUpdateEvent event) throws BeanUpdateRejectedException {
      if (event.getSourceBean() instanceof ForeignJNDIProviderMBean) {
         ForeignJNDIProviderMBean sourceBean = (ForeignJNDIProviderMBean)event.getSourceBean();
         BeanUpdateEvent.PropertyUpdate[] changes = event.getUpdateList();

         for(int i = 0; i < changes.length; ++i) {
            if (changes[i].getAddedObject() instanceof ForeignJNDILinkMBean) {
               ForeignJNDILinkMBean addedBean = (ForeignJNDILinkMBean)changes[i].getAddedObject();
               this.checkDuplicate(addedBean.getLocalJNDIName());
            }
         }
      } else if (event.getSourceBean() instanceof ForeignJNDILinkMBean) {
         ForeignJNDILinkMBean sourceBean = (ForeignJNDILinkMBean)event.getSourceBean();
         ForeignJNDILinkMBean propBean = (ForeignJNDILinkMBean)event.getProposedBean();
         String propJNDIName = propBean.getLocalJNDIName();
         if (!sourceBean.getLocalJNDIName().equals(propJNDIName)) {
            this.checkDuplicate(propJNDIName);
            this.pendingChanges.put(propJNDIName, sourceBean.getLocalJNDIName());
         }
      }

   }

   public void activateUpdate(BeanUpdateEvent event) {
      BeanUpdateEvent.PropertyUpdate[] changes;
      int i;
      if (event.getSourceBean() instanceof ForeignJNDIProviderMBean) {
         ForeignJNDIProviderMBean sourceBean = (ForeignJNDIProviderMBean)event.getSourceBean();
         changes = event.getUpdateList();

         for(i = 0; i < changes.length; ++i) {
            if (changes[i].getAddedObject() instanceof ForeignJNDILinkMBean) {
               this.addLink(changes[i]);
            } else if (changes[i].getRemovedObject() instanceof ForeignJNDILinkMBean) {
               this.removeLink(changes[i]);
            } else {
               this.updateJndiProvider(sourceBean, changes[i]);
            }
         }
      } else if (event.getSourceBean() instanceof ForeignJNDILinkMBean) {
         ForeignJNDILinkMBean sourceBean = (ForeignJNDILinkMBean)event.getSourceBean();
         changes = event.getUpdateList();

         for(i = 0; i < changes.length; ++i) {
            String attributeName = changes[i].getPropertyName();
            if (attributeName.equals("LocalJNDIName")) {
               String propJndiName = sourceBean.getLocalJNDIName();
               String oldJNDI = (String)this.pendingChanges.get(propJndiName);
               this.unbindOldJndi(oldJNDI);
               this.bind(false, propJndiName, sourceBean.getRemoteJNDIName());
            } else if (attributeName.equals("RemoteJNDIName")) {
               this.bind(true, sourceBean.getLocalJNDIName(), sourceBean.getRemoteJNDIName());
            }
         }
      }

   }

   public void rollbackUpdate(BeanUpdateEvent event) {
   }

   private void updateJndiProvider(ForeignJNDIProviderMBean sourceBean, BeanUpdateEvent.PropertyUpdate change) {
      if (this.jndiEnvironment == null) {
         this.jndiEnvironment = new Hashtable(5);
      }

      String attributeName = change.getPropertyName();
      String userName;
      if (attributeName.equals("InitialContextFactory")) {
         userName = sourceBean.getInitialContextFactory();
         if (userName != null && userName.trim().length() != 0) {
            this.jndiEnvironment.put("java.naming.factory.initial", userName);
            this.bindAll(true);
         }
      } else if (attributeName.equals("ProviderURL")) {
         userName = sourceBean.getProviderURL();
         if (userName != null && userName.trim().length() != 0) {
            this.jndiEnvironment.put("java.naming.provider.url", userName);
            this.bindAll(true);
         }
      } else if (attributeName.equals("Password")) {
         userName = sourceBean.getPassword();
         if (userName != null && userName.trim().length() != 0) {
            this.jndiEnvironment.put("java.naming.security.credentials", ces.encrypt(userName));
            this.bindAll(true);
         }
      } else if (attributeName.equals("PasswordEncrypted")) {
         byte[] password = sourceBean.getPasswordEncrypted();
         if (password != null) {
            String pwd = new String(ces.decryptBytes(password));
            this.jndiEnvironment.put("java.naming.security.credentials", ces.encrypt(pwd));
            this.bindAll(true);
         }
      } else if (attributeName.equals("User")) {
         userName = sourceBean.getUser();
         if (userName != null && userName.trim().length() != 0) {
            this.jndiEnvironment.put("java.naming.security.principal", ces.encrypt(userName));
            this.bindAll(true);
         }
      } else if (attributeName.equals("Properties")) {
         Properties properties = sourceBean.getProperties();
         if (properties != null && properties.size() != 0) {
            this.jndiEnvironment.putAll(properties);
            this.bindAll(true);
         }
      }

   }

   private void unbindOldJndi(String localJndiName) {
      this.unbind(localJndiName);
      this.local2remote.remove(localJndiName);
   }

   private void addLink(BeanUpdateEvent.PropertyUpdate change) {
      ForeignJNDILinkMBean linkBean = (ForeignJNDILinkMBean)change.getAddedObject();
      String remoteJNDIName = linkBean.getRemoteJNDIName();
      String localJNDIName = linkBean.getLocalJNDIName();
      this.local2remote.put(localJNDIName, remoteJNDIName);
      this.bind(false, localJNDIName, remoteJNDIName);
      linkBean.addBeanUpdateListener(this);
   }

   private void removeLink(BeanUpdateEvent.PropertyUpdate change) {
      ForeignJNDILinkMBean linkBean = (ForeignJNDILinkMBean)change.getRemovedObject();
      String localJNDIName = linkBean.getLocalJNDIName();
      this.unbind(localJNDIName);
      this.local2remote.remove(localJNDIName);
   }

   static String getPartitionName(ForeignJNDIProviderMBean bean) {
      if (activeBeanUtil.isInPartition(bean)) {
         String beanName = bean.getName();
         int pIndex = beanName.lastIndexOf("$");
         if (pIndex > 0) {
            return beanName.substring(pIndex + 1);
         }
      }

      return "DOMAIN";
   }
}
