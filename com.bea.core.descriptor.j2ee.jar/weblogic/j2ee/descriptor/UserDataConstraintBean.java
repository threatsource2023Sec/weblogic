package weblogic.j2ee.descriptor;

public interface UserDataConstraintBean {
   String[] getDescriptions();

   void addDescription(String var1);

   void removeDescription(String var1);

   void setDescriptions(String[] var1);

   String getTransportGuarantee();

   void setTransportGuarantee(String var1);

   String getId();

   void setId(String var1);
}
