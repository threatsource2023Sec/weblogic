package weblogic.deployment.jms;

import java.util.Map;
import javax.jms.JMSException;
import org.jvnet.hk2.annotations.Contract;

@Contract
public interface JMSConnectionHelperServiceGenerator {
   JMSConnectionHelperService createJMSConnectionHelperService(String var1, Map var2, boolean var3, WrappedClassManager var4) throws JMSException;

   JMSConnectionHelperService createJMSConnectionHelperService(String var1, Map var2, String var3, String var4, WrappedClassManager var5) throws JMSException;
}
