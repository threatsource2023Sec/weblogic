package weblogic.corba.rmic;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

final class Structure {
   public String name;
   public Hashtable elements;
   int indentLevel = 0;
   final String SPACES = "                                                     ";

   public Structure() {
   }

   public String spaces() {
      return "                                                     ".substring(0, this.indentLevel * 2);
   }

   public String toString() {
      StringBuffer sb = new StringBuffer(100);
      sb.append(this.spaces() + "(" + this.name + "\n");

      for(Enumeration e = this.elements.keys(); e.hasMoreElements(); --this.indentLevel) {
         ++this.indentLevel;
         String k = (String)e.nextElement();
         Object o = this.elements.get(k);
         if (o instanceof Structure) {
            sb.append(o);
         } else if (o instanceof String) {
            sb.append(this.spaces() + k + ": " + (String)o + "\n");
         } else if (o instanceof Vector) {
            sb.append(this.spaces() + k + ": [ ");
            Vector v = (Vector)o;
            int len = v.size();

            for(int i = 0; i < len; ++i) {
               sb.append((String)v.elementAt(i) + " ");
            }

            sb.append("]\n");
         }
      }

      sb.append(this.spaces() + ")\n");
      return sb.toString();
   }
}
