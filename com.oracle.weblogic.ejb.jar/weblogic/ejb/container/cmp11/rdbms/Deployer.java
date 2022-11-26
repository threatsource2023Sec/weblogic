package weblogic.ejb.container.cmp11.rdbms;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.zip.ZipEntry;
import weblogic.descriptor.DescriptorBean;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.ejb.container.EJBDebugService;
import weblogic.ejb.container.EJBLogger;
import weblogic.ejb.container.cmp.rdbms.RDBMSDescriptor;
import weblogic.ejb.container.cmp11.rdbms.codegen.RDBMSCodeGenerator;
import weblogic.ejb.container.cmp11.rdbms.codegen.TypeUtils;
import weblogic.ejb.container.cmp11.rdbms.compliance.RDBMSComplianceChecker;
import weblogic.ejb.container.cmp11.rdbms.finders.Finder;
import weblogic.ejb.container.cmp11.rdbms.finders.FinderNotFoundException;
import weblogic.ejb.container.cmp11.rdbms.finders.IllegalExpressionException;
import weblogic.ejb.container.cmp11.rdbms.finders.InvalidFinderException;
import weblogic.ejb.container.dd.xml.DDUtils;
import weblogic.ejb.container.ejbc.EJBCException;
import weblogic.ejb.container.persistence.spi.CMPBeanDescriptor;
import weblogic.ejb.container.persistence.spi.CMPCodeGenerator;
import weblogic.ejb.container.persistence.spi.CMPDeployer;
import weblogic.ejb.container.persistence.spi.JarDeployment;
import weblogic.ejb.container.persistence.spi.PersistenceManager;
import weblogic.ejb.container.persistence.spi.Relationships;
import weblogic.ejb.container.utils.ClassUtils;
import weblogic.ejb.container.utils.MethodUtils;
import weblogic.ejb.spi.EjbDescriptorBean;
import weblogic.ejb.spi.WLDeploymentException;
import weblogic.ejb20.cmp.rdbms.RDBMSException;
import weblogic.j2ee.descriptor.wl60.WeblogicRdbmsJarBean;
import weblogic.logging.Loggable;
import weblogic.utils.ErrorCollectionException;
import weblogic.utils.StackTraceUtils;
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
   private List primaryKeyList = null;
   private Map parameterMap = null;
   private Class ejbClass = null;
   private Class homeInterfaceClass = null;
   private Class remoteInterfaceClass = null;
   private Class primaryKeyClass = null;
   private CMPBeanDescriptor bd = null;
   private RDBMSDeployment rdbmsDeployment = null;

   public void setup(JarDeployment jarDeployment) {
      this.currBean = new RDBMSBean();
      this.rdbmsDeployment = (RDBMSDeployment)jarDeployment;
   }

   public void setCMPBeanDescriptor(CMPBeanDescriptor cmebd) {
      assert cmebd != null;

      this.bd = cmebd;
      this.ejbClass = this.bd.getBeanClass();
      this.homeInterfaceClass = this.bd.getHomeInterfaceClass();
      this.remoteInterfaceClass = this.bd.getRemoteInterfaceClass();
      this.primaryKeyClass = this.bd.getPrimaryKeyClass();
      this.finderList = MethodUtils.getFinderMethodList(this.homeInterfaceClass);
      this.primaryKeyList = new ArrayList(this.bd.getPrimaryKeyFieldNames());
      this.fieldList = new ArrayList(this.bd.getCMFieldNames());
   }

   public void setBeanMap(Map beanMap) {
      throw new AssertionError("Deployer.setBeanMap called for a 1.1 CMP bean.");
   }

   public void setRelationships(Relationships relationships) {
      throw new AssertionError("Deployer.setRelationships called for a 1.1 CMP bean.");
   }

   public void setDependentMap(Map dependentMap) {
      throw new AssertionError("Deployer.setDependentMap called for a 1.1 CMP bean.");
   }

   public void setParameters(Map parameters) {
      this.parameterMap = new HashMap(parameters);
   }

   public void initializePersistenceManager(PersistenceManager pm) throws WLDeploymentException {
      assert this.bd != null;

      PersistenceManagerImpl pmi = (PersistenceManagerImpl)pm;
      pmi.setBeanInfo(this.currBean);
      pmi.bd = this.bd;
   }

   private boolean fieldListContainsField(String fieldName) {
      Iterator iter = this.fieldList.iterator();

      String field;
      do {
         if (!iter.hasNext()) {
            return false;
         }

         field = (String)iter.next();
      } while(!field.equals(fieldName));

      return true;
   }

   private boolean currBeanContainsField(String field) {
      Iterator linkIter = this.currBean.getObjectLinks();

      RDBMSBean.ObjectLink link;
      do {
         if (!linkIter.hasNext()) {
            return false;
         }

         link = (RDBMSBean.ObjectLink)linkIter.next();
      } while(!link.getBeanField().equals(field));

      return true;
   }

   public synchronized RDBMSBean getTypeSpecificData() {
      return this.currBean;
   }

   public synchronized void setTypeSpecificData(RDBMSBean b) {
      this.currBean = b;
   }

   public RDBMSDeploymentInfo parseXMLFile(VirtualJarFile vjar, String fileName, String ejbName, ProcessorFactory procFac, EjbDescriptorBean ejbDescriptor) throws RDBMSException, InvalidFinderException, XMLParsingException, XMLProcessingException {
      BufferedInputStream xmlInputStream = null;
      if (debugLogger.isDebugEnabled()) {
         debug("getRDBMSBean(" + vjar + ")");
      }

      WeblogicRdbmsJarBean wlJar = null;
      CMPDDParser.CompatibilitySettings compat = null;

      try {
         Loggable l;
         try {
            ZipEntry ze = vjar.getEntry(fileName);
            if (ze == null) {
               wlJar = this.parseXMLFileWithSchema((VirtualJarFile)null, fileName, ejbDescriptor);
               if (wlJar == null) {
                  l = EJBLogger.logRdbmsDescriptorNotFoundInJarLoggable(fileName);
                  throw new RDBMSException(l.getMessageText());
               }
            } else {
               InputStream xmlStream = vjar.getInputStream(ze);
               xmlInputStream = new BufferedInputStream(xmlStream);
               String encoding = DDUtils.getXMLEncoding(xmlInputStream, fileName);
               xmlInputStream.mark(1048576);
               CMPDDParser result = null;
               Object o = procFac.getProcessor(xmlInputStream, RDBMSUtils.validRdbmsCmp11JarPublicIds);
               xmlInputStream.reset();
               if (o != null) {
                  if (!(o instanceof CMPDDParser)) {
                     Loggable l = EJBLogger.logincorrectXMLFileVersionLoggable(fileName, ejbName, o.getClass().getName());
                     throw new RDBMSException(l.getMessageText());
                  }

                  result = (CMPDDParser)o;
               }

               if (null == result) {
                  throw new AssertionError("Couldn't find a loader for weblogic-cmp-ejb-jar.xml. The document probably references an unknown dtd.");
               }

               result.setCurrentEJBName(ejbName);
               result.setFileName(fileName);
               result.setEJBDescriptor(ejbDescriptor);
               result.setEncoding(encoding);
               result.process((InputStream)xmlInputStream);
               wlJar = result.getDescriptorMBean();
               compat = result.getCompatibilitySettings();
            }
         } catch (ProcessorFactoryException var28) {
            try {
               xmlInputStream.reset();
               wlJar = this.parseXMLFileWithSchema(vjar, fileName, ejbDescriptor);
               if (wlJar == null) {
                  l = EJBLogger.logRdbmsDescriptorNotFoundInJarLoggable(fileName);
                  throw new RDBMSException(l.getMessageText());
               }
            } catch (RDBMSException var26) {
               throw var26;
            } catch (Exception var27) {
               throw new RDBMSException(StackTraceUtils.throwable2StackTrace(var27));
            }

            ejbDescriptor.addWeblogicRdbms11JarBean(wlJar);
         } catch (IOException var29) {
            throw new RDBMSException(StackTraceUtils.throwable2StackTrace(var29));
         }
      } finally {
         try {
            if (xmlInputStream != null) {
               xmlInputStream.close();
            }
         } catch (IOException var25) {
         }

      }

      return new RDBMSDeploymentInfo(wlJar, compat, fileName);
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

         return rdbmsJar;
      } catch (Exception var6) {
         throw new RDBMSException(StackTraceUtils.throwable2StackTrace(var6));
      }
   }

   public synchronized void readTypeSpecificData(VirtualJarFile vjar, String fileName) throws Exception {
      if (debugLogger.isDebugEnabled()) {
         debug("readTypeSpecificData called.");
      }

      if (this.rdbmsDeployment.needToReadFile(fileName)) {
         if (debugLogger.isDebugEnabled()) {
            debug("processing XML for bean: " + this.bd.getEJBName());
         }

         try {
            ProcessorFactory procFac = new ProcessorFactory();
            RDBMSDeploymentInfo rdbmsInfo = this.parseXMLFile(vjar, fileName, this.bd.getEJBName(), procFac, this.bd.getEjbDescriptorBean());
            this.rdbmsDeployment.addRdbmsBeans(rdbmsInfo.getRDBMSBeanMap());
            this.rdbmsDeployment.addDescriptorMBean(rdbmsInfo.getWeblogicRdbmsJarBean());
            this.rdbmsDeployment.addFileName(fileName);
         } catch (Exception var12) {
            if (debugLogger.isDebugEnabled()) {
               debug("parseXMLFile exception: " + var12);
            }

            throw var12;
         }
      }

      this.currBean = this.rdbmsDeployment.getRDBMSBean(this.bd.getEJBName());
      if (this.currBean == null) {
         Loggable l = EJBLogger.logUnableToFindBeanInRDBMSDescriptorLoggable(this.bd.getEJBName(), fileName);
         throw new RDBMSException(l.getMessageText());
      } else {
         this.currBean.setCMPBeanDescriptor(this.bd);
         RDBMSComplianceChecker checker = new RDBMSComplianceChecker(this.bd, this.ejbClass, this.fieldList);
         checker.checkCompliance();
         Iterator finders = this.currBean.getFinders();
         Finder finder = null;

         while(finders.hasNext()) {
            finder = (Finder)finders.next();
            Iterator finderExpressions = finder.getFinderExpressions();
            if (finderExpressions.hasNext()) {
               Loggable l = EJBLogger.logPersistenceUsesFinderExpressionsLoggable();
               throw new WLDeploymentException(l.getMessageText());
            }
         }

         boolean hasPrimaryKeyFinder = false;
         finders = this.currBean.getFinders();
         finder = null;

         while(finders.hasNext()) {
            finder = (Finder)finders.next();
            if (finder.getName().equals("findByPrimaryKey")) {
               hasPrimaryKeyFinder = true;
               finders.remove();
               break;
            }
         }

         Finder findByPrimaryKey = this.generateFindByPrimaryKeyFinder();
         if (hasPrimaryKeyFinder) {
            findByPrimaryKey.setFinderOptions(finder.getFinderOptions());
         }

         this.currBean.addFinder(findByPrimaryKey);
         if (debugLogger.isDebugEnabled()) {
            debug("currBean=" + this.currBean);
         }

         String b = "False";
         String validateDbSchemaWith = "";
         int databaseType = 0;
         WeblogicRdbmsJarBean cmpDesc = this.rdbmsDeployment.getDescriptorMBean(this.currBean.getEjbName());
         if (cmpDesc != null) {
            if (cmpDesc.isCreateDefaultDbmsTables()) {
               b = "CreateOnly";
            } else {
               b = "Disabled";
            }

            validateDbSchemaWith = cmpDesc.getValidateDbSchemaWith();
            databaseType = MethodUtils.dbmsType2int(cmpDesc.getDatabaseType());
         }

         if (validateDbSchemaWith == null) {
            validateDbSchemaWith = "";
         }

         this.currBean.setCreateDefaultDBMSTables(b);
         this.currBean.setValidateDbSchemaWith(validateDbSchemaWith);
         this.currBean.setDatabaseType(databaseType);
         this.currBean.setPrimaryKeyFields(this.primaryKeyList);
      }
   }

   public void saveTypeSpecificData(OutputStream out) {
      assert out != null;

      assert this.currBean != null;

      BeanWriter writer = new BeanWriter();
      writer.putRDBMSBean(this.currBean, out);
   }

   public void preCodeGeneration(CMPCodeGenerator codeGenerator) throws ErrorCollectionException {
      RDBMSCodeGenerator rdbmsStoreGen = (RDBMSCodeGenerator)codeGenerator;
      this.validateBeanSettings();
      this.currBean.generateFinderSQLStatements();
      rdbmsStoreGen.setCMPBeanDescriptor(this.bd);
      rdbmsStoreGen.setRDBMSBean(this.currBean);
      rdbmsStoreGen.setFinderList(this.finderList);
      rdbmsStoreGen.setPrimaryKeyFields(this.primaryKeyList);
      rdbmsStoreGen.setParameterMap(this.parameterMap);
      rdbmsStoreGen.setCMFields(this.currBean.getFieldNamesList());
   }

   public void postCodeGeneration(CMPCodeGenerator codeGenerator) {
      assert codeGenerator instanceof RDBMSCodeGenerator;

   }

   private void validateBeanSettings() throws InvalidBeanException {
      InvalidBeanException methodIBE = new InvalidBeanException();
      this.validateAttributeMapAndFieldList(methodIBE);
      this.validateAttributeFieldsInBean(methodIBE);
      this.validateFinderMethodsHaveDescriptors(methodIBE);
      this.validateFinderExpressionsHaveAppropriateTypes(methodIBE);
      this.validateFinderQueries(methodIBE);
      if (!methodIBE.getExceptions().isEmpty()) {
         throw methodIBE;
      }
   }

   private boolean validateFinderMethodsHaveDescriptors(InvalidBeanException methodIBE) {
      Iterator var2 = this.finderList.iterator();

      while(var2.hasNext()) {
         Method method = (Method)var2.next();
         Finder finderObject = this.currBean.getFinderForMethod(method);
         if (finderObject == null) {
            methodIBE.add(new FinderNotFoundException(method, this.currBean.getEjbName(), this.currBean.getFileName()));
         }
      }

      return true;
   }

   private void validateFinderExpressionsHaveAppropriateTypes(InvalidBeanException methodIBE) {
      IllegalExpressionException iee = null;
      Iterator finders = this.currBean.getFinders();

      while(finders.hasNext()) {
         Finder finder = (Finder)finders.next();
         Iterator finderExpressions = finder.getFinderExpressions();

         while(finderExpressions.hasNext()) {
            Finder.FinderExpression expression = (Finder.FinderExpression)finderExpressions.next();
            String expressionTypeName = expression.getExpressionType();
            Class expressionType = null;

            try {
               expressionType = ClassUtils.nameToClass(expressionTypeName, this.getClass().getClassLoader());
            } catch (ClassNotFoundException var10) {
               iee = new IllegalExpressionException(3, expressionTypeName, expression);
               iee.setFinder(finder);
               methodIBE.add(iee);
            }

            if (!TypeUtils.isValidSQLType(expressionType)) {
               iee = new IllegalExpressionException(3, expressionTypeName, expression);
               iee.setFinder(finder);
               methodIBE.add(iee);
            }
         }
      }

   }

   private void validateAttributeFieldsInBean(InvalidBeanException methodIBE) {
      Iterator objectLinks = this.currBean.getObjectLinks();

      while(objectLinks.hasNext()) {
         RDBMSBean.ObjectLink objectLink = (RDBMSBean.ObjectLink)objectLinks.next();
         String fieldName = objectLink.getBeanField();

         try {
            this.ejbClass.getField(fieldName);
         } catch (NoSuchFieldException var6) {
            methodIBE.add(new AttributeMapException(2, fieldName));
         }
      }

   }

   private void validateAttributeMapAndFieldList(InvalidBeanException methodIBE) {
      Iterator linkIter = this.currBean.getObjectLinks();

      String field;
      while(linkIter.hasNext()) {
         RDBMSBean.ObjectLink link = (RDBMSBean.ObjectLink)linkIter.next();
         field = link.getBeanField();
         if (!this.fieldListContainsField(field)) {
            methodIBE.add(new AttributeMapException(3, field));
         }
      }

      Iterator fieldIter = this.fieldList.iterator();

      while(fieldIter.hasNext()) {
         field = (String)fieldIter.next();
         if (!this.currBeanContainsField(field)) {
            methodIBE.add(new AttributeMapException(4, field));
         }
      }

   }

   private void validateFinderQueries(InvalidBeanException methodIBE) {
      Iterator finders = this.currBean.getFinders();

      while(finders.hasNext()) {
         Finder finder = (Finder)finders.next();

         IllegalExpressionException iee;
         try {
            finder.parseExpression();
         } catch (EJBCException var6) {
            iee = new IllegalExpressionException(4, finder.getWeblogicQuery());
            iee.setFinder(finder);
            methodIBE.add(iee);
         } catch (InvalidFinderException var7) {
            iee = new IllegalExpressionException(4, finder.getWeblogicQuery());
            iee.setFinder(finder);
            methodIBE.add(iee);
         }
      }

   }

   private Finder generateFindByPrimaryKeyFinder() {
      assert this.primaryKeyClass != null : "PrimaryKeyClass is null";

      assert this.ejbClass != null : "ejbClass is null";

      assert this.currBean != null : "currBean is null";

      StringBuffer query = new StringBuffer();
      Set pkList = this.bd.getPrimaryKeyFieldNames();

      assert pkList != null : "No primaryKeyList set in Deployer.";

      String[] pkArray = (String[])((String[])pkList.toArray(new String[0]));
      if (pkArray.length > 1) {
         query.append("(& ");
      }

      for(int iField = 0; iField < pkArray.length; ++iField) {
         String field = pkArray[iField];

         assert field != null : "Field " + iField + " in pkArray is null.";

         query.append(" (= $" + iField + " " + field + " ) ");
      }

      if (pkArray.length > 1) {
         query.append(")");
      }

      if (debugLogger.isDebugEnabled()) {
         debug("Created findByPrimaryKey query of " + query);
      }

      Finder finder = null;

      try {
         finder = new Finder("findByPrimaryKey", query.toString());
      } catch (InvalidFinderException var6) {
         throw new AssertionError("Caught an InvalidFinderException in generated finder: " + var6);
      }

      finder.addParameterType(this.primaryKeyClass.getName());
      return finder;
   }

   public String toString() {
      StringBuffer result = new StringBuffer(150);
      result.append("[weblogic.cmp11.rdbms.Deployer =\n");
      result.append("\tremote interface = " + (this.remoteInterfaceClass != null ? this.remoteInterfaceClass.getName() : "null"));
      result.append("\n");
      result.append("\thome interface = " + (this.homeInterfaceClass != null ? this.homeInterfaceClass.getName() : "null"));
      result.append("\n");
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

   private static void debug(String s) {
      debugLogger.debug("[Deployer] " + s);
   }

   public DescriptorBean parseXML(VirtualJarFile vjar, String fileName, String ejbName, ProcessorFactory procFac, EjbDescriptorBean ejbDescriptor) throws XMLParsingException, XMLProcessingException, RDBMSException, InvalidFinderException {
      RDBMSDeploymentInfo rdbmsDI = this.parseXMLFile(vjar, fileName, ejbName, procFac, ejbDescriptor);
      return (DescriptorBean)rdbmsDI.getWeblogicRdbmsJarBean();
   }

   static {
      debugLogger = EJBDebugService.cmpDeploymentLogger;
   }
}
