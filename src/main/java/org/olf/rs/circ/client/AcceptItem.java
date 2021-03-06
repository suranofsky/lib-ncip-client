package org.olf.rs.circ.client;

import java.util.HashMap;
import org.extensiblecatalog.ncip.v2.service.AcceptItemInitiationData;
import org.extensiblecatalog.ncip.v2.service.AcceptItemResponseData;
import org.extensiblecatalog.ncip.v2.service.AgencyId;
import org.extensiblecatalog.ncip.v2.service.ApplicationProfileType;
import org.extensiblecatalog.ncip.v2.service.BibliographicDescription;
import org.extensiblecatalog.ncip.v2.service.BibliographicItemId;
import org.extensiblecatalog.ncip.v2.service.BibliographicItemIdentifierCode;
import org.extensiblecatalog.ncip.v2.service.FromAgencyId;
import org.extensiblecatalog.ncip.v2.service.InitiationHeader;
import org.extensiblecatalog.ncip.v2.service.ItemDescription;
import org.extensiblecatalog.ncip.v2.service.ItemId;
import org.extensiblecatalog.ncip.v2.service.ItemOptionalFields;
import org.extensiblecatalog.ncip.v2.service.NCIPInitiationData;
import org.extensiblecatalog.ncip.v2.service.NCIPResponseData;
import org.extensiblecatalog.ncip.v2.service.PickupLocation;
import org.extensiblecatalog.ncip.v2.service.RequestId;
import org.extensiblecatalog.ncip.v2.service.RequestedActionType;
import org.extensiblecatalog.ncip.v2.service.ToAgencyId;
import org.extensiblecatalog.ncip.v2.service.UserId;
import org.json.JSONObject;

public class AcceptItem extends NCIP2Service implements NCIPCircTransaction {

	private String requestIdString;
	private String useridString;
	private String itemIdString;
	private String pickupLocationString;
	protected String toAgency;
	protected String fromAgency;
	private String requestedActionTypeString;
	private String applicationProfileTypeString;
	private HashMap<String, HashMap> itemOptionalFields = new HashMap<String, HashMap>();

	public AcceptItem() {
		itemOptionalFields.put(Constants.BIBLIOGRAPHIC_DESCRIPTION, new HashMap<String, String>());
		itemOptionalFields.put(Constants.ITEM_DESCRIPTION, new HashMap<String, String>());
	}

	public AcceptItem setRequestActionType(String action) {
		requestedActionTypeString = action;
		return this;
	}

	public AcceptItem setApplicationProfileType(String profileType) {
		applicationProfileTypeString = profileType;
		return this;
	}

	public AcceptItem setItemId(String itemId) {
		itemIdString = itemId;
		return this;
	}

	public AcceptItem setRequestId(String requestId) {
		requestIdString = requestId;
		return this;
	}

	public AcceptItem setUserId(String userId) {
		useridString = userId;
		return this;
	}

	public AcceptItem setPickupLocation(String pickupLocation) {
		pickupLocationString = pickupLocation;
		return this;
	}

	public AcceptItem addBibliographicDescription(String bibliographicDescriptionType, String value) {
		itemOptionalFields.get(Constants.BIBLIOGRAPHIC_DESCRIPTION).put(bibliographicDescriptionType, value);
		return this;
	}

	public AcceptItem addItemDescription(String itemDescriptionType, String value) {
		itemOptionalFields.get(Constants.ITEM_DESCRIPTION).put(itemDescriptionType, value);
		return this;
	}

	public AcceptItem setToAgency(String toAgency) {
		this.toAgency = toAgency;
		return this;
	}

	public AcceptItem setFromAgency(String fromAgency) {
		this.fromAgency = fromAgency;
		return this;
	}

	public AcceptItem setRequestedActionTypeString(String actionType) {
		this.requestedActionTypeString = actionType;
		return this;
	}

	// Convenience methods
	public AcceptItem setTitle(String title) {
		this.itemOptionalFields.get(Constants.BIBLIOGRAPHIC_DESCRIPTION).put(Constants.TITLE, title);
		return this;
	}

	public AcceptItem setAuthor(String author) {
		this.itemOptionalFields.get(Constants.BIBLIOGRAPHIC_DESCRIPTION).put(Constants.AUTHOR, author);
		return this;
	}

	public AcceptItem setPublisher(String publisher) {
		this.itemOptionalFields.get(Constants.BIBLIOGRAPHIC_DESCRIPTION).put(Constants.PUBLISHER, publisher);
		return this;
	}

	public AcceptItem setPublicationDate(String pubDate) {
		this.itemOptionalFields.get(Constants.BIBLIOGRAPHIC_DESCRIPTION).put(Constants.PUBLICATION_DATE, pubDate);
		return this;
	}

	public AcceptItem setIsbn(String isbn) {
		this.itemOptionalFields.get(Constants.BIBLIOGRAPHIC_DESCRIPTION).put(Constants.ISBN, isbn);
		return this;
	}

	public AcceptItem setIssn(String issn) {
		this.itemOptionalFields.get(Constants.BIBLIOGRAPHIC_DESCRIPTION).put(Constants.ISSN, issn);
		return this;
	}

	public AcceptItem setCallNumber(String callNumber) {
		this.itemOptionalFields.get(Constants.ITEM_DESCRIPTION).put(Constants.CALL_NUMBER, callNumber);
		return this;
	}

