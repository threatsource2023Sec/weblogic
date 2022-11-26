package weblogic.j2ee.descriptor;

public interface DependsOnBean {
   String[] getEjbNames();

   void addEjbName(String var1);

   void removeEjbName(String var1);

   void setEjbNames(String[] var1);

   String getId();

   void setId(String var1);
}
