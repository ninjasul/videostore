import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class StatementRenderer {

    public String render(Statement statement) {

        StringBuilder result = new StringBuilder("Rental Record for " + statement.name() + "\n");

        statement.eachRental(pricedRental ->
            result.append("\t").append(pricedRental.title).append("\t").append(String.valueOf(pricedRental.price)).append("\n")
        );

        result.append("You owed ").append(String.valueOf(statement.total())).append("\n");

        statement.discount().ifPresent(discount -> result.append("You got a discount of " + String.valueOf(discount.discountAmount) +
            ", so now you owe " + String.valueOf(discount.discountedTotal) + "\n"
        ));

        result.append("You earned ").append(String.valueOf(statement.point())).append(" frequent renter points\n");

        return result.toString();
    }

    public static class Statement {

        private final String name;
        private final List<PricedRental> pricedRentalList;
        private final double discountPercentage;

        public Statement(String name, List<PricedRental> pricedRentalList, double discountPercentage) {
            this.name = name;
            this.pricedRentalList = pricedRentalList;
            this.discountPercentage = discountPercentage;
        }

        public Optional<Discount> discount() {
            if( discountPercentage == 0.0 ) {
                return Optional.empty();
            }
            else {
                final double total = total();
                double discountAmount = total * discountPercentage;
                return Optional.of(new Discount(discountAmount, total - discountAmount));
            }
        }

        public void eachRental(Consumer<PricedRental> consumer) {
            pricedRentalList.forEach(consumer);
        }

        public double total() {
            return pricedRentalList.stream().collect(Collectors.summingDouble(pr -> pr.price));
        }

        public int point() {
            return pricedRentalList.stream().collect(Collectors.summingInt(pr -> pr.points));
        }

        public String name() {
            return name;
        }

        private class Discount {
            public final double discountAmount;
            public final double discountedTotal;

            public Discount(double discountAmount, double discountedTotal) {
                this.discountAmount = discountAmount;
                this.discountedTotal = discountedTotal;
            }
        }

    }
}