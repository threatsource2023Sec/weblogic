package weblogic.coherence.app.descriptor.wl;

import weblogic.descriptor.SettableBean;

public interface InitParamsBean extends SettableBean {
   InitParamBean[] getInitParams();
}
