package ru.yandex.practicum.gym;

import java.sql.Time;
import java.util.*;

public class Timetable {

    private HashMap<DayOfWeek, TreeMap<TimeOfDay, ArrayList<TrainingSession>>> timetable = new HashMap<>();

    public void addNewTrainingSession(TrainingSession trainingSession) {
        //проверяем, что занятие содержит все необходимые данные для расписания
        if(trainingSession == null || trainingSession.getCoach() == null || trainingSession.getTimeOfDay() == null
                || trainingSession.getDayOfWeek() == null) return;

        //подготовка к сохранению: если расписание только создано, то нужно создать необходимые списки
        TreeMap<TimeOfDay, ArrayList<TrainingSession>> trainingSessionsDuringDay =
                timetable.get(trainingSession.getDayOfWeek()) == null? new TreeMap<>():
                        timetable.get(trainingSession.getDayOfWeek());
        ArrayList<TrainingSession> trainingSessionsAtTime = trainingSessionsDuringDay.get(
                trainingSession.getTimeOfDay()) == null? new ArrayList<>(): trainingSessionsDuringDay.get(
                trainingSession.getTimeOfDay());
        if(trainingSessionsAtTime.contains(trainingSession)) return;

        trainingSessionsAtTime.add(trainingSession);
        trainingSessionsDuringDay.put(trainingSession.getTimeOfDay(),  trainingSessionsAtTime);
        timetable.put(trainingSession.getDayOfWeek(), trainingSessionsDuringDay);
    }

    public TreeMap<TimeOfDay, ArrayList<TrainingSession>> getTrainingSessionsForDay(DayOfWeek dayOfWeek) {
        if(timetable.get(dayOfWeek) == null) return new TreeMap<>();
        return timetable.get(dayOfWeek);
    }

    public List<TrainingSession> getTrainingSessionsForDayAndTime(DayOfWeek dayOfWeek, TimeOfDay timeOfDay) {
        TreeMap<TimeOfDay, ArrayList<TrainingSession>> trainingsForDay = timetable.get(dayOfWeek);
        if (trainingsForDay == null) {
            return Collections.emptyList();
        }
        return trainingsForDay.getOrDefault(timeOfDay, new ArrayList<>());
    }

    public LinkedHashMap<Coach, Integer> getCountbyCoaches(){
       HashMap<Coach, Integer> unsortedStatistics = new HashMap<>();
       for(DayOfWeek day: timetable.keySet()){
           for(TimeOfDay time: timetable.get(day).keySet()){
               ArrayList<TrainingSession> trainingSessions = timetable.get(day).get(time);
               for(TrainingSession trainingSession: trainingSessions){
                   if(trainingSession == null) continue;
                   int countPerCoach = unsortedStatistics.get(trainingSession.getCoach())== null? 0:
                           unsortedStatistics.get(trainingSession.getCoach());
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
       ArrayList<Coach> sortedCoaches = new ArrayList<>(unsortedStatistics.keySet());
       sortedCoaches.sort(comparator);

       LinkedHashMap<Coach, Integer> sortedStatistics = new LinkedHashMap<>();
       for(Coach coach: sortedCoaches){
           sortedStatistics.putLast(coach, unsortedStatistics.get(coach));
       }
       return sortedStatistics;
    }

}
