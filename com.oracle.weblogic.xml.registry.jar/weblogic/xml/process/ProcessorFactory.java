package weblogic.xml.process;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.PushbackInputStream;
import java.io.PushbackReader;
import java.io.Reader;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import weblogic.ejb.spi.XMLConstants;
import weblogic.utils.AssertionError;

public class ProcessorFactory {
   public static final String PROCESS_FACTORY_DEBUG = "weblogic.xml.process.debug";
   private static boolean debug;
   private static final String DOCTYPE_DECL_START = "<!DOCTYPE";
   private static final String PUBLIC_DECL_START = "PUBLIC";
   private static final String[] QUOTES = new String[]{"\"", "'"};
   private boolean validate = true;
   Map registeredProcessors = new HashMap();

   public ProcessorFactory() {
      debug = Boolean.getBoolean("weblogic.xml.process.debug");
      this.loadRegistry();
   }

   public ProcessorFactory(Map id2class) {
      debug = Boolean.getBoolean("weblogic.xml.process.debug");
      Iterator iterator = id2class.keySet().iterator();

      while(iterator.hasNext()) {
         String publicId = (String)iterator.next();
         String processorClassName = (String)id2class.get(publicId);
         this.registerProcessor(publicId, processorClassName);
      }

   }

   private void loadRegistry() {
      this.registerProcessor(ProcessorConstants.XML_TO_JAVA_PUBLIC_ID(), ProcessorConstants.XML_TO_JAVA_LOADER_CLASS());
      this.registerProcessor(ProcessorConstants.JAVA_TO_XML_PUBLIC_ID(), ProcessorConstants.JAVA_TO_XML_LOADER_CLASS());
      this.registerProcessors(XMLConstants.processors);
      this.registerProcessor("-//Sun Microsystems, Inc.//DTD J2EE Application 1.2//EN", "weblogic.j2ee.dd.xml.J2EEDeploymentDescriptorLoader_J2EE12");
      this.registerProcessor("-//Sun Microsystems, Inc.//DTD J2EE Application 1.3//EN", "weblogic.j2ee.dd.xml.J2EEDeploymentDescriptorLoader_J2EE13");
      this.registerProcessor("-//BEA Systems, Inc.//DTD WebLogic Application 7.0.0//EN", "weblogic.j2ee.dd.xml.WebLogicApplication_1_0");
      this.registerProcessor("-//BEA Systems, Inc.//DTD WebLogic Application 8.1.0//EN", "weblogic.j2ee.dd.xml.WebLogicApplication_2_0");
      this.registerProcessor("-//BEA Systems, Inc.//DTD WebLogic Application 9.0.0//EN", "weblogic.j2ee.dd.xml.WebLogicApplication_3_0");
   }

   private void registerProcessors(Map id2processor) {
      Iterator ids = id2processor.keySet().iterator();

      while(ids.hasNext()) {
         String publicId = (String)ids.next();
         this.registerProcessor(publicId, (String)id2processor.get(publicId));
      }

   }

   private void registerProcessor(String publicId, String processorClassName) {
      if (debug) {
         System.out.println("Registering " + processorClassName + " for " + publicId);
      }

      this.registeredProcessors.put(publicId, processorClassName);
   }

   public XMLProcessor getProcessor(String xmlFilePath, String[] validPublicIds) throws ProcessorFactoryException {
      FileInputStream r = null;

      try {
         r = new FileInputStream(xmlFilePath);
      } catch (IOException var14) {
         throw new ProcessorFactoryException(var14);
      }

      XMLProcessor var4;
      try {
         var4 = this.getProcessor((InputStream)r, validPublicIds);
      } finally {
         try {
            r.close();
         } catch (IOException var12) {
         }

      }

      return var4;
   }

   public XMLProcessor getProcessor(File xmlFile, String[] validPublicIds) throws ProcessorFactoryException {
      FileInputStream r = null;

      try {
         r = new FileInputStream(xmlFile);
      } catch (IOException var14) {
         throw new ProcessorFactoryException(var14);
      }

      XMLProcessor var4;
      try {
         var4 = this.getProcessor((InputStream)r, validPublicIds);
      } finally {
         try {
            r.close();
         } catch (IOException var12) {
         }

      }

      return var4;
   }

