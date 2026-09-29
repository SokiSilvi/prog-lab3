import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class FilterMain {
    public static void main(String[] args) {
        String input = null;
        String output = null;
        String pattern = "";
        boolean gi = false;
        boolean go = false;
        for (int i = 0; i < args.length; i++) {
            if ((i+1 < args.length) && args[i].equals("-i")) {
                i++;
                input = args[i];
            } else if ((i+1 < args.length) && args[i].equals("-o")) {
                i++;
                output = args[i];
            } else if ((i+1 < args.length) && args[i].equals("-p")) {
                i++;
                pattern = args[i];
            } else if (args[i].equals("-go")) {
                go = true;
            } else if (args[i].equals("-gi")) {
                gi = true;
            }
        }
        List<String> lines = new ArrayList<>();

        if (input == null || output == null) { return; }
        File infile = new File(input);
        File outfile = new File(output);

        if (gi) {
            try(InputStream in = new GZIPInputStream(new FileInputStream(infile));
                Scanner scanner = new Scanner(in)) {

                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    if (line.matches(pattern)) {
                        lines.add(line);
                    }
                }

            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            try(Scanner scanner = new Scanner(infile)) {

                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    if (line.matches(pattern)) {
                        lines.add(line);
                    }
                }

            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }

        if (go) {
            try(OutputStream os = new GZIPOutputStream(new FileOutputStream(outfile));
            PrintWriter pw = new PrintWriter(os)) {

                for (String line: lines) {
                    pw.println(line);
                }

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        else {
            try(PrintWriter pw = new PrintWriter(outfile)) {

                for (String line: lines) {
                    pw.println(line);
                }

            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
