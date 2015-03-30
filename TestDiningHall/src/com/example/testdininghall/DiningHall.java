package com.example.testdininghall;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.w3c.tidy.Tidy;

import javax.xml.xpath.*;

import java.util.*; 

import org.w3c.dom.*;

import javax.xml.transform.*;
import javax.xml.transform.stream.*;
import javax.xml.transform.dom.*;

public class DiningHall {
    //XPath for extracting lunch menu information from Covel
    private static String CovelPath_ExK_Lunch = "//div[@class = 'menucontent'][1]//tr[td//text()[contains(., 'Exhibition Kitchen')]]/td[1]//a[@class = 'itemlink']/text() | //div[@class = 'menucontent'][1]//tr[td//text()[contains(., 'Exhibition Kitchen')]]/td[1]//a[@class = 'itemlinkt']/text()";
    private static String CovelPath_EuK_Lunch = "//div[@class = 'menucontent'][1]//tr[td//text()[contains(., 'Euro Kitchen')]]/td[1]//a[@class = 'itemlink']/text() | //div[@class = 'menucontent'][1]//tr[td//text()[contains(., 'Euro Kitchen')]]/td[1]//a[@class = 'itemlinkt']/text()";
    private static String CovelPath_PO_Lunch = "//div[@class = 'menucontent'][1]//tr[td//text()[contains(., 'Pizza Oven')]]/td[1]//a[@class = 'itemlink']/text() | //div[@class = 'menucontent'][1]//tr[td//text()[contains(., 'Pizza Oven')]]/td[1]//a[@class = 'itemlinkt']/text()";
    
     //XPath for extracting lunch menu information from De Neve
    private static String DeNevePath_ExK_Lunch = "//div[@class = 'menucontent'][1]//tr[td//text()[contains(., 'Exhibition Kitchen')]]/td[2]//a[@class = 'itemlink']/text() | //div[@class = 'menucontent'][1]//tr[td//text()[contains(., 'Exhibition Kitchen')]]/td[2]//a[@class = 'itemlinkt']/text()";
    private static String DeNevePath_EuK_Lunch = "//div[@class = 'menucontent'][1]//tr[td//text()[contains(., 'Euro Kitchen')]]/td[2]//a[@class = 'itemlink']/text() | //div[@class = 'menucontent'][1]//tr[td//text()[contains(., 'Euro Kitchen')]]/td[2]//a[@class = 'itemlinkt']/text()";
    private static String DeNevePath_PO_Lunch = "//div[@class = 'menucontent'][1]//tr[td//text()[contains(., 'Pizza Oven')]]/td[2]//a[@class = 'itemlink']/text() | //div[@class = 'menucontent'][1]//tr[td//text()[contains(., 'Pizza Oven')]]/td[2]//a[@class = 'itemlinkt']/text()";
    
    
	 //XPath for extracting lunch menu information from Feast
    private static String Feast_BW_Lunch = "//div[@class = 'menucontent'][1]//tr[td//text()[contains(., 'Bruin Wok')]]/td[1]//a[@class = 'itemlink']/text() | //div[@class = 'menucontent'][1]//tr[td//text()[contains(., 'Bruin Wok')]]/td[1]//a[@class = 'itemlinkt']/text()";
    private static String Feast_SK_Lunch = "//div[@class = 'menucontent'][1]//tr[td//text()[contains(., 'Spice Kitchen')]]/td[1]//a[@class = 'itemlink']/text() | //div[@class = 'menucontent'][1]//tr[td//text()[contains(., 'Spice Kitchen')]]/td[1]//a[@class = 'itemlinkt']/text()";
    private static String Feast_SO_Lunch = "//div[@class = 'menucontent'][1]//tr[td//text()[contains(., 'Stone Oven')]]/td[1]//a[@class = 'itemlink']/text() | //div[@class = 'menucontent'][1]//tr[td//text()[contains(., 'Stone Oven')]]/td[1]//a[@class = 'itemlinkt']/text()";
    private static String Feast_IG_Lunch = "//div[@class = 'menucontent'][1]//tr[td//text()[contains(., 'Iron Grill')]]/td[1]//a[@class = 'itemlink']/text() | //div[@class = 'menucontent'][1]//tr[td//text()[contains(., 'Iron Grill')]]/td[1]//a[@class = 'itemlinkt']/text()";
    
