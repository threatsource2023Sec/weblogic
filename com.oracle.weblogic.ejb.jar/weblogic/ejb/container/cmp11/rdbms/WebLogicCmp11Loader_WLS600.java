package weblogic.ejb.container.cmp11.rdbms;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;
import org.xml.sax.InputSource;
import weblogic.ejb.container.compliance.EJBComplianceTextFormatter;
import weblogic.j2ee.descriptor.wl60.FieldMapBean;
import weblogic.j2ee.descriptor.wl60.FinderBean;
import weblogic.j2ee.descriptor.wl60.WeblogicRdbmsBeanBean;
import weblogic.j2ee.descriptor.wl60.WeblogicRdbmsJarBean;
import weblogic.utils.AssertionError;
import weblogic.utils.Debug;
import weblogic.xml.process.Functions;
import weblogic.xml.process.InProcessor;
import weblogic.xml.process.ProcessingContext;
import weblogic.xml.process.ProcessorDriver;
import weblogic.xml.process.SAXProcessorException;
import weblogic.xml.process.SAXValidationException;
import weblogic.xml.process.XMLParsingException;
import weblogic.xml.process.XMLProcessingException;
import weblogic.xml.process.XMLProcessor;

public final class WebLogicCmp11Loader_WLS600 extends CMPDDParser implements XMLProcessor, InProcessor {
   private static final boolean debug = true;
   private static final boolean verbose = true;
   private static final Map paths = new HashMap();
   private ProcessorDriver driver;
   private static final String publicId = "-//BEA Systems, Inc.//DTD WebLogic 6.0.0 EJB 1.1 RDBMS Persistence//EN";
   private static final String localDTDResourceName = "/weblogic/ejb/container/cmp11/rdbms/weblogic-rdbms11-persistence-600.dtd";

   public ProcessorDriver getDriver() {
      return this.driver;
   }

   public WebLogicCmp11Loader_WLS600() {
      this(true);
   }

   public WebLogicCmp11Loader_WLS600(boolean var1) {
      this.driver = new ProcessorDriver(this, "-//BEA Systems, Inc.//DTD WebLogic 6.0.0 EJB 1.1 RDBMS Persistence//EN", "/weblogic/ejb/container/cmp11/rdbms/weblogic-rdbms11-persistence-600.dtd", var1);
   }

   public void process(String var1) throws IOException, XMLParsingException, XMLProcessingException {
      this.driver.process(var1);
   }

   public void process(File var1) throws IOException, XMLParsingException, XMLProcessingException {
      this.driver.process(var1);
   }

   public void process(Reader var1) throws IOException, XMLParsingException, XMLProcessingException {
      this.driver.process(var1);
   }

   public void process(InputSource var1) throws IOException, XMLParsingException, XMLProcessingException {
      this.driver.process(var1);
   }

   public void process(InputStream var1) throws IOException, XMLParsingException, XMLProcessingException {
      this.driver.process(var1);
   }

   public void preProc(ProcessingContext var1) throws SAXProcessorException {
      Debug.assertion(var1 != null);
      String var2 = var1.getPath();
      Debug.assertion(var2 != null);
      Integer var3 = (Integer)paths.get(var2);
      if (var3 != null) {
         switch (var3) {
            case 128:
               this.__pre_128(var1);
               this.__post_128(var1);
               break;
            case 129:
               this.__pre_129(var1);
               this.__post_129(var1);
               break;
            case 130:
               this.__pre_130(var1);
               break;
            case 131:
               this.__pre_131(var1);
               break;
            case 132:
               this.__pre_132(var1);
               break;
            case 133:
               this.__pre_133(var1);
               break;
            case 134:
               this.__pre_134(var1);
               this.__post_134(var1);
               break;
            case 135:
               this.__pre_135(var1);
               break;
            case 136:
               this.__pre_136(var1);
               break;
            case 137:
               this.__pre_137(var1);
               this.__post_137(var1);
               break;
            case 138:
               this.__pre_138(var1);
               break;
            case 139:
               this.__pre_139(var1);
               break;
            case 140:
               this.__pre_140(var1);
               break;
            case 141:
               this.__pre_141(var1);
               break;
            case 142:
               this.__pre_142(var1);
               break;
            case 143:
               this.__pre_143(var1);
               break;
            case 144:
               this.__pre_144(var1);
               break;
            case 145:
               this.__pre_145(var1);
               break;
            case 146:
               this.__pre_146(var1);
               break;
            default:
               throw new AssertionError(var3.toString());
         }

      }
   }

