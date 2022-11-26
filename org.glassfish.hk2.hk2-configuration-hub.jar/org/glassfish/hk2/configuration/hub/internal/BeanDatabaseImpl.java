package org.glassfish.hk2.configuration.hub.internal;

import java.io.PrintStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import org.glassfish.hk2.configuration.hub.api.BeanDatabase;
import org.glassfish.hk2.configuration.hub.api.Instance;
import org.glassfish.hk2.configuration.hub.api.Type;

public class BeanDatabaseImpl implements BeanDatabase {
   private final long revision;
   private final HashMap types;

   BeanDatabaseImpl(long revision) {
      this.types = new HashMap();
      this.revision = revision;
   }

   BeanDatabaseImpl(long revision, BeanDatabase beanDatabase) {
      this(revision);
      Iterator var4 = beanDatabase.getAllTypes().iterator();

      while(var4.hasNext()) {
         Type type = (Type)var4.next();
         this.types.put(type.getName(), new TypeImpl(type, ((WriteableTypeImpl)type).getHelper()));
      }

   }

   public synchronized Set getAllTypes() {
      return Collections.unmodifiableSet(new HashSet(this.types.values()));
   }

   public synchronized Instance getInstance(String type, String instanceKey) {
      Type t = this.getType(type);
      return t == null ? null : t.getInstance(instanceKey);
   }

   public synchronized Type getType(String type) {
      return (Type)this.types.get(type);
   }

   long getRevision() {
      return this.revision;
   }

   public void dumpDatabase() {
      this.dumpDatabase(System.err);
   }

   public void dumpDatabase(PrintStream output) {
      Utilities.dumpDatabase(this, output);
   }

   public String dumpDatabaseAsString() {
      return Utilities.dumpDatabaseAsString(this);
   }

   public String toString() {
      return "BeanDatabaseImpl(" + this.revision + "," + System.identityHashCode(this) + ")";
   }
}
