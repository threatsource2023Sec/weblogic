package weblogic.apache.xerces.parsers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FilterInputStream;
import java.io.FilterReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URL;
import java.util.Properties;
import weblogic.apache.xerces.impl.XMLEntityDescription;
import weblogic.apache.xerces.impl.dtd.XMLDTDProcessor;
import weblogic.apache.xerces.util.SecurityManager;
import weblogic.apache.xerces.util.SymbolTable;
import weblogic.apache.xerces.xni.Augmentations;
import weblogic.apache.xerces.xni.XMLDTDHandler;
import weblogic.apache.xerces.xni.XMLLocator;
import weblogic.apache.xerces.xni.XMLResourceIdentifier;
import weblogic.apache.xerces.xni.XMLString;
import weblogic.apache.xerces.xni.XNIException;
import weblogic.apache.xerces.xni.grammars.XMLGrammarPool;
import weblogic.apache.xerces.xni.parser.XMLComponentManager;
import weblogic.apache.xerces.xni.parser.XMLConfigurationException;
import weblogic.apache.xerces.xni.parser.XMLDTDFilter;
import weblogic.apache.xerces.xni.parser.XMLDTDScanner;
import weblogic.apache.xerces.xni.parser.XMLDTDSource;
import weblogic.apache.xerces.xni.parser.XMLEntityResolver;
import weblogic.apache.xerces.xni.parser.XMLInputSource;

public final class SecureProcessingConfiguration extends XIncludeAwareParserConfiguration {
   private static final String SECURITY_MANAGER_PROPERTY = "http://apache.org/xml/properties/security-manager";
   private static final String ENTITY_RESOLVER_PROPERTY = "http://apache.org/xml/properties/internal/entity-resolver";
   private static final String EXTERNAL_GENERAL_ENTITIES = "http://xml.org/sax/features/external-general-entities";
   private static final String EXTERNAL_PARAMETER_ENTITIES = "http://xml.org/sax/features/external-parameter-entities";
   private static final String LOAD_EXTERNAL_DTD = "http://apache.org/xml/features/nonvalidating/load-external-dtd";
   private static final boolean DEBUG = isDebugEnabled();
   private static Properties jaxpProperties = null;
   private static long lastModified = -1L;
   private static final int SECURITY_MANAGER_DEFAULT_ENTITY_EXPANSION_LIMIT = 100000;
   private static final int SECURITY_MANAGER_DEFAULT_MAX_OCCUR_NODE_LIMIT = 3000;
   private static final String ENTITY_EXPANSION_LIMIT_PROPERTY_NAME = "jdk.xml.entityExpansionLimit";
   private static final String MAX_OCCUR_LIMIT_PROPERTY_NAME = "jdk.xml.maxOccur";
   private static final String TOTAL_ENTITY_SIZE_LIMIT_PROPERTY_NAME = "jdk.xml.totalEntitySizeLimit";
   private static final String MAX_GENERAL_ENTITY_SIZE_LIMIT_PROPERTY_NAME = "jdk.xml.maxGeneralEntitySizeLimit";
   private static final String MAX_PARAMETER_ENTITY_SIZE_LIMIT_PROPERTY_NAME = "jdk.xml.maxParameterEntitySizeLimit";
   private static final String RESOLVE_EXTERNAL_ENTITIES_PROPERTY_NAME = "jdk.xml.resolveExternalEntities";
   private static final int ENTITY_EXPANSION_LIMIT_DEFAULT_VALUE = 64000;
   private static final int MAX_OCCUR_LIMIT_DEFAULT_VALUE = 5000;
   private static final int TOTAL_ENTITY_SIZE_LIMIT_DEFAULT_VALUE = 50000000;
   private static final int MAX_GENERAL_ENTITY_SIZE_LIMIT_DEFAULT_VALUE = Integer.MAX_VALUE;
   private static final int MAX_PARAMETER_ENTITY_SIZE_LIMIT_DEFAULT_VALUE = Integer.MAX_VALUE;
   private static final boolean RESOLVE_EXTERNAL_ENTITIES_DEFAULT_VALUE = true;
   protected final int ENTITY_EXPANSION_LIMIT_SYSTEM_VALUE;
   protected final int MAX_OCCUR_LIMIT_SYSTEM_VALUE;
   protected final int TOTAL_ENTITY_SIZE_LIMIT_SYSTEM_VALUE;
   protected final int MAX_GENERAL_ENTITY_SIZE_LIMIT_SYSTEM_VALUE;
   protected final int MAX_PARAMETER_ENTITY_SIZE_LIMIT_SYSTEM_VALUE;
   protected final boolean RESOLVE_EXTERNAL_ENTITIES_SYSTEM_VALUE;
   private final boolean fJavaSecurityManagerEnabled;
   private boolean fLimitSpecified;
   private SecurityManager fSecurityManager;
   private InternalEntityMonitor fInternalEntityMonitor;
   private final ExternalEntityMonitor fExternalEntityMonitor;
   private int fTotalEntitySize;
   // $FF: synthetic field
   static Class class$weblogic$apache$xerces$parsers$SecureProcessingConfiguration;

