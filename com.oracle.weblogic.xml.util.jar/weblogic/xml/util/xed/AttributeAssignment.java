package weblogic.xml.util.xed;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import weblogic.xml.stream.Attribute;

public class AttributeAssignment extends Assignment {
   private void addResult(ArrayList result, Object obj) throws StreamEditorException {
      System.out.println("obj----->" + obj);
      if (obj instanceof Attribute) {
         result.add(obj);
      } else if (obj instanceof Collection) {
         result.addAll((Collection)obj);
      } else {
         throw new StreamEditorException("Unable to add" + obj);
      }
   }

   public Object evaluate(Context context) throws StreamEditorException {
      if (context.getEventType() != 2) {
         return null;
      } else {
         Iterator i = this.getRHS().iterator();
         if (!i.hasNext()) {
            throw new StreamEditorException("Evaluation error:invalid right hand side" + this.toString());
         } else {
            ArrayList result = new ArrayList();
            this.addResult(result, ((Variable)i.next()).evaluate(context));

            while(i.hasNext()) {
               this.addResult(result, ((Variable)i.next()).evaluate(context));
            }

            this.getLHS().assign(result, context);
            return this.getLHS();
         }
      }
   }
}
