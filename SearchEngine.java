import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;

class StringHandler implements URLHandler {
    // The one bit of state on the server: a number that will be manipulated by
    // various requests.
    ArrayList<String> l = new ArrayList<String>();
    String s = "";
    
    public String handleRequest(URI url) {
        if (url.getPath().equals("/")) {
            return String.format("Text: %s", s);
        } else if (url.getPath().equals("/addE")) {
            s += "e";
            return String.format("String added :)");
        } else {
            System.out.println("Path: " + url.getPath());
            if (url.getPath().contains("/add")) {
                String[] parameters = url.getQuery().split("=");
                if (parameters[0].equals("s")) {
                    l.add(parameters[1]);
                }
                return String.format("Added");
            }
            else if(url.getPath().contains("/search")) {
                String[] parameters = url.getQuery().split("=");
                if(parameters[0].equals("s")) {
                    String str = parameters[1];
                    String strs = "";
                    for(int i = 0; i < l.size(); i++) {
                        if(l.get(i).contains(str)) {
                            strs = strs + l.get(i) + " "; 
                        }
                    }
                    return String.format(strs);
                }
            }
            return "404 Not Found!";
        }
    }

    public void printArrayList(ArrayList<String> lst) {
        for(int i = 0; i < lst.size(); i++) {
            System.out.print(lst.get(i));
        }
    }
}

class  SearchEngine {
    public static void main(String[] args) throws IOException {
        if(args.length == 0){
            System.out.println("Missing port number! Try any number between 1024 to 49151");
            return;
        }

        int port = Integer.parseInt(args[0]);

        Server.start(port, new StringHandler());
    }
}