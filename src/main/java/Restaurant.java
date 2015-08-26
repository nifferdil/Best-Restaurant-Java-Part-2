import java.util.List;
import org.sql2o.*;
import java.util.Arrays;

public class Restaurant {
  private int id;
  private int cuisineId;
  private String restName;

  public int getId() {
    return id;
  }

  public String getRestName() {
    return restName;
  }

  public int getCuisineId() {
    return cuisineId;
  }

  public Restaurant(String restName, int cuisineId) {
    this.restName = restName;
    this.cuisineId = cuisineId;
  }

  @Override
  public boolean equals(Object otherRestaurant){
    if (!(otherRestaurant instanceof Restaurant)) {
      return false;
    } else {
      Restaurant newRestaurant = (Restaurant) otherRestaurant;
      System.out.println(this.getCuisineId());
      System.out.println(newRestaurant.getCuisineId());
      return this.getRestName().equals(newRestaurant.getRestName()) &&
             this.getId() == newRestaurant.getId() &&
             this.getCuisineId() == newRestaurant.getCuisineId();
    }
  }

  public static List<Restaurant> all() {
    String sql = "SELECT id, restName, cuisineId FROM restaurants";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Restaurant.class);
    }
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO restaurants(restName, cuisineId) VALUES (:restName, :cuisineId)";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("restName", restName)
        .addParameter("cuisineId", cuisineId)
        .executeUpdate()
        .getKey();
    }
  }

  public static Restaurant find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM restaurants where id=:id";
      Restaurant restaurant = con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Restaurant.class);
      return restaurant;
    }
  }

  public void update(String restName) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE restaurants SET restName = :restName) WHERE id = :id";
      con.createQuery(sql)
        .addParameter("restName", restName)
        .addParameter("id", id)
        .executeUpdate();
    }
  }

  public void delete() {
    try(Connection con = DB.sql2o.open()) {
    String sql = "DELETE FROM restaurants WHERE id = :id;";
      con.createQuery(sql)
        .addParameter("id", id)
        .executeUpdate();
    }
  }
}
