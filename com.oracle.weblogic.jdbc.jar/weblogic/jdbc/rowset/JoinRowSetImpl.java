package weblogic.jdbc.rowset;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import javax.sql.RowSet;
import javax.sql.RowSetEvent;
import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.JoinRowSet;
import javax.sql.rowset.Joinable;
import weblogic.xml.stream.XMLInputStream;

/** @deprecated */
@Deprecated
public class JoinRowSetImpl extends CachedRowSetImpl implements JoinRowSet {
   private static final long serialVersionUID = 7370178015207304595L;
   private ArrayList rowSets = new ArrayList();
   private String where = null;
   private int joinType = 1;

   public void populate(ResultSetMetaData md) throws SQLException {
      throw new SQLException("This is not supported for JoinRowSet");
   }

   public void populate(ResultSet rs) throws SQLException {
      throw new SQLException("This is not supported for JoinRowSet");
   }

   public void execute() throws SQLException {
      throw new SQLException("This is not supported for JoinRowSet");
   }

   public void execute(Connection con) throws SQLException {
      throw new SQLException("This is not supported for JoinRowSet");
   }

   public String executeAndGuessTableName() throws SQLException {
      throw new SQLException("This is not supported for JoinRowSet");
   }

   public boolean executeAndGuessTableNameAndPrimaryKeys() throws SQLException {
      throw new SQLException("This is not supported for JoinRowSet");
   }

   public void loadXML(XMLInputStream xis) throws IOException, SQLException {
      throw new SQLException("This is not supported for JoinRowSet");
   }

   private void validate(CachedRow left, int[] leftMatchColumns, CachedRow right, int[] rightMatchColumns) throws SQLException {
      if (leftMatchColumns.length != rightMatchColumns.length) {
         throw new SQLException("The number of match columns in two Joinables are not the same.");
      } else {
         for(int i = 0; i < leftMatchColumns.length; ++i) {
            this.isJoinable(left.getColumn(leftMatchColumns[i]).getClass(), right.getColumn(rightMatchColumns[i]).getClass());
         }

      }
   }

   public void addRowSet(Joinable rowset) throws SQLException {
      CachedRowSetImpl crs = null;
      if (rowset instanceof CachedRowSetImpl) {
         crs = (CachedRowSetImpl)rowset;
      } else {
         if (!(rowset instanceof RowSet)) {
            throw new SQLException(rowset + " can not be added into JoinRowSet.");
         }

         crs = new CachedRowSetImpl();
         crs.populate((ResultSet)((RowSet)rowset));
         crs.setMatchColumn(rowset.getMatchColumnIndexes());
      }

      if (this.rowSets.size() == 0) {
         super.populate(crs, 1);
         this.setMatchColumn(crs.getMatchColumnIndexes());
      } else {
         CachedRowSetMetaData leftMeta = this.metaData;
         CachedRowSetMetaData rightMeta = (CachedRowSetMetaData)crs.getMetaData();
         int[] leftMatch = leftMeta.getMatchColumns();
         int[] rightMatch = rightMeta.getMatchColumns();
         if (this.rows.size() != 0 && crs.rows.size() != 0) {
            this.validate((CachedRow)this.rows.get(0), leftMatch, (CachedRow)crs.rows.get(0), rightMatch);
         }

         ArrayList left = new ArrayList(this.rows);
         ArrayList right = new ArrayList(crs.rows);
         ArrayList match = null;
         if (this.joinType == 0) {
            match = this.crossJoin(left, right);
         } else {
            Collections.sort(left, new JoinSorter(leftMatch, leftMatch));
            Collections.sort(right, new JoinSorter(rightMatch, rightMatch));
            if (this.joinType == 1) {
               match = this.innerJoin(left, right, new JoinSorter(leftMatch, rightMatch));
            } else if (this.joinType == 2) {
               match = this.leftJoin(left, right, new JoinSorter(leftMatch, rightMatch));
            } else if (this.joinType == 3) {
               match = this.rightJoin(left, right, new JoinSorter(leftMatch, rightMatch));
            } else if (this.joinType == 4) {
               match = this.fullJoin(left, right, new JoinSorter(leftMatch, rightMatch));
            }
         }

         int leftColumnCount = this.metaData.getColumnCount();
         this.metaData.addColumns(crs.getMetaData());
         ArrayList newRows = new ArrayList();

         int i;
         for(i = 0; i < match.size(); ++i) {
            MatchInfo m = (MatchInfo)match.get(i);
            CachedRow leftRow = m.getLeft();
            CachedRow rightRow = m.getRight();
            CachedRow newRow = new CachedRow(this.metaData);
            newRow.copyFrom(leftRow);
            newRow.copyFrom(leftColumnCount, rightRow);
            newRows.add(newRow);
         }

         this.allrows = newRows;
         this.filter();

         for(i = 0; i < leftMatch.length; ++i) {
            if (this.where != null) {
               this.where = this.where + " AND ";
            }

            this.where = leftMeta.getQualifiedColumnName(leftMatch[i]) + "=" + rightMeta.getQualifiedColumnName(rightMatch[i]);
         }
      }

      this.rowSets.add(rowset);
   }

