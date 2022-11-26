package weblogic.security.utils;

import org.jvnet.hk2.annotations.Contract;
import weblogic.management.configuration.EmbeddedLDAPMBean;

@Contract
public interface EmbeddedLDAPService {
   String ROOT_USER_NAME = "cn=Admin";

   EmbeddedLDAPMBean getEmbeddedLDAPMBean();

   void setReplicaInvalid();
}
