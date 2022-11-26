package weblogic.j2ee.descriptor;

public interface ApplicationExceptionBean {
   String getExceptionClass();

   void setExceptionClass(String var1);

   boolean isRollback();

   void setRollback(boolean var1);

   String getId();

   void setId(String var1);

   boolean isInherited();

   void setInherited(boolean var1);
}
