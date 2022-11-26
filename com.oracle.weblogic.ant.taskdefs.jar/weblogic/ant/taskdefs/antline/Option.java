package weblogic.ant.taskdefs.antline;

import org.apache.tools.ant.BuildException;

public class Option {
   private String antName;
   private String optName;
   private Description description;
   private Arg arg;

   public void setAntname(String val) {
      this.antName = val;
   }

   public String getAntname() {
      return this.antName;
   }

   public void setOptname(String val) {
      this.optName = val;
   }

   public String getOptname() {
      return this.optName == null ? this.antName : this.optName;
   }

   public Description createDescription() {
      if (this.description != null) {
         throw new BuildException("Only one <description> element may be specified for <option>");
      } else {
         this.description = new Description();
         return this.description;
      }
   }

   public String getDescription() {
      return this.description == null ? null : this.description.getText();
   }

   public Arg createArg() {
      if (this.arg != null) {
         throw new BuildException("Only one <arg> element may be specified for <option>");
      } else {
         this.arg = new Arg();
         return this.arg;
      }
   }

   public String getArg() {
      return this.arg == null ? null : this.arg.getText();
   }

   public String getConverter() {
      return this.arg == null ? null : this.arg.getConverter();
   }

   public void validateAttributes() {
      if (this.antName == null) {
         throw new BuildException("The antName attribute of <option> must be set");
      }
   }

   public static class Arg {
      private StringBuffer text = new StringBuffer();
      private String converter;

      public void addText(String t) {
         this.text.append(t);
      }

      public String getText() {
         return this.text.toString();
      }

      public void setConverter(String val) {
         this.converter = val;
      }

      public String getConverter() {
         return this.converter;
      }
   }

   public static class Description {
      private StringBuffer text = new StringBuffer();

      public void addText(String t) {
         this.text.append(t);
      }

      public String getText() {
         return this.text.toString();
      }
   }
}
