package weblogic.j2ee.descriptor;

public interface MethodParamsBean {
   String[] getMethodParams();

   void addMethodParam(String var1);

   void removeMethodParam(String var1);

   void setMethodParams(String[] var1);

   String getId();

   void setId(String var1);
}