   public XMLProcessor getProcessor(InputStream xmlStream, String[] validPublicIds) throws ProcessorFactoryException {
      return this.getProcessor(xmlStream, false, validPublicIds);
   }

   public XMLProcessor getProcessor(Reader xmlStream, String[] validPublicIds) throws ProcessorFactoryException {
      return this.getProcessor(xmlStream, false, validPublicIds);
   }

   public XMLProcessor getProcessor(InputStream xmlStream, boolean reset, String[] validPublicIds) throws ProcessorFactoryException {
      try {
         String publicId = this.readPublicId(xmlStream, reset);
         if (publicId != null) {
            publicId = publicId.trim();
         }

         String processorClassName = (String)this.registeredProcessors.get(publicId);
         if (processorClassName == null) {
            if (debug) {
               System.out.println("Failed to find publicid = \"" + publicId + "\"");
               System.out.println("processor registry = " + this.registeredProcessors);
            }

            String msg = "The public id, '" + publicId + "', specified in the XML document is invalid.";
            if (validPublicIds != null && validPublicIds.length > 0) {
               String validIds = "\n";

               for(int i = 0; i < validPublicIds.length; ++i) {
                  validIds = validIds + "\"" + validPublicIds[i] + "\"\n";
               }

               msg = msg + "  Use one of the following valid public ids: " + validIds;
            }

            throw new ProcessorFactoryException(msg);
         } else {
            Constructor ctor = Class.forName(processorClassName).getConstructor(Boolean.TYPE);
            return (XMLProcessor)ctor.newInstance(new Boolean(this.validate));
         }
      } catch (EOFException var9) {
         throw new ProcessorFactoryException("XML document does not appear to contain a properly formed DOCTYPE header");
      } catch (NoSuchMethodException var10) {
         throw new AssertionError("Cannot find boolean constructor of processor");
      } catch (SecurityException var11) {
         throw new AssertionError("Cannot invoke boolean constructor of processor", var11);
      } catch (InvocationTargetException var12) {
         throw new AssertionError("Cannot invoke boolean constructor of processor", var12);
      } catch (ClassNotFoundException var13) {
         throw new ProcessorFactoryException(var13);
      } catch (InstantiationException var14) {
         throw new ProcessorFactoryException(var14);
      } catch (IllegalAccessException var15) {
         throw new ProcessorFactoryException(var15);
      } catch (IOException var16) {
         throw new ProcessorFactoryException(var16);
      }
   }

   public XMLProcessor getProcessor(Reader xmlStream, boolean reset, String[] validPublicIds) throws ProcessorFactoryException {
      try {
         String publicId = this.readPublicId(xmlStream, reset);
         String processorClassName = (String)this.registeredProcessors.get(publicId);
         if (processorClassName == null) {
            if (debug) {
               System.out.println("Failed to find publicid = \"" + publicId + "\"");
               System.out.println("processor registry = " + this.registeredProcessors);
            }

            String msg = "The public id, '" + publicId + "', specified in the XML document is invalid.";
            if (validPublicIds != null && validPublicIds.length > 0) {
               String validIds = "\n";

               for(int i = 0; i < validPublicIds.length; ++i) {
                  validIds = validIds + "\"" + validPublicIds[i] + "\"\n";
               }

               msg = msg + "  Use one of the following valid public ids: " + validIds;
            }

            throw new ProcessorFactoryException(msg);
         } else {
            Constructor ctor = Class.forName(processorClassName).getConstructor(Boolean.TYPE);
            return (XMLProcessor)ctor.newInstance(new Boolean(this.validate));
         }
      } catch (EOFException var9) {
         throw new ProcessorFactoryException("XML document does not appear to contain a properly formed DOCTYPE header");
      } catch (NoSuchMethodException var10) {
         throw new AssertionError("Cannot find boolean constructor of processor");
      } catch (SecurityException var11) {
         throw new AssertionError("Cannot invoke boolean constructor of processor", var11);
      } catch (InvocationTargetException var12) {
         throw new AssertionError("Cannot invoke boolean constructor of processor", var12);
      } catch (ClassNotFoundException var13) {
         throw new ProcessorFactoryException(var13);
      } catch (InstantiationException var14) {
         throw new ProcessorFactoryException(var14);
      } catch (IllegalAccessException var15) {
         throw new ProcessorFactoryException(var15);
      } catch (IOException var16) {
         throw new ProcessorFactoryException(var16);
      }
   }

