package javax.jdo;

import javax.jdo.listener.ClearCallback;
import javax.jdo.listener.DeleteCallback;
import javax.jdo.listener.LoadCallback;
import javax.jdo.listener.StoreCallback;

public interface InstanceCallbacks extends ClearCallback, DeleteCallback, LoadCallback, StoreCallback {
}
