package weblogic.application.compiler.utils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.util.Arrays;
import java.util.zip.ZipEntry;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import weblogic.application.archive.ApplicationArchive;
import weblogic.application.compiler.FactoryException;
import weblogic.application.compiler.ToolsFactoryManager;
import weblogic.application.compiler.ToolsInitializerManager;
import weblogic.application.compiler.ToolsModule;
import weblogic.application.io.AA;
import weblogic.application.utils.CachedApplicationArchiveFactory;
import weblogic.application.utils.EarUtils;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.utils.jars.VirtualJarFactory;
import weblogic.utils.jars.VirtualJarFile;

public class ApplicationNameScanner {
   private static final DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugAppContainerTools");

   public static String getApplicationNameFromDescriptor(File appFile, File altDescriptorFile) throws ApplicationNameScannerException {
      ToolsInitializerManager.init();
      String[] path;
      String descriptorUri;
      if (EarUtils.isEar(appFile)) {
         descriptorUri = "META-INF/application.xml";
         path = new String[]{"application", "application-name"};
         if (debugLogger.isDebugEnabled()) {
            debug(EarUtils.addClassName("Enterprise application identified Scanning descriptorUri " + descriptorUri + " for element " + Arrays.toString(path)));
         }
      } else {
         try {
            ApplicationArchive archive = null;
            if (AA.useApplicationArchive(appFile)) {
               archive = CachedApplicationArchiveFactory.instance.create(appFile);
            }

            ToolsModule m = null;

            try {
               m = ToolsFactoryManager.createStandaloneModule(appFile, archive);
            } catch (FactoryException var8) {
               if (debugLogger.isDebugEnabled()) {
                  debug("Unable to create standalone tools module, error is " + var8.getMessage());
               }

               return null;
            }

            if (m.getApplicationNameXPath() == null || m.getApplicationNameXPath().length == 0) {
               if (debugLogger.isDebugEnabled()) {
                  debug("Application type does not support application name in descriptor");
               }

               return null;
            }

            descriptorUri = m.getStandardDescriptorURI();
            path = m.getApplicationNameXPath();
            if (debugLogger.isDebugEnabled()) {
               debug(EarUtils.addClassName("Matched module type " + m.getModuleType() + " Scanning descriptorUri " + descriptorUri + " for element " + Arrays.toString(path)));
            }
         } catch (IOException var9) {
            throw new ApplicationNameScannerException("Unable to examine application", var9);
         }
      }

      try {
         if (altDescriptorFile == null) {
            return appFile.isFile() ? extractAppNameFromFile(appFile, descriptorUri, path) : extractAppNameFromDirectory(appFile, descriptorUri, path);
         } else {
            return extractAppNameFromDescriptorFile(altDescriptorFile, path);
         }
      } catch (IOException var7) {
         throw new ApplicationNameScannerException(var7);
      }
   }

   private static String extractAppNameFromDescriptorFile(File descrFile, String[] path) throws IOException {
      try {
         ByteBufferFile byteBufferFile = new ByteBufferFile(descrFile);
         ByteBasedXMLScanner sc = new ByteBasedXMLScanner(byteBufferFile.byteBuffer, false);
         String name = sc.seek(path) ? sc.readText() : null;
         byteBufferFile.close();
         return name;
      } catch (FileNotFoundException var5) {
         return null;
      }
   }

   private static String extractAppNameFromDirectory(File appFile, String descriptorUri, String[] path) throws IOException {
      File descrFile = new File(appFile, descriptorUri);
      return extractAppNameFromDescriptorFile(descrFile, path);
   }

   private static String extractAppNameFromFile(File appFile, String descriptorUri, String[] path) throws IOException {
      VirtualJarFile application = VirtualJarFactory.createVirtualJar(appFile);

      InputStream entryInputStream;
      try {
         ZipEntry entry = application.getEntry(descriptorUri);
         if (entry != null) {
            entryInputStream = application.getInputStream(entry);

            try {
               ByteBasedXMLScanner sc = new ByteBasedXMLScanner(entryInputStream, (int)entry.getSize());
               String var7 = sc.seek(path) ? sc.readText() : null;
               return var7;
            } finally {
               entryInputStream.close();
            }
         }

         entryInputStream = null;
      } finally {
         application.close();
      }

      return entryInputStream;
   }

   private static void debug(String message) {
      debugLogger.debug(EarUtils.addClassName(message));
   }

   private static class ByteBufferFile {
      ByteBuffer byteBuffer;

      private ByteBufferFile() {
      }

      private ByteBufferFile(File descrFile) throws IOException {
         int fileLength = (int)descrFile.length();
         this.byteBuffer = ByteBuffer.allocate(fileLength);
         byte[] buf = this.byteBuffer.array();
         FileInputStream fis = new FileInputStream(descrFile);

         for(int read = 0; read < fileLength; read += fis.read(buf, read, fileLength - read)) {
         }

         fis.close();
      }

      public void close() throws IOException {
      }

      // $FF: synthetic method
      ByteBufferFile(File x0, Object x1) throws IOException {
         this(x0);
      }
   }

   private static class ElementContentHandler extends DefaultHandler {
      private boolean readyToReadElementData = false;
      private CharBuffer chars;
      private final String[] path;
      private int index = 0;
      private boolean workCompleted = false;

      private ElementContentHandler(String[] path) {
         this.path = path;
      }

      public void characters(char[] ch, int start, int length) throws SAXException {
         if (!this.workCompleted && this.readyToReadElementData) {
            if (this.chars == null) {
               this.chars = CharBuffer.wrap(ch, start, length);
            } else {
               this.chars = this.chars.put(ch, start, length);
            }

            if (ApplicationNameScanner.debugLogger.isDebugEnabled()) {
               ApplicationNameScanner.debug(EarUtils.addClassName("Element content found so far: " + this.chars.toString()));
            }
         }

      }

      public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
         if (!this.workCompleted && qName.endsWith(this.path[this.index])) {
            if (ApplicationNameScanner.debugLogger.isDebugEnabled()) {
               ApplicationNameScanner.debug(EarUtils.addClassName("Element name " + this.path[this.index] + " found"));
            }

            if (this.index == this.path.length - 1) {
               this.readyToReadElementData = true;
            } else {
               ++this.index;
            }
         }

      }

      public void endElement(String uri, String localName, String qName) throws SAXException {
         if (!this.workCompleted && this.readyToReadElementData && qName.endsWith(this.path[this.index])) {
            this.workCompleted = true;
            this.readyToReadElementData = false;
         }

      }

      public InputSource resolveEntity(String publicId, String systemId) throws IOException, SAXException {
         return new InputSource(new ByteArrayInputStream("<?xml version='1.0' encoding='UTF-8'?>".getBytes()));
      }

      private String getElementContents() {
         return this.chars == null ? null : this.chars.toString();
      }
   }
}
