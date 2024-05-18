package org.example;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;

public class Main {
    public static void main(String[] args) throws IOException {
        ArrayList<Program> allProgram = new ArrayList<>();
        Deserialization deserialization = new Deserialization();
        ArrayList<String> fileData;

        try {
            fileData = deserialization.read();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Переменные для временного хранения канала и времени
        String currentChannel = null;
        BroadcastsTime currentTime = null;

        // Разбор данных из файла и создание объектов Program
        for (String fileDatum : fileData) {
            if (fileDatum.contains("#")) {
                currentChannel = fileDatum;
            } else if (fileDatum.contains(":") && fileDatum.length() == 5) {
                currentTime = new BroadcastsTime(fileDatum);
            } else {
                allProgram.add(new Program(currentChannel, currentTime, fileDatum));
            }
        }

        sortTime(allProgram);

        // Сохранение отсортированных данных в Excel файл
        Serialization writer = new Serialization(allProgram, "тв-программы.xlsx");
        Serialization.saveToExcel(allProgram, "тв-программы.xlsx");
    }

    // Метод для сортировки программ по времени
    public static ArrayList<Program> sortTime(ArrayList<Program> allProgram){
        allProgram.sort((program1, program2) -> {
            int timeComparison = program1.getTime().compareTo(program2.getTime());
            return timeComparison;
        });
        return allProgram;
    }

    // Метод для получения текущих программ
    public static ArrayList<Program> curProgram(ArrayList<Program> allProgram, String curTime){
        BroadcastsTime curBroadTome = new BroadcastsTime(curTime);
        ArrayList<Program> curProg = new ArrayList<>();
        for (Program program : allProgram) {
            if (program.getTime().compareTo(curBroadTome) == 0) {
                curProg.add(program);
            }
        }
        return curProg;
    }

    // Метод для получения программ по названию
    public static ArrayList<Program> getProgramsWithName(ArrayList<Program> programs, String name) {
        ArrayList<Program> programsWithName = new ArrayList<>();
        for(Program program: programs) {
            if (program.getName().equals(name)) {
                programsWithName.add(program);
            }
        }
        return programsWithName;
    }

    // Метод для получения программ между двумя временными интервалами на заданном канале
    public static ArrayList<Program> programBetween(ArrayList<Program> allProgram, String time1, String time2, String channel){
        BroadcastsTime broadTime1 = new BroadcastsTime(time1);
        BroadcastsTime broadTime2 = new BroadcastsTime(time2);
        ArrayList<Program> progBetween = new ArrayList<>();
        for (Program program : allProgram) {
            if (program.getTime().between(broadTime1, broadTime2) && (program.getChannel().equals("#" + channel))){
                progBetween.add(program);
            }
        }
        return progBetween;
    }

    // Метод для получения программ на заданном канале в текущее время
    public static ArrayList<Program> getChanelNameAndCurrentTime(ArrayList<Program> allProgram, String curTime, String chanelName) {
        ArrayList<Program> currentTimePrograms = curProgram(allProgram, curTime);
        ArrayList<Program> programsWithChanelAndCurrentTime = new ArrayList<>();
        for(Program program: currentTimePrograms) {
            if (program.getChannel().equals("#" + chanelName)) {
                programsWithChanelAndCurrentTime.add(program);
            }
        }
        return programsWithChanelAndCurrentTime;
    }
}