   public SecureProcessingConfiguration() {
      this((SymbolTable)null, (XMLGrammarPool)null, (XMLComponentManager)null);
   }

   public SecureProcessingConfiguration(SymbolTable var1) {
      this(var1, (XMLGrammarPool)null, (XMLComponentManager)null);
   }

   public SecureProcessingConfiguration(SymbolTable var1, XMLGrammarPool var2) {
      this(var1, var2, (XMLComponentManager)null);
   }

   public SecureProcessingConfiguration(SymbolTable var1, XMLGrammarPool var2, XMLComponentManager var3) {
      super(var1, var2, var3);
      this.fTotalEntitySize = 0;
      this.fJavaSecurityManagerEnabled = System.getSecurityManager() != null;
      this.ENTITY_EXPANSION_LIMIT_SYSTEM_VALUE = this.getPropertyValue("jdk.xml.entityExpansionLimit", 64000);
      this.MAX_OCCUR_LIMIT_SYSTEM_VALUE = this.getPropertyValue("jdk.xml.maxOccur", 5000);
      this.TOTAL_ENTITY_SIZE_LIMIT_SYSTEM_VALUE = this.getPropertyValue("jdk.xml.totalEntitySizeLimit", 50000000);
      this.MAX_GENERAL_ENTITY_SIZE_LIMIT_SYSTEM_VALUE = this.getPropertyValue("jdk.xml.maxGeneralEntitySizeLimit", Integer.MAX_VALUE);
      this.MAX_PARAMETER_ENTITY_SIZE_LIMIT_SYSTEM_VALUE = this.getPropertyValue("jdk.xml.maxParameterEntitySizeLimit", Integer.MAX_VALUE);
      this.RESOLVE_EXTERNAL_ENTITIES_SYSTEM_VALUE = this.getPropertyValue("jdk.xml.resolveExternalEntities", true);
      if (this.fJavaSecurityManagerEnabled || this.fLimitSpecified) {
         if (!this.RESOLVE_EXTERNAL_ENTITIES_SYSTEM_VALUE) {
            super.setFeature("http://xml.org/sax/features/external-general-entities", false);
            super.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
            super.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
         }

         this.fSecurityManager = new SecurityManager();
         this.fSecurityManager.setEntityExpansionLimit(this.ENTITY_EXPANSION_LIMIT_SYSTEM_VALUE);
         this.fSecurityManager.setMaxOccurNodeLimit(this.MAX_OCCUR_LIMIT_SYSTEM_VALUE);
         super.setProperty("http://apache.org/xml/properties/security-manager", this.fSecurityManager);
      }

      this.fExternalEntityMonitor = new ExternalEntityMonitor();
      super.setProperty("http://apache.org/xml/properties/internal/entity-resolver", this.fExternalEntityMonitor);
   }

