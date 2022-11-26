package weblogic.application.internal.library.util;

public class NodeModificationException extends Exception {
   private final Type type;
   private final RONode node;

   public NodeModificationException(Type t, RONode n) {
      super(getExceptionMessage(t));
      this.type = t;
      this.node = n;
   }

   public Type getType() {
      return this.type;
   }

   public RONode getNode() {
      return this.node;
   }

   public int getDepth() {
      return this.getNode().getDepth();
   }

   private static String getExceptionMessage(Type t) {
      return t.getMessage();
   }

   public static class Type {
      public static final Type ADDING_VALUE_TO_NON_LEAF_NODE = new Type("Trying to add value to a non-leaf Node");
      public static final Type ADDING_EDGE_TO_LEAF_NODE = new Type("addingEdgeToLeafNodeMessage");
      private final String message;

      private Type(String message) {
         this.message = message;
      }

      private String getMessage() {
         return this.message;
      }
   }
}
