package weblogic.collage.descriptor;

public interface CollageBean {
   String getMappingStyle();

   void setMappingStyle(String var1);

   PatternsetBean[] getPatternsets();

   MappingBean[] getMappings();

   String getPathToWritableRoot();
}