   protected void checkEntitySizeLimits(int var1, int var2, boolean var3) {
      this.fTotalEntitySize += var2;
      if (this.fTotalEntitySize > this.TOTAL_ENTITY_SIZE_LIMIT_SYSTEM_VALUE) {
         this.fErrorReporter.reportError("http://www.w3.org/TR/1998/REC-xml-19980210", "TotalEntitySizeLimitExceeded", new Object[]{new Integer(this.TOTAL_ENTITY_SIZE_LIMIT_SYSTEM_VALUE)}, (short)2);
      }

      if (var3) {
         if (var1 > this.MAX_PARAMETER_ENTITY_SIZE_LIMIT_SYSTEM_VALUE) {
            this.fErrorReporter.reportError("http://www.w3.org/TR/1998/REC-xml-19980210", "MaxParameterEntitySizeLimitExceeded", new Object[]{new Integer(this.MAX_PARAMETER_ENTITY_SIZE_LIMIT_SYSTEM_VALUE)}, (short)2);
         }
      } else if (var1 > this.MAX_GENERAL_ENTITY_SIZE_LIMIT_SYSTEM_VALUE) {
         this.fErrorReporter.reportError("http://www.w3.org/TR/1998/REC-xml-19980210", "MaxGeneralEntitySizeLimitExceeded", new Object[]{new Integer(this.MAX_GENERAL_ENTITY_SIZE_LIMIT_SYSTEM_VALUE)}, (short)2);
      }

   }

   public Object getProperty(String var1) throws XMLConfigurationException {
      if ("http://apache.org/xml/properties/security-manager".equals(var1)) {
         return this.fSecurityManager;
      } else {
         return "http://apache.org/xml/properties/internal/entity-resolver".equals(var1) ? this.fExternalEntityMonitor : super.getProperty(var1);
      }
   }

   public void setProperty(String var1, Object var2) throws XMLConfigurationException {
      if ("http://apache.org/xml/properties/security-manager".equals(var1)) {
         if (var2 == null && this.fJavaSecurityManagerEnabled) {
            return;
         }

         this.fSecurityManager = (SecurityManager)var2;
         if (this.fSecurityManager != null) {
            if (this.fSecurityManager.getEntityExpansionLimit() == 100000) {
               this.fSecurityManager.setEntityExpansionLimit(this.ENTITY_EXPANSION_LIMIT_SYSTEM_VALUE);
            }

            if (this.fSecurityManager.getMaxOccurNodeLimit() == 3000) {
               this.fSecurityManager.setMaxOccurNodeLimit(this.MAX_OCCUR_LIMIT_SYSTEM_VALUE);
            }
         }
      } else if ("http://apache.org/xml/properties/internal/entity-resolver".equals(var1)) {
         this.fExternalEntityMonitor.setEntityResolver((XMLEntityResolver)var2);
         return;
      }

      super.setProperty(var1, var2);
   }

   protected void configurePipeline() {
      super.configurePipeline();
      this.configurePipelineCommon(true);
   }

   protected void configureXML11Pipeline() {
      super.configureXML11Pipeline();
      this.configurePipelineCommon(false);
   }

   private void configurePipelineCommon(boolean var1) {
      if (this.fSecurityManager != null) {
         this.fTotalEntitySize = 0;
         if (this.fInternalEntityMonitor == null) {
            this.fInternalEntityMonitor = new InternalEntityMonitor();
         }

         Object var2;
         Object var3;
         if (var1) {
            var2 = this.fDTDScanner;
            var3 = this.fDTDProcessor;
         } else {
            var2 = this.fXML11DTDScanner;
            var3 = this.fXML11DTDProcessor;
         }

         ((XMLDTDScanner)var2).setDTDHandler(this.fInternalEntityMonitor);
         this.fInternalEntityMonitor.setDTDSource((XMLDTDSource)var2);
         this.fInternalEntityMonitor.setDTDHandler((XMLDTDHandler)var3);
         ((XMLDTDProcessor)var3).setDTDSource(this.fInternalEntityMonitor);
      }

   }

