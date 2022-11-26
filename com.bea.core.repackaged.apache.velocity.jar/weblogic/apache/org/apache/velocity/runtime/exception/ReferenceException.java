package weblogic.apache.org.apache.velocity.runtime.exception;

import weblogic.apache.org.apache.velocity.runtime.parser.node.Node;

public class ReferenceException extends Exception {
   public ReferenceException(String exceptionMessage, Node node) {
      super(exceptionMessage + " [line " + node.getLine() + ",column " + node.getColumn() + "] : " + node.literal() + " is not a valid reference.");
   }
}
