package weblogic.buzzmessagebus;

import org.jvnet.hk2.annotations.Contract;

@Contract
public interface BuzzMessageBusEndPointMBeanInfo {
   String getBuzzAddress();

   int getBuzzPort();

   boolean isBuzzEnabled();
}
