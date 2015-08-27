import java.util.Arrays;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import static spark.Spark.*;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;

public class App {
  public static void main(String[] args){
    //staticFileLocation("/public");
    String layout = "templates/layout.vtl";

    get("/", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      model.put("cuisines", Cuisine.all());
      model.put("template", "templates/index.vtl");
      model.put("restaurants", request.session().attribute("restaurants"));
      return new ModelAndView(model, layout);
   }, new VelocityTemplateEngine());

   get("/cuisines/new", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      model.put("template", "templates/index.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/cuisines", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      String name = request.queryParams("name");
      Cuisine newCuisine = new Cuisine(name);
      newCuisine.save();
      model.put("cuisines", Cuisine.all());
      model.put("template", "templates/cuisines.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/cuisines", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      model.put("cuisines", Cuisine.all());
      model.put("template", "templates/cuisines.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/cuisines/:id", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      // Id parameter is key from the database when we use the save method
      Cuisine cuisine = Cuisine.find(Integer.parseInt(request.params(":id")));
      model.put("cuisine", cuisine);
      model.put("template", "templates/cuisine.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/cuisine/:id/restaurants/new", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      Cuisine cuisine = Cuisine.find(Integer.parseInt(request.params(":id")));
      model.put("cuisine", cuisine);
      model.put("template", "templates/cuisine.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/restaurants", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      Cuisine cuisine = Cuisine.find(Integer.parseInt(request.queryParams("cuisineId")));
      String restName = request.queryParams("restName");
      //System.out.println(restName);
      Restaurant newRestaurant = new Restaurant(restName, cuisine.getId());
      newRestaurant.save();
      model.put("cuisine", cuisine);
      model.put("template", "templates/cuisine.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("cuisines/:id/restaurants", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      Cuisine cuisine = Cuisine.find(Integer.parseInt(request.params(":id")));
      String restName = request.queryParams("restName");
      //System.out.println(restName);
      Restaurant newRestaurant = new Restaurant(restName, cuisine.getId());
      newRestaurant.save();
      model.put("cuisine", cuisine);
      model.put("template", "templates/cuisine.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/restaurants/:id", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      Restaurant restaurant = Restaurant.find(Integer.parseInt(request.params("id")));
      Cuisine cuisine = Cuisine.find(restaurant.getCuisineId());
      String restName = request.queryParams("restName");
      restaurant.update("restName");
      model.put("template", "templates/restaurant.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/restaurants/:id/delete", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      Restaurant restaurant = Restaurant.find(Integer.parseInt(request.params("id")));
      restaurant.delete();
      model.put("template", "templates/cuisines.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/cuisines/:id/delete", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      Cuisine cuisine = Cuisine.find(Integer.parseInt(request.params("id")));
      cuisine.delete();
      //after we delete we have to put our list back in
      model.put("cuisines", Cuisine.all());
      //choosing which page you want to end up at
      model.put("template", "templates/index.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

  }
}
