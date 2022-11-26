package weblogic.deploy.api.spi.config;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.ref.SoftReference;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLStreamException;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Element;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import weblogic.application.descriptor.NamespaceURIMunger;
import weblogic.core.base.api.CapabilitiesService;
import weblogic.deploy.api.internal.utils.ConfigHelperLowLevel;
import weblogic.deploy.api.internal.utils.Debug;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.EditableDescriptorManager;
import weblogic.j2ee.descriptor.wl.DeploymentPlanBean;
import weblogic.management.ManagementException;
import weblogic.server.GlobalServiceLocator;
import weblogic.utils.XXEUtils;
import weblogic.utils.io.StreamUtils;
import weblogic.utils.io.UnsyncByteArrayInputStream;

public class DescriptorParser {
   private static final boolean ddebug = Debug.isDebug("utils");
   private static final String VERSION = "version";
   private Document doc = null;
   private final ByteArrayCache ddStreamCache;
   private String version = null;
   private boolean hasSchema = true;
   private static EditableDescriptorManager edm = null;
   private static final String DEFAULT_ENCODING = "UTF-8";
   private DescriptorSupport ds;
   private DeploymentPlanBean plan = null;
   private File configDir = null;
   File ddFile = null;
   private static Map map = new HashMap();
   private static final String CONNECTOR10_RAR_PUBLIC_ID = "-//Sun Microsystems, Inc.//DTD Connector 1.0//EN";
   private static final String SERVLET_JAR_PUBLIC_ID = "-//Sun Microsystems, Inc.//DTD Web Application 1.2//EN";
   private static final String SERVLET_JAR_PUBLIC_ID2 = "-//Sun Microsystems, Inc.//DTD Web Application 2.2//EN";
   private static final String SERVLET_JAR_PUBLIC_ID3 = "-//Sun Microsystems, Inc.//DTD Web Application 2.3//EN";
   private static final String J2EE12_EAR_PUBLIC_ID = "-//Sun Microsystems, Inc.//DTD J2EE Application 1.2//EN";
   private static final String J2EE13_EAR_PUBLIC_ID = "-//Sun Microsystems, Inc.//DTD J2EE Application 1.3//EN";
   private static final String APPCLIENT_SUN_PUBLIC_ID = "-//Sun Microsystems, Inc.//DTD J2EE Application Client 1.3//EN";
   private static final String APPCLIENT_SUN_PUBLIC_ID12 = "-//Sun Microsystems, Inc.//DTD J2EE Application Client 1.2//EN";
   private static final String EJB11_JAR_PUBLIC_ID = "-//Sun Microsystems, Inc.//DTD Enterprise JavaBeans 1.1//EN";
   private static final String EJB11_JAR_LOCAL_DTD_LOCATION = "/weblogic/ejb/container/dd/xml/ejb11-jar.dtd";
   private static final String EJB20_JAR_LOCAL_DTD_LOCATION = "/weblogic/ejb/container/dd/xml/ejb20-jar.dtd";
   public static final String WLS510_EJB_JAR_PUBLIC_ID = "-//BEA Systems, Inc.//DTD WebLogic 5.1.0 EJB//EN";
   private static final String EJB20_JAR_PUBLIC_ID = "-//Sun Microsystems, Inc.//DTD Enterprise JavaBeans 2.0//EN";
   private static final String DATA_FILE_DOCTYPE_6_0 = "-//BEA Systems, Inc.//DTD WebLogic 6.0.0 EJB RDBMS Persistence//EN";
   private static final String DATA_FILE_LOCAL_DTD_LOCATION_6_0 = "/weblogic/ejb/container/cmp/rdbms/weblogic-rdbms20-persistence-600.dtd";
   private static final String DATA_FILE_DOCTYPE_7_0 = "-//BEA Systems, Inc.//DTD WebLogic 7.0.0 EJB RDBMS Persistence//EN";
   private static final String DATA_FILE_LOCAL_DTD_LOCATION_7_0 = "/weblogic/ejb/container/cmp/rdbms/weblogic-rdbms20-persistence-700.dtd";
   private static final String DATA_FILE_DOCTYPE_8_1 = "-//BEA Systems, Inc.//DTD WebLogic 8.1.0 EJB RDBMS Persistence//EN";
   private static final String DATA_FILE_LOCAL_DTD_LOCATION_8_1 = "/weblogic/ejb/container/cmp/rdbms/weblogic-rdbms20-persistence-810.dtd";
   private static final String DATA_FILE_CMP11_DOCTYPE_5_1 = "-//BEA Systems, Inc.//DTD WebLogic 5.1.0 EJB RDBMS Persistence//EN";
   private static final String DATA_FILE_CMP11_LOCAL_DTD_LOCATION_5_1 = "/weblogic/ejb/container/cmp11/rdbms/weblogic-rdbms-persistence.dtd";
   public static final String DATA_FILE_CMP11_DOCTYPE_6_0 = "-//BEA Systems, Inc.//DTD WebLogic 6.0.0 EJB 1.1 RDBMS Persistence//EN";
   private static final String DATA_FILE_CMP11_LOCAL_DTD_LOCATION_6_0 = "/weblogic/ejb/container/cmp11/rdbms/weblogic-rdbms-persistence.dtd";
   public static final Map locations = new HashMap();

