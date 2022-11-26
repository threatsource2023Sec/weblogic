package org.python.bouncycastle.jcajce;

import java.util.Collection;
import org.python.bouncycastle.util.Selector;
import org.python.bouncycastle.util.Store;
import org.python.bouncycastle.util.StoreException;

public interface PKIXCertStore extends Store {
   Collection getMatches(Selector var1) throws StoreException;
}
