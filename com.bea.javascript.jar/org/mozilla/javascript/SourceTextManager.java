package org.mozilla.javascript;

import java.util.Enumeration;

public interface SourceTextManager {
   Enumeration getAllItems();

   SourceTextItem getItem(String var1);

   SourceTextItem newItem(String var1);
}