   public void addRowSet(RowSet rowset, int columnIdx) throws SQLException {
      CachedRowSetImpl crs = null;
      if (rowset instanceof CachedRowSetImpl) {
         crs = (CachedRowSetImpl)rowset;
      } else {
         if (!(rowset instanceof RowSet)) {
            throw new SQLException(rowset + " can not be added into JoinRowSet.");
         }

         crs = new CachedRowSetImpl();
         crs.populate((ResultSet)rowset);
      }

      crs.setMatchColumn(new int[]{columnIdx});
      this.addRowSet(crs);
   }

   public void addRowSet(RowSet rowset, String columnName) throws SQLException {
      CachedRowSetImpl crs = null;
      if (rowset instanceof CachedRowSetImpl) {
         crs = (CachedRowSetImpl)rowset;
      } else {
         if (!(rowset instanceof RowSet)) {
            throw new SQLException(rowset + " can not be added into JoinRowSet.");
         }

         crs = new CachedRowSetImpl();
         crs.populate((ResultSet)rowset);
      }

      crs.setMatchColumn(new String[]{columnName});
      this.addRowSet(crs);
   }

   public void addRowSet(RowSet[] rowset, int[] columnIdx) throws SQLException {
      for(int i = 0; i < rowset.length; ++i) {
         this.addRowSet(rowset[i], columnIdx[i]);
      }

   }

   public void addRowSet(RowSet[] rowset, String[] columnName) throws SQLException {
      for(int i = 0; i < rowset.length; ++i) {
         this.addRowSet(rowset[i], columnName[i]);
      }

   }

   public Collection getRowSets() throws SQLException {
      return this.rowSets;
   }

   public String[] getRowSetNames() throws SQLException {
      String[] names = new String[this.rowSets.size()];
      Iterator it = this.rowSets.iterator();

      for(int i = 0; it.hasNext(); names[i++] = ((CachedRowSetImpl)it.next()).getTableName()) {
      }

      return names;
   }

   public CachedRowSet toCachedRowSet() throws SQLException {
      CachedRowSetImpl crs = new CachedRowSetImpl();
      crs.populate((ResultSet)this);
      return crs;
   }

   public boolean supportsCrossJoin() {
      return true;
   }

   public boolean supportsInnerJoin() {
      return true;
   }

   public boolean supportsLeftOuterJoin() {
      return true;
   }

   public boolean supportsRightOuterJoin() {
      return true;
   }

   public boolean supportsFullJoin() {
      return true;
   }

   public int getJoinType() {
      return this.joinType;
   }

   public void setJoinType(int joinType) throws SQLException {
      this.joinType = joinType;
   }

   public String getWhereClause() throws SQLException {
      return this.where;
   }

   ArrayList crossJoin(ArrayList left, ArrayList right) {
      ArrayList result = new ArrayList();
      int k = false;

      for(int i = 0; i < left.size(); ++i) {
         for(int j = 0; j < right.size(); ++j) {
            result.add(new MatchInfo((CachedRow)left.get(i), (CachedRow)right.get(j)));
         }
      }

      return result;
   }

   ArrayList innerJoin(ArrayList l, ArrayList r, JoinSorter c) {
      ArrayList result = new ArrayList();
      if (l != null && r != null && l.size() > 0 && r.size() > 0) {
         int lowerPos = 0;
         int higherPos = 0;
         ArrayList lowerData;
         ArrayList higherData;
         if (c.compare(l.get(l.size() - 1), r.get(r.size() - 1)) < 0) {
            lowerData = l;
            higherData = r;
            c.exchange();
         } else {
            lowerData = r;
            higherData = l;
         }

         while(lowerPos < lowerData.size() && higherPos < higherData.size()) {
            int comp;
            for(comp = c.compare(higherData.get(higherPos), lowerData.get(lowerPos)); comp < 0; comp = c.compare(higherData.get(higherPos), lowerData.get(lowerPos))) {
               ++higherPos;
            }

            if (comp != 0) {
               ++lowerPos;
            } else {
               ArrayList highs = new ArrayList();
               ArrayList lowes = new ArrayList();

               do {
                  highs.add(higherData.get(higherPos));
                  ++higherPos;
               } while(higherPos < higherData.size() && c.compare(higherData.get(higherPos), lowerData.get(lowerPos)) == 0);

               do {
                  lowes.add(lowerData.get(lowerPos));
                  ++lowerPos;
               } while(lowerPos < lowerData.size() && c.compare(higherData.get(higherPos - 1), lowerData.get(lowerPos)) == 0);

               if (l == higherData) {
                  result.addAll(this.crossJoin(highs, lowes));
               } else {
                  result.addAll(this.crossJoin(lowes, highs));
               }
            }
         }

         return result;
      } else {
         return result;
      }
   }

