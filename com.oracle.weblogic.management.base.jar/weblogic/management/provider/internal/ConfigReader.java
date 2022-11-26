package weblogic.management.provider.internal;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Enumeration;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLStreamException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;
import weblogic.descriptor.DescriptorReader;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.logging.Loggable;
import weblogic.management.ManagementLogger;
import weblogic.management.VersionConstants;

public class ConfigReader extends DescriptorReader {
   private static DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugConfigurationRuntime");
   public static final String EXECUTE_CONFIG_TRANSLATORS_PROP = "weblogic.configuration.executeConfigurationTranslators";
   private static final boolean executeConfigurationTranslators = getBooleanProperty("weblogic.configuration.executeConfigurationTranslators", false);
   private static final boolean convertSecurityExtensionSchema = getBooleanProperty("weblogic.management.convertSecurityExtensionSchema", false);

   public static boolean getBooleanProperty(String prop, boolean _default) {
      String value = System.getProperty(prop);
      return value != null ? Boolean.parseBoolean(value) : _default;
   }

   public ConfigReader(InputStream _in) throws XMLStreamException {
      this(_in, (ConfigReaderContext)null);
   }

   public ConfigReader(InputStream _in, ConfigReaderContext ctx) throws XMLStreamException {
      super(convert(_in, ctx));
      if (ctx != null && ctx.isStreamModifed()) {
         this.setModified(true);
      }

      int i;
      for(i = 0; i < VersionConstants.NAMESPACE_MAPPING.length; ++i) {
         this.addNamespaceMapping(VersionConstants.NAMESPACE_MAPPING[i][0], VersionConstants.NAMESPACE_MAPPING[i][1]);
      }

      if (convertSecurityExtensionSchema) {
         for(i = 0; i < VersionConstants.EXTENSION_NAMESPACE_MAPPING.length; ++i) {
            this.addNamespaceMapping(VersionConstants.EXTENSION_NAMESPACE_MAPPING[i][0], VersionConstants.EXTENSION_NAMESPACE_MAPPING[i][1]);
         }
      }

   }

   public static InputStream convert(InputStream in, ConfigReaderContext ctx) throws XMLStreamException {
      try {
         TransformerFactory factory = TransformerFactory.newInstance();
         DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
         dbf.setNamespaceAware(true);

         try {
            dbf.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
            dbf.setFeature("http://xml.org/sax/features/external-general-entities", false);
         } catch (ParserConfigurationException var13) {
            var13.printStackTrace();
         }

         DocumentBuilder db = dbf.newDocumentBuilder();
         Document d = db.parse(in);
         Element docElt = d.getDocumentElement();
         String docNS = docElt.getNamespaceURI();
         if ("http://www.bea.com/ns/weblogic/90/domain".equals(docNS) || "http://www.bea.com/ns/weblogic/920/domain".equals(docNS)) {
            if (ctx != null) {
               ctx.setStreamModified(true);
            }

            convert90(d);
         }

         if (executeConfigurationTranslators) {
            if (ctx != null) {
               ctx.setStreamModified(true);
            }

            convertPluggable(d);
         }

         ByteArrayOutputStream out = new ByteArrayOutputStream();
         Source source = new DOMSource(d);
         Result result = new StreamResult(out);
         Transformer transformer = factory.newTransformer();
         transformer.transform(source, result);
         byte[] b = out.toByteArray();
         return new ByteArrayInputStream(b);
      } catch (ParserConfigurationException var14) {
         throw new XMLStreamException(var14.getMessage(), var14);
      } catch (SAXException var15) {
         throw new XMLStreamException(var15.getMessage(), var15);
      } catch (TransformerConfigurationException var16) {
         throw new XMLStreamException(var16.getMessage(), var16);
      } catch (IOException var17) {
         throw new XMLStreamException(var17.getMessage(), var17);
      } catch (TransformerException var18) {
         throw new XMLStreamException(var18.getMessage(), var18);
      }
   }

   private static void convert90(Document d) {
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("Convert config from 90 to current version");
      }

