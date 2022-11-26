package weblogic.ejb.container.persistence;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import javax.naming.Name;
import weblogic.ejb.container.cmp.rdbms.RDBMSUtils;
import weblogic.ejb.container.deployer.DeploymentDescriptorException;
import weblogic.ejb.container.interfaces.CMPInfo;
import weblogic.ejb.container.interfaces.EntityBeanInfo;
import weblogic.ejb.container.persistence.spi.CMPBeanDescriptor;
import weblogic.ejb.container.utils.ClassUtils;
import weblogic.ejb.spi.EjbDescriptorBean;
import weblogic.ejb20.dd.DescriptorErrorInfo;

public class CMPBeanDescriptorImpl implements CMPBeanDescriptor {
   private Class beanClass;
   private Class primaryKeyClass;
   private boolean hasComplexPrimaryKey = false;
   private String primaryKeyClassName;
   private final Set containerFieldSet = new HashSet();
   private final Collection allQueries;
   private final String genBeanClassName;
   private final String genBeanInterfaceName;
   private final boolean isDynamicQueriesEnabled;
   private final EjbDescriptorBean desc;
   private final EntityBeanInfo ebi;
   private final List primaryKeyFieldList = new LinkedList();
   private final Set primaryKeyFieldNameSet = new TreeSet();
   private final Hashtable cmFieldTable = new Hashtable();

   public CMPBeanDescriptorImpl(EntityBeanInfo ebi, EjbDescriptorBean desc) throws DeploymentDescriptorException {
      this.ebi = ebi;
      this.desc = desc;
      this.beanClass = ebi.getBeanClass();
      CMPInfo cmpi = ebi.getCMPInfo();
      this.genBeanClassName = cmpi.getGeneratedBeanClassName();
      this.genBeanInterfaceName = ebi.getGeneratedBeanInterfaceName();

      Iterator pkClass;
      String nameString;
      Class fType;
      for(pkClass = cmpi.getAllContainerManagedFieldNames().iterator(); pkClass.hasNext(); this.cmFieldTable.put(nameString, fType)) {
         nameString = (String)pkClass.next();
         this.containerFieldSet.add(nameString);
         if (cmpi.uses20CMP()) {
            Method m = PersistenceUtils.getMethodIncludeSuper(this.beanClass, RDBMSUtils.getterMethodName(nameString), (Class[])null);
            if (m == null) {
               throw new DeploymentDescriptorException("For cmp-field '" + nameString + "' of bean '" + this.getEJBName() + "', we expected to find a corresponding '" + RDBMSUtils.getterMethodName(nameString) + "' method in the abstract bean class.  Compilation cannot continue without this 'get' method", new DescriptorErrorInfo("<cmp-field>", this.getEJBName(), nameString));
            }

            fType = m.getReturnType();
         } else {
            try {
               fType = this.beanClass.getField(nameString).getType();
            } catch (NoSuchFieldException var10) {
               throw new DeploymentDescriptorException("Unable to find public field '" + nameString + "' on bean class: " + this.beanClass.getName(), new DescriptorErrorInfo("<cmp-field>", this.getEJBName(), nameString));
            }
         }
      }

      this.primaryKeyClassName = ebi.getPrimaryKeyClassName();
      this.primaryKeyClass = ebi.getPrimaryKeyClass();
      if (cmpi.getCMPrimaryKeyFieldName() == null && !ebi.isUnknownPrimaryKey()) {
         pkClass = null;
         if (this.primaryKeyClassName != null) {
            this.hasComplexPrimaryKey = true;
            Class pkClass = this.primaryKeyClass;
            if (pkClass != null) {
               Field[] var12 = pkClass.getFields();
               int var13 = var12.length;

               for(int var14 = 0; var14 < var13; ++var14) {
                  Field field = var12[var14];
                  if (!field.getName().equals("serialVersionUID")) {
                     Class declaringClass = field.getDeclaringClass();
                     if (!declaringClass.equals(Object.class)) {
                        this.primaryKeyFieldList.add(field);
                        this.primaryKeyFieldNameSet.add(field.getName());
                     }
                  }
               }
            }
         }
      } else {
         this.hasComplexPrimaryKey = false;
         if (cmpi.getCMPrimaryKeyFieldName() != null) {
            this.primaryKeyFieldNameSet.add(cmpi.getCMPrimaryKeyFieldName());
         }
      }

      this.allQueries = ebi.getAllQueries();
      this.isDynamicQueriesEnabled = ebi.isDynamicQueriesEnabled();
   }

   public Method getGetterMethod(Class clazz, String cmField) {
      return this.ebi.getCMPInfo().uses20CMP() ? PersistenceUtils.getMethodIncludeSuper(clazz, RDBMSUtils.getterMethodName(cmField), (Class[])null) : null;
   }

   public Method getSetterMethod(Class clazz, String cmField) {
      return this.ebi.getCMPInfo().uses20CMP() ? PersistenceUtils.getMethodIncludeSuper(clazz, RDBMSUtils.setterMethodName(cmField), new Class[]{this.getFieldClass(cmField)}) : null;
   }

