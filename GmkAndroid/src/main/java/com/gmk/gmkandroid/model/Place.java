package com.gmk.gmkandroid.model;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Place {

  private String Id;
  private String Rev;
  private String schema;
  private String name;
  private String type;
  private String category;
  private String address;
  private String postalCode;
  private String phone;
  private String phone2;
  private String phone3;
  private String fax;
  private String cuisine;
  private String website;
  private String email;
  private String facebook;
  private String twitter;
  private String parking;
  private Boolean wifi;
  private Boolean creditCard;
  private String operatingHrs;
  private Boolean delivery;
  private Boolean catering;
  private String source;
  private String created;
  private String updated;
  private List<Double> coordinates = new ArrayList<Double>();
  private Mapbox mapbox;
  private Map<String, Object> additionalProperties = new HashMap<String, Object>();

  public static Place fromJson(JSONObject jsonObject) {
    Place p = new Place();

    // Deserialize json into object fields
    try {
      p.Id = jsonObject.getString("_id");
      p.name = jsonObject.getString("name");
      p.type = jsonObject.getString("type");
      p.category = jsonObject.getString("category");
      p.address = jsonObject.getString("address");
      p.postalCode = jsonObject.getString("postalCode");
      p.phone = jsonObject.getString("phone");
      p.phone2 = jsonObject.getString("phone2");
      p.phone3 = jsonObject.getString("phone3");
      p.fax = jsonObject.getString("fax");
      p.cuisine = jsonObject.getString("cuisine");
      p.website = jsonObject.getString("website");
      p.email = jsonObject.getString("email");
      p.facebook = jsonObject.getString("facebook");
      p.twitter = jsonObject.getString("twitter");
      p.parking = jsonObject.getString("parking");
      p.wifi = jsonObject.getBoolean("wifi");
      p.creditCard = jsonObject.getBoolean("creditCard");
      p.operatingHrs = jsonObject.getString("operatingHrs");
      p.delivery = jsonObject.getBoolean("delivery");
      p.catering = jsonObject.getBoolean("catering");
      p.source = jsonObject.getString("source");
      p.created = jsonObject.getString("created");
      p.updated = jsonObject.getString("updated");

      JSONArray array = jsonObject.getJSONArray("coordinates");
      p.coordinates.add((Double) array.get(0));
      p.coordinates.add((Double) array.get(1));
    } catch (JSONException e) {
      e.printStackTrace();
      return null;
    }
    // Return new object
    return p;
  }

  public static ArrayList<Place> fromJson(JSONArray jsonArray) {
    ArrayList<Place> places = new ArrayList<Place>(jsonArray.length());

    // Process each result in json array, decode and convert to place object
    for (int i=0; i < jsonArray.length(); i++) {
      JSONObject placeJson = null;

      try {
        placeJson = jsonArray.getJSONObject(i);
      } catch (Exception e) {
        e.printStackTrace();
        continue;
      }

      Place p = Place.fromJson(placeJson);
      if (p != null) {
        places.add(p);
      }
    }

    return places;
  }

  /**
   * @return The Id
   */
  public String getId() {
    return Id;
  }

  /**
   * @param Id The _id
   */
  public void setId(String Id) {
    this.Id = Id;
  }

  /**
   * @return The Rev
   */
  public String getRev() {
    return Rev;
  }

  /**
   * @param Rev The _rev
   */
  public void setRev(String Rev) {
    this.Rev = Rev;
  }

  /**
   * @return The schema
   */
  public String getSchema() {
    return schema;
  }

  /**
   * @param schema The schema
   */
  public void setSchema(String schema) {
    this.schema = schema;
  }

  /**
   * @return The name
   */
  public String getName() {
    return name;
  }

  /**
   * @param name The name
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * @return The type
   */
  public String getType() {
    return type;
  }

  /**
   * @param type The type
   */
  public void setType(String type) {
    this.type = type;
  }

  /**
   * @return The category
   */
  public String getCategory() {
    return category;
  }

  /**
   * @param category The category
   */
  public void setCategory(String category) {
    this.category = category;
  }

  /**
   * @return The address
   */
  public String getAddress() {
    return address;
  }

  /**
   * @param address The address
   */
  public void setAddress(String address) {
    this.address = address;
  }

  /**
   * @return The postalCode
   */
  public String getPostalCode() {
    return postalCode;
  }

  /**
   * @param postalCode The postalCode
   */
  public void setPostalCode(String postalCode) {
    this.postalCode = postalCode;
  }

  /**
   * @return The phone
   */
  public String getPhone() {
    return phone;
  }

  /**
   * @param phone The phone
   */
  public void setPhone(String phone) {
    this.phone = phone;
  }

  /**
   * @return The phone2
   */
  public String getPhone2() {
    return phone2;
  }

  /**
   * @param phone2 The phone2
   */
  public void setPhone2(String phone2) {
    this.phone2 = phone2;
  }

  /**
   * @return The phone3
   */
  public String getPhone3() {
    return phone3;
  }

  /**
   * @param phone3 The phone3
   */
  public void setPhone3(String phone3) {
    this.phone3 = phone3;
  }

  /**
   * @return The fax
   */
  public String getFax() {
    return fax;
  }

  /**
   * @param fax The fax
   */
  public void setFax(String fax) {
    this.fax = fax;
  }

  /**
   * @return The cuisine
   */
  public String getCuisine() {
    return cuisine;
  }

  /**
   * @param cuisine The cuisine
   */
  public void setCuisine(String cuisine) {
    this.cuisine = cuisine;
  }

  /**
   * @return The website
   */
  public String getWebsite() {
    return website;
  }

  /**
   * @param website The website
   */
  public void setWebsite(String website) {
    this.website = website;
  }

  /**
   * @return The email
   */
  public String getEmail() {
    return email;
  }

  /**
   * @param email The email
   */
  public void setEmail(String email) {
    this.email = email;
  }

  /**
   * @return The facebook
   */
  public String getFacebook() {
    return facebook;
  }

  /**
   * @param facebook The facebook
   */
  public void setFacebook(String facebook) {
    this.facebook = facebook;
  }

  /**
   * @return The twitter
   */
  public String getTwitter() {
    return twitter;
  }

  /**
   * @param twitter The twitter
   */
  public void setTwitter(String twitter) {
    this.twitter = twitter;
  }

  /**
   * @return The parking
   */
  public String getParking() {
    return parking;
  }

  /**
   * @param parking The parking
   */
  public void setParking(String parking) {
    this.parking = parking;
  }

  /**
   * @return The wifi
   */
  public Boolean getWifi() {
    return wifi;
  }

  /**
   * @param wifi The wifi
   */
  public void setWifi(Boolean wifi) {
    this.wifi = wifi;
  }

  /**
   * @return The creditCard
   */
  public Boolean getCreditCard() {
    return creditCard;
  }

  /**
   * @param creditCard The creditCard
   */
  public void setCreditCard(Boolean creditCard) {
    this.creditCard = creditCard;
  }

  /**
   * @return The operatingHrs
   */
  public String getOperatingHrs() {
    return operatingHrs;
  }

  /**
   * @param operatingHrs The operatingHrs
   */
  public void setOperatingHrs(String operatingHrs) {
    this.operatingHrs = operatingHrs;
  }

  /**
   * @return The delivery
   */
  public Boolean getDelivery() {
    return delivery;
  }

  /**
   * @param delivery The delivery
   */
  public void setDelivery(Boolean delivery) {
    this.delivery = delivery;
  }

  /**
   * @return The catering
   */
  public Boolean getCatering() {
    return catering;
  }

  /**
   * @param catering The catering
   */
  public void setCatering(Boolean catering) {
    this.catering = catering;
  }

  /**
   * @return The source
   */
  public String getSource() {
    return source;
  }

  /**
   * @param source The source
   */
  public void setSource(String source) {
    this.source = source;
  }

  /**
   * @return The created
   */
  public String getCreated() {
    return created;
  }

  /**
   * @param created The created
   */
  public void setCreated(String created) {
    this.created = created;
  }

  /**
   * @return The updated
   */
  public String getUpdated() {
    return updated;
  }

  /**
   * @param updated The updated
   */
  public void setUpdated(String updated) {
    this.updated = updated;
  }

  /**
   * @return The coordinates
   */
  public List<Double> getCoordinates() {
    return coordinates;
  }

  /**
   * @param coordinates The coordinates
   */
  public void setCoordinates(List<Double> coordinates) {
    this.coordinates = coordinates;
  }

  /**
   * @return The mapbox
   */
  public Mapbox getMapbox() {
    return mapbox;
  }

  /**
   * @param mapbox The mapbox
   */
  public void setMapbox(Mapbox mapbox) {
    this.mapbox = mapbox;
  }

  public Map<String, Object> getAdditionalProperties() {
    return this.additionalProperties;
  }

  public void setAdditionalProperty(String name, Object value) {
    this.additionalProperties.put(name, value);
  }
}
