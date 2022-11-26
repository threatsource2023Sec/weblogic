package weblogic.socket;

final class PollError {
   int badFdCount = 0;
   int[] fds;
   int[] revents;

   PollError(int[] fds) {
      this.fds = fds;
      this.revents = (int[])((int[])fds.clone());
   }

   void clear() {
      this.badFdCount = 0;
   }
}
