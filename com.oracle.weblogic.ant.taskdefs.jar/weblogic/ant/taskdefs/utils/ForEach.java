package weblogic.ant.taskdefs.utils;

import java.util.StringTokenizer;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.taskdefs.Ant;

public class ForEach extends Ant {
   private String _values;
   private String _current = "current";
   private String _delimiter = ",";

   public void setDelimiter(String delimiter) {
      this._delimiter = delimiter;
   }

   public void setValues(String values) {
      this._values = values;
   }

   public void setCurrent(String current) {
      this._current = current;
   }

   public void execute() throws BuildException {
      if (this._values == null) {
         throw new BuildException("Values property is not set");
      } else {
         String values = this.getProject().replaceProperties(this._values);
         StringTokenizer toks = new StringTokenizer(values, this._delimiter);

         while(toks.hasMoreTokens()) {
            String val = toks.nextToken();
            this.getProject().setProperty(this._current, val);
            super.execute();
         }

      }
   }
}