     //XPath for extracting menu lunch information from Bruin Plate
    private static String BruinPlate_FB_Lunch = "//div[@class = 'menucontent'][1]//tr[td//text()[contains(., 'Freshly Bowled')]]/td[2]//a[@class = 'itemlink']/text() | //div[@class = 'menucontent'][1]//tr[td//text()[contains(., 'Freshly Bowled')]]/td[2]//a[@class = 'itemlinkt']/text()";
    private static String BruinPlate_H_Lunch = "//div[@class = 'menucontent'][1]//tr[td//text()[contains(., 'Harvest')]]/td[2]//a[@class = 'itemlink']/text() | //div[@class = 'menucontent'][1]//tr[td//text()[contains(., 'Harvest')]]/td[2]//a[@class = 'itemlinkt']/text()";
    private static String BruinPlate_SF_Lunch = "//div[@class = 'menucontent'][1]//tr[td//text()[contains(., 'Stone Fired')]]/td[2]//a[@class = 'itemlink']/text() | //div[@class = 'menucontent'][1]//tr[td//text()[contains(., 'Stone Fired')]]/td[2]//a[@class = 'itemlinkt']/text()";
    private static String BruinPlate_SG_Lunch = "//div[@class = 'menucontent'][1]//tr[td//text()[contains(., 'Simply Grilled')]]/td[2]//a[@class = 'itemlink']/text() | //div[@class = 'menucontent'][1]//tr[td//text()[contains(., 'Simply Grilled')]]/td[2]//a[@class = 'itemlinkt']/text()";
    
    //XPath for extracting menu dinner information from Covel
    private static String CovelPath_ExK_Dinner = "//div[@class = 'menucontent'][2]//tr[td//text()[contains(., 'Exhibition Kitchen')]]/td[1]//a[@class = 'itemlink']/text() | //div[@class = 'menucontent'][2]//tr[td//text()[contains(., 'Exhibition Kitchen')]]/td[1]//a[@class = 'itemlinkt']/text()";
    private static String CovelPath_EuK_Dinner = "//div[@class = 'menucontent'][2]//tr[td//text()[contains(., 'Euro Kitchen')]]/td[1]//a[@class = 'itemlink']/text() | //div[@class = 'menucontent'][2]//tr[td//text()[contains(., 'Euro Kitchen')]]/td[1]//a[@class = 'itemlinkt']/text()";
    private static String CovelPath_PO_Dinner = "//div[@class = 'menucontent'][2]//tr[td//text()[contains(., 'Pizza Oven')]]/td[1]//a[@class = 'itemlink']/text() | //div[@class = 'menucontent'][2]//tr[td//text()[contains(., 'Pizza Oven')]]/td[1]//a[@class = 'itemlinkt']/text()";
    
     //XPath for extracting menu dinner information from De Neve
    private static String DeNevePath_ExK_Dinner = "//div[@class = 'menucontent'][2]//tr[td//text()[contains(., 'Exhibition Kitchen')]]/td[2]//a[@class = 'itemlink']/text() | //div[@class = 'menucontent'][2]//tr[td//text()[contains(., 'Exhibition Kitchen')]]/td[2]//a[@class = 'itemlinkt']/text()";
    private static String DeNevePath_EuK_Dinner = "//div[@class = 'menucontent'][2]//tr[td//text()[contains(., 'Euro Kitchen')]]/td[2]//a[@class = 'itemlink']/text() | //div[@class = 'menucontent'][2]//tr[td//text()[contains(., 'Euro Kitchen')]]/td[2]//a[@class = 'itemlinkt']/text()";
    private static String DeNevePath_PO_Dinner = "//div[@class = 'menucontent'][2]//tr[td//text()[contains(., 'Pizza Oven')]]/td[2]//a[@class = 'itemlink']/text() | //div[@class = 'menucontent'][2]//tr[td//text()[contains(., 'Pizza Oven')]]/td[2]//a[@class = 'itemlinkt']/text()";
    
     //XPath for extracting menu dinner information from Hedrick
    private static String HedrickPath_EuK_Dinner = "//div[@class = 'menucontent'][2]//tr[td//text()[contains(., 'Euro Kitchen')]]/td[3]//a[@class = 'itemlink']/text() | //div[@class = 'menucontent'][2]//tr[td//text()[contains(., 'Euro Kitchen')]]/td[3]//a[@class = 'itemlinkt']/text()";
    
