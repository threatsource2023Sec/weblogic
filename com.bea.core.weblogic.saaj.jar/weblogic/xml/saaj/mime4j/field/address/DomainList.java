package weblogic.xml.saaj.mime4j.field.address;

import java.util.ArrayList;

public class DomainList {
   private ArrayList domains;

   public DomainList(ArrayList domains, boolean dontCopy) {
      if (domains != null) {
         this.domains = dontCopy ? domains : (ArrayList)domains.clone();
      } else {
         this.domains = new ArrayList(0);
      }

   }

   public int size() {
      return this.domains.size();
   }

   public String get(int index) {
      if (0 <= index && this.size() > index) {
         return (String)this.domains.get(index);
      } else {
         throw new IndexOutOfBoundsException();
      }
   }

   public String toRouteString() {
      StringBuffer out = new StringBuffer();

      for(int i = 0; i < this.domains.size(); ++i) {
         out.append("@");
         out.append(this.get(i));
         if (i + 1 < this.domains.size()) {
            out.append(",");
         }
      }

      return out.toString();
   }
}