   public void postProc(ProcessingContext var1) throws SAXProcessorException {
      Debug.assertion(var1 != null);
      String var2 = var1.getPath();
      Debug.assertion(var2 != null);
      Integer var3 = (Integer)paths.get(var2);
      if (var3 != null) {
         switch (var3) {
            case 128:
            case 129:
            case 134:
            case 137:
               break;
            case 130:
               this.__post_130(var1);
               break;
            case 131:
               this.__post_131(var1);
               break;
            case 132:
               this.__post_132(var1);
               break;
            case 133:
               this.__post_133(var1);
               break;
            case 135:
               this.__post_135(var1);
               break;
            case 136:
               this.__post_136(var1);
               break;
            case 138:
               this.__post_138(var1);
               break;
            case 139:
               this.__post_139(var1);
               break;
            case 140:
               this.__post_140(var1);
               break;
            case 141:
               this.__post_141(var1);
               break;
            case 142:
               this.__post_142(var1);
               break;
            case 143:
               this.__post_143(var1);
               break;
            case 144:
               this.__post_144(var1);
               break;
            case 145:
               this.__post_145(var1);
               break;
            case 146:
               this.__post_146(var1);
               break;
            default:
               throw new AssertionError(var3.toString());
         }

      }
   }

   private void __pre_140(ProcessingContext var1) {
   }

   private void __post_140(ProcessingContext var1) throws SAXProcessorException, SAXValidationException {
      String var2 = Functions.value(var1);
      FinderBean var3 = (FinderBean)var1.getBoundObject("finder");
      WeblogicRdbmsBeanBean var4 = (WeblogicRdbmsBeanBean)var1.getBoundObject("cmpBean");
      WeblogicRdbmsJarBean var5 = (WeblogicRdbmsJarBean)var1.getBoundObject("cmpJar");
      String var6 = var2;
      if (var2.trim().length() == 0) {
         var6 = "empty-finder";
      }

      var3.setFinderQuery(var6);
   }

   private void __pre_141(ProcessingContext var1) {
   }

   private void __post_141(ProcessingContext var1) throws SAXProcessorException, SAXValidationException {
      String var2 = Functions.value(var1);
      FinderBean var3 = (FinderBean)var1.getBoundObject("finder");
      WeblogicRdbmsBeanBean var4 = (WeblogicRdbmsBeanBean)var1.getBoundObject("cmpBean");
      WeblogicRdbmsJarBean var5 = (WeblogicRdbmsJarBean)var1.getBoundObject("cmpJar");
      var3.setFinderSql(var2);
   }

   private void __pre_129(ProcessingContext var1) {
      Object var2 = null;
      var1.addBoundObject(var2, "cmpBean");
   }

   private void __post_129(ProcessingContext var1) throws SAXProcessorException {
      WeblogicRdbmsBeanBean var2 = (WeblogicRdbmsBeanBean)var1.getBoundObject("cmpBean");
      WeblogicRdbmsJarBean var3 = (WeblogicRdbmsJarBean)var1.getBoundObject("cmpJar");
      this.addBoundObject(var3.createWeblogicRdbmsBean(), "cmpBean");
   }

   private void __pre_128(ProcessingContext var1) {
      Object var2 = null;
      var1.addBoundObject(var2, "cmpJar");
   }

   private void __post_128(ProcessingContext var1) throws SAXProcessorException {
      WeblogicRdbmsJarBean var2 = (WeblogicRdbmsJarBean)var1.getBoundObject("cmpJar");
      var2 = this.ejbDescriptor.createWeblogicRdbms11JarBean(this.encoding);
      this.addBoundObject(var2, "cmpJar");
      this.setDescriptorMBean(var2);
   }

   private void __pre_131(ProcessingContext var1) {
   }

   private void __post_131(ProcessingContext var1) throws SAXProcessorException, SAXValidationException {
      String var2 = Functions.value(var1);
      WeblogicRdbmsBeanBean var3 = (WeblogicRdbmsBeanBean)var1.getBoundObject("cmpBean");
      WeblogicRdbmsJarBean var4 = (WeblogicRdbmsJarBean)var1.getBoundObject("cmpJar");
      if (var2.length() == 0) {
         throw new SAXValidationException("PAction[1130478920](.weblogic-rdbms-jar.weblogic-rdbms-bean.pool-name.) must be a non-empty string");
      } else {
         var3.setPoolName(var2);
      }
   }

