package weblogic.ejb.container.cmp.rdbms;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;
import org.xml.sax.InputSource;
import weblogic.ejb.container.compliance.EJBComplianceTextFormatter;
import weblogic.j2ee.descriptor.wl.AutomaticKeyGenerationBean;
import weblogic.j2ee.descriptor.wl.ColumnMapBean;
import weblogic.j2ee.descriptor.wl.EjbQlQueryBean;
import weblogic.j2ee.descriptor.wl.FieldGroupBean;
import weblogic.j2ee.descriptor.wl.FieldMapBean;
import weblogic.j2ee.descriptor.wl.MethodParamsBean;
import weblogic.j2ee.descriptor.wl.QueryMethodBean;
import weblogic.j2ee.descriptor.wl.RelationshipRoleMapBean;
import weblogic.j2ee.descriptor.wl.TableMapBean;
import weblogic.j2ee.descriptor.wl.WeblogicQueryBean;
import weblogic.j2ee.descriptor.wl.WeblogicRdbmsBeanBean;
import weblogic.j2ee.descriptor.wl.WeblogicRdbmsJarBean;
import weblogic.j2ee.descriptor.wl.WeblogicRdbmsRelationBean;
import weblogic.j2ee.descriptor.wl.WeblogicRelationshipRoleBean;
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

public final class WebLogicCmp20Loader_WLS600 extends CMPDDParser implements XMLProcessor, InProcessor {
   private static final boolean debug = true;
   private static final boolean verbose = true;
   private static final Map paths = new HashMap();
   private ProcessorDriver driver;
   private static final String publicId = "-//BEA Systems, Inc.//DTD WebLogic 6.0.0 EJB RDBMS Persistence//EN";
   private static final String localDTDResourceName = "/weblogic/ejb/container/cmp/rdbms/weblogic-rdbms20-persistence-600.dtd";

   public ProcessorDriver getDriver() {
      return this.driver;
   }

   public WebLogicCmp20Loader_WLS600() {
      this(true);
   }

