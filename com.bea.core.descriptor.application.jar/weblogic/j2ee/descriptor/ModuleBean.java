package weblogic.j2ee.descriptor;

public interface ModuleBean {
   String getConnector();

   void setConnector(String var1);

   String getEjb();

   void setEjb(String var1);

   boolean isEjbSet();

   String getJava();

   void setJava(String var1);

   boolean isJavaSet();

   WebBean getWeb();

   WebBean createWeb();

   void destroyWeb(WebBean var1);

   String getAltDd();

   void setAltDd(String var1);

   String getId();

   void setId(String var1);
}
