import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class Parser {
    static List<Game> games = new ArrayList<>();

    public List<Game> sortByName() {
        List<Game> sortedByName = new ArrayList<>(games);
        sortedByName.sort(Comparator.comparing(Game::getName));
        return sortedByName;
    }

    public List<Game> sortByRating() {
        List<Game> sortedByRating = new ArrayList<>(games);
        sortedByRating.sort(Comparator.comparingDouble(Game::getRating).reversed());
        return sortedByRating;
    }

    public List<Game> sortByPrice() {
        List<Game> sortedByPrice = new ArrayList<>(games);
        sortedByPrice.sort(Comparator.comparingInt(Game::getPrice).reversed());
        return sortedByPrice;
    }

    public void setUp() throws IOException {
        File input = new File("src/Resources/games.html");
        Document doc = Jsoup.parse(input, "UTF-8");

        Elements gameElements = doc.select("div.col-md-4.game");

        for (Element gameElement : gameElements) {
            String name = gameElement.select("h3.game-name").text();
            String ratingText = gameElement.select("span.game-rating").text().replace("/5", "");
            String priceText = gameElement.select("span.game-price").text().replace("€", "").replace("€", "").trim();

            try {
                double rating = Double.parseDouble(ratingText);
                int price = Integer.parseInt(priceText);

                Game game = new Game(name, rating, price);
                games.add(game);
            } catch (NumberFormatException e) {
                System.out.println("Error reading data for game: " + name);
            }
        }
    }

    public static void main(String[] args) throws IOException {
        Parser parser = new Parser();
        parser.setUp();

        System.out.println(" Sorted by Name:");
        parser.sortByName().forEach(System.out::println);

        System.out.println("\n Sorted by Rating:");
        parser.sortByRating().forEach(System.out::println);

        System.out.println("\n Sorted by Price:");
        parser.sortByPrice().forEach(System.out::println);
    }
}
