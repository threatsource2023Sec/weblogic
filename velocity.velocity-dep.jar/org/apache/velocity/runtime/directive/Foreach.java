package org.apache.velocity.runtime.directive;

import java.io.IOException;
import java.io.Writer;
import java.util.Iterator;
import org.apache.velocity.context.InternalContextAdapter;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.runtime.RuntimeServices;
import org.apache.velocity.runtime.parser.node.Node;
import org.apache.velocity.util.introspection.Info;

public class Foreach extends Directive {
   private String counterName;
   private int counterInitialValue;
   private String elementKey;
   protected Info uberInfo;

   public String getName() {
      return "foreach";
   }

   public int getType() {
      return 1;
   }

   public void init(RuntimeServices rs, InternalContextAdapter context, Node node) throws Exception {
      super.init(rs, context, node);
      this.counterName = super.rsvc.getString("directive.foreach.counter.name");
      this.counterInitialValue = super.rsvc.getInt("directive.foreach.counter.initial.value");
      this.elementKey = node.jjtGetChild(0).getFirstToken().image.substring(1);
      this.uberInfo = new Info(context.getCurrentTemplateName(), this.getLine(), this.getColumn());
   }

   public boolean render(InternalContextAdapter context, Writer writer, Node node) throws IOException, MethodInvocationException, ResourceNotFoundException, ParseErrorException {
      Object listObject = node.jjtGetChild(2).value(context);
      if (listObject == null) {
         return false;
      } else {
         Iterator i = null;

         try {
            i = super.rsvc.getUberspect().getIterator(listObject, this.uberInfo);
         } catch (Exception var9) {
            System.out.println(var9);
         }

         if (i == null) {
            return false;
         } else {
            int counter = this.counterInitialValue;
            Object o = context.get(this.elementKey);

            Object ctr;
            for(ctr = context.get(this.counterName); i.hasNext(); ++counter) {
               context.put(this.counterName, new Integer(counter));
               context.put(this.elementKey, i.next());
               node.jjtGetChild(3).render(context, writer);
            }

            if (ctr != null) {
               context.put(this.counterName, ctr);
            } else {
               context.remove(this.counterName);
            }

            if (o != null) {
               context.put(this.elementKey, o);
            } else {
               context.remove(this.elementKey);
            }

            return true;
         }
      }
   }
}
