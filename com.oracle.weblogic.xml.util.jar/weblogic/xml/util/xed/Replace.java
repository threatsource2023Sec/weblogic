package weblogic.xml.util.xed;

import java.util.ArrayList;
import java.util.Iterator;

public class Replace extends Command {
   private String xml;
   private ArrayList assignments = new ArrayList();

   public void setXML(String xml) {
      this.xml = xml;
   }

   public String getXML() {
      return this.xml;
   }

   public String getName() {
      return "replace";
   }

   public void add(Assignment a) {
      this.assignments.add(a);
   }

   public Object evaluate(Context context) throws StreamEditorException {
      Iterator i = this.assignments.iterator();

      while(i.hasNext()) {
         ((Command)i.next()).evaluate(context);
      }

      return null;
   }

   public String toString() {
      if (this.getXML() != null) {
         return super.toString() + " [" + this.getXML() + "]";
      } else {
         StringBuffer b = new StringBuffer();
         b.append(super.toString() + "\n");
         Iterator i = this.assignments.iterator();

         while(i.hasNext()) {
            b.append("\t[" + i.next().toString() + "]\n");
         }

         return b.toString();
      }
   }
}
