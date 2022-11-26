package weblogic.j2ee.descriptor.wl;

public interface WeblogicCompatibilityBean {
   boolean isEntityAlwaysUsesTransaction();

   void setEntityAlwaysUsesTransaction(boolean var1);

   String getId();

   void setId(String var1);
}
