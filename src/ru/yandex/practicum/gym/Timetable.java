package ru.yandex.practicum.gym;

import com.sun.source.tree.Tree;

import java.lang.reflect.Array;
import java.util.*;

public class Timetable {

    private HashMap<DayOfWeek, TreeMap<TimeOfDay, ArrayList<TrainingSession>>> timetable = new HashMap<>();

    public void addNewTrainingSession(TrainingSession trainingSession) {
        //сохраняем занятие в расписании
        TreeMap<TimeOfDay, ArrayList<TrainingSession>> trainingSessionsDuringDay =
                timetable.get(trainingSession.getDayOfWeek()) == null? new TreeMap<>():
                        timetable.get(trainingSession.getDayOfWeek());
        ArrayList<TrainingSession> trainingSessionsAtTime = trainingSessionsDuringDay.get(
                trainingSession.getTimeOfDay()) == null? new ArrayList<>(): trainingSessionsDuringDay.get(
                trainingSession.getTimeOfDay());

        trainingSessionsAtTime.add(trainingSession);
        trainingSessionsDuringDay.put(trainingSession.getTimeOfDay(),  trainingSessionsAtTime);
        timetable.put(trainingSession.getDayOfWeek(), trainingSessionsDuringDay);
    }

    public ArrayList<TrainingSession> getTrainingSessionsForDay(DayOfWeek dayOfWeek) {
        //как реализовать, тоже непонятно, но сложность должна быть О(1)
        if(timetable.get(dayOfWeek) == null) return new ArrayList<>();
        ArrayList<TrainingSession> trainings = new ArrayList<>();
        for(TimeOfDay time: timetable.get(dayOfWeek).keySet()){
           trainings.addAll(timetable.get(dayOfWeek).get(time));
        }
        return trainings;
    }

    public ArrayList<TrainingSession> getTrainingSessionsForDayAndTime(DayOfWeek dayOfWeek, TimeOfDay timeOfDay) {
        //как реализовать, тоже непонятно, но сложность должна быть О(1)
        if(timetable.get(dayOfWeek) == null) return new ArrayList<>();
        if(timetable.get(dayOfWeek).get(timeOfDay) == null) return new ArrayList<>();
        ArrayList<TrainingSession> trainings = new ArrayList<>();
            trainings.addAll(timetable.get(dayOfWeek).get(timeOfDay));
        return trainings;
    }

}