	public NCIPInitiationData generateNCIP2Object() {
		AcceptItemInitiationData acceptItemInitationData = new AcceptItemInitiationData();
		InitiationHeader initiationHeader = new InitiationHeader();
		ApplicationProfileType applicationProfileType = new ApplicationProfileType(null, applicationProfileTypeString);
		initiationHeader.setApplicationProfileType(applicationProfileType);
		ToAgencyId toAgencyId = new ToAgencyId();
		toAgencyId.setAgencyId(new AgencyId(toAgency));
		FromAgencyId fromAgencyId = new FromAgencyId();
		fromAgencyId.setAgencyId(new AgencyId(fromAgency));
		initiationHeader.setToAgencyId(toAgencyId);
		initiationHeader.setFromAgencyId(fromAgencyId);
		acceptItemInitationData.setInitiationHeader(initiationHeader);
		RequestId requestId = new RequestId();
		requestId.setAgencyId(new AgencyId(fromAgency));
		requestId.setRequestIdentifierValue(requestIdString);
		RequestedActionType requestActionType = new RequestedActionType(null, requestedActionTypeString);
		UserId userid = new UserId();
		userid.setAgencyId(new AgencyId(fromAgency));
		userid.setUserIdentifierValue(useridString);
		ItemId itemId = new ItemId();
		itemId.setAgencyId(new AgencyId(fromAgency));
		itemId.setItemIdentifierValue(itemIdString);
		ItemOptionalFields itemOptionalFields = new ItemOptionalFields();
		BibliographicDescription bibliographicDescription = new BibliographicDescription();
		bibliographicDescription.setAuthor(
				(String) this.itemOptionalFields.get(Constants.BIBLIOGRAPHIC_DESCRIPTION).get(Constants.AUTHOR));
		bibliographicDescription.setTitle(
				(String) this.itemOptionalFields.get(Constants.BIBLIOGRAPHIC_DESCRIPTION).get(Constants.TITLE));
		bibliographicDescription.setPublisher(
				(String) this.itemOptionalFields.get(Constants.BIBLIOGRAPHIC_DESCRIPTION).get(Constants.PUBLISHER));
		bibliographicDescription.setPublicationDate((String) this.itemOptionalFields
				.get(Constants.BIBLIOGRAPHIC_DESCRIPTION).get(Constants.PUBLICATION_DATE));
		//TODO
		//I DON'T THINK OLE SUPPORTS THIS--BUT IT COULD BE USEFUL
		//KEEP/REMOVE?
		if (this.itemOptionalFields.get(Constants.BIBLIOGRAPHIC_DESCRIPTION).get(Constants.ISBN) != null) {
			BibliographicItemId bibItemId = new BibliographicItemId();
			bibItemId.setBibliographicItemIdentifier(
					(String) this.itemOptionalFields.get(Constants.BIBLIOGRAPHIC_DESCRIPTION).get(Constants.ISBN));
			BibliographicItemIdentifierCode code = new BibliographicItemIdentifierCode("", Constants.ISBN);
			bibItemId.setBibliographicItemIdentifierCode(code);
		}
		
		if (this.itemOptionalFields.get(Constants.BIBLIOGRAPHIC_DESCRIPTION).get(Constants.ISSN) != null) {
			BibliographicItemId bibItemId = new BibliographicItemId();
			bibItemId.setBibliographicItemIdentifier(
					(String) this.itemOptionalFields.get(Constants.BIBLIOGRAPHIC_DESCRIPTION).get(Constants.ISSN));
			BibliographicItemIdentifierCode code = new BibliographicItemIdentifierCode("", Constants.ISSN);
			bibItemId.setBibliographicItemIdentifierCode(code);
		}
		
		itemOptionalFields.setBibliographicDescription(bibliographicDescription);
		ItemDescription itemDescription = new ItemDescription();
		itemDescription.setCallNumber(
				(String) this.itemOptionalFields.get(Constants.ITEM_DESCRIPTION).get(Constants.CALL_NUMBER));
		itemOptionalFields.setItemDescription(itemDescription);
		PickupLocation pickupLocation = new PickupLocation(pickupLocationString);

		acceptItemInitationData.setItemId(itemId);
		acceptItemInitationData.setPickupLocation(pickupLocation);
		acceptItemInitationData.setUserId(userid);
		acceptItemInitationData.setInitiationHeader(initiationHeader);
		acceptItemInitationData.setRequestId(requestId);
		acceptItemInitationData.setRequestedActionType(requestActionType);
		acceptItemInitationData.setItemOptionalFields(itemOptionalFields);
		return acceptItemInitationData;
	}

	public JSONObject constructResponseNcip2Response(NCIPResponseData responseData) {
		AcceptItemResponseData acceptItem = (AcceptItemResponseData) responseData;
		JSONObject returnJson = new JSONObject();
		
		// DEAL W/PROBLEMS IN THE RESPONSE
		if (acceptItem.getProblems().size() > 0) {
			return constructProblem(responseData);
		}

		System.out.println(acceptItem.getRequestId().getRequestIdentifierValue());
		String itemId = acceptItem.getItemId().getItemIdentifierValue();
		String requestId = acceptItem.getRequestId().getRequestIdentifierValue();

		returnJson.put(Constants.ITEM_ID, itemId);
		returnJson.put(Constants.REQUEST_ID, requestId);
		return returnJson;
	}

}
