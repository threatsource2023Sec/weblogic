package weblogic.j2ee.descriptor;

public interface WelcomeFileListBean {
   String[] getWelcomeFiles();

   void addWelcomeFile(String var1);

   void removeWelcomeFile(String var1);

   void setWelcomeFiles(String[] var1);

   String getId();

   void setId(String var1);
}