   public void setValidating(boolean val) {
      this.validate = val;
   }

   public boolean isValidating() {
      return this.validate;
   }

   private String readPublicId(InputStream xmlStream, boolean reset) throws IOException {
      reset &= xmlStream.markSupported();
      if (debug) {
         System.out.println("readPublicId: Reset = " + reset);
      }

      int BYTE_LIMIT = true;
      if (reset) {
         xmlStream.mark(1000);
      }

      PushbackInputStream r = new PushbackInputStream(xmlStream);
      if (debug) {
         System.out.println("reading through <!DOCTYPE");
      }

      ParsingUtils.read(r, "<!DOCTYPE", true);
      if (debug) {
         System.out.println("reading through WS");
      }

      ParsingUtils.readWS(r);
      if (debug) {
         System.out.println("reading through PUBLIC");
      }

      ParsingUtils.read(r, "PUBLIC", true);
      if (debug) {
         System.out.println("reading through first quote");
      }

      String result = ParsingUtils.read(r, QUOTES, true);
      char quoteType = result.charAt(result.length() - 1);
      String quoteTypeStr = new String(new char[]{quoteType});
      if (debug) {
         System.out.println("reading until close quote");
      }

      String publicId = ParsingUtils.read(r, quoteTypeStr, false);
      if (reset) {
         try {
            xmlStream.reset();
         } catch (IOException var10) {
            if (debug) {
               var10.printStackTrace();
            }
         }
      }

      if (debug) {
         System.out.println("Read publicId = " + publicId);
      }

      return publicId;
   }

   private String readPublicId(Reader xmlStream, boolean reset) throws IOException {
      reset &= xmlStream.markSupported();
      if (debug) {
         System.out.println("readPublicId: Reset = " + reset);
      }

      int BYTE_LIMIT = true;
      if (reset) {
         try {
            xmlStream.mark(1000);
         } catch (IOException var10) {
            reset = false;
         }
      }

      PushbackReader r = new PushbackReader(xmlStream);
      if (debug) {
         System.out.println("reading through <!DOCTYPE");
      }

      ParsingUtils.read(r, "<!DOCTYPE", true);
      if (debug) {
         System.out.println("reading through WS");
      }

      ParsingUtils.readWS(r);
      if (debug) {
         System.out.println("reading through PUBLIC");
      }

      ParsingUtils.read(r, "PUBLIC", true);
      if (debug) {
         System.out.println("reading through first quote");
      }

      String result = ParsingUtils.read(r, QUOTES, true);
      char quoteType = result.charAt(result.length() - 1);
      String quoteTypeStr = new String(new char[]{quoteType});
      if (debug) {
         System.out.println("reading until close quote");
      }

      String publicId = ParsingUtils.read(r, quoteTypeStr, false);
      if (reset) {
         try {
            xmlStream.reset();
         } catch (IOException var11) {
            if (debug) {
               var11.printStackTrace();
            }
         }
      }

      if (debug) {
         System.out.println("Read publicId = " + publicId);
      }

      return publicId;
   }

   public static void main(String[] args) throws Exception {
      if (debug) {
         ProcessorFactory procFac = new ProcessorFactory();
         Reader r = new FileReader(args[0]);
         System.out.println("public id = " + procFac.readPublicId((Reader)r, false));
      }

   }
}
