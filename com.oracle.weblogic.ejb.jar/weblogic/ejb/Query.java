package weblogic.ejb;

import java.sql.ResultSet;
import java.util.Collection;
import java.util.Properties;
import javax.ejb.FinderException;

public interface Query extends QueryProperties {
   Collection find(String var1) throws FinderException;

   Collection find(String var1, Properties var2) throws FinderException;

   ResultSet execute(String var1) throws FinderException;

   ResultSet execute(String var1, Properties var2) throws FinderException;

   String getLanguage();
}
