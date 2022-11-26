package org.apache.openjpa.util;

import java.util.Calendar;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.Map;
import java.util.TimeZone;

public interface ProxyManager {
   Object copyArray(Object var1);

   Date copyDate(Date var1);

   Proxy newDateProxy(Class var1);

   Calendar copyCalendar(Calendar var1);

   Proxy newCalendarProxy(Class var1, TimeZone var2);

   Collection copyCollection(Collection var1);

   Proxy newCollectionProxy(Class var1, Class var2, Comparator var3, boolean var4);

   Map copyMap(Map var1);

   Proxy newMapProxy(Class var1, Class var2, Class var3, Comparator var4, boolean var5);

   Object copyCustom(Object var1);

   Proxy newCustomProxy(Object var1, boolean var2);
}
