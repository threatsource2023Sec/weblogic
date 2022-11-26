package weblogic.application.naming;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.naming.Context;
import javax.naming.Name;
import javax.naming.NamingException;
import javax.sql.DataSource;
import weblogic.application.internal.FlowContext;
import weblogic.common.ResourceException;
import weblogic.descriptor.DescriptorManager;
import weblogic.j2ee.descriptor.ApplicationClientBean;
import weblogic.j2ee.descriptor.DataSourceBean;
import weblogic.j2ee.descriptor.JavaEEPropertyBean;
import weblogic.jdbc.common.internal.DataSourceService;
import weblogic.jndi.OpaqueReference;
import weblogic.kernel.KernelStatus;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;

public class DataSourceOpaqueReference implements OpaqueReference, Externalizable {
   private transient FlowContext appCtx;
   private transient String key;
   private transient Map dataSourceInfo;
   private transient DataSource dataSource;
   private transient String applicationName;
   private transient String moduleName;
   private transient String componentName;
   private static final transient BeanInfo dataSourceBeanInfo = getDataSourceBeanInfo();
   private static final transient Map propDescriptorsByName = new HashMap() {
      {
         PropertyDescriptor[] var1 = DataSourceOpaqueReference.dataSourceBeanInfo.getPropertyDescriptors();
         int var2 = var1.length;

         for(int var3 = 0; var3 < var2; ++var3) {
            PropertyDescriptor pd = var1[var3];
            this.put(pd.getName(), pd);
         }

      }
   };
   private static transient DataSourceBinder dataSourceBinder;
   private static final transient Map dataSourceRefsByKey = new HashMap();
   private static final AuthenticatedSubject kernelIdentity = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

   private static BeanInfo getDataSourceBeanInfo() {
      try {
         return Introspector.getBeanInfo(DataSourceBean.class);
      } catch (IntrospectionException var1) {
         throw new AssertionError(var1);
      }
   }

   private static String toKey(String appName, String compName, String jndiName) {
      if (jndiName.startsWith("java:global")) {
         return "_WL_GLOBAL_" + jndiName;
      } else if (jndiName.startsWith("java:app")) {
         return "_WL_" + appName + "_" + jndiName;
      } else if (jndiName.startsWith("java:comp")) {
         return "_WL_" + compName + jndiName;
      } else {
         throw new IllegalArgumentException("Expecting a name in java:global, java:app or java:comp");
      }
   }

   public DataSourceOpaqueReference() {
   }

   protected DataSourceOpaqueReference(FlowContext appCtx, DataSource aDataSource, DataSourceBean dataSourceBean, String anAppName, String aModuleName, String aComponentName) {
      this.appCtx = appCtx;
      this.dataSource = aDataSource;
      this.dataSourceInfo = extractDataSourceInfo(dataSourceBean);
      if (dataSourceBean != null) {
         this.key = toKey(anAppName, aComponentName, dataSourceBean.getName());
         dataSourceRefsByKey.put(this.key, this);
      }

      this.applicationName = anAppName;
      this.moduleName = aModuleName;
      this.componentName = aComponentName;
   }

   public static void bootStrapOnClient(DataSourceBinder aDataSourceBinder) {
      dataSourceBinder = aDataSourceBinder;
   }

   public static DataSourceBean getDataSourceBeanForName(String appName, String compName, String jndiName) {
      DataSourceOpaqueReference opaqueReference = (DataSourceOpaqueReference)dataSourceRefsByKey.get(toKey(appName, compName, jndiName));
      return opaqueReference == null ? null : toDataSourceBean(opaqueReference.dataSourceInfo);
   }

   private static Map extractDataSourceInfo(DataSourceBean dataSourceBean) {
      if (dataSourceBean == null) {
         return null;
      } else {
         Map info = new HashMap();
         PropertyDescriptor[] var2 = dataSourceBeanInfo.getPropertyDescriptors();
         int var3 = var2.length;

         int var4;
         for(var4 = 0; var4 < var3; ++var4) {
            PropertyDescriptor pd = var2[var4];

            try {
               if (!pd.isHidden()) {
                  info.put(pd.getName(), (Serializable)pd.getReadMethod().invoke(dataSourceBean));
               }
            } catch (IllegalArgumentException var7) {
            } catch (IllegalAccessException var8) {
            } catch (InvocationTargetException var9) {
               throw new AssertionError(var9);
            }
         }

         HashMap propertyMap = new HashMap();
         info.put("properties", propertyMap);
         JavaEEPropertyBean[] var11 = dataSourceBean.getProperties();
         var4 = var11.length;

         for(int var12 = 0; var12 < var4; ++var12) {
            JavaEEPropertyBean prop = var11[var12];
            propertyMap.put(prop.getName(), prop.getValue());
         }

         return info;
      }
   }