   public WebLogicCmp20Loader_WLS600(boolean var1) {
      this.driver = new ProcessorDriver(this, "-//BEA Systems, Inc.//DTD WebLogic 6.0.0 EJB RDBMS Persistence//EN", "/weblogic/ejb/container/cmp/rdbms/weblogic-rdbms20-persistence-600.dtd", var1);
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
               this.__post_133(var1);
               break;
            case 134:
               this.__pre_134(var1);
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
               this.__post_141(var1);
               break;
            case 142:
               this.__pre_142(var1);
               break;
            case 143:
               this.__pre_143(var1);
               this.__post_143(var1);
               break;
            case 144:
               this.__pre_144(var1);
               break;
            case 145:
               this.__pre_145(var1);
               this.__post_145(var1);
               break;
            case 146:
               this.__pre_146(var1);
               break;
            case 147:
               this.__pre_147(var1);
               break;
            case 148:
               this.__pre_148(var1);
               break;
            case 149:
               this.__pre_149(var1);
               break;
            case 150:
               this.__pre_150(var1);
               break;
            case 151:
               this.__pre_151(var1);
               break;
            case 152:
               this.__pre_152(var1);
               break;
            case 153:
               this.__pre_153(var1);
               this.__post_153(var1);
               break;
            case 154:
               this.__pre_154(var1);
               break;
            case 155:
               this.__pre_155(var1);
               break;
            case 156:
               this.__pre_156(var1);
               break;
            case 157:
               this.__pre_157(var1);
               this.__post_157(var1);
               break;
            case 158:
               this.__pre_158(var1);
               break;
            case 159:
               this.__pre_159(var1);
               break;
            case 160:
               this.__pre_160(var1);
               this.__post_160(var1);
               break;
            case 161:
               this.__pre_161(var1);
               break;
            case 162:
               this.__pre_162(var1);
               break;
            case 163:
               this.__pre_163(var1);
               this.__post_163(var1);
               break;
            case 164:
               this.__pre_164(var1);
               break;
            case 165:
               this.__pre_165(var1);
               break;
            case 166:
               this.__pre_166(var1);
               break;
            case 167:
               this.__pre_167(var1);
               break;
            case 168:
               this.__pre_168(var1);
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
            case 133:
            case 137:
            case 141:
            case 143:
            case 145:
            case 153:
            case 157:
            case 160:
            case 163:
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
            case 134:
               this.__post_134(var1);
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
            case 142:
               this.__post_142(var1);
               break;
            case 144:
               this.__post_144(var1);
               break;
            case 146:
               this.__post_146(var1);
               break;
            case 147:
               this.__post_147(var1);
               break;
            case 148:
               this.__post_148(var1);
               break;
            case 149:
               this.__post_149(var1);
               break;
            case 150:
               this.__post_150(var1);
               break;
            case 151:
               this.__post_151(var1);
               break;
            case 152:
               this.__post_152(var1);
               break;
            case 154:
               this.__post_154(var1);
               break;
            case 155:
               this.__post_155(var1);
               break;
            case 156:
               this.__post_156(var1);
               break;
            case 158:
               this.__post_158(var1);
               break;
            case 159:
               this.__post_159(var1);
               break;
            case 161:
               this.__post_161(var1);
               break;
            case 162:
               this.__post_162(var1);
               break;
            case 164:
               this.__post_164(var1);
               break;
            case 165:
               this.__post_165(var1);
               break;
            case 166:
               this.__post_166(var1);
               break;
            case 167:
               this.__post_167(var1);
               break;
            case 168:
               this.__post_168(var1);
               break;
            default:
               throw new AssertionError(var3.toString());
         }

      }
   }

   private void __pre_128(ProcessingContext var1) {
      Object var2 = null;
      var1.addBoundObject(var2, "cmpJar");
   }

   private void __post_128(ProcessingContext var1) throws SAXProcessorException {
      WeblogicRdbmsJarBean var2 = (WeblogicRdbmsJarBean)var1.getBoundObject("cmpJar");
      var2 = this.ejbDescriptor.createWeblogicRdbmsJarBean(this.encoding);
      this.addBoundObject(var2, "cmpJar");
      this.setDescriptorMBean(var2);
   }

   private void __pre_146(ProcessingContext var1) {
   }

   private void __post_146(ProcessingContext var1) throws SAXProcessorException {
      String var2 = Functions.value(var1);
      MethodParamsBean var3 = (MethodParamsBean)var1.getBoundObject("mp");
      QueryMethodBean var4 = (QueryMethodBean)var1.getBoundObject("qm");
      WeblogicQueryBean var5 = (WeblogicQueryBean)var1.getBoundObject("query");
      DefaultHelper var6 = (DefaultHelper)var1.getBoundObject("defaultHelper");
      WeblogicRdbmsBeanBean var7 = (WeblogicRdbmsBeanBean)var1.getBoundObject("cmpBean");
      TableMapBean var8 = (TableMapBean)var1.getBoundObject("tableMap");
      WeblogicRdbmsJarBean var9 = (WeblogicRdbmsJarBean)var1.getBoundObject("cmpJar");
      var3.addMethodParam(var2);
   }

   private void __pre_153(ProcessingContext var1) {
      Object var2 = null;
      var1.addBoundObject(var2, "keyGen");
   }

   private void __post_153(ProcessingContext var1) throws SAXProcessorException {
      AutomaticKeyGenerationBean var2 = (AutomaticKeyGenerationBean)var1.getBoundObject("keyGen");
      DefaultHelper var3 = (DefaultHelper)var1.getBoundObject("defaultHelper");
      WeblogicRdbmsBeanBean var4 = (WeblogicRdbmsBeanBean)var1.getBoundObject("cmpBean");
      TableMapBean var5 = (TableMapBean)var1.getBoundObject("tableMap");
      WeblogicRdbmsJarBean var6 = (WeblogicRdbmsJarBean)var1.getBoundObject("cmpJar");
      this.addBoundObject(var4.createAutomaticKeyGeneration(), "keyGen");
   }

   private void __pre_139(ProcessingContext var1) {
   }

   private void __post_139(ProcessingContext var1) throws SAXProcessorException, SAXValidationException {
      String var2 = Functions.value(var1);
      FieldGroupBean var3 = (FieldGroupBean)var1.getBoundObject("fg");
      DefaultHelper var4 = (DefaultHelper)var1.getBoundObject("defaultHelper");
      WeblogicRdbmsBeanBean var5 = (WeblogicRdbmsBeanBean)var1.getBoundObject("cmpBean");
      TableMapBean var6 = (TableMapBean)var1.getBoundObject("tableMap");
      WeblogicRdbmsJarBean var7 = (WeblogicRdbmsJarBean)var1.getBoundObject("cmpJar");
      if (var2.length() == 0) {
         throw new SAXValidationException("PAction[1130478920](.weblogic-rdbms-jar.weblogic-rdbms-bean.field-group.cmp-field.) must be a non-empty string");
      } else {
         var3.addCmpField(var2);
      }
   }

   private void __pre_142(ProcessingContext var1) {
   }

   private void __post_142(ProcessingContext var1) throws SAXProcessorException {
      String var2 = Functions.value(var1);
      WeblogicQueryBean var3 = (WeblogicQueryBean)var1.getBoundObject("query");
      DefaultHelper var4 = (DefaultHelper)var1.getBoundObject("defaultHelper");
      WeblogicRdbmsBeanBean var5 = (WeblogicRdbmsBeanBean)var1.getBoundObject("cmpBean");
      TableMapBean var6 = (TableMapBean)var1.getBoundObject("tableMap");
      WeblogicRdbmsJarBean var7 = (WeblogicRdbmsJarBean)var1.getBoundObject("cmpJar");
      var3.setDescription(var2);
   }

   private void __pre_161(ProcessingContext var1) {
   }

   private void __post_161(ProcessingContext var1) throws SAXProcessorException, SAXValidationException {
      String var2 = Functions.value(var1);
      WeblogicRelationshipRoleBean var3 = (WeblogicRelationshipRoleBean)var1.getBoundObject("relRole");
      WeblogicRdbmsRelationBean var4 = (WeblogicRdbmsRelationBean)var1.getBoundObject("relation");
      WeblogicRdbmsJarBean var5 = (WeblogicRdbmsJarBean)var1.getBoundObject("cmpJar");
      if (var2.length() == 0) {
         throw new SAXValidationException("PAction[1404928347](.weblogic-rdbms-jar.weblogic-rdbms-relation.weblogic-relationship-role.relationship-role-name.) must be a non-empty string");
      } else {
         var3.setRelationshipRoleName(var2);
      }
   }

   private void __pre_131(ProcessingContext var1) {
   }

   private void __post_131(ProcessingContext var1) throws SAXProcessorException, SAXValidationException {
      String var2 = Functions.value(var1);
      DefaultHelper var3 = (DefaultHelper)var1.getBoundObject("defaultHelper");
      WeblogicRdbmsBeanBean var4 = (WeblogicRdbmsBeanBean)var1.getBoundObject("cmpBean");
      TableMapBean var5 = (TableMapBean)var1.getBoundObject("tableMap");
      WeblogicRdbmsJarBean var6 = (WeblogicRdbmsJarBean)var1.getBoundObject("cmpJar");
      if (var2.length() == 0) {
         throw new SAXValidationException("PAction[604107971](.weblogic-rdbms-jar.weblogic-rdbms-bean.data-source-name.) must be a non-empty string");
      } else {
         var4.setDataSourceJndiName(var2);
      }
   }

   private void __pre_154(ProcessingContext var1) {
   }

   private void __post_154(ProcessingContext var1) throws SAXProcessorException, SAXValidationException {
      String var2 = Functions.value(var1);
      AutomaticKeyGenerationBean var3 = (AutomaticKeyGenerationBean)var1.getBoundObject("keyGen");
      DefaultHelper var4 = (DefaultHelper)var1.getBoundObject("defaultHelper");
      WeblogicRdbmsBeanBean var5 = (WeblogicRdbmsBeanBean)var1.getBoundObject("cmpBean");
      TableMapBean var6 = (TableMapBean)var1.getBoundObject("tableMap");
      WeblogicRdbmsJarBean var7 = (WeblogicRdbmsJarBean)var1.getBoundObject("cmpJar");
      if (var2.length() == 0) {
         throw new SAXValidationException("PAction[123961122](.weblogic-rdbms-jar.weblogic-rdbms-bean.automatic-key-generation.generator-type.) must be a non-empty string");
      } else {
         String var8;
         if ("Oracle".equalsIgnoreCase(var2)) {
            var8 = "Sequence";
            var7.setDatabaseType("Oracle");
         } else if (!"SQLServer".equalsIgnoreCase(var2) && !"SQL_SERVER".equalsIgnoreCase(var2)) {
            if (!"SQLServer2000".equalsIgnoreCase(var2) && !"SQL_SERVER_2000".equalsIgnoreCase(var2)) {
               if (!"NAMED_SEQUENCE_TABLE".equalsIgnoreCase(var2) && !"NamedSequenceTable".equalsIgnoreCase(var2)) {
                  throw new SAXValidationException(EJBComplianceTextFormatter.getInstance().ILLEGAL_AUTOKEY_GENERATOR_VALUE(this.getFileName(), var5.getEjbName(), var2));
               }

               var8 = "SequenceTable";
            } else {
               var8 = "Identity";
               var7.setDatabaseType("SQLServer2000");
            }
         } else {
            var8 = "Identity";
            var7.setDatabaseType("SQLServer");
         }

         var3.setGeneratorType(var8);
      }
   }

   private void __pre_168(ProcessingContext var1) {
   }

   private void __post_168(ProcessingContext var1) throws SAXProcessorException, SAXValidationException {
      String var2 = Functions.value(var1);
      WeblogicRdbmsJarBean var3 = (WeblogicRdbmsJarBean)var1.getBoundObject("cmpJar");
      if (var2.length() == 0) {
         throw new SAXValidationException("PAction[1227229563](.weblogic-rdbms-jar.validate-db-schema-with.) must be a non-empty string");
      } else {
         var3.setValidateDbSchemaWith(var2);
      }
   }

   private void __pre_158(ProcessingContext var1) {
   }

   private void __post_158(ProcessingContext var1) throws SAXProcessorException, SAXValidationException {
      String var2 = Functions.value(var1);
      WeblogicRdbmsRelationBean var3 = (WeblogicRdbmsRelationBean)var1.getBoundObject("relation");
      WeblogicRdbmsJarBean var4 = (WeblogicRdbmsJarBean)var1.getBoundObject("cmpJar");
      if (var2.length() == 0) {
         throw new SAXValidationException("PAction[1982791261](.weblogic-rdbms-jar.weblogic-rdbms-relation.relation-name.) must be a non-empty string");
      } else {
         var3.setRelationName(var2);
      }
   }

   private void __pre_141(ProcessingContext var1) {
      Object var2 = null;
      var1.addBoundObject(var2, "query");
   }

   private void __post_141(ProcessingContext var1) throws SAXProcessorException {
      WeblogicQueryBean var2 = (WeblogicQueryBean)var1.getBoundObject("query");
      DefaultHelper var3 = (DefaultHelper)var1.getBoundObject("defaultHelper");
      WeblogicRdbmsBeanBean var4 = (WeblogicRdbmsBeanBean)var1.getBoundObject("cmpBean");
      TableMapBean var5 = (TableMapBean)var1.getBoundObject("tableMap");
      WeblogicRdbmsJarBean var6 = (WeblogicRdbmsJarBean)var1.getBoundObject("cmpJar");
      var2 = var4.createWeblogicQuery();
      EjbQlQueryBean var7 = var2.createEjbQlQuery();
      this.addBoundObject(var2, "query");
   }

   private void __pre_133(ProcessingContext var1) {
      Object var2 = null;
      var1.addBoundObject(var2, "fm");
   }

   private void __post_133(ProcessingContext var1) throws SAXProcessorException {
      FieldMapBean var2 = (FieldMapBean)var1.getBoundObject("fm");
      DefaultHelper var3 = (DefaultHelper)var1.getBoundObject("defaultHelper");
      WeblogicRdbmsBeanBean var4 = (WeblogicRdbmsBeanBean)var1.getBoundObject("cmpBean");
      TableMapBean var5 = (TableMapBean)var1.getBoundObject("tableMap");
      WeblogicRdbmsJarBean var6 = (WeblogicRdbmsJarBean)var1.getBoundObject("cmpJar");
      this.addBoundObject(var5.createFieldMap(), "fm");
   }

   private void __pre_148(ProcessingContext var1) {
   }

   private void __post_148(ProcessingContext var1) throws SAXProcessorException, SAXValidationException {
      String var2 = Functions.value(var1);
      WeblogicQueryBean var3 = (WeblogicQueryBean)var1.getBoundObject("query");
      DefaultHelper var4 = (DefaultHelper)var1.getBoundObject("defaultHelper");
      WeblogicRdbmsBeanBean var5 = (WeblogicRdbmsBeanBean)var1.getBoundObject("cmpBean");
      TableMapBean var6 = (TableMapBean)var1.getBoundObject("tableMap");
      WeblogicRdbmsJarBean var7 = (WeblogicRdbmsJarBean)var1.getBoundObject("cmpJar");
      if (var2.length() == 0) {
         throw new SAXValidationException("PAction[1562557367](.weblogic-rdbms-jar.weblogic-rdbms-bean.weblogic-query.group-name.) must be a non-empty string");
      } else {
         EjbQlQueryBean var8 = var3.getEjbQlQuery();
         var8.setGroupName(var2);
      }
   }

   private void __pre_157(ProcessingContext var1) {
      Object var2 = null;
      var1.addBoundObject(var2, "relation");
   }

   private void __post_157(ProcessingContext var1) throws SAXProcessorException {
      WeblogicRdbmsRelationBean var2 = (WeblogicRdbmsRelationBean)var1.getBoundObject("relation");
      WeblogicRdbmsJarBean var3 = (WeblogicRdbmsJarBean)var1.getBoundObject("cmpJar");
      this.addBoundObject(var3.createWeblogicRdbmsRelation(), "relation");
   }

   private void __pre_136(ProcessingContext var1) {
   }

   private void __post_136(ProcessingContext var1) throws SAXProcessorException, SAXValidationException {
      String var2 = Functions.value(var1);
      FieldMapBean var3 = (FieldMapBean)var1.getBoundObject("fm");
      DefaultHelper var4 = (DefaultHelper)var1.getBoundObject("defaultHelper");
      WeblogicRdbmsBeanBean var5 = (WeblogicRdbmsBeanBean)var1.getBoundObject("cmpBean");
      TableMapBean var6 = (TableMapBean)var1.getBoundObject("tableMap");
      WeblogicRdbmsJarBean var7 = (WeblogicRdbmsJarBean)var1.getBoundObject("cmpJar");
      if (var2.length() == 0) {
         throw new SAXValidationException("PAction[1101288798](.weblogic-rdbms-jar.weblogic-rdbms-bean.field-map.dbms-column-type.) must be a non-empty string");
      } else if (!"OracleBlob".equals(var2) && !"OracleClob".equals(var2) && !"LongString".equals(var2)) {
         throw new SAXValidationException("PAction[1101288798](.weblogic-rdbms-jar.weblogic-rdbms-bean.field-map.dbms-column-type.) must be one of the values: OracleBlob,OracleClob,LongString");
      } else {
         String var8 = var2;
         if (var2.equals("OracleBlob")) {
            var7.setDatabaseType("Oracle");
            var8 = "Blob";
         } else if (var2.equals("OracleClob")) {
            var7.setDatabaseType("Oracle");
            var8 = "Clob";
         }

         var3.setDbmsColumnType(var8);
      }
   }

   private void __pre_149(ProcessingContext var1) {
   }

   private void __post_149(ProcessingContext var1) throws SAXProcessorException, SAXValidationException {
      String var2 = Functions.value(var1);
      WeblogicQueryBean var3 = (WeblogicQueryBean)var1.getBoundObject("query");
      DefaultHelper var4 = (DefaultHelper)var1.getBoundObject("defaultHelper");
      WeblogicRdbmsBeanBean var5 = (WeblogicRdbmsBeanBean)var1.getBoundObject("cmpBean");
      TableMapBean var6 = (TableMapBean)var1.getBoundObject("tableMap");
      WeblogicRdbmsJarBean var7 = (WeblogicRdbmsJarBean)var1.getBoundObject("cmpJar");
      if (var2.length() == 0) {
         throw new SAXValidationException("PAction[1617791695](.weblogic-rdbms-jar.weblogic-rdbms-bean.weblogic-query.max-elements.) must be a non-empty string");
      } else {
         try {
            this.validatePositiveInteger(var2);
         } catch (Exception var9) {
            throw new SAXValidationException("Path " + var1.getPath() + ": " + var9.getMessage());
         }

         int var8 = Integer.parseInt(var2);
         var3.setMaxElements(var8);
      }
   }

   private void __pre_155(ProcessingContext var1) {
   }

   private void __post_155(ProcessingContext var1) throws SAXProcessorException, SAXValidationException {
      String var2 = Functions.value(var1);
      AutomaticKeyGenerationBean var3 = (AutomaticKeyGenerationBean)var1.getBoundObject("keyGen");
      DefaultHelper var4 = (DefaultHelper)var1.getBoundObject("defaultHelper");
      WeblogicRdbmsBeanBean var5 = (WeblogicRdbmsBeanBean)var1.getBoundObject("cmpBean");
      TableMapBean var6 = (TableMapBean)var1.getBoundObject("tableMap");
      WeblogicRdbmsJarBean var7 = (WeblogicRdbmsJarBean)var1.getBoundObject("cmpJar");
      if (var2.length() == 0) {
         throw new SAXValidationException("PAction[1192108080](.weblogic-rdbms-jar.weblogic-rdbms-bean.automatic-key-generation.generator-name.) must be a non-empty string");
      } else {
         var3.setGeneratorName(var2);
      }
   }

   private void __pre_156(ProcessingContext var1) {
   }

   private void __post_156(ProcessingContext var1) throws SAXProcessorException, SAXValidationException {
      String var2 = Functions.value(var1);
      AutomaticKeyGenerationBean var3 = (AutomaticKeyGenerationBean)var1.getBoundObject("keyGen");
      DefaultHelper var4 = (DefaultHelper)var1.getBoundObject("defaultHelper");
      WeblogicRdbmsBeanBean var5 = (WeblogicRdbmsBeanBean)var1.getBoundObject("cmpBean");
      TableMapBean var6 = (TableMapBean)var1.getBoundObject("tableMap");
      WeblogicRdbmsJarBean var7 = (WeblogicRdbmsJarBean)var1.getBoundObject("cmpJar");
      if (var2.length() == 0) {
         throw new SAXValidationException("PAction[1068824137](.weblogic-rdbms-jar.weblogic-rdbms-bean.automatic-key-generation.key-cache-size.) must be a non-empty string");
      } else {
         int var8 = Integer.parseInt(var2);
         var3.setKeyCacheSize(var8);
      }
   }

   private void __pre_129(ProcessingContext var1) {
      DefaultHelper var2 = new DefaultHelper();
      var1.addBoundObject(var2, "defaultHelper");
      Object var3 = null;
      var1.addBoundObject(var3, "cmpBean");
      Object var4 = null;
      var1.addBoundObject(var4, "tableMap");
   }

   private void __post_129(ProcessingContext var1) throws SAXProcessorException {
      DefaultHelper var2 = (DefaultHelper)var1.getBoundObject("defaultHelper");
      WeblogicRdbmsBeanBean var3 = (WeblogicRdbmsBeanBean)var1.getBoundObject("cmpBean");
      TableMapBean var4 = (TableMapBean)var1.getBoundObject("tableMap");
      WeblogicRdbmsJarBean var5 = (WeblogicRdbmsJarBean)var1.getBoundObject("cmpJar");
      var3 = var5.createWeblogicRdbmsBean();
      this.addBoundObject(var3, "cmpBean");
      this.addBoundObject(var3.createTableMap(), "tableMap");
      var2.setVersion(6.0F);
   }

   private void __pre_151(ProcessingContext var1) {
   }

   private void __post_151(ProcessingContext var1) throws SAXProcessorException, SAXValidationException {
      String var2 = Functions.value(var1);
      WeblogicQueryBean var3 = (WeblogicQueryBean)var1.getBoundObject("query");
      DefaultHelper var4 = (DefaultHelper)var1.getBoundObject("defaultHelper");
      WeblogicRdbmsBeanBean var5 = (WeblogicRdbmsBeanBean)var1.getBoundObject("cmpBean");
      TableMapBean var6 = (TableMapBean)var1.getBoundObject("tableMap");
      WeblogicRdbmsJarBean var7 = (WeblogicRdbmsJarBean)var1.getBoundObject("cmpJar");
      if (var2.length() == 0) {
         throw new SAXValidationException("PAction[864237698](.weblogic-rdbms-jar.weblogic-rdbms-bean.weblogic-query.sql-select-distinct.) must be a non-empty string");
      } else if (!"True".equals(var2) && !"False".equals(var2) && !"true".equals(var2) && !"false".equals(var2)) {
         throw new SAXValidationException("PAction[864237698](.weblogic-rdbms-jar.weblogic-rdbms-bean.weblogic-query.sql-select-distinct.) must be one of the values: True,False,true,false");
      } else {
         var3.setSqlSelectDistinct("true".equalsIgnoreCase(var2));
      }
   }

   private void __pre_159(ProcessingContext var1) {
   }

   private void __post_159(ProcessingContext var1) throws SAXProcessorException, SAXValidationException {
      String var2 = Functions.value(var1);
      WeblogicRdbmsRelationBean var3 = (WeblogicRdbmsRelationBean)var1.getBoundObject("relation");
      WeblogicRdbmsJarBean var4 = (WeblogicRdbmsJarBean)var1.getBoundObject("cmpJar");
      if (var2.length() == 0) {
         throw new SAXValidationException("PAction[537548559](.weblogic-rdbms-jar.weblogic-rdbms-relation.table-name.) must be a non-empty string");
      } else {
         var3.setTableName(var2);
      }
   }

   private void __pre_152(ProcessingContext var1) {
   }

   private void __post_152(ProcessingContext var1) throws SAXProcessorException, SAXValidationException {
      String var2 = Functions.value(var1);
      DefaultHelper var3 = (DefaultHelper)var1.getBoundObject("defaultHelper");
      WeblogicRdbmsBeanBean var4 = (WeblogicRdbmsBeanBean)var1.getBoundObject("cmpBean");
      TableMapBean var5 = (TableMapBean)var1.getBoundObject("tableMap");
      WeblogicRdbmsJarBean var6 = (WeblogicRdbmsJarBean)var1.getBoundObject("cmpJar");
      if (var2.length() == 0) {
         throw new SAXValidationException("PAction[380894366](.weblogic-rdbms-jar.weblogic-rdbms-bean.delay-database-insert-until.) must be a non-empty string");
      } else if (!"ejbCreate".equals(var2) && !"ejbPostCreate".equals(var2) && !"EjbCreate".equals(var2) && !"EjbPostCreate".equals(var2) && !"ejbcreate".equals(var2) && !"ejbpostcreate".equals(var2)) {
         throw new SAXValidationException("PAction[380894366](.weblogic-rdbms-jar.weblogic-rdbms-bean.delay-database-insert-until.) must be one of the values: ejbCreate,ejbPostCreate,EjbCreate,EjbPostCreate,ejbcreate,ejbpostcreate");
      } else {
         String var7 = null;
         if ("ejbcreate".equalsIgnoreCase(var2)) {
            var7 = "ejbCreate";
         } else {
            var7 = "ejbPostCreate";
         }

         var4.setDelayDatabaseInsertUntil(var7);
      }
   }

   private void __pre_163(ProcessingContext var1) {
      Object var2 = null;
      var1.addBoundObject(var2, "colMap");
   }

   private void __post_163(ProcessingContext var1) throws SAXProcessorException {
      ColumnMapBean var2 = (ColumnMapBean)var1.getBoundObject("colMap");
      WeblogicRelationshipRoleBean var3 = (WeblogicRelationshipRoleBean)var1.getBoundObject("relRole");
      WeblogicRdbmsRelationBean var4 = (WeblogicRdbmsRelationBean)var1.getBoundObject("relation");
      WeblogicRdbmsJarBean var5 = (WeblogicRdbmsJarBean)var1.getBoundObject("cmpJar");
      RelationshipRoleMapBean var6 = var3.getRelationshipRoleMap();
      if (var6 == null) {
         var6 = var3.createRelationshipRoleMap();
      }

      this.addBoundObject(var6.createColumnMap(), "colMap");
   }

   private void __pre_160(ProcessingContext var1) {
      Object var2 = null;
      var1.addBoundObject(var2, "relRole");
   }

   private void __post_160(ProcessingContext var1) throws SAXProcessorException {
      WeblogicRelationshipRoleBean var2 = (WeblogicRelationshipRoleBean)var1.getBoundObject("relRole");
      WeblogicRdbmsRelationBean var3 = (WeblogicRdbmsRelationBean)var1.getBoundObject("relation");
      WeblogicRdbmsJarBean var4 = (WeblogicRdbmsJarBean)var1.getBoundObject("cmpJar");
      this.addBoundObject(var3.createWeblogicRelationshipRole(), "relRole");
   }

   private void __pre_130(ProcessingContext var1) {
   }

   private void __post_130(ProcessingContext var1) throws SAXProcessorException, SAXValidationException {
      String var2 = Functions.value(var1);
      DefaultHelper var3 = (DefaultHelper)var1.getBoundObject("defaultHelper");
      WeblogicRdbmsBeanBean var4 = (WeblogicRdbmsBeanBean)var1.getBoundObject("cmpBean");
      TableMapBean var5 = (TableMapBean)var1.getBoundObject("tableMap");
      WeblogicRdbmsJarBean var6 = (WeblogicRdbmsJarBean)var1.getBoundObject("cmpJar");
      if (var2.length() == 0) {
         throw new SAXValidationException("PAction[237852351](.weblogic-rdbms-jar.weblogic-rdbms-bean.ejb-name.) must be a non-empty string");
      } else {
         var4.setEjbName(var2.replace('/', '.'));
         this.addDefaultHelper(var4.getEjbName(), var3);
      }
   }

   private void __pre_143(ProcessingContext var1) {
      Object var2 = null;
      var1.addBoundObject(var2, "qm");
   }

   private void __post_143(ProcessingContext var1) throws SAXProcessorException {
      QueryMethodBean var2 = (QueryMethodBean)var1.getBoundObject("qm");
      WeblogicQueryBean var3 = (WeblogicQueryBean)var1.getBoundObject("query");
      DefaultHelper var4 = (DefaultHelper)var1.getBoundObject("defaultHelper");
      WeblogicRdbmsBeanBean var5 = (WeblogicRdbmsBeanBean)var1.getBoundObject("cmpBean");
      TableMapBean var6 = (TableMapBean)var1.getBoundObject("tableMap");
      WeblogicRdbmsJarBean var7 = (WeblogicRdbmsJarBean)var1.getBoundObject("cmpJar");
      this.addBoundObject(var3.createQueryMethod(), "qm");
   }

   private void __pre_144(ProcessingContext var1) {
   }

   private void __post_144(ProcessingContext var1) throws SAXProcessorException, SAXValidationException {
      String var2 = Functions.value(var1);
      QueryMethodBean var3 = (QueryMethodBean)var1.getBoundObject("qm");
      WeblogicQueryBean var4 = (WeblogicQueryBean)var1.getBoundObject("query");
      DefaultHelper var5 = (DefaultHelper)var1.getBoundObject("defaultHelper");
      WeblogicRdbmsBeanBean var6 = (WeblogicRdbmsBeanBean)var1.getBoundObject("cmpBean");
      TableMapBean var7 = (TableMapBean)var1.getBoundObject("tableMap");
      WeblogicRdbmsJarBean var8 = (WeblogicRdbmsJarBean)var1.getBoundObject("cmpJar");
      if (var2.length() == 0) {
         throw new SAXValidationException("PAction[608188624](.weblogic-rdbms-jar.weblogic-rdbms-bean.weblogic-query.query-method.method-name.) must be a non-empty string");
      } else {
         var3.setMethodName(var2);
      }
   }

   private void __pre_164(ProcessingContext var1) {
   }

   private void __post_164(ProcessingContext var1) throws SAXProcessorException, SAXValidationException {
      String var2 = Functions.value(var1);
      ColumnMapBean var3 = (ColumnMapBean)var1.getBoundObject("colMap");
      WeblogicRelationshipRoleBean var4 = (WeblogicRelationshipRoleBean)var1.getBoundObject("relRole");
      WeblogicRdbmsRelationBean var5 = (WeblogicRdbmsRelationBean)var1.getBoundObject("relation");
      WeblogicRdbmsJarBean var6 = (WeblogicRdbmsJarBean)var1.getBoundObject("cmpJar");
      if (var2.length() == 0) {
         throw new SAXValidationException("PAction[1451270520](.weblogic-rdbms-jar.weblogic-rdbms-relation.weblogic-relationship-role.column-map.foreign-key-column.) must be a non-empty string");
      } else {
         var3.setForeignKeyColumn(var2);
      }
   }

   private void __pre_145(ProcessingContext var1) {
      Object var2 = null;
      var1.addBoundObject(var2, "mp");
   }

   private void __post_145(ProcessingContext var1) throws SAXProcessorException {
      MethodParamsBean var2 = (MethodParamsBean)var1.getBoundObject("mp");
      QueryMethodBean var3 = (QueryMethodBean)var1.getBoundObject("qm");
      WeblogicQueryBean var4 = (WeblogicQueryBean)var1.getBoundObject("query");
      DefaultHelper var5 = (DefaultHelper)var1.getBoundObject("defaultHelper");
      WeblogicRdbmsBeanBean var6 = (WeblogicRdbmsBeanBean)var1.getBoundObject("cmpBean");
      TableMapBean var7 = (TableMapBean)var1.getBoundObject("tableMap");
      WeblogicRdbmsJarBean var8 = (WeblogicRdbmsJarBean)var1.getBoundObject("cmpJar");
      this.addBoundObject(var3.createMethodParams(), "mp");
   }

   private void __pre_162(ProcessingContext var1) {
   }

   private void __post_162(ProcessingContext var1) throws SAXProcessorException, SAXValidationException {
      String var2 = Functions.value(var1);
      WeblogicRelationshipRoleBean var3 = (WeblogicRelationshipRoleBean)var1.getBoundObject("relRole");
      WeblogicRdbmsRelationBean var4 = (WeblogicRdbmsRelationBean)var1.getBoundObject("relation");
      WeblogicRdbmsJarBean var5 = (WeblogicRdbmsJarBean)var1.getBoundObject("cmpJar");
      if (var2.length() == 0) {
         throw new SAXValidationException("PAction[1608446010](.weblogic-rdbms-jar.weblogic-rdbms-relation.weblogic-relationship-role.group-name.) must be a non-empty string");
      } else {
         var3.setGroupName(var2);
      }
   }

   private void __pre_165(ProcessingContext var1) {
   }

   private void __post_165(ProcessingContext var1) throws SAXProcessorException, SAXValidationException {
      String var2 = Functions.value(var1);
      ColumnMapBean var3 = (ColumnMapBean)var1.getBoundObject("colMap");
      WeblogicRelationshipRoleBean var4 = (WeblogicRelationshipRoleBean)var1.getBoundObject("relRole");
      WeblogicRdbmsRelationBean var5 = (WeblogicRdbmsRelationBean)var1.getBoundObject("relation");
      WeblogicRdbmsJarBean var6 = (WeblogicRdbmsJarBean)var1.getBoundObject("cmpJar");
      if (var2.length() == 0) {
         throw new SAXValidationException("PAction[992136656](.weblogic-rdbms-jar.weblogic-rdbms-relation.weblogic-relationship-role.column-map.key-column.) must be a non-empty string");
      } else {
         var3.setKeyColumn(var2);
      }
   }

   private void __pre_150(ProcessingContext var1) {
   }

   private void __post_150(ProcessingContext var1) throws SAXProcessorException, SAXValidationException {
      String var2 = Functions.value(var1);
      WeblogicQueryBean var3 = (WeblogicQueryBean)var1.getBoundObject("query");
      DefaultHelper var4 = (DefaultHelper)var1.getBoundObject("defaultHelper");
      WeblogicRdbmsBeanBean var5 = (WeblogicRdbmsBeanBean)var1.getBoundObject("cmpBean");
      TableMapBean var6 = (TableMapBean)var1.getBoundObject("tableMap");
      WeblogicRdbmsJarBean var7 = (WeblogicRdbmsJarBean)var1.getBoundObject("cmpJar");
      if (var2.length() == 0) {
         throw new SAXValidationException("PAction[511833308](.weblogic-rdbms-jar.weblogic-rdbms-bean.weblogic-query.include-updates.) must be a non-empty string");
      } else if (!"True".equals(var2) && !"False".equals(var2) && !"true".equals(var2) && !"false".equals(var2)) {
         throw new SAXValidationException("PAction[511833308](.weblogic-rdbms-jar.weblogic-rdbms-bean.weblogic-query.include-updates.) must be one of the values: True,False,true,false");
      } else {
         var4.addQueryWithIncludeUpdates(var3, var2);
         var3.setIncludeUpdates("true".equalsIgnoreCase(var2));
      }
   }

   private void __pre_166(ProcessingContext var1) {
   }

   private void __post_166(ProcessingContext var1) throws SAXProcessorException {
      WeblogicRelationshipRoleBean var2 = (WeblogicRelationshipRoleBean)var1.getBoundObject("relRole");
      WeblogicRdbmsRelationBean var3 = (WeblogicRdbmsRelationBean)var1.getBoundObject("relation");
      WeblogicRdbmsJarBean var4 = (WeblogicRdbmsJarBean)var1.getBoundObject("cmpJar");
      var2.createDbCascadeDelete();
   }

   private void __pre_132(ProcessingContext var1) {
   }

   private void __post_132(ProcessingContext var1) throws SAXProcessorException, SAXValidationException {
      String var2 = Functions.value(var1);
      DefaultHelper var3 = (DefaultHelper)var1.getBoundObject("defaultHelper");
      WeblogicRdbmsBeanBean var4 = (WeblogicRdbmsBeanBean)var1.getBoundObject("cmpBean");
      TableMapBean var5 = (TableMapBean)var1.getBoundObject("tableMap");
      WeblogicRdbmsJarBean var6 = (WeblogicRdbmsJarBean)var1.getBoundObject("cmpJar");
      if (var2.length() == 0) {
         throw new SAXValidationException("PAction[1297685781](.weblogic-rdbms-jar.weblogic-rdbms-bean.table-name.) must be a non-empty string");
      } else {
         var5.setTableName(var2);
      }
   }

   private void __pre_135(ProcessingContext var1) {
   }

   private void __post_135(ProcessingContext var1) throws SAXProcessorException, SAXValidationException {
      String var2 = Functions.value(var1);
      FieldMapBean var3 = (FieldMapBean)var1.getBoundObject("fm");
      DefaultHelper var4 = (DefaultHelper)var1.getBoundObject("defaultHelper");
      WeblogicRdbmsBeanBean var5 = (WeblogicRdbmsBeanBean)var1.getBoundObject("cmpBean");
      TableMapBean var6 = (TableMapBean)var1.getBoundObject("tableMap");
      WeblogicRdbmsJarBean var7 = (WeblogicRdbmsJarBean)var1.getBoundObject("cmpJar");
      if (var2.length() == 0) {
         throw new SAXValidationException("PAction[1705929636](.weblogic-rdbms-jar.weblogic-rdbms-bean.field-map.dbms-column.) must be a non-empty string");
      } else {
         var3.setDbmsColumn(var2);
      }
   }

   private void __pre_140(ProcessingContext var1) {
   }

   private void __post_140(ProcessingContext var1) throws SAXProcessorException, SAXValidationException {
      String var2 = Functions.value(var1);
      FieldGroupBean var3 = (FieldGroupBean)var1.getBoundObject("fg");
      DefaultHelper var4 = (DefaultHelper)var1.getBoundObject("defaultHelper");
      WeblogicRdbmsBeanBean var5 = (WeblogicRdbmsBeanBean)var1.getBoundObject("cmpBean");
      TableMapBean var6 = (TableMapBean)var1.getBoundObject("tableMap");
      WeblogicRdbmsJarBean var7 = (WeblogicRdbmsJarBean)var1.getBoundObject("cmpJar");
      if (var2.length() == 0) {
         throw new SAXValidationException("PAction[1221555852](.weblogic-rdbms-jar.weblogic-rdbms-bean.field-group.cmr-field.) must be a non-empty string");
      } else {
         var3.addCmrField(var2);
      }
   }

   private void __pre_167(ProcessingContext var1) {
   }

   private void __post_167(ProcessingContext var1) throws SAXProcessorException, SAXValidationException {
      String var2 = Functions.value(var1);
      WeblogicRdbmsJarBean var3 = (WeblogicRdbmsJarBean)var1.getBoundObject("cmpJar");
      if (var2.length() == 0) {
         throw new SAXValidationException("PAction[1509514333](.weblogic-rdbms-jar.create-default-dbms-tables.) must be a non-empty string");
      } else {
         if (!"True".equalsIgnoreCase(var2) && !"CreateOnly".equalsIgnoreCase(var2)) {
            var3.setCreateDefaultDbmsTables("Disabled");
         } else {
            var3.setCreateDefaultDbmsTables("CreateOnly");
         }

      }
   }

   private void __pre_134(ProcessingContext var1) {
   }

   private void __post_134(ProcessingContext var1) throws SAXProcessorException, SAXValidationException {
      String var2 = Functions.value(var1);
      FieldMapBean var3 = (FieldMapBean)var1.getBoundObject("fm");
      DefaultHelper var4 = (DefaultHelper)var1.getBoundObject("defaultHelper");
      WeblogicRdbmsBeanBean var5 = (WeblogicRdbmsBeanBean)var1.getBoundObject("cmpBean");
      TableMapBean var6 = (TableMapBean)var1.getBoundObject("tableMap");
      WeblogicRdbmsJarBean var7 = (WeblogicRdbmsJarBean)var1.getBoundObject("cmpJar");
      if (var2.length() == 0) {
         throw new SAXValidationException("PAction[1556956098](.weblogic-rdbms-jar.weblogic-rdbms-bean.field-map.cmp-field.) must be a non-empty string");
      } else {
         var3.setCmpField(var2);
      }
   }

   private void __pre_137(ProcessingContext var1) {
      Object var2 = null;
      var1.addBoundObject(var2, "fg");
   }

   private void __post_137(ProcessingContext var1) throws SAXProcessorException {
      FieldGroupBean var2 = (FieldGroupBean)var1.getBoundObject("fg");
      DefaultHelper var3 = (DefaultHelper)var1.getBoundObject("defaultHelper");
      WeblogicRdbmsBeanBean var4 = (WeblogicRdbmsBeanBean)var1.getBoundObject("cmpBean");
      TableMapBean var5 = (TableMapBean)var1.getBoundObject("tableMap");
      WeblogicRdbmsJarBean var6 = (WeblogicRdbmsJarBean)var1.getBoundObject("cmpJar");
      this.addBoundObject(var4.createFieldGroup(), "fg");
   }

   private void __pre_138(ProcessingContext var1) {
   }

   private void __post_138(ProcessingContext var1) throws SAXProcessorException, SAXValidationException {
      String var2 = Functions.value(var1);
      FieldGroupBean var3 = (FieldGroupBean)var1.getBoundObject("fg");
      DefaultHelper var4 = (DefaultHelper)var1.getBoundObject("defaultHelper");
      WeblogicRdbmsBeanBean var5 = (WeblogicRdbmsBeanBean)var1.getBoundObject("cmpBean");
      TableMapBean var6 = (TableMapBean)var1.getBoundObject("tableMap");
      WeblogicRdbmsJarBean var7 = (WeblogicRdbmsJarBean)var1.getBoundObject("cmpJar");
      if (var2.length() == 0) {
         throw new SAXValidationException("PAction[1252585652](.weblogic-rdbms-jar.weblogic-rdbms-bean.field-group.group-name.) must be a non-empty string");
      } else {
         var3.setGroupName(var2);
      }
   }

   private void __pre_147(ProcessingContext var1) {
   }

   private void __post_147(ProcessingContext var1) throws SAXProcessorException, SAXValidationException {
      String var2 = Functions.value(var1);
      WeblogicQueryBean var3 = (WeblogicQueryBean)var1.getBoundObject("query");
      DefaultHelper var4 = (DefaultHelper)var1.getBoundObject("defaultHelper");
      WeblogicRdbmsBeanBean var5 = (WeblogicRdbmsBeanBean)var1.getBoundObject("cmpBean");
      TableMapBean var6 = (TableMapBean)var1.getBoundObject("tableMap");
      WeblogicRdbmsJarBean var7 = (WeblogicRdbmsJarBean)var1.getBoundObject("cmpJar");
      if (var2.length() == 0) {
         throw new SAXValidationException("PAction[2036368507](.weblogic-rdbms-jar.weblogic-rdbms-bean.weblogic-query.weblogic-ql.) must be a non-empty string");
      } else {
         EjbQlQueryBean var8 = var3.getEjbQlQuery();
         var8.setWeblogicQl(var2);
      }
   }

   public void addBoundObject(Object var1, String var2) {
      this.driver.currentNode().addBoundObject(var1, var2);
   }

   static {
      paths.put(".weblogic-rdbms-jar.", new Integer(128));
      paths.put(".weblogic-rdbms-jar.weblogic-rdbms-bean.weblogic-query.query-method.method-params.method-param.", new Integer(146));
      paths.put(".weblogic-rdbms-jar.weblogic-rdbms-bean.automatic-key-generation.", new Integer(153));
      paths.put(".weblogic-rdbms-jar.weblogic-rdbms-bean.field-group.cmp-field.", new Integer(139));
      paths.put(".weblogic-rdbms-jar.weblogic-rdbms-bean.weblogic-query.description.", new Integer(142));
      paths.put(".weblogic-rdbms-jar.weblogic-rdbms-relation.weblogic-relationship-role.relationship-role-name.", new Integer(161));
      paths.put(".weblogic-rdbms-jar.weblogic-rdbms-bean.data-source-name.", new Integer(131));
      paths.put(".weblogic-rdbms-jar.weblogic-rdbms-bean.automatic-key-generation.generator-type.", new Integer(154));
      paths.put(".weblogic-rdbms-jar.validate-db-schema-with.", new Integer(168));
      paths.put(".weblogic-rdbms-jar.weblogic-rdbms-relation.relation-name.", new Integer(158));
      paths.put(".weblogic-rdbms-jar.weblogic-rdbms-bean.weblogic-query.", new Integer(141));
      paths.put(".weblogic-rdbms-jar.weblogic-rdbms-bean.field-map.", new Integer(133));
      paths.put(".weblogic-rdbms-jar.weblogic-rdbms-bean.weblogic-query.group-name.", new Integer(148));
      paths.put(".weblogic-rdbms-jar.weblogic-rdbms-relation.", new Integer(157));
      paths.put(".weblogic-rdbms-jar.weblogic-rdbms-bean.field-map.dbms-column-type.", new Integer(136));
      paths.put(".weblogic-rdbms-jar.weblogic-rdbms-bean.weblogic-query.max-elements.", new Integer(149));
      paths.put(".weblogic-rdbms-jar.weblogic-rdbms-bean.automatic-key-generation.generator-name.", new Integer(155));
      paths.put(".weblogic-rdbms-jar.weblogic-rdbms-bean.automatic-key-generation.key-cache-size.", new Integer(156));
      paths.put(".weblogic-rdbms-jar.weblogic-rdbms-bean.", new Integer(129));
      paths.put(".weblogic-rdbms-jar.weblogic-rdbms-bean.weblogic-query.sql-select-distinct.", new Integer(151));
      paths.put(".weblogic-rdbms-jar.weblogic-rdbms-relation.table-name.", new Integer(159));
      paths.put(".weblogic-rdbms-jar.weblogic-rdbms-bean.delay-database-insert-until.", new Integer(152));
      paths.put(".weblogic-rdbms-jar.weblogic-rdbms-relation.weblogic-relationship-role.column-map.", new Integer(163));
      paths.put(".weblogic-rdbms-jar.weblogic-rdbms-relation.weblogic-relationship-role.", new Integer(160));
      paths.put(".weblogic-rdbms-jar.weblogic-rdbms-bean.ejb-name.", new Integer(130));
      paths.put(".weblogic-rdbms-jar.weblogic-rdbms-bean.weblogic-query.query-method.", new Integer(143));
      paths.put(".weblogic-rdbms-jar.weblogic-rdbms-bean.weblogic-query.query-method.method-name.", new Integer(144));
      paths.put(".weblogic-rdbms-jar.weblogic-rdbms-relation.weblogic-relationship-role.column-map.foreign-key-column.", new Integer(164));
      paths.put(".weblogic-rdbms-jar.weblogic-rdbms-bean.weblogic-query.query-method.method-params.", new Integer(145));
      paths.put(".weblogic-rdbms-jar.weblogic-rdbms-relation.weblogic-relationship-role.group-name.", new Integer(162));
      paths.put(".weblogic-rdbms-jar.weblogic-rdbms-relation.weblogic-relationship-role.column-map.key-column.", new Integer(165));
      paths.put(".weblogic-rdbms-jar.weblogic-rdbms-bean.weblogic-query.include-updates.", new Integer(150));
      paths.put(".weblogic-rdbms-jar.weblogic-rdbms-relation.weblogic-relationship-role.db-cascade-delete.", new Integer(166));
      paths.put(".weblogic-rdbms-jar.weblogic-rdbms-bean.table-name.", new Integer(132));
      paths.put(".weblogic-rdbms-jar.weblogic-rdbms-bean.field-map.dbms-column.", new Integer(135));
      paths.put(".weblogic-rdbms-jar.weblogic-rdbms-bean.field-group.cmr-field.", new Integer(140));
      paths.put(".weblogic-rdbms-jar.create-default-dbms-tables.", new Integer(167));
      paths.put(".weblogic-rdbms-jar.weblogic-rdbms-bean.field-map.cmp-field.", new Integer(134));
      paths.put(".weblogic-rdbms-jar.weblogic-rdbms-bean.field-group.", new Integer(137));
      paths.put(".weblogic-rdbms-jar.weblogic-rdbms-bean.field-group.group-name.", new Integer(138));
      paths.put(".weblogic-rdbms-jar.weblogic-rdbms-bean.weblogic-query.weblogic-ql.", new Integer(147));
   }
}
