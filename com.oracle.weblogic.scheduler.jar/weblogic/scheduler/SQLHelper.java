package weblogic.scheduler;

public interface SQLHelper {
   String getCancelTimerSQL();

   String getAdvanceTimerSQL();

   String getUpdateStartTimeSQL();

   String getCreateTimerSQL();

   String getCreateTimerSQLWithUserKey();

   String getTimerStateSQL();

   String getReadyTimersSQL();

   String getTimersSQL();

   String getTimersLikeIdSQL();

   String getTimersByUserKey();

   String getCancelTimersSQL();
}
