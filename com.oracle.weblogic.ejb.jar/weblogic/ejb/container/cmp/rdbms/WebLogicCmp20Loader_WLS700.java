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
import weblogic.j2ee.descriptor.wl.CachingElementBean;
import weblogic.j2ee.descriptor.wl.ColumnMapBean;
import weblogic.j2ee.descriptor.wl.EjbQlQueryBean;
import weblogic.j2ee.descriptor.wl.FieldGroupBean;
import weblogic.j2ee.descriptor.wl.FieldMapBean;
import weblogic.j2ee.descriptor.wl.MethodParamsBean;
import weblogic.j2ee.descriptor.wl.QueryMethodBean;
import weblogic.j2ee.descriptor.wl.RelationshipCachingBean;
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

public final class WebLogicCmp20Loader_WLS700 extends CMPDDParser implements XMLProcessor, InProcessor {
   private static final boolean debug = true;
   private static final boolean verbose = true;
   private static final Map paths = new HashMap();
   private ProcessorDriver driver;
   private static final String publicId = "-//BEA Systems, Inc.//DTD WebLogic 7.0.0 EJB RDBMS Persistence//EN";
   private static final String localDTDResourceName = "/weblogic/ejb/container/cmp/rdbms/weblogic-rdbms20-persistence-700.dtd";

   public ProcessorDriver getDriver() {
      return this.driver;
   }

   public WebLogicCmp20Loader_WLS700() {
      this(true);
   }

