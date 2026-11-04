package ru.yandex.practicum.gym;

import java.lang.reflect.Array;
import java.util.*;

public class Timetable {

    private HashMap<DayOfWeek, TreeMap<TimeOfDay, ArrayList<TrainingSession>>> timetable;

    public void addNewTrainingSession(TrainingSession trainingSession) {
        //сохраняем занятие в расписании
        ArrayList<TrainingSession> trainings = timetable.get(trainingSession.getDayOfWeek()).get(trainingSession.getTimeOfDay());
        trainings.add(trainingSession);
    }

    public ArrayList<TrainingSession> getTrainingSessionsForDay(DayOfWeek dayOfWeek) {
        //как реализовать, тоже непонятно, но сложность должна быть О(1)
        ArrayList<TrainingSession> trainings = new ArrayList<>();
       for(TimeOfDay time: timetable.get(dayOfWeek).keySet()){
           trainings.addAll(timetable.get(dayOfWeek).get(time));
       }
       return trainings;
    }

    public ArrayList<TrainingSession> getTrainingSessionsForDayAndTime(DayOfWeek dayOfWeek, TimeOfDay timeOfDay) {
        //как реализовать, тоже непонятно, но сложность должна быть О(1)
        ArrayList<TrainingSession> trainings = new ArrayList<>();
            trainings.addAll(timetable.get(dayOfWeek).get(timeOfDay));
        return trainings;
    }
}
