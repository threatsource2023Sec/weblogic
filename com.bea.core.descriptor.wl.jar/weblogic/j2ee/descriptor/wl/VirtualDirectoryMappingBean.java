package weblogic.j2ee.descriptor.wl;

public interface VirtualDirectoryMappingBean {
   String getLocalPath();

   void setLocalPath(String var1);

   String[] getUrlPatterns();

   void addUrlPattern(String var1);

   void removeUrlPattern(String var1);

   void setUrlPatterns(String[] var1);

   String getId();

   void setId(String var1);
}
