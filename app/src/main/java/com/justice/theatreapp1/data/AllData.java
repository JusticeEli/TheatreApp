package com.justice.theatreapp1.data;

import android.os.Environment;

import com.backendless.BackendlessUser;
import com.justice.theatreapp1.theatre.theatre_first_page.ShowData;
import com.justice.theatreapp1.theatre.theatre_first_page.view_feedback.Feedback;
import com.justice.theatreapp1.theatre.theatre_login.TheatreLoginData;
import com.justice.theatreapp1.theatre.theatre_register.TheatreRegisterData;
import com.justice.theatreapp1.user.user_first_page.book_my_show.BookData;
import com.justice.theatreapp1.user.user_register.UserRegisterData;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class AllData {
    public static BackendlessUser user;

     public static List<TheatreLoginData> theatreLoginDataList = new ArrayList<>();
    public static List<TheatreRegisterData> theatreRegisterDataList = new ArrayList<>();
    public static List<UserRegisterData> userRegisterDataList = new ArrayList<>();
    public static List<ShowData> showsDataList = new ArrayList<>();
    public static List<BookData> bookDataList = new ArrayList<>();
    public static List<Feedback> feedbackList = new ArrayList<>();
    public static String path;



    public static void initializeDirectoryAndFile() {
        path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/TheatreData";
        File dir = new File(path);
        dir.mkdirs();


    }


    public static void writeAllDataToFiles() {

        /**
         *   AllData allData = new AllData();
         *              allData.writeUserLoginDataList();
         *              allData.writeTheatreLoginDataList();
         *              allData.writeTheatreRegisterDataList();
         *              allData.writeUserRegisterDataList();
         *              allData.writeShowDataList();
         *             allData.writeBookDataList();
         *            allData.writeFeedbackList();
         *
         */


    }


    /**
     * public void writeBookDataListDemo(){
     * try {
     * FileOutputStream fileOutputStream = new FileOutputStream("book_data.bin");
     * ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
     * <p>
     * objectOutputStream.writeObject(feedbackList);
     * } catch (IOException e) {
     * e.printStackTrace();
     * }
     * <p>
     * <p>
     * }
     */

    public void writeBookDataList() {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(path + "/book_data.bin");
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

            objectOutputStream.writeObject(bookDataList);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void writeFeedbackList() {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(path + "/feedback.bin");
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

            objectOutputStream.writeObject(feedbackList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeShowDataList() {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(path + "/show_data.bin");
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

            objectOutputStream.writeObject(showsDataList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeUserRegisterDataList() {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(path + "/user_register_data.bin");
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

            objectOutputStream.writeObject(userRegisterDataList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeTheatreRegisterDataList() {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(path + "/theatre_register_data.bin");
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

            objectOutputStream.writeObject(theatreRegisterDataList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeTheatreLoginDataList() {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(path + "/theatre_login_data.bin");
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

            objectOutputStream.writeObject(theatreLoginDataList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




    public static void readAllDataFromFiles() {
        /**
         *    AllData allData = new AllData();
         *               allData.readUserLoginDataList();
         *               allData.readTheatreLoginDataList();
         *               allData.readTheatreRegisterDataList();
         *               allData.readUserRegisterDataList();
         *               allData.readShowDataList();
         *               allData.readBookDataList();
         *               allData.readFeedbackList();
         *
         */


    }


    private void readFeedbackList() {
        try {
            FileInputStream fileInputStream = new FileInputStream(path + "/feedback.bin");
            ObjectInputStream objectInputStream = new ObjectInputStream((fileInputStream));
            feedbackList = (List<Feedback>) objectInputStream.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void readBookDataList() {
        try {
            FileInputStream fileInputStream = new FileInputStream(path + "/book_data.bin");
            ObjectInputStream objectInputStream = new ObjectInputStream((fileInputStream));
            bookDataList = (List<BookData>) objectInputStream.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void readShowDataList() {
        try {
            FileInputStream fileInputStream = new FileInputStream(path + "/show_data.bin");
            ObjectInputStream objectInputStream = new ObjectInputStream((fileInputStream));
            showsDataList = (List<ShowData>) objectInputStream.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void readUserRegisterDataList() {
        try {
            FileInputStream fileInputStream = new FileInputStream(path + "/user_register_data.bin");
            ObjectInputStream objectInputStream = new ObjectInputStream((fileInputStream));
            userRegisterDataList = (List<UserRegisterData>) objectInputStream.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void readTheatreRegisterDataList() {
        try {
            FileInputStream fileInputStream = new FileInputStream(path + "/theatre_register_data.bin");
            ObjectInputStream objectInputStream = new ObjectInputStream((fileInputStream));
            theatreRegisterDataList = (List<TheatreRegisterData>) objectInputStream.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void readTheatreLoginDataList() {
        try {
            FileInputStream fileInputStream = new FileInputStream(path + "/theatre_login_data.bin");
            ObjectInputStream objectInputStream = new ObjectInputStream((fileInputStream));
            theatreLoginDataList = (List<TheatreLoginData>) objectInputStream.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }



    private void readFromFile() {


        String data = null;
        try {

            FileInputStream fileInputStream = new FileInputStream(path + "/theatre_register_data.bin");
            ObjectInputStream objectInputStream = new ObjectInputStream((fileInputStream));
            AllData.theatreRegisterDataList = (List<TheatreRegisterData>) objectInputStream.readObject();
            String names = "";
            for (TheatreRegisterData user : AllData.theatreRegisterDataList) {
                names += user.toString() + " \n\n";
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    //////////////////////////////PUT DUMMY VALUES //////////////////////////////




}