   private int getPropertyValue(String var1, int var2) {
      try {
         String var3 = SecuritySupport.getSystemProperty(var1);
         if (var3 != null && var3.length() > 0) {
            if (DEBUG) {
               debugPrintln("found system property \"" + var1 + "\", value=" + var3);
            }

            int var39 = Integer.parseInt(var3);
            this.fLimitSpecified = true;
            if (var39 > 0) {
               return var39;
            }

            return Integer.MAX_VALUE;
         }
      } catch (VirtualMachineError var34) {
         throw var34;
      } catch (ThreadDeath var35) {
         throw var35;
      } catch (Throwable var36) {
         if (DEBUG) {
            debugPrintln(var36.getClass().getName() + ": " + var36.getMessage());
            var36.printStackTrace();
         }
      }

      try {
         boolean var37 = false;
         File var4 = null;

         String var6;
         try {
            String var5 = SecuritySupport.getSystemProperty("java.home");
            var6 = var5 + File.separator + "lib" + File.separator + "jaxp.properties";
            var4 = new File(var6);
            var37 = SecuritySupport.getFileExists(var4);
         } catch (SecurityException var27) {
            lastModified = -1L;
            jaxpProperties = null;
         }

         Class var38 = class$weblogic$apache$xerces$parsers$SecureProcessingConfiguration == null ? (class$weblogic$apache$xerces$parsers$SecureProcessingConfiguration = class$("weblogic.apache.xerces.parsers.SecureProcessingConfiguration")) : class$weblogic$apache$xerces$parsers$SecureProcessingConfiguration;
         synchronized(var38) {
            boolean var40 = false;
            FileInputStream var7 = null;

            try {
               if (lastModified >= 0L) {
                  if (var37 && lastModified < (lastModified = SecuritySupport.getLastModified(var4))) {
                     var40 = true;
                  } else if (!var37) {
                     lastModified = -1L;
                     jaxpProperties = null;
                  }
               } else if (var37) {
                  var40 = true;
                  lastModified = SecuritySupport.getLastModified(var4);
               }

               if (var40) {
                  jaxpProperties = new Properties();
                  var7 = SecuritySupport.getFileInputStream(var4);
                  jaxpProperties.load(var7);
               }
            } catch (Exception var28) {
               lastModified = -1L;
               jaxpProperties = null;
            } finally {
               if (var7 != null) {
                  try {
                     var7.close();
                  } catch (IOException var26) {
                  }
               }

            }
         }

         if (jaxpProperties != null) {
            var6 = jaxpProperties.getProperty(var1);
            if (var6 != null && var6.length() > 0) {
               if (DEBUG) {
                  debugPrintln("found \"" + var1 + "\" in jaxp.properties, value=" + var6);
               }

               int var41 = Integer.parseInt(var6);
               this.fLimitSpecified = true;
               if (var41 > 0) {
                  return var41;
               }

               return Integer.MAX_VALUE;
            }
         }
      } catch (VirtualMachineError var31) {
         throw var31;
      } catch (ThreadDeath var32) {
         throw var32;
      } catch (Throwable var33) {
         if (DEBUG) {
            debugPrintln(var33.getClass().getName() + ": " + var33.getMessage());
            var33.printStackTrace();
         }
      }

      return var2;
   }

