package org.python.indexer.types;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.python.indexer.Indexer;
import org.python.indexer.Scope;

public abstract class NType {
   private Scope table;
   protected static final String LIBRARY_URL = "http://docs.python.org/library/";
   protected static final String TUTORIAL_URL = "http://docs.python.org/tutorial/";
   protected static final String REFERENCE_URL = "http://docs.python.org/reference/";
   private static Pattern INSTANCE_TAG = Pattern.compile("(.+?)=#([0-9]+)");

   public void setTable(Scope table) {
      this.table = table;
   }

   public Scope getTable() {
      if (this.table == null) {
         this.table = new Scope((Scope)null, Scope.Type.SCOPE);
      }

      return this.table;
   }

   public NType follow() {
      return NUnknownType.follow(this);
   }

   public boolean isNative() {
      return Indexer.idx.builtins.isNative(this);
   }

   public boolean isClassType() {
      return this instanceof NClassType;
   }

   public boolean isDictType() {
      return this instanceof NDictType;
   }

   public boolean isFuncType() {
      return this instanceof NFuncType;
   }

   public boolean isInstanceType() {
      return this instanceof NInstanceType;
   }

   public boolean isListType() {
      return this instanceof NListType;
   }

   public boolean isModuleType() {
      return this instanceof NModuleType;
   }

   public boolean isNumType() {
      return this == Indexer.idx.builtins.BaseNum;
   }

   public boolean isStrType() {
      return this == Indexer.idx.builtins.BaseStr;
   }

   public boolean isTupleType() {
      return this instanceof NTupleType;
   }

   public boolean isUnionType() {
      return this instanceof NUnionType;
   }

   public boolean isUnknownType() {
      return this instanceof NUnknownType;
   }

   public NClassType asClassType() {
      return (NClassType)this;
   }

   public NDictType asDictType() {
      return (NDictType)this;
   }

   public NFuncType asFuncType() {
      return (NFuncType)this;
   }

   public NInstanceType asInstanceType() {
      return (NInstanceType)this;
   }

   public NListType asListType() {
      return (NListType)this;
   }

   public NModuleType asModuleType() {
      return (NModuleType)this;
   }

   public NTupleType asTupleType() {
      return (NTupleType)this;
   }

   public NUnionType asUnionType() {
      return (NUnionType)this;
   }

   public NUnknownType asUnknownType() {
      return (NUnknownType)this;
   }

   public String toString() {
      StringBuilder input = new StringBuilder();
      this.print(new CyclicTypeRecorder(), input);
      StringBuilder sb = new StringBuilder(input.length());
      Matcher m = INSTANCE_TAG.matcher(input.toString());
      int end = -1;

      while(m.find()) {
         end = m.end();
         int num = Integer.parseInt(m.group(2));
         if (input.indexOf("<#" + num + ">") == -1) {
            sb.append(m.group(1));
         } else {
            sb.append(m.group());
         }
      }

      if (end != -1) {
         sb.append(input.substring(end));
      }

      return sb.toString();
   }

   protected void print(CyclicTypeRecorder ctr, StringBuilder sb) {
      int num = ctr.fetch(this);
      if (num > 0) {
         sb.append("<#").append(num).append(">");
      } else {
         String tag = this.getClass().getName();
         tag = tag.substring(tag.lastIndexOf(".") + 2);
         sb.append("<").append(tag).append("=#").append(-num).append(":");
         this.printKids(ctr, sb);
         sb.append(">");
      }

   }

   protected abstract void printKids(CyclicTypeRecorder var1, StringBuilder var2);

   protected class CyclicTypeRecorder {
      int count = 0;
      private Map elements = new HashMap();

      public int fetch(NType t) {
         Integer i = (Integer)this.elements.get(t);
         if (i != null) {
            return i;
         } else {
            i = ++this.count;
            this.elements.put(t, i);
            return -i;
         }
      }
   }
}
