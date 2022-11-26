package weblogic.diagnostics.descriptor;

public interface WLDFHarvestedTypeBean extends WLDFBean {
   String getName();

   void setName(String var1);

   boolean isEnabled();

   void setEnabled(boolean var1);

   boolean isKnownType();

   void setKnownType(boolean var1);

   String[] getHarvestedAttributes();

   void setHarvestedAttributes(String[] var1);

   String[] getHarvestedInstances();

   void setHarvestedInstances(String[] var1);

   String getNamespace();

   void setNamespace(String var1);
}
