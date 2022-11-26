package org.python.google.common.collect;

import java.util.SortedSet;
import org.python.google.common.annotations.GwtIncompatible;

@GwtIncompatible
interface SortedMultisetBridge extends Multiset {
   SortedSet elementSet();
}
