package weblogic.application.metadatacache;

import java.io.File;
import java.io.IOException;

public interface MetadataEntry {
   MetadataType getType();

   File getLocation();

   boolean isStale(File var1);

   boolean isValid(Object var1);

   Object getCachableObject() throws MetadataCacheException;

   Object readObject(File var1) throws IOException, ClassNotFoundException;

   void writeObject(File var1, Object var2) throws IOException;
}