   public WebLogicCmp20Loader_WLS700(boolean var1) {
      this.driver = new ProcessorDriver(this, "-//BEA Systems, Inc.//DTD WebLogic 7.0.0 EJB RDBMS Persistence//EN", "/weblogic/ejb/container/cmp/rdbms/weblogic-rdbms20-persistence-700.dtd", var1);
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
               this.__post_132(var1);
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
               this.__post_147(var1);
               break;
            case 148:
               this.__pre_148(var1);
               break;
            case 149:
               this.__pre_149(var1);
               break;
            case 150:
               this.__pre_150(var1);
               this.__post_150(var1);
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
               this.__post_156(var1);
               break;
            case 157:
               this.__pre_157(var1);
               break;
            case 158:
               this.__pre_158(var1);
               break;
            case 159:
               this.__pre_159(var1);
               this.__post_159(var1);
               break;
            case 160:
               this.__pre_160(var1);
               break;
            case 161:
               this.__pre_161(var1);
               break;
            case 162:
               this.__pre_162(var1);
               this.__post_162(var1);
               break;
            case 163:
               this.__pre_163(var1);
               break;
            case 164:
               this.__pre_164(var1);
               this.__post_164(var1);
               break;
            case 165:
               this.__pre_165(var1);
               break;
            case 166:
               this.__pre_166(var1);
               this.__post_166(var1);
               break;
            case 167:
               this.__pre_167(var1);
               break;
            case 168:
               this.__pre_168(var1);
               break;
            case 169:
               this.__pre_169(var1);
               break;
            case 170:
               this.__pre_170(var1);
               break;
            case 171:
               this.__pre_171(var1);
               break;
            case 172:
               this.__pre_172(var1);
               break;
            case 173:
               this.__pre_173(var1);
               break;
            case 174:
               this.__pre_174(var1);
               break;
            case 175:
               this.__pre_175(var1);
               break;
            case 176:
               this.__pre_176(var1);
               break;
            case 177:
               this.__pre_177(var1);
               break;
            case 178:
               this.__pre_178(var1);
               this.__post_178(var1);
               break;
            case 179:
               this.__pre_179(var1);
               break;
            case 180:
               this.__pre_180(var1);
               break;
            case 181:
               this.__pre_181(var1);
               break;
            case 182:
               this.__pre_182(var1);
               this.__post_182(var1);
               break;
            case 183:
               this.__pre_183(var1);
               break;
            case 184:
               this.__pre_184(var1);
               break;
            case 185:
               this.__pre_185(var1);
               this.__post_185(var1);
               break;
            case 186:
               this.__pre_186(var1);
               break;
            case 187:
               this.__pre_187(var1);
               break;
            case 188:
               this.__pre_188(var1);
               break;
            case 189:
               this.__pre_189(var1);
               this.__post_189(var1);
               break;
            case 190:
               this.__pre_190(var1);
               break;
            case 191:
               this.__pre_191(var1);
               break;
            case 192:
               this.__pre_192(var1);
               this.__post_192(var1);
               break;
            case 193:
               this.__pre_193(var1);
               break;
            case 194:
               this.__pre_194(var1);
               break;
            case 195:
               this.__pre_195(var1);
               break;
            case 196:
               this.__pre_196(var1);
               break;
            case 197:
               this.__pre_197(var1);
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
            case 132:
            case 134:
            case 141:
            case 145:
            case 147:
            case 150:
            case 153:
            case 156:
            case 159:
            case 162:
            case 164:
            case 166:
            case 178:
            case 182:
            case 185:
            case 189:
            case 192:
               break;
            case 130:
               this.__post_130(var1);
               break;
            case 131:
               this.__post_131(var1);
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
            case 137:
               this.__post_137(var1);
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
            case 143:
               this.__post_143(var1);
               break;
            case 144:
               this.__post_144(var1);
               break;
            case 146:
               this.__post_146(var1);
               break;
            case 148:
               this.__post_148(var1);
               break;
            case 149:
               this.__post_149(var1);
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
            case 157:
               this.__post_157(var1);
               break;
            case 158:
               this.__post_158(var1);
               break;
            case 160:
               this.__post_160(var1);
               break;
            case 161:
               this.__post_161(var1);
               break;
            case 163:
               this.__post_163(var1);
               break;
            case 165:
               this.__post_165(var1);
               break;
            case 167:
               this.__post_167(var1);
               break;
            case 168:
               this.__post_168(var1);
               break;
            case 169:
               this.__post_169(var1);
               break;
            case 170:
               this.__post_170(var1);
               break;
            case 171:
               this.__post_171(var1);
               break;
            case 172:
               this.__post_172(var1);
               break;
            case 173:
               this.__post_173(var1);
               break;
            case 174:
               this.__post_174(var1);
               break;
            case 175:
               this.__post_175(var1);
               break;
            case 176:
               this.__post_176(var1);
               break;
            case 177:
               this.__post_177(var1);
               break;
            case 179:
               this.__post_179(var1);
               break;
            case 180:
               this.__post_180(var1);
               break;
            case 181:
               this.__post_181(var1);
               break;
            case 183:
               this.__post_183(var1);
               break;
            case 184:
               this.__post_184(var1);
               break;
            case 186:
               this.__post_186(var1);
               break;
            case 187:
               this.__post_187(var1);
               break;
            case 188:
               this.__post_188(var1);
               break;
            case 190:
               this.__post_190(var1);
               break;
            case 191:
               this.__post_191(var1);
               break;
            case 193:
               this.__post_193(var1);
               break;
            case 194:
               this.__post_194(var1);
               break;
            case 195:
               this.__post_195(var1);
               break;
            case 196:
               this.__post_196(var1);
               break;
            case 197:
               this.__post_197(var1);
               break;
            default:
               throw new AssertionError(var3.toString());
         }

      }
   }

   private void __pre_170(ProcessingContext var1) {
   }

   private void __post_170(ProcessingContext var1) throws SAXProcessorException, SAXValidationException {
      String var2 = Functions.value(var1);
      WeblogicQueryBean var3 = (WeblogicQueryBean)var1.getBoundObject("query");
      DefaultHelper var4 = (DefaultHelper)var1.getBoundObject("defaultHelper");
      WeblogicRdbmsBeanBean var5 = (WeblogicRdbmsBeanBean)var1.getBoundObject("cmpBean");
      WeblogicRdbmsJarBean var6 = (WeblogicRdbmsJarBean)var1.getBoundObject("cmpJar");
      if (var2.length() == 0) {
         throw new SAXValidationException("PAction[405662939](.weblogic-rdbms-jar.weblogic-rdbms-bean.weblogic-query.caching-name.) must be a non-empty string");
      } else {
         EjbQlQueryBean var7 = var3.getEjbQlQuery();
         var7.setCachingName(var2);
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

   private void __pre_167(ProcessingContext var1) {
   }

   private void __post_167(ProcessingContext var1) throws SAXProcessorException {
      String var2 = Functions.value(var1);
      MethodParamsBean var3 = (MethodParamsBean)var1.getBoundObject("mp");
      QueryMethodBean var4 = (QueryMethodBean)var1.getBoundObject("qm");
      WeblogicQueryBean var5 = (WeblogicQueryBean)var1.getBoundObject("query");
      DefaultHelper var6 = (DefaultHelper)var1.getBoundObject("defaultHelper");
      WeblogicRdbmsBeanBean var7 = (WeblogicRdbmsBeanBean)var1.getBoundObject("cmpBean");
      WeblogicRdbmsJarBean var8 = (WeblogicRdbmsJarBean)var1.getBoundObject("cmpJar");
      var3.addMethodParam(var2);
   }

   private void __pre_178(ProcessingContext var1) {
      Object var2 = null;
      var1.addBoundObject(var2, "keyGen");
   }

   private void __post_178(ProcessingContext var1) throws SAXProcessorException {
      AutomaticKeyGenerationBean var2 = (AutomaticKeyGenerationBean)var1.getBoundObject("keyGen");
      DefaultHelper var3 = (DefaultHelper)var1.getBoundObject("defaultHelper");
      WeblogicRdbmsBeanBean var4 = (WeblogicRdbmsBeanBean)var1.getBoundObject("cmpBean");
      WeblogicRdbmsJarBean var5 = (WeblogicRdbmsJarBean)var1.getBoundObject("cmpJar");
      this.addBoundObject(var4.createAutomaticKeyGeneration(), "keyGen");
   }

   private void __pre_189(ProcessingContext var1) {
      Object var2 = null;
      var1.addBoundObject(var2, "relationshipRoleMap");
   }

   private void __post_189(ProcessingContext var1) throws SAXProcessorException {
      RelationshipRoleMapBean var2 = (RelationshipRoleMapBean)var1.getBoundObject("relationshipRoleMap");
      WeblogicRelationshipRoleBean var3 = (WeblogicRelationshipRoleBean)var1.getBoundObject("relRole");
      WeblogicRdbmsRelationBean var4 = (WeblogicRdbmsRelationBean)var1.getBoundObject("relation");
      WeblogicRdbmsJarBean var5 = (WeblogicRdbmsJarBean)var1.getBoundObject("cmpJar");
      this.addBoundObject(var3.createRelationshipRoleMap(), "relationshipRoleMap");
   }

   private void __pre_133(ProcessingContext var1) {
   }

   private void __post_133(ProcessingContext var1) throws SAXProcessorException {
      String var2 = Functions.value(var1);
      TableMapBean var3 = (TableMapBean)var1.getBoundObject("tableMap");
      DefaultHelper var4 = (DefaultHelper)var1.getBoundObject("defaultHelper");
      WeblogicRdbmsBeanBean var5 = (WeblogicRdbmsBeanBean)var1.getBoundObject("cmpBean");
      WeblogicRdbmsJarBean var6 = (WeblogicRdbmsJarBean)var1.getBoundObject("cmpJar");
      var3.setTableName(var2);
   }

   private void __pre_176(ProcessingContext var1) {
   }

   private void __post_176(ProcessingContext var1) throws SAXProcessorException, SAXValidationException {
      String var2 = Functions.value(var1);
      DefaultHelper var3 = (DefaultHelper)var1.getBoundObject("defaultHelper");
      WeblogicRdbmsBeanBean var4 = (WeblogicRdbmsBeanBean)var1.getBoundObject("cmpBean");
      WeblogicRdbmsJarBean var5 = (WeblogicRdbmsJarBean)var1.getBoundObject("cmpJar");
      if (var2.length() == 0) {
         throw new SAXValidationException("PAction[1404928347](.weblogic-rdbms-jar.weblogic-rdbms-bean.lock-order.) must be a non-empty string");
      } else {
         try {
            this.validatePositiveInteger(var2);
         } catch (Exception var7) {
            throw new SAXValidationException("Path " + var1.getPath() + ": " + var7.getMessage());
         }

         int var6 = Integer.parseInt(var2);
         var4.setLockOrder(var6);
      }
   }

   private void __pre_186(ProcessingContext var1) {
   }

   private void __post_186(ProcessingContext var1) throws SAXProcessorException, SAXValidationException {
      String var2 = Functions.value(var1);
      WeblogicRelationshipRoleBean var3 = (WeblogicRelationshipRoleBean)var1.getBoundObject("relRole");
      WeblogicRdbmsRelationBean var4 = (WeblogicRdbmsRelationBean)var1.getBoundObject("relation");
      WeblogicRdbmsJarBean var5 = (WeblogicRdbmsJarBean)var1.getBoundObject("cmpJar");
      if (var2.length() == 0) {
         throw new SAXValidationException("PAction[604107971](.weblogic-rdbms-jar.weblogic-rdbms-relation.weblogic-relationship-role.relationship-role-name.) must be a non-empty string");
      } else {
         var3.setRelationshipRoleName(var2);
      }
   }

   private void __pre_146(ProcessingContext var1) {
   }

   private void __post_146(ProcessingContext var1) throws SAXProcessorException, SAXValidationException {
      String var2 = Functions.value(var1);
      RelationshipCachingBean var3 = (RelationshipCachingBean)var1.getBoundObject("rc");
      DefaultHelper var4 = (DefaultHelper)var1.getBoundObject("defaultHelper");
      WeblogicRdbmsBeanBean var5 = (WeblogicRdbmsBeanBean)var1.getBoundObject("cmpBean");
      WeblogicRdbmsJarBean var6 = (WeblogicRdbmsJarBean)var1.getBoundObject("cmpJar");
      if (var2.length() == 0) {
         throw new SAXValidationException("PAction[123961122](.weblogic-rdbms-jar.weblogic-rdbms-bean.relationship-caching.caching-name.) must be a non-empty string");
      } else {
         var3.setCachingName(var2);
      }
   }

   private void __pre_175(ProcessingContext var1) {
   }

   private void __post_175(ProcessingContext var1) throws SAXProcessorException, SAXValidationException {
      String var2 = Functions.value(var1);
      DefaultHelper var3 = (DefaultHelper)var1.getBoundObject("defaultHelper");
      WeblogicRdbmsBeanBean var4 = (WeblogicRdbmsBeanBean)var1.getBoundObject("cmpBean");
      WeblogicRdbmsJarBean var5 = (WeblogicRdbmsJarBean)var1.getBoundObject("cmpJar");
      if (var2.length() == 0) {
         throw new SAXValidationException("PAction[1227229563](.weblogic-rdbms-jar.weblogic-rdbms-bean.use-select-for-update.) must be a non-empty string");
      } else if (!"True".equals(var2) && !"False".equals(var2) && !"true".equals(var2) && !"false".equals(var2)) {
         throw new SAXValidationException("PAction[1227229563](.weblogic-rdbms-jar.weblogic-rdbms-bean.use-select-for-update.) must be one of the values: True,False,true,false");
      } else {
         var4.setUseSelectForUpdate("true".equalsIgnoreCase(var2));
      }
   }

   private void __pre_196(ProcessingContext var1) {
   }

   private void __post_196(ProcessingContext var1) throws SAXProcessorException, SAXValidationException {
      String var2 = Functions.value(var1);
      WeblogicRdbmsJarBean var3 = (WeblogicRdbmsJarBean)var1.getBoundObject("cmpJar");
      if (var2.length() == 0) {
         throw new SAXValidationException("PAction[1982791261](.weblogic-rdbms-jar.validate-db-schema-with.) must be a non-empty string");
      } else {
         var3.setValidateDbSchemaWith(var2);
      }
   }

   private void __pre_156(ProcessingContext var1) {
      Object var2 = null;
      var1.addBoundObject(var2, "ce3");
   }

   private void __post_156(ProcessingContext var1) throws SAXProcessorException {
      CachingElementBean var2 = (CachingElementBean)var1.getBoundObject("ce3");
      CachingElementBean var3 = (CachingElementBean)var1.getBoundObject("ce2");
      CachingElementBean var4 = (CachingElementBean)var1.getBoundObject("ce1");
      CachingElementBean var5 = (CachingElementBean)var1.getBoundObject("ce");
      RelationshipCachingBean var6 = (RelationshipCachingBean)var1.getBoundObject("rc");
      DefaultHelper var7 = (DefaultHelper)var1.getBoundObject("defaultHelper");
      WeblogicRdbmsBeanBean var8 = (WeblogicRdbmsBeanBean)var1.getBoundObject("cmpBean");
      WeblogicRdbmsJarBean var9 = (WeblogicRdbmsJarBean)var1.getBoundObject("cmpJar");
      this.addBoundObject(var3.createCachingElement(), "ce3");
   }

   private void __pre_162(ProcessingContext var1) {
      Object var2 = null;
      var1.addBoundObject(var2, "query");
   }

   private void __post_162(ProcessingContext var1) throws SAXProcessorException {
      WeblogicQueryBean var2 = (WeblogicQueryBean)var1.getBoundObject("query");
      DefaultHelper var3 = (DefaultHelper)var1.getBoundObject("defaultHelper");
      WeblogicRdbmsBeanBean var4 = (WeblogicRdbmsBeanBean)var1.getBoundObject("cmpBean");
      WeblogicRdbmsJarBean var5 = (WeblogicRdbmsJarBean)var1.getBoundObject("cmpJar");
      var2 = var4.createWeblogicQuery();
      EjbQlQueryBean var6 = var2.createEjbQlQuery();
      this.addBoundObject(var2, "query");
   }

   private void __pre_169(ProcessingContext var1) {
   }

   private void __post_169(ProcessingContext var1) throws SAXProcessorException, SAXValidationException {
      String var2 = Functions.value(var1);
      WeblogicQueryBean var3 = (WeblogicQueryBean)var1.getBoundObject("query");
      DefaultHelper var4 = (DefaultHelper)var1.getBoundObject("defaultHelper");
      WeblogicRdbmsBeanBean var5 = (WeblogicRdbmsBeanBean)var1.getBoundObject("cmpBean");
      WeblogicRdbmsJarBean var6 = (WeblogicRdbmsJarBean)var1.getBoundObject("cmpJar");
      if (var2.length() == 0) {
         throw new SAXValidationException("PAction[1562557367](.weblogic-rdbms-jar.weblogic-rdbms-bean.weblogic-query.group-name.) must be a non-empty string");
      } else {
         EjbQlQueryBean var7 = var3.getEjbQlQuery();
         var7.setGroupName(var2);
      }
   }

   private void __pre_182(ProcessingContext var1) {
      Object var2 = null;
      var1.addBoundObject(var2, "relation");
   }

   private void __post_182(ProcessingContext var1) throws SAXProcessorException {
      WeblogicRdbmsRelationBean var2 = (WeblogicRdbmsRelationBean)var1.getBoundObject("relation");
      WeblogicRdbmsJarBean var3 = (WeblogicRdbmsJarBean)var1.getBoundObject("cmpJar");
      this.addBoundObject(var3.createWeblogicRdbmsRelation(), "relation");
   }

   private void __pre_171(ProcessingContext var1) {
   }

   private void __post_171(ProcessingContext var1) throws SAXProcessorException, SAXValidationException {
      String var2 = Functions.value(var1);
      WeblogicQueryBean var3 = (WeblogicQueryBean)var1.getBoundObject("query");
      DefaultHelper var4 = (DefaultHelper)var1.getBoundObject("defaultHelper");
      WeblogicRdbmsBeanBean var5 = (WeblogicRdbmsBeanBean)var1.getBoundObject("cmpBean");
      WeblogicRdbmsJarBean var6 = (WeblogicRdbmsJarBean)var1.getBoundObject("cmpJar");
      if (var2.length() == 0) {
         throw new SAXValidationException("PAction[1763847188](.weblogic-rdbms-jar.weblogic-rdbms-bean.weblogic-query.max-elements.) must be a non-empty string");
      } else {
         try {
            this.validatePositiveInteger(var2);
         } catch (Exception var8) {
            throw new SAXValidationException("Path " + var1.getPath() + ": " + var8.getMessage());
         }

         int var7 = Integer.parseInt(var2);
         var3.setMaxElements(var7);
      }
   }

   private void __pre_147(ProcessingContext var1) {
      Object var2 = null;
      var1.addBoundObject(var2, "ce");
   }

   private void __post_147(ProcessingContext var1) throws SAXProcessorException {
      CachingElementBean var2 = (CachingElementBean)var1.getBoundObject("ce");
      RelationshipCachingBean var3 = (RelationshipCachingBean)var1.getBoundObject("rc");
      DefaultHelper var4 = (DefaultHelper)var1.getBoundObject("defaultHelper");
      WeblogicRdbmsBeanBean var5 = (WeblogicRdbmsBeanBean)var1.getBoundObject("cmpBean");
      WeblogicRdbmsJarBean var6 = (WeblogicRdbmsJarBean)var1.getBoundObject("cmpJar");
      this.addBoundObject(var3.createCachingElement(), "ce");
   }

   private void __pre_180(ProcessingContext var1) {
   }

   private void __post_180(ProcessingContext var1) throws SAXProcessorException, SAXValidationException {
      String var2 = Functions.value(var1);
      AutomaticKeyGenerationBean var3 = (AutomaticKeyGenerationBean)var1.getBoundObject("keyGen");
      DefaultHelper var4 = (DefaultHelper)var1.getBoundObject("defaultHelper");
      WeblogicRdbmsBeanBean var5 = (WeblogicRdbmsBeanBean)var1.getBoundObject("cmpBean");
      WeblogicRdbmsJarBean var6 = (WeblogicRdbmsJarBean)var1.getBoundObject("cmpJar");
      if (var2.length() == 0) {
         throw new SAXValidationException("PAction[125993742](.weblogic-rdbms-jar.weblogic-rdbms-bean.automatic-key-generation.generator-name.) must be a non-empty string");
      } else {
         var3.setGeneratorName(var2);
      }
   }

   private void __pre_148(ProcessingContext var1) {
   }

   private void __post_148(ProcessingContext var1) throws SAXProcessorException, SAXValidationException {
      String var2 = Functions.value(var1);
      CachingElementBean var3 = (CachingElementBean)var1.getBoundObject("ce");
      RelationshipCachingBean var4 = (RelationshipCachingBean)var1.getBoundObject("rc");
      DefaultHelper var5 = (DefaultHelper)var1.getBoundObject("defaultHelper");
      WeblogicRdbmsBeanBean var6 = (WeblogicRdbmsBeanBean)var1.getBoundObject("cmpBean");
      WeblogicRdbmsJarBean var7 = (WeblogicRdbmsJarBean)var1.getBoundObject("cmpJar");
      if (var2.length() == 0) {
         throw new SAXValidationException("PAction[1192108080](.weblogic-rdbms-jar.weblogic-rdbms-bean.relationship-caching.caching-element.cmr-field.) must be a non-empty string");
      } else {
         var3.setCmrField(var2);
      }
   }

   private void __pre_181(ProcessingContext var1) {
   }

   private void __post_181(ProcessingContext var1) throws SAXProcessorException, SAXValidationException {
      String var2 = Functions.value(var1);
      AutomaticKeyGenerationBean var3 = (AutomaticKeyGenerationBean)var1.getBoundObject("keyGen");
      DefaultHelper var4 = (DefaultHelper)var1.getBoundObject("defaultHelper");
      WeblogicRdbmsBeanBean var5 = (WeblogicRdbmsBeanBean)var1.getBoundObject("cmpBean");
      WeblogicRdbmsJarBean var6 = (WeblogicRdbmsJarBean)var1.getBoundObject("cmpJar");
      if (var2.length() == 0) {
         throw new SAXValidationException("PAction[1068824137](.weblogic-rdbms-jar.weblogic-rdbms-bean.automatic-key-generation.key-cache-size.) must be a non-empty string");
      } else {
         int var7 = Integer.parseInt(var2);
         var3.setKeyCacheSize(var7);
      }
   }

   private void __pre_140(ProcessingContext var1) {
   }

   private void __post_140(ProcessingContext var1) throws SAXProcessorException, SAXValidationException {
      String var2 = Functions.value(var1);
      TableMapBean var3 = (TableMapBean)var1.getBoundObject("tableMap");
      DefaultHelper var4 = (DefaultHelper)var1.getBoundObject("defaultHelper");
      WeblogicRdbmsBeanBean var5 = (WeblogicRdbmsBeanBean)var1.getBoundObject("cmpBean");
      WeblogicRdbmsJarBean var6 = (WeblogicRdbmsJarBean)var1.getBoundObject("cmpJar");
      if (var2.length() == 0) {
         throw new SAXValidationException("PAction[864237698](.weblogic-rdbms-jar.weblogic-rdbms-bean.table-map.optimistic-column.) must be a non-empty string");
      } else {
         var3.setOptimisticColumn(var2);
      }
   }

   private void __pre_173(ProcessingContext var1) {
   }

   private void __post_173(ProcessingContext var1) throws SAXProcessorException, SAXValidationException {
      String var2 = Functions.value(var1);
      WeblogicQueryBean var3 = (WeblogicQueryBean)var1.getBoundObject("query");
      DefaultHelper var4 = (DefaultHelper)var1.getBoundObject("defaultHelper");
      WeblogicRdbmsBeanBean var5 = (WeblogicRdbmsBeanBean)var1.getBoundObject("cmpBean");
      WeblogicRdbmsJarBean var6 = (WeblogicRdbmsJarBean)var1.getBoundObject("cmpJar");
      if (var2.length() == 0) {
         throw new SAXValidationException("PAction[537548559](.weblogic-rdbms-jar.weblogic-rdbms-bean.weblogic-query.sql-select-distinct.) must be a non-empty string");
      } else if (!"True".equals(var2) && !"False".equals(var2) && !"true".equals(var2) && !"false".equals(var2)) {
         throw new SAXValidationException("PAction[537548559](.weblogic-rdbms-jar.weblogic-rdbms-bean.weblogic-query.sql-select-distinct.) must be one of the values: True,False,true,false");
      } else {
         var3.setSqlSelectDistinct("true".equalsIgnoreCase(var2));
      }
   }

   private void __pre_149(ProcessingContext var1) {
   }

   private void __post_149(ProcessingContext var1) throws SAXProcessorException {
      String var2 = Functions.value(var1);
      CachingElementBean var3 = (CachingElementBean)var1.getBoundObject("ce");
      RelationshipCachingBean var4 = (RelationshipCachingBean)var1.getBoundObject("rc");
      DefaultHelper var5 = (DefaultHelper)var1.getBoundObject("defaultHelper");
      WeblogicRdbmsBeanBean var6 = (WeblogicRdbmsBeanBean)var1.getBoundObject("cmpBean");
      WeblogicRdbmsJarBean var7 = (WeblogicRdbmsJarBean)var1.getBoundObject("cmpJar");
      var3.setGroupName(var2);
   }

   private void __pre_164(ProcessingContext var1) {
      Object var2 = null;
      var1.addBoundObject(var2, "qm");
   }

   private void __post_164(ProcessingContext var1) throws SAXProcessorException {
      QueryMethodBean var2 = (QueryMethodBean)var1.getBoundObject("qm");
      WeblogicQueryBean var3 = (WeblogicQueryBean)var1.getBoundObject("query");
      DefaultHelper var4 = (DefaultHelper)var1.getBoundObject("defaultHelper");
      WeblogicRdbmsBeanBean var5 = (WeblogicRdbmsBeanBean)var1.getBoundObject("cmpBean");
      WeblogicRdbmsJarBean var6 = (WeblogicRdbmsJarBean)var1.getBoundObject("cmpJar");
      this.addBoundObject(var3.createQueryMethod(), "qm");
   }

   private void __pre_165(ProcessingContext var1) {
   }

   private void __post_165(ProcessingContext var1) throws SAXProcessorException, SAXValidationException {
      String var2 = Functions.value(var1);
      QueryMethodBean var3 = (QueryMethodBean)var1.getBoundObject("qm");
      WeblogicQueryBean var4 = (WeblogicQueryBean)var1.getBoundObject("query");
      DefaultHelper var5 = (DefaultHelper)var1.getBoundObject("defaultHelper");
      WeblogicRdbmsBeanBean var6 = (WeblogicRdbmsBeanBean)var1.getBoundObject("cmpBean");
      WeblogicRdbmsJarBean var7 = (WeblogicRdbmsJarBean)var1.getBoundObject("cmpJar");
      if (var2.length() == 0) {
         throw new SAXValidationException("PAction[380894366](.weblogic-rdbms-jar.weblogic-rdbms-bean.weblogic-query.query-method.method-name.) must be a non-empty string");
      } else {
         var3.setMethodName(var2);
      }
   }

   private void __pre_152(ProcessingContext var1) {
   }

   private void __post_152(ProcessingContext var1) throws SAXProcessorException {
      String var2 = Functions.value(var1);
      CachingElementBean var3 = (CachingElementBean)var1.getBoundObject("ce1");
      CachingElementBean var4 = (CachingElementBean)var1.getBoundObject("ce");
      RelationshipCachingBean var5 = (RelationshipCachingBean)var1.getBoundObject("rc");
      DefaultHelper var6 = (DefaultHelper)var1.getBoundObject("defaultHelper");
      WeblogicRdbmsBeanBean var7 = (WeblogicRdbmsBeanBean)var1.getBoundObject("cmpBean");
      WeblogicRdbmsJarBean var8 = (WeblogicRdbmsJarBean)var1.getBoundObject("cmpJar");
      var3.setGroupName(var2);
   }

   private void __pre_134(ProcessingContext var1) {
      Object var2 = null;
      var1.addBoundObject(var2, "fieldMap");
   }

   private void __post_134(ProcessingContext var1) throws SAXProcessorException {
      FieldMapBean var2 = (FieldMapBean)var1.getBoundObject("fieldMap");
      TableMapBean var3 = (TableMapBean)var1.getBoundObject("tableMap");
      DefaultHelper var4 = (DefaultHelper)var1.getBoundObject("defaultHelper");
      WeblogicRdbmsBeanBean var5 = (WeblogicRdbmsBeanBean)var1.getBoundObject("cmpBean");
      WeblogicRdbmsJarBean var6 = (WeblogicRdbmsJarBean)var1.getBoundObject("cmpJar");
      this.addBoundObject(var3.createFieldMap(), "fieldMap");
   }

   private void __pre_135(ProcessingContext var1) {
   }

   private void __post_135(ProcessingContext var1) throws SAXProcessorException {
      String var2 = Functions.value(var1);
      FieldMapBean var3 = (FieldMapBean)var1.getBoundObject("fieldMap");
      TableMapBean var4 = (TableMapBean)var1.getBoundObject("tableMap");
      DefaultHelper var5 = (DefaultHelper)var1.getBoundObject("defaultHelper");
      WeblogicRdbmsBeanBean var6 = (WeblogicRdbmsBeanBean)var1.getBoundObject("cmpBean");
      WeblogicRdbmsJarBean var7 = (WeblogicRdbmsJarBean)var1.getBoundObject("cmpJar");
      var3.setCmpField(var2);
   }

   private void __pre_197(ProcessingContext var1) {
   }

   private void __post_197(ProcessingContext var1) throws SAXProcessorException, SAXValidationException {
      String var2 = Functions.value(var1);
      WeblogicRdbmsJarBean var3 = (WeblogicRdbmsJarBean)var1.getBoundObject("cmpJar");
      if (var2.length() == 0) {
         throw new SAXValidationException("PAction[237852351](.weblogic-rdbms-jar.database-type.) must be a non-empty string");
      } else {
         String var4;
         if ("DB2".equalsIgnoreCase(var2)) {
            var4 = "DB2";
         } else if ("ORACLE".equalsIgnoreCase(var2)) {
            var4 = "Oracle";
         } else if ("SYBASE".equalsIgnoreCase(var2)) {
            var4 = "Sybase";
         } else if ("INFORMIX".equalsIgnoreCase(var2)) {
            var4 = "Informix";
         } else if ("POINTBASE".equalsIgnoreCase(var2)) {
            var4 = "Pointbase";
         } else if ("MYSQL".equalsIgnoreCase(var2)) {
            var4 = "MySQL";
         } else if ("SQL_SERVER".equalsIgnoreCase(var2)) {
            var4 = "SQLServer";
         } else if ("SQLSERVER".equalsIgnoreCase(var2)) {
            var4 = "SQLServer";
         } else if ("SQLSERVER2000".equalsIgnoreCase(var2)) {
            var4 = "SQLServer2000";
         } else if ("DERBY".equalsIgnoreCase(var2)) {
            var4 = "Derby";
         } else {
            if (!"TIMESTEN".equalsIgnoreCase(var2)) {
               throw new SAXValidationException(EJBComplianceTextFormatter.getInstance().ILLEGAL_DATABASETYPE_VALUE(var2));
            }

            var4 = "TimesTen";
         }

         var3.setDatabaseType(var4);
      }
   }

   private void __pre_190(ProcessingContext var1) {
   }

   private void __post_190(ProcessingContext var1) throws SAXProcessorException, SAXValidationException {
      String var2 = Functions.value(var1);
      RelationshipRoleMapBean var3 = (RelationshipRoleMapBean)var1.getBoundObject("relationshipRoleMap");
      WeblogicRelationshipRoleBean var4 = (WeblogicRelationshipRoleBean)var1.getBoundObject("relRole");
      WeblogicRdbmsRelationBean var5 = (WeblogicRdbmsRelationBean)var1.getBoundObject("relation");
      WeblogicRdbmsJarBean var6 = (WeblogicRdbmsJarBean)var1.getBoundObject("cmpJar");
      if (var2.length() == 0) {
         throw new SAXValidationException("PAction[608188624](.weblogic-rdbms-jar.weblogic-rdbms-relation.weblogic-relationship-role.relationship-role-map.foreign-key-table.) must be a non-empty string");
      } else {
         var3.setForeignKeyTable(var2);
      }
   }

   private void __pre_150(ProcessingContext var1) {
      Object var2 = null;
      var1.addBoundObject(var2, "ce1");
   }

   private void __post_150(ProcessingContext var1) throws SAXProcessorException {
      CachingElementBean var2 = (CachingElementBean)var1.getBoundObject("ce1");
      CachingElementBean var3 = (CachingElementBean)var1.getBoundObject("ce");
      RelationshipCachingBean var4 = (RelationshipCachingBean)var1.getBoundObject("rc");
      DefaultHelper var5 = (DefaultHelper)var1.getBoundObject("defaultHelper");
      WeblogicRdbmsBeanBean var6 = (WeblogicRdbmsBeanBean)var1.getBoundObject("cmpBean");
      WeblogicRdbmsJarBean var7 = (WeblogicRdbmsJarBean)var1.getBoundObject("cmpJar");
      this.addBoundObject(var3.createCachingElement(), "ce1");
   }

   private void __pre_166(ProcessingContext var1) {
      Object var2 = null;
      var1.addBoundObject(var2, "mp");
   }

   private void __post_166(ProcessingContext var1) throws SAXProcessorException {
      MethodParamsBean var2 = (MethodParamsBean)var1.getBoundObject("mp");
      QueryMethodBean var3 = (QueryMethodBean)var1.getBoundObject("qm");
      WeblogicQueryBean var4 = (WeblogicQueryBean)var1.getBoundObject("query");
      DefaultHelper var5 = (DefaultHelper)var1.getBoundObject("defaultHelper");
      WeblogicRdbmsBeanBean var6 = (WeblogicRdbmsBeanBean)var1.getBoundObject("cmpBean");
      WeblogicRdbmsJarBean var7 = (WeblogicRdbmsJarBean)var1.getBoundObject("cmpJar");
      this.addBoundObject(var3.createMethodParams(), "mp");
   }

   private void __pre_172(ProcessingContext var1) {
   }

   private void __post_172(ProcessingContext var1) throws SAXProcessorException, SAXValidationException {
      String var2 = Functions.value(var1);
      WeblogicQueryBean var3 = (WeblogicQueryBean)var1.getBoundObject("query");
      DefaultHelper var4 = (DefaultHelper)var1.getBoundObject("defaultHelper");
      WeblogicRdbmsBeanBean var5 = (WeblogicRdbmsBeanBean)var1.getBoundObject("cmpBean");
      WeblogicRdbmsJarBean var6 = (WeblogicRdbmsJarBean)var1.getBoundObject("cmpJar");
      if (var2.length() == 0) {
         throw new SAXValidationException("PAction[1451270520](.weblogic-rdbms-jar.weblogic-rdbms-bean.weblogic-query.include-updates.) must be a non-empty string");
      } else if (!"True".equals(var2) && !"False".equals(var2) && !"true".equals(var2) && !"false".equals(var2)) {
         throw new SAXValidationException("PAction[1451270520](.weblogic-rdbms-jar.weblogic-rdbms-bean.weblogic-query.include-updates.) must be one of the values: True,False,true,false");
      } else {
         var4.addQueryWithIncludeUpdates(var3, var2);
         var3.setIncludeUpdates("true".equalsIgnoreCase(var2));
      }
   }

   private void __pre_177(ProcessingContext var1) {
   }

   private void __post_177(ProcessingContext var1) throws SAXProcessorException, SAXValidationException {
      String var2 = Functions.value(var1);
      DefaultHelper var3 = (DefaultHelper)var1.getBoundObject("defaultHelper");
      WeblogicRdbmsBeanBean var4 = (WeblogicRdbmsBeanBean)var1.getBoundObject("cmpBean");
      WeblogicRdbmsJarBean var5 = (WeblogicRdbmsJarBean)var1.getBoundObject("cmpJar");
      if (var2.length() == 0) {
         throw new SAXValidationException("PAction[1608446010](.weblogic-rdbms-jar.weblogic-rdbms-bean.check-exists-on-method.) must be a non-empty string");
      } else {
         var3.setIsSet_checkExistsOnMethod(true, var2);
         if ("true".equalsIgnoreCase(var2)) {
            var4.setCheckExistsOnMethod(true);
         } else {
            if (!"false".equalsIgnoreCase(var2)) {
               throw new SAXValidationException(EJBComplianceTextFormatter.getInstance().ILLEGAL_VALUE_FOR_CHECK_EXISTS(this.getFileName(), var4.getEjbName(), var2));
            }

            var4.setCheckExistsOnMethod(false);
         }

      }
   }

   private void __pre_188(ProcessingContext var1) {
   }

   private void __post_188(ProcessingContext var1) throws SAXProcessorException {
      WeblogicRelationshipRoleBean var2 = (WeblogicRelationshipRoleBean)var1.getBoundObject("relRole");
      WeblogicRdbmsRelationBean var3 = (WeblogicRdbmsRelationBean)var1.getBoundObject("relation");
      WeblogicRdbmsJarBean var4 = (WeblogicRdbmsJarBean)var1.getBoundObject("cmpJar");
      var2.createDbCascadeDelete();
   }

   private void __pre_132(ProcessingContext var1) {
      Object var2 = null;
      var1.addBoundObject(var2, "tableMap");
   }

   private void __post_132(ProcessingContext var1) throws SAXProcessorException {
      TableMapBean var2 = (TableMapBean)var1.getBoundObject("tableMap");
      DefaultHelper var3 = (DefaultHelper)var1.getBoundObject("defaultHelper");
      WeblogicRdbmsBeanBean var4 = (WeblogicRdbmsBeanBean)var1.getBoundObject("cmpBean");
      WeblogicRdbmsJarBean var5 = (WeblogicRdbmsJarBean)var1.getBoundObject("cmpJar");
      this.addBoundObject(var4.createTableMap(), "tableMap");
   }

   private void __pre_168(ProcessingContext var1) {
   }

   private void __post_168(ProcessingContext var1) throws SAXProcessorException, SAXValidationException {
      String var2 = Functions.value(var1);
      WeblogicQueryBean var3 = (WeblogicQueryBean)var1.getBoundObject("query");
      DefaultHelper var4 = (DefaultHelper)var1.getBoundObject("defaultHelper");
      WeblogicRdbmsBeanBean var5 = (WeblogicRdbmsBeanBean)var1.getBoundObject("cmpBean");
      WeblogicRdbmsJarBean var6 = (WeblogicRdbmsJarBean)var1.getBoundObject("cmpJar");
      if (var2.length() == 0) {
         throw new SAXValidationException("PAction[992136656](.weblogic-rdbms-jar.weblogic-rdbms-bean.weblogic-query.weblogic-ql.) must be a non-empty string");
      } else {
         EjbQlQueryBean var7 = var3.getEjbQlQuery();
         var7.setWeblogicQl(var2);
      }
   }

   private void __pre_192(ProcessingContext var1) {
      Object var2 = null;
      var1.addBoundObject(var2, "colMap");
   }

   private void __post_192(ProcessingContext var1) throws SAXProcessorException {
      ColumnMapBean var2 = (ColumnMapBean)var1.getBoundObject("colMap");
      RelationshipRoleMapBean var3 = (RelationshipRoleMapBean)var1.getBoundObject("relationshipRoleMap");
      WeblogicRelationshipRoleBean var4 = (WeblogicRelationshipRoleBean)var1.getBoundObject("relRole");
      WeblogicRdbmsRelationBean var5 = (WeblogicRdbmsRelationBean)var1.getBoundObject("relation");
      WeblogicRdbmsJarBean var6 = (WeblogicRdbmsJarBean)var1.getBoundObject("cmpJar");
      this.addBoundObject(var3.createColumnMap(), "colMap");
   }

   private void __pre_143(ProcessingContext var1) {
   }

   private void __post_143(ProcessingContext var1) throws SAXProcessorException, SAXValidationException {
      String var2 = Functions.value(var1);
      FieldGroupBean var3 = (FieldGroupBean)var1.getBoundObject("fg");
      DefaultHelper var4 = (DefaultHelper)var1.getBoundObject("defaultHelper");
      WeblogicRdbmsBeanBean var5 = (WeblogicRdbmsBeanBean)var1.getBoundObject("cmpBean");
      WeblogicRdbmsJarBean var6 = (WeblogicRdbmsJarBean)var1.getBoundObject("cmpJar");
      if (var2.length() == 0) {
         throw new SAXValidationException("PAction[511833308](.weblogic-rdbms-jar.weblogic-rdbms-bean.field-group.cmp-field.) must be a non-empty string");
      } else {
         var3.addCmpField(var2);
      }
   }

   private void __pre_163(ProcessingContext var1) {
   }

   private void __post_163(ProcessingContext var1) throws SAXProcessorException {
      String var2 = Functions.value(var1);
      WeblogicQueryBean var3 = (WeblogicQueryBean)var1.getBoundObject("query");
      DefaultHelper var4 = (DefaultHelper)var1.getBoundObject("defaultHelper");
      WeblogicRdbmsBeanBean var5 = (WeblogicRdbmsBeanBean)var1.getBoundObject("cmpBean");
      WeblogicRdbmsJarBean var6 = (WeblogicRdbmsJarBean)var1.getBoundObject("cmpJar");
      var3.setDescription(var2);
   }

   private void __pre_153(ProcessingContext var1) {
      Object var2 = null;
      var1.addBoundObject(var2, "ce2");
   }

   private void __post_153(ProcessingContext var1) throws SAXProcessorException {
      CachingElementBean var2 = (CachingElementBean)var1.getBoundObject("ce2");
      CachingElementBean var3 = (CachingElementBean)var1.getBoundObject("ce1");
      CachingElementBean var4 = (CachingElementBean)var1.getBoundObject("ce");
      RelationshipCachingBean var5 = (RelationshipCachingBean)var1.getBoundObject("rc");
      DefaultHelper var6 = (DefaultHelper)var1.getBoundObject("defaultHelper");
      WeblogicRdbmsBeanBean var7 = (WeblogicRdbmsBeanBean)var1.getBoundObject("cmpBean");
      WeblogicRdbmsJarBean var8 = (WeblogicRdbmsJarBean)var1.getBoundObject("cmpJar");
      this.addBoundObject(var3.createCachingElement(), "ce2");
   }

   private void __pre_131(ProcessingContext var1) {
   }

   private void __post_131(ProcessingContext var1) throws SAXProcessorException, SAXValidationException {
      String var2 = Functions.value(var1);
      DefaultHelper var3 = (DefaultHelper)var1.getBoundObject("defaultHelper");
      WeblogicRdbmsBeanBean var4 = (WeblogicRdbmsBeanBean)var1.getBoundObject("cmpBean");
      WeblogicRdbmsJarBean var5 = (WeblogicRdbmsJarBean)var1.getBoundObject("cmpJar");
      if (var2.length() == 0) {
         throw new SAXValidationException("PAction[1297685781](.weblogic-rdbms-jar.weblogic-rdbms-bean.data-source-name.) must be a non-empty string");
      } else {
         var4.setDataSourceJndiName(var2);
      }
   }

   private void __pre_136(ProcessingContext var1) {
   }

   private void __post_136(ProcessingContext var1) throws SAXProcessorException {
      String var2 = Functions.value(var1);
      FieldMapBean var3 = (FieldMapBean)var1.getBoundObject("fieldMap");
      TableMapBean var4 = (TableMapBean)var1.getBoundObject("tableMap");
      DefaultHelper var5 = (DefaultHelper)var1.getBoundObject("defaultHelper");
      WeblogicRdbmsBeanBean var6 = (WeblogicRdbmsBeanBean)var1.getBoundObject("cmpBean");
      WeblogicRdbmsJarBean var7 = (WeblogicRdbmsJarBean)var1.getBoundObject("cmpJar");
      var3.setDbmsColumn(var2);
   }

   private void __pre_179(ProcessingContext var1) {
   }

   private void __post_179(ProcessingContext var1) throws SAXProcessorException, SAXValidationException {
      String var2 = Functions.value(var1);
      AutomaticKeyGenerationBean var3 = (AutomaticKeyGenerationBean)var1.getBoundObject("keyGen");
      DefaultHelper var4 = (DefaultHelper)var1.getBoundObject("defaultHelper");
      WeblogicRdbmsBeanBean var5 = (WeblogicRdbmsBeanBean)var1.getBoundObject("cmpBean");
      WeblogicRdbmsJarBean var6 = (WeblogicRdbmsJarBean)var1.getBoundObject("cmpJar");
      if (var2.length() == 0) {
         throw new SAXValidationException("PAction[1705929636](.weblogic-rdbms-jar.weblogic-rdbms-bean.automatic-key-generation.generator-type.) must be a non-empty string");
      } else {
         String var7;
         if ("Oracle".equalsIgnoreCase(var2)) {
            var7 = "Sequence";
            var6.setDatabaseType("Oracle");
         } else if (!"SQLServer".equalsIgnoreCase(var2) && !"SQL_SERVER".equalsIgnoreCase(var2)) {
            if (!"SQLServer2000".equalsIgnoreCase(var2) && !"SQL_SERVER_2000".equalsIgnoreCase(var2)) {
               if (!"NAMED_SEQUENCE_TABLE".equalsIgnoreCase(var2) && !"NamedSequenceTable".equalsIgnoreCase(var2)) {
                  throw new SAXValidationException(EJBComplianceTextFormatter.getInstance().ILLEGAL_AUTOKEY_GENERATOR_VALUE(this.getFileName(), var5.getEjbName(), var2));
               }

               var7 = "SequenceTable";
            } else {
               var7 = "Identity";
               var6.setDatabaseType("SQLServer2000");
            }
         } else {
            var7 = "Identity";
            var6.setDatabaseType("SQLServer");
         }

         var3.setGeneratorType(var7);
      }
   }

   private void __pre_159(ProcessingContext var1) {
      Object var2 = null;
      var1.addBoundObject(var2, "ce4");
   }

   private void __post_159(ProcessingContext var1) throws SAXProcessorException {
      CachingElementBean var2 = (CachingElementBean)var1.getBoundObject("ce4");
      CachingElementBean var3 = (CachingElementBean)var1.getBoundObject("ce3");
      CachingElementBean var4 = (CachingElementBean)var1.getBoundObject("ce2");
      CachingElementBean var5 = (CachingElementBean)var1.getBoundObject("ce1");
      CachingElementBean var6 = (CachingElementBean)var1.getBoundObject("ce");
      RelationshipCachingBean var7 = (RelationshipCachingBean)var1.getBoundObject("rc");
      DefaultHelper var8 = (DefaultHelper)var1.getBoundObject("defaultHelper");
      WeblogicRdbmsBeanBean var9 = (WeblogicRdbmsBeanBean)var1.getBoundObject("cmpBean");
      WeblogicRdbmsJarBean var10 = (WeblogicRdbmsJarBean)var1.getBoundObject("cmpJar");
      this.addBoundObject(var3.createCachingElement(), "ce4");
   }

   private void __pre_158(ProcessingContext var1) {
   }

   private void __post_158(ProcessingContext var1) throws SAXProcessorException {
      String var2 = Functions.value(var1);
      CachingElementBean var3 = (CachingElementBean)var1.getBoundObject("ce3");
      CachingElementBean var4 = (CachingElementBean)var1.getBoundObject("ce2");
      CachingElementBean var5 = (CachingElementBean)var1.getBoundObject("ce1");
      CachingElementBean var6 = (CachingElementBean)var1.getBoundObject("ce");
      RelationshipCachingBean var7 = (RelationshipCachingBean)var1.getBoundObject("rc");
      DefaultHelper var8 = (DefaultHelper)var1.getBoundObject("defaultHelper");
      WeblogicRdbmsBeanBean var9 = (WeblogicRdbmsBeanBean)var1.getBoundObject("cmpBean");
      WeblogicRdbmsJarBean var10 = (WeblogicRdbmsJarBean)var1.getBoundObject("cmpJar");
      var3.setGroupName(var2);
   }

   private void __pre_183(ProcessingContext var1) {
   }

   private void __post_183(ProcessingContext var1) throws SAXProcessorException, SAXValidationException {
      String var2 = Functions.value(var1);
      WeblogicRdbmsRelationBean var3 = (WeblogicRdbmsRelationBean)var1.getBoundObject("relation");
      WeblogicRdbmsJarBean var4 = (WeblogicRdbmsJarBean)var1.getBoundObject("cmpJar");
      if (var2.length() == 0) {
         throw new SAXValidationException("PAction[1221555852](.weblogic-rdbms-jar.weblogic-rdbms-relation.relation-name.) must be a non-empty string");
      } else {
         var3.setRelationName(var2);
      }
   }

   private void __pre_155(ProcessingContext var1) {
   }

   private void __post_155(ProcessingContext var1) throws SAXProcessorException {
      String var2 = Functions.value(var1);
      CachingElementBean var3 = (CachingElementBean)var1.getBoundObject("ce2");
      CachingElementBean var4 = (CachingElementBean)var1.getBoundObject("ce1");
      CachingElementBean var5 = (CachingElementBean)var1.getBoundObject("ce");
      RelationshipCachingBean var6 = (RelationshipCachingBean)var1.getBoundObject("rc");
      DefaultHelper var7 = (DefaultHelper)var1.getBoundObject("defaultHelper");
      WeblogicRdbmsBeanBean var8 = (WeblogicRdbmsBeanBean)var1.getBoundObject("cmpBean");
      WeblogicRdbmsJarBean var9 = (WeblogicRdbmsJarBean)var1.getBoundObject("cmpJar");
      var3.setGroupName(var2);
   }

   private void __pre_138(ProcessingContext var1) {
   }

   private void __post_138(ProcessingContext var1) throws SAXProcessorException {
      String var2 = Functions.value(var1);
      TableMapBean var3 = (TableMapBean)var1.getBoundObject("tableMap");
      DefaultHelper var4 = (DefaultHelper)var1.getBoundObject("defaultHelper");
      WeblogicRdbmsBeanBean var5 = (WeblogicRdbmsBeanBean)var1.getBoundObject("cmpBean");
      WeblogicRdbmsJarBean var6 = (WeblogicRdbmsJarBean)var1.getBoundObject("cmpJar");
      var3.setVerifyRows(var2);
   }

   private void __pre_129(ProcessingContext var1) {
      DefaultHelper var2 = new DefaultHelper();
      var1.addBoundObject(var2, "defaultHelper");
      Object var3 = null;
      var1.addBoundObject(var3, "cmpBean");
   }

   private void __post_129(ProcessingContext var1) throws SAXProcessorException {
      DefaultHelper var2 = (DefaultHelper)var1.getBoundObject("defaultHelper");
      WeblogicRdbmsBeanBean var3 = (WeblogicRdbmsBeanBean)var1.getBoundObject("cmpBean");
      WeblogicRdbmsJarBean var4 = (WeblogicRdbmsJarBean)var1.getBoundObject("cmpJar");
      this.addBoundObject(var4.createWeblogicRdbmsBean(), "cmpBean");
      var2.setVersion(7.0F);
   }

   private void __pre_184(ProcessingContext var1) {
   }

   private void __post_184(ProcessingContext var1) throws SAXProcessorException, SAXValidationException {
      String var2 = Functions.value(var1);
      WeblogicRdbmsRelationBean var3 = (WeblogicRdbmsRelationBean)var1.getBoundObject("relation");
      WeblogicRdbmsJarBean var4 = (WeblogicRdbmsJarBean)var1.getBoundObject("cmpJar");
      if (var2.length() == 0) {
         throw new SAXValidationException("PAction[1509514333](.weblogic-rdbms-jar.weblogic-rdbms-relation.table-name.) must be a non-empty string");
      } else {
         var3.setTableName(var2);
      }
   }

   private void __pre_174(ProcessingContext var1) {
   }

   private void __post_174(ProcessingContext var1) throws SAXProcessorException, SAXValidationException {
      String var2 = Functions.value(var1);
      DefaultHelper var3 = (DefaultHelper)var1.getBoundObject("defaultHelper");
      WeblogicRdbmsBeanBean var4 = (WeblogicRdbmsBeanBean)var1.getBoundObject("cmpBean");
      WeblogicRdbmsJarBean var5 = (WeblogicRdbmsJarBean)var1.getBoundObject("cmpJar");
      if (var2.length() == 0) {
         throw new SAXValidationException("PAction[1556956098](.weblogic-rdbms-jar.weblogic-rdbms-bean.delay-database-insert-until.) must be a non-empty string");
      } else if (!"ejbCreate".equals(var2) && !"ejbPostCreate".equals(var2) && !"EjbCreate".equals(var2) && !"EjbPostCreate".equals(var2) && !"ejbcreate".equals(var2) && !"ejbpostcreate".equals(var2) && !"commit".equals(var2)) {
         throw new SAXValidationException("PAction[1556956098](.weblogic-rdbms-jar.weblogic-rdbms-bean.delay-database-insert-until.) must be one of the values: ejbCreate,ejbPostCreate,EjbCreate,EjbPostCreate,ejbcreate,ejbpostcreate,commit");
      } else {
         String var6 = null;
         if ("ejbcreate".equalsIgnoreCase(var2)) {
            var6 = "ejbCreate";
         } else if ("commit".equalsIgnoreCase(var2)) {
            var3.setIsSet_enableBatchOperations(true, var2);
            var6 = "commit";
         } else {
            var6 = "ejbPostCreate";
         }

         var4.setDelayDatabaseInsertUntil(var6);
      }
   }

   private void __pre_191(ProcessingContext var1) {
   }

   private void __post_191(ProcessingContext var1) throws SAXProcessorException, SAXValidationException {
      String var2 = Functions.value(var1);
      RelationshipRoleMapBean var3 = (RelationshipRoleMapBean)var1.getBoundObject("relationshipRoleMap");
      WeblogicRelationshipRoleBean var4 = (WeblogicRelationshipRoleBean)var1.getBoundObject("relRole");
      WeblogicRdbmsRelationBean var5 = (WeblogicRdbmsRelationBean)var1.getBoundObject("relation");
      WeblogicRdbmsJarBean var6 = (WeblogicRdbmsJarBean)var1.getBoundObject("cmpJar");
      if (var2.length() == 0) {
         throw new SAXValidationException("PAction[1252585652](.weblogic-rdbms-jar.weblogic-rdbms-relation.weblogic-relationship-role.relationship-role-map.primary-key-table.) must be a non-empty string");
      } else {
         var3.setPrimaryKeyTable(var2);
      }
   }

   private void __pre_160(ProcessingContext var1) {
   }

   private void __post_160(ProcessingContext var1) throws SAXProcessorException, SAXValidationException {
      String var2 = Functions.value(var1);
      CachingElementBean var3 = (CachingElementBean)var1.getBoundObject("ce4");
      CachingElementBean var4 = (CachingElementBean)var1.getBoundObject("ce3");
      CachingElementBean var5 = (CachingElementBean)var1.getBoundObject("ce2");
      CachingElementBean var6 = (CachingElementBean)var1.getBoundObject("ce1");
      CachingElementBean var7 = (CachingElementBean)var1.getBoundObject("ce");
      RelationshipCachingBean var8 = (RelationshipCachingBean)var1.getBoundObject("rc");
      DefaultHelper var9 = (DefaultHelper)var1.getBoundObject("defaultHelper");
      WeblogicRdbmsBeanBean var10 = (WeblogicRdbmsBeanBean)var1.getBoundObject("cmpBean");
      WeblogicRdbmsJarBean var11 = (WeblogicRdbmsJarBean)var1.getBoundObject("cmpJar");
      if (var2.length() == 0) {
         throw new SAXValidationException("PAction[2036368507](.weblogic-rdbms-jar.weblogic-rdbms-bean.relationship-caching.caching-element.caching-element.caching-element.caching-element.caching-element.cmr-field.) must be a non-empty string");
      } else {
         var3.setCmrField(var2);
      }
   }

   private void __pre_139(ProcessingContext var1) {
   }

   private void __post_139(ProcessingContext var1) throws SAXProcessorException {
      String var2 = Functions.value(var1);
      TableMapBean var3 = (TableMapBean)var1.getBoundObject("tableMap");
      DefaultHelper var4 = (DefaultHelper)var1.getBoundObject("defaultHelper");
      WeblogicRdbmsBeanBean var5 = (WeblogicRdbmsBeanBean)var1.getBoundObject("cmpBean");
      WeblogicRdbmsJarBean var6 = (WeblogicRdbmsJarBean)var1.getBoundObject("cmpJar");
      var3.setVerifyColumns(var2);
   }

   private void __pre_157(ProcessingContext var1) {
   }

   private void __post_157(ProcessingContext var1) throws SAXProcessorException, SAXValidationException {
      String var2 = Functions.value(var1);
      CachingElementBean var3 = (CachingElementBean)var1.getBoundObject("ce3");
      CachingElementBean var4 = (CachingElementBean)var1.getBoundObject("ce2");
      CachingElementBean var5 = (CachingElementBean)var1.getBoundObject("ce1");
      CachingElementBean var6 = (CachingElementBean)var1.getBoundObject("ce");
      RelationshipCachingBean var7 = (RelationshipCachingBean)var1.getBoundObject("rc");
      DefaultHelper var8 = (DefaultHelper)var1.getBoundObject("defaultHelper");
      WeblogicRdbmsBeanBean var9 = (WeblogicRdbmsBeanBean)var1.getBoundObject("cmpBean");
      WeblogicRdbmsJarBean var10 = (WeblogicRdbmsJarBean)var1.getBoundObject("cmpJar");
      if (var2.length() == 0) {
         throw new SAXValidationException("PAction[1785210046](.weblogic-rdbms-jar.weblogic-rdbms-bean.relationship-caching.caching-element.caching-element.caching-element.caching-element.cmr-field.) must be a non-empty string");
      } else {
         var3.setCmrField(var2);
      }
   }

   private void __pre_185(ProcessingContext var1) {
      Object var2 = null;
      var1.addBoundObject(var2, "relRole");
   }

   private void __post_185(ProcessingContext var1) throws SAXProcessorException {
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
      WeblogicRdbmsJarBean var5 = (WeblogicRdbmsJarBean)var1.getBoundObject("cmpJar");
      if (var2.length() == 0) {
         throw new SAXValidationException("PAction[1552787810](.weblogic-rdbms-jar.weblogic-rdbms-bean.ejb-name.) must be a non-empty string");
      } else {
         var4.setEjbName(var2.replace('/', '.'));
         this.addDefaultHelper(var4.getEjbName(), var3);
      }
   }

   private void __pre_193(ProcessingContext var1) {
   }

   private void __post_193(ProcessingContext var1) throws SAXProcessorException, SAXValidationException {
      String var2 = Functions.value(var1);
      ColumnMapBean var3 = (ColumnMapBean)var1.getBoundObject("colMap");
      RelationshipRoleMapBean var4 = (RelationshipRoleMapBean)var1.getBoundObject("relationshipRoleMap");
      WeblogicRelationshipRoleBean var5 = (WeblogicRelationshipRoleBean)var1.getBoundObject("relRole");
      WeblogicRdbmsRelationBean var6 = (WeblogicRdbmsRelationBean)var1.getBoundObject("relation");
      WeblogicRdbmsJarBean var7 = (WeblogicRdbmsJarBean)var1.getBoundObject("cmpJar");
      if (var2.length() == 0) {
         throw new SAXValidationException("PAction[1361960727](.weblogic-rdbms-jar.weblogic-rdbms-relation.weblogic-relationship-role.relationship-role-map.column-map.foreign-key-column.) must be a non-empty string");
      } else {
         var3.setForeignKeyColumn(var2);
      }
   }

   private void __pre_161(ProcessingContext var1) {
   }

   private void __post_161(ProcessingContext var1) throws SAXProcessorException {
      String var2 = Functions.value(var1);
      CachingElementBean var3 = (CachingElementBean)var1.getBoundObject("ce4");
      CachingElementBean var4 = (CachingElementBean)var1.getBoundObject("ce3");
      CachingElementBean var5 = (CachingElementBean)var1.getBoundObject("ce2");
      CachingElementBean var6 = (CachingElementBean)var1.getBoundObject("ce1");
      CachingElementBean var7 = (CachingElementBean)var1.getBoundObject("ce");
      RelationshipCachingBean var8 = (RelationshipCachingBean)var1.getBoundObject("rc");
      DefaultHelper var9 = (DefaultHelper)var1.getBoundObject("defaultHelper");
      WeblogicRdbmsBeanBean var10 = (WeblogicRdbmsBeanBean)var1.getBoundObject("cmpBean");
      WeblogicRdbmsJarBean var11 = (WeblogicRdbmsJarBean)var1.getBoundObject("cmpJar");
      var3.setGroupName(var2);
   }

   private void __pre_154(ProcessingContext var1) {
   }

   private void __post_154(ProcessingContext var1) throws SAXProcessorException, SAXValidationException {
      String var2 = Functions.value(var1);
      CachingElementBean var3 = (CachingElementBean)var1.getBoundObject("ce2");
      CachingElementBean var4 = (CachingElementBean)var1.getBoundObject("ce1");
      CachingElementBean var5 = (CachingElementBean)var1.getBoundObject("ce");
      RelationshipCachingBean var6 = (RelationshipCachingBean)var1.getBoundObject("rc");
      DefaultHelper var7 = (DefaultHelper)var1.getBoundObject("defaultHelper");
      WeblogicRdbmsBeanBean var8 = (WeblogicRdbmsBeanBean)var1.getBoundObject("cmpBean");
      WeblogicRdbmsJarBean var9 = (WeblogicRdbmsJarBean)var1.getBoundObject("cmpJar");
      if (var2.length() == 0) {
         throw new SAXValidationException("PAction[739498517](.weblogic-rdbms-jar.weblogic-rdbms-bean.relationship-caching.caching-element.caching-element.caching-element.cmr-field.) must be a non-empty string");
      } else {
         var3.setCmrField(var2);
      }
   }

   private void __pre_145(ProcessingContext var1) {
      Object var2 = null;
      var1.addBoundObject(var2, "rc");
   }

   private void __post_145(ProcessingContext var1) throws SAXProcessorException {
      RelationshipCachingBean var2 = (RelationshipCachingBean)var1.getBoundObject("rc");
      DefaultHelper var3 = (DefaultHelper)var1.getBoundObject("defaultHelper");
      WeblogicRdbmsBeanBean var4 = (WeblogicRdbmsBeanBean)var1.getBoundObject("cmpBean");
      WeblogicRdbmsJarBean var5 = (WeblogicRdbmsJarBean)var1.getBoundObject("cmpJar");
      this.addBoundObject(var4.createRelationshipCaching(), "rc");
   }

   private void __pre_187(ProcessingContext var1) {
   }

   private void __post_187(ProcessingContext var1) throws SAXProcessorException, SAXValidationException {
      String var2 = Functions.value(var1);
      WeblogicRelationshipRoleBean var3 = (WeblogicRelationshipRoleBean)var1.getBoundObject("relRole");
      WeblogicRdbmsRelationBean var4 = (WeblogicRdbmsRelationBean)var1.getBoundObject("relation");
      WeblogicRdbmsJarBean var5 = (WeblogicRdbmsJarBean)var1.getBoundObject("cmpJar");
      if (var2.length() == 0) {
         throw new SAXValidationException("PAction[125130493](.weblogic-rdbms-jar.weblogic-rdbms-relation.weblogic-relationship-role.group-name.) must be a non-empty string");
      } else {
         var3.setGroupName(var2);
      }
   }

   private void __pre_144(ProcessingContext var1) {
   }

   private void __post_144(ProcessingContext var1) throws SAXProcessorException, SAXValidationException {
      String var2 = Functions.value(var1);
      FieldGroupBean var3 = (FieldGroupBean)var1.getBoundObject("fg");
      DefaultHelper var4 = (DefaultHelper)var1.getBoundObject("defaultHelper");
      WeblogicRdbmsBeanBean var5 = (WeblogicRdbmsBeanBean)var1.getBoundObject("cmpBean");
      WeblogicRdbmsJarBean var6 = (WeblogicRdbmsJarBean)var1.getBoundObject("cmpJar");
      if (var2.length() == 0) {
         throw new SAXValidationException("PAction[914504136](.weblogic-rdbms-jar.weblogic-rdbms-bean.field-group.cmr-field.) must be a non-empty string");
      } else {
         var3.addCmrField(var2);
      }
   }

   private void __pre_194(ProcessingContext var1) {
   }

   private void __post_194(ProcessingContext var1) throws SAXProcessorException, SAXValidationException {
      String var2 = Functions.value(var1);
      ColumnMapBean var3 = (ColumnMapBean)var1.getBoundObject("colMap");
      RelationshipRoleMapBean var4 = (RelationshipRoleMapBean)var1.getBoundObject("relationshipRoleMap");
      WeblogicRelationshipRoleBean var5 = (WeblogicRelationshipRoleBean)var1.getBoundObject("relRole");
      WeblogicRdbmsRelationBean var6 = (WeblogicRdbmsRelationBean)var1.getBoundObject("relation");
      WeblogicRdbmsJarBean var7 = (WeblogicRdbmsJarBean)var1.getBoundObject("cmpJar");
      if (var2.length() == 0) {
         throw new SAXValidationException("PAction[166239592](.weblogic-rdbms-jar.weblogic-rdbms-relation.weblogic-relationship-role.relationship-role-map.column-map.key-column.) must be a non-empty string");
      } else {
         var3.setKeyColumn(var2);
      }
   }

   private void __pre_151(ProcessingContext var1) {
   }

   private void __post_151(ProcessingContext var1) throws SAXProcessorException, SAXValidationException {
      String var2 = Functions.value(var1);
      CachingElementBean var3 = (CachingElementBean)var1.getBoundObject("ce1");
      CachingElementBean var4 = (CachingElementBean)var1.getBoundObject("ce");
      RelationshipCachingBean var5 = (RelationshipCachingBean)var1.getBoundObject("rc");
      DefaultHelper var6 = (DefaultHelper)var1.getBoundObject("defaultHelper");
      WeblogicRdbmsBeanBean var7 = (WeblogicRdbmsBeanBean)var1.getBoundObject("cmpBean");
      WeblogicRdbmsJarBean var8 = (WeblogicRdbmsJarBean)var1.getBoundObject("cmpJar");
      if (var2.length() == 0) {
         throw new SAXValidationException("PAction[991505714](.weblogic-rdbms-jar.weblogic-rdbms-bean.relationship-caching.caching-element.caching-element.cmr-field.) must be a non-empty string");
      } else {
         var3.setCmrField(var2);
      }
   }

   private void __pre_195(ProcessingContext var1) {
   }

   private void __post_195(ProcessingContext var1) throws SAXProcessorException, SAXValidationException {
      String var2 = Functions.value(var1);
      WeblogicRdbmsJarBean var3 = (WeblogicRdbmsJarBean)var1.getBoundObject("cmpJar");
      if (var2.length() == 0) {
         throw new SAXValidationException("PAction[385242642](.weblogic-rdbms-jar.create-default-dbms-tables.) must be a non-empty string");
      } else {
         if (!"True".equalsIgnoreCase(var2) && !"CreateOnly".equalsIgnoreCase(var2)) {
            var3.setCreateDefaultDbmsTables("Disabled");
         } else {
            var3.setCreateDefaultDbmsTables("CreateOnly");
         }

      }
   }

   private void __pre_137(ProcessingContext var1) {
   }

   private void __post_137(ProcessingContext var1) throws SAXProcessorException, SAXValidationException {
      String var2 = Functions.value(var1);
      FieldMapBean var3 = (FieldMapBean)var1.getBoundObject("fieldMap");
      TableMapBean var4 = (TableMapBean)var1.getBoundObject("tableMap");
      DefaultHelper var5 = (DefaultHelper)var1.getBoundObject("defaultHelper");
      WeblogicRdbmsBeanBean var6 = (WeblogicRdbmsBeanBean)var1.getBoundObject("cmpBean");
      WeblogicRdbmsJarBean var7 = (WeblogicRdbmsJarBean)var1.getBoundObject("cmpJar");
      if (var2.length() == 0) {
         throw new SAXValidationException("PAction[824009085](.weblogic-rdbms-jar.weblogic-rdbms-bean.table-map.field-map.dbms-column-type.) must be a non-empty string");
      } else if (!"OracleClob".equals(var2) && !"OracleBlob".equals(var2) && !"LongString".equals(var2) && !"SybaseBinary".equals(var2)) {
         throw new SAXValidationException("PAction[824009085](.weblogic-rdbms-jar.weblogic-rdbms-bean.table-map.field-map.dbms-column-type.) must be one of the values: OracleClob,OracleBlob,LongString,SybaseBinary");
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

   private void __pre_141(ProcessingContext var1) {
      Object var2 = null;
      var1.addBoundObject(var2, "fg");
   }

   private void __post_141(ProcessingContext var1) throws SAXProcessorException {
      FieldGroupBean var2 = (FieldGroupBean)var1.getBoundObject("fg");
      DefaultHelper var3 = (DefaultHelper)var1.getBoundObject("defaultHelper");
      WeblogicRdbmsBeanBean var4 = (WeblogicRdbmsBeanBean)var1.getBoundObject("cmpBean");
      WeblogicRdbmsJarBean var5 = (WeblogicRdbmsJarBean)var1.getBoundObject("cmpJar");
      this.addBoundObject(var4.createFieldGroup(), "fg");
   }

   private void __pre_142(ProcessingContext var1) {
   }

   private void __post_142(ProcessingContext var1) throws SAXProcessorException, SAXValidationException {
      String var2 = Functions.value(var1);
      FieldGroupBean var3 = (FieldGroupBean)var1.getBoundObject("fg");
      DefaultHelper var4 = (DefaultHelper)var1.getBoundObject("defaultHelper");
      WeblogicRdbmsBeanBean var5 = (WeblogicRdbmsBeanBean)var1.getBoundObject("cmpBean");
      WeblogicRdbmsJarBean var6 = (WeblogicRdbmsJarBean)var1.getBoundObject("cmpJar");
      if (var2.length() == 0) {
         throw new SAXValidationException("PAction[2085857771](.weblogic-rdbms-jar.weblogic-rdbms-bean.field-group.group-name.) must be a non-empty string");
      } else {
         var3.setGroupName(var2);
      }
   }

   public void addBoundObject(Object var1, String var2) {
      this.driver.currentNode().addBoundObject(var1, var2);
   }

   static {
      paths.put(".weblogic-rdbms-jar.weblogic-rdbms-bean.weblogic-query.caching-name.", new Integer(170));
      paths.put(".weblogic-rdbms-jar.", new Integer(128));
      paths.put(".weblogic-rdbms-jar.weblogic-rdbms-bean.weblogic-query.query-method.method-params.method-param.", new Integer(167));
      paths.put(".weblogic-rdbms-jar.weblogic-rdbms-bean.automatic-key-generation.", new Integer(178));
      paths.put(".weblogic-rdbms-jar.weblogic-rdbms-relation.weblogic-relationship-role.relationship-role-map.", new Integer(189));
      paths.put(".weblogic-rdbms-jar.weblogic-rdbms-bean.table-map.table-name.", new Integer(133));
      paths.put(".weblogic-rdbms-jar.weblogic-rdbms-bean.lock-order.", new Integer(176));
      paths.put(".weblogic-rdbms-jar.weblogic-rdbms-relation.weblogic-relationship-role.relationship-role-name.", new Integer(186));
      paths.put(".weblogic-rdbms-jar.weblogic-rdbms-bean.relationship-caching.caching-name.", new Integer(146));
      paths.put(".weblogic-rdbms-jar.weblogic-rdbms-bean.use-select-for-update.", new Integer(175));
      paths.put(".weblogic-rdbms-jar.validate-db-schema-with.", new Integer(196));
      paths.put(".weblogic-rdbms-jar.weblogic-rdbms-bean.relationship-caching.caching-element.caching-element.caching-element.caching-element.", new Integer(156));
      paths.put(".weblogic-rdbms-jar.weblogic-rdbms-bean.weblogic-query.", new Integer(162));
      paths.put(".weblogic-rdbms-jar.weblogic-rdbms-bean.weblogic-query.group-name.", new Integer(169));
      paths.put(".weblogic-rdbms-jar.weblogic-rdbms-relation.", new Integer(182));
      paths.put(".weblogic-rdbms-jar.weblogic-rdbms-bean.weblogic-query.max-elements.", new Integer(171));
      paths.put(".weblogic-rdbms-jar.weblogic-rdbms-bean.relationship-caching.caching-element.", new Integer(147));
      paths.put(".weblogic-rdbms-jar.weblogic-rdbms-bean.automatic-key-generation.generator-name.", new Integer(180));
      paths.put(".weblogic-rdbms-jar.weblogic-rdbms-bean.relationship-caching.caching-element.cmr-field.", new Integer(148));
      paths.put(".weblogic-rdbms-jar.weblogic-rdbms-bean.automatic-key-generation.key-cache-size.", new Integer(181));
      paths.put(".weblogic-rdbms-jar.weblogic-rdbms-bean.table-map.optimistic-column.", new Integer(140));
      paths.put(".weblogic-rdbms-jar.weblogic-rdbms-bean.weblogic-query.sql-select-distinct.", new Integer(173));
      paths.put(".weblogic-rdbms-jar.weblogic-rdbms-bean.relationship-caching.caching-element.group-name.", new Integer(149));
      paths.put(".weblogic-rdbms-jar.weblogic-rdbms-bean.weblogic-query.query-method.", new Integer(164));
      paths.put(".weblogic-rdbms-jar.weblogic-rdbms-bean.weblogic-query.query-method.method-name.", new Integer(165));
      paths.put(".weblogic-rdbms-jar.weblogic-rdbms-bean.relationship-caching.caching-element.caching-element.group-name.", new Integer(152));
      paths.put(".weblogic-rdbms-jar.weblogic-rdbms-bean.table-map.field-map.", new Integer(134));
      paths.put(".weblogic-rdbms-jar.weblogic-rdbms-bean.table-map.field-map.cmp-field.", new Integer(135));
      paths.put(".weblogic-rdbms-jar.database-type.", new Integer(197));
      paths.put(".weblogic-rdbms-jar.weblogic-rdbms-relation.weblogic-relationship-role.relationship-role-map.foreign-key-table.", new Integer(190));
      paths.put(".weblogic-rdbms-jar.weblogic-rdbms-bean.relationship-caching.caching-element.caching-element.", new Integer(150));
      paths.put(".weblogic-rdbms-jar.weblogic-rdbms-bean.weblogic-query.query-method.method-params.", new Integer(166));
      paths.put(".weblogic-rdbms-jar.weblogic-rdbms-bean.weblogic-query.include-updates.", new Integer(172));
      paths.put(".weblogic-rdbms-jar.weblogic-rdbms-bean.check-exists-on-method.", new Integer(177));
      paths.put(".weblogic-rdbms-jar.weblogic-rdbms-relation.weblogic-relationship-role.db-cascade-delete.", new Integer(188));
      paths.put(".weblogic-rdbms-jar.weblogic-rdbms-bean.table-map.", new Integer(132));
      paths.put(".weblogic-rdbms-jar.weblogic-rdbms-bean.weblogic-query.weblogic-ql.", new Integer(168));
      paths.put(".weblogic-rdbms-jar.weblogic-rdbms-relation.weblogic-relationship-role.relationship-role-map.column-map.", new Integer(192));
      paths.put(".weblogic-rdbms-jar.weblogic-rdbms-bean.field-group.cmp-field.", new Integer(143));
      paths.put(".weblogic-rdbms-jar.weblogic-rdbms-bean.weblogic-query.description.", new Integer(163));
      paths.put(".weblogic-rdbms-jar.weblogic-rdbms-bean.relationship-caching.caching-element.caching-element.caching-element.", new Integer(153));
      paths.put(".weblogic-rdbms-jar.weblogic-rdbms-bean.data-source-name.", new Integer(131));
      paths.put(".weblogic-rdbms-jar.weblogic-rdbms-bean.table-map.field-map.dbms-column.", new Integer(136));
      paths.put(".weblogic-rdbms-jar.weblogic-rdbms-bean.automatic-key-generation.generator-type.", new Integer(179));
      paths.put(".weblogic-rdbms-jar.weblogic-rdbms-bean.relationship-caching.caching-element.caching-element.caching-element.caching-element.caching-element.", new Integer(159));
      paths.put(".weblogic-rdbms-jar.weblogic-rdbms-bean.relationship-caching.caching-element.caching-element.caching-element.caching-element.group-name.", new Integer(158));
      paths.put(".weblogic-rdbms-jar.weblogic-rdbms-relation.relation-name.", new Integer(183));
      paths.put(".weblogic-rdbms-jar.weblogic-rdbms-bean.relationship-caching.caching-element.caching-element.caching-element.group-name.", new Integer(155));
      paths.put(".weblogic-rdbms-jar.weblogic-rdbms-bean.table-map.verify-rows.", new Integer(138));
      paths.put(".weblogic-rdbms-jar.weblogic-rdbms-bean.", new Integer(129));
      paths.put(".weblogic-rdbms-jar.weblogic-rdbms-relation.table-name.", new Integer(184));
      paths.put(".weblogic-rdbms-jar.weblogic-rdbms-bean.delay-database-insert-until.", new Integer(174));
      paths.put(".weblogic-rdbms-jar.weblogic-rdbms-relation.weblogic-relationship-role.relationship-role-map.primary-key-table.", new Integer(191));
      paths.put(".weblogic-rdbms-jar.weblogic-rdbms-bean.relationship-caching.caching-element.caching-element.caching-element.caching-element.caching-element.cmr-field.", new Integer(160));
      paths.put(".weblogic-rdbms-jar.weblogic-rdbms-bean.table-map.verify-columns.", new Integer(139));
      paths.put(".weblogic-rdbms-jar.weblogic-rdbms-bean.relationship-caching.caching-element.caching-element.caching-element.caching-element.cmr-field.", new Integer(157));
      paths.put(".weblogic-rdbms-jar.weblogic-rdbms-relation.weblogic-relationship-role.", new Integer(185));
      paths.put(".weblogic-rdbms-jar.weblogic-rdbms-bean.ejb-name.", new Integer(130));
      paths.put(".weblogic-rdbms-jar.weblogic-rdbms-relation.weblogic-relationship-role.relationship-role-map.column-map.foreign-key-column.", new Integer(193));
      paths.put(".weblogic-rdbms-jar.weblogic-rdbms-bean.relationship-caching.caching-element.caching-element.caching-element.caching-element.caching-element.group-name.", new Integer(161));
      paths.put(".weblogic-rdbms-jar.weblogic-rdbms-bean.relationship-caching.caching-element.caching-element.caching-element.cmr-field.", new Integer(154));
      paths.put(".weblogic-rdbms-jar.weblogic-rdbms-bean.relationship-caching.", new Integer(145));
      paths.put(".weblogic-rdbms-jar.weblogic-rdbms-relation.weblogic-relationship-role.group-name.", new Integer(187));
      paths.put(".weblogic-rdbms-jar.weblogic-rdbms-bean.field-group.cmr-field.", new Integer(144));
      paths.put(".weblogic-rdbms-jar.weblogic-rdbms-relation.weblogic-relationship-role.relationship-role-map.column-map.key-column.", new Integer(194));
      paths.put(".weblogic-rdbms-jar.weblogic-rdbms-bean.relationship-caching.caching-element.caching-element.cmr-field.", new Integer(151));
      paths.put(".weblogic-rdbms-jar.create-default-dbms-tables.", new Integer(195));
      paths.put(".weblogic-rdbms-jar.weblogic-rdbms-bean.table-map.field-map.dbms-column-type.", new Integer(137));
      paths.put(".weblogic-rdbms-jar.weblogic-rdbms-bean.field-group.", new Integer(141));
      paths.put(".weblogic-rdbms-jar.weblogic-rdbms-bean.field-group.group-name.", new Integer(142));
   }
}
