package com.octetstring.vde;

import com.octetstring.vde.util.DirectoryException;

public interface EntrySet {
   Entry getNext() throws DirectoryException;

   boolean hasMore();
}
