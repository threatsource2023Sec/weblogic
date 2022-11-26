package weblogic.diagnostics.accessor;

public interface AccessorConfigurationProvider {
   boolean isDataRetirementTestModeEnabled();

   String getStoreDirectory();

   boolean isDataRetirementEnabled();

   int getPreferredStoreSizeLimit();

   int getStoreSizeCheckPeriod();

   String[] getAccessorNames();

   AccessorConfiguration getAccessorConfiguration(String var1) throws UnknownLogTypeException;

   void addAccessor(AccessorConfiguration var1);

   void removeAccessor(String var1);
}
