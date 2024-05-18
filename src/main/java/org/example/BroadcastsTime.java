package org.example;

public class BroadcastsTime implements Comparable<BroadcastsTime> {
    private byte hour;
    private byte minute;

    // Конструктор, принимающий часы и минуты
    BroadcastsTime(byte hour, byte minute) {
        this.hour = hour;
        this.minute = minute;
    }

    // Конструктор, принимающий строку времени в формате "ЧЧ:ММ"
    BroadcastsTime(String time) {
        String[] curTime = time.split(":");
        this.hour = Byte.parseByte(curTime[0]);
        this.minute = Byte.parseByte(curTime[1]);
    }

    // Метод compareTo для сравнения двух объектов BroadcastsTime
    @Override
    public int compareTo(BroadcastsTime other) {
        if (this.hour != other.hour) {
            return Byte.compare(this.hour, other.hour);
        } else {
            return Byte.compare(this.minute, other.minute);
        }
    }

    byte hour() {
        return hour;
    }

    byte minutes() {
        return minute;
    }

    // Метод для проверки, что текущее время после указанного
    public boolean after(BroadcastsTime t) {
        return this.compareTo(t) > 0;
    }

    // Метод для проверки, что текущее время до указанного
    public boolean before(BroadcastsTime t) {
        return this.compareTo(t) < 0;
    }

    // Метод для проверки, что текущее время находится между двумя другими
    public boolean between(BroadcastsTime t1, BroadcastsTime t2) {
        return (!before(t1)) && (!after(t2));
    }

    // Переопределение метода toString для удобного вывода времени в формате "ЧЧ:ММ"
    @Override
    public String toString() {
        return hour + ":" + minute;
    }
}