   private void __pre_138(ProcessingContext var1) {
   }

   private void __post_138(ProcessingContext var1) throws SAXProcessorException, SAXValidationException {
      String var2 = Functions.value(var1);
      FinderBean var3 = (FinderBean)var1.getBoundObject("finder");
      WeblogicRdbmsBeanBean var4 = (WeblogicRdbmsBeanBean)var1.getBoundObject("cmpBean");
      WeblogicRdbmsJarBean var5 = (WeblogicRdbmsJarBean)var1.getBoundObject("cmpJar");
      if (var2.length() == 0) {
         throw new SAXValidationException("PAction[1404928347](.weblogic-rdbms-jar.weblogic-rdbms-bean.finder.finder-name.) must be a non-empty string");
      } else {
         var3.setFinderName(var2);
      }
   }

   private void __pre_137(ProcessingContext var1) {
      Object var2 = null;
      var1.addBoundObject(var2, "finder");
   }

   private void __post_137(ProcessingContext var1) throws SAXProcessorException {
      FinderBean var2 = (FinderBean)var1.getBoundObject("finder");
      WeblogicRdbmsBeanBean var3 = (WeblogicRdbmsBeanBean)var1.getBoundObject("cmpBean");
      WeblogicRdbmsJarBean var4 = (WeblogicRdbmsJarBean)var1.getBoundObject("cmpJar");
      this.addBoundObject(var3.createFinder(), "finder");
   }

   private void __pre_130(ProcessingContext var1) {
   }

   private void __post_130(ProcessingContext var1) throws SAXProcessorException, SAXValidationException {
      String var2 = Functions.value(var1);
      WeblogicRdbmsBeanBean var3 = (WeblogicRdbmsBeanBean)var1.getBoundObject("cmpBean");
      WeblogicRdbmsJarBean var4 = (WeblogicRdbmsJarBean)var1.getBoundObject("cmpJar");
      if (var2.length() == 0) {
         throw new SAXValidationException("PAction[604107971](.weblogic-rdbms-jar.weblogic-rdbms-bean.ejb-name.) must be a non-empty string");
      } else {
         var3.setEjbName(var2.replace('/', '.'));
      }
   }

   private void __pre_132(ProcessingContext var1) {
   }

   private void __post_132(ProcessingContext var1) throws SAXProcessorException, SAXValidationException {
      String var2 = Functions.value(var1);
      WeblogicRdbmsBeanBean var3 = (WeblogicRdbmsBeanBean)var1.getBoundObject("cmpBean");
      WeblogicRdbmsJarBean var4 = (WeblogicRdbmsJarBean)var1.getBoundObject("cmpJar");
      if (var2.length() == 0) {
         throw new SAXValidationException("PAction[123961122](.weblogic-rdbms-jar.weblogic-rdbms-bean.data-source-name.) must be a non-empty string");
      } else {
         var3.setDataSourceJndiName(var2);
      }
   }

   private void __pre_145(ProcessingContext var1) {
   }

   private void __post_145(ProcessingContext var1) throws SAXProcessorException, SAXValidationException {
      String var2 = Functions.value(var1);
      WeblogicRdbmsJarBean var3 = (WeblogicRdbmsJarBean)var1.getBoundObject("cmpJar");
      if (var2.length() == 0) {
         throw new SAXValidationException("PAction[1227229563](.weblogic-rdbms-jar.validate-db-schema-with.) must be a non-empty string");
      } else {
         var3.setValidateDbSchemaWith(var2);
      }
   }

   private void __pre_143(ProcessingContext var1) {
   }

   private void __post_143(ProcessingContext var1) throws SAXProcessorException, SAXValidationException {
      String var2 = Functions.value(var1);
      WeblogicRdbmsBeanBean var3 = (WeblogicRdbmsBeanBean)var1.getBoundObject("cmpBean");
      WeblogicRdbmsJarBean var4 = (WeblogicRdbmsJarBean)var1.getBoundObject("cmpJar");
      if (var2.length() == 0) {
         throw new SAXValidationException("PAction[1982791261](.weblogic-rdbms-jar.weblogic-rdbms-bean.enable-tuned-updates.) must be a non-empty string");
      } else if (!"true".equals(var2) && !"True".equals(var2) && !"false".equals(var2) && !"False".equals(var2)) {
         throw new SAXValidationException("PAction[1982791261](.weblogic-rdbms-jar.weblogic-rdbms-bean.enable-tuned-updates.) must be one of the values: true,True,false,False");
      } else {
         var3.setEnableTunedUpdates("True".equalsIgnoreCase(var2));
      }
   }