   public DescriptorSupport getDescriptorSupport() {
      return this.ds;
   }

   private DescriptorParser(InputStream dd, DeploymentPlanBean plan, DescriptorSupport ds) throws IOException {
      this.plan = plan;
      if (dd == null) {
         throw new IOException("Valid Stream is required for descriptor");
      } else {
         this.ddStreamCache = new ByteArrayCache(dd);
         this.ds = ds;
         this.checkDocType();
         this.configDir = ConfigHelperLowLevel.getConfigRootFile(plan);
      }
   }

   public final InputStream getDDStream() {
      return this.ddStreamCache.getInputStream();
   }

   private static void init() {
      if (edm == null) {
         edm = new EditableDescriptorManager();
         edm.setProductionMode(((CapabilitiesService)GlobalServiceLocator.getServiceLocator().getService(CapabilitiesService.class, new Annotation[0])).isProductionMode());
      }
   }

   public static DescriptorParser getDescriptorParser(InputStream is, DeploymentPlanBean plan, DescriptorSupport ds) throws IOException {
      return new DescriptorParser(is, plan, ds);
   }

   public static DescriptorBean getWLSEditableDescriptorBean(Class beanClass) {
      return edm.createDescriptorRoot(beanClass, "UTF-8").getRootBean();
   }

   public static DeploymentPlanBean parseDeploymentPlan(InputStream is) throws IOException {
      try {
         String[] oldNamespaceURIs = new String[]{"http://www.bea.com/ns/weblogic/90", "http://www.bea.com/ns/weblogic/deployment-plan"};
         return (DeploymentPlanBean)edm.createDescriptor(new NamespaceURIMunger(is, "http://xmlns.oracle.com/weblogic/deployment-plan", oldNamespaceURIs), false).getRootBean();
      } catch (XMLStreamException var2) {
         throw new IOException(var2.getMessage());
      }
   }

   public Document getDocument() throws IOException {
      if (this.doc != null) {
         return this.doc;
      } else {
         Exception ex = null;
         InputStream is = null;

         try {
            is = this.getDDStream();
            DocumentBuilderFactory dbfactory = XXEUtils.createDocumentBuilderFactoryInstance();
            if (dbfactory != null) {
               InputSource inputsource = new InputSource(is);
               dbfactory.setNamespaceAware(true);
               dbfactory.setCoalescing(true);
               dbfactory.setIgnoringElementContentWhitespace(true);
               dbfactory.setValidating(false);
               DocumentBuilder db = dbfactory.newDocumentBuilder();
               db.setEntityResolver(new MyEntityResolver());
               this.doc = db.parse(inputsource);
               this.setDocumentVersion(this.doc);
            }
         } catch (SAXException var16) {
            ex = var16;
         } catch (ParserConfigurationException var17) {
            ex = var17;
         } finally {
            if (is != null) {
               try {
                  is.close();
               } catch (IOException var15) {
               }
            }

         }

         if (ex != null) {
            if (ddebug) {
               ((Exception)ex).printStackTrace();
            }

            Throwable t = ManagementException.unWrapExceptions((Throwable)ex);
            IOException e2 = new IOException(t.toString());
            e2.initCause(t);
            throw e2;
         } else {
            return this.doc;
         }
      }
   }

   private void setDocumentVersion(Document doc) {
      DocumentType dt = doc.getDoctype();
      if (dt != null) {
         String label = dt.getPublicId();
         if (label != null) {
            int endx = label.lastIndexOf("//");

            int sndx;
            for(sndx = endx; label.charAt(sndx) != ' ' && sndx != 0; --sndx) {
            }

            if (sndx != 0) {
               this.version = label.substring(sndx + 1, endx);
            }
         }
      } else {
         Element root = doc.getDocumentElement();
         this.version = root.getAttribute("version");
      }

      if (ddebug) {
         Debug.say("doc version set to " + this.version + ". isSchema: " + this.hasSchema);
      }

   }

   public String getDocumentVersion() {
      return this.version;
   }

   public boolean isSchemaBased() {
      return this.hasSchema;
   }

   private void checkDocType() throws IOException {
      InputStream is = null;

      try {
         is = this.getDDStream();
         byte[] buf = new byte[1024];
         is.read(buf, 0, 1024);
         String buff = new String(buf);
         if (buff.indexOf("DOCTYPE") != -1) {
            this.hasSchema = false;
         }
      } finally {
         if (is != null) {
            try {
               is.close();
            } catch (IOException var9) {
            }
         }

      }

   }

   public DeploymentPlanBean getDeploymentPlan() {
      return this.plan;
   }

   public File getConfigDir() {
      return this.configDir;
   }

