package oracle.jrockit.jfr.openmbean;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.management.openmbean.CompositeData;
import javax.management.openmbean.CompositeType;
import javax.management.openmbean.OpenDataException;
import javax.management.openmbean.OpenType;

abstract class JFRMBeanType {
   private final String[] names;
   private final CompositeType type;

   JFRMBeanType(String name, String desc, String[] names, String[] descriptions, OpenType[] types) throws OpenDataException {
      this.names = names;
      this.type = new CompositeType(name, desc, names, descriptions, types);
   }

   JFRMBeanType(String name, String desc, Member... members) throws OpenDataException {
      int n = members.length;
      this.names = new String[n];
      String[] descriptions = new String[n];
      OpenType[] types = new OpenType[n];

      for(int i = 0; i < n; ++i) {
         this.names[i] = members[i].name;
         descriptions[i] = members[i].description;
         types[i] = members[i].openType;
      }

      this.type = new CompositeType(name, desc, this.names, descriptions, types);
   }

   public final String[] getNames() {
      return this.names;
   }

   public final CompositeType getType() {
      return this.type;
   }

   public abstract CompositeData toCompositeTypeData(Object var1) throws OpenDataException;

   public List toCompositeData(Collection c) throws OpenDataException {
      ArrayList result = new ArrayList(c.size());
      Iterator i$ = c.iterator();

      while(i$.hasNext()) {
         Object t = i$.next();
         result.add(this.toCompositeTypeData(t));
      }

      return result;
   }

   public Object toJavaTypeData(CompositeData d) throws OpenDataException {
      throw new OpenDataException("Cannot construct java types for " + this.getType().getTypeName());
   }

   public List toJavaTypeData(Collection c) throws OpenDataException {
      ArrayList result = new ArrayList(c.size());
      Iterator i$ = c.iterator();

      while(i$.hasNext()) {
         CompositeData d = (CompositeData)i$.next();
         result.add(this.toJavaTypeData(d));
      }

      return result;
   }

   protected final OpenDataException openDataException(String msg, Throwable t) {
      return (OpenDataException)(new OpenDataException(msg)).initCause(t);
   }

   protected final OpenDataException openDataException(Throwable t) {
      return this.openDataException(t.getMessage(), t);
   }

   protected final boolean booleanAt(CompositeData d, Member m) {
      return (Boolean)d.get(m.name);
   }

   protected final int intAt(CompositeData d, Member m) {
      return ((Number)d.get(m.name)).intValue();
   }

   protected final long longAt(CompositeData d, Member m) {
      return ((Number)d.get(m.name)).longValue();
   }

   protected final String stringAt(CompositeData d, Member m) {
      return (String)d.get(m.name);
   }

   protected final Date dateAt(CompositeData d, Member m) {
      return (Date)d.get(m.name);
   }
}
