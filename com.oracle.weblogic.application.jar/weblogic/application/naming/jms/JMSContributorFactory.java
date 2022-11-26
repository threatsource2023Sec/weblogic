package weblogic.application.naming.jms;

import javax.naming.Context;
import org.jvnet.hk2.annotations.Contract;

@Contract
public interface JMSContributorFactory {
   JMSContributor get(Context var1, Context var2, Context var3, Context var4);
}