   private static byte[] readStream(InputStream in) throws IOException {
      if (in == null) {
         return null;
      } else {
         byte[] var3;
         try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            StreamUtils.writeTo(in, baos);
            byte[] toBytes = baos.toByteArray();
            if (ddebug) {
               Debug.say(" +++ getBytes = " + new String(toBytes));
            }

            var3 = toBytes;
         } finally {
            try {
               if (in != null) {
                  in.close();
               }
            } catch (IOException var10) {
            }

         }

         return var3;
      }
   }

   static {
      locations.put("-//Sun Microsystems, Inc.//DTD Enterprise JavaBeans 1.1//EN", "/weblogic/ejb/container/dd/xml/ejb11-jar.dtd");
      locations.put("-//Sun Microsystems, Inc.//DTD Enterprise JavaBeans 2.0//EN", "/weblogic/ejb/container/dd/xml/ejb20-jar.dtd");
      locations.put("-//BEA Systems, Inc.//DTD WebLogic 6.0.0 EJB RDBMS Persistence//EN", "/weblogic/ejb/container/cmp/rdbms/weblogic-rdbms20-persistence-600.dtd");
      locations.put("-//BEA Systems, Inc.//DTD WebLogic 7.0.0 EJB RDBMS Persistence//EN", "/weblogic/ejb/container/cmp/rdbms/weblogic-rdbms20-persistence-700.dtd");
      locations.put("-//BEA Systems, Inc.//DTD WebLogic 8.1.0 EJB RDBMS Persistence//EN", "/weblogic/ejb/container/cmp/rdbms/weblogic-rdbms20-persistence-810.dtd");
      locations.put("-//BEA Systems, Inc.//DTD WebLogic 5.1.0 EJB RDBMS Persistence//EN", "/weblogic/ejb/container/cmp11/rdbms/weblogic-rdbms-persistence.dtd");
      locations.put("-//BEA Systems, Inc.//DTD WebLogic 6.0.0 EJB 1.1 RDBMS Persistence//EN", "/weblogic/ejb/container/cmp11/rdbms/weblogic-rdbms-persistence.dtd");
      map.putAll(locations);
      map.put("-//Sun Microsystems, Inc.//DTD Connector 1.0//EN", "/weblogic/connector/configuration/connector_1_0.dtd");
      map.put("-//Sun Microsystems, Inc.//DTD J2EE Application Client 1.3//EN", "/weblogic/j2eeclient/application-client_1_3.dtd");
      map.put("-//Sun Microsystems, Inc.//DTD J2EE Application Client 1.2//EN", "/weblogic/j2eeclient/application-client_1_2.dtd");
      map.put("-//Sun Microsystems, Inc.//DTD J2EE Application 1.2//EN", "/weblogic/j2ee/dd/xml/application_1_2.dtd");
      map.put("-//Sun Microsystems, Inc.//DTD J2EE Application 1.3//EN", "/weblogic/j2ee/dd/xml/application_1_3.dtd");
      map.put("-//Sun Microsystems, Inc.//DTD Web Application 1.2//EN", "/weblogic/servlet/internal/dd/web-jar.dtd");
      map.put("-//Sun Microsystems, Inc.//DTD Web Application 2.2//EN", "/weblogic/servlet/internal/dd/web-jar.dtd");
      map.put("-//Sun Microsystems, Inc.//DTD Web Application 2.3//EN", "/weblogic/servlet/internal/dd/web-jar-23.dtd");

      try {
         init();
      } catch (Exception var1) {
         if (ddebug) {
            var1.printStackTrace();
         }

         throw new RuntimeException(var1.toString(), var1);
      }
   }

   private static class ByteArrayCache {
      private final ByteBuffer byteBuffer;
      private final int size;
      private SoftReference cache;

      ByteArrayCache(byte[] bytes) {
         this.size = bytes.length;
         this.byteBuffer = ByteBuffer.allocateDirect(this.size);
         this.byteBuffer.put(bytes);
         this.cache = new SoftReference(bytes);
      }

      ByteArrayCache(InputStream stream) throws IOException {
         this(DescriptorParser.readStream(stream));
      }

      InputStream getInputStream() {
         return new UnsyncByteArrayInputStream(this.getBytes());
      }

      byte[] getBytes() {
         byte[] bytes = (byte[])((byte[])this.cache.get());
         if (bytes != null) {
            return bytes;
         } else {
            bytes = new byte[this.size];
            this.byteBuffer.rewind();
            this.byteBuffer.get(bytes, 0, this.size);
            this.cache = new SoftReference(bytes);
            return bytes;
         }
      }

      long length() {
         return (long)this.size;
      }
   }

   public class MyEntityResolver implements EntityResolver {
      public InputSource resolveEntity(String publicId, String systemId) {
         InputSource dtd = null;
         String localDtd = (String)DescriptorParser.map.get(publicId);
         if (DescriptorParser.ddebug) {
            Debug.say("local dtd for " + publicId + " is " + localDtd);
         }

         if (localDtd != null) {
            InputStream is = this.getClass().getResourceAsStream(localDtd);
            if (is == null) {
               localDtd = localDtd.substring(1);
               if (DescriptorParser.ddebug) {
                  Debug.say("now trying " + localDtd);
               }

               is = this.getClass().getResourceAsStream(localDtd);
            }

            if (is != null) {
               dtd = new InputSource(is);
            }
         }

         return dtd;
      }
   }
}
