package com.bea.security.providers.xacml.entitlement.parser;

import com.bea.security.xacml.IOException;
import java.io.Writer;
import java.util.Iterator;
import java.util.List;

public class Groups implements Expression {
   private StringList data;

   public Groups(StringList data) {
      this.data = data;
   }

   public StringList getData() {
      return this.data;
   }

   protected char getPersistantTypeId() {
      return 'G';
   }

   private void writePersistantTypeId(Writer w) throws IOException {
      try {
         w.write((char)(this.getPersistantTypeId() | (!this.getData().getData().isEmpty() ? 128 : 0)));
      } catch (java.io.IOException var3) {
         throw new IOException(var3);
      }
   }

   public void writePersistantForm(Writer w) throws IOException {
      List data = this.getData().getData();

      try {
         if (data.size() == 1) {
            w.write(103);
            w.write(Parser.escape((String)data.get(0)));
            w.write(10);
         } else {
            this.writePersistantTypeId(w);
            w.write((char)data.size());
            Iterator var3 = data.iterator();

            while(var3.hasNext()) {
               String member = (String)var3.next();
               w.write(103);
               w.write(Parser.escape(member));
               w.write(10);
            }
         }

      } catch (java.io.IOException var5) {
         throw new IOException(var5);
      }
   }

   public String toString() {
      StringBuffer sb = new StringBuffer();
      sb.append("Grp(");
      if (this.data != null) {
         Iterator d = this.data.getData().iterator();

         while(d.hasNext()) {
            sb.append(Parser.escape((String)d.next()));
            if (d.hasNext()) {
               sb.append(',');
            }
         }
      }

      sb.append(')');
      return sb.toString();
   }
}
