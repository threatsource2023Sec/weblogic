package weblogic.ejb.container.deployer;

import java.io.File;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import weblogic.ejb.container.cmp.rdbms.Deployer;
import weblogic.ejb.container.cmp.rdbms.RDBMSBean;
import weblogic.ejb.container.cmp.rdbms.RDBMSPersistenceManager;
import weblogic.ejb.container.cmp.rdbms.finders.Finder;
import weblogic.ejb.container.cmp.rdbms.finders.RDBMSFinder;
import weblogic.ejb.container.interfaces.CMPInfo;
import weblogic.ejb.container.persistence.CMPBeanDescriptorImpl;
import weblogic.ejb.container.persistence.PersistenceType;
import weblogic.ejb.container.persistence.spi.CMPBeanDescriptor;
import weblogic.ejb.container.persistence.spi.CMPDeployer;
import weblogic.ejb.container.persistence.spi.EjbEntityRef;
import weblogic.ejb.container.persistence.spi.PersistenceManager;
import weblogic.ejb.container.persistence.spi.Relationships;
import weblogic.ejb.spi.WLDeploymentException;
import weblogic.j2ee.descriptor.CmpFieldBean;
import weblogic.j2ee.descriptor.EntityBeanBean;
import weblogic.utils.Getopt2;
import weblogic.utils.jars.VirtualJarFile;

public final class CMPInfoImpl implements CMPInfo {
   private final EntityBeanInfoImpl ebi;
   private final String cmpVersion;
   private final Collection containerManagedFieldNames = new LinkedList();
   private final String cmPrimaryKeyFieldName;
   private final String abstractSchemaName;
   private final Collection queries;
   private final String persistenceUseIdentifier;
   private final String persistenceUseVersion;
   private final String persistenceUseStorage;
   private final boolean findersLoadBean;
   private Relationships relationships;
   private Map beanMap;
   private Map allBeanMap;
   private String generatedBeanClassName;
   private PersistenceType persistenceType;
   private CMPDeployer deployer;
   private final Set ejbEntityRefs = new HashSet();
   private Collection finderList;
   private Map finderMap;
   private final int maxQueriesInCache;

   public CMPInfoImpl(EntityBeanInfoImpl ebi, CompositeDescriptor cdesc) {
      this.ebi = ebi;
      EntityBeanBean entity = (EntityBeanBean)cdesc.getBean();
      this.queries = Arrays.asList(entity.getQueries());
      this.cmpVersion = entity.getCmpVersion();
      this.abstractSchemaName = entity.getAbstractSchemaName();
      CmpFieldBean[] var4 = entity.getCmpFields();
      int var5 = var4.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         CmpFieldBean field = var4[var6];
         this.containerManagedFieldNames.add(field.getFieldName());
      }

