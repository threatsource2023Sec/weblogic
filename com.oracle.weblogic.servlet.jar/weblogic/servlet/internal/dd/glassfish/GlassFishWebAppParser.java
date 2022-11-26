package weblogic.servlet.internal.dd.glassfish;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import weblogic.descriptor.DescriptorManager;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.j2ee.descriptor.wl.WeblogicWebAppBean;
import weblogic.utils.jars.VirtualJarFile;
import weblogic.xml.stax.XMLStreamInputFactory;

public class GlassFishWebAppParser {
   private static final String GLASSFISH_DD = "WEB-INF/glassfish-web.xml";
   private static final String SUNWEB_DD = "WEB-INF/sun-web.xml";
   private static final DebugLogger DEBUG = DebugLogger.getDebugLogger("GlassFishWebAppParser");
   private InputStream descriptorInputStream;
   private Map parserMap = new HashMap();

   private GlassFishWebAppParser(InputStream inputStream) {
      this.descriptorInputStream = inputStream;
      this.parserMap.put("context-root", new ContextRootTagParser());
      this.parserMap.put("security-role-mapping", new SecurityRoleMappingTagParser());
      this.parserMap.put("session-config", new SessionConfigTagParser());
      this.parserMap.put("ejb-ref", new EjbRefTagParser());
      this.parserMap.put("resource-ref", new ResourceRefTagParser());
      this.parserMap.put("resource-env-ref", new ResourceEnvRefTagParser());
      this.parserMap.put("class-loader", new ClassLoaderTagParser());
      this.parserMap.put("jsp-config", new JspConfigTagParser());
      this.parserMap.put("default-tag-parser", new DefaultTagParser());
   }

   public static GlassFishWebAppParser getParser(VirtualJarFile vjFile) {
      InputStream inputStream = null;
      if (vjFile != null) {
         ZipEntry entry = vjFile.getEntry("WEB-INF/glassfish-web.xml");
         if (entry == null) {
            entry = vjFile.getEntry("WEB-INF/sun-web.xml");
         }

         if (entry != null) {
            try {
               inputStream = vjFile.getInputStream(entry);
            } catch (IOException var4) {
               var4.printStackTrace();
            }
         }
      }

      return new GlassFishWebAppParser(inputStream);
   }

   public static GlassFishWebAppParser getParser(String fileName, boolean isValidation) throws FileNotFoundException {
      InputStream inputStream = new FileInputStream(new File(fileName));
      return new GlassFishWebAppParser(inputStream);
   }

   public WeblogicWebAppBean getWeblogicWebAppBean() throws IOException, XMLStreamException {
      if (this.descriptorInputStream == null) {
         return null;
      } else {
         XMLInputFactory factory = XMLStreamInputFactory.newInstance();
         XMLStreamReader reader = factory.createXMLStreamReader(this.descriptorInputStream);

         WeblogicWebAppBean var3;
         try {
            var3 = this.parseDescriptor(reader);
         } finally {
            if (reader != null) {
               reader.close();
            }

         }

         return var3;
      }
   }

   private WeblogicWebAppBean parseDescriptor(XMLStreamReader reader) throws IOException, XMLStreamException {
      if (DEBUG.isDebugEnabled()) {
         DEBUG.debug("Start Parse Glassfish Descriptor...");
      }

      WeblogicWebAppBean weblogicWebAppBean = this.createWeblogicWebAppBean();

      do {
         int event = reader.next();
         if (event == 1) {
            this.getTagParser(reader.getLocalName()).parse(reader, weblogicWebAppBean);
         }
      } while(reader.hasNext());

      if (DEBUG.isDebugEnabled()) {
         DEBUG.debug("Parse Glassfish Descriptor completed!");
      }

      return weblogicWebAppBean;
   }

   private BaseGlassfishTagParser getTagParser(String localName) {
      BaseGlassfishTagParser parser = (BaseGlassfishTagParser)this.parserMap.get(localName);
      if (parser == null) {
         parser = (BaseGlassfishTagParser)this.parserMap.get("default-tag-parser");
      }

      return parser;
   }

   private WeblogicWebAppBean createWeblogicWebAppBean() {
      WeblogicWebAppBean weblogicWebAppBean = (WeblogicWebAppBean)(new DescriptorManager()).createDescriptorRoot(WeblogicWebAppBean.class).getRootBean();
      return weblogicWebAppBean;
   }
}
