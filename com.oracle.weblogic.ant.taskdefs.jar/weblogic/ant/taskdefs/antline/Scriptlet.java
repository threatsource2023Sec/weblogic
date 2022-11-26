package weblogic.ant.taskdefs.antline;

class Scriptlet extends Tag {
   public String getJavaScript() {
      String content = this.getContent();
      return "\n" + content.substring(2, content.length() - 2);
   }
}