   private boolean getPropertyValue(String var1, boolean var2) {
      try {
         String var3 = SecuritySupport.getSystemProperty(var1);
         if (var3 != null && var3.length() > 0) {
            if (DEBUG) {
               debugPrintln("found system property \"" + var1 + "\", value=" + var3);
            }

            boolean var39 = Boolean.valueOf(var3);
            this.fLimitSpecified = true;
            return var39;
         }
      } catch (VirtualMachineError var34) {
         throw var34;
      } catch (ThreadDeath var35) {
         throw var35;
      } catch (Throwable var36) {
         if (DEBUG) {
            debugPrintln(var36.getClass().getName() + ": " + var36.getMessage());
            var36.printStackTrace();
         }
      }

      try {
         boolean var37 = false;
         File var4 = null;

         String var6;
         try {
            String var5 = SecuritySupport.getSystemProperty("java.home");
            var6 = var5 + File.separator + "lib" + File.separator + "jaxp.properties";
            var4 = new File(var6);
            var37 = SecuritySupport.getFileExists(var4);
         } catch (SecurityException var27) {
            lastModified = -1L;
            jaxpProperties = null;
         }

         Class var38 = class$weblogic$apache$xerces$parsers$SecureProcessingConfiguration == null ? (class$weblogic$apache$xerces$parsers$SecureProcessingConfiguration = class$("weblogic.apache.xerces.parsers.SecureProcessingConfiguration")) : class$weblogic$apache$xerces$parsers$SecureProcessingConfiguration;
         synchronized(var38) {
            boolean var40 = false;
            FileInputStream var7 = null;

            try {
               if (lastModified >= 0L) {
                  if (var37 && lastModified < (lastModified = SecuritySupport.getLastModified(var4))) {
                     var40 = true;
                  } else if (!var37) {
                     lastModified = -1L;
                     jaxpProperties = null;
                  }
               } else if (var37) {
                  var40 = true;
                  lastModified = SecuritySupport.getLastModified(var4);
               }

               if (var40) {
                  jaxpProperties = new Properties();
                  var7 = SecuritySupport.getFileInputStream(var4);
                  jaxpProperties.load(var7);
               }
            } catch (Exception var28) {
               lastModified = -1L;
               jaxpProperties = null;
            } finally {
               if (var7 != null) {
                  try {
                     var7.close();
                  } catch (IOException var26) {
                  }
               }

            }
         }

         if (jaxpProperties != null) {
            var6 = jaxpProperties.getProperty(var1);
            if (var6 != null && var6.length() > 0) {
               if (DEBUG) {
                  debugPrintln("found \"" + var1 + "\" in jaxp.properties, value=" + var6);
               }

               boolean var41 = Boolean.valueOf(var6);
               this.fLimitSpecified = true;
               return var41;
            }
         }
      } catch (VirtualMachineError var31) {
         throw var31;
      } catch (ThreadDeath var32) {
         throw var32;
      } catch (Throwable var33) {
         if (DEBUG) {
            debugPrintln(var33.getClass().getName() + ": " + var33.getMessage());
            var33.printStackTrace();
         }
      }

      return var2;
   }

   private static boolean isDebugEnabled() {
      try {
         String var0 = SecuritySupport.getSystemProperty("xerces.debug");
         return var0 != null && !"false".equals(var0);
      } catch (SecurityException var1) {
         return false;
      }
   }

   private static void debugPrintln(String var0) {
      if (DEBUG) {
         System.err.println("XERCES: " + var0);
      }

   }

   // $FF: synthetic method
   static Class class$(String var0) {
      try {
         return Class.forName(var0);
      } catch (ClassNotFoundException var2) {
         throw new NoClassDefFoundError(var2.getMessage());
      }
   }

   final class ExternalEntityMonitor implements XMLEntityResolver {
      private XMLEntityResolver fEntityResolver;

