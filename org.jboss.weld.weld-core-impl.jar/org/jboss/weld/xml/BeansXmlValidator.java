package org.jboss.weld.xml;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import org.jboss.weld.logging.XmlLogger;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class BeansXmlValidator implements ErrorHandler {
   private static final StreamSource[] EMPTY_SOURCE_ARRAY = new StreamSource[0];
   private static final String ROOT_ELEMENT_NAME = "beans";
   private static final String VALIDATION_ERROR_CODE_CVC_ELT_1 = "cvc-elt.1";
   private final Schema cdi11Schema;
   private final Schema cdi20Schema;

   public BeansXmlValidator() {
      SchemaFactory factory = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
      this.cdi11Schema = this.initSchema(factory, XmlSchema.CDI11_SCHEMAS);
      this.cdi20Schema = this.initSchema(factory, XmlSchema.CDI20_SCHEMAS);
   }

   @SuppressFBWarnings(
      value = {"RCN_REDUNDANT_NULLCHECK_WOULD_HAVE_BEEN_A_NPE"},
      justification = "False positive, see https://github.com/spotbugs/spotbugs/issues/259"
   )
   public void validate(URL beansXml, ErrorHandler errorHandler) {
      if (beansXml == null) {
         throw XmlLogger.LOG.loadError("unknown", (Throwable)null);
      } else {
         if (errorHandler == null) {
            errorHandler = this;
         }

         Schema schema = this.cdi20Schema;

         try {
            label517: {
               InputStream in = beansXml.openStream();
               Throwable var5 = null;

               try {
                  if (in.available() != 0) {
                     BufferedReader reader = new BufferedReader(new InputStreamReader(in, Charset.forName(StandardCharsets.UTF_8.name())));
                     Throwable var7 = null;

                     try {
                        while(true) {
                           String line;
                           if ((line = reader.readLine()) == null) {
                              break label517;
                           }

                           if (line.contains(XmlSchema.CDI11.getFileName())) {
                              schema = this.cdi11Schema;
                              break label517;
                           }

                           if (line.contains(XmlSchema.CDI20.getFileName())) {
                              break label517;
                           }
                        }
                     } catch (Throwable var62) {
                        var7 = var62;
                        throw var62;
                     } finally {
                        if (reader != null) {
                           if (var7 != null) {
                              try {
                                 reader.close();
                              } catch (Throwable var57) {
                                 var7.addSuppressed(var57);
                              }
                           } else {
                              reader.close();
                           }
                        }

                     }
                  }
               } catch (Throwable var64) {
                  var5 = var64;
                  throw var64;
               } finally {
                  if (in != null) {
                     if (var5 != null) {
                        try {
                           in.close();
                        } catch (Throwable var56) {
                           var5.addSuppressed(var56);
                        }
                     } else {
                        in.close();
                     }
                  }

               }

               return;
            }
         } catch (IOException var66) {
            throw XmlLogger.LOG.loadError(beansXml, var66);
         }

         if (schema != null) {
            Validator validator = schema.newValidator();
            validator.setErrorHandler((ErrorHandler)errorHandler);

            try {
               InputStream in = beansXml.openStream();
               Throwable var69 = null;

               try {
                  validator.validate(new StreamSource(in));
               } catch (Throwable var59) {
                  var69 = var59;
                  throw var59;
               } finally {
                  if (in != null) {
                     if (var69 != null) {
                        try {
                           in.close();
                        } catch (Throwable var58) {
                           var69.addSuppressed(var58);
                        }
                     } else {
                        in.close();
                     }
                  }

               }
            } catch (IOException | SAXException var61) {
            }

         }
      }
   }

   public void validate(URL beansXml) {
      this.validate(beansXml, this);
   }

   public void warning(SAXParseException e) throws SAXException {
      XmlLogger.LOG.xsdValidationWarning(e.getSystemId(), e.getLineNumber(), e.getMessage());
   }

   public void error(SAXParseException e) throws SAXException {
      if (!e.getMessage().startsWith("cvc-elt.1") || !e.getMessage().contains("beans")) {
         XmlLogger.LOG.xsdValidationError(e.getSystemId(), e.getLineNumber(), e.getMessage());
      }
   }

   public void fatalError(SAXParseException e) throws SAXException {
      throw e;
   }

   private static StreamSource[] loadXsds(XmlSchema[] schemas) {
      List xsds = new ArrayList();
      XmlSchema[] var2 = schemas;
      int var3 = schemas.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         XmlSchema schema = var2[var4];
         Source source = loadXsd(schema.getFileName(), schema.getClassLoader());
         if (source != null) {
            xsds.add(source);
         }
      }

      return (StreamSource[])xsds.toArray(EMPTY_SOURCE_ARRAY);
   }

   private static StreamSource loadXsd(String name, ClassLoader classLoader) {
      InputStream in = classLoader.getResourceAsStream(name);
      return in == null ? null : new StreamSource(in);
   }

   private Schema initSchema(SchemaFactory factory, XmlSchema[] schemas) {
      StreamSource[] sources = null;
      boolean var22 = false;

      Schema var4;
      StreamSource[] var5;
      int var7;
      label157: {
         try {
            var22 = true;
            sources = loadXsds(schemas);
            var4 = factory.newSchema(sources);
            var22 = false;
            break label157;
         } catch (SAXException var26) {
            XmlLogger.LOG.warnf("Error initializing schema from %s", Arrays.toString(schemas));
            var5 = null;
            var22 = false;
         } finally {
            if (var22) {
               if (sources != null) {
                  StreamSource[] var12 = sources;
                  int var13 = sources.length;

                  for(int var14 = 0; var14 < var13; ++var14) {
                     StreamSource source = var12[var14];

                     try {
                        source.getInputStream().close();
                     } catch (IOException var24) {
                        XmlLogger.LOG.warn("Error closing schema resource", var24);
                     }
                  }
               }

            }
         }

         if (sources != null) {
            StreamSource[] var6 = sources;
            var7 = sources.length;

            for(int var8 = 0; var8 < var7; ++var8) {
               StreamSource source = var6[var8];

               try {
                  source.getInputStream().close();
               } catch (IOException var23) {
                  XmlLogger.LOG.warn("Error closing schema resource", var23);
               }
            }
         }

         return var5;
      }

      if (sources != null) {
         var5 = sources;
         int var28 = sources.length;

         for(var7 = 0; var7 < var28; ++var7) {
            StreamSource source = var5[var7];

            try {
               source.getInputStream().close();
            } catch (IOException var25) {
               XmlLogger.LOG.warn("Error closing schema resource", var25);
            }
         }
      }

      return var4;
   }
}
