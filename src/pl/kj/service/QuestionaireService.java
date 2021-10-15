package pl.kj.service;

import pl.kj.document.Question;
import pl.kj.document.Questionnaire;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class QuestionaireService {


    FileWriter file = new FileWriter("questionnaire.txt", true);
    BufferedWriter save = new BufferedWriter(file);
    int questionnaireCounter = 1;
    int possibleAnswersCounter = 1;

    public QuestionaireService() throws IOException {
    }

    public void saveToDoc(Questionnaire questionnaire) throws IOException {
        List<Question> questions = questionnaire.getQuestions();
        save.write(questionnaire.getTitle());
        save.newLine();
        for (Question q : questions) {

            try {
                save.write("Pytanie " + questionnaireCounter + "." + q.getQuestionText());
                save.newLine();
                for (String answers : q.getPossibleAnswers()) {


                    save.write("    " + possibleAnswersCounter + "." + answers);
                    save.newLine();
                    possibleAnswersCounter++;

                }
                save.newLine();
                possibleAnswersCounter = 1;
                questionnaireCounter++;
            } catch (IOException ioe) {
                ioe.getStackTrace();
            }


        }
        save.close();

    }
}
