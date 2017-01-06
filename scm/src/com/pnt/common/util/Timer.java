package com.pnt.common.util;

import java.util.Calendar;


public class Timer {
    private String interval; // 04:11:15 등의 경과 시간 문자열이 저장될 버퍼 정의
    private long startTime; // 타이머가 ON 되었을 때의 시각을 기억하고 있는 변수
    private long finishTime;

    public void start() {
        this.stopwatch(1);
    }
    
    public void stop() {
        this.stopwatch(0);
    }

    private void stopwatch(int onOff) {
        if (onOff == 1) // 타이머 켜기
            this.startTime = System.currentTimeMillis();

        if (onOff == 0) {
            this.finishTime = System.currentTimeMillis();
            secToHHMMSS(((int) this.finishTime / 1000) - ((int) this.startTime / 1000));
        }
    }

    // 정수로 된 시간을 초단위(sec)로 입력 받아, "04:11:15" 등의 형식의 문자열로 시분초를 저장
    private void secToHHMMSS(int secs) {
        int hour, min, sec;

        sec = secs % 60;
        min = secs / 60 % 60;
        hour = secs / 3600;

        this.interval = String.format("%02d:%02d:%02d", hour, min, sec);
    }

    public String getInterval() {
        return interval;
    }
    
    public void interval() {
        System.out.format("Timer OFF! 경과 시간: [%s]%n", this.interval); // 경과 시간 화면에 출력
    }
    
    public long getStartTime() {
        return startTime;
    }

    public long getFinishTime() {
        return finishTime;
    }

    public static String formatTime(long time) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(time);
        return (c.get(Calendar.HOUR_OF_DAY) + "시 " + c.get(Calendar.MINUTE) + "분 " + c.get(Calendar.SECOND) + "." + c.get(Calendar.MILLISECOND) + "초");
    }

}
