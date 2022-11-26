package weblogic.security.acl;

import java.io.Serializable;
import java.security.Principal;

/** @deprecated */
@Deprecated
public interface UserInfo extends Principal, Serializable {
   long serialVersionUID = 3939680155872447490L;

   String getName();

   String getRealmName();
}
