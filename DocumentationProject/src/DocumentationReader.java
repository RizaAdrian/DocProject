/**
 * 
 * PRIMA INCERCARE
 * 
BufferedReader reader = null;
        String line = null;

        File f = new File("Test.html");
        if(!f.exists()){
        f.createNewFile();
        }else{
        System.out.println("File already exists");
        }

        File file = new File("C:\\Users\\adrian.riza\\Desktop\\New folder (3)\\plp project\\imp.k");
        try {
        reader = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException e1) {
        e1.printStackTrace();
        }

        BufferedWriter bw = new BufferedWriter(new FileWriter(f));
        try {
        bw.write("<!DOCTYPE html>");
        bw.newLine();
        bw.write("<html lang=\"en\">");
        bw.newLine();
        bw.write("<title>plp project</title>");
        bw.newLine();
        bw.write("<meta charset=\"UTF-8\">");
        bw.newLine();
        bw.write("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">");
        bw.newLine();
        bw.write("<link rel=\"stylesheet\" href=\"https://www.w3schools.com/w3css/4/w3.css\">");
        bw.newLine();
        bw.write("<body>");
        bw.newLine();
        bw.write("<header class=\"w3-display-container w3-content w3-center\" style=\"max-width:1500px\">");
        bw.newLine();
        bw.write("<div class=\"w3-display-middle w3-padding-large w3-border w3-wide w3-text-light-grey w3-center\">");
        bw.newLine();
        bw.write(" <h5 class=\"\" style=\"white-space:nowrap\">Documentation generator</h5>");
        bw.newLine();
        bw.write("</div>");
        bw.newLine();
        bw.write("</header>");
        bw.newLine();
        while ((line = reader.readLine()) != null) {
        System.out.println(line);
        //bw.write(line);
        if(line.startsWith("/**")){
        line = reader.readLine();
        bw.write(line);
        if(line.endsWith("")){
        break;
        }
        }
        if(line.startsWith("//@")){
        line = reader.readLine();
        bw.write(line);
        }
        }
        bw.write("</body>");
        bw.newLine();
        bw.write("</html>");
        } catch (IOException e) {
        e.printStackTrace();
        } finally {
        try {
        reader.close();
        bw.close();
        } catch (IOException e) {
        e.printStackTrace();
        }
        }}
       */