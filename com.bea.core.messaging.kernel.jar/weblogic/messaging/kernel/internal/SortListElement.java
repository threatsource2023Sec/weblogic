package weblogic.messaging.kernel.internal;

public interface SortListElement {
   void setNext(SortListElement var1);

   SortListElement getNext();

   void setPrev(SortListElement var1);

   SortListElement getPrev();

   SortList getList();

   void setList(SortList var1);
}
