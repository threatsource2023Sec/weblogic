package weblogic.apache.xerces.xinclude;

import weblogic.apache.xerces.xni.NamespaceContext;

public class XIncludeNamespaceSupport extends MultipleScopeNamespaceSupport {
   private boolean[] fValidContext = new boolean[8];

   public XIncludeNamespaceSupport() {
   }

   public XIncludeNamespaceSupport(NamespaceContext var1) {
      super(var1);
   }

   public void pushContext() {
      super.pushContext();
      if (this.fCurrentContext + 1 == this.fValidContext.length) {
         boolean[] var1 = new boolean[this.fValidContext.length * 2];
         System.arraycopy(this.fValidContext, 0, var1, 0, this.fValidContext.length);
         this.fValidContext = var1;
      }

      this.fValidContext[this.fCurrentContext] = true;
   }

   public void setContextInvalid() {
      this.fValidContext[this.fCurrentContext] = false;
   }

   public String getURIFromIncludeParent(String var1) {
      int var2;
      for(var2 = this.fCurrentContext - 1; var2 > 0 && !this.fValidContext[var2]; --var2) {
      }

      return this.getURI(var1, var2);
   }
}
