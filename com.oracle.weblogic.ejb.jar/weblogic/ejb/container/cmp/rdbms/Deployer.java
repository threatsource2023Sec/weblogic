package weblogic.ejb.container.cmp.rdbms;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import javax.ejb.FinderException;
import weblogic.descriptor.DescriptorBean;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.ejb.container.EJBDebugService;
import weblogic.ejb.container.EJBLogger;
import weblogic.ejb.container.cmp.rdbms.codegen.RDBMSCodeGenerator;
import weblogic.ejb.container.cmp.rdbms.compliance.RDBMSBeanChecker;
import weblogic.ejb.container.cmp.rdbms.compliance.RDBMSComplianceChecker;
import weblogic.ejb.container.cmp.rdbms.finders.EjbqlFinder;
import weblogic.ejb.container.cmp.rdbms.finders.Finder;
import weblogic.ejb.container.compliance.EJBComplianceTextFormatter;
import weblogic.ejb.container.dd.xml.DDUtils;
import weblogic.ejb.container.deployer.CompositeDescriptor;
import weblogic.ejb.container.deployer.EntityBeanQueryImpl;
import weblogic.ejb.container.interfaces.EntityBeanQuery;
import weblogic.ejb.container.metadata.EJBDescriptorBeanUtils;
import weblogic.ejb.container.persistence.spi.CMPBeanDescriptor;
import weblogic.ejb.container.persistence.spi.CMPCodeGenerator;
import weblogic.ejb.container.persistence.spi.CMPDeployer;
import weblogic.ejb.container.persistence.spi.EjbRelation;
import weblogic.ejb.container.persistence.spi.EjbRelationshipRole;
import weblogic.ejb.container.persistence.spi.JarDeployment;
import weblogic.ejb.container.persistence.spi.PersistenceManager;
import weblogic.ejb.container.persistence.spi.Relationships;
import weblogic.ejb.container.utils.MethodUtils;
import weblogic.ejb.spi.EjbDescriptorBean;
import weblogic.ejb.spi.WLDeploymentException;
import weblogic.ejb20.cmp.rdbms.RDBMSException;
import weblogic.ejb20.cmp.rdbms.finders.EJBQLCompilerException;
import weblogic.ejb20.cmp.rdbms.finders.InvalidFinderException;
import weblogic.ejb20.dd.DescriptorErrorInfo;
import weblogic.j2ee.descriptor.EnterpriseBeanBean;
import weblogic.j2ee.descriptor.EntityBeanBean;
import weblogic.j2ee.descriptor.QueryBean;
import weblogic.j2ee.descriptor.wl.CompatibilityBean;
import weblogic.j2ee.descriptor.wl.WeblogicEnterpriseBeanBean;
import weblogic.j2ee.descriptor.wl.WeblogicRdbmsBeanBean;
import weblogic.j2ee.descriptor.wl.WeblogicRdbmsJarBean;
import weblogic.j2ee.validation.ComplianceException;
import weblogic.logging.Loggable;
import weblogic.utils.AssertionError;
import weblogic.utils.ErrorCollectionException;
import weblogic.utils.StackTraceUtilsClient;
import weblogic.utils.jars.VirtualJarFile;
import weblogic.xml.process.ProcessorFactory;
import weblogic.xml.process.ProcessorFactoryException;
import weblogic.xml.process.XMLParsingException;
import weblogic.xml.process.XMLProcessingException;

public final class Deployer implements CMPDeployer {
   private static final DebugLogger debugLogger;
   private RDBMSBean currBean = null;
   private List fieldList = null;
   private List finderList = null;
   private List localFinderList = null;
   private List primaryKeyList = null;
   private Map parameterMap = null;
   private Class ejbClass = null;
   private Class primaryKeyClass = null;
   private CMPBeanDescriptor bd = null;
   private Map beanMap = null;
   private Relationships relationships = null;
   private Map dependentMap = null;
   private RDBMSDeployment rdbmsDeployment = null;
   private static final String DOCTYPE_DECL_START = "<!DOCTYPE";

   public void setup(JarDeployment jarDeployment) {
      if (debugLogger.isDebugEnabled()) {
         debug("called setup()");
      }

      assert jarDeployment != null;

      this.currBean = null;
      this.rdbmsDeployment = (RDBMSDeployment)jarDeployment;
   }

   public void setCMPBeanDescriptor(CMPBeanDescriptor bd) {
      if (debugLogger.isDebugEnabled()) {
         debug("called setCMPBeanDescriptor()");
      }

      this.bd = bd;
      this.ejbClass = bd.getBeanClass();
      this.primaryKeyClass = bd.getPrimaryKeyClass();
      if (bd.hasRemoteClientView()) {
         this.finderList = MethodUtils.getFinderMethodList(bd.getHomeInterfaceClass());
      }

      if (bd.hasLocalClientView()) {
         this.localFinderList = MethodUtils.getFinderMethodList(bd.getLocalHomeInterfaceClass());
      }

      this.primaryKeyList = new ArrayList(bd.getPrimaryKeyFieldNames());
      this.fieldList = new ArrayList(bd.getCMFieldNames());
   }

   public void setBeanMap(Map beanMap) {
      if (debugLogger.isDebugEnabled()) {
         debug("called setBeanMap()");
      }

      this.beanMap = beanMap;
   }

   public void setRelationships(Relationships relationships) {
      if (debugLogger.isDebugEnabled()) {
         debug("called setRelationships()");
      }

      this.relationships = relationships;
   }