   private void __pre_146(ProcessingContext var1) {
   }

   private void __post_146(ProcessingContext var1) throws SAXProcessorException, SAXValidationException {
      String var2 = Functions.value(var1);
      WeblogicRdbmsJarBean var3 = (WeblogicRdbmsJarBean)var1.getBoundObject("cmpJar");
      if (var2.length() == 0) {
         throw new SAXValidationException("PAction[1562557367](.weblogic-rdbms-jar.database-type.) must be a non-empty string");
      } else if (!"DB2".equalsIgnoreCase(var2) && !"ORACLE".equalsIgnoreCase(var2) && !"SYBASE".equalsIgnoreCase(var2) && !"INFORMIX".equalsIgnoreCase(var2) && !"POINTBASE".equalsIgnoreCase(var2) && !"TIMESTEN".equalsIgnoreCase(var2) && !"MYSQL".equalsIgnoreCase(var2) && !"SQL_SERVER".equalsIgnoreCase(var2) && !"SQLSERVER".equalsIgnoreCase(var2)) {
         throw new SAXValidationException(EJBComplianceTextFormatter.getInstance().ILLEGAL_DATABASETYPE_VALUE_VER71(var2));
      } else {
         var3.setDatabaseType(var2);
      }
   }

   private void __pre_134(ProcessingContext var1) {
      Object var2 = null;
      var1.addBoundObject(var2, "fm");
   }

   private void __post_134(ProcessingContext var1) throws SAXProcessorException {
      FieldMapBean var2 = (FieldMapBean)var1.getBoundObject("fm");
      WeblogicRdbmsBeanBean var3 = (WeblogicRdbmsBeanBean)var1.getBoundObject("cmpBean");
      WeblogicRdbmsJarBean var4 = (WeblogicRdbmsJarBean)var1.getBoundObject("cmpJar");
      this.addBoundObject(var3.createFieldMap(), "fm");
   }

   private void __pre_142(ProcessingContext var1) {
   }

   private void __post_142(ProcessingContext var1) throws SAXProcessorException {
      String var2 = Functions.value(var1);
      FinderBean var3 = (FinderBean)var1.getBoundObject("finder");
      WeblogicRdbmsBeanBean var4 = (WeblogicRdbmsBeanBean)var1.getBoundObject("cmpBean");
      WeblogicRdbmsJarBean var5 = (WeblogicRdbmsJarBean)var1.getBoundObject("cmpJar");
      var3.setFindForUpdate(Boolean.valueOf(var2));
   }

   private void __pre_133(ProcessingContext var1) {
   }

   private void __post_133(ProcessingContext var1) throws SAXProcessorException, SAXValidationException {
      String var2 = Functions.value(var1);
      WeblogicRdbmsBeanBean var3 = (WeblogicRdbmsBeanBean)var1.getBoundObject("cmpBean");
      WeblogicRdbmsJarBean var4 = (WeblogicRdbmsJarBean)var1.getBoundObject("cmpJar");
      if (var2.length() == 0) {
         throw new SAXValidationException("PAction[1101288798](.weblogic-rdbms-jar.weblogic-rdbms-bean.table-name.) must be a non-empty string");
      } else {
         var3.setTableName(var2);
      }
   }

   private void __pre_136(ProcessingContext var1) {
   }

   private void __post_136(ProcessingContext var1) throws SAXProcessorException, SAXValidationException {
      String var2 = Functions.value(var1);
      FieldMapBean var3 = (FieldMapBean)var1.getBoundObject("fm");
      WeblogicRdbmsBeanBean var4 = (WeblogicRdbmsBeanBean)var1.getBoundObject("cmpBean");
      WeblogicRdbmsJarBean var5 = (WeblogicRdbmsJarBean)var1.getBoundObject("cmpJar");
      if (var2.length() == 0) {
         throw new SAXValidationException("PAction[1617791695](.weblogic-rdbms-jar.weblogic-rdbms-bean.field-map.dbms-column.) must be a non-empty string");
      } else {
         var3.setDbmsColumn(var2);
      }
   }

   private void __pre_139(ProcessingContext var1) {
   }

