package kodo.kernel.jdoql;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.openjpa.kernel.Filters;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.util.InternalException;
import org.apache.openjpa.util.UserException;
import serp.util.Numbers;

public class SingleString implements Serializable, Cloneable {
   private static final int KEY_SELECT = 0;
   private static final int KEY_INTO = 1;
   private static final int KEY_FROM = 2;
   private static final int KEY_WHERE = 3;
   private static final int KEY_VARS = 4;
   private static final int KEY_PARAMS = 5;
   private static final int KEY_IMPORTS = 6;
   private static final int KEY_IMPORT = 7;
   private static final int KEY_GROUP = 8;
   private static final int KEY_ORDER = 9;
   private static final int KEY_RANGE = 10;
   private static final Map KEYS = new HashMap();
   private static final Localizer _loc;
   public Boolean unique = null;
   public String result = null;
   public String resultType = null;
   public String candidateType = null;
   public boolean subclasses = true;
   public String candidateAlias = null;
   public String filter = null;
   public String parameters = null;
   public String variables = null;
   public String imports = null;
   public String grouping = null;
   public String ordering = null;
   public String range = null;

   public String toString() {
      StringBuffer buf = new StringBuffer(100);
      buf.append("select");
      if (this.unique != null && this.unique) {
         buf.append(" unique");
      }

      if (this.result != null) {
         buf.append(" ").append(this.result);
      }

      if (this.resultType != null) {
         buf.append(" into ").append(this.resultType);
      }

      if (this.candidateType != null) {
         buf.append(" from ").append(this.candidateType);
         if (this.candidateAlias != null) {
            buf.append(" ").append(this.candidateAlias);
         }

         if (!this.subclasses) {
            buf.append(" exclude subclasses");
         }
      }

      if (this.filter != null) {
         buf.append(" where ").append(this.filter);
      }

      if (this.variables != null) {
         buf.append(" variables ").append(this.variables);
      }

      if (this.parameters != null) {
         buf.append(" parameters ").append(this.parameters);
      }

      if (this.imports != null) {
         buf.append(" ").append(this.imports);
      }

      if (this.grouping != null) {
         buf.append(" group by ").append(this.grouping);
      }

      if (this.ordering != null) {
         buf.append(" order by ").append(this.ordering);
      }

      if (this.range != null) {
         buf.append(" range ").append(this.range);
      }

      return buf.toString();
   }

   public Object clone() {
      try {
         return super.clone();
      } catch (CloneNotSupportedException var2) {
         return null;
      }
   }

   public void fromString(String query) {
      if (query != null && (!query.startsWith("select") && !query.startsWith("SELECT") || query.length() > 6 && query.charAt(6) != ' ')) {
         query = "select where " + query;
      }

      this.fromWords(query, Filters.splitExpressions(query, ' ', 25));
   }

   public void fromWords(String query, List words) {
      this.reset();
      if (words != null && !words.isEmpty()) {
         if (!"select".equalsIgnoreCase((String)words.get(0))) {
            throw new UserException(_loc.get("bad-start-word", query));
         } else if (words.size() != 1) {
            int pos = 1;
            if ("unique".equalsIgnoreCase((String)words.get(pos))) {
               this.unique = Boolean.TRUE;
               ++pos;
            }

            int last = 0;
            int from = pos;
            boolean insideSubQuery = false;

            for(int parendepth = 0; pos < words.size(); ++pos) {
               String word = ((String)words.get(pos)).toLowerCase();
               if (!insideSubQuery && "(".equals(word) && "select".equals((String)words.get(pos + 1))) {
                  insideSubQuery = true;
                  parendepth = 0;
               }

               if (insideSubQuery) {
                  if ("(".equals(word)) {
                     ++parendepth;
                  } else if (")".equals(word)) {
                     --parendepth;
                     if (parendepth == 0) {
                        insideSubQuery = false;
                     }
                  }
               } else {
                  Integer key = (Integer)KEYS.get(word);
                  if (key != null && (key != 7 || last != 7 && last != 7)) {
                     if (key < last && (key != 4 || last != 5)) {
                        throw new UserException(_loc.get("bad-word-order", query, words.get(pos)));
                     }

                     int to;
                     if (last < 4 && "with".equalsIgnoreCase((String)words.get(pos - 1))) {
                        to = pos - 1;
                     } else {
                        to = pos;
                     }

                     if (from == pos || this.setLast(last, words, from, to, query)) {
                        last = key;
                        from = pos + 1;
                     }
                  }
               }
            }

            if (pos > from) {
               this.setLast(last, words, from, pos, query);
            }

         }
      }
   }

