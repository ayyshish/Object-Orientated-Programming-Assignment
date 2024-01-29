package entity;

import java.io.*;
import java.util.*;

/**
 * Database to load/save all User objects created
 * @version 26/11/2023
 */
public class UserDatabase {

	public static final String SEPARATOR = ",";

    // an example of reading
	public static List<User> readUsers(String filename) throws IOException {
		// read String from text file
		ArrayList<String> stringArray = (ArrayList<String>)read(filename);
		ArrayList<User> alr = new ArrayList<User>();

        for (int i = 0 ; i < stringArray.size() ; i++) {
				String st = (String)stringArray.get(i);
				// get individual 'fields' of the string separated by SEPARATOR
				StringTokenizer star = new StringTokenizer(st , SEPARATOR);	// pass in the string to the string tokenizer using delimiter ","

				String userID = star.nextToken().trim();	// first token
				String password = star.nextToken().trim();	// second token
				String faculty = star.nextToken().trim();   // third token
				Boolean firstLogin = Boolean.parseBoolean(star.nextToken().trim()); // fourth token
				String usertype = star.nextToken().trim(); // fifth token
				UserType userType = UserType.valueOf(usertype);
				// create Professor object from file data
				User user = new User(userID, password, faculty, firstLogin, userType);
				// add to Professors list
				alr.add(user) ;
			}
        return alr;
	}

  // an example of saving
public static void saveUsers(String filename, List al) throws IOException {
		List<String> alw = new ArrayList<String>() ;// to store Professors data

        for (int i = 0 ; i < al.size() ; i++) {
				User user = (User)al.get(i);
				StringBuilder st =  new StringBuilder() ;
				st.append(user.getUserID());
				st.append(SEPARATOR);
				st.append(user.getPassword());
				st.append(SEPARATOR);
				st.append(user.getFaculty());
				st.append(SEPARATOR);
				st.append(user.getFirstLogin());
				st.append(SEPARATOR);
				st.append(user.getUserType());
				alw.add(st.toString());
			}
			write(filename,alw);
	}

  /** Write fixed content to the given file. */
  public static void write(String fileName, List data) throws IOException  {
    PrintWriter out = new PrintWriter(new FileWriter(fileName));

    try {
		for (int i =0; i < data.size() ; i++) {
      		out.println((String)data.get(i));
		}
    }
    finally {
      out.close();
    }
  }

  /** Read the contents of the given file. */
  public static List read(String fileName) throws IOException {
	List<String> data = new ArrayList<String>() ;
    Scanner scanner = new Scanner(new FileInputStream(fileName));
    try {
      while (scanner.hasNextLine()){
        data.add(scanner.nextLine());
      }
    }
    finally{
      scanner.close();
    }
    return data;
  }

}