   public void setDependentMap(Map dependentMap) {
      if (debugLogger.isDebugEnabled()) {
         debug("called setDependentMap()");
      }

      this.dependentMap = dependentMap;
   }

   public void setParameters(Map parameters) {
      if (debugLogger.isDebugEnabled()) {
         debug("called setParameters()");
      }

      this.parameterMap = new HashMap(parameters);
   }

   public void initializePersistenceManager(PersistenceManager pm) throws WLDeploymentException {
      if (debugLogger.isDebugEnabled()) {
         debug("called  initializePersistenceManager()");
      }

      RDBMSPersistenceManager pmi = (RDBMSPersistenceManager)pm;
      pmi.setRdbmsBean(this.currBean);
      this.currBean.setRDBMSPersistenceManager(pmi);
   }

   public RDBMSBean getTypeSpecificData() {
      if (debugLogger.isDebugEnabled()) {
         debug("called getTypeSpecificData()");
      }

      return this.currBean;
   }

   public void setTypeSpecificData(RDBMSBean b) {
      if (debugLogger.isDebugEnabled()) {
         debug("called setTypeSpecificData()");
      }

      this.currBean = b;
   }

   private static void adjustDefaults(CMPDDParser parser, EjbDescriptorBean desc, Map beanMapToUse) throws RDBMSException, WLDeploymentException {
      WeblogicRdbmsJarBean cmpDesc = parser.getDescriptorMBean();
      WeblogicRdbmsBeanBean[] var4 = cmpDesc.getWeblogicRdbmsBeans();
      int var5 = var4.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         WeblogicRdbmsBeanBean wrbb = var4[var6];
         String ejbName = wrbb.getEjbName();
         DefaultHelper defaultHelper = parser.getDefaultHelper(ejbName);
         if (beanMapToUse != null) {
            CMPBeanDescriptor cmebd = (CMPBeanDescriptor)beanMapToUse.get(ejbName);
            defaultHelper.adjustDefaults(cmebd.getPrimaryKeyClassName(), cmebd.getAllQueries(), cmebd.getConcurrencyStrategy(), cmpDesc, wrbb);
         } else {
            EnterpriseBeanBean theBean = EJBDescriptorBeanUtils.getEnterpriseBean(ejbName, desc);
            if (theBean == null) {
               Loggable l1 = EJBLogger.logmissingEnterpriseBeanMBeanLoggable(ejbName);
               throw new RDBMSException(l1.getMessageText());
            }

            EntityBeanBean entityMBean = null;
            if (!(theBean instanceof EntityBeanBean) || !"2.x".equals(((EntityBeanBean)theBean).getCmpVersion())) {
               Loggable l2 = EJBLogger.logentityMBeanWrongVersionLoggable(ejbName, "ejb20");
               throw new RDBMSException(l2.getMessageText());
            }

            entityMBean = (EntityBeanBean)theBean;
            String primaryKeyClassName = entityMBean.getPrimKeyClass();
            ArrayList ejbQueries = new ArrayList();
            QueryBean[] var14 = entityMBean.getQueries();
            int var15 = var14.length;

            for(int var16 = 0; var16 < var15; ++var16) {
               QueryBean qb = var14[var16];
               ejbQueries.add(new EntityBeanQueryImpl(qb));
            }

            WeblogicEnterpriseBeanBean wlBeanMBean = EJBDescriptorBeanUtils.getWeblogicEnterpriseBean(ejbName, desc);
            CompositeDescriptor compMBean = new CompositeDescriptor(theBean, wlBeanMBean, desc);
            defaultHelper.adjustDefaults(primaryKeyClassName, ejbQueries, DDUtils.concurrencyStringToInt(compMBean.getConcurrencyStrategy()), cmpDesc, wrbb);
         }
      }

   }

   public void readTypeSpecificData(VirtualJarFile vjar, String fileName) throws RDBMSException, ProcessorFactoryException, XMLParsingException, XMLProcessingException, WLDeploymentException, ErrorCollectionException {
      if (this.rdbmsDeployment.needToReadFile(fileName)) {
         if (debugLogger.isDebugEnabled()) {
            debug("processing RDBMS CMP XML for: " + this.bd.getEJBName() + " file: " + fileName);
         }

         this.rdbmsDeployment.addFileName(fileName);
         new ProcessorFactory();
         WeblogicRdbmsJarBean cmpDesc = this.parseXMLFile(vjar, fileName, this.bd.getEjbDescriptorBean());
         RDBMSDeploymentInfo rdbmsDeploymentInfo = new RDBMSDeploymentInfo(cmpDesc, this.beanMap, fileName);
         Map rdbmsBeans = rdbmsDeploymentInfo.getRDBMSBeanMap();
         if (rdbmsDeploymentInfo.getRDBMSBean(this.bd.getEJBName()) == null) {
            Loggable l = EJBLogger.logUnableToFindBeanInRDBMSDescriptorLoggable(this.bd.getEJBName(), fileName);
            throw new RDBMSException(l.getMessageText());
         }

         String ddlFileName = cmpDesc.getDefaultDbmsTablesDdl();
         Iterator it = rdbmsBeans.entrySet().iterator();

         while(it.hasNext()) {
            RDBMSBean rb = (RDBMSBean)((Map.Entry)it.next()).getValue();
            rb.setDatabaseType(MethodUtils.dbmsType2int(cmpDesc.getDatabaseType()));
            rb.setOrderDatabaseOperations(cmpDesc.isOrderDatabaseOperations());
            rb.setEnableBatchOperations(cmpDesc.isEnableBatchOperations());
            rb.setCreateDefaultDBMSTables(cmpDesc.getCreateDefaultDbmsTables());
            rb.setValidateDbSchemaWith(cmpDesc.getValidateDbSchemaWith());
            rb.setDefaultDbmsTablesDdl(ddlFileName);
            CompatibilityBean compatibilityBean = cmpDesc.getCompatibility();
            if (compatibilityBean != null) {
               rb.setByteArrayIsSerializedToOracleBlob(compatibilityBean.isSerializeByteArrayToOracleBlob());
               rb.setAllowReadonlyCreateAndRemove(compatibilityBean.isAllowReadonlyCreateAndRemove());
               rb.setCharArrayIsSerializedToBytes(compatibilityBean.isSerializeCharArrayToBytes());
               rb.setDisableStringTrimming(compatibilityBean.isDisableStringTrimming());
               rb.setFindersReturnNulls(compatibilityBean.isFindersReturnNulls());
               rb.setLoadRelatedBeansFromDbInPostCreate(compatibilityBean.isLoadRelatedBeansFromDbInPostCreate());
            }
         }

         if (ddlFileName != null) {
            RDBMSBean.deleteDefaultDbmsTableDdlFile(ddlFileName);
         }

         this.rdbmsDeployment.addRdbmsBeans(rdbmsBeans);
         this.rdbmsDeployment.addRdbmsRelations(rdbmsDeploymentInfo.getRDBMSRelationMap());
         this.rdbmsDeployment.addDescriptorBean(cmpDesc);
         RDBMSComplianceChecker checker = new RDBMSComplianceChecker(this.beanMap, this.relationships, this.dependentMap, rdbmsDeploymentInfo.getRDBMSBeanMap(), rdbmsDeploymentInfo.getRDBMSRelationMap(), cmpDesc);
         checker.checkCompliance();
         it = rdbmsBeans.entrySet().iterator();

         while(it.hasNext()) {
            RDBMSBean rb = (RDBMSBean)((Map.Entry)it.next()).getValue();

            assert !rb.normalizeMultiTables_done();

            CMPBeanDescriptor cmebd = (CMPBeanDescriptor)this.beanMap.get(rb.getEjbName());
            List pkFieldList = new ArrayList(cmebd.getPrimaryKeyFieldNames());
            rb.setPrimaryKeyFields(pkFieldList);
            rb.normalizeMultiTables((CMPBeanDescriptor)this.beanMap.get(rb.getEjbName()));
         }

         it = rdbmsBeans.entrySet().iterator();
         ErrorCollectionException excCol = null;

         RDBMSBean rb;
         while(it.hasNext()) {
            rb = (RDBMSBean)((Map.Entry)it.next()).getValue();
            if (this.rdbmsDeployment.getRDBMSRelationMap().isEmpty()) {
               this.relationships = null;
            }

            assert !rb.initialized();

            rb.processDescriptors(this.beanMap, this.relationships, this.rdbmsDeployment.getRDBMSBeanMap(), this.rdbmsDeployment.getRDBMSRelationMap());

            try {
               this.processFinders(rb, (CMPBeanDescriptor)this.beanMap.get(rb.getEjbName()));
            } catch (Exception var13) {
               if (excCol == null) {
                  excCol = new ErrorCollectionException();
               }

               excCol.add(var13);
            }

            rb.setupAutoKeyGen();
            rb.setupFieldGroupIndexes();
         }

         if (excCol != null && excCol.getExceptions().size() > 0) {
            throw excCol;
         }

         this.validateFinderQueries();
         this.checkIsValidRelationshipCaching();
         it = rdbmsBeans.entrySet().iterator();

         while(it.hasNext()) {
            rb = (RDBMSBean)((Map.Entry)it.next()).getValue();
            rb.generateFinderSQLStatements(rb.getFinders());
            rb.createRelationFinders();
            rb.createRelationFinders2();
            rb.generateFinderSQLStatements(rb.getRelationFinders());
            rb.setupRelatedBeanMap();
            this.processEjbSelect(rb.getFinders());
            this.processEjbSelect(rb.getRelationFinders());
            if (ddlFileName != null) {
               rb.addTableDefToDDLFile();
            }
         }

         if (ddlFileName != null) {
            EJBLogger.logDDLFileCreated(ddlFileName);
         }
      }

      this.currBean = this.rdbmsDeployment.getRDBMSBean(this.bd.getEJBName());
      if (this.currBean == null) {
         Loggable l = EJBLogger.logUnableToFindBeanInRDBMSDescriptor1Loggable(this.bd.getEJBName());
         throw new RDBMSException(l.getMessageText());
      } else {
         this.checkResultTypeMapping(this.currBean.getFinders());
      }
   }

   private void checkResultTypeMapping(Iterator finders) throws ErrorCollectionException {
      ErrorCollectionException ece = new ErrorCollectionException();

      while(true) {
         Finder each;
         do {
            do {
               if (!finders.hasNext()) {
                  if (!ece.isEmpty()) {
                     throw ece;
                  }

                  return;
               }

               each = (Finder)finders.next();
            } while(!each.isSelect());
         } while(each.getQueryType() != 4 && each.getQueryType() != 2);

         RDBMSBean rrb = each.getSelectBeanTarget();
         CMPBeanDescriptor rbd = rrb.getCMPBeanDescriptor();
         EJBComplianceTextFormatter fmt = new EJBComplianceTextFormatter();
         WLDeploymentException wle;
         if (each.hasLocalResultType()) {
            if (!rbd.hasLocalClientView()) {
               wle = new WLDeploymentException(fmt.INVALID_RESULT_TYPE_LOCAL(each.getRDBMSBean().getEjbName(), DDUtils.getMethodSignature(each.getName(), each.getParameterClassNames()), rrb.getEjbName()));
               ece.add(wle);
            }
         } else if (each.hasRemoteResultType() && !rbd.hasRemoteClientView()) {
            wle = new WLDeploymentException(fmt.INVALID_RESULT_TYPE_REMOTE(each.getRDBMSBean().getEjbName(), DDUtils.getMethodSignature(each.getName(), each.getParameterClassNames()), rrb.getEjbName()));
            ece.add(wle);
         }
      }
   }

   private boolean methodMatchesQuery(Method method, EntityBeanQuery query) {
      if (!method.getName().equals(query.getMethodName())) {
         return false;
      } else {
         Class[] methodParams = method.getParameterTypes();
         String[] queryParams = query.getMethodParams();
         if (methodParams.length != queryParams.length) {
            return false;
         } else {
            for(int i = 0; i < methodParams.length; ++i) {
               String methodParam = MethodUtils.decodeArrayTypes(methodParams[i]);
               if (!methodParam.equals(queryParams[i])) {
                  return false;
               }
            }

            return true;
         }
      }
   }

   private Method[] getMethodsForQuery(String ejbName, EntityBeanQuery query, List finderMethods, List ejbSelectMethods) throws InvalidFinderException {
      boolean ejbSelect = false;
      Method[] methods = new Method[2];
      int currentMethod = 0;
      Iterator methodIter = null;
      EJBComplianceTextFormatter fmt = new EJBComplianceTextFormatter();
      if (query.getMethodName().startsWith("find")) {
         methodIter = finderMethods.iterator();
      } else {
         if (!query.getMethodName().startsWith("ejbSelect")) {
            throw new InvalidFinderException(8, fmt.INVALID_QUERY_NAME(ejbName, DDUtils.getMethodSignature(query.getMethodName(), query.getMethodParams())), new DescriptorErrorInfo("<ejb-ql>", ejbName, query.getMethodName()));
         }

         methodIter = ejbSelectMethods.iterator();
         ejbSelect = true;
      }

      while(methodIter.hasNext()) {
         Method currMethod = (Method)methodIter.next();
         if (this.methodMatchesQuery(currMethod, query)) {
            methods[currentMethod] = currMethod;
            ++currentMethod;
            if (ejbSelect || currentMethod == 2) {
               break;
            }
         }
      }

      if (currentMethod == 0) {
         throw new InvalidFinderException(8, fmt.QUERY_NOT_FOUND(ejbName, query.getMethodSignature()), new DescriptorErrorInfo("<ejb-ql>", ejbName, query.getMethodName()));
      } else {
         return methods;
      }
   }

   private Method getFindByPrimaryKeyMethod(List finderMethods) {
      Iterator var2 = finderMethods.iterator();

      Method method;
      do {
         if (!var2.hasNext()) {
            throw new AssertionError("Method 'findByPrimaryKey' not found on Home interface.");
         }

         method = (Method)var2.next();
      } while(!method.getName().equals("findByPrimaryKey"));

      return method;
   }

   private void processFinders(RDBMSBean bean, CMPBeanDescriptor cmebd) throws Exception {
      Collection queries = cmebd.getAllQueries();
      if (queries == null) {
         Loggable l = EJBLogger.logFinderCollectionIsNullLoggable("Deployer.readTypeSpecificData");
         throw new Exception(l.getMessageText());
      } else {
         Iterator queryIter = queries.iterator();
         Finder finder = null;
         Class ejb = cmebd.getBeanClass();
         Class homeInterface = cmebd.getHomeInterfaceClass();
         Class localHomeInterface = cmebd.getLocalHomeInterfaceClass();
         List finderList = MethodUtils.getFinderMethodList(homeInterface, localHomeInterface);
         List ejbSelectList = MethodUtils.getSelectMethodList(ejb);

         while(queryIter.hasNext()) {
            EntityBeanQuery query = (EntityBeanQuery)queryIter.next();

            try {
               if (debugLogger.isDebugEnabled()) {
                  debug("\n ++++++++  about to create new finder for: " + query.getMethodName() + "  " + query.getQueryText());
               }

               finder = bean.createFinder(query.getMethodName(), query.getMethodParams(), query.getQueryText());
            } catch (InvalidFinderException var19) {
               if (debugLogger.isDebugEnabled()) {
                  debug("getRDBMSBean returned exception " + var19);
               }

               var19.setDescriptorErrorInfo(new DescriptorErrorInfo("<query-method>", bean.getEjbName(), query.getMethodName()));
               throw var19;
            }

            Method[] methods = this.getMethodsForQuery(cmebd.getEJBName(), query, finderList, ejbSelectList);
            finder.setMethods(methods);
            finder.setFinderLoadsBean(cmebd.getFindersLoadBean());
            if (finder.isSelect()) {
               finder.setResultTypeMapping(query.getResultTypeMapping());
            }

            bean.perhapsSetQueryCachingEnabled(finder);
            bean.addFinder(finder);
         }

         String categoryFieldName = null;
         if (RDBMSBeanChecker.validateCategoryFieldAvailible(bean)) {
            categoryFieldName = bean.getCategoryCmpField();
         }

         if (categoryFieldName != null) {
            if (debugLogger.isDebugEnabled()) {
               debug("category-cmp-field " + categoryFieldName + " is set to EJB " + bean.getEjbName());
            }

            Class categoryFieldClass = bean.getCmpFieldClass(categoryFieldName);
            String[] finderParam = new String[]{categoryFieldClass.getName()};
            String ejbQl = "SELECT Object(bean) FROM " + bean.getAbstractSchemaName() + " AS bean WHERE bean." + categoryFieldName + " = ?1";
            Finder categoryFinder = bean.createFinder("findByCategory__WL_", finderParam, ejbQl);
            categoryFinder.setReturnClassType(Collection.class);
            categoryFinder.setExceptionClassTypes(new Class[]{FinderException.class});
            categoryFinder.setModifierString("public ");
            categoryFinder.setParameterClassTypes(new Class[]{categoryFieldClass});
            categoryFinder.setFinderLoadsBean(true);
            if (debugLogger.isDebugEnabled()) {
               debug("categoryFinder created: " + categoryFinder);
            }

            bean.addFinder(categoryFinder);
         }

         ErrorCollectionException excCol = null;
         List localFinderList;
         if (cmebd.hasRemoteClientView()) {
            localFinderList = MethodUtils.getFinderMethodList(cmebd.getHomeInterfaceClass());

            try {
               this.checkClassFinderForXMLFinder(cmebd.getEJBName(), localFinderList.iterator(), queries);
            } catch (Exception var16) {
               excCol = new ErrorCollectionException();
               excCol.add(var16);
            }
         }

         if (cmebd.hasLocalClientView()) {
            localFinderList = MethodUtils.getFinderMethodList(cmebd.getLocalHomeInterfaceClass());

            try {
               this.checkClassFinderForXMLFinder(cmebd.getEJBName(), localFinderList.iterator(), queries);
            } catch (Exception var18) {
               if (excCol == null) {
                  excCol = new ErrorCollectionException();
               }

               excCol.add(var18);
            }
         }

         try {
            this.checkClassFinderForXMLFinder(cmebd.getEJBName(), ejbSelectList.iterator(), queries);
         } catch (Exception var17) {
            if (excCol == null) {
               excCol = new ErrorCollectionException();
            }

            excCol.add(var17);
         }

         if (excCol != null) {
            throw excCol;
         } else {
            Finder findByPrimaryKey = this.generateFindByPrimaryKeyFinder(bean);
            findByPrimaryKey.setFinderLoadsBean(cmebd.getFindersLoadBean());
            bean.addFinder(findByPrimaryKey);
         }
      }
   }

   private void processEjbSelect(Iterator finders) {
      while(finders.hasNext()) {
         Finder f = (Finder)finders.next();
         if ((f.isSelect() || f.isSelectInEntity()) && (f.getQueryType() == 4 || f.getQueryType() == 2)) {
            RDBMSBean targBean = f.getSelectBeanTarget();
            CMPBeanDescriptor targCbd = (CMPBeanDescriptor)this.beanMap.get(targBean.getEjbName());
            f.setFinderLoadsBean(targCbd.getFindersLoadBean());
            targBean.addToEjbSelectInternalList(f);
         }
      }

   }

   private void checkClassFinderForXMLFinder(String ejbName, Iterator methodIt, Collection xmlQueryCol) throws ErrorCollectionException {
      ErrorCollectionException excCol = null;
      EJBComplianceTextFormatter fmt = new EJBComplianceTextFormatter();

      while(true) {
         Method currMethod;
         do {
            if (!methodIt.hasNext()) {
               if (excCol != null) {
                  throw excCol;
               }

               return;
            }

            currMethod = (Method)methodIt.next();
         } while(currMethod.getName().equals("findByPrimaryKey"));

         Iterator queryIter = xmlQueryCol.iterator();
         boolean matched = false;

         while(queryIter.hasNext()) {
            EntityBeanQuery query = (EntityBeanQuery)queryIter.next();
            if (this.methodMatchesQuery(currMethod, query)) {
               matched = true;
               break;
            }
         }

         if (!matched) {
            if (excCol == null) {
               excCol = new ErrorCollectionException();
            }

            excCol.add(new InvalidFinderException(8, fmt.MISSING_QUERY_IN_EJBJAR(ejbName, DDUtils.getMethodSignature(currMethod)), new DescriptorErrorInfo("<ejb-ql>", ejbName, DDUtils.getMethodSignature(currMethod))));
         }
      }
   }

   public WeblogicRdbmsJarBean parseXMLFile(VirtualJarFile vjar, String fileName, EjbDescriptorBean ejbDescriptor) throws RDBMSException, ProcessorFactoryException, XMLParsingException, XMLProcessingException, WLDeploymentException {
      WeblogicRdbmsJarBean rdbmsJar = null;
      if (debugLogger.isDebugEnabled()) {
         debug("called parseXMLFile() fileName=" + fileName);
      }

      BufferedInputStream xmlReader = null;

      WeblogicRdbmsJarBean var7;
      try {
         WeblogicRdbmsJarBean var28;
         if (vjar == null) {
            rdbmsJar = this.parseXMLFileWithSchema((VirtualJarFile)null, fileName, ejbDescriptor);
            if (rdbmsJar == null) {
               Loggable l = EJBLogger.logRdbmsDescriptorNotFoundInJarLoggable(fileName);
               throw new RDBMSException(l.getMessageText());
            }

            var28 = rdbmsJar;
            return var28;
         }

         ZipEntry ze = vjar.getEntry(fileName);
         if (ze != null) {
            InputStream xmlStream = vjar.getInputStream(ze);
            xmlReader = new BufferedInputStream(xmlStream);
            var28 = parseXMLFile(xmlReader, fileName, ejbDescriptor, this.beanMap);
            return var28;
         }

         rdbmsJar = this.parseXMLFileWithSchema(vjar, fileName, ejbDescriptor);
         if (rdbmsJar == null) {
            Loggable l = EJBLogger.logRdbmsDescriptorNotFoundInJarLoggable(fileName);
            throw new RDBMSException(l.getMessageText());
         }

         WeblogicRdbmsJarBean var8 = rdbmsJar;
         return var8;
      } catch (ProcessorFactoryException var25) {
         if (xmlReader == null) {
            throw var25;
         }

         try {
            xmlReader.reset();
            rdbmsJar = this.parseXMLFileWithSchema(vjar, fileName, ejbDescriptor);
            if (rdbmsJar == null) {
               Loggable l = EJBLogger.logRdbmsDescriptorNotFoundInJarLoggable(fileName);
               throw new RDBMSException(l.getMessageText());
            }

            var7 = rdbmsJar;
         } catch (RDBMSException var23) {
            throw var23;
         } catch (Exception var24) {
            throw new RDBMSException(StackTraceUtilsClient.throwable2StackTrace(var24));
         }
      } catch (IOException var26) {
         throw new RDBMSException(StackTraceUtilsClient.throwable2StackTrace(var26));
      } finally {
         try {
            if (xmlReader != null) {
               xmlReader.close();
            }
         } catch (IOException var22) {
         }

      }

      return var7;
   }

   public static WeblogicRdbmsJarBean parseXMLFile(BufferedInputStream xmlReader, String fileName, EjbDescriptorBean ejbDescriptor, Map beanMapToUse) throws RDBMSException, IOException, ProcessorFactoryException, XMLParsingException, XMLProcessingException, WLDeploymentException {
      ProcessorFactory procFac = new ProcessorFactory();
      DDUtils.getXMLEncoding(xmlReader, fileName);
      xmlReader.mark(1048576);
      if (isSchemaBasedDD(xmlReader)) {
         throw new ProcessorFactoryException("This is a Schema based DD");
      } else {
         xmlReader.reset();
         xmlReader.mark(1048576);
         Object o = procFac.getProcessor(xmlReader, RDBMSUtils.validRdbmsCmp20JarPublicIds);
         xmlReader.reset();
         if (o instanceof CMPDDParser) {
            CMPDDParser parser = (CMPDDParser)o;
            parser.setFileName(fileName);
            parser.setEJBDescriptor(ejbDescriptor);
            parser.process((InputStream)xmlReader);
            adjustDefaults(parser, ejbDescriptor, beanMapToUse);
            return parser.getDescriptorMBean();
         } else {
            Loggable l = EJBLogger.logCmp20DDHasWrongDocumentTypeLoggable();
            String msg = l.getMessageText() + "\n";
            String[] var9 = RDBMSUtils.validRdbmsCmp20JarPublicIds;
            int var10 = var9.length;

            for(int var11 = 0; var11 < var10; ++var11) {
               String s = var9[var11];
               msg = msg + "\"" + s + "\"\n";
            }

            throw new RDBMSException(msg);
         }
      }
   }

   private WeblogicRdbmsJarBean parseXMLFileWithSchema(VirtualJarFile vjar, String fileName, EjbDescriptorBean ejbDescriptor) throws RDBMSException {
      try {
         WeblogicRdbmsJarBean rdbmsJar = null;
         RDBMSDescriptor ddParser = new RDBMSDescriptor(vjar, fileName, ejbDescriptor.getAppName(), ejbDescriptor.getUri(), ejbDescriptor.getDeploymentPlan(), ejbDescriptor.getConfigDirectory());
         if (ejbDescriptor.isReadOnly()) {
            rdbmsJar = (WeblogicRdbmsJarBean)ddParser.getDescriptorBean();
         } else {
            rdbmsJar = (WeblogicRdbmsJarBean)ddParser.getEditableDescriptorBean();
         }

         ejbDescriptor.addWeblogicRdbmsJarBean(rdbmsJar);
         return rdbmsJar;
      } catch (Exception var6) {
         throw new RDBMSException(StackTraceUtilsClient.throwable2StackTrace(var6));
      }
   }

   public void preCodeGeneration(CMPCodeGenerator codeGenerator) throws ErrorCollectionException {
      assert codeGenerator instanceof RDBMSCodeGenerator;

      if (debugLogger.isDebugEnabled()) {
         debug("called preCodeGeneration()");
      }

      RDBMSCodeGenerator beanGen = (RDBMSCodeGenerator)codeGenerator;
      beanGen.setCMPBeanDescriptor(this.bd);
      beanGen.setRDBMSBean(this.currBean);
      beanGen.setFinderList(this.currBean.getFinderList());
      beanGen.setEjbSelectInternalList(this.currBean.getEjbSelectInternalList());
      beanGen.setParameterMap(this.parameterMap);
   }

   public void postCodeGeneration(CMPCodeGenerator codeGenerator) {
      assert codeGenerator instanceof RDBMSCodeGenerator;

      if (debugLogger.isDebugEnabled()) {
         debug("called postCodeGeneration()");
      }

   }

   private void validateFinderQueries() throws ErrorCollectionException {
      if (debugLogger.isDebugEnabled()) {
         debug("called validateFinderQueries.");
      }

      ErrorCollectionException excCol = new ErrorCollectionException();
      Map rdbmsBeans = this.rdbmsDeployment.getRDBMSBeanMap();
      Iterator var3 = rdbmsBeans.values().iterator();

      while(var3.hasNext()) {
         RDBMSBean rb = (RDBMSBean)var3.next();
         Iterator finders = rb.getFinders();

         while(finders.hasNext()) {
            Finder finder = (Finder)finders.next();

            try {
               if (finder instanceof EjbqlFinder) {
                  ((EjbqlFinder)finder).parseExpression();
               }
            } catch (EJBQLCompilerException var8) {
               excCol.add(var8);
            }
         }
      }

      if (excCol.getExceptions().size() > 0) {
         throw excCol;
      }
   }

   private void checkIsValidRelationshipCaching() throws ErrorCollectionException {
      if (debugLogger.isDebugEnabled()) {
         debug("called checkIsValidRelationshipCaching.");
      }

      ErrorCollectionException excCol = new ErrorCollectionException();
      Map rdbmsBeanMap = this.rdbmsDeployment.getRDBMSBeanMap();
      Iterator rdbmsBeans = rdbmsBeanMap.entrySet().iterator();

      while(rdbmsBeans.hasNext()) {
         RDBMSBean rb = (RDBMSBean)((Map.Entry)rdbmsBeans.next()).getValue();
         Iterator relationshipCachings = rb.getRelationshipCachings().iterator();

         while(relationshipCachings.hasNext()) {
            RelationshipCaching caching = (RelationshipCaching)relationshipCachings.next();
            Iterator cachingElements = caching.getCachingElements().iterator();
            this.checkIsValidRelationshipCachingForCachingElements(rb, cachingElements, caching, excCol, rb);
         }
      }

      if (excCol.getExceptions().size() > 0) {
         throw excCol;
      }
   }

   private void checkIsValidRelationshipCachingForCachingElements(RDBMSBean prevBean, Iterator cachingElements, RelationshipCaching caching, ErrorCollectionException excCol, RDBMSBean rootBean) {
      EJBComplianceTextFormatter fmt = new EJBComplianceTextFormatter();
      Map rdbmsBeanMap = this.rdbmsDeployment.getRDBMSBeanMap();

      while(true) {
         while(cachingElements.hasNext()) {
            RelationshipCaching.CachingElement cachingElement = (RelationshipCaching.CachingElement)cachingElements.next();
            String cachingElementCmrField = cachingElement.getCmrField();
            String cachingElementGroupName = cachingElement.getGroupName();
            if (!prevBean.getAllCmrFields().contains(cachingElementCmrField)) {
               excCol.add(new ComplianceException(fmt.RELATIONSHIP_CACHING_CONTAINS_UNDEFINED_CMR_FIELD(prevBean.getEjbName(), caching.getCachingName(), cachingElementCmrField), new DescriptorErrorInfo("<relationship-caching>", prevBean.getEjbName(), caching.getCachingName())));
            } else {
               RDBMSBean cachingElementBean = prevBean.getRelatedRDBMSBean(cachingElementCmrField);
               if (cachingElementBean.getFieldGroup(cachingElementGroupName) == null) {
                  excCol.add(new ComplianceException(fmt.RELATIONSHIP_CACHING_CONTAINS_UNDEFINED_GROUP_NAME(prevBean.getEjbName(), caching.getCachingName(), cachingElementBean.getEjbName(), cachingElementGroupName), new DescriptorErrorInfo("<relationship-caching>", prevBean.getEjbName(), caching.getCachingName())));
               }

               if (rootBean.isOptimistic() && !cachingElementBean.isOptimistic() && !cachingElementBean.isReadOnly()) {
                  excCol.add(new ComplianceException(fmt.RELATIONSHIP_CACHING_INCONSISTENT_CONCURRENCY_STRATEGY(rootBean.getEjbName(), caching.getCachingName(), cachingElementBean.getEjbName()), new DescriptorErrorInfo("<relationship-caching>", rootBean.getEjbName(), caching.getCachingName())));
               }

               Iterator ejbRelations = this.relationships.getAllEjbRelations().values().iterator();

               while(ejbRelations.hasNext()) {
                  EjbRelation ejbRelation = (EjbRelation)ejbRelations.next();
                  Iterator ejbRoles = ejbRelation.getAllEjbRelationshipRoles().iterator();
                  EjbRelationshipRole ejbRole1 = (EjbRelationshipRole)ejbRoles.next();
                  EjbRelationshipRole ejbRole2 = (EjbRelationshipRole)ejbRoles.next();
                  boolean found = false;
                  if (ejbRole1.getCmrField() != null) {
                     found = cachingElementCmrField.equals(ejbRole1.getCmrField().getName());
                  }

                  if (ejbRole2.getCmrField() != null) {
                     found = found || cachingElementCmrField.equals(ejbRole2.getCmrField().getName());
                  }

                  if (found) {
                     RDBMSBean rb1 = (RDBMSBean)rdbmsBeanMap.get(ejbRole1.getRoleSource().getEjbName());
                     RDBMSBean rb2 = (RDBMSBean)rdbmsBeanMap.get(ejbRole2.getRoleSource().getEjbName());
                     if ((prevBean == rb1 && cachingElementBean == rb2 || prevBean == rb2 && cachingElementBean == rb1) && ejbRole1.getMultiplicity().equalsIgnoreCase("many") && ejbRole2.getMultiplicity().equalsIgnoreCase("many")) {
                        excCol.add(new ComplianceException(fmt.RELATIONSHIP_CACHING_CANNOT_BE_SPECIFIED(prevBean.getEjbName(), caching.getCachingName(), cachingElementBean.getEjbName())));
                     }
                  }
               }

               Iterator cachingElementNested = cachingElement.getCachingElements().iterator();
               if (cachingElementNested.hasNext()) {
                  this.checkIsValidRelationshipCachingForCachingElements(cachingElementBean, cachingElementNested, caching, excCol, rootBean);
               }
            }
         }

         return;
      }
   }

   private Finder generateFindByPrimaryKeyFinder(RDBMSBean currBean) throws Exception {
      assert this.primaryKeyClass != null : "PrimaryKeyClass is null";

      assert this.ejbClass != null : "ejbClass is null";

      assert currBean != null : "currBean is null";

      CMPBeanDescriptor cmebd = currBean.getCMPBeanDescriptor();
      if (cmebd == null) {
         Loggable l = EJBLogger.logCmpBeanDescriptorIsNullLoggable("Deployer.generateFindByPrimaryKeyFinder", currBean.getEjbName());
         throw new Exception(l.getMessageText());
      } else {
         String query = currBean.findByPrimaryKeyQuery();
         if (debugLogger.isDebugEnabled()) {
            debug("Created findByPrimaryKey query of " + query);
         }

         Method method = null;

         Class finder;
         try {
            finder = null;
            if (cmebd.hasRemoteClientView()) {
               finder = cmebd.getHomeInterfaceClass();
            } else {
               finder = cmebd.getLocalHomeInterfaceClass();
            }

            method = this.getFindByPrimaryKeyMethod(MethodUtils.getFinderMethodList(finder));
         } catch (Exception var9) {
            throw new AssertionError("Caught an Exception while setting in generated finder props: " + var9.toString());
         }

         finder = null;

         EjbqlFinder finder;
         try {
            Class[] paramTypes = method.getParameterTypes();
            String[] params = new String[paramTypes.length];
            int i = 0;

            while(true) {
               if (i >= params.length) {
                  finder = (EjbqlFinder)currBean.createFinder("findByPrimaryKey", params, query);
                  break;
               }

               params[i] = paramTypes[i].getName();
               ++i;
            }
         } catch (InvalidFinderException var10) {
            throw new AssertionError("Caught an InvalidFinderException in generated finder: " + var10);
         }

         finder.setKeyFinder(true);
         finder.setKeyBean(currBean);
         finder.setMethods(new Method[]{method});
         return finder;
      }
   }

   public String toString() {
      StringBuffer result = new StringBuffer(150);
      result.append("[weblogic.cmp.rdbms.Deployer =\n");
      result.append("\tbean class = " + (this.ejbClass != null ? this.ejbClass.getName() : "null"));
      result.append("\n");
      result.append("\tprimary key class = " + (this.primaryKeyClass != null ? this.primaryKeyClass.getName() : "null"));
      result.append("\n");
      result.append("\n");
      result.append("\tparameterMap = {");
      Iterator i;
      if (this.parameterMap != null && this.parameterMap.keySet() != null) {
         i = this.parameterMap.keySet().iterator();

         while(i.hasNext()) {
            String key = (String)i.next();
            result.append("(" + key + ",value)");
            if (i.hasNext()) {
               result.append(", ");
            } else {
               result.append("}\n");
            }
         }
      } else {
         result.append("null}\n");
      }

      result.append("\tfieldList = {");
      Field f;
      if (this.fieldList == null) {
         result.append("null}\n");
      } else {
         i = this.fieldList.iterator();

         while(i.hasNext()) {
            f = (Field)i.next();
            result.append(f.getName());
            if (i.hasNext()) {
               result.append(", ");
            } else {
               result.append("}\n");
            }
         }
      }

      result.append("\tfinderList = {");
      if (this.finderList == null) {
         result.append("null}\n");
      } else {
         i = this.finderList.iterator();

         while(i.hasNext()) {
            Method m = (Method)i.next();
            result.append(m.getName());
            if (i.hasNext()) {
               result.append(", ");
            } else {
               result.append("}\n");
            }
         }
      }

      result.append("\tprimaryKeyList = {");
      if (this.primaryKeyList == null) {
         result.append("null}\n");
      } else {
         i = this.primaryKeyList.iterator();

         while(i.hasNext()) {
            f = (Field)i.next();
            result.append(f.getName());
            if (i.hasNext()) {
               result.append(", ");
            } else {
               result.append("}\n");
            }
         }
      }

      if (this.currBean == null) {
         result.append("\tcurrBean = null\n");
      } else {
         result.append("\t" + this.currBean.toString());
      }

      result.append("]");
      return result.toString();
   }

   public static boolean isSchemaBasedDD(BufferedInputStream xmlStream) throws IOException {
      byte[] b = new byte[1000];
      xmlStream.read(b, 0, 1000);
      String s = new String(b);
      return s.indexOf("<!DOCTYPE") == -1;
   }

   private static void debug(String s) {
      debugLogger.debug("[Deployer] " + s);
   }

   public DescriptorBean parseXML(VirtualJarFile vjar, String fileName, String ejbName, ProcessorFactory procFac, EjbDescriptorBean ejbDescriptor) throws ProcessorFactoryException, XMLParsingException, XMLProcessingException, RDBMSException, WLDeploymentException {
      return (DescriptorBean)this.parseXMLFile(vjar, fileName, ejbDescriptor);
   }

   static {
      debugLogger = EJBDebugService.cmpDeploymentLogger;
   }
}
