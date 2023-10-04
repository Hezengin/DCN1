package nl.avans;

import com.google.gson.Gson;

import java.io.*;
import java.net.URL;

public class KnockKnockProtocol {
    private static final int STARTING = 0;//first state when
    private static final int WAIT_FOR_ISBN = 1;//state where we  wait for input isbn
    private static int state = STARTING;


    public String processInput(String theInput) throws IOException {
        String theOutput = null;

        if (state == STARTING) {
            theOutput = "Knock! Knock! Give ISBN";
            state = WAIT_FOR_ISBN;
        } else if (state == WAIT_FOR_ISBN) {
            String isbn = theInput;

            if (theInput.startsWith("isbn: ")){
                isbn = theInput.substring(6);
            }

            URL bookInfo = new URL("https://www.googleapis.com/books/v1/volumes?q=isbn:" + isbn);

        BufferedReader in = new BufferedReader(new InputStreamReader(bookInfo.openStream()));

        String inputLine;
        while ((inputLine = in.readLine()) != null)
            System.out.println(inputLine);
        in.close();

            InputStream input = bookInfo.openStream();
            Reader reader = new InputStreamReader(input, "UTF-8");
            JsonResult result = new Gson().fromJson(reader, JsonResult.class);

            String s = "ISBN: " + isbn + "\n" +
                    "Title: " + result.getBookDetail().getTitle() + "\n" +
                    "Subtitle: " + result.getBookDetail().getSubTitle() + "\n" +
                    "Authors: " + getAuthors(result) + "\n" +
                    "Description: " + result.getBookDetail().getDescription() + "\n" +
                    "Pages: " + result.getBookDetail().getPageCount() + "\n" +
                    "Language: " + result.getBookDetail().getLanguage() + "\n";
            theOutput = s;
        }
        System.out.println("Server sent: " + theOutput);
        return theOutput;
    }

    public String getAuthors(JsonResult result) {
        String s = "";
        for (String author : result.getBookDetail().getAuthors()) {
            s = s.concat(author + ", ");
        }
        return s;
    }
}