   ArrayList leftJoin(ArrayList l, ArrayList r, JoinSorter c) {
      ArrayList result = new ArrayList();
      if (l != null && l.size() > 0) {
         int lowerPos;
         if (r != null && r.size() > 0) {
            lowerPos = 0;
            int higherPos = 0;
            ArrayList lowerData;
            ArrayList higherData;
            if (c.compare(l.get(l.size() - 1), r.get(r.size() - 1)) < 0) {
               lowerData = l;
               higherData = r;
               c.exchange();
            } else {
               lowerData = r;
               higherData = l;
            }

            while(lowerPos < lowerData.size() && higherPos < higherData.size()) {
               int comp;
               for(comp = c.compare(higherData.get(higherPos), lowerData.get(lowerPos)); comp < 0; comp = c.compare(higherData.get(higherPos), lowerData.get(lowerPos))) {
                  if (higherData == l) {
                     result.add(new MatchInfo((CachedRow)higherData.get(higherPos), (CachedRow)null));
                  }

                  ++higherPos;
               }

               if (comp != 0) {
                  if (lowerData == l) {
                     result.add(new MatchInfo((CachedRow)lowerData.get(lowerPos), (CachedRow)null));
                  }

                  ++lowerPos;
               } else {
                  ArrayList highs = new ArrayList();
                  ArrayList lowes = new ArrayList();

                  do {
                     highs.add(higherData.get(higherPos));
                     ++higherPos;
                  } while(higherPos < higherData.size() && c.compare(higherData.get(higherPos), lowerData.get(lowerPos)) == 0);

                  do {
                     lowes.add(lowerData.get(lowerPos));
                     ++lowerPos;
                  } while(lowerPos < lowerData.size() && c.compare(higherData.get(higherPos - 1), lowerData.get(lowerPos)) == 0);

                  if (l == higherData) {
                     result.addAll(this.crossJoin(highs, lowes));
                  } else {
                     result.addAll(this.crossJoin(lowes, highs));
                  }
               }
            }

            if (lowerData == l) {
               while(lowerPos < lowerData.size()) {
                  result.add(new MatchInfo((CachedRow)lowerData.get(lowerPos++), (CachedRow)null));
               }
            } else {
               while(higherPos < higherData.size()) {
                  result.add(new MatchInfo((CachedRow)higherData.get(higherPos++), (CachedRow)null));
               }
            }

            return result;
         } else {
            for(lowerPos = 0; lowerPos < l.size(); ++lowerPos) {
               result.add(new MatchInfo((CachedRow)l.get(lowerPos), (CachedRow)null));
            }

            return result;
         }
      } else {
         return result;
      }
   }

