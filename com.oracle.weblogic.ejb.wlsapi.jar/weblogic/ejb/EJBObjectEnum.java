package weblogic.ejb;

import java.io.Serializable;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Vector;

public final class EJBObjectEnum implements Enumeration, Serializable {
   private static final long serialVersionUID = 2670533090316944880L;
   private Vector v = new Vector();
   private transient int index;

   public EJBObjectEnum() {
   }

   public EJBObjectEnum(Collection objs) {
      this.v = new Vector(objs);
   }

   public void addElement(Object obj) {
      this.v.addElement(obj);
   }

   public boolean hasMoreElements() {
      return this.index < this.v.size();
   }

   public Object nextElement() {
      return this.v.elementAt(this.index++);
   }

   public Object clone() {
      EJBObjectEnum e = new EJBObjectEnum();
      e.v = this.v;
      return e;
   }
}
