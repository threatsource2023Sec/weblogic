package weblogic.ant.taskdefs.antline;

class Expression extends Tag {
   public String getJavaScript() {
      String content = this.getContent();
      content = content.substring(3, content.length() - 2);
      return "\n out.print( " + content + " );";
   }
}