   ArrayList rightJoin(ArrayList l, ArrayList r, JoinSorter c) {
      ArrayList result = new ArrayList();
      if (r != null && r.size() > 0) {
         int lowerPos;
         if (l != null && l.size() > 0) {
            lowerPos = 0;
            int higherPos = 0;
            ArrayList lowerData;
            ArrayList higherData;
            if (c.compare(l.get(l.size() - 1), r.get(r.size() - 1)) < 0) {
               lowerData = l;
               higherData = r;
               c.exchange();
            } else {
               lowerData = r;
               higherData = l;
            }

            while(lowerPos < lowerData.size() && higherPos < higherData.size()) {
               int comp;
               for(comp = c.compare(higherData.get(higherPos), lowerData.get(lowerPos)); comp < 0; comp = c.compare(higherData.get(higherPos), lowerData.get(lowerPos))) {
                  if (higherData == r) {
                     result.add(new MatchInfo((CachedRow)null, (CachedRow)higherData.get(higherPos)));
                  }

                  ++higherPos;
               }

               if (comp != 0) {
                  if (lowerData == r) {
                     result.add(new MatchInfo((CachedRow)null, (CachedRow)lowerData.get(lowerPos)));
                  }

                  ++lowerPos;
               } else {
                  ArrayList highs = new ArrayList();
                  ArrayList lowes = new ArrayList();

                  do {
                     highs.add(higherData.get(higherPos));
                     ++higherPos;
                  } while(higherPos < higherData.size() && c.compare(higherData.get(higherPos), lowerData.get(lowerPos)) == 0);

                  do {
                     lowes.add(lowerData.get(lowerPos));
                     ++lowerPos;
                  } while(lowerPos < lowerData.size() && c.compare(higherData.get(higherPos - 1), lowerData.get(lowerPos)) == 0);

                  if (l == higherData) {
                     result.addAll(this.crossJoin(highs, lowes));
                  } else {
                     result.addAll(this.crossJoin(lowes, highs));
                  }
               }
            }

            if (lowerData == r) {
               while(lowerPos < lowerData.size()) {
                  result.add(new MatchInfo((CachedRow)null, (CachedRow)lowerData.get(lowerPos++)));
               }
            } else {
               while(higherPos < higherData.size()) {
                  result.add(new MatchInfo((CachedRow)null, (CachedRow)higherData.get(higherPos++)));
               }
            }

            return result;
         } else {
            for(lowerPos = 0; lowerPos < r.size(); ++lowerPos) {
               result.add(new MatchInfo((CachedRow)null, (CachedRow)r.get(lowerPos)));
            }

            return result;
         }
      } else {
         return result;
      }
   }

   ArrayList fullJoin(ArrayList l, ArrayList r, JoinSorter c) {
      ArrayList result = new ArrayList();
      int lowerPos;
      if (r != null && r.size() > 0) {
         if (l != null && l.size() > 0) {
            lowerPos = 0;
            int higherPos = 0;
            ArrayList lowerData;
            ArrayList higherData;
            if (c.compare(l.get(l.size() - 1), r.get(r.size() - 1)) < 0) {
               lowerData = l;
               higherData = r;
               c.exchange();
            } else {
               lowerData = r;
               higherData = l;
            }

            while(lowerPos < lowerData.size() && higherPos < higherData.size()) {
               int comp;
               for(comp = c.compare(higherData.get(higherPos), lowerData.get(lowerPos)); comp < 0; comp = c.compare(higherData.get(higherPos), lowerData.get(lowerPos))) {
                  if (higherData == l) {
                     result.add(new MatchInfo((CachedRow)higherData.get(higherPos), (CachedRow)null));
                  } else {
                     result.add(new MatchInfo((CachedRow)null, (CachedRow)higherData.get(higherPos)));
                  }

                  ++higherPos;
               }

               if (comp != 0) {
                  if (lowerData == l) {
                     result.add(new MatchInfo((CachedRow)lowerData.get(lowerPos), (CachedRow)null));
                  } else {
                     result.add(new MatchInfo((CachedRow)null, (CachedRow)lowerData.get(lowerPos)));
                  }

                  ++lowerPos;
               } else {
                  ArrayList highs = new ArrayList();
                  ArrayList lowes = new ArrayList();

                  do {
                     highs.add(higherData.get(higherPos));
                     ++higherPos;
                  } while(higherPos < higherData.size() && c.compare(higherData.get(higherPos), lowerData.get(lowerPos)) == 0);

                  do {
                     lowes.add(lowerData.get(lowerPos));
                     ++lowerPos;
                  } while(lowerPos < lowerData.size() && c.compare(higherData.get(higherPos - 1), lowerData.get(lowerPos)) == 0);

                  if (l == higherData) {
                     result.addAll(this.crossJoin(highs, lowes));
                  } else {
                     result.addAll(this.crossJoin(lowes, highs));
                  }
               }
            }

            if (lowerData == l) {
               while(lowerPos < lowerData.size()) {
                  result.add(new MatchInfo((CachedRow)lowerData.get(lowerPos++), (CachedRow)null));
               }

               while(higherPos < higherData.size()) {
                  result.add(new MatchInfo((CachedRow)null, (CachedRow)higherData.get(higherPos++)));
               }
            } else {
               while(lowerPos < lowerData.size()) {
                  result.add(new MatchInfo((CachedRow)null, (CachedRow)lowerData.get(lowerPos++)));
               }

               while(higherPos < higherData.size()) {
                  result.add(new MatchInfo((CachedRow)higherData.get(higherPos++), (CachedRow)null));
               }
            }

            return result;
         } else {
            for(lowerPos = 0; lowerPos < r.size(); ++lowerPos) {
               result.add(new MatchInfo((CachedRow)null, (CachedRow)r.get(lowerPos)));
            }

            return result;
         }
      } else {
         if (l != null) {
            for(lowerPos = 0; lowerPos < l.size(); ++lowerPos) {
               result.add(new MatchInfo((CachedRow)l.get(lowerPos), (CachedRow)null));
            }
         }

         return result;
      }
   }

