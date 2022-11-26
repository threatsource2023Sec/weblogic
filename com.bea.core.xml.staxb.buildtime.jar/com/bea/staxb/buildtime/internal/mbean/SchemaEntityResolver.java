package com.bea.staxb.buildtime.internal.mbean;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

class SchemaEntityResolver implements EntityResolver {
   private static final String FILE_TYPE = "file:\\";
   private Map schemas = new HashMap();
   private Both2BindProcessor processor;

   SchemaEntityResolver(Both2BindProcessor processor) {
      this.processor = processor;
   }

   public InputSource resolveEntity(String publicId, String systemId) throws SAXException, IOException {
      if (systemId == null) {
         this.processor.getLogger().logInfo("Resolver called with " + publicId + ":" + systemId);
         return null;
      } else if (!systemId.endsWith(".xsd")) {
         this.processor.getLogger().logInfo("Resolver called with " + publicId + ":" + systemId);
         return null;
      } else {
         String shortName;
         if (systemId.contains("/")) {
            shortName = systemId.substring(systemId.lastIndexOf(47) + 1);
         } else if (systemId.contains("\\")) {
            shortName = systemId.substring(systemId.lastIndexOf(92) + 1);
         } else {
            shortName = systemId;
         }

         byte[] result = (byte[])this.schemas.get(shortName);
         if (result != null) {
            return new InputSource(new ByteArrayInputStream(result));
         } else {
            this.processor.getLogger().logInfo("Null result for short name " + shortName);
            if (systemId.startsWith("file:\\")) {
               return new InputSource(new FileInputStream(systemId.substring("file:\\".length())));
            } else {
               this.processor.getLogger().logInfo("Resolver unable to resolve " + publicId + ":" + systemId);
               return null;
            }
         }
      }
   }

   public void addSchema(String shortName, InputStream inputStream) throws IOException {
      int size = inputStream.available();
      byte[] result = new byte[size];
      inputStream.read(result, 0, size);
      this.schemas.put(shortName, result);
   }
}
