package weblogic.ant.taskdefs.antline;

abstract class Tag {
   private String content;

   public String getContent() {
      return this.content;
   }

   public void setContent(String content) {
      this.content = content;
   }

   public abstract String getJavaScript() throws ScriptException;
}
