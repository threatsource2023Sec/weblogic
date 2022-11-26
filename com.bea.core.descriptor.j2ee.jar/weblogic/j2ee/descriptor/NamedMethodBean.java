package weblogic.j2ee.descriptor;

public interface NamedMethodBean {
   String getMethodName();

   void setMethodName(String var1);

   MethodParamsBean getMethodParams();

   MethodParamsBean createMethodParams();

   void destroyMethodParams(MethodParamsBean var1);

   String getId();

   void setId(String var1);
}
