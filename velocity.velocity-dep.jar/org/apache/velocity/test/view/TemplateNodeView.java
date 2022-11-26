package org.apache.velocity.test.view;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import org.apache.velocity.context.InternalContextAdapter;
import org.apache.velocity.runtime.RuntimeSingleton;
import org.apache.velocity.runtime.parser.node.SimpleNode;
import org.apache.velocity.runtime.visitor.NodeViewMode;

public class TemplateNodeView {
   private SimpleNode document;
   private NodeViewMode visitor;

   public TemplateNodeView(String template) {
      try {
         RuntimeSingleton.init("velocity.properties");
         InputStreamReader isr = new InputStreamReader(new FileInputStream(template), RuntimeSingleton.getString("input.encoding"));
         BufferedReader br = new BufferedReader(isr);
         this.document = RuntimeSingleton.parse(br, template);
         this.visitor = new NodeViewMode();
         this.visitor.setContext((InternalContextAdapter)null);
         this.visitor.setWriter(new PrintWriter(System.out));
         this.document.jjtAccept(this.visitor, (Object)null);
      } catch (Exception var4) {
         System.out.println(var4);
         var4.printStackTrace();
      }

   }

   public static void main(String[] args) {
      new TemplateNodeView(args[0]);
   }
}
