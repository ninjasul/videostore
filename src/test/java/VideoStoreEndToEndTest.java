import org.hamcrest.Matcher;
import org.junit.Ignore;
import org.junit.Test;

import static java.util.Arrays.stream;
import static java.util.stream.Collectors.joining;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class VideoStoreEndToEndTest {

    @Test
    public void basic_tariff() throws Exception {
        Customer basic = new Customer("basic", new PricePlan(new StandardTariff(), new LoyaltyPlan()));
        basic.addRental( new Rental(getMovie("childrens", Movie.CHILDRENS), 3));
        basic.addRental( new Rental(getMovie("regular", Movie.REGULAR), 3));
        basic.addRental( new Rental(getMovie("new", Movie.NEW_RELEASE), 3));

        assertThat(basic.generateStatement(new StatementRenderer()),
                hasStatementLines(
                    "Rental Record for basic",
                    "\tchildrens\t1.5",
                    "\tregular\t3.5",
                    "\tnew\t9.0",
                    "You owed 14.0",
                    "You earned 4 frequent renter points"
                )
        );
    }

    @Test
    public void vip_tariff() throws Exception {
        Customer vip = new Customer("vip", new PricePlan(new VipTariff(), new LoyaltyPlan()));
        vip.addRental( new Rental(getMovie("childrens", Movie.CHILDRENS), 3));
        vip.addRental( new Rental(getMovie("regular", Movie.REGULAR), 3));
        vip.addRental( new Rental(getMovie("new", Movie.NEW_RELEASE), 3));

        assertThat(vip.generateStatement(new StatementRenderer()),
                hasStatementLines(
                    "Rental Record for vip",
                    "\tchildrens\t1.0",
                    "\tregular\t3.0",
                    "\tnew\t6.0",
                    "You owed 10.0",
                    "You earned 4 frequent renter points"
                )
        );
    }

    @Test
    public void basic_tariff_with_discount() throws Exception {
        Customer basic = new Customer("basic", new PricePlan(new StandardTariff(), new LoyaltyPlan()));
        basic.addRental( new Rental(getMovie("childrens", Movie.CHILDRENS), 3));
        basic.addRental( new Rental(getMovie("regular", Movie.REGULAR), 3));
        basic.addRental( new Rental(getMovie("new1", Movie.NEW_RELEASE), 3));
        basic.addRental( new Rental(getMovie("new2", Movie.NEW_RELEASE), 3));
        basic.addRental( new Rental(getMovie("new3", Movie.NEW_RELEASE), 3));

        assertThat(basic.generateStatement(new StatementRenderer()),
                hasStatementLines(
                        "Rental Record for basic",
                        "\tchildrens\t1.5",
                        "\tregular\t3.5",
                        "\tnew1\t9.0",
                        "\tnew2\t9.0",
                        "\tnew3\t9.0",
                        "You owed 32.0",
                        "You got a discount of 1.6, so now you owe 30.4",
                        "You earned 8 frequent renter points"
                )
        );
    }

    private Matcher<String> hasStatementLines(String... lines) {
        return equalTo(stream(lines).collect(joining("\n", "", "\n")));
    }
    private Movie getMovie(String title, int code) {
        return new Movie(title, code);
    }
}