    //XPath for extracting menu dinner information from Feast
    private static String Feast_BW_Dinner = "//div[@class = 'menucontent'][2]//tr[td//text()[contains(., 'Bruin Wok')]]/td[1]//a[@class = 'itemlink']/text() | //div[@class = 'menucontent'][2]//tr[td//text()[contains(., 'Bruin Wok')]]/td[1]//a[@class = 'itemlinkt']/text()";
    private static String Feast_SK_Dinner = "//div[@class = 'menucontent'][2]//tr[td//text()[contains(., 'Spice Kitchen')]]/td[1]//a[@class = 'itemlink']/text() | //div[@class = 'menucontent'][2]//tr[td//text()[contains(., 'Spice Kitchen')]]/td[1]//a[@class = 'itemlinkt']/text()";
    private static String Feast_SO_Dinner = "//div[@class = 'menucontent'][2]//tr[td//text()[contains(., 'Stone Oven')]]/td[1]//a[@class = 'itemlink']/text() | //div[@class = 'menucontent'][2]//tr[td//text()[contains(., 'Stone Oven')]]/td[1]//a[@class = 'itemlinkt']/text()";
    private static String Feast_IG_Dinner = "//div[@class = 'menucontent'][2]//tr[td//text()[contains(., 'Iron Grill')]]/td[1]//a[@class = 'itemlink']/text() | //div[@class = 'menucontent'][2]//tr[td//text()[contains(., 'Iron Grill')]]/td[1]//a[@class = 'itemlinkt']/text()";
    
     //XPath for extracting menu dinner information from Bruin Plate
    private static String BruinPlate_FB_Dinner = "//div[@class = 'menucontent'][2]//tr[td//text()[contains(., 'Freshly Bowled')]]/td[2]//a[@class = 'itemlink']/text() | //div[@class = 'menucontent'][2]//tr[td//text()[contains(., 'Freshly Bowled')]]/td[2]//a[@class = 'itemlinkt']/text()";
    private static String BruinPlate_H_Dinner = "//div[@class = 'menucontent'][2]//tr[td//text()[contains(., 'Harvest')]]/td[2]//a[@class = 'itemlink']/text() | //div[@class = 'menucontent'][2]//tr[td//text()[contains(., 'Harvest')]]/td[2]//a[@class = 'itemlinkt']/text()";
    private static String BruinPlate_SF_Dinner = "//div[@class = 'menucontent'][2]//tr[td//text()[contains(., 'Stone Fired')]]/td[2]//a[@class = 'itemlink']/text() | //div[@class = 'menucontent'][2]//tr[td//text()[contains(., 'Stone Fired')]]/td[2]//a[@class = 'itemlinkt']/text()";
    private static String BruinPlate_SG_Dinner = "//div[@class = 'menucontent'][2]//tr[td//text()[contains(., 'Simply Grilled')]]/td[2]//a[@class = 'itemlink']/text() | //div[@class = 'menucontent'][2]//tr[td//text()[contains(., 'Simply Grilled')]]/td[2]//a[@class = 'itemlinkt']/text()";
    /*
    private List<String> Covel_Lunch;
    private List<String> DeNeve_Lunch;
    private List<String> Feast_Lunch;
    private List<String> BruinPlate_Lunch;
    private List<String> Covel_Dinner;
    private List<String> DeNeve_Dinner;
    private List<String> Hedrick_Dinner;
    private List<String> BruinPlate_Dinner;
    private List<String> Feast_Dinner;
	*/
    private final List<MenuItem> menu; // array containing all menu information
    private int size=0;


    //default constructor
    public DiningHall(){
        /*
        Covel_Lunch = new ArrayList<String>();
        DeNeve_Lunch = new ArrayList<String>();
        Feast_Lunch = new ArrayList<String>();
        BruinPlate_Lunch = new ArrayList<String>();
        
        Covel_Dinner = new ArrayList<String>();
        DeNeve_Dinner = new ArrayList<String>();
        Feast_Dinner = new ArrayList<String>();
        Hedrick_Dinner = new ArrayList<String>();
        BruinPlate_Dinner = new ArrayList<String>();*/
        menu = new ArrayList<MenuItem>();

    }
    
	public static void printDocument(Document doc, OutputStream out) throws IOException, TransformerException {
	    TransformerFactory tf = TransformerFactory.newInstance();
	    Transformer transformer = tf.newTransformer();
	    transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
	    transformer.setOutputProperty(OutputKeys.METHOD, "xml");
	    transformer.setOutputProperty(OutputKeys.INDENT, "yes");
	    transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
	    transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

	    transformer.transform(new DOMSource(doc), 
		 new StreamResult(new OutputStreamWriter(out, "UTF-8")));
	}
    /*
    public List<String> getCovelLunch(){
        return Covel_Lunch;
    }
    public List<String> getDeNeveLunch(){
        return DeNeve_Lunch;
    }
    public List<String> getFeastLunch(){
        return Feast_Lunch;
    }
    public List<String> getBPLunch(){
        return BruinPlate_Lunch;
    }
    public List<String> getCovelDinner(){
        return Covel_Dinner;
    }
    public List<String> getDeNeveDinner(){
        return DeNeve_Dinner;
    }
    public List<String> getHedrickDinner(){
        return Hedrick_Dinner;
    }
    public List<String> getFeastDinner(){
        return Feast_Dinner;
    }
    public List<String> getBPDinner(){
        return BruinPlate_Dinner;
    }
	*/
    public List<MenuItem> getMenu(){
        return menu;
    }
    public int getSize(){
        return size;
    }
    
