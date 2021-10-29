package com.kubilaycakmak;

import com.google.gson.Gson;
import com.kubilaycakmak.models.Recipe;
import com.kubilaycakmak.models.Type;
import com.kubilaycakmak.models.User;
import com.mongodb.BasicDBObject;
import com.mongodb.Block;
import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import org.bson.Document;

import java.util.*;

public class Management {
    char option;
    Scanner scanner = new Scanner(System.in);
    Gson gson = new Gson();
    MongoClient mongoClient = MongoClients.create("mongodb+srv://kubilaycakmak:qzDbBV5W9o6eAjrN@aurora.asapg.mongodb.net/myFirstDatabase?retryWrites=true&w=majority");
    MongoDatabase mongoDatabase = mongoClient.getDatabase("RecipeManagement");
    MongoCollection mongoUserCollection = mongoDatabase.getCollection("user");
    MongoCollection mongoTypeCollection = mongoDatabase.getCollection("types");
    void errorMessage(String message) {
        System.out.println("\n");
        System.out.println("---------------------------");
        System.out.println(message);
        System.out.println("---------------------------");
        System.out.println("\n");
    }

    void login(String username){
        FindIterable<Document> user = null;
        user = mongoUserCollection.find(new Document("username", username));

        if(user != null){
            String decodedUser = user.first().get("name").toString().toUpperCase() + " " + user.first().get("lastname").toString().toUpperCase();
            String user_id = user.first().get("id").toString();

            recipeMenu(username, decodedUser, user_id);
        }
    }

    void checkOwnRecipes(String id){
        FindIterable<Document> iterableRecipes = null;
        iterableRecipes = mongoUserCollection.find(Filters.eq("id", id));
        ArrayList recipes = new ArrayList();
        Gson gson = new Gson();

        iterableRecipes.forEach(new Block<Document>() {
            @Override
            public void apply(Document document) {
                recipes.add(gson.toJson(document.get("recipes")));
            }
        });

        for (int i=0;i<recipes.size(); i++){
            if(recipes.get(i) != ""){
                System.out.println("############################");
                System.out.println(recipes.get(i));
            }
        }
    }

    void checkAllRecipes(){
        Document bson = mongoDatabase.getCollection("user").find().first();
        ArrayList<Document> arrayList = new ArrayList();
        arrayList = (ArrayList) bson.get("recipes");
        System.out.println("#############################");
        System.out.println(arrayList);
    }

//    void addTypes(){
//        Type type1 = new Type("1", "Breakfast");
//        Document document = Document.parse(gson.toJson(type1));
//        Type type2 = new Type("2", "Brunch");
//        Document document2 = Document.parse(gson.toJson(type1));
//        Type type3 = new Type("3", "Lunch");
//        Document document3 = Document.parse(gson.toJson(type1));
//        Type type4 = new Type("4", "Dinner");
//        Document document4 = Document.parse(gson.toJson(type1));
//        Type type5 = new Type("5", "Desserts");
//        Document document5 = Document.parse(gson.toJson(type1));
//
//        mongoTypeCollection.insertOne(document);
//        mongoTypeCollection.insertOne(document2);
//        mongoTypeCollection.insertOne(document3);
//        mongoTypeCollection.insertOne(document4);
//        mongoTypeCollection.insertOne(document5);
//    }

    void addRecipe(String recipeId,
                   String userId,
                   String name,
                   String shortDescription,
                   String longDescription,
                   String img,
                   double calorie,
                   double cookTime,
                   Type type,
                   String date){
        Recipe recipe = new Recipe(recipeId, userId, name, shortDescription, longDescription, img, calorie, cookTime, type, date);
        Document document = Document.parse(gson.toJson(recipe));
        mongoUserCollection.updateOne(Filters.eq("id", userId), Updates.push("recipes", document));
    }

    void register(User user){
        Document document = Document.parse(gson.toJson(user));
        mongoUserCollection.insertOne(document);
    }

    void wipeRecipes(String id){
        ArrayList arrayList = new ArrayList();
        FindIterable<Document> user = null;
        user = mongoUserCollection.find(new Document("id", id));

        BasicDBObject query = new BasicDBObject();
        query.put("recipes", user.first().get("recipes")); // (1)

        BasicDBObject newDocument = new BasicDBObject();
        newDocument.put("recipes", arrayList); // (2)

        BasicDBObject updateObject = new BasicDBObject();
        updateObject.put("$set", newDocument); // (3)

        System.out.println(query);
        mongoUserCollection.updateOne(query, updateObject);
    }

    void deleteAccount(String id){
        BasicDBObject query = new BasicDBObject();
        query.put("id", id);

        mongoUserCollection.deleteOne(query);
    }

    void editUser(String username, String nickname){
        BasicDBObject query = new BasicDBObject();
        query.put("username", username);

        BasicDBObject newDocument = new BasicDBObject();
        newDocument.put("username", nickname);

        BasicDBObject updateObject = new BasicDBObject();
        updateObject.put("$set", newDocument);

        try{
            mongoUserCollection.updateOne(query, updateObject);

            System.out.println("############################");
            System.out.println("ACCOUNT DELETED!");
            System.out.println("############################");
            System.out.println("BYE BYE!");
        }catch (Exception e){
            System.out.println(e);
        }


    }

