package weblogic.rmi.internal;

public interface Enrollable {
   void enroll();

   void unenroll();

   void renewLease();
}