   public boolean previousPage() throws SQLException {
      throw new SQLException("This is not supported for JoinRowSet");
   }

   public boolean nextPage() throws SQLException {
      throw new SQLException("This is not supported for JoinRowSet");
   }

   public void populate(ResultSet rs, int i) throws SQLException {
      throw new SQLException("This is not supported for JoinRowSet");
   }

   public void rowSetPopulated(RowSetEvent rse, int i) throws SQLException {
      throw new SQLException("This is not supported for JoinRowSet");
   }

   public Object getObject(int columnIndex, Class type) throws SQLException {
      throw new SQLFeatureNotSupportedException();
   }

   public Object getObject(String columnLabel, Class type) throws SQLException {
      throw new SQLFeatureNotSupportedException();
   }

   class JoinSorter implements Comparator {
      int[] leftCols = null;
      int[] rightCols = null;

      public JoinSorter(int[] l, int[] r) {
         this.leftCols = l;
         this.rightCols = r;
      }

      void exchange() {
         int[] temp = this.leftCols;
         this.leftCols = this.rightCols;
         this.rightCols = temp;
      }

      public final int compare(Object a, Object b) {
         int retx = false;

         for(int i = 0; i < this.leftCols.length; ++i) {
            Object val1;
            Object val2;
            try {
               val1 = ((CachedRow)a).getColumn(this.leftCols[i]);
               val2 = ((CachedRow)b).getColumn(this.rightCols[i]);
            } catch (Throwable var9) {
               throw new RuntimeException("Failed to retrieve the object to compare.");
            }

            try {
               if (val1 == null && val2 == null) {
                  return 0;
               }

               if (val1 == null) {
                  return -1;
               }

               if (val2 == null) {
                  return 1;
               }

               Class c1 = val1.getClass();
               Class c2 = val2.getClass();
               int ret;
               if (c1 == c2 && Comparable.class.isAssignableFrom(c1)) {
                  ret = ((Comparable)val1).compareTo((Comparable)val2);
               } else if (!Number.class.isAssignableFrom(c1) && !Number.class.isAssignableFrom(c2)) {
                  if (!Date.class.isAssignableFrom(c1) && !Date.class.isAssignableFrom(c2) && !Time.class.isAssignableFrom(c1) && !Time.class.isAssignableFrom(c2) && !Timestamp.class.isAssignableFrom(c1) && !Timestamp.class.isAssignableFrom(c2)) {
                     if (!Boolean.class.isAssignableFrom(c1) && !Boolean.class.isAssignableFrom(c2)) {
                        throw new RuntimeException(c1 + " can not be joined with " + c2);
                     }

                     if (String.class.isAssignableFrom(c1)) {
                        val1 = new Boolean(val1.toString().trim());
                     }

                     if (String.class.isAssignableFrom(c2)) {
                        val2 = new Boolean(val2.toString().trim());
                     }

                     ret = ((Comparable)val1).compareTo((Comparable)val2);
                  } else {
                     if (String.class.isAssignableFrom(c1)) {
                        val1 = Date.valueOf((String)val1);
                     }

                     if (String.class.isAssignableFrom(c2)) {
                        val2 = Date.valueOf((String)val2);
                     }

                     ret = ((Comparable)val1).compareTo((Comparable)val2);
                  }
               } else {
                  if (Number.class.isAssignableFrom(c1) || String.class.isAssignableFrom(c1)) {
                     val1 = new BigDecimal(val1.toString().trim());
                  }

                  if (Number.class.isAssignableFrom(c2) || String.class.isAssignableFrom(c2)) {
                     val2 = new BigDecimal(val2.toString().trim());
                  }

                  ret = ((Comparable)val1).compareTo((Comparable)val2);
               }

               if (ret != 0) {
                  return ret;
               }
            } catch (Throwable var10) {
               throw new RuntimeException(val1.getClass() + " can not be joined with " + val2.getClass());
            }
         }

         return 0;
      }
   }

   class MatchInfo {
      CachedRow left;
      CachedRow right;

      MatchInfo(CachedRow l, CachedRow r) {
         this.left = l;
         this.right = r;
      }

      CachedRow getLeft() {
         return this.left;
      }

      CachedRow getRight() {
         return this.right;
      }
   }
}
