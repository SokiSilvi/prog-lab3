import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

public class Main {

    protected static File wd = new File(System.getProperty("user.dir"));
    protected static void printIOError(IOException e) {
        System.out.println("Oooof something went wrong :( Please stand by or try again later :)");
        System.out.println(e.getMessage());
    }


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while(true) {
            String line = scanner.nextLine();
            String[] words = line.split(" ");

            if (words[0].equals("exit")) { System.exit(0); }
            else if (words[0].equals("pwd")) { pwd(words); }
            else if (words[0].equals("ls")) { ls(words); }
            else if (words[0].equals("cd")) { cd(words); }
            else if (words[0].equals("rm")) { rm(words); }
            else if (words[0].equals("mkdir")) { mkdir(words); }
            else if (words[0].equals("mv")) { mv(words); }
            else if (words[0].equals("cp")) { cp(words); }
            else if (words[0].equals("cat")) { cat(words); }
            else if (words[0].equals("length")) { length(words); }
            else if (words[0].equals("head")) { head(words); }
            else if (words[0].equals("tail")) { tail(words); }
            else if (words[0].equals("wc")) { wc(words); }
            else if (words[0].equals("grap")) { grap(words); }

            else { System.out.println("Unknown command :(( What did you want to say? :("); }
        }

    }




    protected static void pwd_old(String[] args) {
        String homedirect = System.getProperty("user.dir");
        System.out.println("Your home directory is " + homedirect);
        File dir = new File(homedirect);
        File[] files = dir.listFiles();
        if (files != null)
            System.out.println("In this directory, you have exactly " + files.length + " files or directories. Cool!");

    }

    protected static void oldls(String[] args) {
        File[] files = wd.listFiles();
        System.out.println("Your directory's content:");
        if (files == null) return;
        for (File file: files) {
            System.out.println("\t " + file.getName());
        }
    }

    protected static void pwd(String[] args) {
        try {
            System.out.println(wd.getCanonicalPath());
        } catch (IOException e) { printIOError(e); }
    }

    protected static void cd(String[] args) {
        if (args.length < 2) {
            System.out.println("Please tell us where to go :(( ");
            return;
        }

        if (args[1].equals("..")) {
            wd = wd.getParentFile();
        }
        else {
            File newwd = new File(wd, args[1]);
            if (newwd.exists() && newwd.isDirectory()) wd = new File(wd, args[1]);
            else System.out.println("You do not have a directory named " + args[1]);
        }

    }

    protected static void ls(String[] args) {
        File[] files = wd.listFiles();
        System.out.println("Your directory's content:");
        if (files == null) return;
        for (File file: files) {
            String output = ("\t " + file.getName());
            if (args.length > 1 && args[1].equals("-l")) {
                output += "\t" + file.length() + " " + (file.isDirectory() ? "d" : "f");
            }
            System.out.println(output);
        }
    }

    protected static void rm(String[] args) {
        if (args.length < 2) {
            System.out.println("Tell us which file or empty directory did you want to remove :((");
            return;
        }
        try {
            File file = new File(wd, args[1]);
            if (!file.delete()) {
                System.out.println("Deleting this file was unsuccessful :(( Please try again later");
            }
        } catch (SecurityException e) {
            System.out.println("Access denied :(( \n" + e.getMessage());
        }
    }

    protected static void mkdir(String[] args) {
        if (args.length < 2) {
            System.out.println("Please tell us your desired name for your beautiful little new directory :((");
            return;
        }
        try {
            File path = new File(wd, args[1]);
            if (!path.mkdir()) {
                System.out.println("Making this directory was unsuccessful :(( Please try again later");
            }
        } catch (SecurityException e) {
            System.out.println("Access denied :(( \n" + e.getMessage());
        }
    }

    protected static void mv(String[] args) {
        if (args.length < 3) {
            System.out.println("Please tell us which file we should rename AND your desired beautiful little new name :((");
            return;
        }
        try {
            File path = new File(wd, args[1]);
            File newname = new File(wd, args[2]);
            if (!path.renameTo(newname)) {
                System.out.println("Renaming this directory was unsuccessful :(( Please try again later");
            }
        } catch (SecurityException e) {
            System.out.println("Access denied :(( \n" + e.getMessage());
        }
    }

    protected static void cp(String[] args) {
        if (args.length < 3) {
            System.out.println("Please tell us which file we copy AND where :((");
            return;
        }
        File source = new File(wd, args[1]);
        File destination = new File(wd, args[2]);
        if(source.exists()) {
            try(FileInputStream fr = new FileInputStream(source)) {
                try(FileOutputStream of = new FileOutputStream(destination)) {
                    int b;
                    while((b=fr.read()) != -1) {

                        of.write(b);
                    }
                }
            } catch (Exception e) {
                System.out.println("Error while opening your file :(( \n" + e.getMessage());
            }

        } else { System.out.println("We could not find Your beautiful little source file :(("); }

    }

    protected static void cat(String[] args) {
        if (args.length < 2) {
            System.out.println("Please tell us which file would yu like us to read aloud for you :((");
            return;
        }
        File file = new File(wd, args[1]);
        if (!file.exists()) {
            System.out.println("Your file does not seem to be existing :((");
            return;
        }
        try(Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                System.out.println(scanner.nextLine());
            }
        } catch (Exception e) {
            System.out.println("Error while opening your file :(( \n" + e.getMessage());
        }
    }

    protected static void length(String[] args) {
        if (args.length < 2) {
            System.out.println("Please tell us which file or directory you would want to know it's length is :((");
            return;
        }
        File file = new File(wd, args[1]);
        if (!file.exists()) {
            System.out.println("Your file does not seem to be existing :((");
            return;
        }
        System.out.println("Your files length is " + file.length());
    }

    protected static void head(String[] args) {
        if (args.length < 2) {
            System.out.println("Please tell us which file would yu like us to read aloud from for you :((");
            return;
        }
        File file;
        int n = 10;
        if (args[1].equals("-n")) {
            if (args.length < 4) {
                System.out.println("Please tell us how many lines AND which file would yu like us to read aloud from for you :((");
                return;
            }
            try {
                n = Integer.parseInt(args[2]);
            } catch (NumberFormatException e) {
                System.out.println("-n is not an integer :(( please write a valid integer like 1; 2; 3; 4; 5...");
                return;
            }
            file = new File(wd, args[3]);
        }
        else {
            file = new File(wd, args[1]);
        }
        if (!file.exists()) {
            System.out.println("Your file does not seem to be existing :((");
            return;
        }
        try(Scanner scanner = new Scanner(file)) {
            while(scanner.hasNextLine() && n > 0) {
                System.out.println(scanner.nextLine());
                n --;
            }
        } catch (Exception e) {
            System.out.println("Error while opening your file :(( \n" + e.getMessage());
        }
    }

    protected static void tail(String[] args) {
        if (args.length < 2) {
            System.out.println("Please tell us which file would yu like us to read aloud from for you :((");
            return;
        }
        File file;
        int n = 10;
        if (args[1].equals("-n")) {
            if (args.length < 4) {
                System.out.println("Please tell us how many lines AND which file would yu like us to read aloud from for you :((");
                return;
            }
            try {
                n = Integer.parseInt(args[2]);
            } catch (NumberFormatException e) {
                System.out.println("-n is not an integer :(( please write a valid integer like 1; 2; 3; 4; 5...");
                return;
            }
            file = new File(wd, args[3]);
        }
        else {
            file = new File(wd, args[1]);
        }
        if (!file.exists()) {
            System.out.println("Your file does not seem to be existing :((");
            return;
        }
        try(Scanner scanner = new Scanner(file)) {
            LinkedList<String> rows = new LinkedList<String>();
            while(scanner.hasNextLine()) {
                rows.add(scanner.nextLine());
            }
            Iterator<String> it = rows.descendingIterator();
            LinkedList<String> output = new LinkedList<>();
            while(it.hasNext() && n > 0) {
                output.add(it.next());
                n --;
            }
            Collections.reverse(output);
            for (String row: output) {
                System.out.println(row);
            }
        } catch (Exception e) {
            System.out.println("Error while opening your file :(( \n" + e.getMessage());
        }
    }

    protected static void wc(String[] args) {
        if (args.length < 2) {
            System.out.println("Please tell us which file would yu like us to read aloud for you :((");
            return;
        }
        File file = new File(wd, args[1]);
        if (!file.exists()) {
            System.out.println("Your file does not seem to be existing :((");
            return;
        }
        try(Scanner scanner = new Scanner(file)) {
            int rowsum = 0;
            int wordsum = 0;
            int charsum = 0;
            while (scanner.hasNextLine()) {
                rowsum++;
                String[] words = scanner.nextLine().split(" ");
                for(String word: words) {
                    wordsum++;
                    charsum += word.length();
                }
            }
            System.out.println("In this beautiful little file you have: ");
            System.out.println("\t" + rowsum + " rows");
            System.out.println("\t" + wordsum + " words");
            System.out.println("\t" + charsum + " chars");

        } catch (Exception e) {
            System.out.println("Error while opening your file :(( \n" + e.getMessage());
        }
    }

    protected static void grap(String[] args) {
        if (args.length < 3) {
            System.out.println("Please tell us what do you want us to search AND from which file you want us to search for in for you :((");
            return;
        }
        File file = new File(wd, args[2]);
        if (!file.exists()) {
            System.out.println("Your file does not seem to be existing :((");
            return;
        }
        try(Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.matches(".*" + args[1] + ".*")) {
                    System.out.println(line);
                }
            }
        } catch (Exception e) {
            System.out.println("Error while opening your file :(( \n" + e.getMessage());
        }
    }


}