      public XMLInputSource resolveEntity(XMLResourceIdentifier var1) throws XNIException, IOException {
         XMLInputSource var2 = null;
         if (this.fEntityResolver != null) {
            var2 = this.fEntityResolver.resolveEntity(var1);
         }

         if (SecureProcessingConfiguration.this.fSecurityManager != null && var1 instanceof XMLEntityDescription) {
            String var3 = ((XMLEntityDescription)var1).getEntityName();
            boolean var4 = var3 != null && var3.startsWith("%");
            String var7;
            if (var2 == null) {
               String var5 = var1.getPublicId();
               String var6 = var1.getExpandedSystemId();
               var7 = var1.getBaseSystemId();
               var2 = new XMLInputSource(var5, var6, var7);
            }

            Reader var9 = var2.getCharacterStream();
            if (var9 != null) {
               var2.setCharacterStream(new ReaderMonitor(var9, var4));
            } else {
               InputStream var10 = var2.getByteStream();
               if (var10 != null) {
                  var2.setByteStream(new InputStreamMonitor(var10, var4));
               } else {
                  var7 = var1.getExpandedSystemId();
                  URL var8 = new URL(var7);
                  var10 = var8.openStream();
                  var2.setByteStream(new InputStreamMonitor(var10, var4));
               }
            }
         }

         return var2;
      }

      public void setEntityResolver(XMLEntityResolver var1) {
         this.fEntityResolver = var1;
      }

      public XMLEntityResolver getEntityResolver() {
         return this.fEntityResolver;
      }

      final class ReaderMonitor extends FilterReader {
         private final boolean isPE;
         private int size = 0;

         protected ReaderMonitor(Reader var2, boolean var3) {
            super(var2);
            this.isPE = var3;
         }

         public int read() throws IOException {
            int var1 = super.read();
            if (var1 != -1) {
               ++this.size;
               SecureProcessingConfiguration.this.checkEntitySizeLimits(this.size, 1, this.isPE);
            }

            return var1;
         }

         public int read(char[] var1, int var2, int var3) throws IOException {
            int var4 = super.read(var1, var2, var3);
            if (var4 > 0) {
               this.size += var4;
               SecureProcessingConfiguration.this.checkEntitySizeLimits(this.size, var4, this.isPE);
            }

            return var4;
         }
      }

      final class InputStreamMonitor extends FilterInputStream {
         private final boolean isPE;
         private int size = 0;

         protected InputStreamMonitor(InputStream var2, boolean var3) {
            super(var2);
            this.isPE = var3;
         }

         public int read() throws IOException {
            int var1 = super.read();
            if (var1 != -1) {
               ++this.size;
               SecureProcessingConfiguration.this.checkEntitySizeLimits(this.size, 1, this.isPE);
            }

            return var1;
         }

         public int read(byte[] var1, int var2, int var3) throws IOException {
            int var4 = super.read(var1, var2, var3);
            if (var4 > 0) {
               this.size += var4;
               SecureProcessingConfiguration.this.checkEntitySizeLimits(this.size, var4, this.isPE);
            }

            return var4;
         }
      }
   }

   final class InternalEntityMonitor implements XMLDTDFilter {
      private XMLDTDSource fDTDSource;
      private XMLDTDHandler fDTDHandler;

      public InternalEntityMonitor() {
      }

      public void startDTD(XMLLocator var1, Augmentations var2) throws XNIException {
         if (this.fDTDHandler != null) {
            this.fDTDHandler.startDTD(var1, var2);
         }

      }

      public void startParameterEntity(String var1, XMLResourceIdentifier var2, String var3, Augmentations var4) throws XNIException {
         if (this.fDTDHandler != null) {
            this.fDTDHandler.startParameterEntity(var1, var2, var3, var4);
         }

      }

      public void textDecl(String var1, String var2, Augmentations var3) throws XNIException {
         if (this.fDTDHandler != null) {
            this.fDTDHandler.textDecl(var1, var2, var3);
         }

      }

      public void endParameterEntity(String var1, Augmentations var2) throws XNIException {
         if (this.fDTDHandler != null) {
            this.fDTDHandler.endParameterEntity(var1, var2);
         }

      }

      public void startExternalSubset(XMLResourceIdentifier var1, Augmentations var2) throws XNIException {
         if (this.fDTDHandler != null) {
            this.fDTDHandler.startExternalSubset(var1, var2);
         }

      }

