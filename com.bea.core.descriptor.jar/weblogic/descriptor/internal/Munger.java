package weblogic.descriptor.internal;

import java.util.List;

public interface Munger {
   boolean usingDTD();

   boolean supportsValidation();

   boolean isValidationEnabled();

   void logError(List var1);

   public interface SchemaHelper {
      int getPropertyIndex(ReaderEventInfo var1);

      int getPropertyIndex(String var1);

      boolean isArray(int var1);

      boolean isBean(int var1);

      boolean isAnAttribute(int var1);

      SchemaHelper getSchemaHelper(int var1);
   }

   public interface ReaderEventInfo {
      String getElementName();

      ReaderEventInfo getParentReaderInfo();

      boolean compareXpaths(String var1);
   }
}
