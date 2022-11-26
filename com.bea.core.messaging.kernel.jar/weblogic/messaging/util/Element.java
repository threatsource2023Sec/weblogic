package weblogic.messaging.util;

public interface Element {
   void setNext(Element var1);

   Element getNext();

   void setPrev(Element var1);

   Element getPrev();

   List getList();

   void setList(List var1);
}
