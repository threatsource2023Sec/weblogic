package org.mozilla.javascript;

public class Node implements Cloneable {
   public static final int TARGET_PROP = 1;
   public static final int BREAK_PROP = 2;
   public static final int CONTINUE_PROP = 3;
   public static final int ENUM_PROP = 4;
   public static final int FUNCTION_PROP = 5;
   public static final int TEMP_PROP = 6;
   public static final int LOCAL_PROP = 7;
   public static final int CODEOFFSET_PROP = 8;
   public static final int FIXUPS_PROP = 9;
   public static final int VARS_PROP = 10;
   public static final int USES_PROP = 11;
   public static final int REGEXP_PROP = 12;
   public static final int CASES_PROP = 13;
   public static final int DEFAULT_PROP = 14;
   public static final int CASEARRAY_PROP = 15;
   public static final int SOURCENAME_PROP = 16;
   public static final int SOURCE_PROP = 17;
   public static final int TYPE_PROP = 18;
   public static final int SPECIAL_PROP_PROP = 19;
   public static final int LABEL_PROP = 20;
   public static final int FINALLY_PROP = 21;
   public static final int LOCALCOUNT_PROP = 22;
   public static final int TARGETBLOCK_PROP = 23;
   public static final int VARIABLE_PROP = 24;
   public static final int LASTUSE_PROP = 25;
   public static final int ISNUMBER_PROP = 26;
   public static final int DIRECTCALL_PROP = 27;
   public static final int BASE_LINENO_PROP = 28;
   public static final int END_LINENO_PROP = 29;
   public static final int SPECIALCALL_PROP = 30;
   public static final int DEBUGSOURCE_PROP = 31;
   public static final int BOTH = 0;
   public static final int LEFT = 1;
   public static final int RIGHT = 2;
   private static String[] propNames;
   protected int type;
   protected Node next;
   protected Node first;
   protected Node last;
   protected UintMap props;
   protected Object datum;

   public Node(int var1) {
      this.type = var1;
   }

   public Node(int var1, Object var2) {
      this.type = var1;
      this.datum = var2;
   }

   public Node(int var1, Node var2) {
      this.type = var1;
      this.first = this.last = var2;
      var2.next = null;
   }

   public Node(int var1, Node var2, Object var3) {
      this(var1, var2);
      this.datum = var3;
   }

   public Node(int var1, Node var2, Node var3) {
      this.type = var1;
      this.first = var2;
      this.last = var3;
      var2.next = var3;
      var3.next = null;
   }

   public Node(int var1, Node var2, Node var3, Object var4) {
      this(var1, var2, var3);
      this.datum = var4;
   }

   public Node(int var1, Node var2, Node var3, Node var4) {
      this.type = var1;
      this.first = var2;
      this.last = var4;
      var2.next = var3;
      var3.next = var4;
      var4.next = null;
   }

   public void addChildAfter(Node var1, Node var2) {
      if (var1.next != null) {
         throw new RuntimeException("newChild had siblings in addChildAfter");
      } else {
         var1.next = var2.next;
         var2.next = var1;
         if (this.last == var2) {
            this.last = var1;
         }

      }
   }

   public void addChildBefore(Node var1, Node var2) {
      if (var1.next != null) {
         throw new RuntimeException("newChild had siblings in addChildBefore");
      } else if (this.first == var2) {
         var1.next = this.first;
         this.first = var1;
      } else {
         Node var3 = this.getChildBefore(var2);
         this.addChildAfter(var1, var3);
      }
   }

   public void addChildToBack(Node var1) {
      var1.next = null;
      if (this.last == null) {
         this.first = this.last = var1;
      } else {
         this.last.next = var1;
         this.last = var1;
      }
   }

   public void addChildToFront(Node var1) {
      var1.next = this.first;
      this.first = var1;
      if (this.last == null) {
         this.last = var1;
      }

   }

   public void addChildrenToBack(Node var1) {
      if (this.last != null) {
         this.last.next = var1;
      }

      this.last = var1.getLastSibling();
      if (this.first == null) {
         this.first = var1;
      }

   }

   public void addChildrenToFront(Node var1) {
      Node var2 = var1.getLastSibling();
      var2.next = this.first;
      this.first = var1;
      if (this.last == null) {
         this.last = var2;
      }

   }

   public Node cloneNode() {
      try {
         Node var1 = (Node)super.clone();
         var1.next = null;
         var1.first = null;
         var1.last = null;
         return var1;
      } catch (CloneNotSupportedException var3) {
         throw new RuntimeException(var3.getMessage());
      }
   }

   public Node getChildBefore(Node var1) {
      if (var1 == this.first) {
         return null;
      } else {
         Node var2 = this.first;

         while(var2.next != var1) {
            var2 = var2.next;
            if (var2 == null) {
               throw new RuntimeException("node is not a child");
            }
         }

         return var2;
      }
   }

   public ShallowNodeIterator getChildIterator() {
      return new ShallowNodeIterator(this.first);
   }

   public Object getDatum() {
      return this.datum;
   }

   public double getDouble() {
      return ((Number)this.datum).doubleValue();
   }

   public int getExistingIntProp(int var1) {
      return this.props.getExistingInt(var1);
   }

   public Node getFirst() {
      return this.first;
   }

   public Node getFirstChild() {
      return this.first;
   }

   public int getInt() {
      return ((Number)this.datum).intValue();
   }

   public int getIntProp(int var1, int var2) {
      return this.props == null ? var2 : this.props.getInt(var1, var2);
   }

   public Node getLastChild() {
      return this.last;
   }

   public Node getLastSibling() {
      Node var1;
      for(var1 = this; var1.next != null; var1 = var1.next) {
      }

      return var1;
   }

   public long getLong() {
      return ((Number)this.datum).longValue();
   }

   public Node getNext() {
      return this.next;
   }

   public Node getNextSibling() {
      return this.next;
   }

   public PreorderNodeIterator getPreorderIterator() {
      return new PreorderNodeIterator(this);
   }

   public Object getProp(int var1) {
      return this.props == null ? null : this.props.getObject(var1);
   }

   public String getString() {
      return (String)this.datum;
   }

   public int getType() {
      return this.type;
   }

   public boolean hasChildren() {
      return this.first != null;
   }

   private static final String propToString(int var0) {
      return propNames[var0 - 1];
   }

   public void putIntProp(int var1, int var2) {
      if (this.props == null) {
         this.props = new UintMap(2);
      }

      this.props.put(var1, var2);
   }

   public void putProp(int var1, Object var2) {
      if (this.props == null) {
         this.props = new UintMap(2);
      }

      if (var2 == null) {
         this.props.remove(var1);
      } else {
         this.props.put(var1, var2);
      }

   }

   public void removeChild(Node var1) {
      Node var2 = this.getChildBefore(var1);
      if (var2 == null) {
         this.first = this.first.next;
      } else {
         var2.next = var1.next;
      }

      if (var1 == this.last) {
         this.last = var2;
      }

      var1.next = null;
   }

   public void replaceChild(Node var1, Node var2) {
      var2.next = var1.next;
      if (var1 == this.first) {
         this.first = var2;
      } else {
         Node var3 = this.getChildBefore(var1);
         var3.next = var2;
      }

      if (var1 == this.last) {
         this.last = var2;
      }

      var1.next = null;
   }

   public void setDatum(Object var1) {
      this.datum = var1;
   }

   public void setType(int var1) {
      this.type = var1;
   }

   public String toString() {
      return null;
   }

   public String toStringTree() {
      return this.toStringTreeHelper(0);
   }

   private String toStringTreeHelper(int var1) {
      return "";
   }
}
