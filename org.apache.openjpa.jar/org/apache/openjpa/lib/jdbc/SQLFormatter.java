package org.apache.openjpa.lib.jdbc;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;
import org.apache.openjpa.lib.util.J2DoPrivHelper;

public class SQLFormatter {
   private boolean multiLine = false;
   private boolean doubleSpace = true;
   private String newline = J2DoPrivHelper.getLineSeparator();
   private int lineLength = 72;
   private String wrapIndent = "        ";
   private String clauseIndent = "    ";
   private static final String[] selectSeparators = new String[]{"FROM ", "WHERE ", "ORDER BY ", "GROUP BY ", "HAVING "};
   private static final String[] insertSeparators = new String[]{"VALUES "};
   private static final String[] updateSeparators = new String[]{"SET ", "WHERE "};
   private static final String[] deleteSeparators = new String[]{"WHERE "};
   private static final String[] createTableSeparators = new String[]{"( "};
   private static final String[] createIndexSeparators = new String[]{"ON ", "( "};

   public void setNewline(String val) {
      this.newline = val;
   }

   public String getNewline() {
      return this.newline;
   }

   public void setLineLength(int val) {
      this.lineLength = val;
   }

   public int getLineLength() {
      return this.lineLength;
   }

   public void setWrapIndent(String val) {
      this.wrapIndent = val;
   }

   public String getWrapIndent() {
      return this.wrapIndent;
   }

   public void setClauseIndent(String val) {
      this.clauseIndent = val;
   }

   public String getClauseIndent() {
      return this.clauseIndent;
   }

   public void setMultiLine(boolean multiLine) {
      this.multiLine = multiLine;
   }

   public boolean getMultiLine() {
      return this.multiLine;
   }

   public void setDoubleSpace(boolean doubleSpace) {
      this.doubleSpace = doubleSpace;
   }

   public boolean getDoubleSpace() {
      return this.doubleSpace;
   }

   public Object prettyPrint(Object sqlObject) {
      if (!this.multiLine) {
         return this.prettyPrintLine(sqlObject);
      } else {
         StringBuffer sql = new StringBuffer(sqlObject.toString());
         StringBuffer buf = new StringBuffer(sql.length());

         while(sql.length() > 0) {
            String line = null;
            int index = Math.max(sql.toString().indexOf(";\n"), sql.toString().indexOf(";\r"));
            if (index == -1) {
               line = sql.toString();
            } else {
               line = sql.substring(0, index + 2);
            }

            sql.delete(0, line.length());
            buf.append(this.prettyPrintLine(line));

            for(int i = 0; i < 1 + (this.getDoubleSpace() ? 1 : 0); ++i) {
               buf.append(J2DoPrivHelper.getLineSeparator());
            }
         }

         return buf.toString();
      }
   }

   private Object prettyPrintLine(Object sqlObject) {
      String sql = sqlObject.toString().trim();
      String lowerCaseSql = sql.toLowerCase();
      String[] separators;
      if (lowerCaseSql.startsWith("select")) {
         separators = selectSeparators;
      } else if (lowerCaseSql.startsWith("insert")) {
         separators = insertSeparators;
      } else if (lowerCaseSql.startsWith("update")) {
         separators = updateSeparators;
      } else if (lowerCaseSql.startsWith("delete")) {
         separators = deleteSeparators;
      } else if (lowerCaseSql.startsWith("create table")) {
         separators = createTableSeparators;
      } else if (lowerCaseSql.startsWith("create index")) {
         separators = createIndexSeparators;
      } else {
         separators = new String[0];
      }

      int start = 0;
      int end = true;
      List clauses = new ArrayList();
      clauses.add(new StringBuffer());

      StringBuffer clause;
      for(int i = 0; i < separators.length; ++i) {
         int end = lowerCaseSql.indexOf(" " + separators[i].toLowerCase(), start);
         if (end == -1) {
            break;
         }

         clause = (StringBuffer)clauses.get(clauses.size() - 1);
         clause.append(sql.substring(start, end));
         clause = new StringBuffer();
         clauses.add(clause);
         clause.append(this.clauseIndent);
         clause.append(separators[i]);
         start = end + 1 + separators[i].length();
      }

      clause = (StringBuffer)clauses.get(clauses.size() - 1);
      clause.append(sql.substring(start));
      StringBuffer pp = new StringBuffer(sql.length());
      Iterator iter = clauses.iterator();

      while(iter.hasNext()) {
         pp.append(this.wrapLine(((StringBuffer)iter.next()).toString()));
         if (iter.hasNext()) {
            pp.append(this.newline);
         }
      }

      return pp.toString();
   }

   private String wrapLine(String line) {
      StringBuffer lines = new StringBuffer(line.length());

      for(int i = 0; i < line.length() && (line.charAt(i) == ' ' || line.charAt(i) == '\t'); ++i) {
         lines.append(line.charAt(i));
      }

      StringTokenizer tok = new StringTokenizer(line);
      int length = 0;

      while(tok.hasMoreTokens()) {
         String elem = tok.nextToken();
         length += elem.length();
         if (length >= this.lineLength) {
            lines.append(this.newline);
            lines.append(this.wrapIndent);
            lines.append(elem);
            lines.append(' ');
            length = this.wrapIndent.length() + elem.length() + 1;
         } else if (elem.length() >= this.lineLength) {
            lines.append(elem);
            if (tok.hasMoreTokens()) {
               lines.append(this.newline);
            }

            lines.append(this.wrapIndent);
            length = this.wrapIndent.length();
         } else {
            lines.append(elem);
            lines.append(' ');
            ++length;
         }
      }

      return lines.toString();
   }

   public static void main(String[] args) {
      SQLFormatter formatter = new SQLFormatter();

      for(int i = 0; i < args.length; ++i) {
         System.out.println(formatter.prettyPrint(args[i]));
      }

   }
}
