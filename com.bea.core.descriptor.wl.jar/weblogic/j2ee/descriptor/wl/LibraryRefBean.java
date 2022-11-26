package weblogic.j2ee.descriptor.wl;

public interface LibraryRefBean {
   String getLibraryName();

   void setLibraryName(String var1);

   String getSpecificationVersion();

   void setSpecificationVersion(String var1);

   String getImplementationVersion();

   void setImplementationVersion(String var1);

   boolean getExactMatch();

   void setExactMatch(boolean var1);

   String getContextRoot();

   void setContextRoot(String var1);
}
