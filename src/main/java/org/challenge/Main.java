package org.challenge;

import org.challenge.normalizer.Normaliser;

public class Main {
    public static void main(String[] args) {
        String jt = "C# engineer";
        Normaliser n = new Normaliser();
        String normalized = n.normalise(jt).trim();
        System.out.println(normalized);

    }
}