      NodeList nodes = d.getElementsByTagName("domain");
      if (nodes != null && nodes.getLength() > 0) {
         Node domain = nodes.item(0);
         Node domainVersionNode = null;
         Node nextNode = null;
         nodes = domain.getChildNodes();

         for(int i = 0; i < nodes.getLength(); ++i) {
            Node n = nodes.item(i);
            if (("name".equals(n.getLocalName()) || "notes".equals(n.getLocalName())) && nodes.getLength() > i + 1) {
               nextNode = nodes.item(i + 1);
            }

            if ("domain-version".equals(n.getLocalName())) {
               domainVersionNode = n;
            }
         }

         NodeList logs = d.getElementsByTagName("log");
         if (logs != null && logs.getLength() > 0) {
            for(int m = 0; m < nodes.getLength(); ++m) {
               Node e = logs.item(m);
               Node logFileFilterNode = null;
               Node stdoutSeverityNode = null;
               Node stdoutFormatNode = null;
               Node stdoutLogStackNode = null;
               if (e != null) {
                  NodeList logElements = e.getChildNodes();
                  if (logElements != null && logElements.getLength() > 0) {
                     for(int k = 0; k < logElements.getLength(); ++k) {
                        Node logItem = logElements.item(k);
                        if (logItem.getLocalName() != null) {
                           if (logItem.getLocalName().equals("stdout-severity") && stdoutSeverityNode == null) {
                              stdoutSeverityNode = logItem;
                           }

                           if (logItem.getLocalName().equals("log-file-filter") && logFileFilterNode == null) {
                              logFileFilterNode = logItem;
                           }

                           if (logItem.getLocalName().equals("stdout-format") && stdoutFormatNode == null) {
                              stdoutFormatNode = logItem;
                           }

                           if (logItem.getLocalName().equals("stdout-log-stack") && stdoutLogStackNode == null) {
                              stdoutLogStackNode = logItem;
                           }
                        }
                     }

                     if (stdoutSeverityNode != null & logFileFilterNode != null) {
                        e.insertBefore(stdoutSeverityNode, logFileFilterNode);
                     }

                     if (stdoutFormatNode != null & logFileFilterNode != null) {
                        e.insertBefore(stdoutFormatNode, logFileFilterNode);
                     }

                     if (stdoutLogStackNode != null & logFileFilterNode != null) {
                        e.insertBefore(stdoutLogStackNode, logFileFilterNode);
                     }
                  }
               }
            }
         }

         if (domainVersionNode == null) {
            Element elt = d.createElement("domain-version");
            Text text = d.createTextNode("10.3.0.0");
            Text cr = d.createTextNode("\n  ");
            elt.appendChild(text);
            if (nextNode != null) {
               domain.insertBefore(elt, nextNode);
               domain.insertBefore(cr, elt);
            } else {
               domain.appendChild(elt);
               domain.appendChild(cr);
            }
         }
      }

   }

   private static void convertPluggable(Document d) {
      ConfigurationTranslator tx = null;
      Element docElt = d.getDocumentElement();
      String docNS = docElt.getNamespaceURI();
      if (docNS != null) {
         ClassLoader cl = Thread.currentThread().getContextClassLoader();
         if (cl == null) {
            cl = ConfigReader.class.getClassLoader();
         }

         Enumeration translators = null;

         try {
            translators = cl.getResources("META-INF/wls-configuration-translators");

            while(translators.hasMoreElements()) {
               URL url = (URL)translators.nextElement();
               if (debugLogger.isDebugEnabled()) {
                  debugLogger.debug("Run configuration translator " + url);
               }

               BufferedReader r = new BufferedReader(new InputStreamReader(url.openStream()));

               for(String line = r.readLine(); line != null; line = r.readLine()) {
                  Loggable l;
                  try {
                     tx = (ConfigurationTranslator)Class.forName(line).newInstance();
                  } catch (ClassNotFoundException var11) {
                     l = ManagementLogger.logErrorLoadingConfigTranslatorLoggable(line, var11.toString());
                     l.log();
                     continue;
                  } catch (InstantiationException var12) {
                     l = ManagementLogger.logErrorLoadingConfigTranslatorLoggable(line, var12.toString());
                     l.log();
                     continue;
                  } catch (IllegalAccessException var13) {
                     l = ManagementLogger.logErrorLoadingConfigTranslatorLoggable(line, var13.toString());
                     l.log();
                     continue;
                  } catch (ClassCastException var14) {
                     l = ManagementLogger.logErrorLoadingConfigTranslatorLoggable(line, var14.toString());
                     l.log();
                     continue;
                  }

                  if (docNS.equals(tx.sourceNamespace()) && "http://www.bea.com/ns/weblogic/920/domain".equals(tx.targetNamespace())) {
                     tx.translate(d);
                  }
               }
            }
         } catch (IOException var15) {
            ManagementLogger.logExceptionDuringConfigTranslation(var15);
         }

      }
   }
}
