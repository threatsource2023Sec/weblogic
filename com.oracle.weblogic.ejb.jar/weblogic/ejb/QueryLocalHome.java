package weblogic.ejb;

import java.util.Properties;
import javax.ejb.EJBException;
import javax.ejb.EJBLocalHome;

public interface QueryLocalHome extends EJBLocalHome {
   Query createQuery() throws EJBException;

   PreparedQuery prepareQuery(String var1) throws EJBException;

   PreparedQuery prepareQuery(String var1, Properties var2) throws EJBException;

   Query createSqlQuery() throws EJBException;

   String nativeQuery(String var1) throws EJBException;

   String getDatabaseProductName() throws EJBException;

   String getDatabaseProductVersion() throws EJBException;
}
