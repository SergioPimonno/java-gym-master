package ru.yandex.practicum.gym;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.*;

public class TimetableTest {

    @Test
    void testGetTrainingSessionsForDaySingleSession() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);

        Assertions.assertEquals(1, timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY).size());
        //Проверили, что за понедельник вернулось одно занятие

        Assertions.assertTrue(timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY).isEmpty());
        //Проверили, что за вторник не вернулось занятий

        TreeMap<TimeOfDay, List<TrainingSession>> checkMap = new TreeMap<>();
        checkMap.put(singleTrainingSession.getTimeOfDay(), List.of(singleTrainingSession));
        Assertions.assertEquals(checkMap,timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY));
        //Проверили, что за понедельник вернулось именно то занятие, которое добавили
    }

    @Test
    void testGetTrainingSessionsForDayMultipleSessions() {
        Timetable timetable = new Timetable();

        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");

        Group groupAdult = new Group("Акробатика для взрослых", Age.ADULT, 90);
        TrainingSession thursdayAdultTrainingSession = new TrainingSession(groupAdult, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(20, 0));

        timetable.addNewTrainingSession(thursdayAdultTrainingSession);

        Group groupChild = new Group("Акробатика для детей", Age.CHILD, 60);
        TrainingSession mondayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        TrainingSession thursdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(13, 0));
        TrainingSession saturdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.SATURDAY, new TimeOfDay(10, 0));

        timetable.addNewTrainingSession(mondayChildTrainingSession);
        timetable.addNewTrainingSession(thursdayChildTrainingSession);
        timetable.addNewTrainingSession(saturdayChildTrainingSession);

        Assertions.assertEquals(1, timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY).size());
        // Проверили, что за понедельник вернулось одно занятие

        TreeMap<TimeOfDay, List<TrainingSession>> checkMap = new TreeMap<>();
        checkMap.put(mondayChildTrainingSession.getTimeOfDay(), List.of(mondayChildTrainingSession));
        Assertions.assertEquals(checkMap, timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY));
        //Проверили, что за понедельник вернулось именно то занятие, которое добавили

        Timetable correctTimetableForThursday = new Timetable();
        correctTimetableForThursday.addNewTrainingSession(thursdayChildTrainingSession);
        correctTimetableForThursday.addNewTrainingSession(thursdayAdultTrainingSession);
        Assertions.assertEquals(correctTimetableForThursday.getTrainingSessionsForDay(DayOfWeek.THURSDAY),
                timetable.getTrainingSessionsForDay(DayOfWeek.THURSDAY));
        // Проверили, что за четверг вернулось два занятия в правильном порядке: сначала в 13:00, потом в 20:00

        checkMap.clear();
        checkMap.put(thursdayChildTrainingSession.getTimeOfDay(), List.of(thursdayChildTrainingSession));
        checkMap.put(thursdayAdultTrainingSession.getTimeOfDay(), List.of(thursdayAdultTrainingSession));
        Assertions.assertEquals(checkMap, timetable.getTrainingSessionsForDay(DayOfWeek.THURSDAY));
        // Проверили, что за четверг вернулись те самые два занятия в правильном порядке: сначала в 13:00, потом в 20:00
        //избыточный тест?

        Assertions.assertTrue(timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY).isEmpty());
        // Проверили, что за вторник не вернулось занятий
    }

    @Test
    void testGetTrainingSessionsForDayAndTime() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);

        Assertions.assertEquals(1, timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY,
                new TimeOfDay(13, 00)).size());
        //Проверили, что за понедельник в 13:00 вернулось одно занятие

        Assertions.assertEquals(0, timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY,
                new TimeOfDay(14, 00)).size());
        //Проверили, что за понедельник в 14:00 не вернулось занятий
    }

    @Test
    void testGetEmptyListFromEmptyTimeTableByGetTrainingSessionForDayMethod(){
        Timetable timetable = new Timetable();
        Assertions.assertEquals(new HashMap<>(), timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY));
        //проверили что пустое расписание не выдаст ошибку при отсутствии содержимого и вернет пустой список
    }
    @Test
    void testGetEmptyListFromEmptyTimeTableByGetTrainingSessionForDayAndTimeMethod(){
        Timetable timetable = new Timetable();
        Assertions.assertEquals(new ArrayList<>(), timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY,
                new TimeOfDay(12,00)));
        //проверили что пустое расписание не выдаст ошибку при отсутствии содержимого и вернет пустой список
    }
    @Test
    void testGetEmptyListFromEmptyTimeTableByGetCountByCoaches(){
        Timetable timetable = new Timetable();
        Assertions.assertEquals(new HashMap<Coach, Integer>(), timetable.getCountbyCoaches());
        //проверили что пустое расписание не выдаст ошибку при отсутствии содержимого и вернет пустой список
    }

    @Test
    void testGetCountByCoachesForOneCoach(){
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);

        HashMap<Coach, Integer> testStats = new HashMap<>();
        testStats.put(coach, 1);
        Assertions.assertEquals(testStats, timetable.getCountbyCoaches());

    }

    @Test
    void testGetCountByCoachesForSeveralCoachesWithDifferentCounts(){
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach1 = new Coach("Васильев", "Николай", "Сергеевич");
        Coach coach2 = new Coach("Васильева", "Светлана", "Сергеевна");
        Coach coach3 = new Coach("Светлаков", "Игорь", "Евгеньевич");
        TrainingSession firstTrainingSessionC1 = new TrainingSession(group, coach1,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        TrainingSession secondTrainingSessionC1 = new TrainingSession(group, coach1,
                DayOfWeek.MONDAY, new TimeOfDay(14, 0));
        TrainingSession trainingSessionC2 = new TrainingSession(group, coach2,
                DayOfWeek.TUESDAY, new TimeOfDay(13, 0));


        timetable.addNewTrainingSession(firstTrainingSessionC1);
        timetable.addNewTrainingSession(secondTrainingSessionC1);
        timetable.addNewTrainingSession(trainingSessionC2);

        HashMap<Coach, Integer> testStats = new HashMap<>();
        testStats.put(coach1, 2);
        testStats.put(coach2, 1);
        testStats.put(coach3, 0);
        Assertions.assertFalse(testStats.equals(timetable.getCountbyCoaches()));
        //если тренера в расписании нет, то он в статистике не появляется, что логично

        testStats.remove(coach3);
        Assertions.assertEquals(testStats, timetable.getCountbyCoaches());

    }

    @Test
    void testGetCountByCoachesForSeveralCoachesWithSameCounts(){
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach1 = new Coach("Васильев", "Николай", "Сергеевич");
        Coach coach2 = new Coach("Васильева", "Светлана", "Сергеевна");
        TrainingSession firstTrainingSessionC1 = new TrainingSession(group, coach1,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        TrainingSession trainingSessionC2 = new TrainingSession(group, coach2,
                DayOfWeek.TUESDAY, new TimeOfDay(13, 0));


        timetable.addNewTrainingSession(firstTrainingSessionC1);
        timetable.addNewTrainingSession(trainingSessionC2);

        LinkedHashMap<Coach, Integer> testStats1 = new LinkedHashMap<>();
        testStats1.put(coach1, 1);
        testStats1.put(coach2, 1);
        Assertions.assertTrue(testStats1.equals(timetable.getCountbyCoaches()));
        //проверили содержимое
        Assertions.assertEquals(List.of(coach1, coach2), List.of(timetable.getCountbyCoaches().keySet().toArray()));
        Assertions.assertFalse(List.of(coach2, coach1).equals(
                List.of(timetable.getCountbyCoaches().keySet().toArray())));
        //проверили отсортированность содержимого
    }

    @Test
    void testGetCountByCoachForSeveralDays(){
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession firstTrainingSessionMonday = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        TrainingSession secondTrainingSessionMonday = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(14, 0));
        TrainingSession trainingSessionTuesday = new TrainingSession(group, coach,
                DayOfWeek.TUESDAY, new TimeOfDay(13, 0));


        timetable.addNewTrainingSession(firstTrainingSessionMonday);
        timetable.addNewTrainingSession(secondTrainingSessionMonday);
        timetable.addNewTrainingSession(trainingSessionTuesday);

        LinkedHashMap<Coach, Integer> testStats = new LinkedHashMap<>();
        testStats.put(coach, 3);
        Assertions.assertEquals(testStats, timetable.getCountbyCoaches());

    }

    @Test
    void testGetCountByCoachMethodConsistency(){
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession firstTrainingSessionMonday = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        TrainingSession secondTrainingSessionMonday = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(14, 0));
        TrainingSession trainingSessionTuesday = new TrainingSession(group, coach,
                DayOfWeek.TUESDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(firstTrainingSessionMonday);
        timetable.addNewTrainingSession(secondTrainingSessionMonday);

        LinkedHashMap<Coach, Integer> firstMethodCall = timetable.getCountbyCoaches();
        LinkedHashMap<Coach, Integer> secondMethodCall = timetable.getCountbyCoaches();
        Assertions.assertEquals(firstMethodCall, secondMethodCall);

        timetable.addNewTrainingSession(trainingSessionTuesday);
        LinkedHashMap<Coach, Integer> testStats = new LinkedHashMap<>();
        testStats.put(coach, 3);
        Assertions.assertEquals(testStats, timetable.getCountbyCoaches());
        Assertions.assertFalse(firstMethodCall.equals(timetable.getCountbyCoaches()));

    }

    @Test
    void testGetTrainingSessionsForDaySingleSessionSeveralTimes() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);
        timetable.addNewTrainingSession(singleTrainingSession);
        timetable.addNewTrainingSession(singleTrainingSession);

        Assertions.assertEquals(1, timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY).size());
        //Проверили, что за понедельник вернулось одно занятие

        Assertions.assertTrue(timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY).isEmpty());
        //Проверили, что за вторник не вернулось занятий

        TreeMap<TimeOfDay, List<TrainingSession>> checkMap = new TreeMap<>();
        checkMap.put(singleTrainingSession.getTimeOfDay(), List.of(singleTrainingSession));
        Assertions.assertEquals(checkMap, timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY));
        //Проверили, что за понедельник вернулось именно то занятие, которое добавили
    }

    @Test
    void addMultipleSessionsPerDayAndTimeWorksAsIntended(){
        Timetable timetable = new Timetable();

        Coach coach1 = new Coach("Васильев", "Николай", "Сергеевич");
        Coach coach2 = new Coach("Васильев", "Семен", "Сергеевич");

        Group groupChild = new Group("Акробатика для детей", Age.CHILD, 60);
        TrainingSession mondayChildTrainingSession13 = new TrainingSession(groupChild, coach1,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        TrainingSession mondayChildTrainingSession14 = new TrainingSession(groupChild, coach1,
                DayOfWeek.MONDAY, new TimeOfDay(14, 0));
        Group groupAdult = new Group("Акробатика для взрослых", Age.ADULT, 60);
        TrainingSession mondayAdultTrainingSession = new TrainingSession(groupAdult, coach2,
                DayOfWeek.MONDAY, new TimeOfDay(14, 0));

        timetable.addNewTrainingSession(mondayChildTrainingSession13);
        timetable.addNewTrainingSession(mondayChildTrainingSession14);
        timetable.addNewTrainingSession(mondayAdultTrainingSession);

        TreeMap<TimeOfDay, List<TrainingSession>> checkMap = new TreeMap<>();
        checkMap.put(mondayChildTrainingSession13.getTimeOfDay(), List.of(mondayChildTrainingSession13));
        checkMap.put(mondayAdultTrainingSession.getTimeOfDay(), List.of(mondayChildTrainingSession14,
                mondayAdultTrainingSession));
        Assertions.assertEquals(checkMap, timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY));
        //Проверили, что за понедельник вернулось именно то занятие, которое добавили
    }

}
