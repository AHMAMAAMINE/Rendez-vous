package sample.java;

import java.util.Locale;


public class random {

        /**
         * Generate a random string.
         */


        public static final String upper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

        public static final String lower = upper.toLowerCase(Locale.ROOT);

        public static final String digits = "0123456789";

        public  String alphanum ;


        public String rndom() {
            char lettreAleatoire = lower.charAt((char) (Math.random() * ((lower.length()) )));
            char lettreAleatoire2 = upper.charAt((char) (Math.random() * ((upper.length()) )));
            char lettreAleatoire3 = lower.charAt((char) (Math.random() * ((lower.length()) )));
            char lettreAleatoire4 = upper.charAt((char) (Math.random() * ((upper.length()))));
            alphanum=lettreAleatoire+""+lettreAleatoire2+lettreAleatoire3+lettreAleatoire4;
        return alphanum;
        }

}
