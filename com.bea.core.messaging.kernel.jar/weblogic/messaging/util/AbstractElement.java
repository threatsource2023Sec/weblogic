package weblogic.messaging.util;

public abstract class AbstractElement implements Element {
   private Element next;
   private Element prev;
   private List list;

   public void setNext(Element next) {
      this.next = next;
   }

   public Element getNext() {
      return this.next;
   }

   public void setPrev(Element prev) {
      this.prev = prev;
   }

   public Element getPrev() {
      return this.prev;
   }

   public void setList(List list) {
      this.list = list;
   }

   public List getList() {
      return this.list;
   }
}
