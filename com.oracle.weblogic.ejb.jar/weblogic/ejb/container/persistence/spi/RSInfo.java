package weblogic.ejb.container.persistence.spi;

import java.sql.ResultSet;

public interface RSInfo {
   ResultSet getRS();

   int getGroupIndex();

   Integer getOffset();

   String getCmrField();

   Integer getCmrFieldOffset();

   Object getPK();

   boolean usesCmpBean();
}
