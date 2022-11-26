package weblogic.coherence.app.descriptor.wl;

import weblogic.descriptor.SettableBean;

public interface InitParamBean extends SettableBean {
   String getParamType();

   void setParamType(String var1);

   String getParamValue();

   void setParamValue(String var1);
}
