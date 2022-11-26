package weblogic.deployment.jms;

import java.util.Map;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import org.jvnet.hk2.annotations.Contract;

@Contract
public interface PrimaryContextHelperServiceGenerator {
   PrimaryContextHelperService createPrimaryContextHelperService(String var1, Map var2, boolean var3, WrappedClassManager var4) throws JMSException;

   PrimaryContextHelperService createPrimaryContextHelperService(String var1, Map var2, String var3, String var4, WrappedClassManager var5) throws JMSException;

   boolean hasNativeTransactions(JMSContext var1);
}
