
package org.wordcamp.android.objects.wordcamp;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WordCamp {

    @SerializedName("id")
    @Expose
    private long id;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("date_gmt")
    @Expose
    private String dateGmt;
    @SerializedName("modified")
    @Expose
    private String modified;
    @SerializedName("modified_gmt")
    @Expose
    private String modifiedGmt;
    @SerializedName("slug")
    @Expose
    private String slug;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("link")
    @Expose
    private String link;
    @SerializedName("title")
    @Expose
    private Title title;
    @SerializedName("content")
    @Expose
    private Content content;
    @SerializedName("author")
    @Expose
    private long author;
    @SerializedName("featured_media")
    @Expose
    private long featuredMedia;
    @SerializedName("template")
    @Expose
    private String template;
    @SerializedName("Start Date (YYYY-mm-dd)")
    @Expose
    private String startDateYYYYMmDd;
    @SerializedName("End Date (YYYY-mm-dd)")
    @Expose
    private String endDateYYYYMmDd;
    @SerializedName("Location")
    @Expose
    private String location;
    @SerializedName("URL")
    @Expose
    private String uRL;
    @SerializedName("Twitter")
    @Expose
    private String twitter;
    @SerializedName("WordCamp Hashtag")
    @Expose
    private String wordCampHashtag;
    @SerializedName("Number of Anticipated Attendees")
    @Expose
    private String numberOfAnticipatedAttendees;
    @SerializedName("Organizer Name")
    @Expose
    private String organizerName;
    @SerializedName("WordPress.org Username")
    @Expose
    private String wordPressOrgUsername;
    @SerializedName("Venue Name")
    @Expose
    private String venueName;
    @SerializedName("Physical Address")
    @Expose
    private String physicalAddress;
    @SerializedName("Maximum Capacity")
    @Expose
    private String maximumCapacity;
    @SerializedName("Available Rooms")
    @Expose
    private String availableRooms;
    @SerializedName("Website URL")
    @Expose
    private String websiteURL;
    @SerializedName("Exhibition Space Available")
    @Expose
    private String exhibitionSpaceAvailable;
    @SerializedName("_venue_coordinates")
    @Expose
    private Object venueCoordinates;
    @SerializedName("_venue_city")
    @Expose
    private String venueCity;
    @SerializedName("_venue_state")
    @Expose
    private String venueState;
    @SerializedName("_venue_country_code")
    @Expose
    private String venueCountryCode;
    @SerializedName("_venue_country_name")
    @Expose
    private String venueCountryName;
    @SerializedName("_venue_zip")
    @Expose
    private String venueZip;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDateGmt() {
        return dateGmt;
    }

    public void setDateGmt(String dateGmt) {
        this.dateGmt = dateGmt;
    }

    public String getModified() {
        return modified;
    }

    public void setModified(String modified) {
        this.modified = modified;
    }

    public String getModifiedGmt() {
        return modifiedGmt;
    }

    public void setModifiedGmt(String modifiedGmt) {
        this.modifiedGmt = modifiedGmt;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Title getTitle() {
        return title;
    }

    public void setTitle(Title title) {
        this.title = title;
    }

    public Content getContent() {
        return content;
    }

    public void setContent(Content content) {
        this.content = content;
    }

    public long getAuthor() {
        return author;
    }

    public void setAuthor(long author) {
        this.author = author;
    }

    public long getFeaturedMedia() {
        return featuredMedia;
    }

    public void setFeaturedMedia(long featuredMedia) {
        this.featuredMedia = featuredMedia;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public String getStartDateYYYYMmDd() {
        return startDateYYYYMmDd;
    }

    public void setStartDateYYYYMmDd(String startDateYYYYMmDd) {
        this.startDateYYYYMmDd = startDateYYYYMmDd;
    }

    public String getEndDateYYYYMmDd() {
        return endDateYYYYMmDd;
    }

    public void setEndDateYYYYMmDd(String endDateYYYYMmDd) {
        this.endDateYYYYMmDd = endDateYYYYMmDd;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getURL() {
        return uRL;
    }

    public void setURL(String uRL) {
        this.uRL = uRL;
    }

    public String getTwitter() {
        return twitter;
    }

    public void setTwitter(String twitter) {
        this.twitter = twitter;
    }

    public String getWordCampHashtag() {
        return wordCampHashtag;
    }

    public void setWordCampHashtag(String wordCampHashtag) {
        this.wordCampHashtag = wordCampHashtag;
    }

    public String getNumberOfAnticipatedAttendees() {
        return numberOfAnticipatedAttendees;
    }

    public void setNumberOfAnticipatedAttendees(String numberOfAnticipatedAttendees) {
        this.numberOfAnticipatedAttendees = numberOfAnticipatedAttendees;
    }

    public String getOrganizerName() {
        return organizerName;
    }

    public void setOrganizerName(String organizerName) {
        this.organizerName = organizerName;
    }

    public String getWordPressOrgUsername() {
        return wordPressOrgUsername;
    }

    public void setWordPressOrgUsername(String wordPressOrgUsername) {
        this.wordPressOrgUsername = wordPressOrgUsername;
    }

    public String getVenueName() {
        return venueName;
    }

    public void setVenueName(String venueName) {
        this.venueName = venueName;
    }

    public String getPhysicalAddress() {
        return physicalAddress;
    }

    public void setPhysicalAddress(String physicalAddress) {
        this.physicalAddress = physicalAddress;
    }

    public String getMaximumCapacity() {
        return maximumCapacity;
    }

    public void setMaximumCapacity(String maximumCapacity) {
        this.maximumCapacity = maximumCapacity;
    }

    public String getAvailableRooms() {
        return availableRooms;
    }

    public void setAvailableRooms(String availableRooms) {
        this.availableRooms = availableRooms;
    }

    public String getWebsiteURL() {
        return websiteURL;
    }

    public void setWebsiteURL(String websiteURL) {
        this.websiteURL = websiteURL;
    }

    public String getExhibitionSpaceAvailable() {
        return exhibitionSpaceAvailable;
    }

    public void setExhibitionSpaceAvailable(String exhibitionSpaceAvailable) {
        this.exhibitionSpaceAvailable = exhibitionSpaceAvailable;
    }

    public Object getVenueCoordinates() {
        return venueCoordinates;
    }

    public void setVenueCoordinates(Object venueCoordinates) {
        this.venueCoordinates = venueCoordinates;
    }

    public String getVenueCity() {
        return venueCity;
    }

    public void setVenueCity(String venueCity) {
        this.venueCity = venueCity;
    }

    public String getVenueState() {
        return venueState;
    }

    public void setVenueState(String venueState) {
        this.venueState = venueState;
    }

    public String getVenueCountryCode() {
        return venueCountryCode;
    }

    public void setVenueCountryCode(String venueCountryCode) {
        this.venueCountryCode = venueCountryCode;
    }

    public String getVenueCountryName() {
        return venueCountryName;
    }

    public void setVenueCountryName(String venueCountryName) {
        this.venueCountryName = venueCountryName;
    }

    public String getVenueZip() {
        return venueZip;
    }

    public void setVenueZip(String venueZip) {
        this.venueZip = venueZip;
    }
}
