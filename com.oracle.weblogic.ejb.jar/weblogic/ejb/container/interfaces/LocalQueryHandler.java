package weblogic.ejb.container.interfaces;

import java.util.Map;
import javax.ejb.EJBException;
import javax.ejb.FinderException;
import weblogic.ejb.PreparedQuery;
import weblogic.ejb.WLQueryProperties;

public interface LocalQueryHandler {
   Object executeQuery(String var1, WLQueryProperties var2, boolean var3, boolean var4) throws EJBException, FinderException;

   Object executePreparedQuery(String var1, PreparedQuery var2, Map var3, Map var4, boolean var5) throws EJBException, FinderException;
}