    void start(){
        String username, name, lastname, id;
        Long date = new Date().getTime();

        loop: do {
            System.out.println("Welcome to Recipe Management");
            System.out.println("A : Login");
            System.out.println("B : Create an account");
            System.out.println("E : Quit");
            option = scanner.next().charAt(0);
            System.out.println("\n");

            switch (Character.toLowerCase(option)) {
                case 'a':
                    System.out.println("=--------------------------=");
                    System.out.println("Please enter your username: ");
                    System.out.println("=--------------------------=");
                    username = scanner.next();
                    try {
                        login(username);
                        break loop;
                    } catch (Exception e) {
                        errorMessage("user not found, please try to enter correct username");
                    }
                    break;

                case 'b':
                    System.out.println("=--------------------------=");
                    System.out.println("Please enter your username: ");
                    System.out.println("=--------------------------=");
                    username = scanner.next();
                    System.out.println("Please enter your name: ");
                    System.out.println("=--------------------------=");
                    name = scanner.next();
                    System.out.println("Please enter your lastname: ");
                    System.out.println("=--------------------------=");
                    lastname = scanner.next();
                    try {
                        if(!username.isEmpty() && !name.isEmpty() && !lastname.isEmpty()){
                            String uniqueID = UUID.randomUUID().toString();
                            ArrayList<Recipe> recipes = new ArrayList<>();
                            User user = new User(uniqueID, username, name, lastname, new Date(date).toString(), recipes);
                            register(user);
                        }

                    } catch (Exception e) {
                        errorMessage("unable to register a user");
                        System.out.println(e);
                    }
                    break;

                case 'q':
                    System.out.println("================================");
                    break;
                default:
                    System.out.println("Invalid Option. Please try again.");
                    break;
            }


        }while(Character.toLowerCase(option) != 'q');
        System.out.println("Good bye for now!");
        scanner.close();
    }
    void recipeMenu(String username, String decodedUser, String id){
        Long date = new Date().getTime();
        loop: do {
            System.out.println("############################");
            System.out.println("Welcome to Recipe Management");
            System.out.println("Account name: " + decodedUser);
            System.out.println("############################");
            System.out.println("A : Add new Recipe");
            System.out.println("B : Check all recipes");
            System.out.println("C : Check own recipes");
            System.out.println("D : Delete all recipes");
            System.out.println("E : Edit User information");
            System.out.println("R : Delete account (PERMANENTLY)");

            System.out.println("Q : Quit");
            option = scanner.next().charAt(0);
            System.out.println("\n");

            switch (Character.toLowerCase(option)) {
                case 'a':
                    System.out.println("############################");
                    System.out.println("Add Recipe Form");
                    System.out.println("############################");
                    System.out.println("Recipe Name: ");
                    String name = scanner.next();
                    System.out.println("Recipe short description: ");
                    String shortDescription = scanner.next();
                    System.out.println("Recipe long description: ");
                    String longDescription = scanner.next();
                    System.out.println("Recipe image url: ");
                    String img = scanner.next();
                    System.out.println("Recipe calorie: ");
                    double calorie = scanner.nextInt();
                    System.out.println("Recipe cooking time: ");
                    double cookTime = scanner.nextInt();
                    System.out.println("Select a Recipe type: ");
                    System.out.println("############################");
                    System.out.println("1 - Breakfast");
                    System.out.println("2 - Brunch");
                    System.out.println("3 - Lunch");
                    System.out.println("4 - Dinner");
                    System.out.println("5 - Desserts");
                    System.out.println("############################");
                    String type = scanner.next();
                    try {
                        FindIterable<Document> recipeType = null;
                        recipeType = mongoTypeCollection.find(new Document("id", type));
                        String uniqueID = UUID.randomUUID().toString();
                        addRecipe(uniqueID, id, name, shortDescription, longDescription, img, calorie, cookTime, new Type(recipeType.first().get("id").toString(), recipeType.first().get("name").toString()), new Date(date).toString());
                    } catch (Exception e) {
                        System.out.println(e);
                    }

                case 'b':
                    try {
                        checkAllRecipes();
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                    break;
                case 'c':
                    try {
                        checkOwnRecipes(id);
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                    break;
                case 'd':
                    System.out.println("############################");
                    System.out.println("DELETING ALL RECIPE, ARE YOU SURE? (Y:YES, N:NO)");
                    String key = scanner.next();
                    try {
                        if(key.equals("Y") || key.equals("y")) {
                            wipeRecipes(id);
                        }
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                    break;
                case 'e':
                    FindIterable<Document> user = null;
                    user = mongoUserCollection.find(new Document("id", id));
                    System.out.println("############################");
                    System.out.println(" User username: " + user.first().get("username"));
                    String nickname = scanner.next();
                    System.out.println("############################");
                    try {
                        if (!nickname.isEmpty()) {
                            editUser(username, nickname);
                        } else {
                            System.out.println("Please enter a value for every query!");
                        }

                    } catch (Exception e) {
                        System.out.println(e);
                    }
                    break;
                case 'f':
                    System.out.println("############################");
                    System.out.println("DELETING ACCOUNT, ARE YOU SURE? (Y:YES, N:NO)");
                    String key1 = scanner.next();
                    try {
                        if(key1.equals("Y") || key1.equals("y")) {
                            deleteAccount(id);
                            break loop;
                        }
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                    break;
                    default:
                        System.out.println("Invalid Option. Please try again.");
                        break;

            }
        }while(Character.toLowerCase(option) != 'q');
        scanner.close();
    }

}
