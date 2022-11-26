package weblogic.ejb.container.dd.xml;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;
import org.xml.sax.InputSource;
import weblogic.j2ee.descriptor.wl.EjbReferenceDescriptionBean;
import weblogic.j2ee.descriptor.wl.EntityCacheBean;
import weblogic.j2ee.descriptor.wl.EntityClusteringBean;
import weblogic.j2ee.descriptor.wl.EntityDescriptorBean;
import weblogic.j2ee.descriptor.wl.IdempotentMethodsBean;
import weblogic.j2ee.descriptor.wl.InvalidationTargetBean;
import weblogic.j2ee.descriptor.wl.JndiBindingBean;
import weblogic.j2ee.descriptor.wl.MessageDrivenDescriptorBean;
import weblogic.j2ee.descriptor.wl.MethodBean;
import weblogic.j2ee.descriptor.wl.MethodParamsBean;
import weblogic.j2ee.descriptor.wl.PersistenceBean;
import weblogic.j2ee.descriptor.wl.PersistenceUseBean;
import weblogic.j2ee.descriptor.wl.PoolBean;
import weblogic.j2ee.descriptor.wl.ResourceDescriptionBean;
import weblogic.j2ee.descriptor.wl.ResourceEnvDescriptionBean;
import weblogic.j2ee.descriptor.wl.SecurityRoleAssignmentBean;
import weblogic.j2ee.descriptor.wl.StatefulSessionCacheBean;
import weblogic.j2ee.descriptor.wl.StatefulSessionClusteringBean;
import weblogic.j2ee.descriptor.wl.StatefulSessionDescriptorBean;
import weblogic.j2ee.descriptor.wl.StatelessClusteringBean;
import weblogic.j2ee.descriptor.wl.StatelessSessionDescriptorBean;
import weblogic.j2ee.descriptor.wl.TransactionDescriptorBean;
import weblogic.j2ee.descriptor.wl.TransactionIsolationBean;
import weblogic.j2ee.descriptor.wl.WeblogicEjbJarBean;
import weblogic.j2ee.descriptor.wl.WeblogicEnterpriseBeanBean;
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

public final class WebLogicEjbJarLoader_WLS600 extends DDLoader implements XMLProcessor, InProcessor {
   private static final boolean debug = true;
   private static final boolean verbose = true;
   private static final Map paths = new HashMap();
   private ProcessorDriver driver;
   private static final String publicId = "-//BEA Systems, Inc.//DTD WebLogic 6.0.0 EJB//EN";
   private static final String localDTDResourceName = "/weblogic/ejb/container/dd/xml/weblogic600-ejb-jar.dtd";

   public ProcessorDriver getDriver() {
      return this.driver;
   }

   public WebLogicEjbJarLoader_WLS600() {
      this(true);
   }

