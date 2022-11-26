package org.python.bouncycastle.crypto.tls;

import java.math.BigInteger;
import java.util.Vector;
import org.python.bouncycastle.crypto.agreement.srp.SRP6StandardGroups;
import org.python.bouncycastle.crypto.params.SRP6GroupParameters;

public class DefaultTlsSRPGroupVerifier implements TlsSRPGroupVerifier {
   protected static final Vector DEFAULT_GROUPS = new Vector();
   protected Vector groups;

   public DefaultTlsSRPGroupVerifier() {
      this(DEFAULT_GROUPS);
   }

   public DefaultTlsSRPGroupVerifier(Vector var1) {
      this.groups = var1;
   }

   public boolean accept(SRP6GroupParameters var1) {
      for(int var2 = 0; var2 < this.groups.size(); ++var2) {
         if (this.areGroupsEqual(var1, (SRP6GroupParameters)this.groups.elementAt(var2))) {
            return true;
         }
      }

      return false;
   }

   protected boolean areGroupsEqual(SRP6GroupParameters var1, SRP6GroupParameters var2) {
      return var1 == var2 || this.areParametersEqual(var1.getN(), var2.getN()) && this.areParametersEqual(var1.getG(), var2.getG());
   }

   protected boolean areParametersEqual(BigInteger var1, BigInteger var2) {
      return var1 == var2 || var1.equals(var2);
   }

   static {
      DEFAULT_GROUPS.addElement(SRP6StandardGroups.rfc5054_1024);
      DEFAULT_GROUPS.addElement(SRP6StandardGroups.rfc5054_1536);
      DEFAULT_GROUPS.addElement(SRP6StandardGroups.rfc5054_2048);
      DEFAULT_GROUPS.addElement(SRP6StandardGroups.rfc5054_3072);
      DEFAULT_GROUPS.addElement(SRP6StandardGroups.rfc5054_4096);
      DEFAULT_GROUPS.addElement(SRP6StandardGroups.rfc5054_6144);
      DEFAULT_GROUPS.addElement(SRP6StandardGroups.rfc5054_8192);
   }
}
