package weblogic.ejb.container.cmp11.rdbms.finders;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public final class WLQLExpression implements WLQLExpressionTypes, Serializable {
   private static final long serialVersionUID = -1319193628507182620L;
   private int type;
   private List terms = new ArrayList();
   private String sval;
   private String specialName;

   public WLQLExpression(int type) {
      this.type = type;
   }

   protected WLQLExpression(int type, WLQLExpression expr1, WLQLExpression expr2) {
      this.type = type;
      this.addTerm(expr1);
      this.addTerm(expr2);
   }

   protected WLQLExpression(int type, WLQLExpression expr) {
      this.type = type;
      this.addTerm(expr);
   }

   protected WLQLExpression(int type, List terms) {
      this.type = type;
      this.terms.addAll(terms);
   }

   protected WLQLExpression(int type, String lit) {
      this.type = type;
      this.sval = lit;
   }

   protected WLQLExpression(String specialName, List terms) {
      this.type = 12;
      this.specialName = specialName;
      this.terms.addAll(terms);
   }

   public int type() {
      return this.type;
   }

   public int getType() {
      return this.type;
   }

   public void addTerm(WLQLExpression q) {
      this.terms.add(q);
   }

   public int numTerms() {
      return this.terms.size();
   }

   public WLQLExpression term(int idx) {
      return (WLQLExpression)this.terms.get(idx);
   }

   public Iterator getTerms() {
      return this.terms.iterator();
   }

   public String getSval() {
      return this.sval;
   }

   public void setSval(String s) {
      this.sval = s;
   }

   public String getSpecialName() {
      return this.specialName;
   }

   public void setSpecialName(String s) {
      this.specialName = s;
   }

   public boolean equals(Object other) {
      if (!(other instanceof WLQLExpression)) {
         return false;
      } else {
         WLQLExpression wlql = (WLQLExpression)other;
         if (this.type != wlql.getType()) {
            return false;
         } else {
            switch (this.type) {
               case 9:
               case 10:
               case 11:
               case 13:
                  if (!this.sval.equals(wlql.getSval())) {
                     return false;
                  }
                  break;
               case 12:
                  if (!this.specialName.equals(wlql.getSpecialName())) {
                     return false;
                  }
            }

            Iterator myTerms = this.getTerms();
            Iterator otherTerms = wlql.getTerms();

            WLQLExpression myItem;
            WLQLExpression otherItem;
            do {
               if (!myTerms.hasNext()) {
                  if (otherTerms.hasNext()) {
                     return false;
                  }

                  return true;
               }

               if (!otherTerms.hasNext()) {
                  return false;
               }

               myItem = (WLQLExpression)myTerms.next();
               otherItem = (WLQLExpression)otherTerms.next();
            } while(myItem.equals(otherItem));

            return false;
         }
      }
   }

   public int hashCode() {
      int hc = this.type;
      if (this.sval != null) {
         hc ^= this.sval.hashCode();
      }

      return hc;
   }

   public void dump() {
      System.out.println("DUMPING");
      dump(this, 0);
   }

   private static void dump(WLQLExpression q, int depth) {
      for(int i = 0; i < depth; ++i) {
         System.out.print("| ");
      }

      System.out.print(TYPE_NAMES[q.type]);
      switch (q.type()) {
         case 9:
            System.out.println(" -- " + q.getSval());
            break;
         case 10:
            System.out.println(" -- \"" + q.getSval() + "\"");
            break;
         case 11:
            System.out.println(" -- " + q.getSval());
            break;
         case 12:
            System.out.println("-- " + q.getSpecialName());
            break;
         case 13:
            System.out.println("-- " + q.getSval());
            break;
         default:
            System.out.println();
      }

      Iterator otherTerms = q.getTerms();

      while(otherTerms.hasNext()) {
         WLQLExpression t = (WLQLExpression)otherTerms.next();
         dump(t, depth + 1);
      }

   }
}
