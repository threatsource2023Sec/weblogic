package weblogic.ejb;

import java.io.Serializable;
import java.util.Date;
import javax.ejb.EJBException;
import javax.ejb.Timer;
import javax.ejb.TimerService;

public interface WLTimerService extends TimerService {
   Timer createTimer(Date var1, long var2, Serializable var4, WLTimerInfo var5) throws IllegalArgumentException, IllegalStateException, EJBException;

   Timer createTimer(Date var1, Serializable var2, WLTimerInfo var3) throws IllegalArgumentException, IllegalStateException, EJBException;

   Timer createTimer(long var1, long var3, Serializable var5, WLTimerInfo var6) throws IllegalArgumentException, IllegalStateException, EJBException;

   Timer createTimer(long var1, Serializable var3, WLTimerInfo var4) throws IllegalArgumentException, IllegalStateException, EJBException;
}
