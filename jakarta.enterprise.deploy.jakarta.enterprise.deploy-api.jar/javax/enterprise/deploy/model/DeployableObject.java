package javax.enterprise.deploy.model;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Enumeration;
import javax.enterprise.deploy.model.exceptions.DDBeanCreateException;
import javax.enterprise.deploy.shared.ModuleType;

public interface DeployableObject {
   ModuleType getType();

   DDBeanRoot getDDBeanRoot();

   DDBean[] getChildBean(String var1);

   String[] getText(String var1);

   Class getClassFromScope(String var1);

   /** @deprecated */
   String getModuleDTDVersion();

   DDBeanRoot getDDBeanRoot(String var1) throws FileNotFoundException, DDBeanCreateException;

   Enumeration entries();

   InputStream getEntry(String var1);
}
