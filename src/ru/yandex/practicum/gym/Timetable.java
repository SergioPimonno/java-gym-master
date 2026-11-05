package ru.yandex.practicum.gym;

import com.sun.source.tree.Tree;

import java.lang.reflect.Array;
import java.util.*;
import java.util.concurrent.CountDownLatch;

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
        //проверяем что расписание еще не содержит это же занятие (дупликат)
        if(trainingSessionsAtTime.contains(trainingSession)) return;

        //сохраняем занятие в расписании

        //если у тренера в один день и в одно время добавляется тренировка, то нужно выдать предупреждение и спросить,
        //хочет ли администратор перезаписать тренировку или отменить добавление
        //сейчас оно всегда перезаписывается
        trainingSessionsAtTime.add(trainingSession);
        trainingSessionsDuringDay.put(trainingSession.getTimeOfDay(),  trainingSessionsAtTime);
        timetable.put(trainingSession.getDayOfWeek(), trainingSessionsDuringDay);
    }

    public ArrayList<TrainingSession> getTrainingSessionsForDay(DayOfWeek dayOfWeek) {
        //как реализовать, тоже непонятно, но сложность должна быть О(1)*
        //*если для решения этой задачи достаточно возвращать TreeMap, то сложность будет O(1)
        //если возвращаем список (для удобства пользователя), то сложность будет O(n), где n-количество тренировок в день
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
