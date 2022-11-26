package weblogic.servlet.jsp;

class JspDependency {
   String uri;
   long lastMod;

   protected JspDependency(String u, long l) {
      this.uri = u;
      this.lastMod = l;
   }

   public int hashCode() {
      return (int)this.lastMod;
   }

   public boolean equals(Object o) {
      if (!(o instanceof JspDependency)) {
         return false;
      } else {
         JspDependency d = (JspDependency)o;
         return this.uri.equals(d.uri) && this.lastMod == d.lastMod;
      }
   }
}
