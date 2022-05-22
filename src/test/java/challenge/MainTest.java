package challenge;

import challenge.normalizer.Normaliser;

public class MainTest {
    public static void main(String[] args) {
        String jt = "C# engineer";
        Normaliser n = new Normaliser();
        String normalized = n.normalise(jt).trim();
        System.out.println(normalized);

    }
}