   private void __post_139(ProcessingContext var1) throws SAXProcessorException, SAXValidationException {
      String var2 = Functions.value(var1);
      FinderBean var3 = (FinderBean)var1.getBoundObject("finder");
      WeblogicRdbmsBeanBean var4 = (WeblogicRdbmsBeanBean)var1.getBoundObject("cmpBean");
      WeblogicRdbmsJarBean var5 = (WeblogicRdbmsJarBean)var1.getBoundObject("cmpJar");
      if (var2.length() == 0) {
         throw new SAXValidationException("PAction[1192108080](.weblogic-rdbms-jar.weblogic-rdbms-bean.finder.finder-param.) must be a non-empty string");
      } else {
         var3.addFinderParam(var2);
      }
   }

   private void __pre_144(ProcessingContext var1) {
   }

   private void __post_144(ProcessingContext var1) throws SAXProcessorException, SAXValidationException {
      String var2 = Functions.value(var1);
      WeblogicRdbmsJarBean var3 = (WeblogicRdbmsJarBean)var1.getBoundObject("cmpJar");
      if (var2.length() == 0) {
         throw new SAXValidationException("PAction[1068824137](.weblogic-rdbms-jar.create-default-dbms-tables.) must be a non-empty string");
      } else {
         if (!"True".equalsIgnoreCase(var2) && !"CreateOnly".equalsIgnoreCase(var2)) {
            var3.setCreateDefaultDbmsTables(false);
         } else {
            var3.setCreateDefaultDbmsTables(true);
         }

      }
   }

   private void __pre_135(ProcessingContext var1) {
   }

   private void __post_135(ProcessingContext var1) throws SAXProcessorException, SAXValidationException {
      String var2 = Functions.value(var1);
      FieldMapBean var3 = (FieldMapBean)var1.getBoundObject("fm");
      WeblogicRdbmsBeanBean var4 = (WeblogicRdbmsBeanBean)var1.getBoundObject("cmpBean");
      WeblogicRdbmsJarBean var5 = (WeblogicRdbmsJarBean)var1.getBoundObject("cmpJar");
      if (var2.length() == 0) {
         throw new SAXValidationException("PAction[864237698](.weblogic-rdbms-jar.weblogic-rdbms-bean.field-map.cmp-field.) must be a non-empty string");
      } else {
         var3.setCmpField(var2);
      }
   }

   public void addBoundObject(Object var1, String var2) {
      this.driver.currentNode().addBoundObject(var1, var2);
   }

   static {
      paths.put(".weblogic-rdbms-jar.weblogic-rdbms-bean.finder.finder-query.", new Integer(140));
      paths.put(".weblogic-rdbms-jar.weblogic-rdbms-bean.finder.finder-sql.", new Integer(141));
      paths.put(".weblogic-rdbms-jar.weblogic-rdbms-bean.", new Integer(129));
      paths.put(".weblogic-rdbms-jar.", new Integer(128));
      paths.put(".weblogic-rdbms-jar.weblogic-rdbms-bean.pool-name.", new Integer(131));
      paths.put(".weblogic-rdbms-jar.weblogic-rdbms-bean.finder.finder-name.", new Integer(138));
      paths.put(".weblogic-rdbms-jar.weblogic-rdbms-bean.finder.", new Integer(137));
      paths.put(".weblogic-rdbms-jar.weblogic-rdbms-bean.ejb-name.", new Integer(130));
      paths.put(".weblogic-rdbms-jar.weblogic-rdbms-bean.data-source-name.", new Integer(132));
      paths.put(".weblogic-rdbms-jar.validate-db-schema-with.", new Integer(145));
      paths.put(".weblogic-rdbms-jar.weblogic-rdbms-bean.enable-tuned-updates.", new Integer(143));
      paths.put(".weblogic-rdbms-jar.database-type.", new Integer(146));
      paths.put(".weblogic-rdbms-jar.weblogic-rdbms-bean.field-map.", new Integer(134));
      paths.put(".weblogic-rdbms-jar.weblogic-rdbms-bean.finder.find-for-update.", new Integer(142));
      paths.put(".weblogic-rdbms-jar.weblogic-rdbms-bean.table-name.", new Integer(133));
      paths.put(".weblogic-rdbms-jar.weblogic-rdbms-bean.field-map.dbms-column.", new Integer(136));
      paths.put(".weblogic-rdbms-jar.weblogic-rdbms-bean.finder.finder-param.", new Integer(139));
      paths.put(".weblogic-rdbms-jar.create-default-dbms-tables.", new Integer(144));
      paths.put(".weblogic-rdbms-jar.weblogic-rdbms-bean.field-map.cmp-field.", new Integer(135));
   }
}