      this.cmPrimaryKeyFieldName = entity.getPrimkeyField();
      this.persistenceUseIdentifier = cdesc.getPersistenceUseIdentifier();
      this.persistenceUseVersion = cdesc.getPersistenceUseVersion();
      this.persistenceUseStorage = cdesc.getPersistenceUseStorage();
      this.findersLoadBean = cdesc.getFindersLoadBean();
      this.maxQueriesInCache = cdesc.getMaxQueriesInCache();
   }

   public void setup(File outputDir, Getopt2 opts, VirtualJarFile jf) throws WLDeploymentException {
      this.deployer = this.getPersistenceType().setupDeployer(this.ebi, outputDir, opts, jf);
      this.deployer.initializePersistenceManager(this.ebi.getPersistenceManager());
      if (this.uses20CMP() && this.getPersistenceUseIdentifier().equals("WebLogic_CMP_RDBMS")) {
         Deployer rdbmsDeployer = (Deployer)this.deployer;
         this.finderList = rdbmsDeployer.getTypeSpecificData().getFinderList();
      }

   }

   public int getInstanceLockOrder() {
      if (this.uses20CMP() && this.getPersistenceUseIdentifier().equals("WebLogic_CMP_RDBMS")) {
         Deployer rdbmsDeployer = (Deployer)this.deployer;
         RDBMSBean rdbmsBean = rdbmsDeployer.getTypeSpecificData();
         if (rdbmsBean.getInstanceLockOrder().equals("AccessOrder")) {
            return 100;
         } else if (rdbmsBean.getInstanceLockOrder().equals("ValueOrder")) {
            return 101;
         } else {
            throw new AssertionError("invalid value for instanceLockOrder: " + rdbmsBean.getInstanceLockOrder());
         }
      } else {
         return 100;
      }
   }

   public Collection getAllContainerManagedFieldNames() {
      return this.containerManagedFieldNames;
   }

   public Collection getAllQueries() {
      return this.queries;
   }

   public boolean hasContainerManagedFields() {
      return !this.containerManagedFieldNames.isEmpty();
   }

   public String getCMPrimaryKeyFieldName() {
      return this.cmPrimaryKeyFieldName;
   }

   public String getCMPVersion() {
      return this.cmpVersion;
   }

   public boolean uses20CMP() {
      return this.getCMPVersion().startsWith("2");
   }

   public String getAbstractSchemaName() {
      return this.abstractSchemaName;
   }

   public boolean findersLoadBean() {
      return this.findersLoadBean;
   }

   public String getPersistenceUseIdentifier() {
      return this.persistenceUseIdentifier;
   }

   public String getPersistenceUseVersion() {
      return this.persistenceUseVersion;
   }

   public String getPersistenceUseStorage() {
      return this.persistenceUseStorage;
   }

   public void setRelationships(Relationships rel) {
      this.relationships = rel;
   }

   public Relationships getRelationships() {
      return this.relationships;
   }

   public void setBeanMap(Map beanMap) {
      this.beanMap = beanMap;
   }

   public Map getBeanMap() {
      return this.beanMap;
   }

   public CMPBeanDescriptor getCMPBeanDescriptor(String ejbName) {
      return (CMPBeanDescriptor)this.allBeanMap.get(ejbName);
   }

   public void setAllBeanMap(Map allBeanMap) {
      this.allBeanMap = allBeanMap;
   }

   public Map getAllBeanMap() {
      return this.allBeanMap;
   }

   public void setGeneratedBeanClassName(String val) {
      this.generatedBeanClassName = val;
   }

   public String getGeneratedBeanClassName() {
      return this.generatedBeanClassName;
   }

   public PersistenceType getPersistenceType() {
      return this.persistenceType;
   }

   public void setPersistenceType(PersistenceType persistenceType) {
      this.persistenceType = persistenceType;
   }

   public Class getGeneratedBeanClass() {
      return this.ebi.getGeneratedBeanClass();
   }

   public CMPDeployer getDeployer() {
      return this.deployer;
   }

   public Collection getAllEJBEntityReferences() {
      return this.ejbEntityRefs;
   }

   public void addEjbEntityRef(EjbEntityRef eref) {
      this.ejbEntityRefs.add(eref);
   }

   public void setupParentBeanManagers() {
      if (this.uses20CMP()) {
         PersistenceManager pm = this.ebi.getPersistenceManager();
         if (pm instanceof RDBMSPersistenceManager) {
            ((RDBMSPersistenceManager)pm).setupParentBeanManagers();
         }
      }

   }

   public void setCycleExists() {
      if (this.uses20CMP()) {
         PersistenceManager pm = this.ebi.getPersistenceManager();
         if (pm instanceof RDBMSPersistenceManager) {
            ((RDBMSPersistenceManager)pm).setCycleExists();
         }
      }

   }

   public void setupMNBeanManagers() {
      if (this.uses20CMP()) {
         PersistenceManager pm = this.ebi.getPersistenceManager();
         if (pm instanceof RDBMSPersistenceManager) {
            ((RDBMSPersistenceManager)pm).setupM2NBeanManagers();
         }
      }

   }

   public boolean isQueryCachingEnabled(Method method) {
      if (!this.uses20CMP()) {
         return false;
      } else if (this.finderMap != null && this.finderMap.containsKey(new RDBMSFinder.FinderKey(method))) {
         return true;
      } else if (this.finderMap == null) {
         boolean ret = false;
         this.finderMap = new HashMap();
         Iterator var3 = this.finderList.iterator();

         while(var3.hasNext()) {
            Finder f = (Finder)var3.next();
            if (f.isQueryCachingEnabled()) {
               RDBMSFinder.FinderKey fk = new RDBMSFinder.FinderKey(f);
               this.finderMap.put(fk, f);
               if (fk.equals(new RDBMSFinder.FinderKey(method))) {
                  ret = true;
               }
            }
         }

         return ret;
      } else {
         return false;
      }
   }

   public String getQueryCachingEnabledFinderIndex(Method method) {
      return ((Finder)this.finderMap.get(new RDBMSFinder.FinderKey(method))).getFinderIndex();
   }

   public int getMaxQueriesInCache() {
      return this.maxQueriesInCache;
   }

   public boolean isEnableEagerRefresh(Method method) {
      return ((Finder)this.finderMap.get(new RDBMSFinder.FinderKey(method))).isEagerRefreshEnabled();
   }

   public void beanImplClassChangeNotification() {
      CMPBeanDescriptorImpl bd = (CMPBeanDescriptorImpl)this.allBeanMap.get(this.ebi.getEJBName());
      bd.beanImplClassChangeNotification();
   }
}
