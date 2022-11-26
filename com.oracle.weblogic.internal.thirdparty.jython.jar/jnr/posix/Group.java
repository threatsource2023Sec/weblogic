package jnr.posix;

public interface Group {
   String getName();

   String getPassword();

   long getGID();

   String[] getMembers();
}
