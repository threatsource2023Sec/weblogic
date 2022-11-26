package weblogic.ant.taskdefs.antline;

class Declaration extends Tag {
   public String getJavaScript() throws ScriptException {
      String content = this.getContent();
      return "\n" + content.substring(3, content.length() - 2);
   }
}
