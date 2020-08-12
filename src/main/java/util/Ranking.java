package util;

/**
 * Ranking interface implemented by User class to enable priority ordering.
 *
 * Staff and Student class implements getRanking() method.
 *
 * getRanking() method returns an integer indicating the rank of the object
 */
public interface Ranking {
    int getRanking();
}
