package io.customers;

import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MongoDbManager  {

    private MongoClient mongoClient;
    private MongoCollection<Document> customers;

    public static final String CUSTOMER_ID  = "CUSTOMER_ID";
    public static final String FIRSTNAME  = "FIRSTNAME";
    public static final String LASTNAME  = "LASTNAME";
    public static final String PHONE  = "PHONE";
    public static final String EMAIL  = "EMAIL";

    public MongoDbManager() {
        dbConnect();
    }

    public void dbConnect()  {
        mongoClient = MongoClients.create("mongodb://localhost:27017/?readPreference=primary&appname=MongoDB%20Compass&directConnection=true&ssl=false");
        MongoDatabase customer_db = mongoClient.getDatabase("customers");
        customers = customer_db.getCollection("customer");

        Logger logger = Logger.getLogger("org.mongodb.driver");
        logger.setLevel(Level.SEVERE);
    }

    public Customer insertCustomer(Customer customer) {
        customer.setCustomerIDtId((int) (customers.countDocuments() + 1));
        Document document = getDocumentFromCustomer(customer);
        customers.insertOne(document);
        return customer;
    }

    public List<Customer> getAllCustomers() {
        List<Customer> customerList = new ArrayList<>();
        MongoCursor<Document> cursor = customers.find().iterator();

            while (cursor.hasNext()) {
                Document doc = cursor.next();
                Customer customer = getCustomerFromDocument(doc);
                customerList.add(customer);
            }

        return customerList;
    }

    public void deleteEmployee(int customerID) {
        customers.deleteOne(Filters.eq(CUSTOMER_ID, customerID));
    }

    public void updateCustomer(int customerID, String firstName, String lastName, String email, String phone) {

        Document updateFields = new Document();
        updateFields.append(FIRSTNAME, firstName);
        updateFields.append(LASTNAME, lastName);
        updateFields.append(EMAIL, email);
        updateFields.append(PHONE, phone);

        Document setQuery = new Document();
        setQuery.append("$set", updateFields);
        customers.updateMany(Filters.eq(CUSTOMER_ID, customerID), setQuery);

    }

    public Customer searchCustomerByID(int customerID) {
        Document doc = customers.find().filter(Filters.eq(CUSTOMER_ID, customerID)).first();
        Customer customer = getCustomerFromDocument(doc);
        return customer;
    }

    public Customer searchCustomerByName(String name) {
        Document doc = customers.find().filter(Filters.eq(FIRSTNAME, name)).first();
        Customer customer = getCustomerFromDocument(doc);
        return customer;
    }

    public int getTotalCustomers() {
        return (int) customers.countDocuments();
    }

    public void close() {
        mongoClient.close();
    }

    private Customer getCustomerFromDocument(Document doc) {
        Customer customer = new Customer();
        customer.setCustomerIDtId(doc.getInteger(CUSTOMER_ID));
        customer.setFirstName(doc.getString(FIRSTNAME));
        customer.setLastName(doc.getString(LASTNAME));
        customer.setPhone(doc.getString(PHONE));
        customer.setEmail(doc.getString(EMAIL));

        return customer;
    }

    private Document getDocumentFromCustomer(Customer customer) {
        Document document = new Document();

        document.put(CUSTOMER_ID, customer.getCustomerID());
        document.put(FIRSTNAME, customer.getFirstName());
        document.put(LASTNAME, customer.getLastName());
        document.put(PHONE, customer.getPhone());
        document.put(EMAIL, customer.getEmail());
        return document;
    }

}
