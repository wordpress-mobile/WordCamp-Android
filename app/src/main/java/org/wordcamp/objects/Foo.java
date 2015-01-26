
package org.wordcamp.objects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class Foo implements Serializable {

    @SerializedName("Venue Name")
    @Expose
    private List<String> VenueName = new ArrayList<String>();
    @Expose
    private List<String> Telephone = new ArrayList<String>();
    @SerializedName("Multi-Event Sponsor Region")
    @Expose
    private List<String> MultiEventSponsorRegion = new ArrayList<String>();
    @SerializedName("Budget Wrangler E-mail Address")
    @Expose
    private List<String> BudgetWranglerEMailAddress = new ArrayList<String>();
    @SerializedName("Mailing Address")
    @Expose
    private List<String> MailingAddress = new ArrayList<String>();
    @SerializedName("wcor_sent_email_ids")
    @Expose
    private List<String> wcorSentEmailIds = new ArrayList<String>();
    @SerializedName("Available Rooms")
    @Expose
    private List<String> AvailableRooms = new ArrayList<String>();
    @SerializedName("E-mail Address")
    @Expose
    private List<String> EMailAddress = new ArrayList<String>();
    @SerializedName("WordCamp Hashtag")
    @Expose
    private List<String> WordCampHashtag = new ArrayList<String>();
    @Expose
    private List<String> URL = new ArrayList<String>();
    @SerializedName("End Date (YYYY-mm-dd)")
    @Expose
    private List<String> EndDateYYYYMmDd = new ArrayList<String>();
    @SerializedName("Maximum Capacity")
    @Expose
    private List<String> MaximumCapacity = new ArrayList<String>();
    @SerializedName("Budget Wrangler Name")
    @Expose
    private List<String> BudgetWranglerName = new ArrayList<String>();
    @SerializedName("Website URL")
    @Expose
    private List<String> WebsiteURL = new ArrayList<String>();
    @SerializedName("Sponsor Wrangler E-mail Address")
    @Expose
    private List<String> SponsorWranglerEMailAddress = new ArrayList<String>();
    @Expose
    private List<String> Location = new ArrayList<String>();
    @SerializedName("_edit_last")
    @Expose
    private List<String> EditLast = new ArrayList<String>();
    @SerializedName("Number of Anticipated Attendees")
    @Expose
    private List<String> NumberOfAnticipatedAttendees = new ArrayList<String>();
    @SerializedName("Contact Information")
    @Expose
    private List<String> ContactInformation = new ArrayList<String>();
    @SerializedName("Sponsor Wrangler Name")
    @Expose
    private List<String> SponsorWranglerName = new ArrayList<String>();
    @SerializedName("WordPress.org Username")
    @Expose
    private List<String> WordPressOrgUsername = new ArrayList<String>();
    @SerializedName("_edit_lock")
    @Expose
    private List<String> EditLock = new ArrayList<String>();
    @SerializedName("_timestamp_added_to_planning_schedule")
    @Expose
    private List<String> TimestampAddedToPlanningSchedule = new ArrayList<String>();
    @SerializedName("Organizer Name")
    @Expose
    private List<String> OrganizerName = new ArrayList<String>();
    @SerializedName("Physical Address")
    @Expose
    private List<String> PhysicalAddress = new ArrayList<String>();
    @SerializedName("Email Address")
    @Expose
    private List<String> EmailAddress = new ArrayList<String>();
    @SerializedName("Start Date (YYYY-mm-dd)")
    @Expose
    private List<String> StartDateYYYYMmDd = new ArrayList<String>();
    @Expose
    private List<String> Twitter = new ArrayList<String>();

    /**
     * 
     * @return
     *     The VenueName
     */
    public List<String> getVenueName() {
        return VenueName;
    }

    /**
     * 
     * @param VenueName
     *     The Venue Name
     */
    public void setVenueName(List<String> VenueName) {
        this.VenueName = VenueName;
    }

    /**
     * 
     * @return
     *     The Telephone
     */
    public List<String> getTelephone() {
        return Telephone;
    }

    /**
     * 
     * @param Telephone
     *     The Telephone
     */
    public void setTelephone(List<String> Telephone) {
        this.Telephone = Telephone;
    }

    /**
     * 
     * @return
     *     The MultiEventSponsorRegion
     */
    public List<String> getMultiEventSponsorRegion() {
        return MultiEventSponsorRegion;
    }

    /**
     * 
     * @param MultiEventSponsorRegion
     *     The Multi-Event Sponsor Region
     */
    public void setMultiEventSponsorRegion(List<String> MultiEventSponsorRegion) {
        this.MultiEventSponsorRegion = MultiEventSponsorRegion;
    }

    /**
     * 
     * @return
     *     The BudgetWranglerEMailAddress
     */
    public List<String> getBudgetWranglerEMailAddress() {
        return BudgetWranglerEMailAddress;
    }

    /**
     * 
     * @param BudgetWranglerEMailAddress
     *     The Budget Wrangler E-mail Address
     */
    public void setBudgetWranglerEMailAddress(List<String> BudgetWranglerEMailAddress) {
        this.BudgetWranglerEMailAddress = BudgetWranglerEMailAddress;
    }

    /**
     * 
     * @return
     *     The MailingAddress
     */
    public List<String> getMailingAddress() {
        return MailingAddress;
    }

    /**
     * 
     * @param MailingAddress
     *     The Mailing Address
     */
    public void setMailingAddress(List<String> MailingAddress) {
        this.MailingAddress = MailingAddress;
    }

    /**
     * 
     * @return
     *     The wcorSentEmailIds
     */
    public List<String> getWcorSentEmailIds() {
        return wcorSentEmailIds;
    }

    /**
     * 
     * @param wcorSentEmailIds
     *     The wcor_sent_email_ids
     */
    public void setWcorSentEmailIds(List<String> wcorSentEmailIds) {
        this.wcorSentEmailIds = wcorSentEmailIds;
    }

    /**
     * 
     * @return
     *     The AvailableRooms
     */
    public List<String> getAvailableRooms() {
        return AvailableRooms;
    }

    /**
     * 
     * @param AvailableRooms
     *     The Available Rooms
     */
    public void setAvailableRooms(List<String> AvailableRooms) {
        this.AvailableRooms = AvailableRooms;
    }

    /**
     * 
     * @return
     *     The EMailAddress
     */
    public List<String> getEMailAddress() {
        return EMailAddress;
    }

    /**
     * 
     * @param EMailAddress
     *     The E-mail Address
     */
    public void setEMailAddress(List<String> EMailAddress) {
        this.EMailAddress = EMailAddress;
    }

    /**
     * 
     * @return
     *     The WordCampHashtag
     */
    public List<String> getWordCampHashtag() {
        return WordCampHashtag;
    }

    /**
     * 
     * @param WordCampHashtag
     *     The WordCamp Hashtag
     */
    public void setWordCampHashtag(List<String> WordCampHashtag) {
        this.WordCampHashtag = WordCampHashtag;
    }

    /**
     * 
     * @return
     *     The URL
     */
    public List<String> getURL() {
        return URL;
    }

    /**
     * 
     * @param URL
     *     The URL
     */
    public void setURL(List<String> URL) {
        this.URL = URL;
    }

    /**
     * 
     * @return
     *     The EndDateYYYYMmDd
     */
    public List<String> getEndDateYYYYMmDd() {
        return EndDateYYYYMmDd;
    }

    /**
     * 
     * @param EndDateYYYYMmDd
     *     The End Date (YYYY-mm-dd)
     */
    public void setEndDateYYYYMmDd(List<String> EndDateYYYYMmDd) {
        this.EndDateYYYYMmDd = EndDateYYYYMmDd;
    }

    /**
     * 
     * @return
     *     The MaximumCapacity
     */
    public List<String> getMaximumCapacity() {
        return MaximumCapacity;
    }

    /**
     * 
     * @param MaximumCapacity
     *     The Maximum Capacity
     */
    public void setMaximumCapacity(List<String> MaximumCapacity) {
        this.MaximumCapacity = MaximumCapacity;
    }

    /**
     * 
     * @return
     *     The BudgetWranglerName
     */
    public List<String> getBudgetWranglerName() {
        return BudgetWranglerName;
    }

    /**
     * 
     * @param BudgetWranglerName
     *     The Budget Wrangler Name
     */
    public void setBudgetWranglerName(List<String> BudgetWranglerName) {
        this.BudgetWranglerName = BudgetWranglerName;
    }

    /**
     * 
     * @return
     *     The WebsiteURL
     */
    public List<String> getWebsiteURL() {
        return WebsiteURL;
    }

    /**
     * 
     * @param WebsiteURL
     *     The Website URL
     */
    public void setWebsiteURL(List<String> WebsiteURL) {
        this.WebsiteURL = WebsiteURL;
    }

    /**
     * 
     * @return
     *     The SponsorWranglerEMailAddress
     */
    public List<String> getSponsorWranglerEMailAddress() {
        return SponsorWranglerEMailAddress;
    }

    /**
     * 
     * @param SponsorWranglerEMailAddress
     *     The Sponsor Wrangler E-mail Address
     */
    public void setSponsorWranglerEMailAddress(List<String> SponsorWranglerEMailAddress) {
        this.SponsorWranglerEMailAddress = SponsorWranglerEMailAddress;
    }

    /**
     * 
     * @return
     *     The Location
     */
    public List<String> getLocation() {
        return Location;
    }

    /**
     * 
     * @param Location
     *     The Location
     */
    public void setLocation(List<String> Location) {
        this.Location = Location;
    }

    /**
     * 
     * @return
     *     The EditLast
     */
    public List<String> getEditLast() {
        return EditLast;
    }

    /**
     * 
     * @param EditLast
     *     The _edit_last
     */
    public void setEditLast(List<String> EditLast) {
        this.EditLast = EditLast;
    }

    /**
     * 
     * @return
     *     The NumberOfAnticipatedAttendees
     */
    public List<String> getNumberOfAnticipatedAttendees() {
        return NumberOfAnticipatedAttendees;
    }

    /**
     * 
     * @param NumberOfAnticipatedAttendees
     *     The Number of Anticipated Attendees
     */
    public void setNumberOfAnticipatedAttendees(List<String> NumberOfAnticipatedAttendees) {
        this.NumberOfAnticipatedAttendees = NumberOfAnticipatedAttendees;
    }

    /**
     * 
     * @return
     *     The ContactInformation
     */
    public List<String> getContactInformation() {
        return ContactInformation;
    }

    /**
     * 
     * @param ContactInformation
     *     The Contact Information
     */
    public void setContactInformation(List<String> ContactInformation) {
        this.ContactInformation = ContactInformation;
    }

    /**
     * 
     * @return
     *     The SponsorWranglerName
     */
    public List<String> getSponsorWranglerName() {
        return SponsorWranglerName;
    }

    /**
     * 
     * @param SponsorWranglerName
     *     The Sponsor Wrangler Name
     */
    public void setSponsorWranglerName(List<String> SponsorWranglerName) {
        this.SponsorWranglerName = SponsorWranglerName;
    }

    /**
     * 
     * @return
     *     The WordPressOrgUsername
     */
    public List<String> getWordPressOrgUsername() {
        return WordPressOrgUsername;
    }

    /**
     * 
     * @param WordPressOrgUsername
     *     The WordPress.org Username
     */
    public void setWordPressOrgUsername(List<String> WordPressOrgUsername) {
        this.WordPressOrgUsername = WordPressOrgUsername;
    }

    /**
     * 
     * @return
     *     The EditLock
     */
    public List<String> getEditLock() {
        return EditLock;
    }

    /**
     * 
     * @param EditLock
     *     The _edit_lock
     */
    public void setEditLock(List<String> EditLock) {
        this.EditLock = EditLock;
    }

    /**
     * 
     * @return
     *     The TimestampAddedToPlanningSchedule
     */
    public List<String> getTimestampAddedToPlanningSchedule() {
        return TimestampAddedToPlanningSchedule;
    }

    /**
     * 
     * @param TimestampAddedToPlanningSchedule
     *     The _timestamp_added_to_planning_schedule
     */
    public void setTimestampAddedToPlanningSchedule(List<String> TimestampAddedToPlanningSchedule) {
        this.TimestampAddedToPlanningSchedule = TimestampAddedToPlanningSchedule;
    }

    /**
     * 
     * @return
     *     The OrganizerName
     */
    public List<String> getOrganizerName() {
        return OrganizerName;
    }

    /**
     * 
     * @param OrganizerName
     *     The Organizer Name
     */
    public void setOrganizerName(List<String> OrganizerName) {
        this.OrganizerName = OrganizerName;
    }

    /**
     * 
     * @return
     *     The PhysicalAddress
     */
    public List<String> getPhysicalAddress() {
        return PhysicalAddress;
    }

    /**
     * 
     * @param PhysicalAddress
     *     The Physical Address
     */
    public void setPhysicalAddress(List<String> PhysicalAddress) {
        this.PhysicalAddress = PhysicalAddress;
    }

    /**
     * 
     * @return
     *     The EmailAddress
     */
    public List<String> getEmailAddress() {
        return EmailAddress;
    }

    /**
     * 
     * @param EmailAddress
     *     The Email Address
     */
    public void setEmailAddress(List<String> EmailAddress) {
        this.EmailAddress = EmailAddress;
    }

    /**
     * 
     * @return
     *     The StartDateYYYYMmDd
     */
    public List<String> getStartDateYYYYMmDd() {
        return StartDateYYYYMmDd;
    }

    /**
     * 
     * @param StartDateYYYYMmDd
     *     The Start Date (YYYY-mm-dd)
     */
    public void setStartDateYYYYMmDd(List<String> StartDateYYYYMmDd) {
        this.StartDateYYYYMmDd = StartDateYYYYMmDd;
    }

    /**
     * 
     * @return
     *     The Twitter
     */
    public List<String> getTwitter() {
        return Twitter;
    }

    /**
     * 
     * @param Twitter
     *     The Twitter
     */
    public void setTwitter(List<String> Twitter) {
        this.Twitter = Twitter;
    }

}
