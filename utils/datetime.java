import java.util.*;

class DateTime implements Comparable<DateTime>
{
    private Calendar calendar;

    public DateTime(int y, int m, int d, int h, int mm, int s)
    {
        if (m < 1 || m > 12 || d < 1 || d > 31 || h < 0 || h > 23 || mm < 0 || m > 59 || s < 0 || s > 59)
            throw new IllegalArgumentException("Invalid date");
        calendar = new GregorianCalendar(y, m - 1, d, h, mm, s);
    }

    public DateTime(int y, int m, int d)
    {
        this(y, m, d, 0, 0, 0);
    }

    public static DateTime now()
    {
        DateTime t = new DateTime(0, 0, 0, 0, 0, 0);
        t.calendar = Calendar.getInstance();
        return t;
    }

    public int getDay()
    {
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    public int getDayOfWeek()
    {
        return calendar.get(Calendar.DAY_OF_WEEK);
    }

    public int getDayOfYear()
    {
        return calendar.get(Calendar.DAY_OF_YEAR);
    }

    public int getMonth()
    {
        return calendar.get(Calendar.MONTH) + 1;
    }

    public int getYear()
    {
        return calendar.get(Calendar.YEAR);
    }

    public int getHour()
    {
        return calendar.get(Calendar.HOUR);
    }

    public int getMinute()
    {
        return calendar.get(Calendar.MINUTE);
    }

    public int getSecond()
    {
        return calendar.get(Calendar.SECOND);
    }

    public int getMillisecond()
    {
        return calendar.get(Calendar.MILLISECOND);
    }

    public int getWeekOfYear()
    {
        return calendar.get(Calendar.WEEK_OF_YEAR);
    }

    public int getWeekOfMonth()
    {
        return calendar.get(Calendar.WEEK_OF_MONTH);
    }

    public void add(int years, int months, int days, int hours, int minutes, int seconds, int milli)
    {
        calendar.add(Calendar.YEAR, years);
        calendar.add(Calendar.MONTH, months);
        calendar.add(Calendar.DATE, days);
        calendar.add(Calendar.HOUR, hours);
        calendar.add(Calendar.MINUTE, minutes);
        calendar.add(Calendar.SECOND, seconds);
        calendar.add(Calendar.MILLISECOND, milli);
    }

    public TimeInterval subtract(DateTime other)
    {
        long a = this.calendar.getTimeInMillis(), b = other.calendar.getTimeInMillis();
        return new TimeInterval(Math.abs(a - b));
    }

    public String toString()
    {
        return String.format("%1$tD %1$tR", calendar);
    }

    public String toLongString()
    {
        return String.format("%1$tA, %1$tB %1$td, %1$tY %1$tr", calendar);
    }

    public int compareTo(DateTime when)
    {
        return this.calendar.compareTo(when.calendar);
    }

    public boolean isAfter(DateTime when)
    {
        return compareTo(when) > 0;
    }

    public boolean isBefore(DateTime when)
    {
        return compareTo(when) < 0;
    }

    public boolean equals(Object o)
    {
        if (o == null) return false;
        if (o.getClass() != this.getClass()) return false;
        return calendar.equals(((DateTime) o).calendar);
    }

    public int hashCode()
    {
        return calendar.hashCode();
    }
}

class TimeInterval
{
    public int years, months, weeks, days, hours, minutes, seconds, milliseconds;

    public TimeInterval(double milli)
    {
        if (milli >= 3.15569e10)
        {
            years = (int) (milli / 3.15569e10);
            milli /= 3.15569e10;
        }
        if (milli >= 2.62974e9)
        {
            months = (int) (milli / 2.62974e9);
            milli /= 2.62974e9;
        }
        if (milli >= 604800000)
        {
            weeks = (int) (milli / 604800000);
            milli /= 604800000;
        }
        if (milli >= 86400000)
        {
            days = (int) (milli / 86400000);
            milli /= 86400000;
        }
        if (milli >= 3600000)
        {
            hours = (int) (milli / 3600000);
            milli /= 3600000;
        }
        if (milli >= 60000)
        {
            minutes = (int) (milli / 60000);
            milli /= 60000;
        }
        if (milli >= 2)
            milliseconds = (int) milli;
    }
}
