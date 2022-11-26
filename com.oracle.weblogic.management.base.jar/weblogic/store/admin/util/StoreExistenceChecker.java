package weblogic.store.admin.util;

import java.util.List;
import org.jvnet.hk2.annotations.Contract;

@Contract
public interface StoreExistenceChecker {
   List storesExist(List var1);
}
