package org.apache.velocity.runtime.directive;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import org.apache.velocity.context.InternalContextAdapter;
import org.apache.velocity.runtime.RuntimeServices;
import org.apache.velocity.runtime.parser.ParseException;
import org.apache.velocity.runtime.parser.ParserTreeConstants;
import org.apache.velocity.runtime.parser.Token;
import org.apache.velocity.runtime.parser.node.Node;
import org.apache.velocity.runtime.parser.node.NodeUtils;

public class Macro extends Directive {
   private static boolean debugMode = false;

   public String getName() {
      return "macro";
   }

   public int getType() {
      return 1;
   }

   public boolean render(InternalContextAdapter context, Writer writer, Node node) throws IOException {
      return true;
   }

   public void init(RuntimeServices rs, InternalContextAdapter context, Node node) throws Exception {
      super.init(rs, context, node);
   }

   public static void processAndRegister(RuntimeServices rs, Node node, String sourceTemplate) throws IOException, ParseException {
      int numArgs = node.jjtGetNumChildren();
      if (numArgs < 2) {
         rs.error("#macro error : Velocimacro must have name as 1st argument to #macro(). #args = " + numArgs);
         throw new MacroParseException("First argument to #macro() must be  macro name.");
      } else {
         int firstType = node.jjtGetChild(0).getType();
         if (firstType != 8) {
            Token t = node.jjtGetChild(0).getFirstToken();
            throw new MacroParseException("First argument to #macro() must be a token without surrounding ' or \", which specifies the macro name.  Currently it is a " + ParserTreeConstants.jjtNodeName[firstType]);
         } else {
            String[] argArray = getArgArray(node);
            List macroArray = getASTAsStringArray(node.jjtGetChild(numArgs - 1));
            StringBuffer temp = new StringBuffer();

            for(int i = 0; i < macroArray.size(); ++i) {
               temp.append(macroArray.get(i));
            }

            String macroBody = temp.toString();
            rs.addVelocimacro(argArray[0], macroBody, argArray, sourceTemplate);
         }
      }
   }

   private static String[] getArgArray(Node node) {
      int numArgs = node.jjtGetNumChildren();
      --numArgs;
      String[] argArray = new String[numArgs];

      int i;
      for(i = 0; i < numArgs; ++i) {
         argArray[i] = node.jjtGetChild(i).getFirstToken().image;
         if (i > 0 && argArray[i].startsWith("$")) {
            argArray[i] = argArray[i].substring(1, argArray[i].length());
         }
      }

      if (debugMode) {
         System.out.println("Macro.getArgArray() : #args = " + numArgs);
         System.out.print(argArray[0] + "(");

         for(i = 1; i < numArgs; ++i) {
            System.out.print(" " + argArray[i]);
         }

         System.out.println(" )");
      }

      return argArray;
   }

   private static List getASTAsStringArray(Node rootNode) {
      Token t = rootNode.getFirstToken();
      Token tLast = rootNode.getLastToken();
      ArrayList list = new ArrayList();

      for(t = rootNode.getFirstToken(); t != tLast; t = t.next) {
         list.add(NodeUtils.tokenLiteral(t));
      }

      list.add(NodeUtils.tokenLiteral(t));
      return list;
   }
}
