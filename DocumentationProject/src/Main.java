import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author Riza Adrian
 * @email rizariza19@gmail.com
 **/

public class Main {

    public static void main(String[] args) throws IOException {
        File dir = new File(".");

        File f = new File("Test.html");
        if ( f.exists() ) {
            f.delete();
        }
        if ( !f.exists() ) {
            f.createNewFile();
        } else {
            System.out.println("File already exists");
        }


        String source = dir.getCanonicalPath() + File.separator + "imp.k";
        String dest = dir.getCanonicalPath() + File.separator + "Test.html";

        File fin = new File(source);
        FileInputStream fis = new FileInputStream(fin);
        BufferedReader in = new BufferedReader(new InputStreamReader(fis));

        FileWriter fstream = new FileWriter(dest, true);
        BufferedWriter out = new BufferedWriter(fstream);

        out.write("<!DOCTYPE html>");
        out.newLine();
        out.write("<html lang=\"en\">");
        out.newLine();
        out.write("<title>plp project</title>");
        out.newLine();
        out.write("<meta charset=\"UTF-8\">");
        out.newLine();
        out.write("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">");
        out.newLine();
        out.write("<link rel=\"stylesheet\" href=\"https://www.w3schools.com/w3css/4/w3.css\">");
        out.newLine();
        out.write("<body>");
        out.newLine();
        out.write("<header class=\"w3-display-container w3-content w3-center\" style=\"max-width:1500px\">");
        out.newLine();
        out.write("<div class=\"w3-display-middle w3-padding-large w3-border w3-wide w3-center\">");
        out.newLine();
        out.write(" <h4 class=\"\" style=\"color:black\">Documentation generator</h4>");
        out.newLine();
        out.write("</div>");
        out.newLine();
        out.write("</header>");
        out.newLine();
        out.write("<div class=\"w3-content w3-padding-large w3-margin-top\" id=\"portfolio\">");
        out.newLine();
        out.write("<div class=\"w3-light-grey w3-padding-large w3-padding-32 w3-margin-top\" id=\"contact\">");
        out.newLine();
        out.write("<pre>");
        out.newLine();
        out.write("<code>");
        out.newLine();

        String aLine = null;
        while ((aLine = in.readLine()) != null) {
            if ( aLine.contains("/**") || aLine.startsWith("/**") ) {
                // out.write("<h3 class=\"w3-center\">DOCUMENTATION API</h3>");
                out.write("<h4 class=\"w3-left\" style=\"color:red\"><u>DOCUMENTATION API</u></h4>");
                out.write("</br>");
                out.newLine();
                out.newLine();
                out.newLine();
                if ( aLine.contains("*") ) {
                    for (int i = 1; i < 2; i = i + 1) {
                        out.write("<span style=\"color:red\">" + aLine);
                        out.newLine();
                    }

                }
            } else if ( aLine.endsWith("*/") ) {
                for (int i = 0; i < 1; i = i + 1) {
                    out.write(aLine + "</span>");
                    out.newLine();
                }
            } else if ( aLine.contains("//@") ) {
                out.write("<h4 class=\"w3-left\" style=\"color:red\"><u>DOCUMENTATION API</u></h4>");
                out.write("</br>");
                out.newLine();
                out.newLine();
                for (int i = 0; i < 1; i = i + 1) {
                    out.write("<span style=\"color:red\">" + aLine + "</span>");
                    out.newLine();

                }
            } else {
                if ( aLine.contains("<env>") || aLine.contains("</env>") ) {
                    aLine = aLine.replace("<env>", "&ltenv&gt;");
                    aLine = aLine.replace("</env>", "&lt/env&gt;");
                }
                if ( aLine.contains("<k>") || aLine.contains("</k>") ) {
                    aLine = aLine.replace("<k>", "&ltk&gt;");
                    aLine = aLine.replace("</k>", "&lt/k&gt;");
                }
                if ( aLine.contains("<store>") || aLine.contains("</store>") ) {
                    aLine = aLine.replace("<store>", "&ltstore&gt;");
                    aLine = aLine.replace("</store>", "&lt/store&gt;");
                }
                if ( aLine.contains("<stack>") || aLine.contains("</stack>") ) {
                    aLine = aLine.replace("<stack>", "&ltstack&gt;");
                    aLine = aLine.replace("</stack>", "&lt/stack&gt;");
                }
                if ( aLine.contains("<in>") || aLine.contains("</in>") ) {
                    aLine = aLine.replace("<in>", "&lt in &gt;");
                    aLine = aLine.replace("</in>", "&lt/in&gt;");
                }
                if ( aLine.contains("<out>") || aLine.contains("</out>") ) {
                    aLine = aLine.replace("<out>", "&ltout&gt;");
                    aLine = aLine.replace("</out>", "&lt/out&gt;");
                }
                if ( aLine.contains("<fstack>") || aLine.contains("</fstack>") ) {
                    aLine = aLine.replace("<fstack>", "&ltfstack&gt;");
                    aLine = aLine.replace("</fstack>", "&lt/fstack&gt;");
                }
                if ( aLine.contains("<in stream=\"stdin\">") || aLine.contains("<out stream=\"stdout\">") ) {
                    aLine = aLine.replace("<out stream=\"stdout\">", "&lt out stream=\"stdout\"&gt;");
                    aLine = aLine.replace("<in stream=\"stdin\">", "&lt in stream=\"stdin\"&gt;");
                }
                if ( aLine.contains("<T>") || aLine.contains("<T>") ) {
                    aLine = aLine.replace("<T>", "&ltT&gt;");
                    aLine = aLine.replace("</T>", "&lt/T&gt;");
                }
                if ( aLine.contains("<generatedTop>") || aLine.contains("</generatedTop>") ) {
                    aLine = aLine.replace("<generatedTop>", "&ltgeneratedTop&gt;");
                    aLine = aLine.replace("</generatedTop>", "&lt/generatedTop&gt;");
                }
                if ( aLine.contains("<threads>") || aLine.contains("</threads>") ) {
                    aLine = aLine.replace("<threads>", "&ltthreads&gt;");
                    aLine = aLine.replace("</threads>", "&lt/threads&gt;");
                }
                if ( aLine.contains("<thread>") || aLine.contains("</thread>") ) {
                    aLine = aLine.replace("<thread>", "&ltthread&gt;");
                    aLine = aLine.replace("</thread>", "&lt/thread&gt;");
                }
                out.write(aLine);
                out.newLine();
            }
        }
        out.write("</code>");
        out.newLine();
        out.write("</pre>");
        out.write("</div>");
        out.newLine();
        out.write("</div>");
        out.newLine();
        out.write("</body>");
        out.newLine();
        out.write("</html>");

        in.close();
        out.close();
    }
}
