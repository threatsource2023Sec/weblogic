package weblogic.xml.util.xed;

import java.util.ArrayList;
import java.util.Iterator;

public class Assignment extends Command {
   private String name;
   private Variable lhs;
   private ArrayList rhs = new ArrayList();

   public void setName(String name) {
      this.name = name;
   }

   public String getName() {
      return this.name;
   }

   public Object evaluate(Context context) throws StreamEditorException {
      Iterator i = this.rhs.iterator();
      if (!i.hasNext()) {
         throw new StreamEditorException("Evaluation error:invalid right hand side" + this.toString());
      } else {
         String result;
         for(result = (String)((Variable)i.next()).evaluate(context); i.hasNext(); result = result + (String)((Variable)i.next()).evaluate(context)) {
         }

         this.lhs.assign(result, context);
         return this.lhs;
      }
   }

   public Variable getLHS() {
      return this.lhs;
   }

   public ArrayList getRHS() {
      return this.rhs;
   }

   public void setLHS(Variable v) {
      this.lhs = v;
   }

   public void addRHS(Variable v) {
      this.rhs.add(v);
   }

   public String toString() {
      StringBuffer b = new StringBuffer();
      b.append(this.lhs + "=");
      Iterator i = this.rhs.iterator();
      if (i.hasNext()) {
         b.append(i.next().toString());
      }

      while(i.hasNext()) {
         b.append("+" + i.next().toString());
      }

      return b.toString();
   }
}
