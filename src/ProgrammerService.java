import pl.kj.document.*;
import pl.kj.organization.User;
import pl.kj.service.QuestionaireService;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProgrammerService extends Thread {


    public synchronized void execute(DocumentDao documentDao) throws IOException {
        //Miejsce na twój kod:

        Runnable runnable = () -> {
            List<Document> documentList = documentDao.getAllDocumentsInDatabase();
            List<ApplicationForHolidays> applicationForHolidaysList = new ArrayList<>();
            List<Questionnaire> questionnairesList = new ArrayList<>();
            List<User> listOfUsersApplications = new ArrayList<>();

            for (Document doc : documentList) {

                if (doc.getClass() == (ApplicationForHolidays.class)) {
                    applicationForHolidaysList.add((ApplicationForHolidays) doc);
                }
                if (doc.getClass() == (Questionnaire.class)) {
                    questionnairesList.add((Questionnaire) doc);

                }

            }

            long start = System.currentTimeMillis();

            //ZADANIE 1.
            long totalPossibleAnswers = 0;
            int totalQuestions = 0;
            for (Questionnaire q : questionnairesList) {

                totalPossibleAnswers += q.getQuestions().stream()
                        .map(question -> question.getPossibleAnswers().stream().count()).mapToLong(c -> c).sum();

                totalQuestions += q.getQuestions().stream()
                        .count();
            }
            System.out.println("The average number of possible answers of all questions in all questionnaires = " + totalPossibleAnswers / totalQuestions);

            long end = System.currentTimeMillis();
            System.out.println("Executing time of Ex1.: " + (end - start));
            System.out.println();

            //ZADANIE 2.

            //Jezeli użytkownik posiada login zawierający polskie znaki, zwraca TRUE

            start = System.currentTimeMillis();
            applicationForHolidaysList.stream()
                    .forEach(applicationForHolidays -> listOfUsersApplications.add(applicationForHolidays.getUserWhoRequestAboutHolidays()));

            Pattern polishCharactersPattern = Pattern.compile("[ąćęłńóśźż]");


            listOfUsersApplications.stream()
                    .map(user ->
                    {
                        System.out.print(user.getLogin() + " - ");
                        Matcher matcher = polishCharactersPattern.matcher(user.getLogin());
                        return matcher.find();
                    })
                    .forEach(System.out::println);
            end = System.currentTimeMillis();
            System.out.println("Executing time of Ex2.: " + (end - start));
            System.out.println();

            //Zadanie 3

            //Jezeli data "to" jest wczesniej niz "since", zwraca TRUE
            start = System.currentTimeMillis();
            applicationForHolidaysList.stream()
                    .map(application ->
                    {
                        System.out.print(application.getSince() +
                                " - " + application.getTo() + " - ");
                        return application.getTo().before(application.getSince());
                    })
                    .forEach(System.out::println);
            end = System.currentTimeMillis();
            System.out.println("Executing time of Ex.3: " + (end - start));
            System.out.println();

            //Zadanie 4
            start = System.currentTimeMillis();
            QuestionaireService questionaireService;
            try {
                questionaireService = new QuestionaireService();
                //pobieranie ankiety z listy po indexie i zapisanie danych do pliku questionnaire.txt
                questionaireService.saveToDoc(questionnairesList.get(1));
            } catch (IOException e) {
                e.printStackTrace();
            }
            end = System.currentTimeMillis();
            System.out.println("Executing time of Ex.4: " + (end - start));
            System.out.println();

            //Zadanie 5
            start = System.currentTimeMillis();
            String login = "nowaczka";
            listOfUsersApplications.stream()
                    .forEach(application ->
                    {
                        if (application.getLogin().equals(login)) {
                            try {
                                Class c = application.getClass();
                                String field = "salary";
                                Field f = c.getDeclaredField(field);
                                f.setAccessible(true);
                                f.setInt(application, 5000);
                            } catch (NoSuchFieldException nsfe) {
                                nsfe.getStackTrace();
                            } catch (IllegalAccessException iae) {
                                iae.getStackTrace();
                            }
                        }
                        System.out.println(application.getLogin() + " salary: " + application.getSalary());
                    });
            end = System.currentTimeMillis();
            System.out.println("Executing time of Ex.5: " + (end - start));
            System.out.println();

        };
        Thread t1 = new Thread(runnable);
        t1.start();
    }

}
