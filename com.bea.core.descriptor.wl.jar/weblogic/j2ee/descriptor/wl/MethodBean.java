package weblogic.j2ee.descriptor.wl;

public interface MethodBean {
   String getDescription();

   void setDescription(String var1);

   String getEjbName();

   void setEjbName(String var1);

   String getMethodIntf();

   void setMethodIntf(String var1);

   String getMethodName();

   void setMethodName(String var1);

   MethodParamsBean getMethodParams();

   MethodParamsBean createMethodParams();

   void destroyMethodParams(MethodParamsBean var1);

   String getId();

   void setId(String var1);
}
