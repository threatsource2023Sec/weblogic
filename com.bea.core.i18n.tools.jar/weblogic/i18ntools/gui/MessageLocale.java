package weblogic.i18ntools.gui;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Locale;

public final class MessageLocale implements Comparator, Serializable {
   private static final long serialVersionUID = -4739761307713738456L;
   private Locale myLocale;

   public MessageLocale(Locale loc) {
      this.myLocale = loc;
   }

   public Locale getLocale() {
      return this.myLocale;
   }

   public int compare(Object obj1, Object obj2) {
      if (!(obj1 instanceof MessageLocale)) {
         System.err.println("obj1 not instanceof MessageLocale");
      }

      if (!(obj2 instanceof MessageLocale)) {
         System.err.println("obj2 not instanceof MessageLocale");
      }

      return ((MessageLocale)obj1).getLocale().getDisplayName().compareTo(((MessageLocale)obj2).getLocale().getDisplayName());
   }

   public boolean equals(Object obj) {
      if (!(obj instanceof MessageLocale)) {
         System.err.println("obj not instanceof MessageLocale");
      }

      return this.myLocale.getDisplayName().equals(((MessageLocale)obj).getLocale().getDisplayName());
   }

   public int hashCode() {
      return this.myLocale.getDisplayName().hashCode();
   }
}