   public Object getReferent(Name name, Context ctx) throws NamingException {
      if (this.dataSource == null) {
         if (!KernelStatus.isServer()) {
            if (dataSourceBinder == null) {
               throw new IllegalStateException("You must first use the bootStrapOnClient method");
            }

            try {
               return dataSourceBinder.createAndBindDataSource((DataSourceService)null, toDataSourceBean(this.dataSourceInfo));
            } catch (ResourceException var4) {
               throw new NamingException(var4.getMessage());
            }
         }

         this.dataSource = this.createDataSource();
      }

      return this.dataSource;
   }

   private DataSource createDataSource() throws NamingException {
      try {
         return (DataSource)this.appCtx.getSecurityProvider().invokePrivilegedAction(kernelIdentity, new PrivilegedExceptionAction() {
            public Object run() throws PrivilegedActionException {
               try {
                  return DataSourceBinder.createDataSource(DataSourceOpaqueReference.toDataSourceBean(DataSourceOpaqueReference.this.dataSourceInfo), DataSourceOpaqueReference.this.applicationName, DataSourceOpaqueReference.this.moduleName, DataSourceOpaqueReference.this.componentName);
               } catch (ResourceException var2) {
                  throw new PrivilegedActionException(var2);
               }
            }
         });
      } catch (PrivilegedActionException var2) {
         var2.printStackTrace();
         throw new NamingException(var2.getMessage());
      }
   }

   private static DataSourceBean toDataSourceBean(Map info) {
      if (info == null) {
         return null;
      } else {
         DataSourceBean dsBean = ((ApplicationClientBean)(new DescriptorManager()).createDescriptorRoot(ApplicationClientBean.class).getRootBean()).createDataSource();
         Iterator var2 = info.entrySet().iterator();

         while(var2.hasNext()) {
            Map.Entry entry = (Map.Entry)var2.next();
            PropertyDescriptor pd = (PropertyDescriptor)propDescriptorsByName.get(entry.getKey());

            try {
               Method writeMethod = pd.getWriteMethod();
               if (writeMethod == null) {
                  if (pd.getName().equals("properties")) {
                     Map props = (Map)entry.getValue();
                     Iterator var7 = props.entrySet().iterator();

                     while(var7.hasNext()) {
                        Map.Entry prop = (Map.Entry)var7.next();
                        JavaEEPropertyBean propBean = dsBean.createProperty();
                        propBean.setName((String)prop.getKey());
                        propBean.setValue((String)prop.getValue());
                     }
                  }
               } else {
                  writeMethod.invoke(dsBean, entry.getValue());
               }
            } catch (IllegalArgumentException var10) {
            } catch (IllegalAccessException var11) {
            } catch (InvocationTargetException var12) {
               throw new AssertionError(var12);
            }
         }

         return dsBean;
      }
   }

   public void readExternal(ObjectInput objectInput) throws IOException, ClassNotFoundException {
      this.applicationName = (String)objectInput.readObject();
      this.key = (String)objectInput.readObject();
      this.dataSourceInfo = (Map)objectInput.readObject();
      DataSourceBean dsb = toDataSourceBean(this.dataSourceInfo);
      if (dataSourceRefsByKey.containsKey(this.key)) {
         this.dataSource = ((DataSourceOpaqueReference)dataSourceRefsByKey.get(this.key)).dataSource;
      } else {
         dataSourceRefsByKey.put(this.key, this);
      }

   }

   public void writeExternal(ObjectOutput objectOutput) throws IOException {
      objectOutput.writeObject(this.applicationName);
      objectOutput.writeObject(this.key);
      objectOutput.writeObject(this.dataSourceInfo);
   }
}
