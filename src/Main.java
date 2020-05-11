/**
 * Program to read an HTML file as a string and check to
 * see if tags that are supposed to be paired are balanced.
 *
 * Initial developer:  Stan Thomas
 * Illustrates use of:  String, StringBuilder, RegEx, TreeSet, Stack
 *
 * Modified and submitted by: Steven Parrill
 *
 * Honor statement:
 * The code submitted for this project was develped by
 * Steven Parrill without outside assistance or consultation
 * except as allowed by the instructions for this project.
 */

// Imports
import java.io.BufferedReader;
import java.io.IOException;
import java.util.regex.*;
import java.util.Stack;
import java.util.*;
import java.io.FileReader;

public class Main{

    /**
     * Method to read the SelfClosingTags file and return a Set of
     * Strings corresponding to the HTML SelfClosingTags
     * @return
     */
    private static Set<String> readTags2Skip() throws IOException {
        // create a Set of tags to be ignored when scanning HTML file
        // the file is named SelfClosingTags
        Set<String> taglist = new TreeSet<String>();

        // TODO
        BufferedReader input = new BufferedReader(new FileReader("src/SelfClosingTags"));
        String selfClosingTag;
        while((selfClosingTag = input.readLine()) != null){
            taglist.add(selfClosingTag);
        }

        return taglist;
    }

    public static void main(String args[]) throws IOException {

        // read names of SelfClosingTags from file and create a Set
        Set<String> selfClosingTags = readTags2Skip();

        // TODO
        // ask user for a URL, including http:// or https://
        // read the URL and use a method in the ReadWebPage class
        // to read the web page into a String
        // Bad test site: https://sjt.sites.wfu.edu/bad.html
        // Bad test site: https://www.espn.com
        // Good test site: https://sjt.sites.wfu.edu/good.html
        Scanner in = new Scanner(System.in);
        System.out.println("Enter the URL of a website including http:// or https:// ");
        String url = in.next();
        String webpage = ReadWebPage.readWebPage(url);
        //System.out.println(webpage);

        // TODO
        // Remove HTML comments from the String containing the HTML
        // The Regex pattern "(?=<!--)([\\s\\S]*?)-->" will match HTML comments
        webpage = webpage.replaceAll("(?=<!--)([\\\\s\\\\S]*?)-->", "");

        // TODO
        // compile a Pattern to match HTML tags
        // the RegEx to match HTML tags is "<(/?)([a-zA-Z]+)"
        // Details will be discussed in class
        //
        // In order to use this Pattern repeatedly you need to create
        // a Matcher object.  Examples given in class.
        // using a regular expression, set up a matcher for finding
        // a "<tag" or "</tag" in the web page. This Matcher object
        // must be used inside a loop to find the tags one by one
        Pattern pattern = Pattern.compile("<(/?)([a-zA-Z]+)");
        Matcher matcher = pattern.matcher(webpage);

        // TODO
        // create an empty Stack
        Stack<String> s = new Stack<>();

        // TODO
        // Repeatedly use the find() method for Matchers to locate
        // tags. Note that using the pattern identified above, the
        // tag will be identified as the matcher's group(2)
        // TODO
        // use the Stack .push() and .pop() methods to determine whether
        // or not the tags in the file are paired correctly. Any tag in
        // the selfClosingTags Set should be ignored.
        while (matcher.find()) {
            // System.out.println(matcher.group(2));
            // Continues through while loop if a self closing tag was found
            if(selfClosingTags.contains(matcher.group(2))){
                continue;
            }
            // Pushes first tag onto the stack
            if(s.isEmpty()) {
                s.push(matcher.group(2));
                continue;
            }
            if(matcher.group(1).equals("/")){
                // Pops if a closing tag is found
                if (matcher.group(2).equals(s.peek())) {
                    s.pop();
                }
                // If the closing tag does not match the opening tag break out of the loop
                else {
                    break;
                }
            }
            // Else pushes tag onto stack
            else {
                s.push(matcher.group(2));
            }
        }

        // TODO
        // If the Stack is empty the tags were balanced, else they were not.
        // Report the results with messages such as the following.  Your
        // variable names may be different.

        System.out.println("Analysis of " + url);
        System.out.println("The HTML tags are" +
                            (s.empty() ? "" : " not") + " balanced.");
    }
}