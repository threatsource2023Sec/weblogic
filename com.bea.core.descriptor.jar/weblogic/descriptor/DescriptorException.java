package weblogic.descriptor;

import com.bea.xml.XmlError;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class DescriptorException extends IOException {
   private static final long serialVersionUID = -6583376883467803663L;
   private List errorList;

   public DescriptorException() {
      this.errorList = Collections.EMPTY_LIST;
   }

   public DescriptorException(String msg) {
      super(msg);
      this.errorList = Collections.EMPTY_LIST;
   }

   public DescriptorException(String msg, Throwable cause) {
      super(msg);
      this.errorList = Collections.EMPTY_LIST;
      this.initCause(cause);
   }

   public DescriptorException(String msg, List errors) {
      super(msg + getErrorsAsString(errors));
      this.errorList = Collections.EMPTY_LIST;
      if (errors != null && !errors.isEmpty()) {
         this.errorList = new ArrayList(errors.size());

         for(int count = 0; count < errors.size(); ++count) {
            this.errorList.add(errors.get(count).toString());
         }
      }

   }

   public List getErrorList() {
      return this.errorList;
   }

   private static String getErrorsAsString(List errors) {
      String sb = new String();
      if (!errors.isEmpty()) {
         Iterator it = errors.iterator();

         while(it.hasNext()) {
            Object o = it.next();
            if (o instanceof XmlError) {
               XmlError e = (XmlError)o;
               String place = "";
               if (e.getSourceName() == null && e.getLine() != -1 && e.getColumn() != -1) {
                  place = "<" + e.getLine() + ":" + e.getColumn() + "> ";
               }

               sb = sb + "\n  " + place + e.toString().replaceAll("error", "problem");
            } else {
               sb = sb + "\n  " + o.toString().replaceAll("error", "problem");
            }
         }
      }

      return sb.toString();
   }
}
