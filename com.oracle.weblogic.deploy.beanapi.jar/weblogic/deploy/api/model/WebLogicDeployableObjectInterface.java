package weblogic.deploy.api.model;

import java.io.FileNotFoundException;
import javax.enterprise.deploy.model.DDBeanRoot;
import javax.enterprise.deploy.model.exceptions.DDBeanCreateException;
import org.jvnet.hk2.annotations.Contract;

@Contract
public interface WebLogicDeployableObjectInterface {
   boolean hasDDBean(String var1) throws FileNotFoundException;

   DDBeanRoot getDDBeanRoot(String var1) throws FileNotFoundException, DDBeanCreateException;
}
