package monfox.toolkit.snmp.v3.usm;

import java.io.Serializable;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;
import java.util.Vector;
import monfox.toolkit.snmp.engine.SnmpEngineID;

public class USMEngineMap implements Serializable {
   private Hashtable a = new Hashtable();
   private static final String b = "$Id: USMEngineMap.java,v 1.9 2011/02/09 01:58:15 sking Exp $";

   public void add(USMEngineInfo var1) {
      this.a.put(var1.getEngineID(), var1);
   }

   public USMEngineInfo get(SnmpEngineID var1) {
      return var1 == null ? null : (USMEngineInfo)this.a.get(var1);
   }

   public String toString() {
      return this.a.toString();
   }

   public Set getEngineIDs() {
      return this.a.keySet();
   }

   public List getEngineInfoList() {
      Vector var1 = new Vector();
      var1.addAll(this.a.values());
      return var1;
   }
}
