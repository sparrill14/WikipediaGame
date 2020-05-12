// Imports
import java.io.BufferedReader;
import java.io.IOException;
import java.util.regex.*;
import java.util.Queue;
import java.util.*;
import java.io.FileReader;

public class Main{

    public static void main(String args[]) throws IOException {
        //Graph G = new Graph();

        System.out.println("Constructing from https://en.wikipedia.org/wiki/Wikipedia ... ");
        String urlName;
        String url;
        String webpage = "Wikipedia";
        //System.out.println(webpage);


        /**
         * Pattern Requirements:
         * has to start with <a href="/wiki/
         * must be /wiki/article_name
         * cannot be image
         * ignores sidebar, bottom of page, and top of page links
         *
         *
         */
        Pattern pattern = Pattern.compile("<a href=\"/wiki/([^\"]+)");
        Matcher matcher;
        Queue<String> websitesToVisit = new LinkedList<>();
        HashMap<Integer, String> articleSet = new HashMap<>();
        int edgeCount = 0;
        //Inserts first page into queue. Does the first page matter?
        websitesToVisit.add(webpage);
        articleSet.put(webpage.hashCode(), webpage);

        while(!websitesToVisit.isEmpty()) {
            urlName = websitesToVisit.poll();
            url = "https://en.wikipedia.org/wiki/" + urlName;
            webpage = ReadWebPage.readWebPage(url);
            //webpage = webpage.replaceAll("(?=<!--)([\\\\s\\\\S]*?)-->", "");
            matcher = pattern.matcher(webpage);
            //System.out.println(webpage);
            while (matcher.find()) {
                //System.out.println(matcher.group(1));
                if(!matcher.group(1).contains(":") && !articleSet.containsKey(matcher.group(1).hashCode())){
                    edgeCount++;
                    articleSet.put(matcher.group(1).hashCode(), matcher.group(1));
                    websitesToVisit.add(matcher.group(1));
                    //System.out.println("Connection Between: " + urlName.hashCode() + " and " + matcher.group(1).hashCode());
                    System.out.println("Connection Between: " + urlName + " and " + matcher.group(1));
                    //System.out.println(edgeCount);
                }
            }
        }
        int articleCount = articleSet.size();
    }
}