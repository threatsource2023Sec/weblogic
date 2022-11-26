package weblogic.rmi.extensions.server;

final class CBVHolder {
   final int pos;
   final Object obj;
   CBVHolder next;

   public CBVHolder(Object obj, int pos) {
      this.obj = obj;
      this.pos = pos;
   }
}
