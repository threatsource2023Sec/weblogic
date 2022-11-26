package javax.servlet.jsp.jstl.sql;

import java.util.SortedMap;

public interface Result {
   SortedMap[] getRows();

   Object[][] getRowsByIndex();

   String[] getColumnNames();

   int getRowCount();

   boolean isLimitedByMaxRows();
}
