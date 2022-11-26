package org.apache.openjpa.lib.xml;

public interface Commentable {
   String[] EMPTY_COMMENTS = new String[0];

   void setComments(String[] var1);

   String[] getComments();
}
