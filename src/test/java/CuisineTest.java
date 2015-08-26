import org.junit.*;
import static org.junit.Assert.*;
import java.util.Arrays;

public class CuisineTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();


  @Test
  public void all_emptyAtFirst() {
    assertEquals(Cuisine.all().size(), 0);
  }

  @Test
  public void equals_returnsTrueIfNamesAretheSame() {
    Cuisine firstCuisine = new Cuisine("pizza");
    Cuisine secondCuisine = new Cuisine("pizza");
    assertTrue(firstCuisine.equals(secondCuisine));
  }

  @Test
  public void save_savesIntoDatabase_true() {
    Cuisine myCuisine = new Cuisine("pizza");
    myCuisine.save();
    assertTrue(Cuisine.all().get(0).equals(myCuisine));
  }

  @Test
  public void find_findCuisineInDatabase_true() {
    Cuisine myCuisine = new Cuisine("pizza");
    myCuisine.save();
    Cuisine savedCuisine = Cuisine.find(myCuisine.getId());
    assertTrue(myCuisine.equals(savedCuisine));
  }

  @Test
  public void getRestaurants_retrievesALlRestaurantsFromDatabase_restaurantsList() {
    Cuisine myCuisine = new Cuisine("pizza");
    myCuisine.save();
    Restaurant firstRestaurant = new Restaurant("Deli town", myCuisine.getId());
    firstRestaurant.save();
    Restaurant secondRestaurant = new Restaurant("Pizza Hut", myCuisine.getId());
    secondRestaurant.save();
    Restaurant[] restaurants = new Restaurant[] { firstRestaurant, secondRestaurant };
    assertTrue(myCuisine.getRestaurants().containsAll(Arrays.asList(restaurants)));
  }



}
