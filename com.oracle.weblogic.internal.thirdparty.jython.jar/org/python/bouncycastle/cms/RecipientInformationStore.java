package org.python.bouncycastle.cms;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.python.bouncycastle.asn1.x500.X500Name;
import org.python.bouncycastle.util.Iterable;

public class RecipientInformationStore implements Iterable {
   private final List all;
   private final Map table = new HashMap();

   public RecipientInformationStore(RecipientInformation var1) {
      this.all = new ArrayList(1);
      this.all.add(var1);
      RecipientId var2 = var1.getRID();
      this.table.put(var2, this.all);
   }

   public RecipientInformationStore(Collection var1) {
      RecipientInformation var3;
      ArrayList var5;
      for(Iterator var2 = var1.iterator(); var2.hasNext(); var5.add(var3)) {
         var3 = (RecipientInformation)var2.next();
         RecipientId var4 = var3.getRID();
         var5 = (ArrayList)this.table.get(var4);
         if (var5 == null) {
            var5 = new ArrayList(1);
            this.table.put(var4, var5);
         }
      }

      this.all = new ArrayList(var1);
   }

   public RecipientInformation get(RecipientId var1) {
      Collection var2 = this.getRecipients(var1);
      return var2.size() == 0 ? null : (RecipientInformation)var2.iterator().next();
   }

   public int size() {
      return this.all.size();
   }

   public Collection getRecipients() {
      return new ArrayList(this.all);
   }

   public Collection getRecipients(RecipientId var1) {
      if (var1 instanceof KeyTransRecipientId) {
         KeyTransRecipientId var2 = (KeyTransRecipientId)var1;
         X500Name var3 = var2.getIssuer();
         byte[] var4 = var2.getSubjectKeyIdentifier();
         if (var3 != null && var4 != null) {
            ArrayList var5 = new ArrayList();
            Collection var6 = this.getRecipients(new KeyTransRecipientId(var3, var2.getSerialNumber()));
            if (var6 != null) {
               var5.addAll(var6);
            }

            Collection var7 = this.getRecipients(new KeyTransRecipientId(var4));
            if (var7 != null) {
               var5.addAll(var7);
            }

            return var5;
         }
      }

      ArrayList var8 = (ArrayList)this.table.get(var1);
      return var8 == null ? new ArrayList() : new ArrayList(var8);
   }

   public Iterator iterator() {
      return this.getRecipients().iterator();
   }
}