   public WebLogicEjbJarLoader_WLS600(boolean var1) {
      this.driver = new ProcessorDriver(this, "-//BEA Systems, Inc.//DTD WebLogic 6.0.0 EJB//EN", "/weblogic/ejb/container/dd/xml/weblogic600-ejb-jar.dtd", var1);
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
               break;
            case 130:
               this.__pre_130(var1);
               this.__post_130(var1);
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
               this.__post_138(var1);
               break;
            case 139:
               this.__pre_139(var1);
               this.__post_139(var1);
               break;
            case 140:
               this.__pre_140(var1);
               break;
            case 141:
               this.__pre_141(var1);
               break;
            case 142:
               this.__pre_142(var1);
               this.__post_142(var1);
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
               break;
            case 151:
               this.__pre_151(var1);
               break;
            case 152:
               this.__pre_152(var1);
               this.__post_152(var1);
               break;
            case 153:
               this.__pre_153(var1);
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
               this.__post_167(var1);
               break;
            case 168:
               this.__pre_168(var1);
               break;
            case 169:
               this.__pre_169(var1);
               this.__post_169(var1);
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
               break;
            case 179:
               this.__pre_179(var1);
               this.__post_179(var1);
               break;
            case 180:
               this.__pre_180(var1);
               this.__post_180(var1);
               break;
            case 181:
               this.__pre_181(var1);
               break;
            case 182:
               this.__pre_182(var1);
               break;
            case 183:
               this.__pre_183(var1);
               break;
            case 184:
               this.__pre_184(var1);
               break;
            case 185:
               this.__pre_185(var1);
               break;
            case 186:
               this.__pre_186(var1);
               this.__post_186(var1);
               break;
            case 187:
               this.__pre_187(var1);
               break;
            case 188:
               this.__pre_188(var1);
               break;
            case 189:
               this.__pre_189(var1);
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
               this.__post_196(var1);
               break;
            case 197:
               this.__pre_197(var1);
               break;
            case 198:
               this.__pre_198(var1);
               break;
            case 199:
               this.__pre_199(var1);
               break;
            case 200:
               this.__pre_200(var1);
               break;
            case 201:
               this.__pre_201(var1);
               break;
            case 202:
               this.__pre_202(var1);
               this.__post_202(var1);
               break;
            case 203:
               this.__pre_203(var1);
               break;
            case 204:
               this.__pre_204(var1);
               this.__post_204(var1);
               break;
            case 205:
               this.__pre_205(var1);
               break;
            case 206:
               this.__pre_206(var1);
               break;
            case 207:
               this.__pre_207(var1);
               this.__post_207(var1);
               break;
            case 208:
               this.__pre_208(var1);
               break;
            case 209:
               this.__pre_209(var1);
               break;
            case 210:
               this.__pre_210(var1);
               this.__post_210(var1);
               break;
            case 211:
               this.__pre_211(var1);
               break;
            case 212:
               this.__pre_212(var1);
               break;
            case 213:
               this.__pre_213(var1);
               this.__post_213(var1);
               break;
            case 214:
               this.__pre_214(var1);
               break;
            case 215:
               this.__pre_215(var1);
               break;
            case 216:
               this.__pre_216(var1);
               this.__post_216(var1);
               break;
            case 217:
               this.__pre_217(var1);
               break;
            case 218:
               this.__pre_218(var1);
               this.__post_218(var1);
               break;
            case 219:
               this.__pre_219(var1);
               break;
            case 220:
               this.__pre_220(var1);
               break;
            case 221:
               this.__pre_221(var1);
               break;
            case 222:
               this.__pre_222(var1);
               break;
            case 223:
               this.__pre_223(var1);
               this.__post_223(var1);
               break;
            case 224:
               this.__pre_224(var1);
               break;
            case 225:
               this.__pre_225(var1);
               this.__post_225(var1);
               break;
            case 226:
               this.__pre_226(var1);
               break;
            case 227:
               this.__pre_227(var1);
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
            case 130:
            case 138:
            case 139:
            case 142:
            case 147:
            case 152:
            case 156:
            case 159:
            case 164:
            case 166:
            case 167:
            case 169:
            case 179:
            case 180:
            case 186:
            case 192:
            case 196:
            case 202:
            case 204:
            case 207:
            case 210:
            case 213:
            case 216:
            case 218:
            case 223:
            case 225:
               break;
            case 129:
               this.__post_129(var1);
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
            case 134:
               this.__post_134(var1);
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
            case 140:
               this.__post_140(var1);
               break;
            case 141:
               this.__post_141(var1);
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
            case 153:
               this.__post_153(var1);
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
            case 162:
               this.__post_162(var1);
               break;
            case 163:
               this.__post_163(var1);
               break;
            case 165:
               this.__post_165(var1);
               break;
            case 168:
               this.__post_168(var1);
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
            case 178:
               this.__post_178(var1);
               break;
            case 181:
               this.__post_181(var1);
               break;
            case 182:
               this.__post_182(var1);
               break;
            case 183:
               this.__post_183(var1);
               break;
            case 184:
               this.__post_184(var1);
               break;
            case 185:
               this.__post_185(var1);
               break;
            case 187:
               this.__post_187(var1);
               break;
            case 188:
               this.__post_188(var1);
               break;
            case 189:
               this.__post_189(var1);
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
            case 197:
               this.__post_197(var1);
               break;
            case 198:
               this.__post_198(var1);
               break;
            case 199:
               this.__post_199(var1);
               break;
            case 200:
               this.__post_200(var1);
               break;
            case 201:
               this.__post_201(var1);
               break;
            case 203:
               this.__post_203(var1);
               break;
            case 205:
               this.__post_205(var1);
               break;
            case 206:
               this.__post_206(var1);
               break;
            case 208:
               this.__post_208(var1);
               break;
            case 209:
               this.__post_209(var1);
               break;
            case 211:
               this.__post_211(var1);
               break;
            case 212:
               this.__post_212(var1);
               break;
            case 214:
               this.__post_214(var1);
               break;
            case 215:
               this.__post_215(var1);
               break;
            case 217:
               this.__post_217(var1);
               break;
            case 219:
               this.__post_219(var1);
               break;
            case 220:
               this.__post_220(var1);
               break;
            case 221:
               this.__post_221(var1);
               break;
            case 222:
               this.__post_222(var1);
               break;
            case 224:
               this.__post_224(var1);
               break;
            case 226:
               this.__post_226(var1);
               break;
            case 227:
               this.__post_227(var1);
               break;
            default:
               throw new AssertionError(var3.toString());
         }

      }
   }

   private void __pre_135(ProcessingContext var1) {
   }

   private void __post_135(ProcessingContext var1) throws SAXProcessorException, SAXValidationException {
      String var2 = Functions.value(var1);
      WLDD61Helper var3 = (WLDD61Helper)var1.getBoundObject("bd");
      WeblogicEnterpriseBeanBean var4 = (WeblogicEnterpriseBeanBean)var1.getBoundObject("wlBean");
      WeblogicEjbJarBean var5 = (WeblogicEjbJarBean)var1.getBoundObject("wlJar");
      if (var2.length() == 0) {
         throw new SAXValidationException("PAction[405662939](.weblogic-ejb-jar.weblogic-enterprise-bean.run-as-identity-principal.) must be a non-empty string");
      } else {
         var4.setRunAsPrincipalName(var2);
      }
   }

   private void __pre_190(ProcessingContext var1) {
   }

   private void __post_190(ProcessingContext var1) throws SAXProcessorException, SAXValidationException {
      String var2 = Functions.value(var1);
      StatefulSessionClusteringBean var3 = (StatefulSessionClusteringBean)var1.getBoundObject("clustering");
      StatefulSessionDescriptorBean var4 = (StatefulSessionDescriptorBean)var1.getBoundObject("sfsd");
      WLDD61Helper var5 = (WLDD61Helper)var1.getBoundObject("bd");
      WeblogicEnterpriseBeanBean var6 = (WeblogicEnterpriseBeanBean)var1.getBoundObject("wlBean");
      WeblogicEjbJarBean var7 = (WeblogicEjbJarBean)var1.getBoundObject("wlJar");
      if (var2.length() == 0) {
         throw new SAXValidationException("PAction[1404928347](.weblogic-ejb-jar.weblogic-enterprise-bean.stateful-session-descriptor.stateful-session-clustering.replication-type.) must be a non-empty string");
      } else if (!"none".equals(var2) && !"None".equals(var2) && !"inmemory".equals(var2) && !"InMemory".equals(var2)) {
         throw new SAXValidationException("PAction[1404928347](.weblogic-ejb-jar.weblogic-enterprise-bean.stateful-session-descriptor.stateful-session-clustering.replication-type.) must be one of the values: none,None,inmemory,InMemory");
      } else {
         var3.setReplicationType(var2);
      }
   }

   private void __pre_227(ProcessingContext var1) {
   }

   private void __post_227(ProcessingContext var1) throws SAXProcessorException, SAXValidationException {
      String var2 = Functions.value(var1);
      SecurityRoleAssignmentBean var3 = (SecurityRoleAssignmentBean)var1.getBoundObject("sra");
      WeblogicEjbJarBean var4 = (WeblogicEjbJarBean)var1.getBoundObject("wlJar");
      if (var2.length() == 0) {
         throw new SAXValidationException("PAction[604107971](.weblogic-ejb-jar.security-role-assignment.principal-name.) must be a non-empty string");
      } else {
         var3.addPrincipalName(var2);
      }
   }

   private void __pre_163(ProcessingContext var1) {
   }

   private void __post_163(ProcessingContext var1) throws SAXProcessorException, SAXValidationException {
      String var2 = Functions.value(var1);
      EntityClusteringBean var3 = (EntityClusteringBean)var1.getBoundObject("clustering");
      EntityDescriptorBean var4 = (EntityDescriptorBean)var1.getBoundObject("entity");
      WLDD61Helper var5 = (WLDD61Helper)var1.getBoundObject("bd");
      WeblogicEnterpriseBeanBean var6 = (WeblogicEnterpriseBeanBean)var1.getBoundObject("wlBean");
      WeblogicEjbJarBean var7 = (WeblogicEjbJarBean)var1.getBoundObject("wlJar");
      if (var2.length() == 0) {
         throw new SAXValidationException("PAction[123961122](.weblogic-ejb-jar.weblogic-enterprise-bean.entity-descriptor.entity-clustering.use-serverside-stubs.) must be a non-empty string");
      } else if (!"true".equals(var2) && !"True".equals(var2) && !"false".equals(var2) && !"False".equals(var2)) {
         throw new SAXValidationException("PAction[123961122](.weblogic-ejb-jar.weblogic-enterprise-bean.entity-descriptor.entity-clustering.use-serverside-stubs.) must be one of the values: true,True,false,False");
      } else {
         var3.setUseServersideStubs("True".equalsIgnoreCase(var2));
      }
   }

   private void __pre_197(ProcessingContext var1) {
   }

   private void __post_197(ProcessingContext var1) throws SAXProcessorException, SAXValidationException {
      String var2 = Functions.value(var1);
      PoolBean var3 = (PoolBean)var1.getBoundObject("pool");
      MessageDrivenDescriptorBean var4 = (MessageDrivenDescriptorBean)var1.getBoundObject("mdd");
      WLDD61Helper var5 = (WLDD61Helper)var1.getBoundObject("bd");
      WeblogicEnterpriseBeanBean var6 = (WeblogicEnterpriseBeanBean)var1.getBoundObject("wlBean");
      WeblogicEjbJarBean var7 = (WeblogicEjbJarBean)var1.getBoundObject("wlJar");
      if (var2.length() == 0) {
         throw new SAXValidationException("PAction[1227229563](.weblogic-ejb-jar.weblogic-enterprise-bean.message-driven-descriptor.pool.max-beans-in-free-pool.) must be a non-empty string");
      } else {
         try {
            this.validatePositiveInteger(var2);
         } catch (Exception var9) {
            throw new SAXValidationException("Path " + var1.getPath() + ": " + var9.getMessage());
         }

         int var8 = Integer.parseInt(var2);
         var3.setMaxBeansInFreePool(var8);
      }
   }

   private void __pre_220(ProcessingContext var1) {
   }

   private void __post_220(ProcessingContext var1) throws SAXProcessorException, SAXValidationException {
      String var2 = Functions.value(var1);
      MethodBean var3 = (MethodBean)var1.getBoundObject("method");
      TransactionIsolationBean var4 = (TransactionIsolationBean)var1.getBoundObject("tiso");
      WeblogicEjbJarBean var5 = (WeblogicEjbJarBean)var1.getBoundObject("wlJar");
      if (var2.length() == 0) {
         throw new SAXValidationException("PAction[1982791261](.weblogic-ejb-jar.transaction-isolation.method.ejb-name.) must be a non-empty string");
      } else {
         var3.setEjbName(var2.replace('/', '.'));
      }
   }

   private void __pre_183(ProcessingContext var1) {
   }

   private void __post_183(ProcessingContext var1) throws SAXProcessorException, SAXValidationException {
      String var2 = Functions.value(var1);
      StatefulSessionCacheBean var3 = (StatefulSessionCacheBean)var1.getBoundObject("cache");
      StatefulSessionDescriptorBean var4 = (StatefulSessionDescriptorBean)var1.getBoundObject("sfsd");
      WLDD61Helper var5 = (WLDD61Helper)var1.getBoundObject("bd");
      WeblogicEnterpriseBeanBean var6 = (WeblogicEnterpriseBeanBean)var1.getBoundObject("wlBean");
      WeblogicEjbJarBean var7 = (WeblogicEjbJarBean)var1.getBoundObject("wlJar");
      if (var2.length() == 0) {
         throw new SAXValidationException("PAction[1562557367](.weblogic-ejb-jar.weblogic-enterprise-bean.stateful-session-descriptor.stateful-session-cache.session-timeout-seconds.) must be a non-empty string");
      } else {
         try {
            this.validatePositiveInteger(var2);
         } catch (Exception var9) {
            throw new SAXValidationException("Path " + var1.getPath() + ": " + var9.getMessage());
         }

         int var8 = Integer.parseInt(var2);
         var3.setSessionTimeoutSeconds(var8);
      }
   }

   private void __pre_200(ProcessingContext var1) {
   }

   private void __post_200(ProcessingContext var1) throws SAXProcessorException, SAXValidationException {
      String var2 = Functions.value(var1);
      MessageDrivenDescriptorBean var3 = (MessageDrivenDescriptorBean)var1.getBoundObject("mdd");
      WLDD61Helper var4 = (WLDD61Helper)var1.getBoundObject("bd");
      WeblogicEnterpriseBeanBean var5 = (WeblogicEnterpriseBeanBean)var1.getBoundObject("wlBean");
      WeblogicEjbJarBean var6 = (WeblogicEjbJarBean)var1.getBoundObject("wlJar");
      if (var2.length() == 0) {
         throw new SAXValidationException("PAction[1101288798](.weblogic-ejb-jar.weblogic-enterprise-bean.message-driven-descriptor.provider-url.) must be a non-empty string");
      } else {
         var3.setProviderUrl(var2);
      }
   }

   private void __pre_166(ProcessingContext var1) {
      Object var2 = null;
      var1.addBoundObject(var2, "slsd");
   }

   private void __post_166(ProcessingContext var1) throws SAXProcessorException {
      StatelessSessionDescriptorBean var2 = (StatelessSessionDescriptorBean)var1.getBoundObject("slsd");
      WLDD61Helper var3 = (WLDD61Helper)var1.getBoundObject("bd");
      WeblogicEnterpriseBeanBean var4 = (WeblogicEnterpriseBeanBean)var1.getBoundObject("wlBean");
      WeblogicEjbJarBean var5 = (WeblogicEjbJarBean)var1.getBoundObject("wlJar");
      this.addBoundObject(var4.getStatelessSessionDescriptor(), "slsd");
   }

   private void __pre_193(ProcessingContext var1) {
   }

   private void __post_193(ProcessingContext var1) throws SAXProcessorException, SAXValidationException {
      String var2 = Functions.value(var1);
      MessageDrivenDescriptorBean var3 = (MessageDrivenDescriptorBean)var1.getBoundObject("mdd");
      WLDD61Helper var4 = (WLDD61Helper)var1.getBoundObject("bd");
      WeblogicEnterpriseBeanBean var5 = (WeblogicEnterpriseBeanBean)var1.getBoundObject("wlBean");
      WeblogicEjbJarBean var6 = (WeblogicEjbJarBean)var1.getBoundObject("wlJar");
      if (var2.length() == 0) {
         throw new SAXValidationException("PAction[942731712](.weblogic-ejb-jar.weblogic-enterprise-bean.message-driven-descriptor.jms-polling-interval-seconds.) must be a non-empty string");
      } else {
         try {
            this.validatePositiveInteger(var2);
         } catch (Exception var8) {
            throw new SAXValidationException("Path " + var1.getPath() + ": " + var8.getMessage());
         }

         var3.setJmsPollingIntervalSeconds(Integer.parseInt(var2));
      }
   }

   private void __pre_134(ProcessingContext var1) {
   }

   private void __post_134(ProcessingContext var1) throws SAXProcessorException, SAXValidationException {
      String var2 = Functions.value(var1);
      WLDD61Helper var3 = (WLDD61Helper)var1.getBoundObject("bd");
      WeblogicEnterpriseBeanBean var4 = (WeblogicEnterpriseBeanBean)var1.getBoundObject("wlBean");
      WeblogicEjbJarBean var5 = (WeblogicEjbJarBean)var1.getBoundObject("wlJar");
      if (var2.length() == 0) {
         throw new SAXValidationException("PAction[971848845](.weblogic-ejb-jar.weblogic-enterprise-bean.clients-on-same-server.) must be a non-empty string");
      } else if (!"true".equals(var2) && !"True".equals(var2) && !"false".equals(var2) && !"False".equals(var2)) {
         throw new SAXValidationException("PAction[971848845](.weblogic-ejb-jar.weblogic-enterprise-bean.clients-on-same-server.) must be one of the values: true,True,false,False");
      } else {
         var4.setClientsOnSameServer("True".equalsIgnoreCase(var2));
      }
   }

   private void __pre_158(ProcessingContext var1) {
   }

   private void __post_158(ProcessingContext var1) throws SAXProcessorException, SAXValidationException {
      String var2 = Functions.value(var1);
      PersistenceUseBean var3 = (PersistenceUseBean)var1.getBoundObject("pu");
      PersistenceBean var4 = (PersistenceBean)var1.getBoundObject("persist");
      EntityDescriptorBean var5 = (EntityDescriptorBean)var1.getBoundObject("entity");
      WLDD61Helper var6 = (WLDD61Helper)var1.getBoundObject("bd");
      WeblogicEnterpriseBeanBean var7 = (WeblogicEnterpriseBeanBean)var1.getBoundObject("wlBean");
      WeblogicEjbJarBean var8 = (WeblogicEjbJarBean)var1.getBoundObject("wlJar");
      if (var2.length() == 0) {
         throw new SAXValidationException("PAction[1910163204](.weblogic-ejb-jar.weblogic-enterprise-bean.entity-descriptor.persistence.persistence-use.type-version.) must be a non-empty string");
      } else {
         var3.setTypeVersion(var2);
         var3.setTypeStorage(var6.getPersistenceStorage(var3.getTypeIdentifier(), var3.getTypeVersion(), var7.getEjbName()));
      }
   }

   private void __pre_144(ProcessingContext var1) {
   }

   private void __post_144(ProcessingContext var1) throws SAXProcessorException, SAXValidationException {
      String var2 = Functions.value(var1);
      EntityCacheBean var3 = (EntityCacheBean)var1.getBoundObject("cache");
      EntityDescriptorBean var4 = (EntityDescriptorBean)var1.getBoundObject("entity");
      WLDD61Helper var5 = (WLDD61Helper)var1.getBoundObject("bd");
      WeblogicEnterpriseBeanBean var6 = (WeblogicEnterpriseBeanBean)var1.getBoundObject("wlBean");
      WeblogicEjbJarBean var7 = (WeblogicEjbJarBean)var1.getBoundObject("wlJar");
      if (var2.length() == 0) {
         throw new SAXValidationException("PAction[305623748](.weblogic-ejb-jar.weblogic-enterprise-bean.entity-descriptor.entity-cache.idle-timeout-seconds.) must be a non-empty string");
      } else {
         try {
            this.validatePositiveInteger(var2);
         } catch (Exception var9) {
            throw new SAXValidationException("Path " + var1.getPath() + ": " + var9.getMessage());
         }

         int var8 = Integer.parseInt(var2);
         var3.setIdleTimeoutSeconds(var8);
      }
   }

   private void __pre_160(ProcessingContext var1) {
   }

   private void __post_160(ProcessingContext var1) throws SAXProcessorException, SAXValidationException {
      String var2 = Functions.value(var1);
      EntityClusteringBean var3 = (EntityClusteringBean)var1.getBoundObject("clustering");
      EntityDescriptorBean var4 = (EntityDescriptorBean)var1.getBoundObject("entity");
      WLDD61Helper var5 = (WLDD61Helper)var1.getBoundObject("bd");
      WeblogicEnterpriseBeanBean var6 = (WeblogicEnterpriseBeanBean)var1.getBoundObject("wlBean");
      WeblogicEjbJarBean var7 = (WeblogicEjbJarBean)var1.getBoundObject("wlJar");
      if (var2.length() == 0) {
         throw new SAXValidationException("PAction[758529971](.weblogic-ejb-jar.weblogic-enterprise-bean.entity-descriptor.entity-clustering.home-is-clusterable.) must be a non-empty string");
      } else if (!"true".equals(var2) && !"True".equals(var2) && !"false".equals(var2) && !"False".equals(var2)) {
         throw new SAXValidationException("PAction[758529971](.weblogic-ejb-jar.weblogic-enterprise-bean.entity-descriptor.entity-clustering.home-is-clusterable.) must be one of the values: true,True,false,False");
      } else {
         var3.setHomeIsClusterable("True".equalsIgnoreCase(var2));
      }
   }

   private void __pre_133(ProcessingContext var1) {
   }

   private void __post_133(ProcessingContext var1) throws SAXProcessorException, SAXValidationException {
      String var2 = Functions.value(var1);
      WLDD61Helper var3 = (WLDD61Helper)var1.getBoundObject("bd");
      WeblogicEnterpriseBeanBean var4 = (WeblogicEnterpriseBeanBean)var1.getBoundObject("wlBean");
      WeblogicEjbJarBean var5 = (WeblogicEjbJarBean)var1.getBoundObject("wlJar");
      if (var2.length() == 0) {
         throw new SAXValidationException("PAction[2104457164](.weblogic-ejb-jar.weblogic-enterprise-bean.dispatch-policy.) must be a non-empty string");
      } else {
         var4.setDispatchPolicy(var2);
      }
   }

   private void __pre_223(ProcessingContext var1) {
      Object var2 = null;
      var1.addBoundObject(var2, "params");
   }

   private void __post_223(ProcessingContext var1) throws SAXProcessorException {
      MethodParamsBean var2 = (MethodParamsBean)var1.getBoundObject("params");
      MethodBean var3 = (MethodBean)var1.getBoundObject("method");
      TransactionIsolationBean var4 = (TransactionIsolationBean)var1.getBoundObject("tiso");
      WeblogicEjbJarBean var5 = (WeblogicEjbJarBean)var1.getBoundObject("wlJar");
      this.addBoundObject(var3.createMethodParams(), "params");
   }

   private void __pre_189(ProcessingContext var1) {
   }

   private void __post_189(ProcessingContext var1) throws SAXProcessorException, SAXValidationException {
      String var2 = Functions.value(var1);
      StatefulSessionClusteringBean var3 = (StatefulSessionClusteringBean)var1.getBoundObject("clustering");
      StatefulSessionDescriptorBean var4 = (StatefulSessionDescriptorBean)var1.getBoundObject("sfsd");
      WLDD61Helper var5 = (WLDD61Helper)var1.getBoundObject("bd");
      WeblogicEnterpriseBeanBean var6 = (WeblogicEnterpriseBeanBean)var1.getBoundObject("wlBean");
      WeblogicEjbJarBean var7 = (WeblogicEjbJarBean)var1.getBoundObject("wlJar");
      if (var2.length() == 0) {
         throw new SAXValidationException("PAction[237852351](.weblogic-ejb-jar.weblogic-enterprise-bean.stateful-session-descriptor.stateful-session-clustering.home-call-router-class-name.) must be a non-empty string");
      } else {
         var3.setHomeCallRouterClassName(var2);
      }
   }

   private void __pre_188(ProcessingContext var1) {
   }

   private void __post_188(ProcessingContext var1) throws SAXProcessorException, SAXValidationException {
      String var2 = Functions.value(var1);
      StatefulSessionClusteringBean var3 = (StatefulSessionClusteringBean)var1.getBoundObject("clustering");
      StatefulSessionDescriptorBean var4 = (StatefulSessionDescriptorBean)var1.getBoundObject("sfsd");
      WLDD61Helper var5 = (WLDD61Helper)var1.getBoundObject("bd");
      WeblogicEnterpriseBeanBean var6 = (WeblogicEnterpriseBeanBean)var1.getBoundObject("wlBean");
      WeblogicEjbJarBean var7 = (WeblogicEjbJarBean)var1.getBoundObject("wlJar");
      if (var2.length() == 0) {
         throw new SAXValidationException("PAction[608188624](.weblogic-ejb-jar.weblogic-enterprise-bean.stateful-session-descriptor.stateful-session-clustering.home-load-algorithm.) must be a non-empty string");
      } else if (!"roundrobin".equals(var2) && !"RoundRobin".equals(var2) && !"round-robin".equals(var2) && !"random".equals(var2) && !"Random".equals(var2) && !"weightbased".equals(var2) && !"WeightBased".equals(var2) && !"weight-based".equals(var2)) {
         throw new SAXValidationException("PAction[608188624](.weblogic-ejb-jar.weblogic-enterprise-bean.stateful-session-descriptor.stateful-session-clustering.home-load-algorithm.) must be one of the values: roundrobin,RoundRobin,round-robin,random,Random,weightbased,WeightBased,weight-based");
      } else {
         var3.setHomeLoadAlgorithm(var2);
      }
   }

   private void __pre_225(ProcessingContext var1) {
      Object var2 = null;
      var1.addBoundObject(var2, "sra");
   }

   private void __post_225(ProcessingContext var1) throws SAXProcessorException {
      SecurityRoleAssignmentBean var2 = (SecurityRoleAssignmentBean)var1.getBoundObject("sra");
      WeblogicEjbJarBean var3 = (WeblogicEjbJarBean)var1.getBoundObject("wlJar");
      this.addBoundObject(var3.createSecurityRoleAssignment(), "sra");
   }

   private void __pre_129(ProcessingContext var1) {
   }

   private void __post_129(ProcessingContext var1) throws SAXProcessorException {
      String var2 = Functions.value(var1);
      WeblogicEjbJarBean var3 = (WeblogicEjbJarBean)var1.getBoundObject("wlJar");
      var3.setDescription(var2);
   }

   private void __pre_156(ProcessingContext var1) {
      Object var2 = null;
      var1.addBoundObject(var2, "pu");
   }

   private void __post_156(ProcessingContext var1) throws SAXProcessorException {
      PersistenceUseBean var2 = (PersistenceUseBean)var1.getBoundObject("pu");
      PersistenceBean var3 = (PersistenceBean)var1.getBoundObject("persist");
      EntityDescriptorBean var4 = (EntityDescriptorBean)var1.getBoundObject("entity");
      WLDD61Helper var5 = (WLDD61Helper)var1.getBoundObject("bd");
      WeblogicEnterpriseBeanBean var6 = (WeblogicEnterpriseBeanBean)var1.getBoundObject("wlBean");
      WeblogicEjbJarBean var7 = (WeblogicEjbJarBean)var1.getBoundObject("wlJar");
      this.addBoundObject(var3.getPersistenceUse(), "pu");
   }

   private void __pre_167(ProcessingContext var1) {
      Object var2 = null;
      var1.addBoundObject(var2, "pool");
   }

   private void __post_167(ProcessingContext var1) throws SAXProcessorException {
      PoolBean var2 = (PoolBean)var1.getBoundObject("pool");
      StatelessSessionDescriptorBean var3 = (StatelessSessionDescriptorBean)var1.getBoundObject("slsd");
      WLDD61Helper var4 = (WLDD61Helper)var1.getBoundObject("bd");
      WeblogicEnterpriseBeanBean var5 = (WeblogicEnterpriseBeanBean)var1.getBoundObject("wlBean");
      WeblogicEjbJarBean var6 = (WeblogicEjbJarBean)var1.getBoundObject("wlJar");
      this.addBoundObject(var3.getPool(), "pool");
   }

   private void __pre_218(ProcessingContext var1) {
      Object var2 = null;
      var1.addBoundObject(var2, "method");
   }

   private void __post_218(ProcessingContext var1) throws SAXProcessorException {
      MethodBean var2 = (MethodBean)var1.getBoundObject("method");
      TransactionIsolationBean var3 = (TransactionIsolationBean)var1.getBoundObject("tiso");
      WeblogicEjbJarBean var4 = (WeblogicEjbJarBean)var1.getBoundObject("wlJar");
      this.addBoundObject(var3.createMethod(), "method");
   }

   private void __pre_165(ProcessingContext var1) {
   }

   private void __post_165(ProcessingContext var1) throws SAXProcessorException, SAXValidationException {
      String var2 = Functions.value(var1);
      InvalidationTargetBean var3 = (InvalidationTargetBean)var1.getBoundObject("invalidationTarget");
      EntityDescriptorBean var4 = (EntityDescriptorBean)var1.getBoundObject("entity");
      WLDD61Helper var5 = (WLDD61Helper)var1.getBoundObject("bd");
      WeblogicEnterpriseBeanBean var6 = (WeblogicEnterpriseBeanBean)var1.getBoundObject("wlBean");
      WeblogicEjbJarBean var7 = (WeblogicEjbJarBean)var1.getBoundObject("wlJar");
      if (var2.length() == 0) {
         throw new SAXValidationException("PAction[1451270520](.weblogic-ejb-jar.weblogic-enterprise-bean.entity-descriptor.invalidation-target.ejb-name.) must be a non-empty string");
      } else {
         var3.setEjbName(var2.replace('/', '.'));
      }
   }

   private void __pre_226(ProcessingContext var1) {
   }

   private void __post_226(ProcessingContext var1) throws SAXProcessorException, SAXValidationException {
      String var2 = Functions.value(var1);
      SecurityRoleAssignmentBean var3 = (SecurityRoleAssignmentBean)var1.getBoundObject("sra");
      WeblogicEjbJarBean var4 = (WeblogicEjbJarBean)var1.getBoundObject("wlJar");
      if (var2.length() == 0) {
         throw new SAXValidationException("PAction[1608446010](.weblogic-ejb-jar.security-role-assignment.role-name.) must be a non-empty string");
      } else {
         var3.setRoleName(var2);
      }
   }

   private void __pre_216(ProcessingContext var1) {
      Object var2 = null;
      var1.addBoundObject(var2, "tiso");
   }

   private void __post_216(ProcessingContext var1) throws SAXProcessorException {
      TransactionIsolationBean var2 = (TransactionIsolationBean)var1.getBoundObject("tiso");
      WeblogicEjbJarBean var3 = (WeblogicEjbJarBean)var1.getBoundObject("wlJar");
      this.addBoundObject(var3.createTransactionIsolation(), "tiso");
   }

   private void __pre_196(ProcessingContext var1) {
      Object var2 = null;
      var1.addBoundObject(var2, "pool");
   }

   private void __post_196(ProcessingContext var1) throws SAXProcessorException {
      PoolBean var2 = (PoolBean)var1.getBoundObject("pool");
      MessageDrivenDescriptorBean var3 = (MessageDrivenDescriptorBean)var1.getBoundObject("mdd");
      WLDD61Helper var4 = (WLDD61Helper)var1.getBoundObject("bd");
      WeblogicEnterpriseBeanBean var5 = (WeblogicEnterpriseBeanBean)var1.getBoundObject("wlBean");
      WeblogicEjbJarBean var6 = (WeblogicEjbJarBean)var1.getBoundObject("wlJar");
      this.addBoundObject(var3.getPool(), "pool");
   }

   private void __pre_221(ProcessingContext var1) {
   }

   private void __post_221(ProcessingContext var1) throws SAXProcessorException, SAXValidationException {
      String var2 = Functions.value(var1);
      MethodBean var3 = (MethodBean)var1.getBoundObject("method");
      TransactionIsolationBean var4 = (TransactionIsolationBean)var1.getBoundObject("tiso");
      WeblogicEjbJarBean var5 = (WeblogicEjbJarBean)var1.getBoundObject("wlJar");
      if (var2.length() == 0) {
         throw new SAXValidationException("PAction[992136656](.weblogic-ejb-jar.transaction-isolation.method.method-intf.) must be a non-empty string");
      } else if (!"home".equals(var2) && !"Home".equals(var2) && !"remote".equals(var2) && !"Remote".equals(var2) && !"LocalHome".equals(var2) && !"localHome".equals(var2) && !"localhome".equals(var2) && !"local".equals(var2) && !"Local".equals(var2)) {
         throw new SAXValidationException("PAction[992136656](.weblogic-ejb-jar.transaction-isolation.method.method-intf.) must be one of the values: home,Home,remote,Remote,LocalHome,localHome,localhome,local,Local");
      } else {
         var3.setMethodIntf(var2);
      }
   }

   private void __pre_159(ProcessingContext var1) {
      Object var2 = null;
      var1.addBoundObject(var2, "clustering");
   }

   private void __post_159(ProcessingContext var1) throws SAXProcessorException {
      EntityClusteringBean var2 = (EntityClusteringBean)var1.getBoundObject("clustering");
      EntityDescriptorBean var3 = (EntityDescriptorBean)var1.getBoundObject("entity");
      WLDD61Helper var4 = (WLDD61Helper)var1.getBoundObject("bd");
      WeblogicEnterpriseBeanBean var5 = (WeblogicEnterpriseBeanBean)var1.getBoundObject("wlBean");
      WeblogicEjbJarBean var6 = (WeblogicEjbJarBean)var1.getBoundObject("wlJar");
      this.addBoundObject(var3.getEntityClustering(), "clustering");
   }

   private void __pre_148(ProcessingContext var1) {
   }

   private void __post_148(ProcessingContext var1) throws SAXProcessorException, SAXValidationException {
      String var2 = Functions.value(var1);
      PersistenceBean var3 = (PersistenceBean)var1.getBoundObject("persist");
      EntityDescriptorBean var4 = (EntityDescriptorBean)var1.getBoundObject("entity");
      WLDD61Helper var5 = (WLDD61Helper)var1.getBoundObject("bd");
      WeblogicEnterpriseBeanBean var6 = (WeblogicEnterpriseBeanBean)var1.getBoundObject("wlBean");
      WeblogicEjbJarBean var7 = (WeblogicEjbJarBean)var1.getBoundObject("wlJar");
      if (var2.length() == 0) {
         throw new SAXValidationException("PAction[511833308](.weblogic-ejb-jar.weblogic-enterprise-bean.entity-descriptor.persistence.is-modified-method-name.) must be a non-empty string");
      } else {
         var3.setIsModifiedMethodName(var2);
      }
   }

   private void __pre_204(ProcessingContext var1) {
      Object var2 = null;
      var1.addBoundObject(var2, "resDesc");
   }

   private void __post_204(ProcessingContext var1) throws SAXProcessorException {
      ResourceDescriptionBean var2 = (ResourceDescriptionBean)var1.getBoundObject("resDesc");
      WLDD61Helper var3 = (WLDD61Helper)var1.getBoundObject("bd");
      WeblogicEnterpriseBeanBean var4 = (WeblogicEnterpriseBeanBean)var1.getBoundObject("wlBean");
      WeblogicEjbJarBean var5 = (WeblogicEjbJarBean)var1.getBoundObject("wlJar");
      this.addBoundObject(var4.createResourceDescription(), "resDesc");
   }

   private void __pre_149(ProcessingContext var1) {
   }

   private void __post_149(ProcessingContext var1) throws SAXProcessorException, SAXValidationException {
      String var2 = Functions.value(var1);
      PersistenceBean var3 = (PersistenceBean)var1.getBoundObject("persist");
      EntityDescriptorBean var4 = (EntityDescriptorBean)var1.getBoundObject("entity");
      WLDD61Helper var5 = (WLDD61Helper)var1.getBoundObject("bd");
      WeblogicEnterpriseBeanBean var6 = (WeblogicEnterpriseBeanBean)var1.getBoundObject("wlBean");
      WeblogicEjbJarBean var7 = (WeblogicEjbJarBean)var1.getBoundObject("wlJar");
      if (var2.length() == 0) {
         throw new SAXValidationException("PAction[1297685781](.weblogic-ejb-jar.weblogic-enterprise-bean.entity-descriptor.persistence.delay-updates-until-end-of-tx.) must be a non-empty string");
      } else if (!"true".equals(var2) && !"True".equals(var2) && !"false".equals(var2) && !"False".equals(var2)) {
         throw new SAXValidationException("PAction[1297685781](.weblogic-ejb-jar.weblogic-enterprise-bean.entity-descriptor.persistence.delay-updates-until-end-of-tx.) must be one of the values: true,True,false,False");
      } else {
         var3.setDelayUpdatesUntilEndOfTx("True".equalsIgnoreCase(var2));
      }
   }

   private void __pre_173(ProcessingContext var1) {
   }

   private void __post_173(ProcessingContext var1) throws SAXProcessorException, SAXValidationException {
      String var2 = Functions.value(var1);
      StatelessClusteringBean var3 = (StatelessClusteringBean)var1.getBoundObject("clustering");
      StatelessSessionDescriptorBean var4 = (StatelessSessionDescriptorBean)var1.getBoundObject("slsd");
      WLDD61Helper var5 = (WLDD61Helper)var1.getBoundObject("bd");
      WeblogicEnterpriseBeanBean var6 = (WeblogicEnterpriseBeanBean)var1.getBoundObject("wlBean");
      WeblogicEjbJarBean var7 = (WeblogicEjbJarBean)var1.getBoundObject("wlJar");
      if (var2.length() == 0) {
         throw new SAXValidationException("PAction[1705929636](.weblogic-ejb-jar.weblogic-enterprise-bean.stateless-session-descriptor.stateless-clustering.use-serverside-stubs.) must be a non-empty string");
      } else if (!"true".equals(var2) && !"True".equals(var2) && !"false".equals(var2) && !"False".equals(var2)) {
         throw new SAXValidationException("PAction[1705929636](.weblogic-ejb-jar.weblogic-enterprise-bean.stateless-session-descriptor.stateless-clustering.use-serverside-stubs.) must be one of the values: true,True,false,False");
      } else {
         var3.setUseServersideStubs("True".equalsIgnoreCase(var2));
      }
   }

   private void __pre_142(ProcessingContext var1) {
      Object var2 = null;
      var1.addBoundObject(var2, "cache");
   }

   private void __post_142(ProcessingContext var1) throws SAXProcessorException {
      EntityCacheBean var2 = (EntityCacheBean)var1.getBoundObject("cache");
      EntityDescriptorBean var3 = (EntityDescriptorBean)var1.getBoundObject("entity");
      WLDD61Helper var4 = (WLDD61Helper)var1.getBoundObject("bd");
      WeblogicEnterpriseBeanBean var5 = (WeblogicEnterpriseBeanBean)var1.getBoundObject("wlBean");
      WeblogicEjbJarBean var6 = (WeblogicEjbJarBean)var1.getBoundObject("wlJar");
      this.addBoundObject(var3.getEntityCache(), "cache");
   }

   private void __pre_175(ProcessingContext var1) {
   }

   private void __post_175(ProcessingContext var1) throws SAXProcessorException, SAXValidationException {
      String var2 = Functions.value(var1);
      PoolBean var3 = (PoolBean)var1.getBoundObject("pool");
      StatelessSessionDescriptorBean var4 = (StatelessSessionDescriptorBean)var1.getBoundObject("slsd");
      WLDD61Helper var5 = (WLDD61Helper)var1.getBoundObject("bd");
      WeblogicEnterpriseBeanBean var6 = (WeblogicEnterpriseBeanBean)var1.getBoundObject("wlBean");
      WeblogicEjbJarBean var7 = (WeblogicEjbJarBean)var1.getBoundObject("wlJar");
      if (var2.length() == 0) {
         throw new SAXValidationException("PAction[1221555852](.weblogic-ejb-jar.weblogic-enterprise-bean.stateless-session-descriptor.pool.initial-beans-in-free-pool.) must be a non-empty string");
      } else {
         try {
            this.validatePositiveInteger(var2);
         } catch (Exception var9) {
            throw new SAXValidationException("Path " + var1.getPath() + ": " + var9.getMessage());
         }

         int var8 = Integer.parseInt(var2);
         var3.setInitialBeansInFreePool(var8);
      }
   }

   private void __pre_194(ProcessingContext var1) {
   }

   private void __post_194(ProcessingContext var1) throws SAXProcessorException, SAXValidationException {
      String var2 = Functions.value(var1);
      MessageDrivenDescriptorBean var3 = (MessageDrivenDescriptorBean)var1.getBoundObject("mdd");
      WLDD61Helper var4 = (WLDD61Helper)var1.getBoundObject("bd");
      WeblogicEnterpriseBeanBean var5 = (WeblogicEnterpriseBeanBean)var1.getBoundObject("wlBean");
      WeblogicEjbJarBean var6 = (WeblogicEjbJarBean)var1.getBoundObject("wlJar");
      if (var2.length() == 0) {
         throw new SAXValidationException("PAction[1509514333](.weblogic-ejb-jar.weblogic-enterprise-bean.message-driven-descriptor.jms-client-id.) must be a non-empty string");
      } else {
         var3.setJmsClientId(var2);
      }
   }

   private void __pre_202(ProcessingContext var1) {
      Object var2 = null;
      var1.addBoundObject(var2, "td");
   }

   private void __post_202(ProcessingContext var1) throws SAXProcessorException {
      TransactionDescriptorBean var2 = (TransactionDescriptorBean)var1.getBoundObject("td");
      WLDD61Helper var3 = (WLDD61Helper)var1.getBoundObject("bd");
      WeblogicEnterpriseBeanBean var4 = (WeblogicEnterpriseBeanBean)var1.getBoundObject("wlBean");
      WeblogicEjbJarBean var5 = (WeblogicEjbJarBean)var1.getBoundObject("wlJar");
      this.addBoundObject(var4.getTransactionDescriptor(), "td");
   }

   private void __pre_153(ProcessingContext var1) {
   }

   private void __post_153(ProcessingContext var1) throws SAXProcessorException, SAXValidationException {
      String var2 = Functions.value(var1);
      DDLoader.PersistenceType var3 = (DDLoader.PersistenceType)var1.getBoundObject("pType");
      PersistenceBean var4 = (PersistenceBean)var1.getBoundObject("persist");
      EntityDescriptorBean var5 = (EntityDescriptorBean)var1.getBoundObject("entity");
      WLDD61Helper var6 = (WLDD61Helper)var1.getBoundObject("bd");
      WeblogicEnterpriseBeanBean var7 = (WeblogicEnterpriseBeanBean)var1.getBoundObject("wlBean");
      WeblogicEjbJarBean var8 = (WeblogicEjbJarBean)var1.getBoundObject("wlJar");
      if (var2.length() == 0) {
         throw new SAXValidationException("PAction[1556956098](.weblogic-ejb-jar.weblogic-enterprise-bean.entity-descriptor.persistence.persistence-type.type-identifier.) must be a non-empty string");
      } else {
         var3.id = var2;
      }
   }

   private void __pre_178(ProcessingContext var1) {
   }

   private void __post_178(ProcessingContext var1) throws SAXProcessorException, SAXValidationException {
      String var2 = Functions.value(var1);
      StatelessClusteringBean var3 = (StatelessClusteringBean)var1.getBoundObject("clustering");
      StatelessSessionDescriptorBean var4 = (StatelessSessionDescriptorBean)var1.getBoundObject("slsd");
      WLDD61Helper var5 = (WLDD61Helper)var1.getBoundObject("bd");
      WeblogicEnterpriseBeanBean var6 = (WeblogicEnterpriseBeanBean)var1.getBoundObject("wlBean");
      WeblogicEjbJarBean var7 = (WeblogicEjbJarBean)var1.getBoundObject("wlJar");
      if (var2.length() == 0) {
         throw new SAXValidationException("PAction[1252585652](.weblogic-ejb-jar.weblogic-enterprise-bean.stateless-session-descriptor.stateless-clustering.stateless-bean-methods-are-idempotent.) must be a non-empty string");
      } else if (!"true".equals(var2) && !"True".equals(var2) && !"false".equals(var2) && !"False".equals(var2)) {
         throw new SAXValidationException("PAction[1252585652](.weblogic-ejb-jar.weblogic-enterprise-bean.stateless-session-descriptor.stateless-clustering.stateless-bean-methods-are-idempotent.) must be one of the values: true,True,false,False");
      } else {
         if ("True".equalsIgnoreCase(var2)) {
            IdempotentMethodsBean var8 = var7.getIdempotentMethods();
            if (var8 == null) {
               var8 = var7.createIdempotentMethods();
            }

            MethodBean var9 = var8.createMethod();
            var9.setEjbName(var6.getEjbName());
            var9.setMethodName("*");
         }

      }
   }

   private void __pre_198(ProcessingContext var1) {
   }

   private void __post_198(ProcessingContext var1) throws SAXProcessorException, SAXValidationException {
      String var2 = Functions.value(var1);
      PoolBean var3 = (PoolBean)var1.getBoundObject("pool");
      MessageDrivenDescriptorBean var4 = (MessageDrivenDescriptorBean)var1.getBoundObject("mdd");
      WLDD61Helper var5 = (WLDD61Helper)var1.getBoundObject("bd");
      WeblogicEnterpriseBeanBean var6 = (WeblogicEnterpriseBeanBean)var1.getBoundObject("wlBean");
      WeblogicEjbJarBean var7 = (WeblogicEjbJarBean)var1.getBoundObject("wlJar");
      if (var2.length() == 0) {
         throw new SAXValidationException("PAction[2036368507](.weblogic-ejb-jar.weblogic-enterprise-bean.message-driven-descriptor.pool.initial-beans-in-free-pool.) must be a non-empty string");
      } else {
         try {
            this.validatePositiveInteger(var2);
         } catch (Exception var9) {
            throw new SAXValidationException("Path " + var1.getPath() + ": " + var9.getMessage());
         }

         int var8 = Integer.parseInt(var2);
         var3.setInitialBeansInFreePool(var8);
      }
   }

   private void __pre_203(ProcessingContext var1) {
   }

   private void __post_203(ProcessingContext var1) throws SAXProcessorException, SAXValidationException {
      String var2 = Functions.value(var1);
      TransactionDescriptorBean var3 = (TransactionDescriptorBean)var1.getBoundObject("td");
      WLDD61Helper var4 = (WLDD61Helper)var1.getBoundObject("bd");
      WeblogicEnterpriseBeanBean var5 = (WeblogicEnterpriseBeanBean)var1.getBoundObject("wlBean");
      WeblogicEjbJarBean var6 = (WeblogicEjbJarBean)var1.getBoundObject("wlJar");
      if (var2.length() == 0) {
         throw new SAXValidationException("PAction[1785210046](.weblogic-ejb-jar.weblogic-enterprise-bean.transaction-descriptor.trans-timeout-seconds.) must be a non-empty string");
      } else {
         try {
            this.validatePositiveInteger(var2);
         } catch (Exception var8) {
            throw new SAXValidationException("Path " + var1.getPath() + ": " + var8.getMessage());
         }

         int var7 = Integer.parseInt(var2);
         var3.setTransTimeoutSeconds(var7);
      }
   }

   private void __pre_154(ProcessingContext var1) {
   }

   private void __post_154(ProcessingContext var1) throws SAXProcessorException, SAXValidationException {
      String var2 = Functions.value(var1);
      DDLoader.PersistenceType var3 = (DDLoader.PersistenceType)var1.getBoundObject("pType");
      PersistenceBean var4 = (PersistenceBean)var1.getBoundObject("persist");
      EntityDescriptorBean var5 = (EntityDescriptorBean)var1.getBoundObject("entity");
      WLDD61Helper var6 = (WLDD61Helper)var1.getBoundObject("bd");
      WeblogicEnterpriseBeanBean var7 = (WeblogicEnterpriseBeanBean)var1.getBoundObject("wlBean");
      WeblogicEjbJarBean var8 = (WeblogicEjbJarBean)var1.getBoundObject("wlJar");
      if (var2.length() == 0) {
         throw new SAXValidationException("PAction[1552787810](.weblogic-ejb-jar.weblogic-enterprise-bean.entity-descriptor.persistence.persistence-type.type-version.) must be a non-empty string");
      } else {
         var3.version = var2;
      }
   }

   private void __pre_210(ProcessingContext var1) {
      Object var2 = null;
      var1.addBoundObject(var2, "ejbRefDesc");
   }

   private void __post_210(ProcessingContext var1) throws SAXProcessorException {
      EjbReferenceDescriptionBean var2 = (EjbReferenceDescriptionBean)var1.getBoundObject("ejbRefDesc");
      WLDD61Helper var3 = (WLDD61Helper)var1.getBoundObject("bd");
      WeblogicEnterpriseBeanBean var4 = (WeblogicEnterpriseBeanBean)var1.getBoundObject("wlBean");
      WeblogicEjbJarBean var5 = (WeblogicEjbJarBean)var1.getBoundObject("wlJar");
      this.addBoundObject(var4.createEjbReferenceDescription(), "ejbRefDesc");
   }

   private void __pre_187(ProcessingContext var1) {
   }

   private void __post_187(ProcessingContext var1) throws SAXProcessorException, SAXValidationException {
      String var2 = Functions.value(var1);
      StatefulSessionClusteringBean var3 = (StatefulSessionClusteringBean)var1.getBoundObject("clustering");
      StatefulSessionDescriptorBean var4 = (StatefulSessionDescriptorBean)var1.getBoundObject("sfsd");
      WLDD61Helper var5 = (WLDD61Helper)var1.getBoundObject("bd");
      WeblogicEnterpriseBeanBean var6 = (WeblogicEnterpriseBeanBean)var1.getBoundObject("wlBean");
      WeblogicEjbJarBean var7 = (WeblogicEjbJarBean)var1.getBoundObject("wlJar");
      if (var2.length() == 0) {
         throw new SAXValidationException("PAction[1361960727](.weblogic-ejb-jar.weblogic-enterprise-bean.stateful-session-descriptor.stateful-session-clustering.home-is-clusterable.) must be a non-empty string");
      } else if (!"true".equals(var2) && !"True".equals(var2) && !"false".equals(var2) && !"False".equals(var2)) {
         throw new SAXValidationException("PAction[1361960727](.weblogic-ejb-jar.weblogic-enterprise-bean.stateful-session-descriptor.stateful-session-clustering.home-is-clusterable.) must be one of the values: true,True,false,False");
      } else {
         var3.setHomeIsClusterable("True".equalsIgnoreCase(var2));
      }
   }

   private void __pre_195(ProcessingContext var1) {
   }

   private void __post_195(ProcessingContext var1) throws SAXProcessorException, SAXValidationException {
      String var2 = Functions.value(var1);
      MessageDrivenDescriptorBean var3 = (MessageDrivenDescriptorBean)var1.getBoundObject("mdd");
      WLDD61Helper var4 = (WLDD61Helper)var1.getBoundObject("bd");
      WeblogicEnterpriseBeanBean var5 = (WeblogicEnterpriseBeanBean)var1.getBoundObject("wlBean");
      WeblogicEjbJarBean var6 = (WeblogicEjbJarBean)var1.getBoundObject("wlJar");
      if (var2.length() == 0) {
         throw new SAXValidationException("PAction[739498517](.weblogic-ejb-jar.weblogic-enterprise-bean.message-driven-descriptor.destination-jndi-name.) must be a non-empty string");
      } else {
         var3.setDestinationJNDIName(var2);
      }
   }

   private void __pre_206(ProcessingContext var1) {
   }

   private void __post_206(ProcessingContext var1) throws SAXProcessorException, SAXValidationException {
      String var2 = Functions.value(var1);
      ResourceDescriptionBean var3 = (ResourceDescriptionBean)var1.getBoundObject("resDesc");
      WLDD61Helper var4 = (WLDD61Helper)var1.getBoundObject("bd");
      WeblogicEnterpriseBeanBean var5 = (WeblogicEnterpriseBeanBean)var1.getBoundObject("wlBean");
      WeblogicEjbJarBean var6 = (WeblogicEjbJarBean)var1.getBoundObject("wlJar");
      if (var2.length() == 0) {
         throw new SAXValidationException("PAction[125130493](.weblogic-ejb-jar.weblogic-enterprise-bean.reference-descriptor.resource-description.jndi-name.) must be a non-empty string");
      } else {
         var3.setJNDIName(var2);
      }
   }

   private void __pre_213(ProcessingContext var1) {
      Object var2 = null;
      var1.addBoundObject(var2, "ejbLocalRefDesc");
   }

   private void __post_213(ProcessingContext var1) throws SAXProcessorException {
      EjbReferenceDescriptionBean var2 = (EjbReferenceDescriptionBean)var1.getBoundObject("ejbLocalRefDesc");
      WLDD61Helper var3 = (WLDD61Helper)var1.getBoundObject("bd");
      WeblogicEnterpriseBeanBean var4 = (WeblogicEnterpriseBeanBean)var1.getBoundObject("wlBean");
      WeblogicEjbJarBean var5 = (WeblogicEjbJarBean)var1.getBoundObject("wlJar");
      this.addBoundObject(var4.createEjbReferenceDescription(), "ejbLocalRefDesc");
   }

   private void __pre_143(ProcessingContext var1) {
   }

   private void __post_143(ProcessingContext var1) throws SAXProcessorException, SAXValidationException {
      String var2 = Functions.value(var1);
      EntityCacheBean var3 = (EntityCacheBean)var1.getBoundObject("cache");
      EntityDescriptorBean var4 = (EntityDescriptorBean)var1.getBoundObject("entity");
      WLDD61Helper var5 = (WLDD61Helper)var1.getBoundObject("bd");
      WeblogicEnterpriseBeanBean var6 = (WeblogicEnterpriseBeanBean)var1.getBoundObject("wlBean");
      WeblogicEjbJarBean var7 = (WeblogicEjbJarBean)var1.getBoundObject("wlJar");
      if (var2.length() == 0) {
         throw new SAXValidationException("PAction[914504136](.weblogic-ejb-jar.weblogic-enterprise-bean.entity-descriptor.entity-cache.max-beans-in-cache.) must be a non-empty string");
      } else {
         try {
            this.validateIntegerGreaterThanZero(var2);
         } catch (Exception var9) {
            throw new SAXValidationException("Path " + var1.getPath() + ": " + var9.getMessage());
         }

         int var8 = Integer.parseInt(var2);
         var3.setMaxBeansInCache(var8);
      }
   }

   private void __pre_172(ProcessingContext var1) {
   }

   private void __post_172(ProcessingContext var1) throws SAXProcessorException, SAXValidationException {
      String var2 = Functions.value(var1);
      StatelessClusteringBean var3 = (StatelessClusteringBean)var1.getBoundObject("clustering");
      StatelessSessionDescriptorBean var4 = (StatelessSessionDescriptorBean)var1.getBoundObject("slsd");
      WLDD61Helper var5 = (WLDD61Helper)var1.getBoundObject("bd");
      WeblogicEnterpriseBeanBean var6 = (WeblogicEnterpriseBeanBean)var1.getBoundObject("wlBean");
      WeblogicEjbJarBean var7 = (WeblogicEjbJarBean)var1.getBoundObject("wlJar");
      if (var2.length() == 0) {
         throw new SAXValidationException("PAction[166239592](.weblogic-ejb-jar.weblogic-enterprise-bean.stateless-session-descriptor.stateless-clustering.home-load-algorithm.) must be a non-empty string");
      } else if (!"roundrobin".equals(var2) && !"RoundRobin".equals(var2) && !"round-robin".equals(var2) && !"random".equals(var2) && !"Random".equals(var2) && !"weightbased".equals(var2) && !"WeightBased".equals(var2) && !"weight-based".equals(var2)) {
         throw new SAXValidationException("PAction[166239592](.weblogic-ejb-jar.weblogic-enterprise-bean.stateless-session-descriptor.stateless-clustering.home-load-algorithm.) must be one of the values: roundrobin,RoundRobin,round-robin,random,Random,weightbased,WeightBased,weight-based");
      } else {
         var3.setHomeLoadAlgorithm(var2);
      }
   }

   private void __pre_180(ProcessingContext var1) {
      Object var2 = null;
      var1.addBoundObject(var2, "cache");
   }

   private void __post_180(ProcessingContext var1) throws SAXProcessorException {
      StatefulSessionCacheBean var2 = (StatefulSessionCacheBean)var1.getBoundObject("cache");
      StatefulSessionDescriptorBean var3 = (StatefulSessionDescriptorBean)var1.getBoundObject("sfsd");
      WLDD61Helper var4 = (WLDD61Helper)var1.getBoundObject("bd");
      WeblogicEnterpriseBeanBean var5 = (WeblogicEnterpriseBeanBean)var1.getBoundObject("wlBean");
      WeblogicEjbJarBean var6 = (WeblogicEjbJarBean)var1.getBoundObject("wlJar");
      this.addBoundObject(var3.getStatefulSessionCache(), "cache");
   }

   private void __pre_201(ProcessingContext var1) {
   }

   private void __post_201(ProcessingContext var1) throws SAXProcessorException, SAXValidationException {
      String var2 = Functions.value(var1);
      MessageDrivenDescriptorBean var3 = (MessageDrivenDescriptorBean)var1.getBoundObject("mdd");
      WLDD61Helper var4 = (WLDD61Helper)var1.getBoundObject("bd");
      WeblogicEnterpriseBeanBean var5 = (WeblogicEnterpriseBeanBean)var1.getBoundObject("wlBean");
      WeblogicEjbJarBean var6 = (WeblogicEjbJarBean)var1.getBoundObject("wlJar");
      if (var2.length() == 0) {
         throw new SAXValidationException("PAction[991505714](.weblogic-ejb-jar.weblogic-enterprise-bean.message-driven-descriptor.connection-factory-jndi-name.) must be a non-empty string");
      } else {
         var3.setConnectionFactoryJNDIName(var2);
      }
   }

   private void __pre_130(ProcessingContext var1) {
      WLDD61Helper var2 = new WLDD61Helper();
      var1.addBoundObject(var2, "bd");
      Object var3 = null;
      var1.addBoundObject(var3, "wlBean");
   }

   private void __post_130(ProcessingContext var1) throws SAXProcessorException {
      WLDD61Helper var2 = (WLDD61Helper)var1.getBoundObject("bd");
      WeblogicEnterpriseBeanBean var3 = (WeblogicEnterpriseBeanBean)var1.getBoundObject("wlBean");
      WeblogicEjbJarBean var4 = (WeblogicEjbJarBean)var1.getBoundObject("wlJar");
      this.addBoundObject(var4.createWeblogicEnterpriseBean(), "wlBean");
   }

   private void __pre_137(ProcessingContext var1) {
   }

   private void __post_137(ProcessingContext var1) throws SAXProcessorException, SAXValidationException {
      String var2 = Functions.value(var1);
      WLDD61Helper var3 = (WLDD61Helper)var1.getBoundObject("bd");
      WeblogicEnterpriseBeanBean var4 = (WeblogicEnterpriseBeanBean)var1.getBoundObject("wlBean");
      WeblogicEjbJarBean var5 = (WeblogicEjbJarBean)var1.getBoundObject("wlJar");
      if (var2.length() == 0) {
         throw new SAXValidationException("PAction[385242642](.weblogic-ejb-jar.weblogic-enterprise-bean.local-jndi-name.) must be a non-empty string");
      } else {
         JndiBindingBean var6 = var4.lookupJndiBinding("_WL_LOCALHOME");
         if (var6 == null) {
            var6 = var4.createJndiBinding();
         }

         var6.setClassName("_WL_LOCALHOME");
         var6.setJndiName(var2);
      }
   }

   private void __pre_184(ProcessingContext var1) {
   }

   private void __post_184(ProcessingContext var1) throws SAXProcessorException, SAXValidationException {
      String var2 = Functions.value(var1);
      StatefulSessionCacheBean var3 = (StatefulSessionCacheBean)var1.getBoundObject("cache");
      StatefulSessionDescriptorBean var4 = (StatefulSessionDescriptorBean)var1.getBoundObject("sfsd");
      WLDD61Helper var5 = (WLDD61Helper)var1.getBoundObject("bd");
      WeblogicEnterpriseBeanBean var6 = (WeblogicEnterpriseBeanBean)var1.getBoundObject("wlBean");
      WeblogicEjbJarBean var7 = (WeblogicEjbJarBean)var1.getBoundObject("wlJar");
      if (var2.length() == 0) {
         throw new SAXValidationException("PAction[824009085](.weblogic-ejb-jar.weblogic-enterprise-bean.stateful-session-descriptor.stateful-session-cache.cache-type.) must be a non-empty string");
      } else if (!"NRU".equals(var2) && !"LRU".equals(var2)) {
         throw new SAXValidationException("PAction[824009085](.weblogic-ejb-jar.weblogic-enterprise-bean.stateful-session-descriptor.stateful-session-cache.cache-type.) must be one of the values: NRU,LRU");
      } else {
         var3.setCacheType(var2);
      }
   }

   private void __pre_150(ProcessingContext var1) {
   }

   private void __post_150(ProcessingContext var1) throws SAXProcessorException, SAXValidationException {
      String var2 = Functions.value(var1);
      PersistenceBean var3 = (PersistenceBean)var1.getBoundObject("persist");
      EntityDescriptorBean var4 = (EntityDescriptorBean)var1.getBoundObject("entity");
      WLDD61Helper var5 = (WLDD61Helper)var1.getBoundObject("bd");
      WeblogicEnterpriseBeanBean var6 = (WeblogicEnterpriseBeanBean)var1.getBoundObject("wlBean");
      WeblogicEjbJarBean var7 = (WeblogicEjbJarBean)var1.getBoundObject("wlJar");
      if (var2.length() == 0) {
         throw new SAXValidationException("PAction[2085857771](.weblogic-ejb-jar.weblogic-enterprise-bean.entity-descriptor.persistence.finders-load-bean.) must be a non-empty string");
      } else if (!"true".equals(var2) && !"True".equals(var2) && !"false".equals(var2) && !"False".equals(var2)) {
         throw new SAXValidationException("PAction[2085857771](.weblogic-ejb-jar.weblogic-enterprise-bean.entity-descriptor.persistence.finders-load-bean.) must be one of the values: true,True,false,False");
      } else {
         var3.setFindersLoadBean("True".equalsIgnoreCase(var2));
      }
   }

   private void __pre_182(ProcessingContext var1) {
   }

   private void __post_182(ProcessingContext var1) throws SAXProcessorException, SAXValidationException {
      String var2 = Functions.value(var1);
      StatefulSessionCacheBean var3 = (StatefulSessionCacheBean)var1.getBoundObject("cache");
      StatefulSessionDescriptorBean var4 = (StatefulSessionDescriptorBean)var1.getBoundObject("sfsd");
      WLDD61Helper var5 = (WLDD61Helper)var1.getBoundObject("bd");
      WeblogicEnterpriseBeanBean var6 = (WeblogicEnterpriseBeanBean)var1.getBoundObject("wlBean");
      WeblogicEjbJarBean var7 = (WeblogicEjbJarBean)var1.getBoundObject("wlJar");
      if (var2.length() == 0) {
         throw new SAXValidationException("PAction[248609774](.weblogic-ejb-jar.weblogic-enterprise-bean.stateful-session-descriptor.stateful-session-cache.idle-timeout-seconds.) must be a non-empty string");
      } else {
         try {
            this.validatePositiveInteger(var2);
         } catch (Exception var9) {
            throw new SAXValidationException("Path " + var1.getPath() + ": " + var9.getMessage());
         }

         int var8 = Integer.parseInt(var2);
         var3.setIdleTimeoutSeconds(var8);
      }
   }

   private void __pre_185(ProcessingContext var1) {
   }

   private void __post_185(ProcessingContext var1) throws SAXProcessorException, SAXValidationException {
      String var2 = Functions.value(var1);
      StatefulSessionDescriptorBean var3 = (StatefulSessionDescriptorBean)var1.getBoundObject("sfsd");
      WLDD61Helper var4 = (WLDD61Helper)var1.getBoundObject("bd");
      WeblogicEnterpriseBeanBean var5 = (WeblogicEnterpriseBeanBean)var1.getBoundObject("wlBean");
      WeblogicEjbJarBean var6 = (WeblogicEjbJarBean)var1.getBoundObject("wlJar");
      if (var2.length() == 0) {
         throw new SAXValidationException("PAction[708049632](.weblogic-ejb-jar.weblogic-enterprise-bean.stateful-session-descriptor.persistent-store-dir.) must be a non-empty string");
      } else {
         var3.setPersistentStoreDir(var2);
      }
   }

   private void __pre_128(ProcessingContext var1) {
      Object var2 = null;
      var1.addBoundObject(var2, "wlJar");
   }

   private void __post_128(ProcessingContext var1) throws SAXProcessorException {
      WeblogicEjbJarBean var2 = (WeblogicEjbJarBean)var1.getBoundObject("wlJar");
      var2 = this.ejbDescriptor.createWeblogicEjbJarBean(this.encoding);
      this.addBoundObject(var2, "wlJar");
      this.setEntityAlwaysUsesTransactionDefault();
   }

   private void __pre_152(ProcessingContext var1) {
      DDLoader.PersistenceType var2 = new DDLoader.PersistenceType();
      var1.addBoundObject(var2, "pType");
   }

   private void __post_152(ProcessingContext var1) throws SAXProcessorException {
      DDLoader.PersistenceType var2 = (DDLoader.PersistenceType)var1.getBoundObject("pType");
      PersistenceBean var3 = (PersistenceBean)var1.getBoundObject("persist");
      EntityDescriptorBean var4 = (EntityDescriptorBean)var1.getBoundObject("entity");
      WLDD61Helper var5 = (WLDD61Helper)var1.getBoundObject("bd");
      WeblogicEnterpriseBeanBean var6 = (WeblogicEnterpriseBeanBean)var1.getBoundObject("wlBean");
      WeblogicEjbJarBean var7 = (WeblogicEjbJarBean)var1.getBoundObject("wlJar");
   }

   private void __pre_186(ProcessingContext var1) {
      Object var2 = null;
      var1.addBoundObject(var2, "clustering");
   }

   private void __post_186(ProcessingContext var1) throws SAXProcessorException {
      StatefulSessionClusteringBean var2 = (StatefulSessionClusteringBean)var1.getBoundObject("clustering");
      StatefulSessionDescriptorBean var3 = (StatefulSessionDescriptorBean)var1.getBoundObject("sfsd");
      WLDD61Helper var4 = (WLDD61Helper)var1.getBoundObject("bd");
      WeblogicEnterpriseBeanBean var5 = (WeblogicEnterpriseBeanBean)var1.getBoundObject("wlBean");
      WeblogicEjbJarBean var6 = (WeblogicEjbJarBean)var1.getBoundObject("wlJar");
      this.addBoundObject(var3.getStatefulSessionClustering(), "clustering");
   }

   private void __pre_219(ProcessingContext var1) {
   }

   private void __post_219(ProcessingContext var1) throws SAXProcessorException {
      String var2 = Functions.value(var1);
      MethodBean var3 = (MethodBean)var1.getBoundObject("method");
      TransactionIsolationBean var4 = (TransactionIsolationBean)var1.getBoundObject("tiso");
      WeblogicEjbJarBean var5 = (WeblogicEjbJarBean)var1.getBoundObject("wlJar");
      var3.setDescription(var2);
   }

   private void __pre_199(ProcessingContext var1) {
   }

   private void __post_199(ProcessingContext var1) throws SAXProcessorException, SAXValidationException {
      String var2 = Functions.value(var1);
      MessageDrivenDescriptorBean var3 = (MessageDrivenDescriptorBean)var1.getBoundObject("mdd");
      WLDD61Helper var4 = (WLDD61Helper)var1.getBoundObject("bd");
      WeblogicEnterpriseBeanBean var5 = (WeblogicEnterpriseBeanBean)var1.getBoundObject("wlBean");
      WeblogicEjbJarBean var6 = (WeblogicEjbJarBean)var1.getBoundObject("wlJar");
      if (var2.length() == 0) {
         throw new SAXValidationException("PAction[1887400018](.weblogic-ejb-jar.weblogic-enterprise-bean.message-driven-descriptor.initial-context-factory.) must be a non-empty string");
      } else {
         var3.setInitialContextFactory(var2);
      }
   }

   private void __pre_131(ProcessingContext var1) {
   }

   private void __post_131(ProcessingContext var1) throws SAXProcessorException, SAXValidationException {
      String var2 = Functions.value(var1);
      WLDD61Helper var3 = (WLDD61Helper)var1.getBoundObject("bd");
      WeblogicEnterpriseBeanBean var4 = (WeblogicEnterpriseBeanBean)var1.getBoundObject("wlBean");
      WeblogicEjbJarBean var5 = (WeblogicEjbJarBean)var1.getBoundObject("wlJar");
      if (var2.length() == 0) {
         throw new SAXValidationException("PAction[285377351](.weblogic-ejb-jar.weblogic-enterprise-bean.ejb-name.) must be a non-empty string");
      } else {
         var4.setEjbName(var2.replace('/', '.'));
         var4.setEnableCallByReference(true);
      }
   }

   private void __pre_138(ProcessingContext var1) {
      Object var2 = null;
      var1.addBoundObject(var2, "entity");
   }

   private void __post_138(ProcessingContext var1) throws SAXProcessorException {
      EntityDescriptorBean var2 = (EntityDescriptorBean)var1.getBoundObject("entity");
      WLDD61Helper var3 = (WLDD61Helper)var1.getBoundObject("bd");
      WeblogicEnterpriseBeanBean var4 = (WeblogicEnterpriseBeanBean)var1.getBoundObject("wlBean");
      WeblogicEjbJarBean var5 = (WeblogicEjbJarBean)var1.getBoundObject("wlJar");
      this.addBoundObject(var4.getEntityDescriptor(), "entity");
   }

   private void __pre_162(ProcessingContext var1) {
   }

   private void __post_162(ProcessingContext var1) throws SAXProcessorException, SAXValidationException {
      String var2 = Functions.value(var1);
      EntityClusteringBean var3 = (EntityClusteringBean)var1.getBoundObject("clustering");
      EntityDescriptorBean var4 = (EntityDescriptorBean)var1.getBoundObject("entity");
      WLDD61Helper var5 = (WLDD61Helper)var1.getBoundObject("bd");
      WeblogicEnterpriseBeanBean var6 = (WeblogicEnterpriseBeanBean)var1.getBoundObject("wlBean");
      WeblogicEjbJarBean var7 = (WeblogicEjbJarBean)var1.getBoundObject("wlJar");
      if (var2.length() == 0) {
         throw new SAXValidationException("PAction[344560770](.weblogic-ejb-jar.weblogic-enterprise-bean.entity-descriptor.entity-clustering.home-call-router-class-name.) must be a non-empty string");
      } else {
         var3.setHomeCallRouterClassName(var2);
      }
   }

   private void __pre_212(ProcessingContext var1) {
   }

   private void __post_212(ProcessingContext var1) throws SAXProcessorException, SAXValidationException {
      String var2 = Functions.value(var1);
      EjbReferenceDescriptionBean var3 = (EjbReferenceDescriptionBean)var1.getBoundObject("ejbRefDesc");
      WLDD61Helper var4 = (WLDD61Helper)var1.getBoundObject("bd");
      WeblogicEnterpriseBeanBean var5 = (WeblogicEnterpriseBeanBean)var1.getBoundObject("wlBean");
      WeblogicEjbJarBean var6 = (WeblogicEjbJarBean)var1.getBoundObject("wlJar");
      if (var2.length() == 0) {
         throw new SAXValidationException("PAction[559450121](.weblogic-ejb-jar.weblogic-enterprise-bean.reference-descriptor.ejb-reference-description.jndi-name.) must be a non-empty string");
      } else {
         var3.setJNDIName(var2);
      }
   }

   private void __pre_174(ProcessingContext var1) {
   }

   private void __post_174(ProcessingContext var1) throws SAXProcessorException, SAXValidationException {
      String var2 = Functions.value(var1);
      StatelessClusteringBean var3 = (StatelessClusteringBean)var1.getBoundObject("clustering");
      StatelessSessionDescriptorBean var4 = (StatelessSessionDescriptorBean)var1.getBoundObject("slsd");
      WLDD61Helper var5 = (WLDD61Helper)var1.getBoundObject("bd");
      WeblogicEnterpriseBeanBean var6 = (WeblogicEnterpriseBeanBean)var1.getBoundObject("wlBean");
      WeblogicEjbJarBean var7 = (WeblogicEjbJarBean)var1.getBoundObject("wlJar");
      if (var2.length() == 0) {
         throw new SAXValidationException("PAction[716083600](.weblogic-ejb-jar.weblogic-enterprise-bean.stateless-session-descriptor.stateless-clustering.stateless-bean-is-clusterable.) must be a non-empty string");
      } else if (!"true".equals(var2) && !"True".equals(var2) && !"false".equals(var2) && !"False".equals(var2)) {
         throw new SAXValidationException("PAction[716083600](.weblogic-ejb-jar.weblogic-enterprise-bean.stateless-session-descriptor.stateless-clustering.stateless-bean-is-clusterable.) must be one of the values: true,True,false,False");
      } else {
         var3.setStatelessBeanIsClusterable("True".equalsIgnoreCase(var2));
      }
   }

   private void __pre_207(ProcessingContext var1) {
      Object var2 = null;
      var1.addBoundObject(var2, "resEnvDesc");
   }

   private void __post_207(ProcessingContext var1) throws SAXProcessorException {
      ResourceEnvDescriptionBean var2 = (ResourceEnvDescriptionBean)var1.getBoundObject("resEnvDesc");
      WLDD61Helper var3 = (WLDD61Helper)var1.getBoundObject("bd");
      WeblogicEnterpriseBeanBean var4 = (WeblogicEnterpriseBeanBean)var1.getBoundObject("wlBean");
      WeblogicEjbJarBean var5 = (WeblogicEjbJarBean)var1.getBoundObject("wlJar");
      this.addBoundObject(var4.createResourceEnvDescription(), "resEnvDesc");
   }

   private void __pre_132(ProcessingContext var1) {
   }

   private void __post_132(ProcessingContext var1) throws SAXProcessorException, SAXValidationException {
      String var2 = Functions.value(var1);
      WLDD61Helper var3 = (WLDD61Helper)var1.getBoundObject("bd");
      WeblogicEnterpriseBeanBean var4 = (WeblogicEnterpriseBeanBean)var1.getBoundObject("wlBean");
      WeblogicEjbJarBean var5 = (WeblogicEjbJarBean)var1.getBoundObject("wlJar");
      if (var2.length() == 0) {
         throw new SAXValidationException("PAction[791885625](.weblogic-ejb-jar.weblogic-enterprise-bean.enable-call-by-reference.) must be a non-empty string");
      } else if (!"true".equals(var2) && !"True".equals(var2) && !"false".equals(var2) && !"False".equals(var2)) {
         throw new SAXValidationException("PAction[791885625](.weblogic-ejb-jar.weblogic-enterprise-bean.enable-call-by-reference.) must be one of the values: true,True,false,False");
      } else {
         var4.setEnableCallByReference("True".equalsIgnoreCase(var2));
      }
   }

   private void __pre_170(ProcessingContext var1) {
   }

   private void __post_170(ProcessingContext var1) throws SAXProcessorException, SAXValidationException {
      String var2 = Functions.value(var1);
      StatelessClusteringBean var3 = (StatelessClusteringBean)var1.getBoundObject("clustering");
      StatelessSessionDescriptorBean var4 = (StatelessSessionDescriptorBean)var1.getBoundObject("slsd");
      WLDD61Helper var5 = (WLDD61Helper)var1.getBoundObject("bd");
      WeblogicEnterpriseBeanBean var6 = (WeblogicEnterpriseBeanBean)var1.getBoundObject("wlBean");
      WeblogicEjbJarBean var7 = (WeblogicEjbJarBean)var1.getBoundObject("wlJar");
      if (var2.length() == 0) {
         throw new SAXValidationException("PAction[2001112025](.weblogic-ejb-jar.weblogic-enterprise-bean.stateless-session-descriptor.stateless-clustering.home-is-clusterable.) must be a non-empty string");
      } else if (!"true".equals(var2) && !"True".equals(var2) && !"false".equals(var2) && !"False".equals(var2)) {
         throw new SAXValidationException("PAction[2001112025](.weblogic-ejb-jar.weblogic-enterprise-bean.stateless-session-descriptor.stateless-clustering.home-is-clusterable.) must be one of the values: true,True,false,False");
      } else {
         var3.setHomeIsClusterable("True".equalsIgnoreCase(var2));
      }
   }

   private void __pre_192(ProcessingContext var1) {
      Object var2 = null;
      var1.addBoundObject(var2, "mdd");
   }

   private void __post_192(ProcessingContext var1) throws SAXProcessorException {
      MessageDrivenDescriptorBean var2 = (MessageDrivenDescriptorBean)var1.getBoundObject("mdd");
      WLDD61Helper var3 = (WLDD61Helper)var1.getBoundObject("bd");
      WeblogicEnterpriseBeanBean var4 = (WeblogicEnterpriseBeanBean)var1.getBoundObject("wlBean");
      WeblogicEjbJarBean var5 = (WeblogicEjbJarBean)var1.getBoundObject("wlJar");
      this.addBoundObject(var4.getMessageDrivenDescriptor(), "mdd");
   }

   private void __pre_171(ProcessingContext var1) {
   }

   private void __post_171(ProcessingContext var1) throws SAXProcessorException, SAXValidationException {
      String var2 = Functions.value(var1);
      StatelessClusteringBean var3 = (StatelessClusteringBean)var1.getBoundObject("clustering");
      StatelessSessionDescriptorBean var4 = (StatelessSessionDescriptorBean)var1.getBoundObject("slsd");
      WLDD61Helper var5 = (WLDD61Helper)var1.getBoundObject("bd");
      WeblogicEnterpriseBeanBean var6 = (WeblogicEnterpriseBeanBean)var1.getBoundObject("wlBean");
      WeblogicEjbJarBean var7 = (WeblogicEjbJarBean)var1.getBoundObject("wlJar");
      if (var2.length() == 0) {
         throw new SAXValidationException("PAction[314265080](.weblogic-ejb-jar.weblogic-enterprise-bean.stateless-session-descriptor.stateless-clustering.home-call-router-class-name.) must be a non-empty string");
      } else {
         var3.setHomeCallRouterClassName(var2);
      }
   }

   private void __pre_177(ProcessingContext var1) {
   }

   private void __post_177(ProcessingContext var1) throws SAXProcessorException, SAXValidationException {
      String var2 = Functions.value(var1);
      StatelessClusteringBean var3 = (StatelessClusteringBean)var1.getBoundObject("clustering");
      StatelessSessionDescriptorBean var4 = (StatelessSessionDescriptorBean)var1.getBoundObject("slsd");
      WLDD61Helper var5 = (WLDD61Helper)var1.getBoundObject("bd");
      WeblogicEnterpriseBeanBean var6 = (WeblogicEnterpriseBeanBean)var1.getBoundObject("wlBean");
      WeblogicEjbJarBean var7 = (WeblogicEjbJarBean)var1.getBoundObject("wlJar");
      if (var2.length() == 0) {
         throw new SAXValidationException("PAction[1288141870](.weblogic-ejb-jar.weblogic-enterprise-bean.stateless-session-descriptor.stateless-clustering.stateless-bean-call-router-class-name.) must be a non-empty string");
      } else {
         var3.setStatelessBeanCallRouterClassName(var2);
      }
   }

   private void __pre_214(ProcessingContext var1) {
   }

   private void __post_214(ProcessingContext var1) throws SAXProcessorException, SAXValidationException {
      String var2 = Functions.value(var1);
      EjbReferenceDescriptionBean var3 = (EjbReferenceDescriptionBean)var1.getBoundObject("ejbLocalRefDesc");
      WLDD61Helper var4 = (WLDD61Helper)var1.getBoundObject("bd");
      WeblogicEnterpriseBeanBean var5 = (WeblogicEnterpriseBeanBean)var1.getBoundObject("wlBean");
      WeblogicEjbJarBean var6 = (WeblogicEjbJarBean)var1.getBoundObject("wlJar");
      if (var2.length() == 0) {
         throw new SAXValidationException("PAction[2054881392](.weblogic-ejb-jar.weblogic-enterprise-bean.reference-descriptor.ejb-local-reference-description.ejb-ref-name.) must be a non-empty string");
      } else {
         var3.setEjbRefName(var2);
      }
   }

   private void __pre_168(ProcessingContext var1) {
   }

   private void __post_168(ProcessingContext var1) throws SAXProcessorException, SAXValidationException {
      String var2 = Functions.value(var1);
      PoolBean var3 = (PoolBean)var1.getBoundObject("pool");
      StatelessSessionDescriptorBean var4 = (StatelessSessionDescriptorBean)var1.getBoundObject("slsd");
      WLDD61Helper var5 = (WLDD61Helper)var1.getBoundObject("bd");
      WeblogicEnterpriseBeanBean var6 = (WeblogicEnterpriseBeanBean)var1.getBoundObject("wlBean");
      WeblogicEjbJarBean var7 = (WeblogicEjbJarBean)var1.getBoundObject("wlJar");
      if (var2.length() == 0) {
         throw new SAXValidationException("PAction[966808741](.weblogic-ejb-jar.weblogic-enterprise-bean.stateless-session-descriptor.pool.max-beans-in-free-pool.) must be a non-empty string");
      } else {
         try {
            this.validatePositiveInteger(var2);
         } catch (Exception var9) {
            throw new SAXValidationException("Path " + var1.getPath() + ": " + var9.getMessage());
         }

         int var8 = Integer.parseInt(var2);
         var3.setMaxBeansInFreePool(var8);
      }
   }

   private void __pre_209(ProcessingContext var1) {
   }

   private void __post_209(ProcessingContext var1) throws SAXProcessorException, SAXValidationException {
      String var2 = Functions.value(var1);
      ResourceEnvDescriptionBean var3 = (ResourceEnvDescriptionBean)var1.getBoundObject("resEnvDesc");
      WLDD61Helper var4 = (WLDD61Helper)var1.getBoundObject("bd");
      WeblogicEnterpriseBeanBean var5 = (WeblogicEnterpriseBeanBean)var1.getBoundObject("wlBean");
      WeblogicEjbJarBean var6 = (WeblogicEjbJarBean)var1.getBoundObject("wlJar");
      if (var2.length() == 0) {
         throw new SAXValidationException("PAction[1908153060](.weblogic-ejb-jar.weblogic-enterprise-bean.reference-descriptor.resource-env-description.jndi-name.) must be a non-empty string");
      } else {
         var3.setJNDIName(var2);
      }
   }

   private void __pre_140(ProcessingContext var1) {
   }

   private void __post_140(ProcessingContext var1) throws SAXProcessorException, SAXValidationException {
      String var2 = Functions.value(var1);
      PoolBean var3 = (PoolBean)var1.getBoundObject("pool");
      EntityDescriptorBean var4 = (EntityDescriptorBean)var1.getBoundObject("entity");
      WLDD61Helper var5 = (WLDD61Helper)var1.getBoundObject("bd");
      WeblogicEnterpriseBeanBean var6 = (WeblogicEnterpriseBeanBean)var1.getBoundObject("wlBean");
      WeblogicEjbJarBean var7 = (WeblogicEjbJarBean)var1.getBoundObject("wlJar");
      if (var2.length() == 0) {
         throw new SAXValidationException("PAction[116211441](.weblogic-ejb-jar.weblogic-enterprise-bean.entity-descriptor.pool.max-beans-in-free-pool.) must be a non-empty string");
      } else {
         try {
            this.validatePositiveInteger(var2);
         } catch (Exception var9) {
            throw new SAXValidationException("Path " + var1.getPath() + ": " + var9.getMessage());
         }

         int var8 = Integer.parseInt(var2);
         var3.setMaxBeansInFreePool(var8);
      }
   }

   private void __pre_211(ProcessingContext var1) {
   }

   private void __post_211(ProcessingContext var1) throws SAXProcessorException, SAXValidationException {
      String var2 = Functions.value(var1);
      EjbReferenceDescriptionBean var3 = (EjbReferenceDescriptionBean)var1.getBoundObject("ejbRefDesc");
      WLDD61Helper var4 = (WLDD61Helper)var1.getBoundObject("bd");
      WeblogicEnterpriseBeanBean var5 = (WeblogicEnterpriseBeanBean)var1.getBoundObject("wlBean");
      WeblogicEjbJarBean var6 = (WeblogicEjbJarBean)var1.getBoundObject("wlJar");
      if (var2.length() == 0) {
         throw new SAXValidationException("PAction[607635164](.weblogic-ejb-jar.weblogic-enterprise-bean.reference-descriptor.ejb-reference-description.ejb-ref-name.) must be a non-empty string");
      } else {
         var3.setEjbRefName(var2);
      }
   }

   private void __pre_151(ProcessingContext var1) {
   }

   private void __post_151(ProcessingContext var1) throws SAXProcessorException, SAXValidationException {
      String var2 = Functions.value(var1);
      PersistenceBean var3 = (PersistenceBean)var1.getBoundObject("persist");
      EntityDescriptorBean var4 = (EntityDescriptorBean)var1.getBoundObject("entity");
      WLDD61Helper var5 = (WLDD61Helper)var1.getBoundObject("bd");
      WeblogicEnterpriseBeanBean var6 = (WeblogicEnterpriseBeanBean)var1.getBoundObject("wlBean");
      WeblogicEjbJarBean var7 = (WeblogicEjbJarBean)var1.getBoundObject("wlJar");
      if (var2.length() == 0) {
         throw new SAXValidationException("PAction[529116035](.weblogic-ejb-jar.weblogic-enterprise-bean.entity-descriptor.persistence.db-is-shared.) must be a non-empty string");
      } else if (!"true".equals(var2) && !"True".equals(var2) && !"false".equals(var2) && !"False".equals(var2)) {
         throw new SAXValidationException("PAction[529116035](.weblogic-ejb-jar.weblogic-enterprise-bean.entity-descriptor.persistence.db-is-shared.) must be one of the values: true,True,false,False");
      } else {
         EntityCacheBean var8 = var4.getEntityCache();
         var8.setCacheBetweenTransactions(!"True".equalsIgnoreCase(var2));
      }
   }

   private void __pre_179(ProcessingContext var1) {
      Object var2 = null;
      var1.addBoundObject(var2, "sfsd");
   }

   private void __post_179(ProcessingContext var1) throws SAXProcessorException {
      StatefulSessionDescriptorBean var2 = (StatefulSessionDescriptorBean)var1.getBoundObject("sfsd");
      WLDD61Helper var3 = (WLDD61Helper)var1.getBoundObject("bd");
      WeblogicEnterpriseBeanBean var4 = (WeblogicEnterpriseBeanBean)var1.getBoundObject("wlBean");
      WeblogicEjbJarBean var5 = (WeblogicEjbJarBean)var1.getBoundObject("wlJar");
      this.addBoundObject(var4.getStatefulSessionDescriptor(), "sfsd");
   }

   private void __pre_155(ProcessingContext var1) {
   }

   private void __post_155(ProcessingContext var1) throws SAXProcessorException, SAXValidationException {
      String var2 = Functions.value(var1);
      DDLoader.PersistenceType var3 = (DDLoader.PersistenceType)var1.getBoundObject("pType");
      PersistenceBean var4 = (PersistenceBean)var1.getBoundObject("persist");
      EntityDescriptorBean var5 = (EntityDescriptorBean)var1.getBoundObject("entity");
      WLDD61Helper var6 = (WLDD61Helper)var1.getBoundObject("bd");
      WeblogicEnterpriseBeanBean var7 = (WeblogicEnterpriseBeanBean)var1.getBoundObject("wlBean");
      WeblogicEjbJarBean var8 = (WeblogicEjbJarBean)var1.getBoundObject("wlJar");
      if (var2.length() == 0) {
         throw new SAXValidationException("PAction[242481580](.weblogic-ejb-jar.weblogic-enterprise-bean.entity-descriptor.persistence.persistence-type.type-storage.) must be a non-empty string");
      } else {
         var6.addPersistenceType(var3.id, var3.version, var2);
      }
   }

   private void __pre_224(ProcessingContext var1) {
   }

   private void __post_224(ProcessingContext var1) throws SAXProcessorException {
      String var2 = Functions.value(var1);
      MethodParamsBean var3 = (MethodParamsBean)var1.getBoundObject("params");
      MethodBean var4 = (MethodBean)var1.getBoundObject("method");
      TransactionIsolationBean var5 = (TransactionIsolationBean)var1.getBoundObject("tiso");
      WeblogicEjbJarBean var6 = (WeblogicEjbJarBean)var1.getBoundObject("wlJar");
      var3.addMethodParam(var2);
   }

   private void __pre_164(ProcessingContext var1) {
      Object var2 = null;
      var1.addBoundObject(var2, "invalidationTarget");
   }

   private void __post_164(ProcessingContext var1) throws SAXProcessorException {
      InvalidationTargetBean var2 = (InvalidationTargetBean)var1.getBoundObject("invalidationTarget");
      EntityDescriptorBean var3 = (EntityDescriptorBean)var1.getBoundObject("entity");
      WLDD61Helper var4 = (WLDD61Helper)var1.getBoundObject("bd");
      WeblogicEnterpriseBeanBean var5 = (WeblogicEnterpriseBeanBean)var1.getBoundObject("wlBean");
      WeblogicEjbJarBean var6 = (WeblogicEjbJarBean)var1.getBoundObject("wlJar");
      this.addBoundObject(var3.getInvalidationTarget(), "invalidationTarget");
   }

   private void __pre_181(ProcessingContext var1) {
   }

   private void __post_181(ProcessingContext var1) throws SAXProcessorException, SAXValidationException {
      String var2 = Functions.value(var1);
      StatefulSessionCacheBean var3 = (StatefulSessionCacheBean)var1.getBoundObject("cache");
      StatefulSessionDescriptorBean var4 = (StatefulSessionDescriptorBean)var1.getBoundObject("sfsd");
      WLDD61Helper var5 = (WLDD61Helper)var1.getBoundObject("bd");
      WeblogicEnterpriseBeanBean var6 = (WeblogicEnterpriseBeanBean)var1.getBoundObject("wlBean");
      WeblogicEjbJarBean var7 = (WeblogicEjbJarBean)var1.getBoundObject("wlJar");
      if (var2.length() == 0) {
         throw new SAXValidationException("PAction[1627800613](.weblogic-ejb-jar.weblogic-enterprise-bean.stateful-session-descriptor.stateful-session-cache.max-beans-in-cache.) must be a non-empty string");
      } else {
         try {
            this.validatePositiveInteger(var2);
         } catch (Exception var9) {
            throw new SAXValidationException("Path " + var1.getPath() + ": " + var9.getMessage());
         }

         int var8 = Integer.parseInt(var2);
         var3.setMaxBeansInCache(var8);
      }
   }

   private void __pre_215(ProcessingContext var1) {
   }

   private void __post_215(ProcessingContext var1) throws SAXProcessorException, SAXValidationException {
      String var2 = Functions.value(var1);
      EjbReferenceDescriptionBean var3 = (EjbReferenceDescriptionBean)var1.getBoundObject("ejbLocalRefDesc");
      WLDD61Helper var4 = (WLDD61Helper)var1.getBoundObject("bd");
      WeblogicEnterpriseBeanBean var5 = (WeblogicEnterpriseBeanBean)var1.getBoundObject("wlBean");
      WeblogicEjbJarBean var6 = (WeblogicEjbJarBean)var1.getBoundObject("wlJar");
      if (var2.length() == 0) {
         throw new SAXValidationException("PAction[2065530879](.weblogic-ejb-jar.weblogic-enterprise-bean.reference-descriptor.ejb-local-reference-description.jndi-name.) must be a non-empty string");
      } else {
         var3.setJNDIName(var2);
      }
   }

   private void __pre_145(ProcessingContext var1) {
   }

   private void __post_145(ProcessingContext var1) throws SAXProcessorException, SAXValidationException {
      String var2 = Functions.value(var1);
      EntityCacheBean var3 = (EntityCacheBean)var1.getBoundObject("cache");
      EntityDescriptorBean var4 = (EntityDescriptorBean)var1.getBoundObject("entity");
      WLDD61Helper var5 = (WLDD61Helper)var1.getBoundObject("bd");
      WeblogicEnterpriseBeanBean var6 = (WeblogicEnterpriseBeanBean)var1.getBoundObject("wlBean");
      WeblogicEjbJarBean var7 = (WeblogicEjbJarBean)var1.getBoundObject("wlJar");
      if (var2.length() == 0) {
         throw new SAXValidationException("PAction[697960108](.weblogic-ejb-jar.weblogic-enterprise-bean.entity-descriptor.entity-cache.concurrency-strategy.) must be a non-empty string");
      } else if (!"readonly".equals(var2) && !"ReadOnly".equals(var2) && !"readonlyexclusive".equals(var2) && !"ReadOnlyExclusive".equals(var2) && !"exclusive".equals(var2) && !"Exclusive".equals(var2) && !"database".equals(var2) && !"Database".equals(var2)) {
         throw new SAXValidationException("PAction[697960108](.weblogic-ejb-jar.weblogic-enterprise-bean.entity-descriptor.entity-cache.concurrency-strategy.) must be one of the values: readonly,ReadOnly,readonlyexclusive,ReadOnlyExclusive,exclusive,Exclusive,database,Database");
      } else {
         var3.setConcurrencyStrategy(var2);
      }
   }

   private void __pre_169(ProcessingContext var1) {
      Object var2 = null;
      var1.addBoundObject(var2, "clustering");
   }

   private void __post_169(ProcessingContext var1) throws SAXProcessorException {
      StatelessClusteringBean var2 = (StatelessClusteringBean)var1.getBoundObject("clustering");
      StatelessSessionDescriptorBean var3 = (StatelessSessionDescriptorBean)var1.getBoundObject("slsd");
      WLDD61Helper var4 = (WLDD61Helper)var1.getBoundObject("bd");
      WeblogicEnterpriseBeanBean var5 = (WeblogicEnterpriseBeanBean)var1.getBoundObject("wlBean");
      WeblogicEjbJarBean var6 = (WeblogicEjbJarBean)var1.getBoundObject("wlJar");
      this.addBoundObject(var3.getStatelessClustering(), "clustering");
   }

   private void __pre_217(ProcessingContext var1) {
   }

   private void __post_217(ProcessingContext var1) throws SAXProcessorException, SAXValidationException {
      String var2 = Functions.value(var1);
      TransactionIsolationBean var3 = (TransactionIsolationBean)var1.getBoundObject("tiso");
      WeblogicEjbJarBean var4 = (WeblogicEjbJarBean)var1.getBoundObject("wlJar");
      if (var2.length() == 0) {
         throw new SAXValidationException("PAction[943010986](.weblogic-ejb-jar.transaction-isolation.isolation-level.) must be a non-empty string");
      } else if (!"TRANSACTION_SERIALIZABLE".equals(var2) && !"TRANSACTION_READ_COMMITTED".equals(var2) && !"TRANSACTION_READ_COMMITTED_FOR_UPDATE".equals(var2) && !"TRANSACTION_READ_UNCOMMITTED".equals(var2) && !"TRANSACTION_REPEATABLE_READ".equals(var2)) {
         throw new SAXValidationException("PAction[943010986](.weblogic-ejb-jar.transaction-isolation.isolation-level.) must be one of the values: TRANSACTION_SERIALIZABLE,TRANSACTION_READ_COMMITTED,TRANSACTION_READ_COMMITTED_FOR_UPDATE,TRANSACTION_READ_UNCOMMITTED,TRANSACTION_REPEATABLE_READ");
      } else {
         String var5 = var2;
         if ("TRANSACTION_SERIALIZABLE".equalsIgnoreCase(var2)) {
            var5 = "TransactionSerializable";
         } else if ("TRANSACTION_READ_COMMITTED".equalsIgnoreCase(var2)) {
            var5 = "TransactionReadCommitted";
         } else if ("TRANSACTION_READ_COMMITTED_FOR_UPDATE".equalsIgnoreCase(var2)) {
            var5 = "TransactionReadCommittedForUpdate";
         } else if ("TRANSACTION_READ_UNCOMMITTED".equalsIgnoreCase(var2)) {
            var5 = "TransactionReadUncommitted";
         } else if ("TRANSACTION_REPEATABLE_READ".equalsIgnoreCase(var2)) {
            var5 = "TransactionRepeatableRead";
         }

         var3.setIsolationLevel(var5);
      }
   }

   private void __pre_161(ProcessingContext var1) {
   }

   private void __post_161(ProcessingContext var1) throws SAXProcessorException, SAXValidationException {
      String var2 = Functions.value(var1);
      EntityClusteringBean var3 = (EntityClusteringBean)var1.getBoundObject("clustering");
      EntityDescriptorBean var4 = (EntityDescriptorBean)var1.getBoundObject("entity");
      WLDD61Helper var5 = (WLDD61Helper)var1.getBoundObject("bd");
      WeblogicEnterpriseBeanBean var6 = (WeblogicEnterpriseBeanBean)var1.getBoundObject("wlBean");
      WeblogicEjbJarBean var7 = (WeblogicEjbJarBean)var1.getBoundObject("wlJar");
      if (var2.length() == 0) {
         throw new SAXValidationException("PAction[1807837413](.weblogic-ejb-jar.weblogic-enterprise-bean.entity-descriptor.entity-clustering.home-load-algorithm.) must be a non-empty string");
      } else if (!"roundrobin".equals(var2) && !"RoundRobin".equals(var2) && !"round-robin".equals(var2) && !"random".equals(var2) && !"Random".equals(var2) && !"weightbased".equals(var2) && !"WeightBased".equals(var2) && !"weight-based".equals(var2)) {
         throw new SAXValidationException("PAction[1807837413](.weblogic-ejb-jar.weblogic-enterprise-bean.entity-descriptor.entity-clustering.home-load-algorithm.) must be one of the values: roundrobin,RoundRobin,round-robin,random,Random,weightbased,WeightBased,weight-based");
      } else {
         var3.setHomeLoadAlgorithm(var2);
      }
   }

   private void __pre_146(ProcessingContext var1) {
   }

   private void __post_146(ProcessingContext var1) throws SAXProcessorException, SAXValidationException {
      String var2 = Functions.value(var1);
      EntityCacheBean var3 = (EntityCacheBean)var1.getBoundObject("cache");
      EntityDescriptorBean var4 = (EntityDescriptorBean)var1.getBoundObject("entity");
      WLDD61Helper var5 = (WLDD61Helper)var1.getBoundObject("bd");
      WeblogicEnterpriseBeanBean var6 = (WeblogicEnterpriseBeanBean)var1.getBoundObject("wlBean");
      WeblogicEjbJarBean var7 = (WeblogicEjbJarBean)var1.getBoundObject("wlJar");
      if (var2.length() == 0) {
         throw new SAXValidationException("PAction[2066940133](.weblogic-ejb-jar.weblogic-enterprise-bean.entity-descriptor.entity-cache.read-timeout-seconds.) must be a non-empty string");
      } else {
         try {
            this.validatePositiveInteger(var2);
         } catch (Exception var9) {
            throw new SAXValidationException("Path " + var1.getPath() + ": " + var9.getMessage());
         }

         int var8 = Integer.parseInt(var2);
         var3.setReadTimeoutSeconds(var8);
      }
   }

   private void __pre_222(ProcessingContext var1) {
   }

   private void __post_222(ProcessingContext var1) throws SAXProcessorException, SAXValidationException {
      String var2 = Functions.value(var1);
      MethodBean var3 = (MethodBean)var1.getBoundObject("method");
      TransactionIsolationBean var4 = (TransactionIsolationBean)var1.getBoundObject("tiso");
      WeblogicEjbJarBean var5 = (WeblogicEjbJarBean)var1.getBoundObject("wlJar");
      if (var2.length() == 0) {
         throw new SAXValidationException("PAction[48612937](.weblogic-ejb-jar.transaction-isolation.method.method-name.) must be a non-empty string");
      } else {
         var3.setMethodName(var2);
      }
   }

   private void __pre_208(ProcessingContext var1) {
   }

   private void __post_208(ProcessingContext var1) throws SAXProcessorException, SAXValidationException {
      String var2 = Functions.value(var1);
      ResourceEnvDescriptionBean var3 = (ResourceEnvDescriptionBean)var1.getBoundObject("resEnvDesc");
      WLDD61Helper var4 = (WLDD61Helper)var1.getBoundObject("bd");
      WeblogicEnterpriseBeanBean var5 = (WeblogicEnterpriseBeanBean)var1.getBoundObject("wlBean");
      WeblogicEjbJarBean var6 = (WeblogicEjbJarBean)var1.getBoundObject("wlJar");
      if (var2.length() == 0) {
         throw new SAXValidationException("PAction[325333723](.weblogic-ejb-jar.weblogic-enterprise-bean.reference-descriptor.resource-env-description.res-env-ref-name.) must be a non-empty string");
      } else {
         var3.setResourceEnvRefName(var2);
      }
   }

   private void __pre_157(ProcessingContext var1) {
   }

   private void __post_157(ProcessingContext var1) throws SAXProcessorException, SAXValidationException {
      String var2 = Functions.value(var1);
      PersistenceUseBean var3 = (PersistenceUseBean)var1.getBoundObject("pu");
      PersistenceBean var4 = (PersistenceBean)var1.getBoundObject("persist");
      EntityDescriptorBean var5 = (EntityDescriptorBean)var1.getBoundObject("entity");
      WLDD61Helper var6 = (WLDD61Helper)var1.getBoundObject("bd");
      WeblogicEnterpriseBeanBean var7 = (WeblogicEnterpriseBeanBean)var1.getBoundObject("wlBean");
      WeblogicEjbJarBean var8 = (WeblogicEjbJarBean)var1.getBoundObject("wlJar");
      if (var2.length() == 0) {
         throw new SAXValidationException("PAction[1937962514](.weblogic-ejb-jar.weblogic-enterprise-bean.entity-descriptor.persistence.persistence-use.type-identifier.) must be a non-empty string");
      } else {
         var3.setTypeIdentifier(var2);
      }
   }

   private void __pre_147(ProcessingContext var1) {
      Object var2 = null;
      var1.addBoundObject(var2, "persist");
   }

   private void __post_147(ProcessingContext var1) throws SAXProcessorException {
      PersistenceBean var2 = (PersistenceBean)var1.getBoundObject("persist");
      EntityDescriptorBean var3 = (EntityDescriptorBean)var1.getBoundObject("entity");
      WLDD61Helper var4 = (WLDD61Helper)var1.getBoundObject("bd");
      WeblogicEnterpriseBeanBean var5 = (WeblogicEnterpriseBeanBean)var1.getBoundObject("wlBean");
      WeblogicEjbJarBean var6 = (WeblogicEjbJarBean)var1.getBoundObject("wlJar");
      this.addBoundObject(var3.getPersistence(), "persist");
   }

   private void __pre_139(ProcessingContext var1) {
      Object var2 = null;
      var1.addBoundObject(var2, "pool");
   }

   private void __post_139(ProcessingContext var1) throws SAXProcessorException {
      PoolBean var2 = (PoolBean)var1.getBoundObject("pool");
      EntityDescriptorBean var3 = (EntityDescriptorBean)var1.getBoundObject("entity");
      WLDD61Helper var4 = (WLDD61Helper)var1.getBoundObject("bd");
      WeblogicEnterpriseBeanBean var5 = (WeblogicEnterpriseBeanBean)var1.getBoundObject("wlBean");
      WeblogicEjbJarBean var6 = (WeblogicEjbJarBean)var1.getBoundObject("wlJar");
      this.addBoundObject(var3.getPool(), "pool");
   }

   private void __pre_176(ProcessingContext var1) {
   }

   private void __post_176(ProcessingContext var1) throws SAXProcessorException, SAXValidationException {
      String var2 = Functions.value(var1);
      StatelessClusteringBean var3 = (StatelessClusteringBean)var1.getBoundObject("clustering");
      StatelessSessionDescriptorBean var4 = (StatelessSessionDescriptorBean)var1.getBoundObject("slsd");
      WLDD61Helper var5 = (WLDD61Helper)var1.getBoundObject("bd");
      WeblogicEnterpriseBeanBean var6 = (WeblogicEnterpriseBeanBean)var1.getBoundObject("wlBean");
      WeblogicEjbJarBean var7 = (WeblogicEjbJarBean)var1.getBoundObject("wlJar");
      if (var2.length() == 0) {
         throw new SAXValidationException("PAction[274064559](.weblogic-ejb-jar.weblogic-enterprise-bean.stateless-session-descriptor.stateless-clustering.stateless-bean-load-algorithm.) must be a non-empty string");
      } else if (!"roundrobin".equals(var2) && !"RoundRobin".equals(var2) && !"round-robin".equals(var2) && !"random".equals(var2) && !"Random".equals(var2) && !"weightbased".equals(var2) && !"WeightBased".equals(var2) && !"weight-based".equals(var2)) {
         throw new SAXValidationException("PAction[274064559](.weblogic-ejb-jar.weblogic-enterprise-bean.stateless-session-descriptor.stateless-clustering.stateless-bean-load-algorithm.) must be one of the values: roundrobin,RoundRobin,round-robin,random,Random,weightbased,WeightBased,weight-based");
      } else {
         var3.setStatelessBeanLoadAlgorithm(var2);
      }
   }

   private void __pre_191(ProcessingContext var1) {
   }

   private void __post_191(ProcessingContext var1) throws SAXProcessorException, SAXValidationException {
      String var2 = Functions.value(var1);
      StatefulSessionClusteringBean var3 = (StatefulSessionClusteringBean)var1.getBoundObject("clustering");
      StatefulSessionDescriptorBean var4 = (StatefulSessionDescriptorBean)var1.getBoundObject("sfsd");
      WLDD61Helper var5 = (WLDD61Helper)var1.getBoundObject("bd");
      WeblogicEnterpriseBeanBean var6 = (WeblogicEnterpriseBeanBean)var1.getBoundObject("wlBean");
      WeblogicEjbJarBean var7 = (WeblogicEjbJarBean)var1.getBoundObject("wlJar");
      if (var2.length() == 0) {
         throw new SAXValidationException("PAction[1018081122](.weblogic-ejb-jar.weblogic-enterprise-bean.stateful-session-descriptor.stateful-session-clustering.use-serverside-stubs.) must be a non-empty string");
      } else if (!"true".equals(var2) && !"True".equals(var2) && !"false".equals(var2) && !"False".equals(var2)) {
         throw new SAXValidationException("PAction[1018081122](.weblogic-ejb-jar.weblogic-enterprise-bean.stateful-session-descriptor.stateful-session-clustering.use-serverside-stubs.) must be one of the values: true,True,false,False");
      } else {
         var3.setUseServersideStubs("True".equalsIgnoreCase(var2));
      }
   }

   private void __pre_205(ProcessingContext var1) {
   }

   private void __post_205(ProcessingContext var1) throws SAXProcessorException, SAXValidationException {
      String var2 = Functions.value(var1);
      ResourceDescriptionBean var3 = (ResourceDescriptionBean)var1.getBoundObject("resDesc");
      WLDD61Helper var4 = (WLDD61Helper)var1.getBoundObject("bd");
      WeblogicEnterpriseBeanBean var5 = (WeblogicEnterpriseBeanBean)var1.getBoundObject("wlBean");
      WeblogicEjbJarBean var6 = (WeblogicEjbJarBean)var1.getBoundObject("wlJar");
      if (var2.length() == 0) {
         throw new SAXValidationException("PAction[242131142](.weblogic-ejb-jar.weblogic-enterprise-bean.reference-descriptor.resource-description.res-ref-name.) must be a non-empty string");
      } else {
         var3.setResRefName(var2);
      }
   }

   private void __pre_136(ProcessingContext var1) {
   }

   private void __post_136(ProcessingContext var1) throws SAXProcessorException, SAXValidationException {
      String var2 = Functions.value(var1);
      WLDD61Helper var3 = (WLDD61Helper)var1.getBoundObject("bd");
      WeblogicEnterpriseBeanBean var4 = (WeblogicEnterpriseBeanBean)var1.getBoundObject("wlBean");
      WeblogicEjbJarBean var5 = (WeblogicEjbJarBean)var1.getBoundObject("wlJar");
      if (var2.length() == 0) {
         throw new SAXValidationException("PAction[1782113663](.weblogic-ejb-jar.weblogic-enterprise-bean.jndi-name.) must be a non-empty string");
      } else {
         JndiBindingBean var6 = var4.lookupJndiBinding("_WL_HOME");
         if (var6 == null) {
            var6 = var4.createJndiBinding();
            var6.setClassName("_WL_HOME");
         }

         var6.setJndiName(var2);
      }
   }

   private void __pre_141(ProcessingContext var1) {
   }

   private void __post_141(ProcessingContext var1) throws SAXProcessorException, SAXValidationException {
      String var2 = Functions.value(var1);
      PoolBean var3 = (PoolBean)var1.getBoundObject("pool");
      EntityDescriptorBean var4 = (EntityDescriptorBean)var1.getBoundObject("entity");
      WLDD61Helper var5 = (WLDD61Helper)var1.getBoundObject("bd");
      WeblogicEnterpriseBeanBean var6 = (WeblogicEnterpriseBeanBean)var1.getBoundObject("wlBean");
      WeblogicEjbJarBean var7 = (WeblogicEjbJarBean)var1.getBoundObject("wlJar");
      if (var2.length() == 0) {
         throw new SAXValidationException("PAction[1433867275](.weblogic-ejb-jar.weblogic-enterprise-bean.entity-descriptor.pool.initial-beans-in-free-pool.) must be a non-empty string");
      } else {
         try {
            this.validatePositiveInteger(var2);
         } catch (Exception var9) {
            throw new SAXValidationException("Path " + var1.getPath() + ": " + var9.getMessage());
         }

         int var8 = Integer.parseInt(var2);
         var3.setInitialBeansInFreePool(var8);
      }
   }

   public void addBoundObject(Object var1, String var2) {
      this.driver.currentNode().addBoundObject(var1, var2);
   }

   static {
      paths.put(".weblogic-ejb-jar.weblogic-enterprise-bean.run-as-identity-principal.", new Integer(135));
      paths.put(".weblogic-ejb-jar.weblogic-enterprise-bean.stateful-session-descriptor.stateful-session-clustering.replication-type.", new Integer(190));
      paths.put(".weblogic-ejb-jar.security-role-assignment.principal-name.", new Integer(227));
      paths.put(".weblogic-ejb-jar.weblogic-enterprise-bean.entity-descriptor.entity-clustering.use-serverside-stubs.", new Integer(163));
      paths.put(".weblogic-ejb-jar.weblogic-enterprise-bean.message-driven-descriptor.pool.max-beans-in-free-pool.", new Integer(197));
      paths.put(".weblogic-ejb-jar.transaction-isolation.method.ejb-name.", new Integer(220));
      paths.put(".weblogic-ejb-jar.weblogic-enterprise-bean.stateful-session-descriptor.stateful-session-cache.session-timeout-seconds.", new Integer(183));
      paths.put(".weblogic-ejb-jar.weblogic-enterprise-bean.message-driven-descriptor.provider-url.", new Integer(200));
      paths.put(".weblogic-ejb-jar.weblogic-enterprise-bean.stateless-session-descriptor.", new Integer(166));
      paths.put(".weblogic-ejb-jar.weblogic-enterprise-bean.message-driven-descriptor.jms-polling-interval-seconds.", new Integer(193));
      paths.put(".weblogic-ejb-jar.weblogic-enterprise-bean.clients-on-same-server.", new Integer(134));
      paths.put(".weblogic-ejb-jar.weblogic-enterprise-bean.entity-descriptor.persistence.persistence-use.type-version.", new Integer(158));
      paths.put(".weblogic-ejb-jar.weblogic-enterprise-bean.entity-descriptor.entity-cache.idle-timeout-seconds.", new Integer(144));
      paths.put(".weblogic-ejb-jar.weblogic-enterprise-bean.entity-descriptor.entity-clustering.home-is-clusterable.", new Integer(160));
      paths.put(".weblogic-ejb-jar.weblogic-enterprise-bean.dispatch-policy.", new Integer(133));
      paths.put(".weblogic-ejb-jar.transaction-isolation.method.method-params.", new Integer(223));
      paths.put(".weblogic-ejb-jar.weblogic-enterprise-bean.stateful-session-descriptor.stateful-session-clustering.home-call-router-class-name.", new Integer(189));
      paths.put(".weblogic-ejb-jar.weblogic-enterprise-bean.stateful-session-descriptor.stateful-session-clustering.home-load-algorithm.", new Integer(188));
      paths.put(".weblogic-ejb-jar.security-role-assignment.", new Integer(225));
      paths.put(".weblogic-ejb-jar.description.", new Integer(129));
      paths.put(".weblogic-ejb-jar.weblogic-enterprise-bean.entity-descriptor.persistence.persistence-use.", new Integer(156));
      paths.put(".weblogic-ejb-jar.weblogic-enterprise-bean.stateless-session-descriptor.pool.", new Integer(167));
      paths.put(".weblogic-ejb-jar.transaction-isolation.method.", new Integer(218));
      paths.put(".weblogic-ejb-jar.weblogic-enterprise-bean.entity-descriptor.invalidation-target.ejb-name.", new Integer(165));
      paths.put(".weblogic-ejb-jar.security-role-assignment.role-name.", new Integer(226));
      paths.put(".weblogic-ejb-jar.transaction-isolation.", new Integer(216));
      paths.put(".weblogic-ejb-jar.weblogic-enterprise-bean.message-driven-descriptor.pool.", new Integer(196));
      paths.put(".weblogic-ejb-jar.transaction-isolation.method.method-intf.", new Integer(221));
      paths.put(".weblogic-ejb-jar.weblogic-enterprise-bean.entity-descriptor.entity-clustering.", new Integer(159));
      paths.put(".weblogic-ejb-jar.weblogic-enterprise-bean.entity-descriptor.persistence.is-modified-method-name.", new Integer(148));
      paths.put(".weblogic-ejb-jar.weblogic-enterprise-bean.reference-descriptor.resource-description.", new Integer(204));
      paths.put(".weblogic-ejb-jar.weblogic-enterprise-bean.entity-descriptor.persistence.delay-updates-until-end-of-tx.", new Integer(149));
      paths.put(".weblogic-ejb-jar.weblogic-enterprise-bean.stateless-session-descriptor.stateless-clustering.use-serverside-stubs.", new Integer(173));
      paths.put(".weblogic-ejb-jar.weblogic-enterprise-bean.entity-descriptor.entity-cache.", new Integer(142));
      paths.put(".weblogic-ejb-jar.weblogic-enterprise-bean.stateless-session-descriptor.pool.initial-beans-in-free-pool.", new Integer(175));
      paths.put(".weblogic-ejb-jar.weblogic-enterprise-bean.message-driven-descriptor.jms-client-id.", new Integer(194));
      paths.put(".weblogic-ejb-jar.weblogic-enterprise-bean.transaction-descriptor.", new Integer(202));
      paths.put(".weblogic-ejb-jar.weblogic-enterprise-bean.entity-descriptor.persistence.persistence-type.type-identifier.", new Integer(153));
      paths.put(".weblogic-ejb-jar.weblogic-enterprise-bean.stateless-session-descriptor.stateless-clustering.stateless-bean-methods-are-idempotent.", new Integer(178));
      paths.put(".weblogic-ejb-jar.weblogic-enterprise-bean.message-driven-descriptor.pool.initial-beans-in-free-pool.", new Integer(198));
      paths.put(".weblogic-ejb-jar.weblogic-enterprise-bean.transaction-descriptor.trans-timeout-seconds.", new Integer(203));
      paths.put(".weblogic-ejb-jar.weblogic-enterprise-bean.entity-descriptor.persistence.persistence-type.type-version.", new Integer(154));
      paths.put(".weblogic-ejb-jar.weblogic-enterprise-bean.reference-descriptor.ejb-reference-description.", new Integer(210));
      paths.put(".weblogic-ejb-jar.weblogic-enterprise-bean.stateful-session-descriptor.stateful-session-clustering.home-is-clusterable.", new Integer(187));
      paths.put(".weblogic-ejb-jar.weblogic-enterprise-bean.message-driven-descriptor.destination-jndi-name.", new Integer(195));
      paths.put(".weblogic-ejb-jar.weblogic-enterprise-bean.reference-descriptor.resource-description.jndi-name.", new Integer(206));
      paths.put(".weblogic-ejb-jar.weblogic-enterprise-bean.reference-descriptor.ejb-local-reference-description.", new Integer(213));
      paths.put(".weblogic-ejb-jar.weblogic-enterprise-bean.entity-descriptor.entity-cache.max-beans-in-cache.", new Integer(143));
      paths.put(".weblogic-ejb-jar.weblogic-enterprise-bean.stateless-session-descriptor.stateless-clustering.home-load-algorithm.", new Integer(172));
      paths.put(".weblogic-ejb-jar.weblogic-enterprise-bean.stateful-session-descriptor.stateful-session-cache.", new Integer(180));
      paths.put(".weblogic-ejb-jar.weblogic-enterprise-bean.message-driven-descriptor.connection-factory-jndi-name.", new Integer(201));
      paths.put(".weblogic-ejb-jar.weblogic-enterprise-bean.", new Integer(130));
      paths.put(".weblogic-ejb-jar.weblogic-enterprise-bean.local-jndi-name.", new Integer(137));
      paths.put(".weblogic-ejb-jar.weblogic-enterprise-bean.stateful-session-descriptor.stateful-session-cache.cache-type.", new Integer(184));
      paths.put(".weblogic-ejb-jar.weblogic-enterprise-bean.entity-descriptor.persistence.finders-load-bean.", new Integer(150));
      paths.put(".weblogic-ejb-jar.weblogic-enterprise-bean.stateful-session-descriptor.stateful-session-cache.idle-timeout-seconds.", new Integer(182));
      paths.put(".weblogic-ejb-jar.weblogic-enterprise-bean.stateful-session-descriptor.persistent-store-dir.", new Integer(185));
      paths.put(".weblogic-ejb-jar.", new Integer(128));
      paths.put(".weblogic-ejb-jar.weblogic-enterprise-bean.entity-descriptor.persistence.persistence-type.", new Integer(152));
      paths.put(".weblogic-ejb-jar.weblogic-enterprise-bean.stateful-session-descriptor.stateful-session-clustering.", new Integer(186));
      paths.put(".weblogic-ejb-jar.transaction-isolation.method.description.", new Integer(219));
      paths.put(".weblogic-ejb-jar.weblogic-enterprise-bean.message-driven-descriptor.initial-context-factory.", new Integer(199));
      paths.put(".weblogic-ejb-jar.weblogic-enterprise-bean.ejb-name.", new Integer(131));
      paths.put(".weblogic-ejb-jar.weblogic-enterprise-bean.entity-descriptor.", new Integer(138));
      paths.put(".weblogic-ejb-jar.weblogic-enterprise-bean.entity-descriptor.entity-clustering.home-call-router-class-name.", new Integer(162));
      paths.put(".weblogic-ejb-jar.weblogic-enterprise-bean.reference-descriptor.ejb-reference-description.jndi-name.", new Integer(212));
      paths.put(".weblogic-ejb-jar.weblogic-enterprise-bean.stateless-session-descriptor.stateless-clustering.stateless-bean-is-clusterable.", new Integer(174));
      paths.put(".weblogic-ejb-jar.weblogic-enterprise-bean.reference-descriptor.resource-env-description.", new Integer(207));
      paths.put(".weblogic-ejb-jar.weblogic-enterprise-bean.enable-call-by-reference.", new Integer(132));
      paths.put(".weblogic-ejb-jar.weblogic-enterprise-bean.stateless-session-descriptor.stateless-clustering.home-is-clusterable.", new Integer(170));
      paths.put(".weblogic-ejb-jar.weblogic-enterprise-bean.message-driven-descriptor.", new Integer(192));
      paths.put(".weblogic-ejb-jar.weblogic-enterprise-bean.stateless-session-descriptor.stateless-clustering.home-call-router-class-name.", new Integer(171));
      paths.put(".weblogic-ejb-jar.weblogic-enterprise-bean.stateless-session-descriptor.stateless-clustering.stateless-bean-call-router-class-name.", new Integer(177));
      paths.put(".weblogic-ejb-jar.weblogic-enterprise-bean.reference-descriptor.ejb-local-reference-description.ejb-ref-name.", new Integer(214));
      paths.put(".weblogic-ejb-jar.weblogic-enterprise-bean.stateless-session-descriptor.pool.max-beans-in-free-pool.", new Integer(168));
      paths.put(".weblogic-ejb-jar.weblogic-enterprise-bean.reference-descriptor.resource-env-description.jndi-name.", new Integer(209));
      paths.put(".weblogic-ejb-jar.weblogic-enterprise-bean.entity-descriptor.pool.max-beans-in-free-pool.", new Integer(140));
      paths.put(".weblogic-ejb-jar.weblogic-enterprise-bean.reference-descriptor.ejb-reference-description.ejb-ref-name.", new Integer(211));
      paths.put(".weblogic-ejb-jar.weblogic-enterprise-bean.entity-descriptor.persistence.db-is-shared.", new Integer(151));
      paths.put(".weblogic-ejb-jar.weblogic-enterprise-bean.stateful-session-descriptor.", new Integer(179));
      paths.put(".weblogic-ejb-jar.weblogic-enterprise-bean.entity-descriptor.persistence.persistence-type.type-storage.", new Integer(155));
      paths.put(".weblogic-ejb-jar.transaction-isolation.method.method-params.method-param.", new Integer(224));
      paths.put(".weblogic-ejb-jar.weblogic-enterprise-bean.entity-descriptor.invalidation-target.", new Integer(164));
      paths.put(".weblogic-ejb-jar.weblogic-enterprise-bean.stateful-session-descriptor.stateful-session-cache.max-beans-in-cache.", new Integer(181));
      paths.put(".weblogic-ejb-jar.weblogic-enterprise-bean.reference-descriptor.ejb-local-reference-description.jndi-name.", new Integer(215));
      paths.put(".weblogic-ejb-jar.weblogic-enterprise-bean.entity-descriptor.entity-cache.concurrency-strategy.", new Integer(145));
      paths.put(".weblogic-ejb-jar.weblogic-enterprise-bean.stateless-session-descriptor.stateless-clustering.", new Integer(169));
      paths.put(".weblogic-ejb-jar.transaction-isolation.isolation-level.", new Integer(217));
      paths.put(".weblogic-ejb-jar.weblogic-enterprise-bean.entity-descriptor.entity-clustering.home-load-algorithm.", new Integer(161));
      paths.put(".weblogic-ejb-jar.weblogic-enterprise-bean.entity-descriptor.entity-cache.read-timeout-seconds.", new Integer(146));
      paths.put(".weblogic-ejb-jar.transaction-isolation.method.method-name.", new Integer(222));
      paths.put(".weblogic-ejb-jar.weblogic-enterprise-bean.reference-descriptor.resource-env-description.res-env-ref-name.", new Integer(208));
      paths.put(".weblogic-ejb-jar.weblogic-enterprise-bean.entity-descriptor.persistence.persistence-use.type-identifier.", new Integer(157));
      paths.put(".weblogic-ejb-jar.weblogic-enterprise-bean.entity-descriptor.persistence.", new Integer(147));
      paths.put(".weblogic-ejb-jar.weblogic-enterprise-bean.entity-descriptor.pool.", new Integer(139));
      paths.put(".weblogic-ejb-jar.weblogic-enterprise-bean.stateless-session-descriptor.stateless-clustering.stateless-bean-load-algorithm.", new Integer(176));
      paths.put(".weblogic-ejb-jar.weblogic-enterprise-bean.stateful-session-descriptor.stateful-session-clustering.use-serverside-stubs.", new Integer(191));
      paths.put(".weblogic-ejb-jar.weblogic-enterprise-bean.reference-descriptor.resource-description.res-ref-name.", new Integer(205));
      paths.put(".weblogic-ejb-jar.weblogic-enterprise-bean.jndi-name.", new Integer(136));
      paths.put(".weblogic-ejb-jar.weblogic-enterprise-bean.entity-descriptor.pool.initial-beans-in-free-pool.", new Integer(141));
   }
}
