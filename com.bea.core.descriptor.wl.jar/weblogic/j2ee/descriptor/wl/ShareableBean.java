package weblogic.j2ee.descriptor.wl;

public interface ShareableBean {
   String APP_INF_LIB_DIR = "APP-INF-LIB";
   String LIB_DIR = "LIB-DIR";
   String APP_INF_CLASSES_DIR = "APP-INF-CLASSES";
   String WEB_INF_LIB_DIR = "WEB-INF-LIB";
   String WEB_INF_CLASSES_DIR = "WEB-INF-CLASSES";

   String getDir();

   void setDir(String var1);

   String[] getIncludes();

   String[] getExcludes();
}
