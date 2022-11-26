package com.bea.security.providers.xacml.entitlement.parser;

import com.bea.security.xacml.IOException;
import java.io.Writer;
import java.util.Iterator;
import java.util.List;

public class Predicate implements Expression {
   private String classname;
   private StringList parameters;

   public Predicate(String classname) {
      this(classname, (StringList)null);
   }

   public Predicate(String classname, StringList parameters) {
      this.classname = classname;
      this.parameters = parameters;
   }

   public String getClassname() {
      return this.classname;
   }

   public StringList getParameters() {
      return this.parameters;
   }

   protected char getPersistantTypeId() {
      return 'p';
   }

   private void writePersistantTypeId(Writer w) throws IOException {
      try {
         w.write((char)(this.getPersistantTypeId() | (!this.getParameters().getData().isEmpty() ? 128 : 0)));
      } catch (java.io.IOException var3) {
         throw new IOException(var3);
      }
   }

   public void writePersistantForm(Writer w) throws IOException {
      this.writePersistantTypeId(w);
      List data = this.getParameters().getData();

      try {
         com.bea.common.security.jdkutils.WeaverUtil.Writer.append(w, this.getClassname());
         w.write(10);
         w.write((char)data.size());
         Iterator var3 = data.iterator();

         while(var3.hasNext()) {
            String member = (String)var3.next();
            com.bea.common.security.jdkutils.WeaverUtil.Writer.append(w, Parser.escape(member));
            w.write(10);
         }

      } catch (java.io.IOException var5) {
         throw new IOException(var5);
      }
   }

   public String toString() {
      StringBuffer sb = new StringBuffer();
      sb.append('?');
      sb.append(this.getClassname());
      sb.append("(");
      if (this.parameters != null) {
         Iterator d = this.parameters.getData().iterator();

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
