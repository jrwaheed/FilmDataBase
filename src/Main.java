import com.mysql.cj.util.StringUtils;

import java.io.*;
import java.sql.*;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.sql.DriverManager;
import java.sql.Connection;


public class Main {
    public static void main(String[] args) throws IOException {

        List<String> title = new ArrayList<>();
        List<String> genre = new ArrayList<>();

        File filmlist = new File("/home/jamal/FilmDataBase/src/movilineOK.csv");
        FileReader fileReader = new FileReader(filmlist);

        BufferedReader br = new BufferedReader(fileReader);

        String movieline =null;
        int linecounter = 0;


        while(( movieline = br.readLine()) != null /*&& linecounter < 5*/) {
            ArrayList<String> temp = new ArrayList<String>(Arrays.asList(movieline.split(";")));
          //  System.out.println(movieline);
            if(temp.get(0).length() > 40) {
                title.add(temp.get(0).substring(0, 39));
            } else {
                title.add(temp.get(0));
            }


            try {
                if(temp.get(13).length() > 40) {
                    genre.add(temp.get(13).substring(0,  39));
                } else {
                    genre.add(temp.get(13));
                }
            } catch (IndexOutOfBoundsException e){
                genre.add("NA");
            }

            //If you want to use a class instead of two list
            //myDAO.insert(myObject);


            linecounter++;
        }

        try{
            for (int i = 0; i < /*5 */title.size(); i++) {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection con = DriverManager.getConnection("jdbc:mysql://localHost/FilmDataBase", "root", "TheHulk1*");
                PreparedStatement stmt = con.prepareStatement("insert into films (title, genre) values (?,?)");
                stmt.setString(1, title.get(i));
                stmt.setString(2,genre.get(i));
                stmt.execute();
                con.close();


            }



        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}
