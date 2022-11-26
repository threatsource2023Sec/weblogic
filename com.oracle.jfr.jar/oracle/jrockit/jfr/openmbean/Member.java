package oracle.jrockit.jfr.openmbean;

import java.util.Date;
import javax.management.openmbean.CompositeData;
import javax.management.openmbean.OpenType;

class Member {
   final String name;
   final String description;
   final OpenType openType;

   public Member(String name, String description, OpenType openType) {
      this.name = name;
      this.description = description;
      this.openType = openType;
   }

   final Object get(CompositeData d) {
      return d.get(this.name);
   }

   final String getString(CompositeData d) {
      return (String)this.get(d);
   }

   final Date getDate(CompositeData d) {
      return (Date)this.get(d);
   }

   final int getInt(CompositeData d) {
      return (Integer)this.get(d);
   }

   final long getLong(CompositeData d) {
      return (Long)this.get(d);
   }

   final boolean getBoolean(CompositeData d) {
      return (Boolean)this.get(d);
   }
}
