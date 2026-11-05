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

    public HashMap<Coach, Integer> getCountbyCoaches(){
       HashMap<Coach, Integer> unsortedStatistics = new HashMap<>();
       int countPerCoach = 0;
       for(DayOfWeek day: timetable.keySet()){
           for(TimeOfDay time: timetable.get(day).keySet()){
               ArrayList<TrainingSession> trainingSessions = timetable.get(day).get(time);
               for(TrainingSession trainingSession: trainingSessions){
                   countPerCoach = unsortedStatistics.get(trainingSession.getCoach());
                   countPerCoach++;
                   unsortedStatistics.put(trainingSession.getCoach(), countPerCoach);
               }
           }
       }
       Comparator<Coach> comparator = new Comparator<Coach>() {
           @Override
           public int compare(Coach o1, Coach o2) {
               return unsortedStatistics.get(o1) - unsortedStatistics.get(o2);
           }
       };
       ArrayList<Coach> sortedCoaches = (ArrayList<Coach>) unsortedStatistics.keySet().stream().sorted(comparator);
       HashMap<Coach, Integer> sortedStatistics = new HashMap<>();
       for(Coach coach: sortedCoaches){
           sortedStatistics.put(coach, unsortedStatistics.get(coach));
       }
       return sortedStatistics;
    }

}
