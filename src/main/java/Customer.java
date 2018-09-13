import java.util.ArrayList;
import java.util.List;

public class Customer {
    private final String name;
    private final List<Rental> rentals = new ArrayList<>();
    private final PricePlan plan;

    public Customer(String name, PricePlan plan) {
        this.name = name;
        this.plan = plan;
    }

    public void addRental(Rental rental) {
        rentals.add(rental);
    }

    public String generateStatement(StatementRenderer renderer) {
        return renderer.render(plan.statement(name, rentals));
    }
}