package challenge.normalizer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static java.util.Objects.isNull;

public class Normaliser {

    //    You can add other Job titles to the verification array
    private final String[] jobTitleArray
            = {"Architect", "Software engineer", "Quantity surveyor", "Accountant"};
    List<String> jtList = new ArrayList<>(Arrays.asList(jobTitleArray));

    public String normalise(String txt) {
        if (!isNull(txt) && !txt.trim().isEmpty()) {
            String title;
            title = rewriteOf(txt);
            title = singularize(title);
            title = removeSpaces(title);
            title = getTheClosestMatch(jtList, title);
            return title;
        } else {
            throw new IllegalArgumentException("Strings must not be null");
        }
    }

    //  Rewrite "of" terms and reorder the title position
    private String rewriteOf(String term) {
        ArrayList<String> list = new ArrayList<>(List.of(term.split("of")));
        Collections.reverse(list);
        return String.join(" ", list).trim();
    }

    //    Here we transform plural words in sigular
    private String singularize(String name) {
        List<String> titleStrs = new ArrayList<>(List.of(name.split(" ")));
        List<String> resultStrList = new ArrayList<>();
        titleStrs.forEach(str -> {
            String result = str;
            if (seemsPluralised(str)) {
                String lower = str.toLowerCase();
                if (lower.endsWith("ies")) {
                    result = str.substring(0, str.length() - 3) + "y";
                } else if (lower.endsWith("ches") || lower.endsWith("ses")) {
                    result = str.substring(0, str.length() - 2);
                } else if (lower.endsWith("s")) {
                    result = str.substring(0, str.length() - 1);
                }
            }
            resultStrList.add(result);
        });
        return String.join(" ", resultStrList).trim();
    }

    private boolean seemsPluralised(String name) {
        name = name.toLowerCase();
        boolean pluralised = false;
        pluralised |= name.endsWith("es");
        pluralised |= name.endsWith("s");
        pluralised &= !(name.endsWith("ss") || name.endsWith("us"));
        return pluralised;
    }

    private static String removeSpaces(String txt) {
        return txt.replaceAll("\\s+", " ");
    }


    //    This function check the closest string of targent job title, getting the Levenshtein Distance between the
//    list of normalizeds titles.
    private static String getTheClosestMatch(List<String> titles, String target) {
        int distance = Integer.MAX_VALUE;
        String closest = target;
        for (String compareTitle : titles) {
            int currentDistance = getLevenshteinDistance(compareTitle.toLowerCase().toCharArray(), target.toCharArray());
            if (currentDistance < distance) {
                distance = currentDistance;
                closest = compareTitle;
            }
        }
        return closest;
    }

    //    This function calculate the distance between the edition of two string, returning the value of necessaries interactions
    //    to do the change. In general terms, what it do is check if each charactere of the string is diferent of the other.
    private static int getLevenshteinDistance(char[] s1, char[] s2) {

        int[] previous = new int[s2.length + 1];

        for (int j = 0; j < s2.length + 1; j++) {
            previous[j] = j;
        }

        for (int i = 1; i < s1.length + 1; i++) {

            int[] current = new int[s2.length + 1];
            current[0] = i;

            for (int j = 1; j < s2.length + 1; j++) {
                int d1 = previous[j] + 1;
                int d2 = current[j - 1] + 1;
                int d3 = previous[j - 1];
                if (s1[i - 1] != s2[j - 1]) {
                    d3 += 1;
                }
                current[j] = Math.min(Math.min(d1, d2), d3);
            }
            previous = current;
        }
        return previous[s2.length];

    }
}
