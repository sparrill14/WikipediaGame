// Imports
import java.io.*;
import java.util.regex.*;
import java.util.Queue;
import java.util.*;

public class Main{

    public static void main(String args[]) throws IOException {
        //Graph G = new Graph();

        System.out.println("Constructing from https://en.wikipedia.org/wiki/Wikipedia ... ");
        String urlName;
        String url;
        String webpage = "Wikipedia";
        //System.out.println(webpage);

        //File writers
        FileWriter fileWriterArticleList = new FileWriter("src/ArticleList.txt");
        FileWriter fileWriterEdges = new FileWriter("src/Edges.txt");
        PrintWriter printWriterArticleList = new PrintWriter(fileWriterArticleList);
        PrintWriter printWriterEdges = new PrintWriter(fileWriterEdges);



        /**
         * Pattern Description:
         * has to be of format <a href="/wiki/ARTICLE_NAME"
         * group 1 matches the name of any article with a quotation mark marking the end of the article name
         * pattern accepts all characters except "
         */

        //Regex
        Pattern pattern = Pattern.compile("<a href=\"/wiki/([^\"]+)");
        Matcher matcher;

        //Queue performs BFS on articles
        Queue<String> websitesToVisit = new LinkedList<>();
        //Hashmap stores all visited articles as <Article Name Hash Code, Article Name> Pairs
        HashMap<Integer, String> articleSet = new HashMap<>();

        /*
        articleSet.put(1, "2");
        articleSet.put(3, "4");
        articleSet.put(5, "6");
        articleSet.put(7, "8");
        articleSet.put(9, "10");

        articleSet.forEach((k,v) -> printWriterEdges.format("%d %s%n", k, v));
         */

        int edgeCount = 0;
        int articleCount = 0;
        //Inserts first page into queue. Does the first page matter?
        websitesToVisit.add(webpage);
        articleSet.put(webpage.hashCode(), webpage);

        while(!websitesToVisit.isEmpty()) {
            urlName = websitesToVisit.poll(); //Pops next element of queue
            url = "https://en.wikipedia.org/wiki/" + urlName; //Append protocol
            webpage = ReadWebPage.readWebPage(url); //Returns HTML as String
            matcher = pattern.matcher(webpage);
            //System.out.println(webpage);
            while (matcher.find()) {
                //System.out.println(matcher.group(1));
                /**
                 * Article names containing colons are thrown out because
                 * they link to articles containing images, files, and other
                 * undesirable content
                 */
                if(!matcher.group(1).contains(":")){
                    edgeCount++;
                    if(!articleSet.containsKey(matcher.group(1).hashCode())) { // If not already visited add to Queue and HashMap
                        articleSet.put(matcher.group(1).hashCode(), matcher.group(1));
                        websitesToVisit.add(matcher.group(1));
                    }
                    //System.out.println("Connection Between: " + urlName.hashCode() + " and " + matcher.group(1).hashCode());
                    //System.out.println("Connection Between: " + urlName + " and " + matcher.group(1));
                    //System.out.format("%d %d%n", urlName.hashCode(), matcher.group(1).hashCode());
                    printWriterArticleList.format("%d %d%n", urlName.hashCode(), matcher.group(1).hashCode());
                    //System.out.println(edgeCount);
                }
            }
            if(articleSet.size() % 100000 == 0)
                System.out.println("Hunnet Racks");
        }
        articleCount = articleSet.size();

        //Prints contents of hashmap
        articleSet.forEach((k,v) -> printWriterEdges.format("%d %s%n", k, v));

        System.out.println("Articles/Vertices: " + articleCount + " Edges: " + edgeCount);

        printWriterArticleList.close();
        printWriterEdges.close();
    }
}