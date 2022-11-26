package weblogic.deploy.service;

import java.io.Externalizable;
import java.util.Map;

public interface Version extends Externalizable {
   String getIdentity();

   Map getVersionComponents();

   boolean equals(Object var1);

   int hashCode();
}
