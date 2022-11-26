package weblogic.ejb.container.persistence;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.HashSet;
import java.util.Set;
import weblogic.xml.process.XMLParsingException;
import weblogic.xml.process.XMLProcessingException;

public abstract class PersistenceVendorProcessor {
   private Set installedTypes = new HashSet();

   public Set getInstalledTypes() {
      return this.installedTypes;
   }

   public void addType(PersistenceType type) {
      this.installedTypes.add(type);
   }

   public abstract void process(Reader var1) throws IOException, XMLParsingException, XMLProcessingException;

   public abstract void process(InputStream var1) throws IOException, XMLParsingException, XMLProcessingException;
}
