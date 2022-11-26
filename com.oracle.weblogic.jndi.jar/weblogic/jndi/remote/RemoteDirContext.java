package weblogic.jndi.remote;

import java.rmi.Remote;
import javax.naming.directory.DirContext;

public interface RemoteDirContext extends DirContext, Remote {
}