    public void createMenu() throws IOException{
        Tidy tidy = new Tidy();
	    tidy.setXHTML(true);
	    tidy.setQuiet(true);
	    tidy.setShowWarnings(false);
        
	    // Parse an HTML page into a DOM document
        
	    URL url = new URL("http://menu.ha.ucla.edu/foodpro/default.asp");
	    Document doc = tidy.parseDOM(url.openStream(), System.out);
        
        XPath xpath = XPathFactory.newInstance().newXPath();
        
	    /* ----------------------COVEL LUNCH------------------------------- */
        
	    // Covel Exhibition Kitchen
	    XPathExpression expr;
		try {
		expr = xpath.compile(CovelPath_ExK_Lunch);
	    NodeList nodes = (NodeList)expr.evaluate(doc, XPathConstants.NODESET);
	    for (int i = 0; i < nodes.getLength(); i++) {
            MenuItem item = new MenuItem();
            boolean found = false;
            for (MenuItem m : menu)
            {
				//if same menu is offered in different dining hall
                if(m.getName().equals(nodes.item(i).getNodeValue())) 
                {
                    m.addLunchDiningHall("Covel"); // just add Covel as dining hall offering this menu
                    found = true;
                    break;
                }
            }
            if(!found) //if menu is not offered in other dining hall
            {
                item.setName(nodes.item(i).getNodeValue()); //obtain and set menu name
                item.addLunchDiningHall("Covel");  //add Covel as dining hall offering this menu
                menu.add(item); //add it to the list of menu available
                size++;
            }
	    }
        
	    // Covel Euro Kitchen
	    expr = xpath.compile(CovelPath_EuK_Lunch);
	    nodes = (NodeList)expr.evaluate(doc, XPathConstants.NODESET);
	    for (int i = 0; i < nodes.getLength(); i++) {
            MenuItem item = new MenuItem();
            boolean found = false;
            for (MenuItem m : menu)
            {
                if(m.getName().equals(nodes.item(i).getNodeValue()))
                {
                    m.addLunchDiningHall("Covel");
                    found = true;
                    break;
                }
            }
            if(!found)
            {
                item.setName(nodes.item(i).getNodeValue());
                item.addLunchDiningHall("Covel");
                menu.add(item);
                size++;
            }
	    }
        
	    // Covel Pizza Oven
	    expr = xpath.compile(CovelPath_PO_Lunch);
	    nodes = (NodeList)expr.evaluate(doc, XPathConstants.NODESET);
	    for (int i = 0; i < nodes.getLength(); i++) {
            MenuItem item = new MenuItem();
            boolean found = false;
            for (MenuItem m : menu)
            {
                if(m.getName().equals(nodes.item(i).getNodeValue()))
                {
                    m.addLunchDiningHall("Covel");
                    found = true;
                    break;
                }
            }
            if(!found)
            {
                item.setName(nodes.item(i).getNodeValue());
                item.addLunchDiningHall("Covel");
                menu.add(item);
                size++;
            }
	    }        
	    /* -------------------------DE NEVE LUNCH------------------------------ */
	    
	    // De Neve Exhibition Kitchen
	    expr = xpath.compile(DeNevePath_ExK_Lunch);
	    nodes = (NodeList)expr.evaluate(doc, XPathConstants.NODESET);
	    for (int i = 0; i < nodes.getLength(); i++) {
            MenuItem item = new MenuItem();
            boolean found = false;
            for (MenuItem m : menu)
            {
                if(m.getName().equals(nodes.item(i).getNodeValue()))
                {
                    m.addLunchDiningHall("De Neve");
                    found = true;
                    break;
                }
            }
            if(!found)
            {
                item.setName(nodes.item(i).getNodeValue());
                item.addLunchDiningHall("De Neve");
                menu.add(item);
                size++;
            }
	    }
        
	    // De Neve Euro Kitchen
	    expr = xpath.compile(DeNevePath_EuK_Lunch);
	    nodes = (NodeList)expr.evaluate(doc, XPathConstants.NODESET);
	    for (int i = 0; i < nodes.getLength(); i++) {
            MenuItem item = new MenuItem();
            boolean found = false;
            for (MenuItem m : menu)
            {
                if(m.getName().equals(nodes.item(i).getNodeValue()))
                {
                    m.addLunchDiningHall("De Neve");
                    found = true;
                    break;
                }
            }
            if(!found)
            {
                item.setName(nodes.item(i).getNodeValue());
                item.addLunchDiningHall("De Neve");
                menu.add(item);
                size++;
            }
	    }
        
	    // De Neve Pizza Oven
	    expr = xpath.compile(DeNevePath_PO_Lunch);
	    nodes = (NodeList)expr.evaluate(doc, XPathConstants.NODESET);
	    for (int i = 0; i < nodes.getLength(); i++) {
            MenuItem item = new MenuItem();
            boolean found = false;
            for (MenuItem m : menu)
            {
                if(m.getName().equals(nodes.item(i).getNodeValue()))
                {
                    m.addLunchDiningHall("De Neve");
                    found = true;
                    break;
                }
            }
            if(!found)
            {
                item.setName(nodes.item(i).getNodeValue());
                item.addLunchDiningHall("De Neve");
                menu.add(item);
                size++;
            }
	    }
        
	    /* --------------------------FEAST LUNCH---------------------------- */
        
	    // Feast Bruin Wok Kitchen
	    expr = xpath.compile(Feast_BW_Lunch);
	    nodes = (NodeList)expr.evaluate(doc, XPathConstants.NODESET);
	    for (int i = 0; i < nodes.getLength(); i++) {
            MenuItem item = new MenuItem();
            boolean found = false;
            for (MenuItem m : menu)
            {
                if(m.getName().equals(nodes.item(i).getNodeValue()))
                {
                    m.addLunchDiningHall("Reiber");
                    found = true;
                    break;
                }
            }
            if(!found)
            {
                item.setName(nodes.item(i).getNodeValue());
                item.addLunchDiningHall("Reiber");
                menu.add(item);
                size++;
            }
	    }
        
	    // Feast Spice Kitchen
	    expr = xpath.compile(Feast_SK_Lunch);
	    nodes = (NodeList)expr.evaluate(doc, XPathConstants.NODESET);
	    for (int i = 0; i < nodes.getLength(); i++) {
            MenuItem item = new MenuItem();
            boolean found = false;
            for (MenuItem m : menu)
            {
                if(m.getName().equals(nodes.item(i).getNodeValue()))
                {
                    m.addLunchDiningHall("Reiber");
                    found = true;
                    break;
                }
            }
            if(!found)
            {
                item.setName(nodes.item(i).getNodeValue());
                item.addLunchDiningHall("Reiber");
                menu.add(item);
                size++;
            }
	    }
        
	    // Feast Stone Oven
	    expr = xpath.compile(Feast_SO_Lunch);
	    nodes = (NodeList)expr.evaluate(doc, XPathConstants.NODESET);
	    for (int i = 0; i < nodes.getLength(); i++) {
            MenuItem item = new MenuItem();
            boolean found = false;
            for (MenuItem m : menu)
            {
                if(m.getName().equals(nodes.item(i).getNodeValue()))
                {
                    m.addLunchDiningHall("Feast");
                    found = true;
                    break;
                }
            }
            if(!found)
            {
                item.setName(nodes.item(i).getNodeValue());
                item.addLunchDiningHall("Feast");
                menu.add(item);
                size++;
            }
	    }
        
	    // Feast Iron Grill
	    expr = xpath.compile(Feast_IG_Lunch);
	    nodes = (NodeList)expr.evaluate(doc, XPathConstants.NODESET);
	    for (int i = 0; i < nodes.getLength(); i++) {
            MenuItem item = new MenuItem();
            boolean found = false;
            for (MenuItem m : menu)
            {
                if(m.getName().equals(nodes.item(i).getNodeValue()))
                {
                    m.addLunchDiningHall("Feast");
                    found = true;
                    break;
                }
            }
            if(!found)
            {
                item.setName(nodes.item(i).getNodeValue());
                item.addLunchDiningHall("Feast");
                menu.add(item);
                size++;
            }
	    }
        
	    /* -------------------------BRUIN PLATE LUNCH------------------------------- */
        
	    // Bruin Plate Freshly Bowled
	    expr = xpath.compile(BruinPlate_FB_Lunch);
	    nodes = (NodeList)expr.evaluate(doc, XPathConstants.NODESET);
	    for (int i = 0; i < nodes.getLength(); i++) {
            MenuItem item = new MenuItem();
            boolean found = false;
            for (MenuItem m : menu)
            {
                if(m.getName().equals(nodes.item(i).getNodeValue()))
                {
                    m.addLunchDiningHall("Bruin Plate");
                    found = true;
                    break;
                }
            }
            if(!found)
            {
                item.setName(nodes.item(i).getNodeValue());
                item.addLunchDiningHall("Bruin Plate");
                menu.add(item);
                size++;
            }
	    }
        
	    // Bruin Plate Harvest
	    expr = xpath.compile(BruinPlate_H_Lunch);
	    nodes = (NodeList)expr.evaluate(doc, XPathConstants.NODESET);
	    for (int i = 0; i < nodes.getLength(); i++) {
            MenuItem item = new MenuItem();
            boolean found = false;
            for (MenuItem m : menu)
            {
                if(m.getName().equals(nodes.item(i).getNodeValue()))
                {
                    m.addLunchDiningHall("Bruin Plate");
                    found = true;
                    break;
                }
            }
            if(!found)
            {
                item.setName(nodes.item(i).getNodeValue());
                item.addLunchDiningHall("Bruin Plate");
                menu.add(item);
                size++;
            }
	    }
        
	    // Bruin Plate Stone Fired
	    expr = xpath.compile(BruinPlate_SF_Lunch);
	    nodes = (NodeList)expr.evaluate(doc, XPathConstants.NODESET);
	    for (int i = 0; i < nodes.getLength(); i++) {
            MenuItem item = new MenuItem();
            boolean found = false;
            for (MenuItem m : menu)
            {
                if(m.getName().equals(nodes.item(i).getNodeValue()))
                {
                    m.addLunchDiningHall("Bruin Plate");
                    found = true;
                    break;
                }
            }
            if(!found)
            {
                item.setName(nodes.item(i).getNodeValue());
                item.addLunchDiningHall("Bruin Plate");
                menu.add(item);
                size++;
            }
	    }
        
	    // Bruin Plate Simply Grilled
	    expr = xpath.compile(BruinPlate_SG_Lunch);
	    nodes = (NodeList)expr.evaluate(doc, XPathConstants.NODESET);
	    for (int i = 0; i < nodes.getLength(); i++) {
            MenuItem item = new MenuItem();
            boolean found = false;
            for (MenuItem m : menu)
            {
                if(m.getName().equals(nodes.item(i).getNodeValue()))
                {
                    m.addLunchDiningHall("Bruin Plate");
                    found = true;
                    break;
                }
            }
            if(!found)
            {
                item.setName(nodes.item(i).getNodeValue());
                item.addLunchDiningHall("Bruin Plate");
                menu.add(item);
                size++;
            }
	    }
        
	    /* -------------------------COVEL DINNER----------------------------- */
        
	    // Covel Exhibition Kitchen
	    expr = xpath.compile(CovelPath_ExK_Dinner);
	    nodes = (NodeList)expr.evaluate(doc, XPathConstants.NODESET);
	    for (int i = 0; i < nodes.getLength(); i++) {
            MenuItem item = new MenuItem();
            boolean found = false;
            for (MenuItem m : menu)
            {
                if(m.getName().equals(nodes.item(i).getNodeValue()))
                {
                    m.addDinnerDiningHall("Covel");
                    found = true;
                    break;
                }
            }
            if(!found)
            {
                item.setName(nodes.item(i).getNodeValue());
                item.addDinnerDiningHall("Covel");
                menu.add(item);
                size++;
            }
	    }
        
	    // Covel Euro Kitchen
	    expr = xpath.compile(CovelPath_EuK_Dinner);
	    nodes = (NodeList)expr.evaluate(doc, XPathConstants.NODESET);
	    for (int i = 0; i < nodes.getLength(); i++) {
            MenuItem item = new MenuItem();
            boolean found = false;
            for (MenuItem m : menu)
            {
                if(m.getName().equals(nodes.item(i).getNodeValue()))
                {
                    m.addDinnerDiningHall("Covel");
                    found = true;
                    break;
                }
            }
            if(!found)
            {
                item.setName(nodes.item(i).getNodeValue());
                item.addDinnerDiningHall("Covel");
                menu.add(item);
                size++;
            }
        }
        
	    // Covel Pizza Oven
	    expr = xpath.compile(CovelPath_PO_Dinner);
	    nodes = (NodeList)expr.evaluate(doc, XPathConstants.NODESET);
	    for (int i = 0; i < nodes.getLength(); i++) {
            MenuItem item = new MenuItem();
            boolean found = false;
            for (MenuItem m : menu)
            {
                if(m.getName().equals(nodes.item(i).getNodeValue()))
                {
                    m.addDinnerDiningHall("Covel");
                    found = true;
                    break;
                }
            }
            if(!found)
            {
                item.setName(nodes.item(i).getNodeValue());
                item.addDinnerDiningHall("Covel");
                menu.add(item);
                size++;
            }
	    }
        
	    /* -------------------------DE NEVE DINNER------------------------------ */
	    
	    // De Neve Exhibition Kitchen
	    expr = xpath.compile(DeNevePath_ExK_Dinner);
	    nodes = (NodeList)expr.evaluate(doc, XPathConstants.NODESET);
	    for (int i = 0; i < nodes.getLength(); i++) {
            MenuItem item = new MenuItem();
            boolean found = false;
            for (MenuItem m : menu)
            {
                if(m.getName().equals(nodes.item(i).getNodeValue()))
                {
                    m.addDinnerDiningHall("De Neve");
                    found = true;
                    break;
                }
            }
            if(!found)
            {
                item.setName(nodes.item(i).getNodeValue());
                item.addDinnerDiningHall("De Neve");
                menu.add(item);
                size++;
            }
	    }
        
	    // De Neve Euro Kitchen
	    expr = xpath.compile(DeNevePath_EuK_Dinner);
	    nodes = (NodeList)expr.evaluate(doc, XPathConstants.NODESET);
	    for (int i = 0; i < nodes.getLength(); i++) {
            MenuItem item = new MenuItem();
            boolean found = false;
            for (MenuItem m : menu)
            {
                if(m.getName().equals(nodes.item(i).getNodeValue()))
                {
                    m.addDinnerDiningHall("De Neve");
                    found = true;
                    break;
                }
            }
            if(!found)
            {
                item.setName(nodes.item(i).getNodeValue());
                item.addDinnerDiningHall("De Neve");
                menu.add(item);
                size++;
            }
	    }
        
	    // De Neve Pizza Oven
	    expr = xpath.compile(DeNevePath_PO_Dinner);
	    nodes = (NodeList)expr.evaluate(doc, XPathConstants.NODESET);
	    for (int i = 0; i < nodes.getLength(); i++) {
            MenuItem item = new MenuItem();
            boolean found = false;
            for (MenuItem m : menu)
            {
                if(m.getName().equals(nodes.item(i).getNodeValue()))
                {
                    m.addDinnerDiningHall("De Neve");
                    found = true;
                    break;
                }
            }
            if(!found)
            {
                item.setName(nodes.item(i).getNodeValue());
                item.addDinnerDiningHall("De Neve");
                menu.add(item);
                size++;
            }
	    }
        
	    /* -------------------------HEDRICK TEST KITCHEN DINNER--------------------------- */
	    
	    // Hedrick Test Kitchen Euro Kitchen
	    expr = xpath.compile(HedrickPath_EuK_Dinner);
	    nodes = (NodeList)expr.evaluate(doc, XPathConstants.NODESET);
	    for (int i = 0; i < nodes.getLength(); i++) {
            MenuItem item = new MenuItem();
            boolean found = false;
            for (MenuItem m : menu)
            {
                if(m.getName().equals(nodes.item(i).getNodeValue()))
                {
                    m.addDinnerDiningHall("Hedrick");
                    found = true;
                    break;
                }
            }
            if(!found)
            {
                item.setName(nodes.item(i).getNodeValue());
                item.addDinnerDiningHall("Hedrick");
                menu.add(item);
                size++;
            }
	    }
        
	    /* --------------------------FEAST DINNER---------------------------- */
        
	    // Feast Bruin Wok Kitchen
	    expr = xpath.compile(Feast_BW_Dinner);
	    nodes = (NodeList)expr.evaluate(doc, XPathConstants.NODESET);
	    for (int i = 0; i < nodes.getLength(); i++) {
            MenuItem item = new MenuItem();
            boolean found = false;
            for (MenuItem m : menu)
            {
                if(m.getName().equals(nodes.item(i).getNodeValue()))
                {
                    m.addDinnerDiningHall("Feast");
                    found = true;
                    break;
                }
            }
            if(!found)
            {
                item.setName(nodes.item(i).getNodeValue());
                item.addDinnerDiningHall("Feast");
                menu.add(item);
                size++;
            }
	    }
        
	    // Feast Spice Kitchen
	    expr = xpath.compile(Feast_SK_Dinner);
	    nodes = (NodeList)expr.evaluate(doc, XPathConstants.NODESET);
	    for (int i = 0; i < nodes.getLength(); i++) {
            MenuItem item = new MenuItem();
            boolean found = false;
            for (MenuItem m : menu)
            {
                if(m.getName().equals(nodes.item(i).getNodeValue()))
                {
                    m.addDinnerDiningHall("Feast");
                    found = true;
                    break;
                }
            }
            if(!found)
            {
                item.setName(nodes.item(i).getNodeValue());
                item.addDinnerDiningHall("Feast");
                menu.add(item);
                size++;
            }
	    }
        
	    // Feast Stone Oven
	    expr = xpath.compile(Feast_SO_Dinner);
	    nodes = (NodeList)expr.evaluate(doc, XPathConstants.NODESET);
	    for (int i = 0; i < nodes.getLength(); i++) {
            MenuItem item = new MenuItem();
            boolean found = false;
            for (MenuItem m : menu)
            {
                if(m.getName().equals(nodes.item(i).getNodeValue()))
                {
                    m.addDinnerDiningHall("Feast");
                    found = true;
                    break;
                }
            }
            if(!found)
            {
                item.setName(nodes.item(i).getNodeValue());
                item.addDinnerDiningHall("Feast");
                menu.add(item);
                size++;
            }
	    }
        
	    // Feast Iron Grill
	    expr = xpath.compile(Feast_IG_Dinner);
	    nodes = (NodeList)expr.evaluate(doc, XPathConstants.NODESET);
	    for (int i = 0; i < nodes.getLength(); i++) {
            MenuItem item = new MenuItem();
            boolean found = false;
            for (MenuItem m : menu)
            {
                if(m.getName().equals(nodes.item(i).getNodeValue()))
                {
                    m.addDinnerDiningHall("Feast");
                    found = true;
                    break;
                }
            }
            if(!found)
            {
                item.setName(nodes.item(i).getNodeValue());
                item.addDinnerDiningHall("Feast");
                menu.add(item);
                size++;
            }
	    }
        
	    /* -------------------------BRUIN PLATE DINNER------------------------------- */
        
	    // Bruin Plate Freshly Bowled
	    expr = xpath.compile(BruinPlate_FB_Dinner);
	    nodes = (NodeList)expr.evaluate(doc, XPathConstants.NODESET);
	    for (int i = 0; i < nodes.getLength(); i++) {
            MenuItem item = new MenuItem();
            boolean found = false;
            for (MenuItem m : menu)
            {
                if(m.getName().equals(nodes.item(i).getNodeValue()))
                {
                    m.addDinnerDiningHall("Bruin Plate");
                    found = true;
                    break;
                }
            }
            if(!found)
            {
                item.setName(nodes.item(i).getNodeValue());
                item.addDinnerDiningHall("Bruin Plate");
                menu.add(item);
                size++;
            }
	    }
        
	    // Bruin Plate Harvest
	    expr = xpath.compile(BruinPlate_H_Dinner);
	    nodes = (NodeList)expr.evaluate(doc, XPathConstants.NODESET);
	    for (int i = 0; i < nodes.getLength(); i++) {
            MenuItem item = new MenuItem();
            boolean found = false;
            for (MenuItem m : menu)
            {
                if(m.getName().equals(nodes.item(i).getNodeValue()))
                {
                    m.addDinnerDiningHall("Bruin Plate");
                    found = true;
                    break;
                }
            }
            if(!found)
            {
                item.setName(nodes.item(i).getNodeValue());
                item.addDinnerDiningHall("Bruin Plate");
                menu.add(item);
                size++;
            }
	    }
        
	    // Bruin Plate Stone Fired
	    expr = xpath.compile(BruinPlate_SF_Dinner);
	    nodes = (NodeList)expr.evaluate(doc, XPathConstants.NODESET);
	    for (int i = 0; i < nodes.getLength(); i++) {
            MenuItem item = new MenuItem();
            boolean found = false;
            for (MenuItem m : menu)
            {
                if(m.getName().equals(nodes.item(i).getNodeValue()))
                {
                    m.addDinnerDiningHall("Bruin Plate");
                    found = true;
                    break;
                }
            }
            if(!found)
            {
                item.setName(nodes.item(i).getNodeValue());
                item.addDinnerDiningHall("Bruin Plate");
                menu.add(item);
                size++;
            }
	    }
        
	    // Bruin Plate Simply Grilled
	    expr = xpath.compile(BruinPlate_SG_Dinner);
	    nodes = (NodeList)expr.evaluate(doc, XPathConstants.NODESET);
	    for (int i = 0; i < nodes.getLength(); i++) {
            MenuItem item = new MenuItem();
            boolean found = false;
            for (MenuItem m : menu)
            {
                if(m.getName().equals(nodes.item(i).getNodeValue()))
                {
                    m.addDinnerDiningHall("Bruin Plate");
                    found = true;
                    break;
                }
            }
            if(!found)
            {
                item.setName(nodes.item(i).getNodeValue());
                item.addDinnerDiningHall("Bruin Plate");
                menu.add(item);
                size++;
            }
	    }
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}
    }

}
