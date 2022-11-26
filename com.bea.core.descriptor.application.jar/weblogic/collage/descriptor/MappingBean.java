package weblogic.collage.descriptor;

public interface MappingBean {
   String getStyle();

   String getUri();

   String getPath();

   PatternBean[] getPatterns();

   String[] getIncludes();

   String[] getExcludes();
}
