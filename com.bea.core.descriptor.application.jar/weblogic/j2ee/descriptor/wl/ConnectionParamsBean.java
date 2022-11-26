package weblogic.j2ee.descriptor.wl;

public interface ConnectionParamsBean {
   ParameterBean[] getParameters();

   ParameterBean createParameter();

   void destroyParameter(ParameterBean var1);
}