   public String getAbstractSchemaName() {
      return this.ebi.getCMPInfo().getAbstractSchemaName();
   }

   public String getEJBName() {
      return this.ebi.getEJBName();
   }

   public boolean hasLocalClientView() {
      return this.ebi.hasLocalClientView();
   }

   public boolean hasRemoteClientView() {
      return this.ebi.hasRemoteClientView();
   }

   public Class getLocalHomeClass() {
      return this.ebi.getLocalHomeClass();
   }

   public String getLocalHomeInterfaceName() {
      return this.ebi.getLocalHomeInterfaceName();
   }

   public Class getLocalHomeInterfaceClass() {
      return this.ebi.getLocalHomeInterfaceClass();
   }

   public Class getHomeClass() {
      return this.ebi.getHomeClass();
   }

   public String getHomeInterfaceName() {
      return this.ebi.getHomeInterfaceName();
   }

   public Class getHomeInterfaceClass() {
      return this.ebi.getHomeInterfaceClass();
   }

   public Class getLocalClass() {
      return this.ebi.getLocalClass();
   }

   public String getLocalInterfaceName() {
      return this.ebi.getLocalInterfaceName();
   }

   public Class getLocalInterfaceClass() {
      return this.ebi.getLocalInterfaceClass();
   }

   public Class getRemoteClass() {
      return this.ebi.getRemoteClass();
   }

   public String getRemoteInterfaceName() {
      return this.ebi.getRemoteInterfaceName();
   }

   public Class getRemoteInterfaceClass() {
      return this.ebi.getRemoteInterfaceClass();
   }

   public Class getJavaClass() {
      return this.beanClass;
   }

   public Class getBeanClass() {
      return this.beanClass;
   }

   public String getGeneratedBeanClassName() {
      return this.genBeanClassName;
   }

   public String getGeneratedBeanInterfaceName() {
      return this.genBeanInterfaceName;
   }

   public Name getJNDIName() {
      return this.ebi.getJNDIName();
   }

   public Name getLocalJNDIName() {
      return this.ebi.getLocalJNDIName();
   }

   public boolean getCacheBetweenTransactions() {
      return this.ebi.getCacheBetweenTransactions();
   }

   public boolean getBoxCarUpdates() {
      return this.ebi.getBoxCarUpdates();
   }

   public int getTransactionTimeoutSeconds() {
      return this.ebi.getTransactionTimeoutSeconds();
   }

   public boolean isReadOnly() {
      return this.ebi.isReadOnly();
   }

   public boolean isOptimistic() {
      return this.ebi.isOptimistic();
   }

   public boolean hasComplexPrimaryKey() {
      return this.hasComplexPrimaryKey;
   }

   public Class getPrimaryKeyClass() {
      return this.primaryKeyClass;
   }

   public String getPrimaryKeyClassName() {
      return this.primaryKeyClassName;
   }

   public Set getPrimaryKeyFieldNames() {
      return this.primaryKeyFieldNameSet;
   }

   public void setPrimaryKeyField(String fieldName) {
      assert this.primaryKeyFieldNameSet.isEmpty();

      this.primaryKeyFieldNameSet.add(fieldName);
      if (this.containerFieldSet.contains(fieldName)) {
         this.primaryKeyClass = this.getFieldClass(fieldName);
      } else {
         this.primaryKeyClass = Long.class;
         this.containerFieldSet.add(fieldName);
         this.cmFieldTable.put(fieldName, this.primaryKeyClass);
      }

      this.primaryKeyClassName = ClassUtils.getCanonicalName(this.primaryKeyClass);
   }

   public Set getCMFieldNames() {
      return this.containerFieldSet;
   }

   public Class getFieldClass(String fieldName) {
      return (Class)this.cmFieldTable.get(fieldName);
   }

   public ClassLoader getClassLoader() {
      return this.ebi.getClassLoader();
   }

   public boolean getIsReentrant() {
      return this.ebi.isReentrant();
   }

   public String getIsModifiedMethodName() {
      return this.ebi.getIsModifiedMethodName();
   }

   public boolean getFindersLoadBean() {
      return this.ebi.getCMPInfo().findersLoadBean();
   }

   public Collection getAllQueries() {
      return this.allQueries;
   }

   public int getConcurrencyStrategy() {
      return this.ebi.getConcurrencyStrategy();
   }

   public EjbDescriptorBean getEjbDescriptorBean() {
      return this.desc;
   }

   public void beanImplClassChangeNotification() {
      this.beanClass = this.ebi.getBeanClass();
   }

   public boolean getIsDynamicQueriesEnabled() {
      return this.isDynamicQueriesEnabled;
   }

   public boolean isBeanClassAbstract() {
      return Modifier.isAbstract(this.beanClass.getModifiers());
   }

   public int getMaxQueriesInCache() {
      return this.ebi.getMaxQueriesInCache();
   }
}
