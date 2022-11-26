package weblogic.xml.xpath.stream;

import java.util.List;

public interface Axis {
   int HIT = 200;
   int HIT_PRUNE = 201;
   int MISS = 202;
   int MISS_PRUNE = 203;

   int match(StreamContext var1);

   int matchNew(StreamContext var1);

   boolean isAllowedInPredicate();

   boolean isAllowedInRoot();

   boolean isStringConvertible();

   List getNodeset(StreamContext var1);
}