   private void reset() {
      this.unique = null;
      this.result = null;
      this.resultType = null;
      this.candidateType = null;
      this.subclasses = true;
      this.candidateAlias = null;
      this.filter = null;
      this.parameters = null;
      this.variables = null;
      this.imports = null;
      this.grouping = null;
      this.ordering = null;
      this.range = null;
   }

   private boolean setLast(int last, List words, int from, int to, String query) {
      switch (last) {
         case 0:
            this.result = combine(words, from, to);
            return true;
         case 1:
            this.resultType = combine(words, from, to);
            return true;
         case 2:
            return this.setFrom(words, from, to, query);
         case 3:
            this.filter = combine(words, from, to);
            return true;
         case 4:
            this.variables = combine(words, from, to);
            return true;
         case 5:
            this.parameters = combine(words, from, to);
            return true;
         case 6:
         case 7:
            return this.setImports(words, from, to);
         case 8:
            if ("by".equalsIgnoreCase((String)words.get(from)) && to - from >= 2) {
               this.grouping = combine(words, from + 1, to);
               return true;
            }

            return false;
         case 9:
            if ("by".equalsIgnoreCase((String)words.get(from)) && to - from >= 2) {
               this.ordering = combine(words, from + 1, to);
               return true;
            }

            return false;
         case 10:
            this.range = combine(words, from, to);
            return true;
         default:
            throw new InternalException();
      }
   }

   private boolean setFrom(List words, int from, int to, String query) {
      int idx = from + 1;
      this.candidateType = (String)words.get(from);
      if (idx == to) {
         this.subclasses = true;
      } else {
         if (!"exclude".equalsIgnoreCase((String)words.get(idx))) {
            this.candidateAlias = (String)words.get(idx++);
         }

         if (idx == to) {
            this.subclasses = true;
         } else {
            if (!"exclude".equalsIgnoreCase((String)words.get(idx++)) || !"subclasses".equalsIgnoreCase((String)words.get(idx))) {
               throw new UserException(_loc.get("bad-from", combine(words, from, to), query));
            }

            this.subclasses = false;
         }
      }

      return true;
   }

   private boolean setImports(List words, int from, int to) {
      this.imports = combine(words, from, to);
      if (!"import".equals(words.get(from))) {
         this.imports = "import " + this.imports;
      }

      return true;
   }

   private static String combine(List words, int from, int to) {
      if (to <= from) {
         return null;
      } else if (to == from + 1) {
         return (String)words.get(from);
      } else {
         int len = 0;

         for(int i = from; i < to; ++i) {
            len += ((String)words.get(i)).length();
         }

         StringBuffer buf = new StringBuffer(len + to - from - 1);

         for(int i = from; i < to; ++i) {
            if (i > from) {
               buf.append(" ");
            }

            buf.append(words.get(i));
         }

         return buf.toString();
      }
   }

   static {
      KEYS.put("into", Numbers.valueOf(1));
      KEYS.put("from", Numbers.valueOf(2));
      KEYS.put("where", Numbers.valueOf(3));
      KEYS.put("variables", Numbers.valueOf(4));
      KEYS.put("parameters", Numbers.valueOf(5));
      KEYS.put("imports", Numbers.valueOf(6));
      KEYS.put("import", Numbers.valueOf(7));
      KEYS.put("group", Numbers.valueOf(8));
      KEYS.put("order", Numbers.valueOf(9));
      KEYS.put("range", Numbers.valueOf(10));
      _loc = Localizer.forPackage(SingleString.class);
   }
}
