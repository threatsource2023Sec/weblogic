package weblogic.j2ee.descriptor;

public interface ParamValueBean {
   String[] getDescriptions();

   void addDescription(String var1);

   void removeDescription(String var1);

   void setDescriptions(String[] var1);

   String getParamName();

   void setParamName(String var1);

   String getParamValue();

   void setParamValue(String var1);

   boolean isParamValueSet();

   String getId();

   void setId(String var1);
}
