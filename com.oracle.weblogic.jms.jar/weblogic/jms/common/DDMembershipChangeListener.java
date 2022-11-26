package weblogic.jms.common;

import javax.naming.Context;
import javax.naming.NamingException;
import weblogic.security.subject.AbstractSubject;

public interface DDMembershipChangeListener {
   void onDDMembershipChange(DDMembershipChangeEventImpl var1);

   String getDestinationName();

   String getProviderURL();

   Context getInitialContext() throws NamingException;

   Context getEnvContext();

   AbstractSubject getSubject();

   void onFailure(String var1, Exception var2);
}