      public void endExternalSubset(Augmentations var1) throws XNIException {
         if (this.fDTDHandler != null) {
            this.fDTDHandler.endExternalSubset(var1);
         }

      }

      public void comment(XMLString var1, Augmentations var2) throws XNIException {
         if (this.fDTDHandler != null) {
            this.fDTDHandler.comment(var1, var2);
         }

      }

      public void processingInstruction(String var1, XMLString var2, Augmentations var3) throws XNIException {
         if (this.fDTDHandler != null) {
            this.fDTDHandler.processingInstruction(var1, var2, var3);
         }

      }

      public void elementDecl(String var1, String var2, Augmentations var3) throws XNIException {
         if (this.fDTDHandler != null) {
            this.fDTDHandler.elementDecl(var1, var2, var3);
         }

      }

      public void startAttlist(String var1, Augmentations var2) throws XNIException {
         if (this.fDTDHandler != null) {
            this.fDTDHandler.startAttlist(var1, var2);
         }

      }

      public void attributeDecl(String var1, String var2, String var3, String[] var4, String var5, XMLString var6, XMLString var7, Augmentations var8) throws XNIException {
         if (this.fDTDHandler != null) {
            this.fDTDHandler.attributeDecl(var1, var2, var3, var4, var5, var6, var7, var8);
         }

      }

      public void endAttlist(Augmentations var1) throws XNIException {
         if (this.fDTDHandler != null) {
            this.fDTDHandler.endAttlist(var1);
         }

      }

      public void internalEntityDecl(String var1, XMLString var2, XMLString var3, Augmentations var4) throws XNIException {
         SecureProcessingConfiguration.this.checkEntitySizeLimits(var2.length, var2.length, var1 != null && var1.startsWith("%"));
         if (this.fDTDHandler != null) {
            this.fDTDHandler.internalEntityDecl(var1, var2, var3, var4);
         }

      }

      public void externalEntityDecl(String var1, XMLResourceIdentifier var2, Augmentations var3) throws XNIException {
         if (this.fDTDHandler != null) {
            this.fDTDHandler.externalEntityDecl(var1, var2, var3);
         }

      }

      public void unparsedEntityDecl(String var1, XMLResourceIdentifier var2, String var3, Augmentations var4) throws XNIException {
         if (this.fDTDHandler != null) {
            this.fDTDHandler.unparsedEntityDecl(var1, var2, var3, var4);
         }

      }

      public void notationDecl(String var1, XMLResourceIdentifier var2, Augmentations var3) throws XNIException {
         if (this.fDTDHandler != null) {
            this.fDTDHandler.notationDecl(var1, var2, var3);
         }

      }

      public void startConditional(short var1, Augmentations var2) throws XNIException {
         if (this.fDTDHandler != null) {
            this.fDTDHandler.startConditional(var1, var2);
         }

      }

      public void ignoredCharacters(XMLString var1, Augmentations var2) throws XNIException {
         if (this.fDTDHandler != null) {
            this.fDTDHandler.ignoredCharacters(var1, var2);
         }

      }

      public void endConditional(Augmentations var1) throws XNIException {
         if (this.fDTDHandler != null) {
            this.fDTDHandler.endConditional(var1);
         }

      }

      public void endDTD(Augmentations var1) throws XNIException {
         if (this.fDTDHandler != null) {
            this.fDTDHandler.endDTD(var1);
         }

      }

      public void setDTDSource(XMLDTDSource var1) {
         this.fDTDSource = var1;
      }

      public XMLDTDSource getDTDSource() {
         return this.fDTDSource;
      }

      public void setDTDHandler(XMLDTDHandler var1) {
         this.fDTDHandler = var1;
      }

      public XMLDTDHandler getDTDHandler() {
         return this.fDTDHandler;
      }
   }
}
