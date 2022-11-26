package weblogic.jms.common;

import org.jvnet.hk2.annotations.Contract;

@Contract
public interface CDSListProvider {
   DDMemberInformation[] registerListener(CDSListListener var1) throws javax.jms.JMSException;

   void unregisterListener(CDSListListener var1);

   String getMigratableTargetName(String var1);
}
